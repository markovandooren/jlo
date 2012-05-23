package jlo.translate;

import java.util.ArrayList;
import java.util.List;

import jlo.model.component.ComponentRelation;
import jlo.model.component.ComponentType;
import jlo.model.component.ConfigurationBlock;
import jlo.model.component.ConfigurationClause;
import jlo.model.component.Export;
import jlo.model.component.RenamingClause;
import jlo.model.expression.AbstractTarget;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.lookup.LookupException;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.expression.Expression;
import chameleon.oo.expression.MethodInvocation;
import chameleon.oo.member.Member;
import chameleon.oo.member.SimpleNameDeclarationWithParametersSignature;
import chameleon.oo.method.Method;
import chameleon.oo.method.RegularImplementation;
import chameleon.oo.statement.Block;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeReference;
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

	public void inner(Type javaType, ComponentRelation relation, ComponentRelation originalRelation) throws ChameleonProgrammerException, ModelException {
		Type innerClass = createInnerClassFor(relation,javaType, originalRelation);
		javaType.flushCache();
		Type componentType = relation.componentType();
		Type originalComponentType = null;
		List<ComponentRelation> originalRelations = null;
		if(originalRelation != null) {
			originalComponentType = originalRelation.componentType();
			originalRelations = originalComponentType.directlyDeclaredElements(ComponentRelation.class);
		}
		List<ComponentRelation> relations = componentType.directlyDeclaredElements(ComponentRelation.class);
		int size = relations.size();
		for(int i = 0; i < size; i++) {
			ComponentRelation nestedRelation = relations.get(i);
			ComponentRelation originalNestedRelation = null;
			if(originalRelations != null) {
				originalNestedRelation = originalRelations.get(i);
			}
			// subst parameters
			ComponentRelation clonedNestedRelation = nestedRelation.clone();
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			try {
			  substituteTypeParameters(clonedNestedRelation);
			} catch(LookupException exc) {
			  substituteTypeParameters(clonedNestedRelation);
				throw exc;
			}
			inner(innerClass, clonedNestedRelation, originalNestedRelation);
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
				final Method method = (Method) decl;
				Method alias = createAlias(relation, method, ((SimpleNameDeclarationWithParametersSignature)renamingClause.newSignature()).name());
				outer.add(alias);
			}
		}
	}
	
	private Method createAlias(ComponentRelation relation, Method method, String newName) throws LookupException {
		NormalMethod result;
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
	private Type createInnerClassFor(ComponentRelation relationBeingTranslated, Type javaType, ComponentRelation original) throws ChameleonProgrammerException, ModelException {
		Type result = innerClassCreator().emptyInnerClassFor(relationBeingTranslated);
		processComponentRelationBody(relationBeingTranslated, result, original);
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

	private void processComponentRelationBody(ComponentRelation relation, Type result, ComponentRelation original)
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
		processOverriddenSubobjects(relation,result,original);
	}
	
	private void processOverriddenSubobjects(ComponentRelation unused, Type result, ComponentRelation original) throws LookupException {
		if(original != null) {
			List<ComponentRelation> todo = (List<ComponentRelation>)original.directlyOverriddenMembers();
			List<Member> members = original.componentType().directlyDeclaredMembers();
			while(! todo.isEmpty()) {
				ComponentRelation overridden = todo.get(0);
				todo.remove(0);
				ComponentType ctype = overridden.componentTypeDeclaration();
				ComponentType clonedType = ctype.clone();
				clonedType.setUniParent(overridden);
				// The outer and root targets are replaced now because they need to be in the subobjects themselves in order
				// to use the target semantics of the Outer call. Otherwise we must encode its semantics in the translator.
				replaceOuterAndRootTargets(clonedType);
				for(Member typeElement:clonedType.body().members()) {
					if((PROCESS_NESTED_CONSTRUCTORS || ! (typeElement instanceof ComponentRelation)) && (! (typeElement instanceof Export))) {
						if(! alreadyContains(members,typeElement)) {
							Member toAdd = typeElement.clone();
							toAdd.setUniParent(typeElement.parent());
							expandReferences(toAdd);
							rewriteAllThis(toAdd,unused, original);
							toAdd.setUniParent(null);
							result.add(toAdd);
							members.add(toAdd);
						}
					}
				}
			}
		}
	}
	
	private void rewriteAllThis(Member member, ComponentRelation relation, ComponentRelation original) throws LookupException {
		for(ThisLiteral literal: member.descendants(ThisLiteral.class)) {
			rewriteThis(literal,relation,original);
		}
	}
	
	private void rewriteThis(ThisLiteral literal, ComponentRelation relation, ComponentRelation original) throws LookupException {
		TypeReference tref = literal.getTypeReference();
		if(tref != null) {
			Type t = tref.getType();
//			Type type = original.nearestAncestor(Type.class);
			Type literalAncestor = relation.nearestAncestor(Type.class);
			Type lit = null;
			while(literalAncestor != null && lit == null) {
				if(literalAncestor.subTypeOf(t)) {
					lit = literalAncestor;
				} else {
					literalAncestor = relation.nearestAncestor(Type.class);
				}
			}
			Java language = relation.language(Java.class);
			literal.setTypeReference(language.createTypeReference(lit.signature().name()));
		}
	}
	
  private boolean alreadyContains(List<Member> members, Member member) throws LookupException {
  	for(Member m: members) {
  		if(member.signature().sameAs(m.signature())) {
  			return true;
  		}
  	}
  	return false;
  }

	private static boolean PROCESS_NESTED_CONSTRUCTORS=true;

	private void replaceOuterAndRootTargets(TypeElement clone) {
		List<AbstractTarget> outers = clone.descendants(AbstractTarget.class);
		for(AbstractTarget o: outers) {
			String name = o.getTargetDeclaration().name();
			SingleAssociation parentLink = o.parentLink();
			ThisLiteral e = new ThisLiteral();
			e.setTypeReference(new BasicJavaTypeReference(name));
			parentLink.getOtherRelation().replace(parentLink, e.parentLink());
		}
	}

}
