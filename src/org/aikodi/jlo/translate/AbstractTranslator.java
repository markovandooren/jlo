package org.aikodi.jlo.translate;

import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.QualifiedName;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.namespacedeclaration.Import;
import org.aikodi.chameleon.core.namespacedeclaration.NamespaceDeclaration;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.core.reference.CrossReferenceWithTarget;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.method.exception.ExceptionClause;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.NonLocalTypeReference;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.InstantiatedParameterType;
import org.aikodi.chameleon.oo.type.generics.InstantiatedTypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.component.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.JavaMethodInvocation;
import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.association.Association;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.property.Property;

public class AbstractTranslator {

	public AbstractTranslator() {
	}

	
	public final static String SHADOW = "_subobject_";
	
	public final static String IMPL = "_implementation";

	protected void makePublic(ElementWithModifiers element) throws ModelException {
		Java7 language = element.language(Java7.class);
		if(element.hasProperty(language.SCOPE_MUTEX)) {
			Property access = element.property(language.SCOPE_MUTEX);
			if(access != language.PUBLIC) {
				for(Modifier mod: element.modifiers(language.SCOPE_MUTEX)) {
					mod.disconnect();
				}
				element.addModifier(new Public());
			}
		}
	}

	protected void expandReferences(Element type) throws LookupException {
		Java7 language = type.language(Java7.class);
		for(BasicJavaTypeReference tref: type.descendants(BasicJavaTypeReference.class)) {
			if(tref.getTarget() == null) {
				try {
					// Filthy hack, should add meta information to such references, and use that instead.
					String name = tref.name();
					if(! name.contains(SHADOW)) {
						Type element = null;
						boolean implClass = name.contains(IMPL);
						if(implClass) {
							TypeReference tr = new BasicJavaTypeReference(interfaceName(name));
							tr.setUniParent(tref.parent());
							element = tr.getElement();
						} else {
							element = tref.getElement();
						}
						if(! element.isTrue(language.PRIVATE)) {
							String fullyQualifiedName = element.getFullyQualifiedName();
							String predecessor = Util.getAllButLastPart(fullyQualifiedName);
							if(predecessor != null) {
								CrossReference target = language.plugin(ExpressionFactory.class).createNamedTarget(predecessor);
								tref.setTarget(target);
								if(implClass) {
									transformToImplReference(target);
								}
							}
						}
					}
				} catch(LookupException exc) {
					// This occurs because a generated element cannot be resolved in the original model. E.g.
					// an inner class of another class than the one that has been generated.
				}
			}
		}
	}
	
	protected String interfaceName(String name) {
//		if(! name.contains(IMPL)) {
//			throw new IllegalArgumentException();
//		}
		//return name.substring(0, name.length()-IMPL.length());
		return name.replaceAll(IMPL,"");
	}

	/**
	 * Replace all references to type parameters 
	 * @param element
	 * @throws LookupException
	 */
	protected void substituteTypeParameters(Element element) throws LookupException {
		List<TypeReference> crossReferences = 
			element.descendants(TypeReference.class, 
					new AbstractPredicate<TypeReference,LookupException>() {
				public boolean eval(TypeReference object) throws LookupException {
					try {
						return object.getDeclarator() instanceof InstantiatedTypeParameter;
					} catch (LookupException e) {
						e.printStackTrace();
						object.getDeclarator();
						throw e;
					}
				}
			});
		for(TypeReference cref: crossReferences) {
				SingleAssociation parentLink = cref.parentLink();
				Association childLink = parentLink.getOtherRelation();
				InstantiatedTypeParameter declarator = (InstantiatedTypeParameter) cref.getDeclarator(); 
				Type type = cref.getElement();
				while(type instanceof InstantiatedParameterType) {
					type = ((InstantiatedParameterType)type).aliasedType();
				}
//				TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type.getFullyQualifiedName());
				TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type);
				childLink.replace(parentLink, namedTargetExpression.parentLink());
			}
	}

	protected BasicJavaTypeReference innerClassTypeReference(Subobject relation) throws LookupException {
		return relation.language(Java7.class).createTypeReference(innerClassName(relation));
	}

	protected String innerClassName(Subobject relation) throws LookupException {
		Type t = relation.nearestAncestor(Type.class);
		if(splitClass(t)) {
			return innerClassName(relation.signature());
		} else {
			return relation.name();
		}
	}
	
	protected void transformToImplReference(CrossReference<?> tref) {
		if(tref instanceof NonLocalTypeReference) {
			transformToImplReference(((NonLocalTypeReference)tref).actualReference());
		} else if(tref instanceof CrossReferenceWithName) {
			CrossReferenceWithName ref = (CrossReferenceWithName) tref;
			if(ref instanceof CrossReferenceWithTarget) {
				Element target = ((CrossReferenceWithTarget)ref).getTarget();
				if(target instanceof CrossReference) {
					transformToImplReference((CrossReference<?>) target);
				}
			}
			if(! (ref instanceof MethodInvocation)) {

				boolean change;
				Declaration referencedElement = null;
				Java7 lang = tref.language(Java7.class);
				try {
					referencedElement = ref.getElement();
					if(referencedElement instanceof Type && isJLo(referencedElement) && (! referencedElement.isTrue(lang.INTERFACE))) {
						change = true;
					} else {
						change = false;
					}
				} catch(LookupException exc) {
					change = true;
				}
				if(change) {
					String name = ref.name();
					if(! name.endsWith(IMPL)) {
						ref.setName(name+IMPL);
					}
					if(referencedElement instanceof Type && ref instanceof CrossReferenceWithTarget) {
						BasicJavaTypeReference newRef = lang.createTypeReference((Type)referencedElement);
						CrossReferenceTarget cref = newRef.getTarget();
						if(((CrossReferenceWithTarget)ref).getTarget() == null) {
							((CrossReferenceWithTarget)ref).setTarget(cref);
							transformToImplReference((CrossReference<?>) cref);
						}
					}
				}
			}
		}
	}
	
	private String innerClassName(QualifiedName qn) {
		StringBuffer result = new StringBuffer();
		List<Signature> sigs = qn.signatures();
		int size = sigs.size();
		for(int i = 0; i < size; i++) {
			result.append(((SimpleNameSignature)sigs.get(i)).name());
			if(i < size - 1) {
				result.append(SHADOW);
			}
		}
		result.append(IMPL);
		return result.toString();
	}
	
	protected boolean isLocallyDefined(Subobject relation,Type type) throws LookupException {
		return relation.ancestors().contains(type);
	}

	protected String setterName(Subobject relation) {
		return "set"+COMPONENT+"__"+relation.name();
	}
	
	public final static String COMPONENT = "__component__lkjkberfuncye__";
	
	protected String toUnderScore(String string) {
		return string.replace('.', '_');
	}


	
	/**
	 * Incorporate the imports of the namespace part of the declared type of the component relation to
	 * the given namespace part.
	 * @param relationBeingTranslated
	 * @throws LookupException
	 */
	protected void incorporateImports(Subobject relationBeingTranslated, NamespaceDeclaration target)
	throws LookupException {
		Type baseT = relationBeingTranslated.referencedComponentType().baseType();
		NamespaceDeclaration originalNsp = baseT.farthestAncestor(NamespaceDeclaration.class);
		for(Import imp: originalNsp.imports()) {
			target.addImport(Util.clone(imp));
		}
	}

	protected void substituteTypeParameters(Method methodInTypeWhoseParametersMustBeSubstituted, NormalMethod methodWhereActualTypeParametersMustBeFilledIn) throws LookupException {
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(methodInTypeWhoseParametersMustBeSubstituted);
		substituteTypeParameters(methodWhereActualTypeParametersMustBeFilledIn);
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(null);
	}

	protected void useParametersInInvocation(Method method, MethodInvocation invocation, Language language) {
		ExpressionFactory expressionFactory = language.plugin(ExpressionFactory.class);
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(expressionFactory.createNameExpression(param.name(), null));
		}
	}
	
	protected String staticMethodName(String methodName,Type containerOfToBebound) {
		String tmp = containerOfToBebound.getFullyQualifiedName().replace('.', '_')+"_"+methodName;
		return stripImpl(tmp);
	}
	
	protected String staticMethodName(Method clone,Type containerOfToBebound) {
		String result = staticMethodName(clone.name(), containerOfToBebound);
		return result;
	}
	
	private String stripImpl(String string) {
		return string.replaceAll(IMPL, "");
	}
	
	protected String getterName(Subobject relation) {
		return getterName(relation.name());
	}
	
	public String getterName(String componentName) {
		return componentName+COMPONENT;
	}
	
	@SuppressWarnings("rawtypes")
	protected MethodInvocation invocation(Method method, String origin, Language language) {
		MethodInvocation invocation = new JavaMethodInvocation(origin, null);
		// pass parameters.
		useParametersInInvocation(method, invocation, language);
		return invocation;
	}

	protected void addImplementation(Method method, Block body, MethodInvocation invocation) throws LookupException {
		View view = method.view();
		if(method.returnType().equals(view.language(Java7.class).voidType(view.namespace()))) {
			body.addStatement(new StatementExpression(invocation));
		} else {
			body.addStatement(new ReturnStatement(invocation));
		}
	}

	protected NormalMethod innerMethod(Method method, String original) throws LookupException {
		NormalMethod result;
		result = method.language(Java7.class).plugin(ObjectOrientedFactory.class).createNormalMethod((MethodHeader)method.header().clone());
		((SimpleNameMethodHeader)result.header()).setName(original);
		ExceptionClause exceptionClause = method.getExceptionClause();
		ExceptionClause clone = (exceptionClause != null ? Util.clone(exceptionClause): null);
		result.setExceptionClause(clone);
		result.addModifier(new Public());
		return result;
	}

	protected void copyTypeParametersFromAncestors(Element type, BasicJavaTypeReference createTypeReference) {
		Type ancestor = type.nearestAncestorOrSelf(Type.class);
		Java7 language = type.language(Java7.class);
		while(ancestor != null) {
			List<TypeParameter> tpars = ancestor.parameters(TypeParameter.class);
			for(TypeParameter parameter:tpars) {
				createTypeReference.addArgument(language.createEqualityTypeArgument(language.createTypeReference(parameter.name())));
			}
			if(type.isTrue(language.CLASS)) {
				ancestor = null;
			} else {
			  ancestor = ancestor.nearestAncestor(Type.class);
			}
		}
	}

	protected String toImplName(String name) {
		if(! name.endsWith(IMPL)) {
			name = name + IMPL;
		}
		return name;
	}

	protected boolean splitClass(Type type) throws LookupException {
		Java7 lang = type.language(Java7.class);
		return isJLo(type) && ! lang.isException(type)  && ! inheritsFromNonObjectJavaClass(type);
	}
	
	protected boolean inheritsFromNonObjectJavaClass(Type type) throws LookupException {
		Set<Type> types = type.getAllSuperTypes();
		boolean result = false;
		ObjectOrientedLanguage lang = type.language(ObjectOrientedLanguage.class);
		Type object = lang.findType("java.lang.Object", type.view().namespace());
		for(Type superType: types) {
			if(superType != object && !isJLo(superType) && ! superType.isTrue(lang.INTERFACE)) {
				result = true;
			}
		}
		return result;
	}

	protected boolean isJLo(Element element) {
		String fullyQualifiedName = element.nearestAncestor(NamespaceDeclaration.class).namespace().fullyQualifiedName();
		return (! fullyQualifiedName.startsWith("java.")) &&
		       (! fullyQualifiedName.startsWith("javax.")) &&
		       (! fullyQualifiedName.equals("org.ietf.jgss")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA_2_3")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA_2_3.portable")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA.DynAnyPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA.ORBPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA.Portable")) &&
		       (! fullyQualifiedName.equals("org.omg.CORBA.TypeCodePackage")) &&
		       (! fullyQualifiedName.equals("org.omg.CosNaming")) &&
		       (! fullyQualifiedName.equals("org.omg.CosNaming.NamingContextExtPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.CosNaming.NamingContextPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.Dynamic")) &&
		       (! fullyQualifiedName.equals("org.omg.DynamicAny")) &&
		       (! fullyQualifiedName.equals("org.omg.DynamicAny.DynAnyFactoryPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.DynamicAny.DynAnyPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.IOP")) &&
		       (! fullyQualifiedName.equals("org.omg.IOP.CodecFactoryPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.IOP.CodecPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.Messaging")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableInterceptor")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableInterceptor.ORBInitInfoPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer.CurrentPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer.PAOManagerPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer.PAOPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer.portable")) &&
		       (! fullyQualifiedName.equals("org.omg.PortableServer.ServantLocatorPackage")) &&
		       (! fullyQualifiedName.equals("org.omg.SendingContext")) &&
		       (! fullyQualifiedName.equals("org.omg.stub.java.rmi")) &&
		       (! fullyQualifiedName.equals("org.w3c.dom")) &&
		       (! fullyQualifiedName.equals("org.w3c.dom.bootstrap")) &&
		       (! fullyQualifiedName.equals("org.w3c.dom.events")) &&
		       (! fullyQualifiedName.equals("org.w3c.dom.ls")) &&
		       (! fullyQualifiedName.equals("org.xml.sax")) &&
		       (! fullyQualifiedName.equals("org.xml.sax.ext")) &&
		       (! fullyQualifiedName.equals("org.xml.sax.helpers"));
	}

}
