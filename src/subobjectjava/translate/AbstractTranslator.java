package subobjectjava.translate;

import java.util.List;

import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;
import org.rejuse.predicate.UnsafePredicate;
import org.rejuse.property.Property;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.Method;
import chameleon.core.method.exception.ExceptionClause;
import chameleon.core.modifier.ElementWithModifiers;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.statement.Block;
import chameleon.core.variable.FormalParameter;
import chameleon.exception.ModelException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.generics.ActualType;
import chameleon.oo.type.generics.InstantiatedTypeParameter;
import chameleon.oo.type.generics.TypeParameter;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;

public class AbstractTranslator {

	public AbstractTranslator() {
	}

	
	public final static String SHADOW = "_subobject_";
	
	public final static String IMPL = "_implementation";

	protected void makePublic(ElementWithModifiers<?> element) throws ModelException {
		Java language = element.language(Java.class);
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
	protected void substituteTypeParameters(Element<?> element) throws LookupException {
		List<TypeReference> crossReferences = 
			element.descendants(TypeReference.class, 
					new UnsafePredicate<TypeReference,LookupException>() {
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
				while(type instanceof ActualType) {
					type = ((ActualType)type).aliasedType();
				}
//				TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type.getFullyQualifiedName());
				TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type);
				childLink.replace(parentLink, namedTargetExpression.parentLink());
			}
	}

	protected BasicJavaTypeReference innerClassTypeReference(ComponentRelation relation) throws LookupException {
		return relation.language(Java.class).createTypeReference(innerClassName(relation));
	}

	protected String innerClassName(ComponentRelation relation) throws LookupException {
		return innerClassName(relation.signature()); 
	}
	
	private String innerClassName(QualifiedName qn) {
		StringBuffer result = new StringBuffer();
		List<Signature> sigs = qn.signatures();
//		sigs.add(0, outer.signature());
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
	
	protected boolean isLocallyDefined(ComponentRelation relation,Type type) throws LookupException {
		return relation.ancestors().contains(type);
	}

	protected String setterName(ComponentRelation relation) {
		return "set"+COMPONENT+"__"+relation.signature().name();
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
	protected void incorporateImports(ComponentRelation relationBeingTranslated, NamespacePart target)
	throws LookupException {
		Type baseT = relationBeingTranslated.referencedComponentType().baseType();
		NamespacePart originalNsp = baseT.farthestAncestor(NamespacePart.class);
		for(Import imp: originalNsp.imports()) {
			target.addImport(imp.clone());
		}
	}

	protected void substituteTypeParameters(Method<?, ?, ?> methodInTypeWhoseParametersMustBeSubstituted, NormalMethod<?, ?, ?> methodWhereActualTypeParametersMustBeFilledIn) throws LookupException {
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(methodInTypeWhoseParametersMustBeSubstituted);
		substituteTypeParameters(methodWhereActualTypeParametersMustBeFilledIn);
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(null);
	}

	protected void useParametersInInvocation(Method<?, ?, ?> method, MethodInvocation invocation) {
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(new NamedTargetExpression(param.signature().name(), null));
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
	
	protected String getterName(ComponentRelation relation) {
		return getterName(relation.signature().name());
	}
	
	protected String getterName(String componentName) {
		return componentName+COMPONENT;
	}
	
	protected MethodInvocation invocation(Method<?, ?, ?> method, String origin) {
		MethodInvocation invocation = new JavaMethodInvocation(origin, null);
		// pass parameters.
		useParametersInInvocation(method, invocation);
		return invocation;
	}

	protected void addImplementation(Method<?, ?, ?> method, Block body, MethodInvocation invocation) throws LookupException {
		if(method.returnType().equals(method.language(Java.class).voidType())) {
			body.addStatement(new StatementExpression(invocation));
		} else {
			body.addStatement(new ReturnStatement(invocation));
		}
	}

	protected NormalMethod<?, ?, ?> innerMethod(Method<?, ?, ?> method, String original) throws LookupException {
		NormalMethod<?, ?, ?> result;
		TypeReference tref = method.returnTypeReference().clone();
		result = method.language(Java.class).createNormalMethod(method.header().clone(), tref);
		((SimpleNameDeclarationWithParametersHeader)result.header()).setName(original);
		ExceptionClause exceptionClause = method.getExceptionClause();
		ExceptionClause clone = (exceptionClause != null ? exceptionClause.clone(): null);
		result.setExceptionClause(clone);
		result.addModifier(new Public());
		return result;
	}

	protected void copyTypeParametersFromAncestors(Element<?> type, BasicJavaTypeReference createTypeReference) {
		Type ancestor = type.nearestAncestorOrSelf(Type.class);
		Java language = type.language(Java.class);
		while(ancestor != null) {
			List<TypeParameter> tpars = ancestor.parameters(TypeParameter.class);
			for(TypeParameter parameter:tpars) {
				createTypeReference.addArgument(language.createBasicTypeArgument(language.createTypeReference(parameter.signature().name())));
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

}