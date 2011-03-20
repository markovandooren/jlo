package subobjectjava.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jnome.core.expression.invocation.SuperConstructorDelegation;
import jnome.core.language.Java;

import org.rejuse.logic.ternary.Ternary;

import subobjectjava.model.component.ComponentParameterTypeReference;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.type.RegularJLoType;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.reference.SimpleReference;
import chameleon.core.statement.Block;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.BasicTypeReference;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.generics.ActualType;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.statement.StatementExpression;
import chameleon.util.Util;

public class InnerClassCreator extends AbstractTranslator {
	
	public InnerClassCreator(SelectorCreator selectorCreator) {
		_selectorCreator = selectorCreator;
	}
	
	private SelectorCreator _selectorCreator;
	
	public SelectorCreator selectorCreator() {
		return _selectorCreator;
	}

	public Type emptyInnerClassFor(ComponentRelation relationBeingTranslated, Type outer) throws LookupException {
		incorporateImports(relationBeingTranslated);
		String className = innerClassName(relationBeingTranslated);
		Type result = new RegularJLoType(className);
		for(Modifier mod: relationBeingTranslated.modifiers()) {
			result.addModifier(mod.clone());
		}
		
		for(TypeReference superReference : superClassReferences(relationBeingTranslated)) {
			result.addInheritanceRelation(new SubtypeRelation(superReference));
		}
//    TypeReference superReference = superClassReference(relationBeingTranslated);
//		result.addInheritanceRelation(new SubtypeRelation(superReference));

		List<Method> selectors = selectorCreator().selectorsFor(relationBeingTranslated);
		for(Method selector:selectors) {
			result.add(selector);
		}
		processInnerClassMethod(relationBeingTranslated, relationBeingTranslated.referencedComponentType(), result);
		return result;
	}

private TypeReference superClassReference(ComponentRelation relation) throws LookupException {
	TypeReference superReference = relation.componentTypeReference().clone();
	if(superReference instanceof ComponentParameterTypeReference) {
		superReference = ((ComponentParameterTypeReference) superReference).componentTypeReference();
	}
	return superReference;
}

private List<TypeReference> superClassReferences(ComponentRelation relation) throws LookupException {
	Java language = relation.language(Java.class);
	List<TypeReference> result = new ArrayList<TypeReference>();
	TypeReference superReference = relation.componentTypeReference().clone();
	if(superReference instanceof ComponentParameterTypeReference) {
		superReference = ((ComponentParameterTypeReference) superReference).componentTypeReference();
	}
	superReference.setUniParent(relation);
	substituteTypeParameters(superReference);
	superReference.setUniParent(null);

	result.add(superReference);
	Set<ComponentRelation> superSubobjects = (Set<ComponentRelation>) relation.overriddenMembers();
	for(ComponentRelation superSubobject: superSubobjects) {
		result.add(language.createNonLocalTypeReference(language.createTypeReference(innerClassFQN(superSubobject)), language.defaultNamespace()));
	}
	return result;
}

private String innerClassFQN(ComponentRelation relation) throws LookupException {
	return relation.componentType().getFullyQualifiedName();//innerClassName(relation.signature()); 
}

private void processInnerClassMethod(ComponentRelation relation, Type componentType, Type result) throws LookupException {
	List<Method> localMethods = componentType.directlyDeclaredMembers(Method.class);
	for(Method<?,?,?,?> method: localMethods) {
		if(method.is(method.language(ObjectOrientedLanguage.class).CONSTRUCTOR) == Ternary.TRUE) {
			NormalMethod<?,?,?> clone = (NormalMethod) method.clone();
			clone.setUniParent(method.parent());
			for(BasicTypeReference<?> tref: clone.descendants(BasicTypeReference.class)) {
				if(tref.getTarget() == null) {
				  Type element = tref.getElement();
					Type base = element.baseType();
				  if((! (element instanceof ActualType)) && base instanceof RegularType) {
				  	String fqn = base.getFullyQualifiedName();
				  	String qn = Util.getAllButLastPart(fqn);
				  	if(qn != null && (! qn.isEmpty())) {
				  		tref.setTarget(new SimpleReference<TargetDeclaration>(qn, TargetDeclaration.class));
				  	}
				  }
				}
			}
			clone.setUniParent(null);
			String name = result.signature().name();
			RegularImplementation impl = (RegularImplementation) clone.implementation();
			Block block = new Block();
			impl.setBody(block);
			// substitute parameters before replace the return type, method name, and the body.
			// the types are not known in the component type, and the super class of the component type
			// may not have a constructor with the same signature as the current constructor.
			substituteTypeParameters(method, clone);
			MethodInvocation inv = new SuperConstructorDelegation();
			useParametersInInvocation(clone, inv);
			block.addStatement(new StatementExpression(inv));
			clone.setReturnTypeReference(relation.language(Java.class).createTypeReference(name));
			((SimpleNameDeclarationWithParametersHeader)clone.header()).setName(name);
			result.add(clone);
		}
	}
}

/**
 * Incorporate the imports of the namespace part of the declared type of the component relation to
 * the namespace part of the component relation.
 * @param relationBeingTranslated
 * @throws LookupException
 */
private void incorporateImports(ComponentRelation relationBeingTranslated) throws LookupException {
	incorporateImports(relationBeingTranslated, relationBeingTranslated.farthestAncestor(NamespacePart.class));
}


}
