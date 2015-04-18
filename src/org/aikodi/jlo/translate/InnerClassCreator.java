package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.namespace.RootNamespace;
import org.aikodi.chameleon.core.namespacedeclaration.NamespaceDeclaration;
import org.aikodi.chameleon.core.reference.NameReference;
import org.aikodi.chameleon.core.tag.TagImpl;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.BasicTypeReference;
import org.aikodi.chameleon.oo.type.RegularType;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.ActualTypeArgument;
import org.aikodi.chameleon.oo.type.generics.InstantiatedParameterType;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.type.RegularJLoType;

import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.SuperConstructorDelegation;
import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.association.Association;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;

public class InnerClassCreator extends AbstractTranslator {
	
	public InnerClassCreator(SelectorCreator selectorCreator) {
		_selectorCreator = selectorCreator;
	}
	
	private SelectorCreator _selectorCreator;
	
	public SelectorCreator selectorCreator() {
		return _selectorCreator;
	}

	public Type emptyInnerClassFor(Subobject relationBeingTranslated) throws LookupException {
		incorporateImports(relationBeingTranslated);
		String className = innerClassName(relationBeingTranslated);
		Type result = new RegularJLoType(className);
		for(Modifier mod: relationBeingTranslated.modifiers()) {
			result.addModifier(Util.clone(mod));
		}
		
		for(TypeReference superReference : superClassReferences(relationBeingTranslated,result)) {
			result.addInheritanceRelation(new SubtypeRelation(superReference));
		}
		//JENS
		List<Method> selectors = selectorCreator().selectorsFor(relationBeingTranslated);
		for(Method selector:selectors) {
			result.add(selector);
		}
		processInnerClassMethod(relationBeingTranslated, result);
		return result;
	}
//	Type t = superReference.getElement();
//	if(isJLo(t) && (! splitClass(t))) {
//		transformToImplReference(superReference);
//	}

	private List<TypeReference> superClassReferences(Subobject relation, Type context) throws LookupException {
		View view = relation.view();
		Java7 language = view.language(Java7.class);
		List<TypeReference> result = new ArrayList<TypeReference>();
		TypeReference superReference = Util.clone(relation.componentTypeReference());
		superReference.setUniParent(relation);
		substituteTypeParameters(superReference);
		Type parent = relation.nearestAncestor(Type.class);
		Type type = superReference.getType();
		String superTypeName = type.getFullyQualifiedName();
		RootNamespace namespace = view.namespace();
		boolean toImpl = false;
		if(isJLo(parent) && ! splitClass(parent)) {
			toImpl = true;
		}
		BasicJavaTypeReference expandedSuperTypeReference = language.createTypeReference(superTypeName);
		List<ActualTypeArgument> typeArguments = ((BasicJavaTypeReference)superReference).typeArguments();
		for(ActualTypeArgument arg: typeArguments) {
			TypeReference tref = arg.substitutionReference();
			Type trefType = tref.getElement();
			BasicJavaTypeReference expandedTrefTypeReference = language.createExpandedTypeReference(trefType);

			// Creating the non-local reference will disconnect 'tref' from its parent, so we must store
			// the association end to which it is connected.
			Association parentLink = tref.parentLink().getOtherRelation();
			expandedTrefTypeReference.parentLink().connectTo(parentLink);
			expandedSuperTypeReference.addArgument(arg);
			//		TypeReference nonLocalTref = language.createNonLocalTypeReference(tref, tref.getElement());
			//		nonLocalTref.parentLink().connectTo(parentLink);
		}
		TypeReference nonLocal = language.createNonLocalTypeReference(expandedSuperTypeReference, namespace);
		superReference.setUniParent(null);

		//	result.add(superReference);
		if(toImpl) {
			nonLocal.setMetadata(new TagImpl(), IMPL);
		}
		result.add(nonLocal);
		Set<Subobject> superSubobjects = (Set<Subobject>) relation.overriddenMembers();
		Set<String> doneFQNs = new HashSet<String>();
		for(Subobject superSubobject: superSubobjects) {
			Element origin = superSubobject.origin();
			BasicJavaTypeReference tref;
			String fqn;
			if(origin != superSubobject) {
				Subobject tmp = superSubobject;
				superSubobject = (Subobject) origin; 
				fqn = innerClassFQN(superSubobject);
				tref = language.createTypeReference(fqn);
				copyTypeParametersFromAncestors(superSubobject.componentType(), tref);
				tref.setUniParent(superSubobject);
				substituteTypeParameters(tref);
				tref.setUniParent(null);
			} else {
				fqn = innerClassFQN(superSubobject);
				tref = language.createTypeReference(fqn);
			}
			if(! doneFQNs.contains(fqn)) {
				result.add(language.createNonLocalTypeReference(tref, namespace));
				doneFQNs.add(fqn);
			}
		}
		return result;
	}

	private String innerClassFQN(Subobject relation) throws LookupException {
		return relation.componentType().getFullyQualifiedName();//innerClassName(relation.signature()); 
	}

private void processInnerClassMethod(Subobject relationBeingTranslated, Type result) throws LookupException {
	Type componentType = relationBeingTranslated.referencedComponentType();
	List<Method> localMethods = componentType.directlyDeclaredMembers(Method.class);
	for(Method method: localMethods) {
		if(method.is(method.language(ObjectOrientedLanguage.class).CONSTRUCTOR) == Ternary.TRUE) {
			NormalMethod clone = (NormalMethod) method.clone();
			clone.setUniParent(method.parent());
			for(BasicTypeReference tref: clone.descendants(BasicTypeReference.class)) {
				if(tref.getTarget() == null) {
				  Type element = tref.getElement();
					Type base = element.baseType();
				  if((! (element instanceof InstantiatedParameterType)) && base instanceof RegularType) {
				  	String fqn = base.getFullyQualifiedName();
				  	String qn = Util.getAllButLastPart(fqn);
				  	if(qn != null && (! qn.isEmpty())) {
				  		tref.setTarget(new NameReference<Declaration>(qn, Declaration.class));
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
			useParametersInInvocation(clone, inv,relationBeingTranslated.language());
			block.addStatement(new StatementExpression(inv));
			clone.setReturnTypeReference(relationBeingTranslated.language(Java7.class).createTypeReference(name));
			((SimpleNameMethodHeader)clone.header()).setName(name);
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
private void incorporateImports(Subobject relationBeingTranslated) throws LookupException {
	incorporateImports(relationBeingTranslated, relationBeingTranslated.farthestAncestor(NamespaceDeclaration.class));
}


}
