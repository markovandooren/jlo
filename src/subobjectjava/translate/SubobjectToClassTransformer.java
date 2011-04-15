package subobjectjava.translate;

import java.util.List;

import org.rejuse.association.SingleAssociation;

import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.type.BasicJavaTypeReference;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ComponentType;
import subobjectjava.model.component.ConfigurationBlock;
import subobjectjava.model.component.ConfigurationClause;
import subobjectjava.model.component.RenamingClause;
import subobjectjava.model.expression.AbstractTarget;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersSignature;
import chameleon.core.expression.Expression;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.statement.Block;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeWithBody;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.member.simplename.method.NormalMethod;

public class SubobjectToClassTransformer extends AbstractTranslator {
	
	public SubobjectToClassTransformer(InnerClassCreator innerClassCreator) {
		_innerClassCreator = innerClassCreator;
	}
	
	private InnerClassCreator _innerClassCreator;
	
	public InnerClassCreator innerClassCreator() {
		return _innerClassCreator;
	}

	public void inner(Type javaType, ComponentRelation relation) throws LookupException {
		Type innerClass = createInnerClassFor(relation);
		javaType.add(innerClass);
		Type componentType = relation.componentType();
		for(ComponentRelation nestedRelation: componentType.directlyDeclaredElements(ComponentRelation.class)) {
			// subst parameters
			ComponentRelation clonedNestedRelation = nestedRelation.clone();
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			substituteTypeParameters(clonedNestedRelation);
			inner(innerClass, clonedNestedRelation);
		}
		addAliasDelegations(relation, innerClass.nearestAncestor(Type.class),relation.nearestAncestor(Type.class));
	}
	
	private void addAliasDelegations(ComponentRelation relation, Type outer, Type original) throws LookupException {
//			TypeWithBody componentTypeDeclaration = relation.componentTypeDeclaration();
			ConfigurationBlock block = relation.configurationBlock();
			if(block != null) {
				for(ConfigurationClause clause: block.clauses()) {
					if(clause instanceof RenamingClause) {
						RenamingClause ov = (RenamingClause)clause;
						Declaration decl = ov.oldDeclaration();
						if(decl instanceof Method) {
							final Method<?,?,?,?> method = (Method<?, ?, ?, ?>) decl;
							Method alias = createAlias(relation, method, ((SimpleNameDeclarationWithParametersSignature)ov.newSignature()).name());
							outer.add(alias);
							//outer.add(staticAlias(alias,method,original));
						}
					}
				}
			}
	}
	
	private Method createAlias(ComponentRelation relation, Method<?,?,?,?> method, String newName) throws LookupException {
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
	
	private Method staticAlias(Method alias, Method<?,?,?,?> aliasedMethod, Type original) {
		Method<?,?,?,?> result = alias.clone();
		String name = staticMethodName(alias, original);
		if(name.equals("radio_Radio_frequency_setValue")) {
			System.out.println("debug");
		}
		result.setName(name);
		for(SimpleNameMethodInvocation invocation:result.descendants(SimpleNameMethodInvocation.class)) {
			if(invocation.getTarget() != null) {
				invocation.setName(staticMethodName(aliasedMethod, aliasedMethod.nearestAncestor(Type.class)));
			}
		}
		return result;
	}

	/**
	 * 
	 * @param relationBeingTranslated A component relation from either the original class, or one of its nested components.
	 * @param outerJavaType The outer class being generated.
	 */
	private Type createInnerClassFor(ComponentRelation relationBeingTranslated) throws ChameleonProgrammerException, LookupException {
		Type result = innerClassCreator().emptyInnerClassFor(relationBeingTranslated);
		processComponentRelationBody(relationBeingTranslated, result);
		return result;
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
			if(! (typeElement instanceof ComponentRelation)) {
				result.add(typeElement);
			}
			if(relation.signature().name().equals("value") && typeElement instanceof Method && (((Method)typeElement).name().equals("getValue"))) {
				System.out.println("debug");
			}
		}
	}

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
