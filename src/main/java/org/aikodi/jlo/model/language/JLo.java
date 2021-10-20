package org.aikodi.jlo.model.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.namespace.Namespace;
import org.aikodi.chameleon.core.namespace.RootNamespace;
import org.aikodi.chameleon.core.property.DynamicChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.core.relation.EquivalenceRelation;
import org.aikodi.chameleon.core.relation.StrictPartialOrder;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.language.LanguageWithBoxing;
import org.aikodi.chameleon.oo.language.LanguageWithErasure;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguageImpl;
import org.aikodi.chameleon.oo.language.SubtypeRelation;
import org.aikodi.chameleon.oo.type.*;
import org.aikodi.chameleon.oo.type.generics.*;
import org.aikodi.chameleon.util.Util;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.java.core.expression.invocation.ExtendsReference;
import org.aikodi.java.core.expression.invocation.JavaSuperReference;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.language.JavaSubtypingRelation;
import org.aikodi.java.core.type.*;
import org.aikodi.jlo.model.type.*;
import org.aikodi.jlo.workspace.JLoView;
import org.aikodi.rejuse.junit.BasicRevision;

public class JLo extends Java7 implements LanguageWithBoxing, LanguageWithErasure {

	public JLo() {
		this(NAME);
	}

	public final static String NAME = "JLo";
	
	protected JLo(String name) {
		super(name, new BasicRevision(0,1,0));
	}

	@Override
	public StrictPartialOrder<Declaration> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}

	@Override
	public EquivalenceRelation<Declaration> equivalenceRelation() {
		return new JLoEquivalenceRelation();
	}

	@Override
	public View createView() {
		return new JLoView(createRootNamespace(), this);
	}

	private final class JLoEquivalenceRelation implements EquivalenceRelation<Declaration> {
		@Override
		public boolean contains(Declaration first, Declaration second) throws LookupException {
			return first.equals(second);
		}
	}


	@Override
	public <P extends Parameter> TypeInstantiation instantiatedType(Class<P> kind, List<P> parameters, Type baseType) {
		return new JLoTypeInstantiation(new FunctionalParameterSubstitution<P>(kind, parameters), baseType);
	}

	@Override
	public TypeInstantiation createDerivedType(Type baseType, List<TypeArgument> typeArguments) throws LookupException {
		return new JLoTypeInstantiation(baseType,typeArguments);
	}

	@Override
	public String getDefaultSuperClassFQN() {
		return "java.lang.Object";
	}

	@Override
	public Type erasure(Type original) {
		return Java7.erasedType(original);
	}

	@Override
	public TypeReference createDirectTypeReference(Type type) {
		return new DirectJavaTypeReference(type);
	}

	@Override
	public BasicJavaTypeReference createTypeReference(String fqn) {
		return new BasicJavaTypeReference(fqn);
	}

	public Type createdCapturedType(ParameterSubstitution<?> parameterSubstitution, Type base) {
		return new JLoCapturedType(parameterSubstitution, base);
	}

	@Override
	public Type booleanType(Namespace ns) throws LookupException {
		return findType("boolean",ns);
	}

	@Override
	public Type classCastException(Namespace ns) throws LookupException {
		return findType("java.lang.ClassCastException",ns);
	}

	/*@
	 @ also public behavior
	 @
 	 @ post \result.equals(getDefaultPackage().findType("java.lang.NullPointerException"));
 	 @*/
	public Type getNullInvocationException(Namespace ns) throws LookupException {
		return findType("java.lang.NullPointerException",ns);
	}

	/*@
	 @ also public behavior
	 @
	 @ post \result.equals(getDefaultPackage().findType("java.lang.RuntimeException"));
	 @*/
	@Override
	public Type getUncheckedException(Namespace ns) throws LookupException {
		return findType("java.lang.RuntimeException",ns);
	}

	public boolean isCheckedException(Type type) throws LookupException{
		Namespace ns = type.view().namespace();
		Type error = findType("java.lang.Error",ns);
		Type runtimeExc = findType("java.lang.RuntimeException",ns);
		return isException(type) && (! type.assignableTo(error)) && (! type.assignableTo(runtimeExc));
	}

	@Override
	public JavaSubtypingRelation subtypeRelation() {
		return new JavaSubtypingRelation<JLo>(this);
	}

	public boolean isException(Type type) throws LookupException {
		return type.assignableTo(findType("java.lang.Throwable",type.view().namespace()));
	}

	@Override
	public Type voidType(Namespace root) throws LookupException {
		return findType("void", root);
	}

	@Override
	public JavaPureWildcard createPureWildcard() {
		return new JavaPureWildcard();
	}

	@Override
	public TypeReference reference(Type type) {
		TypeReference result;
		Namespace rootNamespace = type.view().namespace();
		if(type instanceof NullType) {
			return new DirectJavaTypeReference(type);
		} else if(type instanceof IntersectionType) {
			result = ((IntersectionType)type).reference();
		} else if(type instanceof UnionType) {
			result = ((UnionType)type).reference();
		} else if (type instanceof ArrayType) {
			result = ((ArrayType)type).reference();
		} else if (type instanceof TypeInstantiation){
			result = ((TypeInstantiation)type).reference();
		} else if (type instanceof TypeVariable) {
			result = ((TypeVariable)type).reference();
		} else if (type instanceof InstantiatedParameterType) {
			result = ((InstantiatedParameterType)type).reference();
		} else if (type instanceof AnonymousInnerClass) {
			result = ((AnonymousInnerClass)type).reference();
		} else if (type instanceof RegularType) {
			// for now, if this code is invoked, there are no generic parameters.
			List<TypeParameter> parameters = type.parameters(TypeParameter.class);
			List<TypeArgument> arguments = new ArrayList<>();
			for(TypeParameter tpar: parameters) {
				Element lookupParent = tpar;
				TypeReference nameref = createTypeReference(tpar.name());
				TypeReference tref = createNonLocalTypeReference(nameref, lookupParent);
				arguments.add(createEqualityTypeArgument(tref));
			}
			result = createTypeReference(type.getFullyQualifiedName(), parameters, arguments);
			result.setUniParent(rootNamespace);
		} else if (type instanceof RawType) {
			result = ((RawType)type).reference();
		} else if (type instanceof ExtendsWildcardType) {
			result = ((ExtendsWildcardType)type).reference();
		} else if (type instanceof SuperWildcardType) {
			result = ((SuperWildcardType)type).reference();
		} else if (type instanceof ConstrainedType) {
			result = ((ConstrainedType) type).reference();
		}
		else {
			throw new ChameleonProgrammerException("Type of type is "+type.getClass().getName());
		}
		if(result.lexical().parent() == null) {
			throw new ChameleonProgrammerException();
		}
		return result;
	}

	public Type getNullType(Namespace ns) {
		if(_nullType == null) {
			try {
				_nullType = findType("null type",ns);
			} catch (LookupException e) {
				throw new ChameleonProgrammerException(e);
			}
		}
		return _nullType;
	}

	private Type _nullType;

	protected DynamicChameleonProperty createPrimitiveTypeProperty()
	{
		return new Java7.PrimitiveTypeProperty("primitive", Java7.namesOfPrimitiveTypes());
	}


	public boolean isBoxable(Type type) throws LookupException {
		return type.isTrue(PRIMITIVE_TYPE);
	}

	public Type unbox(Type type) throws LookupException {
		//SPEED this is horrible
		String fqn = type.getFullyQualifiedName();
		String newFqn = _unboxMap.get(fqn);
		if(newFqn == null) {
			throw new LookupException("Type "+fqn+" cannot be converted through unboxing.");
		}
		return findType(newFqn,type.view().namespace());
	}

	public boolean isUnboxable(Type type) {
		String fqn = type.getFullyQualifiedName();
		return _unboxMap.get(fqn) != null;
	}

	public Type boxedType(Type type) throws LookupException {
		String fqn = type.getFullyQualifiedName();
		String boxedFqn = _boxMap.get(fqn);
		if(boxedFqn == null) {
			throw new LookupException("Type "+fqn+" cannot be converted through boxing.");
		}

		return findType(boxedFqn, type.view().namespace());
	}

	private Map<String,String> _boxMap = Java7.boxMap();

	private Map<String,String> _unboxMap = Java7.unboxMap();

	private Set<String> _unboxables = Java7.unboxables();

	public TypeReference box(TypeReference aRef, Namespace root) throws LookupException {
		if (! (aRef instanceof CrossReferenceWithName)) {
			return aRef;
		}
		String newFqn = _boxMap.get(((CrossReferenceWithName)(aRef)).name());
		if(newFqn == null) {
			//throw new LookupException("Type "+fqn+" cannot be converted through boxing.");
			return aRef;
		}
		TypeReference result = createTypeReference(newFqn);
		result.setUniParent(root);
		return result;
	}

	@Override
	public BasicJavaTypeReference createTypeReference(Type type) {
		BasicJavaTypeReference result = createTypeReference(type.getFullyQualifiedName());
		if(! (type instanceof TypeIndirection)) {
			for(TypeParameter par: type.parameters(TypeParameter.class)) {
				if(par instanceof InstantiatedTypeParameter) {
					InstantiatedTypeParameter inst = (InstantiatedTypeParameter) par;
					result.addArgument(Util.clone(inst.argument()));
				}
			}
		}
		return result;
	}

	@Override
	public BasicJavaTypeReference createTypeReference(CrossReference<? extends Declaration> target, String name) {
		return new BasicJavaTypeReference(target, name);
	}

	@Override
	public TypeReference createSuperReference(TypeReference reference) {
		return new JavaSuperReference(reference);
	}

	@Override
	public TypeReference createExtendsReference(TypeReference reference) {
		return new ExtendsReference(reference);
	}


	@Override
	public TypeReference createTypeReference(String fqn, List<TypeParameter> parameters, List<TypeArgument> arguments) {
		BasicJavaTypeReference result = new BasicJavaTypeReference(fqn);
		for (TypeArgument arg : arguments) {
			result.addArgument(arg);
		}
		return result;
	}
}
