package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import subobjectjava.model.language.SubobjectJava;
import subobjectjava.model.language.SubobjectJavaOverridesRelation;
import chameleon.core.Config;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
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
		for(Type type: typeProvider().elements(result)) {
			ensureTranslation(type);
		}
		for(Type type: typeProvider().elements(result)) {
			for(ComponentRelation relation: type.descendants(ComponentRelation.class)) {
				relation.disconnect();
			}
		}
		return result;
	}
	
	public Language language() throws ParseException, IOException {
		return _language;
	}
	
	private Language _language;
	
	public void ensureTranslation(Type type) throws ChameleonProgrammerException, LookupException {
		if(! _processed.contains(type)) {
			translate(type);
			_processed.add(type);
		}
	}
	
	private Set<Type> _processed = new HashSet<Type>();

	public void translate(Type type) throws ChameleonProgrammerException, LookupException {
		Type backup = type.clone();
		List<ComponentRelation> relations = type.directlyDeclaredMembers(ComponentRelation.class);
		for(ComponentRelation relation : relations) {
      ensureTranslation(relation.componentType());
			// Add a field and a getter for the subobject
			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation);
			if(fieldForComponent != null) {
			  type.add(fieldForComponent);
			}
			Method setterForComponent = setterForComponent(relation);
			if(setterForComponent != null) {
				type.add(setterForComponent);
			}
			List<Method> aliasMethods = aliasMethods(relation);
			type.addAll(aliasMethods);
			// Replace super calls on subobjects with calls to the original methods on the getter
			replaceSuperCalls(relation);
			Type innerClass = createInnerClassFor(relation);
			_innerClassMap.put(relation, type);
			type.add(innerClass);
			addOutwardDelegations(relation, innerClass);
			// Replace component with getter
			SingleAssociation<ComponentRelation, Element> parentLink = relation.parentLink();
			Association<? extends Element, ? super ComponentRelation> childLink = parentLink.getOtherRelation();
			Method getterForComponent = getterForComponent(relation);
			if(getterForComponent != null) {
//				type.add(getterForComponent);
			  childLink.replace(parentLink, getterForComponent.parentLink());
			}
			else {
				relation.disconnect();
			}
		}
	}
	
	private Map<ComponentRelation, Type> _innerClassMap = new HashMap<ComponentRelation, Type>();
	
	public void addOutwardDelegations(ComponentRelation relation, Type innerClass) throws LookupException {
		ConfigurationBlock block = relation.configurationBlock();
		for(ConfigurationClause clause: block.clauses()) {
			if(clause instanceof OverridesClause) {
				OverridesClause ov = (OverridesClause)clause;
				QualifiedName qn = ov.oldFqn();
				QualifiedName poppedName = qn.popped();
				int size = poppedName.length();
				Element container = innerClass;
				if(size > 0) {
					SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
					ref.setUniParent(relation.componentType());
				  container = ref.getElement();
				}
				Signature lastSignature = qn.lastSignature();
				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
				ref.setUniParent(container);
				Declaration decl = ref.getElement();
				if(decl instanceof Method) {
					Method<?,?,?,?> method = (Method<?, ?, ?, ?>) decl;
				  Method original = createOriginal(method, original(method.name()));
				  if(original != null) {
				  	innerClass.add(original);
				  }
				  Method outward = createOutward(method,((SimpleNameMethodSignature)ov.newSignature()).name(),relation);
				  if(outward != null) {
				  	innerClass.add(outward);
				  }
				}
				// oldName(....) {
				//   DirectOuterClass.this.newName();
				// }
			}
		}
	}
	
	public Type translation(Type type) throws ChameleonProgrammerException, LookupException {
		Type result = type.clone();
		result.setUniParent(type.parent());
		translate(result);
		result.setUniParent(null);
		return result;
	}
	
	public Type createInnerClassFor(ComponentRelation relation) throws ChameleonProgrammerException, LookupException {
		NamespacePart nsp = relation.furthestAncestor(NamespacePart.class);
		Type parentType = relation.nearestAncestor(Type.class);
		RegularType componentType = (RegularType) relation.componentType();
		NamespacePart originalNsp = componentType.furthestAncestor(NamespacePart.class);
		for(Import imp: originalNsp.imports()) {
			nsp.addImport(imp.clone());
		}
		// CREATE TYPE
		Type stub = new RegularType(innerClassName(relation));
		// CREATE INHERITANCE RELATION
//		JavaTypeReference superReference = new JavaTypeReference(componentType.getFullyQualifiedName());
		TypeReference superReference = relation.componentTypeReference().clone();
		stub.addInheritanceRelation(new SubtypeRelation(superReference));
		// CREATE AND PASS GENERIC PARAMETERS
//		for(TypeParameter param: componentType.parameters()) {
//		  stub.addParameter(param.clone());
//		  superReference.addArgument(new BasicTypeArgument(new JavaTypeReference(param.signature().name())));
//		}
		// ADD STUB TO PARENT
//		parentType.add(stub);
		return stub;
		
		// ADD ORIGINAL DEFINITION FOR SUPER CALLS
//		for(Member member: stub.members()) {
//			if(member instanceof Method) {
//			  Method method = (Method) member;
//				Method original = createOriginal(method, original(method.name()));
//			  if(original != null) {
//				  stub.add(original);
//			  }
//				Method temporary = createOriginal(method, method.name());
//			  if(temporary != null) {
//				  stub.add(temporary);
//			  }
//			} else if(member instanceof Type) {
//				//TODO modify inheritance relation.
//				Type it = (Type)member;
//				if(it.getName().contains(SHADOW)) {
//					Type clonedInnerType = it.clone();
//					clonedInnerType.removeAllInheritanceRelations();
//					clonedInnerType.addInheritanceRelation(new SubtypeRelation(new JavaTypeReference(it.getFullyQualifiedName())));
//					clonedInnerType.setUniParent(it);
//					substituteTypeParameters(clonedInnerType, it.nearestAncestor(Type.class));
//					clonedInnerType.setUniParent(null);
//					stub.add(clonedInnerType);// hides inner class of actual component type
//				}
//			}
//			// TODO override getter when component is overridden.
//			// TODO Rewrite overridden methods.
//		}
		
	}
	public final static String SHADOW = "_shadow_class_for_";
	
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
		Type type = relation.nearestAncestor(Type.class);
		StringBuffer qn = new StringBuffer();
		while(type != null) {
			qn.append(type.signature().name());
			type = type.nearestAncestor(Type.class);
			if(type != null) {
				qn.append(".");
			}
		}
		return new JavaTypeReference(qn.toString());
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
//		  List<CrossReference> crossReferences = 
//		      result.descendants(CrossReference.class, 
//				              new UnsafePredicate<CrossReference,LookupException>() {
//
//												@Override
//												public boolean eval(CrossReference object) throws LookupException {
//													return object.getElement().equals(selectionDeclaration());
//												}
//			 
//		 });

		}
		else {
			result = null;
		}
		return result;
	}

	private void substituteTypeParameters(Method<?, ?, ?, ?> method, NormalMethod<?, ?, ?> result) throws LookupException {
		result.setUniParent(method);
		Type type = method.nearestAncestor(Type.class);
		substituteTypeParameters(result, type);
		result.setUniParent(null);
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
	
	public String innerClassName(ComponentRelation relation) throws LookupException {
		return relation.componentType().baseType().signature().name()+SHADOW+relation.signature().name();
	}
	
	public void replaceSuperCalls(final ComponentRelation relation) throws LookupException {
		Type parent = relation.nearestAncestor(Type.class);
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
	
	public MemberVariableDeclarator fieldForComponent(ComponentRelation relation) throws LookupException {
		if(! overrides(relation)) {
//		MemberVariableDeclarator result = new MemberVariableDeclarator(relation.componentTypeReference().clone());
			MemberVariableDeclarator result = new MemberVariableDeclarator(innerClassTypeReference(relation));
		result.add(new VariableDeclaration(fieldName(relation)));
		return result;
		} else {
			return null;
		}
	}

	private JavaTypeReference innerClassTypeReference(ComponentRelation relation) throws LookupException {
		return new JavaTypeReference(innerClassName(relation));
	}
	
	public String getterName(ComponentRelation relation) {
		return relation.signature().name()+COMPONENT;
	}
	
	public final static String COMPONENT = "__component__lkjkberfuncye__";
	
	public Method getterForComponent(ComponentRelation relation) throws LookupException {
		if(! overrides(relation)) {
			RegularMethod result = new NormalMethod(new SimpleNameMethodHeader(getterName(relation)), innerClassTypeReference(relation));
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
	
	public Method setterForComponent(ComponentRelation relation) throws LookupException {
		if(! overrides(relation)) {
		String name = relation.signature().name();
		RegularMethod result = new NormalMethod(new SimpleNameMethodHeader(setterName(relation)), new JavaTypeReference("void"));
		result.header().addParameter(new FormalParameter(name, innerClassTypeReference(relation)));
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
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(new ActualArgument(new NamedTargetExpression(param.signature().name(), null)));
		}
		return invocation;
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
