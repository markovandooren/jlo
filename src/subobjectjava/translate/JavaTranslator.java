package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jnome.core.expression.ConstructorInvocation;
import jnome.core.language.Java;
import jnome.core.type.JavaTypeReference;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;
import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.input.SubobjectJavaModelFactory;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ConfigurationBlock;
import subobjectjava.model.component.ConfigurationClause;
import subobjectjava.model.component.OverridesClause;
import subobjectjava.model.expression.SubobjectConstructorCall;
import subobjectjava.model.language.SubobjectJava;
import subobjectjava.model.language.SubobjectJavaOverridesRelation;
import chameleon.core.Config;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.declaration.CompositeQualifiedName;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.expression.ActualArgument;
import chameleon.core.expression.Invocation;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.method.RegularMethod;
import chameleon.core.method.exception.ExceptionClause;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespace.Namespace;
import chameleon.core.namespace.RootNamespace;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.reference.SimpleReference;
import chameleon.core.statement.Block;
import chameleon.core.type.RegularType;
import chameleon.core.type.Type;
import chameleon.core.type.TypeReference;
import chameleon.core.type.generics.InstantiatedTypeParameter;
import chameleon.core.type.generics.TypeParameter;
import chameleon.core.type.inheritance.SubtypeRelation;
import chameleon.core.variable.FormalParameter;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ParseException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.SuperConstructorDelegation;
import chameleon.support.expression.SuperTarget;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.SimpleNameMethodHeader;
import chameleon.support.member.simplename.SimpleNameMethodSignature;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.method.RegularMethodInvocation;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.modifier.Protected;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.support.variable.VariableDeclaration;
import chameleon.test.provider.BasicDescendantProvider;
import chameleon.test.provider.ElementProvider;

public class JavaTranslator {

	public JavaTranslator(SubobjectJava language, ElementProvider<Namespace> namespaceProvider) throws ParseException, IOException {
		super();
		_language = language;
		_typeProvider = new BasicDescendantProvider<Type>(namespaceProvider, Type.class);
	}
	
	public ElementProvider<Type> typeProvider() {
		return _typeProvider;
	}

	private ElementProvider<Type> _typeProvider;

	public Java translate() throws ParseException, IOException, ChameleonProgrammerException, LookupException {
		RootNamespace clone = language().defaultNamespace().clone();
		Java result = new Java();
		result.cloneConnectorsFrom(language());
		result.cloneProcessorsFrom(language());
		result.setDefaultNamespace(clone);
		Map<Type,Type> map = new HashMap<Type,Type>();
		for(Type type: typeProvider().elements(result)) {
			Type newType = translation(type);
			map.put(newType, type);
		}
		
		for(Entry<Type,Type> entry : map.entrySet()) {
			SingleAssociation newParentlink = entry.getKey().parentLink();
			SingleAssociation oldParentlink = entry.getValue().parentLink();
			Association childLink = oldParentlink.getOtherRelation();
			childLink.replace(oldParentlink, newParentlink);
		}
		return result;
	}
	
	public Language language() throws ParseException, IOException {
		return _language;
	}
	
	private Language _language;
	
 /*@
   @ public behavior
   @
   @ pre compilationUnit != null;
   @
   @ post \result != null;
   @ post \fresh(\result);
   @*/
	public CompilationUnit translation(CompilationUnit compilationUnit) {
		CompilationUnit clone = compilationUnit.clone();
	}
	
	
	/**
	 * Return a type that represents the translation of the given JLow class to a Java class.
	 */
	public Type translation(Type original) throws ChameleonProgrammerException, LookupException {
		Type type = original.clone();
		List<ComponentRelation> relations = original.directlyDeclaredMembers(ComponentRelation.class);
		for(ComponentRelation relation : relations) {
      //ensureTranslation(relation.componentType());
			// Add a field subobject
			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation,type);
			if(fieldForComponent != null) {
			  type.add(fieldForComponent);
			}
			
			// Add a getter for subobject
			Method getterForComponent = getterForComponent(relation,type);
			if(getterForComponent != null) {
				type.add(getterForComponent);
			}

			// Add a setter for subobject
			Method setterForComponent = setterForComponent(relation,type);
			if(setterForComponent != null) {
				type.add(setterForComponent);
			}
			
			type.addAll(aliasMethods(relation));
			
			// Create the inner classes for the components
			inner(type, relation, type);

  		addOutwardDelegations(relation, type);
  		
  		// Replace constructor calls

		}
		for(ComponentRelation relation: type.directlyDeclaredMembers(ComponentRelation.class)) {
			type.setUniParent(original.parent());
			replaceSuperCalls(relation, type);
			replaceConstructorCalls(relation);
			type.setUniParent(null);
			relation.disconnect();
		}
		return type;
	}
	
	public void replaceConstructorCalls(final ComponentRelation relation) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		List<SubobjectConstructorCall> constructorCalls = type.descendants(SubobjectConstructorCall.class, new UnsafePredicate<SubobjectConstructorCall,LookupException>() {
			@Override
			public boolean eval(SubobjectConstructorCall constructorCall) throws LookupException {
				return constructorCall.getTarget().getElement().equals(relation);
			}
		}
		);
		for(SubobjectConstructorCall call: constructorCalls) {
			Invocation inv = new ConstructorInvocation(innerClassTypeReference(relation, type), null);
			// move actual arguments from subobject constructor call to new constructor call. 
			inv.addAllArguments(call.actualArgumentList().getActualParameters());
			Invocation setterCall = new RegularMethodInvocation(setterName(relation), null);
			setterCall.addArgument(new ActualArgument(inv));
			SingleAssociation<SubobjectConstructorCall, Element> parentLink = call.parentLink();
			parentLink.getOtherRelation().replace(parentLink, setterCall.parentLink());
		}
	}

	public void inner(Type type, ComponentRelation relation, Type outer) throws LookupException {
		Type innerClass = createInnerClassFor(relation,type);
		//_innerClassMap.put(relation, type);
		type.add(innerClass);
		Type componentType = relation.componentType();
		for(ComponentRelation nestedRelation: componentType.members(ComponentRelation.class)) {
			// subst parameters
			ComponentRelation clonedNestedRelation = nestedRelation.clone();
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			substituteTypeParameters(clonedNestedRelation, componentType);
			inner(innerClass, clonedNestedRelation, outer);
		}
	}
	
	private Map<ComponentRelation, Type> _innerClassMap = new HashMap<ComponentRelation, Type>();
	
	public void addOutwardDelegations(ComponentRelation relation, Type outer) throws LookupException {
		ConfigurationBlock block = relation.configurationBlock();
		for(ConfigurationClause clause: block.clauses()) {
			if(clause instanceof OverridesClause) {
				OverridesClause ov = (OverridesClause)clause;
				QualifiedName qn = ov.oldFqn();
				QualifiedName poppedName = qn.popped();
				int size = poppedName.length();
				Element container = relation.componentType();
				if(size > 0) {
					SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
					ref.setUniParent(relation.componentType());
				  container = ref.getElement();
				}
				Signature lastSignature = qn.lastSignature();
				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
				Type targetInnerClass = targetInnerClass(outer, relation, poppedName);
				ref.setUniParent(container);
				Declaration decl = ref.getElement();
				if(decl instanceof Method) {
					Method<?,?,?,?> method = (Method<?, ?, ?, ?>) decl;
				  Method original = createOriginal(method, original(method.name()));
				  if(original != null) {
				  	targetInnerClass.add(original);
				  }
				  Method outward = createOutward(method,((SimpleNameMethodSignature)ov.newSignature()).name(),relation);
				  if(outward != null) {
				  	targetInnerClass.add(outward);
				  }
				}
			}
		}
	}

	
	public Type targetInnerClass(Type outer, ComponentRelation relation, QualifiedName poppedName) throws LookupException {
		List<Signature> sigs = new ArrayList<Signature>();
		sigs.add(relation.signature());
		sigs.addAll(poppedName.signatures());
		CompositeQualifiedName innerName = new CompositeQualifiedName();
		CompositeQualifiedName acc = new CompositeQualifiedName();
//		innerName.append(outer.signature().clone());
		for(Signature signature: sigs) {
			acc.append(signature.clone());
		  innerName.append(new SimpleNameSignature(innerClassName(outer, acc)));
		}
		SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
		tref.setUniParent(outer);
		outer.setUniParent(relation.nearestAncestor(Type.class).parent());
		Type result = tref.getElement();
		outer.setUniParent(null);
		return result;
	}

	/**
	 * 
	 * @param relation A component relation from either the original class, or one of its nested components.
	 * @param outer The outer class being generated.
	 */
	public Type createInnerClassFor(ComponentRelation relation, Type outer) throws ChameleonProgrammerException, LookupException {
		NamespacePart nsp = relation.furthestAncestor(NamespacePart.class);
//		Type parentType = relation.nearestAncestor(Type.class);
		RegularType componentType = (RegularType) relation.componentType();
		NamespacePart originalNsp = componentType.furthestAncestor(NamespacePart.class);
		for(Import imp: originalNsp.imports()) {
			nsp.addImport(imp.clone());
		}
		Type stub = new RegularType(innerClassName(relation, outer));
		
		TypeReference superReference;
		if(relation.nearestAncestor(Type.class).signature().equals(outer.signature()) && (outer.nearestAncestor(Type.class) == null)) {
		  superReference = relation.componentTypeReference().clone();
		} else {
		   String innerClassName = innerClassName(relation, relation.nearestAncestor(Type.class));
		  superReference = new JavaTypeReference(innerClassName);
		 }
		stub.addInheritanceRelation(new SubtypeRelation(superReference));
		List<Method> localMethods = componentType.directlyDeclaredMembers(Method.class);
		for(Method<?,?,?,?> method: localMethods) {
			if(method.is(method.language(ObjectOrientedLanguage.class).CONSTRUCTOR) == Ternary.TRUE) {
				NormalMethod clone = (NormalMethod) method.clone();
				String name = stub.signature().name();
				RegularImplementation impl = (RegularImplementation) clone.implementation();
				Block block = new Block();
				impl.setBody(block);
				// substitute parameters before replace the return type, method name, and the body.
				// the types are not known in the component type, and the super class of the component type
				// may not have a constructor with the same signature as the current constructor.
				substituteTypeParameters(method, clone);
				Invocation inv = new SuperConstructorDelegation();
				useParametersInInvocation(clone, inv);
				block.addStatement(new StatementExpression(inv));
				clone.setReturnTypeReference(new JavaTypeReference(name));
				((SimpleNameMethodHeader)clone.header()).setName(name);
				stub.add(clone);
			}
		}
		return stub;
	}
	public final static String SHADOW = "_subobject_";
	
	public Method createOutward(Method<?,?,?,?> method, String newName, ComponentRelation relation) throws LookupException {
		NormalMethod<?,?,?> result;
		if((method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
				(method.is(method.language(ObjectOrientedLanguage.class).OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, method.name());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			Invocation invocation = invocation(result, newName);
			TypeReference ref = getRelativeClassName(relation);
			ThisLiteral target = new ThisLiteral(ref);
			invocation.setTarget(target);
			substituteTypeParameters(method, result);
			addImplementation(method, body, invocation);
		} else {
			result = null;
		}
		return result;
	}
	
	public TypeReference getRelativeClassName(ComponentRelation relation) {
		return new JavaTypeReference(relation.nearestAncestor(Type.class).signature().name());
	}
	
	public Method createOriginal(Method<?,?,?,?> method, String original) throws LookupException {
		NormalMethod<?,?,?> result;
		if((method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
				(method.is(method.language(ObjectOrientedLanguage.class).OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, original);
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			Invocation invocation = invocation(result, method.name());
			invocation.setTarget(new SuperTarget());
			addImplementation(method, body, invocation);
			substituteTypeParameters(method, result);
		}
		else {
			result = null;
		}
		return result;
	}

	private void substituteTypeParameters(Method<?, ?, ?, ?> methodInTypeWhoseParametersMustBeSubstituted, NormalMethod<?, ?, ?> methodWhereActualTypeParametersMustBeFilledIn) throws LookupException {
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(methodInTypeWhoseParametersMustBeSubstituted);
		Type type = methodInTypeWhoseParametersMustBeSubstituted.nearestAncestor(Type.class);
		substituteTypeParameters(methodWhereActualTypeParametersMustBeFilledIn, type);
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(null);
	}

	private void addImplementation(Method<?, ?, ?, ?> method, Block body, Invocation invocation) throws LookupException {
		if(method.returnType().equals(method.language(Java.class).voidType())) {
			body.addStatement(new StatementExpression(invocation));
		} else {
			body.addStatement(new ReturnStatement(invocation));
		}
	}

	private NormalMethod<?, ?, ?> innerMethod(Method<?, ?, ?, ?> method, String original) {
		NormalMethod<?, ?, ?> result;
		result = new NormalMethod(method.header().clone(), method.getReturnTypeReference().clone());
		((SimpleNameMethodHeader)result.header()).setName(original);
		ExceptionClause exceptionClause = method.getExceptionClause();
		ExceptionClause clone = (exceptionClause != null ? exceptionClause.clone(): null);
		result.setExceptionClause(clone);
		result.addModifier(new Public());
		return result;
	}

	public void substituteTypeParameters(Element<?, ?> result, Type type) throws LookupException {
		List<TypeParameter> typeParameters = type.parameters();
		for(TypeParameter par: typeParameters) {
			if(par instanceof InstantiatedTypeParameter) {
				((InstantiatedTypeParameter)par).substitute(result);
			}
		}
	}
	
	public String innerClassName(Type outer, QualifiedName qn) {
		StringBuffer result = new StringBuffer();
		result.append(outer.signature().name());
		result.append(SHADOW);
		List<Signature> sigs = qn.signatures();
		int size = sigs.size();
		for(int i = 0; i < size; i++) {
			result.append(((SimpleNameSignature)sigs.get(i)).name());
			if(i < size - 1) {
				result.append(SHADOW);
			}
		}
		return result.toString();
	}
	
	public String innerClassName(ComponentRelation relation, Type outer) throws LookupException {
		return innerClassName(outer, relation.signature()); 
	}
	
	public void replaceSuperCalls(final ComponentRelation relation, Type parent) throws LookupException {
		List<SuperTarget> superTargets = parent.descendants(SuperTarget.class, new UnsafePredicate<SuperTarget,LookupException>() {

			@Override
			public boolean eval(SuperTarget superTarget) throws LookupException {
				return superTarget.getTargetDeclaration().equals(relation);
			}
			
		}
		);
		for(SuperTarget superTarget: superTargets) {
			Element<?,?> inv = superTarget.parent();
			if(inv instanceof RegularMethodInvocation) {
				RegularMethodInvocation call = (RegularMethodInvocation) inv;
			  Invocation subObjectSelection = new RegularMethodInvocation(getterName(relation), null);
			  call.setTarget(subObjectSelection);
			  call.setName(original(call.name()));
			}
      
		}
	}
	
	public String original(String name) {
		return "original__"+name;
	}
	
	public MemberVariableDeclarator fieldForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(! overrides(relation)) {
//		MemberVariableDeclarator result = new MemberVariableDeclarator(relation.componentTypeReference().clone());
			MemberVariableDeclarator result = new MemberVariableDeclarator(innerClassTypeReference(relation, outer));
		result.add(new VariableDeclaration(fieldName(relation)));
		return result;
		} else {
			return null;
		}
	}

	private JavaTypeReference innerClassTypeReference(ComponentRelation relation, Type outer) throws LookupException {
		return new JavaTypeReference(innerClassName(relation, outer));
	}
	
	public String getterName(ComponentRelation relation) {
		return relation.signature().name()+COMPONENT;
	}
	
	public final static String COMPONENT = "__component__lkjkberfuncye__";
	
	public Method getterForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(! overrides(relation)) {
			RegularMethod result = new NormalMethod(new SimpleNameMethodHeader(getterName(relation)), innerClassTypeReference(relation, outer));
			result.addModifier(new Public());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			body.addStatement(new ReturnStatement(new NamedTargetExpression(fieldName(relation), null)));
			return result;
		} else {
			return null;
		}
	}
	
	public String setterName(ComponentRelation relation) {
		return "set"+COMPONENT+"__"+relation.signature().name();
	}
	
	public Method setterForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(! overrides(relation)) {
		String name = relation.signature().name();
		RegularMethod result = new NormalMethod(new SimpleNameMethodHeader(setterName(relation)), new JavaTypeReference("void"));
		result.header().addParameter(new FormalParameter(name, innerClassTypeReference(relation,outer)));
		result.addModifier(new Protected());
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		NamedTargetExpression componentFieldRef = new NamedTargetExpression(fieldName(relation), null);
		body.addStatement(new StatementExpression(new AssignmentExpression(componentFieldRef, new NamedTargetExpression(name, null))));
		return result;
		} else {
			return null;
		}
	}
	
	private boolean overrides(ComponentRelation relation) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		for(Type superType: type.getDirectSuperTypes()) {
			List<ComponentRelation> superComponents = superType.members(ComponentRelation.class);
			for(ComponentRelation superComponent: superComponents) {
				if(new SubobjectJavaOverridesRelation().contains(relation, superComponent)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Method> aliasMethods(ComponentRelation relation) throws LookupException {
		List<Method> result = new ArrayList<Method>();
		List<? extends Member> members = relation.getIntroducedMembers();
		members.remove(relation);
		for(Member member: members) {
			result.add(aliasFor(member, relation));
		}
		return result;
	
	}
	public Method aliasFor(Member member, ComponentRelation relation) throws LookupException{
		if(member instanceof Method) {
			Method<?,?,?,?> method = (Method) member;
			Method<?,?,?,?> origin = (Method) method.origin();
			String methodName = fieldName(relation);
			Method result = new NormalMethod(method.header().clone(), new JavaTypeReference(method.returnType().getFullyQualifiedName()));
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			Invocation invocation = invocation(method, origin.name());
			invocation.setTarget(new NamedTargetExpression(methodName, null));
			if(origin.returnType().equals(origin.language(ObjectOrientedLanguage.class).voidType())) {
				body.addStatement(new StatementExpression(invocation));
			} else {
				body.addStatement(new ReturnStatement(invocation));
			}
			for(Modifier mod: origin.modifiers()) {
				result.addModifier(mod.clone());
			}
			return result;
		} else {
			throw new ChameleonProgrammerException("Translation of member of type "+member.getClass().getName()+" not supported.");
		}
	}

	private Invocation invocation(Method<?, ?, ?, ?> method, String origin) {
		Invocation invocation = new RegularMethodInvocation(origin, null);
		// pass parameters.
		useParametersInInvocation(method, invocation);
		return invocation;
	}

	private void useParametersInInvocation(Method<?, ?, ?, ?> method, Invocation invocation) {
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(new ActualArgument(new NamedTargetExpression(param.signature().name(), null)));
		}
	}
	
	public String fieldName(ComponentRelation relation) {
		return "__component_" + relation.signature().name();
	}
	
	
  /**
   * args[0] = path for the directory to write output
   * args[1] = path to read input files
   * ...1 or more input paths possible...
   * args[i] = fqn of package to read, let this start with "@" to read the package recursively
   *...1 or more packageFqns possible...
   * args[n] = fqn of package to read, let this start with "#" to NOT read the package recursively.
   *...1 or more packageFqns possible...
   *
   * Example 
   * java Copy outputDir baseInputDir customInputDir1 customInputDir2 @myPackage.subPackage 
   */
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java .... JavaTranslator outputDir inputDir* @recursivePackageFQN* #packageFQN* $typeFQN*");
    }
    BasicConfigurator.configure();
    Logger.getRootLogger().setLevel(Level.FATAL);
    Config.setCacheLanguage(true);
    Config.setCacheElementReferences(true);
    Config.setCacheElementProperties(true);
    ProviderProvider provider = new ProviderProvider(new SubobjectJavaModelFactory(),".java",true,true);
    provider.processArguments(args);
    long start = System.currentTimeMillis();
    Java result = new JavaTranslator((SubobjectJava) provider.language(), provider.namespaceProvider()).translate();
    // Output
    long stop = System.currentTimeMillis();
    File outputDir = provider.outputDir();
    TypeWriter writer = new TypeWriter(result, new BasicDescendantProvider<Type>(provider.namespaceProvider(), Type.class),outputDir);
    writer.write();
    System.out.println("Translation took "+(stop - start) + " milliseconds.");
  }
}
