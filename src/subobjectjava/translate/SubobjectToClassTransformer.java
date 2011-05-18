package subobjectjava.translate;

import java.util.ArrayList;
import java.util.List;

import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.association.SingleAssociation;

import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ComponentType;
import subobjectjava.model.component.ConfigurationBlock;
import subobjectjava.model.component.ConfigurationClause;
import subobjectjava.model.component.Export;
import subobjectjava.model.component.RenamingClause;
import subobjectjava.model.expression.AbstractTarget;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersSignature;
import chameleon.core.expression.Expression;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.statement.Block;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.method.NormalMethod;

public class SubobjectToClassTransformer extends AbstractTranslator {
	
	public SubobjectToClassTransformer(InnerClassCreator innerClassCreator, SubobjectConstructorTransformer subobjectConstructorTransformer) {
		_innerClassCreator = innerClassCreator;
		_subobjectConstructorTransformer = subobjectConstructorTransformer;
	}
	
	private InnerClassCreator _innerClassCreator;
	
	public InnerClassCreator innerClassCreator() {
		return _innerClassCreator;
	}
	
	private SubobjectConstructorTransformer _subobjectConstructorTransformer;
	
	public SubobjectConstructorTransformer subobjectConstructorTransformer() {
		return _subobjectConstructorTransformer;
	}

	public void inner(Type javaType, ComponentRelation relation) throws ChameleonProgrammerException, ModelException {
		Type innerClass = createInnerClassFor(relation,javaType);
		javaType.flushCache();
		Type componentType = relation.componentType();
		for(ComponentRelation nestedRelation: componentType.directlyDeclaredElements(ComponentRelation.class)) {
			// subst parameters
			ComponentRelation clonedNestedRelation = nestedRelation.clone();
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			try {
			  substituteTypeParameters(clonedNestedRelation);
			} catch(LookupException exc) {
			  substituteTypeParameters(clonedNestedRelation);
				throw exc;
			}
			inner(innerClass, clonedNestedRelation);
		}
		addAliasDelegations(relation, innerClass.nearestAncestor(Type.class),relation.nearestAncestor(Type.class));
	}
	
	private void addAliasDelegations(ComponentRelation relation, Type outer, Type original) throws LookupException {
		List<RenamingClause> clauses = new ArrayList<RenamingClause>();
		ConfigurationBlock block = relation.configurationBlock();
		if(block != null) {
			for(ConfigurationClause clause: block.clauses()) {
				if(clause instanceof RenamingClause) {
					clauses.add((RenamingClause) clause);
				}
			}
		}
		for(Export exp: relation.componentType().directlyDeclaredElements(Export.class)) {
			clauses.addAll(exp.clauses());
		}
		for(RenamingClause renamingClause: clauses) {
			Declaration decl = renamingClause.oldDeclaration();
			if(decl instanceof Method) {
				final Method<?,?,?> method = (Method<?, ?, ?>) decl;
				Method alias = createAlias(relation, method, ((SimpleNameDeclarationWithParametersSignature)renamingClause.newSignature()).name());
				outer.add(alias);
			}
		}
	}
	
	private Method createAlias(ComponentRelation relation, Method<?,?,?> method, String newName) throws LookupException {
		NormalMethod<?,?,?> result;
		result = innerMethod(method, newName);
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		MethodInvocation invocation = invocation(result, method.name());
		Expression target = new JavaMethodInvocation(getterName(relation), null);
		invocation.setTarget(target);
		substituteTypeParameters(method, result);
		addImplementation(method, body, invocation);
		return result;
	}
	
	/**
	 * 
	 * @param relationBeingTranslated A component relation from either the original class, or one of its nested components.
	 * @param outerJavaType The outer class being generated.
	 * @throws ModelException 
	 */
	private Type createInnerClassFor(ComponentRelation relationBeingTranslated, Type javaType) throws ChameleonProgrammerException, ModelException {
		if(relationBeingTranslated.componentType().getFullyQualifiedName().equals("radio.NestedRefinedMistunedRadio.frequency")) {
			System.out.println("debug");
		}
		Type result = innerClassCreator().emptyInnerClassFor(relationBeingTranslated);
		processComponentRelationBody(relationBeingTranslated, result);
		javaType.add(result);
		if(PROCESS_NESTED_CONSTRUCTORS) {
			List<InheritanceRelation> inheritanceRelations = result.nonMemberInheritanceRelations();
			int size = inheritanceRelations.size();
			for(int i=1; i< size; i++) {
				inheritanceRelations.get(i).setUniParent(null);
			}
			subobjectConstructorTransformer().replaceSubobjectConstructorCalls(result);
			removeSubobjects(result);
			for(int i=1; i< size; i++) {
				result.addInheritanceRelation(inheritanceRelations.get(i));
			}
		}
		return result;
	}
	
	

	private void removeSubobjects(Type result) {
		for(ComponentRelation relation:result.directlyDeclaredElements(ComponentRelation.class)) {
			relation.disconnect();
		}
	}

	private void processComponentRelationBody(ComponentRelation relation, Type result)
	throws LookupException {
		ComponentType ctype = relation.componentTypeDeclaration();
		ComponentType clonedType = ctype.clone();
		clonedType.setUniParent(relation);
		// The outer and root targets are replaced now because they need to be in the subobjects themselves in order
		// to use the target semantics of the Outer call. Otherwise we must encode its semantics in the translator.
		replaceOuterAndRootTargets(clonedType);
		for(TypeElement typeElement:clonedType.body().elements()) {
			if((PROCESS_NESTED_CONSTRUCTORS || ! (typeElement instanceof ComponentRelation)) && (! (typeElement instanceof Export))) {
				result.add(typeElement);
			}
		}
	}
	
	private static boolean PROCESS_NESTED_CONSTRUCTORS=true;

	private void replaceOuterAndRootTargets(TypeElement<?> clone) {
		List<AbstractTarget> outers = clone.descendants(AbstractTarget.class);
		for(AbstractTarget o: outers) {
			String name = o.getTargetDeclaration().getName();
			SingleAssociation parentLink = o.parentLink();
			ThisLiteral e = new ThisLiteral();
			e.setTypeReference(new BasicJavaTypeReference(name));
			parentLink.getOtherRelation().replace(parentLink, e.parentLink());
		}
	}

}
