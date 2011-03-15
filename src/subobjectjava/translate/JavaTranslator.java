package subobjectjava.translate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.NonLocalJavaTypeReference;
import jnome.core.expression.invocation.SuperConstructorDelegation;
import jnome.core.language.Java;
import jnome.core.modifier.Implements;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaTypeReference;

import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;
import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.SafePredicate;
import org.rejuse.predicate.TypePredicate;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.model.component.ActualComponentArgument;
import subobjectjava.model.component.ComponentNameActualArgument;
import subobjectjava.model.component.ComponentParameter;
import subobjectjava.model.component.ComponentParameterTypeReference;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ComponentRelationSet;
import subobjectjava.model.component.ComponentType;
import subobjectjava.model.component.ConfigurationBlock;
import subobjectjava.model.component.ConfigurationClause;
import subobjectjava.model.component.FormalComponentParameter;
import subobjectjava.model.component.InstantiatedComponentParameter;
import subobjectjava.model.component.MultiActualComponentArgument;
import subobjectjava.model.component.MultiFormalComponentParameter;
import subobjectjava.model.component.ParameterReferenceActualArgument;
import subobjectjava.model.component.RenamingClause;
import subobjectjava.model.expression.AbstractTarget;
import subobjectjava.model.expression.ComponentParameterCall;
import subobjectjava.model.expression.SubobjectConstructorCall;
import subobjectjava.model.language.JLo;
import subobjectjava.model.type.RegularJLoType;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.DeclarationWithParametersHeader;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersSignature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.StubDeclarationContainer;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.expression.Expression;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.expression.NamedTarget;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.member.Member;
import chameleon.core.method.Implementation;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.method.RegularMethod;
import chameleon.core.method.exception.ExceptionClause;
import chameleon.core.modifier.ElementWithModifiers;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespace.NamespaceElement;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.namespacepart.TypeImport;
import chameleon.core.property.ChameleonProperty;
import chameleon.core.reference.CrossReference;
import chameleon.core.reference.CrossReferenceWithArguments;
import chameleon.core.reference.CrossReferenceWithName;
import chameleon.core.reference.CrossReferenceWithTarget;
import chameleon.core.reference.SimpleReference;
import chameleon.core.reference.SpecificReference;
import chameleon.core.statement.Block;
import chameleon.core.statement.Statement;
import chameleon.core.variable.FormalParameter;
import chameleon.core.variable.VariableDeclaration;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.oo.type.BasicTypeReference;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.TypeWithBody;
import chameleon.oo.type.generics.ActualType;
import chameleon.oo.type.generics.BasicTypeArgument;
import chameleon.oo.type.generics.InstantiatedTypeParameter;
import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.generics.TypeParameterBlock.Stub;
import chameleon.oo.type.inheritance.AbstractInheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.NullLiteral;
import chameleon.support.expression.SuperTarget;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.method.RegularMethodInvocation;
import chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.modifier.Abstract;
import chameleon.support.modifier.Public;
import chameleon.support.statement.IfThenElseStatement;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.variable.LocalVariableDeclarator;
import chameleon.util.Util;

public class JavaTranslator extends AbstractTranslator {
	
	private InterfaceTransformer _interfaceTransformer = new InterfaceTransformer();
	
	public InterfaceTransformer interfaceTransformer() {
		return _interfaceTransformer;
	}

  public List<CompilationUnit> translate(CompilationUnit source, CompilationUnit implementationCompilationUnit) throws LookupException, ModelException {
  	List<CompilationUnit> result = new ArrayList<CompilationUnit>();
  	// Remove a possible old translation of the given compilation unit
  	// from the target model.
  	NamespacePart originalNamespacePart = source.namespaceParts().get(0);
  	NamespacePart newNamespacePart = implementationCompilationUnit.namespaceParts().get(0);

  	Iterator<Type> originalTypes = originalNamespacePart.children(Type.class).iterator();
  	Iterator<Type> newTypes = newNamespacePart.children(Type.class).iterator();
  	while(originalTypes.hasNext()) {
  		SingleAssociation<Type, Element> newParentLink = newTypes.next().parentLink();
  		Type translated = translatedImplementation(originalTypes.next());
  		newParentLink.getOtherRelation().replace(newParentLink, translated.parentLink());
  	}
//  	newNamespacePart.clearImports();
  	for(Import imp: originalNamespacePart.imports()) {
  		newNamespacePart.addImport(imp.clone());
  	}
  	implementationCompilationUnit.flushCache();
  	result.add(implementationCompilationUnit);
  	implementationCompilationUnit.namespacePart(1).getNamespaceLink().lock();
  	CompilationUnit interfaceCompilationUnit = interfaceTransformer().interfaceCompilationUnit(source, implementationCompilationUnit);
  	for(NamespacePart part: implementationCompilationUnit.descendants(NamespacePart.class)) {
  		part.removeDuplicateImports();
  	}
  	for(NamespacePart part: interfaceCompilationUnit.descendants(NamespacePart.class)) {
  		part.removeDuplicateImports();
  	}
	result.add(interfaceCompilationUnit);
  	return result;
  }

	private boolean isJLo(NamespaceElement element) {
		String fullyQualifiedName = element.getNamespace().getFullyQualifiedName();
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

	private void replaceConstructorCalls(Element<?> type) throws LookupException {
		List<ConstructorInvocation> invocations = type.descendants(ConstructorInvocation.class);
		for(ConstructorInvocation invocation: invocations) {
			try {
				Type constructedType = invocation.getType();
				if(isJLo(constructedType) //&& (! constructedType.isTrue(language.PRIVATE))
						) {
					if(constructedType.getName().equals("Float")) {
						System.out.println("debug");
					}
					transformToImplReference((CrossReferenceWithName) invocation.getTypeReference());
				}
			} catch(LookupException exc) {
				transformToImplReference((CrossReferenceWithName) invocation.getTypeReference());
			}
		}
	}

	private void replaceThisLiterals(Element<?> type) throws LookupException {
		List<ThisLiteral> literals = type.descendants(ThisLiteral.class);
		for(ThisLiteral literal: literals) {
			TypeReference typeReference = literal.getTypeReference();
			if(typeReference != null) {
			  transformToImplReference((CrossReferenceWithName) typeReference);
			}
		}
	}

	private void replaceComponentAccess(Element<?> type) throws LookupException {
		List<CrossReference> literals = type.descendants(CrossReference.class);
		for(CrossReference literal: literals) {
			if((literal instanceof CrossReferenceWithTarget)) {
				transformComponentAccessors((CrossReferenceWithTarget) literal);
			}
		}
	}

	private void transformComponentAccessors(CrossReferenceWithTarget cwt) {
		Element target = cwt.getTarget();
		if(target instanceof CrossReferenceWithTarget) {
			transformComponentAccessors((CrossReferenceWithTarget) target);
		}
		boolean rewrite = false;
		String name = null;
		if((! (cwt instanceof MethodInvocation)) && (! (cwt instanceof TypeReference))) {
			name = ((CrossReferenceWithName)cwt).name();
			try {
				Declaration decl = cwt.getElement();
				if(decl instanceof ComponentRelation) {
					rewrite = true;
				}
			}catch(LookupException exc) {
//				rewrite = true;
			}
		}
		if(rewrite) {
			String getterName = getterName(name);
			MethodInvocation inv = new JavaMethodInvocation(getterName,(InvocationTarget) target);
			SingleAssociation parentLink = cwt.parentLink();
			parentLink.getOtherRelation().replace(parentLink, inv.parentLink());
		}
	}

	

	private void transformToImplReference(CrossReferenceWithName ref) {
		if(ref instanceof CrossReferenceWithTarget) {
			CrossReferenceWithName target = (CrossReferenceWithName) ((CrossReferenceWithTarget)ref).getTarget();
			if(target != null) {
				transformToImplReference(target);
			}
		}
		boolean change;
		try {
			Declaration referencedElement = ref.getElement();
			if(referencedElement instanceof Type) {
				change = true;
			} else {
				change = false;
			}
		} catch(LookupException exc) {
			change = true;
		}
		if(change) {
			String name = ref.name();
			if(name.equals("Float")) {
				System.out.println("debug");
			}
			if(! name.endsWith(IMPL)) {
			  ref.setName(name+IMPL);
			}
		}
	}

	private void transformToInterfaceReference(SpecificReference ref) {
		SpecificReference target = (SpecificReference) ref.getTarget();
		if(target != null) {
			transformToInterfaceReference(target);
		}
		String name = ref.signature().name();
		if(name.endsWith(IMPL)) {
			ref.setSignature(new SimpleNameSignature(interfaceName(name)));
		}
	}
	
	/**
	 * Return a type that represents the translation of the given JLow class to a Java class.
	 * @throws ModelException 
	 */
	private Type translatedImplementation(Type original) throws ChameleonProgrammerException, ModelException {
		Type result = original.clone();
//		result.setUniParent(original.parent());
		StubDeclarationContainer stub = new StubDeclarationContainer();
		stub.add(result);
		stub.setUniParent(original.parent());

		result.setOrigin(original);
		replaceSuperCalls(result);
		result.flushCache();
		rebindOverriddenMethods(result,original);
		result.flushCache();
		replaceConstructorCalls(result);
		result.flushCache();
//		List<ComponentRelation> relations = original.directlyDeclaredMembers(ComponentRelation.class);
		List<ComponentRelation> relations = result.directlyDeclaredMembers(ComponentRelation.class);
		for(ComponentRelation relation : relations) {
			// Add a getter for subobject
			Method getterForComponent = getterForComponent(relation,result);
			if(getterForComponent != null) {
				result.add(getterForComponent);
			}

			// Add a setter for subobject
			Method setterForComponent = setterForComponent(relation,result);
			if(setterForComponent != null) {
				result.add(setterForComponent);
			}

			// Create the inner classes for the components
			inner(result, relation);
//			result.flushCache();
//			addAliasDelegations(relation, result,original);
			result.flushCache();
		}
//		replaceSuperCalls(result);
		replaceSubobjectConstructorCalls(result); // commented out the replaceSubobjectConstructorCalls below
		for(ComponentRelation relation: result.directlyDeclaredMembers(ComponentRelation.class)) {
//			replaceSubobjectConstructorCalls(relation);

			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation,result);
			if(fieldForComponent != null) {
				result.add(fieldForComponent);
			}

			relation.disconnect();
		}
		
		
		result.addAll(selectorsFor(result));
		
		List<ComponentParameterCall> calls = result.descendants(ComponentParameterCall.class);
		for(ComponentParameterCall call: calls) {
			FormalComponentParameter parameter = call.getElement();
			MethodInvocation expr = new JavaMethodInvocation(selectorName(parameter),null);
			expr.addArgument((Expression) call.target());
			SingleAssociation pl = call.parentLink();
			pl.getOtherRelation().replace(pl, expr.parentLink());
		}
		for(SubtypeRelation rel: result.nonMemberInheritanceRelations(SubtypeRelation.class)) {
			processSuperComponentParameters(rel);
		}
//X		rebindOverriddenMethods(result,original);

		addStaticHooksForMethodsOverriddenInSuperSubobject(result,original);
		addNonOverriddenStaticHooks(result,original);

//M		replaceThisLiterals(result);
//N		replaceComponentAccess(result);
//Y		expandReferences(result);
//Z		removeNonLocalReferences(result);
		
		// The result is still temporarily attached to the original model.
		replaceThisLiterals(result); //M
		replaceComponentAccess(result);//N
		transformToImplRecursive(result);
//		replaceConstructorCalls(result);
		expandReferences(result); //Y
		removeNonLocalReferences(result); //Z
		result.setUniParent(null);
		return result;
	}
	
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,Type original) throws ModelException {
		for(ComponentRelation relation: original.descendants(ComponentRelation.class)) {
			addStaticHooksForMethodsOverriddenInSuperSubobject(result,relation);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,ComponentRelation relation) throws ModelException {
		Type container = containerOfDefinition(result, relation.farthestAncestor(Type.class), relation.componentType().signature());
		List<Member> members = relation.componentType().members();
		members.removeAll(relation.componentType().directlyDeclaredMembers());
		Java lang = relation.language(Java.class);
		ChameleonProperty ov = lang.OVERRIDABLE;
		ChameleonProperty def = lang.DEFINED;
		incorporateImports(relation,result.nearestAncestor(NamespacePart.class));
		if(relation.componentType().getFullyQualifiedName().equals("radio.Radio.frequency")) {
			System.out.println("debug");
		}
		for(Member member: members) {
			if(member instanceof Method && member.ancestors().contains(relation) && member.isTrue(ov) && member.isTrue(def) && (!lang.isOperator((Method) member))) {
				Method newMethod = staticMethod(container, (Method) member);
				newMethod.setUniParent(member.parent());
				incorporateImports(newMethod);
				substituteTypeParameters(newMethod);
				newMethod.setUniParent(null);
				createSuperImplementation(newMethod,(Method) member);
				container.add(newMethod);
			}
		}
	}
	
  public void createSuperImplementation(Method<?,?,?,?> method, Method original) throws LookupException {
  	Block body = new Block();
  	method.setImplementation(new RegularImplementation(body));
  	MethodInvocation invocation = invocation(method, original.signature().name());
  	invocation.setTarget(new SuperTarget());
  	addImplementation(original, body, invocation);
}


	
	private void addNonOverriddenStaticHooks(Type result,Type original) throws LookupException {
		for(ComponentRelation relation: original.descendants(ComponentRelation.class)) {
			addNonOverriddenStaticHooks(result,relation);
		}
	}
	
	private void addNonOverriddenStaticHooks(Type result,ComponentRelation relation) throws LookupException {
		Type root = containerOfDefinition(result, relation.farthestAncestor(Type.class), relation.componentType().signature());
//		System.out.println("Root for "+relation.componentType().getFullyQualifiedName()+" is "+root.getFullyQualifiedName());
		List<Member> membersOfRelation = relation.componentType().members();
		
		Set<ComponentRelation> overriddenRelations = (Set<ComponentRelation>) relation.overriddenMembers();
	}
	
	private void rebindOverriddenMethods(Type result, Type original) throws ModelException {
		for(final Method method: original.descendants(Method.class)) {
			rebindOverriddenMethodsOf(result, original, method);
		}
	}

	private void rebindOverriddenMethodsOf(Type result, Type original, Method<?,?,?,?> method) throws Error, ModelException {
		Set<? extends Member> overridden = method.overriddenMembers();
		Java language = method.language(Java.class);
		if(! overridden.isEmpty()) {
		if(! method.isTrue(language.CONSTRUCTOR)) {
			final Method tmp = method.clone();
//			Type containerOfNewDefinition = containerOfDefinition(result,original, method); // SUBOBJECT
			Type containerOfNewDefinition = typeOfDefinition(result,original, method); // OK: SUBOBJECT
		if(containerOfNewDefinition != null) {
				tmp.setUniParent(containerOfNewDefinition);
				DeclarationSelector<Method> selector = new SelectorWithoutOrder<Method>(Method.class) {
					@Override
					public Signature signature() {
						return tmp.signature();
					}
				};
				Method newDefinitionInResult = null;
				newDefinitionInResult = containerOfNewDefinition.members(selector).get(0);
				Method<?,?,?,?> stat = newDefinitionInResult.clone();
				String name = staticMethodName(method, containerOfNewDefinition);
				stat.setName(name);
				containerOfNewDefinition.add(stat);
//				if(!stat.descendants(ComponentParameterCall.class).isEmpty()) {
//					throw new Error();
//				}
			}
		}
		}
		for(Member toBeRebound: overridden) {
			rebind(result, original, method, (Method) toBeRebound);
		}
	}

	private void rebind(Type container, Type original, Method<?,?,?,?> newDefinition, Method toBeRebound) throws ModelException {
		try {
			String newwFQN = newDefinition.nearestAncestor(Type.class).getFullyQualifiedName()+"."+newDefinition.name();
			if(toBeRebound.name().equals("setFrequency")) {
				System.out.println("debug");
			}
//		Type containerOfNewDefinition = containerOfDefinition(container,original, newDefinition);
    Type containerOfNewDefinition = typeOfDefinition(container,original, newDefinition);
		Type rootOfNewDefinitionInOriginal = levelOfDefinition(newDefinition);
		List<Element> trailOfRootInOriginal = filterAncestors(rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.add(0, rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.remove(trailOfRootInOriginal.size()-1);
//		Type containerToAdd = createOrGetInnerTypeAux(container, original, trailOfRootInOriginal,1);
		Type containerToAdd = createOrGetSubobject(container, original, trailOfRootInOriginal,1);
		
		List<Element> trailtoBeReboundInOriginal = filterAncestors(toBeRebound);
		Type rootOfToBeRebound = levelOfDefinition(toBeRebound);
		while(! (trailtoBeReboundInOriginal.get(trailtoBeReboundInOriginal.size()-1) == rootOfToBeRebound)) {
			trailtoBeReboundInOriginal.remove(trailtoBeReboundInOriginal.size()-1);
		}
		trailtoBeReboundInOriginal.remove(trailtoBeReboundInOriginal.size()-1);
		Type containerOfToBebound = containerToAdd;
		if(! trailtoBeReboundInOriginal.isEmpty()) {
			containerOfToBebound = createOrGetSubobject(containerToAdd, original, trailtoBeReboundInOriginal,1);
		}
		if((containerOfToBebound != null) && ! containerOfToBebound.sameAs(containerOfNewDefinition)) {
			System.out.println("----------------------");
			String newFQN = containerOfNewDefinition.getFullyQualifiedName()+"."+newDefinition.name();
			if(newFQN.equals("jlo.graph.WeightedDigraph.hashCode")) {
				System.out.println("debug");
			}
			System.out.println("Source: " + newFQN);
			System.out.println("Target: "+containerOfToBebound.getFullyQualifiedName()+"."+toBeRebound.name());
			System.out.println("----------------------");
			String thisName = containerOfNewDefinition.getFullyQualifiedName();
			Method reboundMethod = createOutward(toBeRebound, newDefinition.name(),thisName);
			//FIXME this is tricky.
			reboundMethod.setUniParent(toBeRebound);
			Implementation<Implementation> impl = reboundMethod.implementation();
			reboundMethod.setImplementation(null);
			substituteTypeParameters(reboundMethod);
			reboundMethod.setImplementation(impl);
			reboundMethod.setUniParent(null);
			containerOfToBebound.add(reboundMethod);
			
			Method<?, ?, ?, ?> staticReboundMethod = staticMethod(containerOfToBebound, reboundMethod);
			String name = containerOfNewDefinition.getFullyQualifiedName().replace('.', '_');
			for(SimpleNameMethodInvocation inv:staticReboundMethod.descendants(SimpleNameMethodInvocation.class)) {
				if(! (inv instanceof ConstructorInvocation)) {
					name = toImplName(name);
					inv.setName(staticMethodName(newDefinition, containerOfNewDefinition));
				}
			}
			containerOfToBebound.add(staticReboundMethod);
			containerOfToBebound.flushCache();
		}
		} catch(ModelException exc) {
			System.out.println("debug");
		}
	}

	private Method<?, ?, ?, ?> staticMethod(Type containerOfToBebound, Method<?,?,?,?> reboundMethod) throws ModelException {
		Method<?,?,?,?> staticReboundMethod = reboundMethod.clone();
		staticReboundMethod.setOrigin(reboundMethod);
		String newName = staticMethodName(reboundMethod,containerOfToBebound);
//		staticReboundMethod.addModifier(new Final());
		ChameleonProperty nat = reboundMethod.language(Java.class).NATIVE;
		staticReboundMethod.setUniParent(reboundMethod.parent());
		for(Modifier modifier: staticReboundMethod.modifiers(nat)) {
			modifier.disconnect();
		}
		staticReboundMethod.setUniParent(null);
		staticReboundMethod.setName(newName);
		return staticReboundMethod;
	}

	private String staticMethodName(String methodName,Type containerOfToBebound) {
		String tmp = containerOfToBebound.getFullyQualifiedName().replace('.', '_')+"_"+methodName;
		return stripImpl(tmp);
	}
	
	private String staticMethodName(Method clone,Type containerOfToBebound) {
		return staticMethodName(clone.name(), containerOfToBebound);
	}
	
	private String stripImpl(String string) {
		return string.replaceAll(IMPL, "");
	}

	private Type f(Type container, Type original, Element newDefinition) throws LookupException {
		List<ComponentRelation> ancestors = newDefinition.ancestors(ComponentRelation.class);
		Type result = container;
		int size = ancestors.size();
		for(int i=0; i< size; i++) {
			ComponentRelation originalRelation = ancestors.get(size-1-i);
			ComponentRelation newRelation = createOrGetSubobject(result, originalRelation);
			result = newRelation.componentType();
		}
		return result;
	}
	
	private Type typeOfDefinition(Type container, Type original, Element<?> newDefinition) throws LookupException {
		Type rootOfNewDefinitionInOriginal = levelOfDefinition(newDefinition);
		Type result = container;

		List<Element> trailOfRootInOriginal = filterAncestors(rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.add(0, rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.remove(trailOfRootInOriginal.size()-1);
		result = createOrGetSubobject(container, original, trailOfRootInOriginal,1);

		List<Element> ancestors = newDefinition.ancestors();
    while(ancestors.get(ancestors.size()-1) != rootOfNewDefinitionInOriginal) {
    	ancestors.remove(ancestors.size()-1);
    }
    ancestors.remove(ancestors.size()-1);
    new TypePredicate<Element,ComponentRelation>(ComponentRelation.class).filter(ancestors);
		int size = ancestors.size();
		for(int i=0; i< size; i++) {
			ComponentRelation originalRelation = (ComponentRelation) ancestors.get(size-1-i);
			ComponentRelation newRelation = createOrGetSubobject(result, originalRelation);
			result = newRelation.componentType();
		}
		return result;
	}
	
	private Type containerOfDefinition(Type container, Type original,
			Element newDefinition) throws LookupException {
		Type rootOfNewDefinitionInOriginal = levelOfDefinition(newDefinition);
		Type result = container;
		List<Element> trailOfRootInOriginal = filterAncestors(rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.add(0, rootOfNewDefinitionInOriginal);
		trailOfRootInOriginal.remove(trailOfRootInOriginal.size()-1);
		result = createOrGetInnerTypeAux(container, original, trailOfRootInOriginal,1);
		
		List<Element> trailNewDefinitionInOriginal = filterAncestors(newDefinition);
		// Remove everything up to the container.
		while(! (trailNewDefinitionInOriginal.get(trailNewDefinitionInOriginal.size()-1) == rootOfNewDefinitionInOriginal)) {
			trailNewDefinitionInOriginal.remove(trailNewDefinitionInOriginal.size()-1);
		}
		// Remove the container.
		trailNewDefinitionInOriginal.remove(trailNewDefinitionInOriginal.size()-1);
		if(! trailNewDefinitionInOriginal.isEmpty()) {
//			trailOfRootInOriginal.add(0, rootOfNewDefinitionInOriginal);
			result = createOrGetInnerTypeAux(result, original, trailNewDefinitionInOriginal,1);
		}
		return result;
	}
	
	private ComponentRelation createOrGetSubobject(Type container, ComponentRelation originalRelation) throws LookupException {
		List<ComponentRelation> relations = container.members(ComponentRelation.class);
		ComponentRelation result = null;
		for(ComponentRelation relation: relations) {
			if(isLocallyDefined(relation, container) && relation.signature().sameAs(originalRelation.signature())) {
				result = relation;
				break;
			}
		}
		if((result == null) || (result.nearestAncestor(Type.class) != container)){
			result = originalRelation.clone();
			result.setOrigin(originalRelation);
			result.setBody(new ClassBody());
			result.setConfigurationBlock(null);
//			x;
			// Must clear the body here, otherwise an overridden member gets into the class of the overriding member, and
			// the overridden member then also 'overrides' the super method of the overriding member, and thus a second copy
			// of the overriding member is generated to delegate to the subobject, but this causes a conflict.
			result.setUniParent(originalRelation.parent());
			substituteTypeParameters(result);
			expandReferences(result);
			result.setUniParent(null);
			container.add(result);
		}
		return result;
	}
	
	private Type levelOfDefinition(Element<?> element) {
		Type result = element.nearestAncestor(Type.class, new SafePredicate<Type>(){
			@Override
			public boolean eval(Type object) {
				return ! (object instanceof ComponentType);
			}});
		return result;
	}

	private String toImplName(String name) {
		if(! name.endsWith(IMPL)) {
			name = name + IMPL;
		}
		return name;
	}
	
	private Type createOrGetInnerTypeForType(Type container, Type original, Type current, List<Element> elements, int baseOneIndex) throws LookupException {
//			Signature innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, original)));
			Signature innerName = current.signature().clone();
			SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
			tref.setUniParent(container);
			Type result;
			try { 
				result= tref.getElement();
			} catch(LookupException exc) {
				// We add the imports to the original. They are copied later on to 'container'.
				ComponentRelation relation = ((ComponentType)current).nearestAncestor(ComponentRelation.class);
				result = emptyInnerClassFor(relation, container);
				NamespacePart namespacePart = container.farthestAncestor(NamespacePart.class);
				incorporateImports(relation, namespacePart);
				// Since we are adding inner classes that were not written in the current namespacepart, their type
				// may not have been imported yet. Therefore, we add an import of the referenced component type.
				namespacePart.addImport(new TypeImport(container.language(Java.class).createTypeReference(relation.referencedComponentType().getFullyQualifiedName())));
				container.add(result);
				container.flushCache();
			}
			return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
	}

	private Type createOrGetSubobjectForType(Type container, Type original, Type current, List<Element> elements, int baseOneIndex) throws LookupException {
//	Signature innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, original)));
	Signature innerName = current.signature().clone();
	SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
	tref.setUniParent(container);
	Type result;
	try { 
		result= tref.getElement();
	} catch(LookupException exc) {
		// We add the imports to the original. They are copied later on to 'container'.
		ComponentRelation relation = ((ComponentType)current).nearestAncestor(ComponentRelation.class);
		result = emptyInnerClassFor(relation, container);
		NamespacePart namespacePart = container.farthestAncestor(NamespacePart.class);
		incorporateImports(relation, namespacePart);
		// Since we are adding inner classes that were not written in the current namespacepart, their type
		// may not have been imported yet. Therefore, we add an import of the referenced component type.
		namespacePart.addImport(new TypeImport(container.language(Java.class).createTypeReference(relation.referencedComponentType().getFullyQualifiedName())));
		container.add(result);
		container.flushCache();
	}
	return createOrGetSubobject(result, original, elements, baseOneIndex + 1);
}
	private Type createOrGetInnerTypeForComponent(Type container, Type original, ComponentRelation relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
			Signature innerName = null;
			innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, container)));
			SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
			tref.setUniParent(container);
			Type result;
			try { 
				result= tref.getElement();
			} catch(LookupException exc) {
				// We add the imports to the original. They are copied later on to 'container'.
				result = emptyInnerClassFor(relationBeingTranslated, container);
				NamespacePart namespacePart = container.farthestAncestor(NamespacePart.class);
				incorporateImports(relationBeingTranslated, namespacePart);
				// Since we are adding inner classes that were not written in the current namespacepart, their type
				// may not have been imported yet. Therefore, we add an import of the referenced component type.
				namespacePart.addImport(new TypeImport(container.language(Java.class).createTypeReference(relationBeingTranslated.referencedComponentType().getFullyQualifiedName())));
				container.add(result);
				container.flushCache();
			}
			return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
	}
	
	//createOrGetSubobjectForComponent
	
	private Type createOrGetSubobjectForComponent(Type container, Type original, ComponentRelation relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
//		Signature innerName = null;
//		innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, container)));
//		SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
//		tref.setUniParent(container);
//		Type result;
//		try { 
//			result= tref.getElement();
//		} catch(LookupException exc) {
//			// We add the imports to the original. They are copied later on to 'container'.
//			result = emptyInnerClassFor(relationBeingTranslated, container);
//			NamespacePart namespacePart = container.farthestAncestor(NamespacePart.class);
//			incorporateImports(relationBeingTranslated, namespacePart);
//			// Since we are adding inner classes that were not written in the current namespacepart, their type
//			// may not have been imported yet. Therefore, we add an import of the referenced component type.
//			namespacePart.addImport(new TypeImport(container.language(Java.class).createTypeReference(relationBeingTranslated.referencedComponentType().getFullyQualifiedName())));
//			container.add(result);
//			container.flushCache();
//		}
//		return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
		ComponentRelation relation = createOrGetSubobject(container, relationBeingTranslated);
		Type result = relation.componentType();
		return createOrGetSubobject(result, original, elements, baseOneIndex + 1);
}
	private List<Element> filterAncestors(Element<?> element) {
		SafePredicate<Element> predicate = componentRelationOrNonComponentTypePredicate();
		return element.ancestors(Element.class, predicate);
	}

	private SafePredicate<Element> componentRelationOrNonComponentTypePredicate() {
		return new SafePredicate<Element>(){
			@Override
			public boolean eval(Element object) {
				return (object instanceof ComponentRelation) || (object instanceof Type && !(object instanceof ComponentType));
			}};
	}
	
	private Type createOrGetInnerTypeAux(Type container, Type original, List<Element> elements, int baseOneIndex) throws LookupException {
		int index = elements.size()-baseOneIndex;
		if(index >= 0) {
			Element element = elements.get(index);
			if(element instanceof ComponentRelation) {
				return createOrGetInnerTypeForComponent(container,original, (ComponentRelation) element, elements,baseOneIndex);
			} else {
				return createOrGetInnerTypeForType(container,original, (Type) element, elements,baseOneIndex);
			}
		} else {
			return container;
		}
	}

	private Type createOrGetSubobject(Type container, Type original, List<Element> elements, int baseOneIndex) throws LookupException {
		int index = elements.size()-baseOneIndex;
		if(index >= 0) {
			Element element = elements.get(index);
			if(element instanceof ComponentRelation) {
				return createOrGetSubobjectForComponent(container,original, (ComponentRelation) element, elements,baseOneIndex);
			} else {
				//return createOrGetInnerTypeForType(container,original, (Type) element, elements,baseOneIndex);
				return createOrGetSubobjectForType(container,original, (Type) element, elements,baseOneIndex);
			}
		} else {
			return container;
		}
	}

	private void transformToImplRecursive(Type type) throws ModelException {
		transformToImpl(type);
		for(Type nested: type.directlyDeclaredMembers(Type.class)) {
			transformToImplRecursive(nested);
		}
	}

	private void transformToImpl(Type type) throws ModelException {
		JLo lang = type.language(JLo.class);
//		if(! type.isTrue(lang.PRIVATE)) {
			// Change the name of the outer type.
			// What a crappy code. I would probably be easier to not add IMPL
			// to the generated subobject class in the first place, but add
			// it afterwards.
			String oldName = type.getName();
			String name = oldName;
			if(! name.endsWith(IMPL)) {
				name = name +IMPL;
				type.signature().setName(name);
			}
			for(SubtypeRelation relation: type.nonMemberInheritanceRelations(SubtypeRelation.class)) {
				BasicJavaTypeReference tref = (BasicJavaTypeReference) relation.superClassReference();
				try {
					if((! relation.isTrue(lang.IMPLEMENTS_RELATION)) && isJLo(tref.getElement())) {
						tref.setSignature(new SimpleNameSignature(tref.signature().name()+IMPL));
					}
				}
				catch(LookupException exc) {
					tref.getElement();
					throw exc;
				}
			}
			implementOwnInterface(type);
			for(TypeElement decl: type.directlyDeclaredElements()) {
				if((decl instanceof Method) && (decl.is(lang.CONSTRUCTOR) == Ternary.TRUE)) {
					((Method)decl).setName(name);
				}
				if(decl instanceof ElementWithModifiers) {
					makePublic(decl);
				}
			}
//		}
	}

	private void implementOwnInterface(Type type) {
		JLo language = type.language(JLo.class);
//		if(!type.isTrue(language.PRIVATE)) {
			String oldFQN = type.getFullyQualifiedName();
			BasicJavaTypeReference createTypeReference = language.createTypeReference(oldFQN);
			transformToInterfaceReference(createTypeReference);
			// Copy own type parameters
//			List<TypeParameter> tpars = type.parameters(TypeParameter.class);
//			for(TypeParameter parameter:tpars) {
//				createTypeReference.addArgument(language.createBasicTypeArgument(language.createTypeReference(parameter.signature().name())));
//			}
			// Copy type parameters from outer classes if necessary
			copyTypeParametersIfNecessary(type, createTypeReference);
			SubtypeRelation relation = new SubtypeRelation(createTypeReference);
			relation.addModifier(new Implements());
			type.addInheritanceRelation(relation);
//		}
	}


	private void copyTypeParametersIfNecessary(Type type, BasicJavaTypeReference createTypeReference) {
		copyTypeParametersFromAncestors(type, createTypeReference);
	}




	private void copyTypeParametersFromAncestors(Element<?> type, BasicJavaTypeReference createTypeReference) {
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
	
//	private void copyTypeParametersFromFarthestAncestor(Element<?> type, BasicJavaTypeReference createTypeReference) {
//		Type farthestAncestor = type.farthestAncestorOrSelf(Type.class);
//		Java language = type.language(Java.class);
//		List<TypeParameter> tpars = farthestAncestor.parameters(TypeParameter.class);
//		for(TypeParameter parameter:tpars) {
//			createTypeReference.addArgument(language.createBasicTypeArgument(language.createTypeReference(parameter.signature().name())));
//		}
//	}
	
	private void processSuperComponentParameters(AbstractInheritanceRelation<?> relation) throws LookupException {
		TypeReference tref = relation.superClassReference();
		Type type = relation.nearestAncestor(Type.class);
		if(tref instanceof ComponentParameterTypeReference) {
			ComponentParameterTypeReference ctref = (ComponentParameterTypeReference) tref;
			Type ctype = tref.getType();
			type.addAll(selectorsForComponent(ctype));
			relation.setSuperClassReference(ctref.componentTypeReference());
		}
	}

	private void removeNonLocalReferences(Type type) throws LookupException {
		for(NonLocalJavaTypeReference tref: type.descendants(NonLocalJavaTypeReference.class)) {
			SingleAssociation<NonLocalJavaTypeReference, Element> parentLink = tref.parentLink();
			parentLink.getOtherRelation().replace(parentLink, tref.actualReference().parentLink());
		}
	}

	private void expandReferences(Element<?> type) throws LookupException {
		Java language = type.language(Java.class);
		for(BasicJavaTypeReference tref: type.descendants(BasicJavaTypeReference.class)) {
			if(tref.getTarget() == null) {
				try {
					// Filthy hack, should add meta information to such references, and use that instead.
					if(! tref.signature().name().contains(SHADOW)) {
						Type element = tref.getElement();
						if(! element.isTrue(language.PRIVATE)) {
							String fullyQualifiedName = element.getFullyQualifiedName();
							String predecessor = Util.getAllButLastPart(fullyQualifiedName);
							if(predecessor != null) {
								NamedTarget nt = new NamedTarget(predecessor);
								tref.setTarget(nt);
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
	
	private List<Method> selectorsFor(ComponentRelation rel) throws LookupException {
		Type t = rel.referencedComponentType();
		return selectorsForComponent(t);
	}

	private List<Method> selectorsForComponent(Type t) throws LookupException {
		List<Method> result = new ArrayList<Method>();
		for(ComponentParameter par: t.parameters(ComponentParameter.class)) {
			
			Method realSelector= realSelectorFor((InstantiatedComponentParameter) par);
			realSelector.setUniParent(t);
			substituteTypeParameters(realSelector);
			realSelector.setUniParent(null);
			result.add(realSelector);
		}
		return result;
	}
	
	private Method realSelectorFor(InstantiatedComponentParameter<?> par) throws LookupException {
		SimpleNameDeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(selectorName(par));
		FormalComponentParameter formal = par.formalParameter();
		Java language = par.language(Java.class);
//		Method result = new NormalMethod(header,formal.componentTypeReference().clone());
		Type declarationType = formal.declarationType();
		JavaTypeReference reference = language.reference(declarationType);
		reference.setUniParent(null);
		Method result = par.language(Java.class).createNormalMethod(header,reference);
		result.addModifier(new Public());
//		result.addModifier(new Abstract());
		header.addFormalParameter(new FormalParameter("argument", formal.containerTypeReference().clone()));
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		ActualComponentArgument arg = par.argument();
		Expression expr;
		if(arg instanceof ComponentNameActualArgument) {
			ComponentNameActualArgument singarg = (ComponentNameActualArgument) arg;
			expr = new JavaMethodInvocation(getterName(singarg.declaration()),new NamedTargetExpression("argument", null));
			body.addStatement(new ReturnStatement(expr));
		} else if(arg instanceof ParameterReferenceActualArgument) {
			ParameterReferenceActualArgument ref = (ParameterReferenceActualArgument) arg;
			ComponentParameter p = ref.declaration();
			expr = new JavaMethodInvocation(selectorName(p), null);
			((JavaMethodInvocation)expr).addArgument(new NamedTargetExpression("argument",null));
			body.addStatement(new ReturnStatement(expr));
		}
		 else {
			// result variable declaration
			VariableDeclaration declaration = new VariableDeclaration("result");
			BasicJavaTypeReference arrayList = language.createTypeReference("java.util.ArrayList");
			JavaTypeReference componentType = language.reference(formal.componentTypeReference().getElement());
			componentType.setUniParent(null);
			BasicTypeArgument targ = language.createBasicTypeArgument(componentType);
			arrayList.addArgument(targ);
			
//			LocalVariableDeclarator varDecl = new LocalVariableDeclarator(reference.clone());
			LocalVariableDeclarator varDecl = new LocalVariableDeclarator(arrayList.clone());

			Expression init = new ConstructorInvocation(arrayList, null);
			declaration.setInitialization(init);
			varDecl.add(declaration);
			body.addStatement(varDecl);
			
			// add all components
			ComponentRelationSet componentRelations = ((MultiActualComponentArgument)arg).declaration();
			for(DeclarationWithType rel: componentRelations.relations()) {
				Expression t = new NamedTargetExpression("result", null);
				SimpleNameMethodInvocation inv = new JavaMethodInvocation("add", t);
				Expression componentSelector;
				if(rel instanceof ComponentParameter) {
					if(rel instanceof MultiFormalComponentParameter) {
						inv.setName("addAll");
					}
					componentSelector = new JavaMethodInvocation(selectorName((ComponentParameter)rel), null);
					((JavaMethodInvocation)componentSelector).addArgument(new NamedTargetExpression("argument",null));
				} else {
				  componentSelector = new NamedTargetExpression(rel.signature().name(), new NamedTargetExpression("argument",null));
				}
				inv.addArgument(componentSelector);
				body.addStatement(new StatementExpression(inv));
			}
			
			// return statement
			expr = new NamedTargetExpression("result",null);
			body.addStatement(new ReturnStatement(expr));
		}
    return result;		
	}
	
	private List<Method> selectorsFor(Type type) throws LookupException {
		ParameterBlock<?,ComponentParameter> block = type.parameterBlock(ComponentParameter.class);
		List<Method> result = new ArrayList<Method>();
		if(block != null) {
		  for(ComponentParameter par: block.parameters()) {
		  	result.add(selectorFor((FormalComponentParameter<?>) par));
		  }
		}
		return result;
	}

	private String selectorName(ComponentParameter<?> par) {
		return "__select$"+ toUnderScore(par.nearestAncestor(Type.class).getFullyQualifiedName())+"$"+par.signature().name();
	}
	
	private String toUnderScore(String string) {
		return string.replace('.', '_');
	}
	
	private Method selectorFor(FormalComponentParameter<?> par) throws LookupException {
		SimpleNameDeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(selectorName(par));
		Java language = par.language(Java.class);
		JavaTypeReference reference = language.reference(par.declarationType());
		reference.setUniParent(null);
		Method result = par.language(Java.class).createNormalMethod(header,reference);
		result.addModifier(new Public());
		header.addFormalParameter(new FormalParameter("argument", par.containerTypeReference().clone()));
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		ConstructorInvocation cons = new ConstructorInvocation((BasicJavaTypeReference) par.language(Java.class).createTypeReference("java.lang.Error"), null);
		body.addStatement(new ThrowStatement(cons));
		return result;
	}

//	private void replaceSubobjectConstructorCalls(final ComponentRelation relation, Method<?,?,?,?> constructor) throws LookupException {
//		boolean overridden = isOverriddenInSuperType(relation);
//		if(overridden) {
//			replaceSubobjectConstructorCallOverridden(relation, constructor);
//		} else {
//			replaceSubobjectConstructorCallNonOverridden(relation, constructor);
//		}
//	}

//	private boolean isLocallyDefinedAndOverriddenInSuperType(final ComponentRelation relation, Type type) throws LookupException {
//		Collection<ComponentRelation> overridden = (Collection<ComponentRelation>) relation.overriddenMembers();
//		new SafePredicate<ComponentRelation>() {
//			@Override
//			public boolean eval(ComponentRelation object) {
//				return object.farthestAncestor(Type.class) != relation.farthestAncestor(Type.class);
//			}
//		}.filter(overridden);
//		return isLocallyDefined(relation, type) && !overridden.isEmpty();
//	}

	private boolean isLocallyDefined(ComponentRelation relation,Type type) throws LookupException {
		return relation.ancestors().contains(type);
	}

	private List<SubobjectConstructorCall> constructorCallsOfRelation(
			final ComponentRelation relation, Method<?, ?, ?, ?> constructor)
			throws LookupException {
		List<SubobjectConstructorCall> constructorCalls = constructor.descendants(SubobjectConstructorCall.class, new UnsafePredicate<SubobjectConstructorCall,LookupException>() {
			@Override
			public boolean eval(SubobjectConstructorCall constructorCall) throws LookupException {
				return constructorCall.getTarget().getElement().equals(relation);
			}
		}
		);
		return constructorCalls;
	}

	private void replaceSubobjectConstructorCalls(Type type) throws LookupException {
		Java lang = type.language(Java.class);
		for(Method constructor: type.descendants(Method.class, lang.CONSTRUCTOR)) {
			createStrategyCloneOfConstructor(constructor);
			replaceSubobjectConstructorCalls(constructor, false);
		}
	}
	
	private void createStrategyCloneOfConstructor(Method<?,?,?,?> constructor) throws LookupException {
		// create super call if it does not already exist.
		List<SuperConstructorDelegation> superCalls = constructor.descendants(SuperConstructorDelegation.class);
		MethodInvocation superCall;
		if(superCalls.isEmpty()) {
			superCall = new SuperConstructorDelegation();
			((RegularImplementation)constructor.implementation()).getBody().addInFront(new StatementExpression(superCall));
		} else {
			superCall = superCalls.get(0);
		}
		Java language = constructor.language(Java.class);
	  Type container = constructor.nearestAncestor(Type.class);
	  Method<?,?,?,?> clone = constructor.clone();
	  DeclarationWithParametersHeader header = clone.header();
	  boolean added = false;
	  for(ComponentRelation relation: container.members(ComponentRelation.class)) {
		  ClassBody body = relation.nearestAncestor(ClassBody.class);
		  if((relation.origin() == relation) && (body != null) && container.subTypeOf(body.nearestAncestor(Type.class))) {
			  if(body.nearestAncestor(Type.class) == container) {
				  createStrategy(relation);
			  }
			  Method cons = null;
			  for(SubobjectConstructorCall call: constructor.descendants(SubobjectConstructorCall.class)) {
			  	if(call.getTarget().getElement() == relation) {
			  		Method originalCons = call.getElement();
			  		cons = originalCons.clone();
			  		cons.setUniParent(originalCons.parent());
			  		substituteTypeParameters(cons);
			  		cons.setUniParent(relation.nearestAncestor(Type.class));
			  	}
			  }
			  if(cons != null) {
				  createSpecificStrategy(relation,cons);
			  }
			  header.addFormalParameter(new FormalParameter(new SimpleNameSignature(constructorArgumentName(relation)), language.createTypeReference(strategyName(relation))));
			  header.addFormalParameter(new FormalParameter(new SimpleNameSignature("default"+constructorArgumentName(relation)), language.createTypeReference(defaultStrategyName(relation,superCall))));
			  added = true;
		  }
	  }
	  if(added) {
	  	container.add(clone);
	  	replaceSubobjectConstructorCalls(clone, true);
	  }
  }

	private String defaultStrategyName(ComponentRelation relation,	MethodInvocation superCall) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		Method constructor = superCall.getElement();
		while(constructor.nearestAncestor(Type.class) != type) {
			List<SuperConstructorDelegation> supers = constructor.descendants(SuperConstructorDelegation.class);
			if(supers.isEmpty()) {
				MethodInvocation defaultConstructorInvocation = new SuperConstructorDelegation();
				defaultConstructorInvocation.setUniParent(constructor.implementation());
				constructor = defaultConstructorInvocation.getElement();
			} else {
				constructor = supers.get(0).getElement();
			}
		}
	}

	private String constructorArgumentName(ComponentRelation relation) throws LookupException {
		return "strategyFor"+toUnderScore(relation.componentType().getFullyQualifiedName());
	}
	
	private Type createStrategy(ComponentRelation relation) throws LookupException {
		String name = strategyName(relation);
		Type type = relation.nearestAncestor(Type.class);
		List<Member> members = type.directlyDeclaredMembers();
		Type result = null;
		boolean add = true;
		for(Member member: members) {
			if(member.signature().name().equals(name)) {
				add = false;
				result = (Type) member;
			}
		}
		if(add) {
			String componentTypeName = relation.componentType().getFullyQualifiedName();
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			DeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(CONSTRUCT);
			TypeReference typeRef = language.createTypeReference(relation.nearestAncestor(Type.class).getFullyQualifiedName());
			header.addFormalParameter(new FormalParameter(new SimpleNameSignature("object"), typeRef));
			Method constructor = language.createNormalMethod(header, language.createTypeReference(componentTypeName));
			constructor.setImplementation(null);
			constructor.addModifier(new Abstract());
			strategy.add(constructor);
			strategy.addModifier(new Abstract());
			type.add(strategy);
			result = strategy;
		}
		return result;
	}

	private Type createSpecificStrategy(ComponentRelation relation, Method<?,?,?,?> constructor) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		Type result = null;
		String name = strategyName(relation);
		String componentTypeName = relation.componentType().getFullyQualifiedName();
		List<Type> parameterTypes = constructor.formalParameterTypes();
		for(Type t: parameterTypes) {
			name += toUnderScore(t.getFullyQualifiedName());
		}

		List<Member> members = type.directlyDeclaredMembers();
		boolean add = true;
		for(Member member: members) {
			if(member instanceof Type && member.signature().name().equals(name)) {
				add = false;
				result = (Type) member;
			}
		}
		if(add) {
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			DeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(CONSTRUCT);
			TypeReference typeRef = language.createTypeReference(relation.nearestAncestor(Type.class).getFullyQualifiedName());
			header.addFormalParameter(new FormalParameter(new SimpleNameSignature("objectafrkuscggfjsdk"), typeRef));
			for(FormalParameter param: constructor.formalParameters()) {
				header.addFormalParameter(param.clone());
			}
			Method factoryMethod = language.createNormalMethod(header, language.createTypeReference(componentTypeName));
			factoryMethod.setImplementation(null);
			factoryMethod.addModifier(new Abstract());
			strategy.add(factoryMethod);
			strategy.addModifier(new Abstract());
			type.add(strategy);
			result = strategy;
		}
		return result;
	}

	
	private String strategyName(ComponentRelation relation) throws LookupException {
		String name = toUnderScore(relation.componentType().getFullyQualifiedName()) + STRATEGY;
		return name;
	}
	
	public final String STRATEGY = "_constructor";

	private void replaceSubobjectConstructorCalls(Method<?,?,?,?> constructor, boolean clonedConstructor)
	throws LookupException {
		SuperConstructorDelegation superCall = constructor.descendants(SuperConstructorDelegation.class).get(0);
		Type type = constructor.nearestAncestor(Type.class);
		for(ComponentRelation relation: type.members(ComponentRelation.class)) {
			ClassBody body = relation.nearestAncestor(ClassBody.class);
			if(body != null && type.subTypeOf(body.nearestAncestor(Type.class))) {
				List<SubobjectConstructorCall> subCalls = constructorCallsOfRelation(relation, constructor);
				if(!isLocallyDefined(relation,type)) {
					if(subCalls.isEmpty()) {
						// Neither strategy is set.
						superCall.addArgument(new NullLiteral());
						superCall.addArgument(new NullLiteral());
					}
				} else if (! relation.overriddenMembers().isEmpty()) {
					// create a strategy that will create the new subobject.
					if(subCalls.isEmpty()) {
						superCall.addArgument(new NullLiteral());
						// add default strategy
						superCall.addArgument(new NullLiteral()); // FIX
					} else {
						// add explicit strategy
						superCall.addArgument(new NullLiteral()); // FIX
						// default is null;
						superCall.addArgument(new NullLiteral());
					}
				} else {
					// Create and set the subobject
					for(SubobjectConstructorCall call: subCalls) {
						MethodInvocation inv = new ConstructorInvocation((BasicJavaTypeReference) innerClassTypeReference(relation, type), null);
						inv.addAllArguments(call.getActualParameters());
						MethodInvocation setterCall = new JavaMethodInvocation(setterName(relation), null);
						setterCall.addArgument(inv);
						if(clonedConstructor) {
							MethodInvocation expression = new InfixOperatorInvocation("==", new NamedTargetExpression(constructorArgumentName(relation)));
							expression.addArgument(new NullLiteral());
							Block ifBlock = new Block();
							ifBlock.addStatement(new StatementExpression(setterCall));
							Block elseBlock = new Block();
							MethodInvocation<?,?> strategyCall = setterCall.clone();
							strategyCall.getActualParameters().get(0).disconnect();
							elseBlock.addStatement(new StatementExpression(strategyCall));
							MethodInvocation strategyInvocation = new JavaMethodInvocation(CONSTRUCT, new NamedTargetExpression(constructorArgumentName(relation)));
							strategyInvocation.addArgument(new ThisLiteral());
							strategyCall.addArgument(strategyInvocation);
							Statement ifthenelse = new IfThenElseStatement(expression, ifBlock, elseBlock);
							
							SingleAssociation<SubobjectConstructorCall, Element> parentLink = call.parent().parentLink();
							parentLink.getOtherRelation().replace(parentLink, ifthenelse.parentLink());
						} else {
							SingleAssociation<SubobjectConstructorCall, Element> parentLink = call.parentLink();
							parentLink.getOtherRelation().replace(parentLink, setterCall.parentLink());
						}
					}
				}
			}
		}
	}
	
	public final String CONSTRUCT = "construct";
	private void inner(Type javaType, ComponentRelation relation) throws LookupException {
		Type innerClass = createInnerClassFor(relation,javaType);
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
			TypeWithBody componentTypeDeclaration = relation.componentTypeDeclaration();
//			List<Method> elements = methodsOfComponentBody(componentTypeDeclaration);
			ConfigurationBlock block = relation.configurationBlock();
			if(block != null) {
				for(ConfigurationClause clause: block.clauses()) {
					if(clause instanceof RenamingClause) {
						RenamingClause ov = (RenamingClause)clause;
						//					final QualifiedName poppedName = ov.oldFqn().popped();
						//					Type targetInnerClass = searchInnerClass(outer, relation, poppedName);
						Declaration decl = ov.oldDeclaration();
						if(decl instanceof Method) {
							final Method<?,?,?,?> method = (Method<?, ?, ?, ?>) decl;
							Method alias = createAlias(relation, method, ((SimpleNameDeclarationWithParametersSignature)ov.newSignature()).name());
							outer.add(alias);
							outer.add(staticAlias(alias,method,original));
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

	private List<Method> methodsOfComponentBody(TypeWithBody componentTypeDeclaration) {
		List<Method> elements;
		if(componentTypeDeclaration != null) {
			elements = componentTypeDeclaration.body().children(Method.class);
		} else {
			elements = new ArrayList<Method>();
		}
		return elements;
	}

	
	/**
	 * 
	 * @param relationBeingTranslated A component relation from either the original class, or one of its nested components.
	 * @param outerJavaType The outer class being generated.
	 */
	private Type createInnerClassFor(ComponentRelation relationBeingTranslated, Type outerJavaType) throws ChameleonProgrammerException, LookupException {
		Type result = emptyInnerClassFor(relationBeingTranslated, outerJavaType);
		processComponentRelationBody(relationBeingTranslated, result);
		return result;
	}

	private Type emptyInnerClassFor(ComponentRelation relationBeingTranslated, Type outer) throws LookupException {
		incorporateImports(relationBeingTranslated);
		String className = innerClassName(relationBeingTranslated, outer);
		Type result = new RegularJLoType(className);
		for(Modifier mod: relationBeingTranslated.modifiers()) {
			result.addModifier(mod.clone());
		}
		
		TypeReference superReference = superClassReference(relationBeingTranslated);
		superReference.setUniParent(relationBeingTranslated);
		substituteTypeParameters(superReference);
		superReference.setUniParent(null);
		
		result.addInheritanceRelation(new SubtypeRelation(superReference));
		
		List<Method> selectors = selectorsFor(relationBeingTranslated);
		for(Method selector:selectors) {
			result.add(selector);
		}
		processInnerClassMethod(relationBeingTranslated, relationBeingTranslated.referencedComponentType(), result);
		return result;
	}

	/**
	 * Incorporate the imports of the namespace part of the declared type of the component relation to
	 * the namespace part of the component relation.
	 * @param relationBeingTranslated
	 * @throws LookupException
	 */
	private void incorporateImports(ComponentRelation relationBeingTranslated)
	throws LookupException {
		incorporateImports(relationBeingTranslated, relationBeingTranslated.farthestAncestor(NamespacePart.class));
	}

	private void incorporateImports(Method<?,?,?,?> method) throws LookupException {
		Java java = method.language(Java.class);
		NamespacePart namespacePart = method.nearestAncestor(NamespacePart.class);
		for(TypeReference tref: method.descendants(TypeReference.class)) {
			try {
			Type type = tref.getElement().baseType();
			if(type instanceof RegularType && (! type.isTrue(java.PRIMITIVE_TYPE))) {
				TypeReference importRef = java.createTypeReference(type.getFullyQualifiedName());
				namespacePart.addImport(new TypeImport(importRef));
			}
			} catch(LookupException exc) {
				tref.getElement();
			}
		}
	}
	
	/**
	 * Incorporate the imports of the namespace part of the declared type of the component relation to
	 * the given namespace part.
	 * @param relationBeingTranslated
	 * @throws LookupException
	 */
	private void incorporateImports(ComponentRelation relationBeingTranslated, NamespacePart target)
	throws LookupException {
		Type baseT = relationBeingTranslated.referencedComponentType().baseType();
		NamespacePart originalNsp = baseT.farthestAncestor(NamespacePart.class);
		for(Import imp: originalNsp.imports()) {
			target.addImport(imp.clone());
		}
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

	private TypeReference superClassReference(ComponentRelation relation) throws LookupException {
		TypeReference superReference = relation.componentTypeReference().clone();
		if(superReference instanceof ComponentParameterTypeReference) {
			superReference = ((ComponentParameterTypeReference) superReference).componentTypeReference();
		}
		return superReference;
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
	private Method createOutward(Method<?,?,?,?> method, String newName, String className) throws LookupException {
		NormalMethod<?,?,?> result;
		if(//(method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
			 (method.is(method.language(ObjectOrientedLanguage.class).OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, method.name());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			MethodInvocation invocation = invocation(result, newName);
			TypeReference ref = method.language(Java.class).createTypeReference(className);
			ThisLiteral target = new ThisLiteral(ref);
			invocation.setTarget(target);
			substituteTypeParameters(method, result);
			addImplementation(method, body, invocation);
		} else {
			result = null;
		}
		return result;
	}
	
//	private Method createDispathToOriginal(Method<?,?,?,?> method, ComponentRelation relation) throws LookupException {
//		NormalMethod<?,?,?> result = null;
//		result = innerMethod(method, method.name());
//		Block body = new Block();
//		result.setImplementation(new RegularImplementation(body));
//		MethodInvocation invocation = invocation(result, original(method.name()));
//		substituteTypeParameters(method, result);
//		addImplementation(method, body, invocation);
//		return result;
//	}
	
	private void substituteTypeParameters(Method<?, ?, ?, ?> methodInTypeWhoseParametersMustBeSubstituted, NormalMethod<?, ?, ?> methodWhereActualTypeParametersMustBeFilledIn) throws LookupException {
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(methodInTypeWhoseParametersMustBeSubstituted);
		substituteTypeParameters(methodWhereActualTypeParametersMustBeFilledIn);
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(null);
	}

	private void addImplementation(Method<?, ?, ?, ?> method, Block body, MethodInvocation invocation) throws LookupException {
		if(method.returnType().equals(method.language(Java.class).voidType())) {
			body.addStatement(new StatementExpression(invocation));
		} else {
			body.addStatement(new ReturnStatement(invocation));
		}
	}

	private NormalMethod<?, ?, ?> innerMethod(Method<?, ?, ?, ?> method, String original) throws LookupException {
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

	/**
	 * Replace all references to type parameters 
	 * @param element
	 * @throws LookupException
	 */
	private void substituteTypeParameters(Element<?> element) throws LookupException {
		List<TypeReference> crossReferences = 
			element.descendants(TypeReference.class, 
					new UnsafePredicate<TypeReference,LookupException>() {
				public boolean eval(TypeReference object) throws LookupException {
					try {
						return object.getDeclarator() instanceof InstantiatedTypeParameter;
					} catch (LookupException e) {
						e.printStackTrace();
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
				TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type.getFullyQualifiedName());
				childLink.replace(parentLink, namedTargetExpression.parentLink());
			}
	}

	private String innerClassName(Type outer, QualifiedName qn) {
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
	
	private String innerClassName(ComponentRelation relation, Type outer) throws LookupException {
		return innerClassName((Type) outer.origin(), relation.signature()); 
	}
	
	private void replaceSuperCalls(Type type) throws LookupException {
		List<SuperTarget> superTargets = type.descendants(SuperTarget.class, new UnsafePredicate<SuperTarget,LookupException>() {
			@Override
			public boolean eval(SuperTarget superTarget) throws LookupException {
				try {
					return superTarget.getTargetDeclaration() instanceof ComponentRelation;
				} catch(LookupException exc) {
					throw exc;
				}
			}
		}
		);
		for(SuperTarget superTarget: superTargets) {
			Element<?> cr = superTarget.parent();
			if(cr instanceof CrossReferenceWithArguments) {
				Element<?> inv = cr.parent();
				if(inv instanceof RegularMethodInvocation) {
					RegularMethodInvocation call = (RegularMethodInvocation) inv;
					ComponentRelation targetComponent = (ComponentRelation) superTarget.getTargetDeclaration();
					MethodInvocation subObjectSelection = new JavaMethodInvocation(getterName(targetComponent), null);
					call.setTarget(subObjectSelection);
					String name = staticMethodName(call.name(), targetComponent.componentType());
					if(name.equals("radio_SpecialRadio_frequency_setValue")) {
						System.out.println("debug");
						targetComponent = (ComponentRelation) superTarget.getTargetDeclaration();
					}
					call.setName(name);
				}
			}
		}
	}
	
	private MemberVariableDeclarator fieldForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			MemberVariableDeclarator result = new MemberVariableDeclarator(componentTypeReference(relation, outer));
		  result.add(new VariableDeclaration(fieldName(relation)));
		  return result;
		} else {
			return null;
		}
	}

	private BasicJavaTypeReference innerClassTypeReference(ComponentRelation relation, Type outer) throws LookupException {
		return relation.language(Java.class).createTypeReference(innerClassName(relation, outer));
	}
	
	private String getterName(ComponentRelation relation) {
		return getterName(relation.signature().name());
	}
	
	private String getterName(String componentName) {
		return componentName+COMPONENT;
	}

	public final static String COMPONENT = "__component__lkjkberfuncye__";
	
	private Method getterForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			JavaTypeReference returnTypeReference = componentTypeReference(relation, outer);
			RegularMethod result = relation.language(Java.class).createNormalMethod(new SimpleNameDeclarationWithParametersHeader(getterName(relation)), returnTypeReference);
			result.addModifier(new Public());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			body.addStatement(new ReturnStatement(new NamedTargetExpression(fieldName(relation), null)));
			return result;
		} else {
			return null;
		}
	}
	
	private String setterName(ComponentRelation relation) {
		return "set"+COMPONENT+"__"+relation.signature().name();
	}
	
	private Method setterForComponent(ComponentRelation relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			String name = relation.signature().name();
			RegularMethod result = relation.language(Java.class).createNormalMethod(new SimpleNameDeclarationWithParametersHeader(setterName(relation)), relation.language(Java.class).createTypeReference("void"));
			BasicJavaTypeReference tref = componentTypeReference(relation, outer);
			result.header().addFormalParameter(new FormalParameter(name, tref));
			result.addModifier(new Public());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			NamedTargetExpression componentFieldRef = new NamedTargetExpression(fieldName(relation), null);
			componentFieldRef.setTarget(new ThisLiteral());
			body.addStatement(new StatementExpression(new AssignmentExpression(componentFieldRef, new NamedTargetExpression(name, null))));
			return result;
		} else {
			return null;
		}
	}

	private BasicJavaTypeReference componentTypeReference(ComponentRelation relation, Type outer) throws LookupException {
		BasicJavaTypeReference tref = innerClassTypeReference(relation,outer);
		copyTypeParametersFromAncestors(outer,tref);
		transformToInterfaceReference(tref);
		return tref;
	}
	
	private MethodInvocation invocation(Method<?, ?, ?, ?> method, String origin) {
		MethodInvocation invocation = new JavaMethodInvocation(origin, null);
		// pass parameters.
		useParametersInInvocation(method, invocation);
		return invocation;
	}

	private void useParametersInInvocation(Method<?, ?, ?, ?> method, MethodInvocation invocation) {
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(new NamedTargetExpression(param.signature().name(), null));
		}
	}
	
	private String fieldName(ComponentRelation relation) {
		return relation.signature().name();
	}
}
