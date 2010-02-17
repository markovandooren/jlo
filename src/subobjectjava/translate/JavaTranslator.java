package subobjectjava.translate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import subobjectjava.model.language.SubobjectJava;
import subobjectjava.model.language.SubobjectJavaOverridesRelation;
import chameleon.core.Config;
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
import chameleon.support.member.simplename.SimpleNameMethodHeader;
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
		List<ComponentRelation> relations = type.directlyDeclaredMembers(ComponentRelation.class);
		for(ComponentRelation relation : relations) {
			// Add a field and a getter for the subobject
			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation);
			if(fieldForComponent != null) {
			  type.add(fieldForComponent);
			}
			Method setterForComponent = setterForComponent(relation);
			if(setterForComponent != null) {
				type.add(setterForComponent);
			}
			type.addAll(aliasMethods(relation));
			// Replace super calls on subobjects with calls to the original methods on the getter
			replaceSuperCalls(relation);
			createInnerClassFor(relation);
			// Replace component with getter
			SingleAssociation<ComponentRelation, Element> parentLink = relation.parentLink();
			Association<? extends Element, ? super ComponentRelation> childLink = parentLink.getOtherRelation();
			Method getterForComponent = getterForComponent(relation);
			if(getterForComponent != null) {
			  childLink.replace(parentLink, getterForComponent.parentLink());
			} else {
				relation.disconnect();
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
	
	public void createInnerClassFor(ComponentRelation relation) throws ChameleonProgrammerException, LookupException {
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
		parentType.add(stub);
		// ADD ORIGINAL DEFINITION FOR SUPER CALLS
		ensureTranslation(componentType);
		for(Member member: stub.members()) {
			if(member instanceof Method) {
			  Method original = createOriginal((Method) member);
			  if(original != null) {
				  stub.add(original);
			  }
			}
		}
		
//		Type clone = translation(componentType);
//		clone.addModifier();
	}
	
	public Method createOriginal(Method<?,?,?,?> method) throws LookupException {
		NormalMethod<?,?,?> result;
		if(method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) {
			result = new NormalMethod(method.header().clone(), method.getReturnTypeReference().clone());
			((SimpleNameMethodHeader)result.header()).setName(original(method.name()));
			ExceptionClause exceptionClause = method.getExceptionClause();
			ExceptionClause clone = (exceptionClause != null ? exceptionClause.clone(): null);
			result.setExceptionClause(clone);
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			Invocation invocation = invocation(result, method);
			invocation.setTarget(new SuperTarget());
			if(method.returnType().equals(method.language(Java.class).voidType())) {
				body.addStatement(new StatementExpression(invocation));
			} else {
				body.addStatement(new ReturnStatement(invocation));
			}
			result.addModifier(new Public());
			result.setUniParent(method);
			Type type = method.nearestAncestor(Type.class);
			List<TypeParameter> typeParameters = type.parameters();
			for(TypeParameter par: typeParameters) {
				if(par instanceof InstantiatedTypeParameter) {
					((InstantiatedTypeParameter)par).substitute(result);
				}
			}
			result.setUniParent(null);
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
	
	public String innerClassName(ComponentRelation relation) throws LookupException {
		return relation.componentType().baseType().signature().name()+"_shadow_class_for_"+relation.signature().name();
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
		return relation.signature().name()+"__component";
	}
	
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
		return "set__component__"+relation.signature().name();
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
			Invocation invocation = invocation(method, origin);
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

	private Invocation invocation(Method<?, ?, ?, ?> method, Method<?, ?, ?, ?> origin) {
		Invocation invocation = new RegularMethodInvocation(origin.name(), null);
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
