package subobjectjava.translate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.NonLocalJavaTypeReference;
import jnome.core.language.Java;
import jnome.core.modifier.Implements;
import jnome.core.type.AnonymousInnerClass;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaTypeReference;

import org.rejuse.association.SingleAssociation;
import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.SafePredicate;
import org.rejuse.predicate.TypePredicate;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.model.component.ComponentParameter;
import subobjectjava.model.component.ComponentParameterTypeReference;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ComponentType;
import subobjectjava.model.component.FormalComponentParameter;
import subobjectjava.model.expression.ComponentParameterCall;
import subobjectjava.model.language.JLo;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.DeclarationWithParametersHeader;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.StubDeclarationContainer;
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
import chameleon.core.variable.FormalParameter;
import chameleon.core.variable.MemberVariable;
import chameleon.core.variable.VariableDeclaration;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.NonLocalTypeReference;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.AbstractInheritanceRelation;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.plugin.output.Syntax;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.SuperTarget;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.method.RegularMethodInvocation;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.modifier.Constructor;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.util.Util;

public class JavaTranslator extends AbstractTranslator {
	
	private InterfaceTransformer _interfaceTransformer = new InterfaceTransformer();
	
	public InterfaceTransformer interfaceTransformer() {
		return _interfaceTransformer;
	}
	
	private SubobjectConstructorTransformer _subobjectConstructorTransformer = new SubobjectConstructorTransformer();
	
	public SubobjectConstructorTransformer subobjectConstructorTransformer() {
		return _subobjectConstructorTransformer;
	}

	private SelectorCreator _selectorCreator = new SelectorCreator();
	
	public SelectorCreator selectorCreator() {
		return _selectorCreator;
	}

	private InnerClassCreator _innerClassCreator = new InnerClassCreator(_selectorCreator);
	
	public InnerClassCreator innerClassCreator() {
		return _innerClassCreator;
	}

	private SubobjectToClassTransformer _subobjectToClassTransformer = new SubobjectToClassTransformer(_innerClassCreator,_subobjectConstructorTransformer);
	
	public SubobjectToClassTransformer subobjectToClassTransformer() {
		return _subobjectToClassTransformer;
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
  	for(Import imp: originalNamespacePart.imports()) {
  		newNamespacePart.addImport(imp.clone());
  	}
  	implementationCompilationUnit.flushCache();
  	result.add(implementationCompilationUnit);
  	implementationCompilationUnit.namespacePart(1).getNamespaceLink().lock();
  	List<NamespacePart> parts = implementationCompilationUnit.descendants(NamespacePart.class);
  	boolean iface = true;
  	if(iface) {
  		CompilationUnit interfaceCompilationUnit = interfaceTransformer().interfaceCompilationUnit(source, implementationCompilationUnit);
  		if(interfaceCompilationUnit != null) {
  			parts.addAll(interfaceCompilationUnit.descendants(NamespacePart.class));
  			result.add(interfaceCompilationUnit);
  		}
  	}
  	for(NamespacePart part: parts) {
  		removeDuplicateImports(part);
  	}
  	return result;
  }

  /**
   * Remove duplicate imports by doing a String based comparison based on the textual
   * representation as defined by the syntax of the language. This avoids the need to
   * perform lookups, and thus the need to clone the entire Java API.
   * @param nsp
   * @throws ModelException
   */
  private void removeDuplicateImports(NamespacePart nsp) throws ModelException {
  	Syntax syntax = nsp.language(Java.class).plugin(Syntax.class);
  	List<Import> imports = nsp.imports();
  	Set<String> importStrings = new HashSet<String>();
  	for(Import imp: imports) {
  		String code = syntax.toCode(imp);
  		if(! importStrings.add(code)) {
  			imp.disconnect();
  		}
  	}
//  	nsp.removeDuplicateImports();
  }
  
	private void replaceStaticCallTargets(Element<?> element) throws LookupException {
		List<MethodInvocation> invocations = element.descendants(MethodInvocation.class);
		for(MethodInvocation invocation: invocations) {
			transformToImplReference(invocation);
		}
		List<NamedTargetExpression> tes = element.descendants(NamedTargetExpression.class);
		for(NamedTargetExpression nte: tes) {
			if(nte.getElement() instanceof MemberVariable) {
				transformToImplReference(nte);
			}
		}
	}
	
	private void replaceConstructorCalls(Element<?> type) throws LookupException {
		List<ConstructorInvocation> invocations = type.descendants(ConstructorInvocation.class);
		for(ConstructorInvocation invocation: invocations) {
			try {
				Type constructedType = invocation.getType();
				if(splitClass(constructedType)) {
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
				try {
					typeReference.getElement();
				} catch(LookupException exc) {
				}
			  transformToImplReference(typeReference);
			}
		}
	}

	private void replaceComponentAccess(Element<?> type) {
		List<CrossReferenceWithTarget> literals = type.nearestDescendants(CrossReferenceWithTarget.class);
		for(CrossReferenceWithTarget literal: literals) {
			transformComponentAccessors(literal);
		}
	}

	//JENS
	private void transformComponentAccessors(CrossReferenceWithTarget<?,?> cwt) {
		Element<?> target = cwt.getTarget();
//		if(target instanceof CrossReferenceWithTarget) {
//			transformComponentAccessors((CrossReferenceWithTarget) target);
//		}
		for(Element element: cwt.children()) {
			replaceComponentAccess(element);
		}
		boolean rewrite = false;
		String name = null;
		if((! (cwt instanceof MethodInvocation)) && (! (cwt instanceof TypeReference)) && (cwt.nearestAncestor(InheritanceRelation.class) == null)) {
			name = ((CrossReferenceWithName)cwt).name();
			try {
				Declaration decl = cwt.getElement();
				if(decl instanceof ComponentRelation) {
					rewrite = true;
				}
			}catch(LookupException exc) {
			}
		}
		if(rewrite) {
			String getterName = getterName(name);
			MethodInvocation inv = new JavaMethodInvocation(getterName,(InvocationTarget) target);
			SingleAssociation parentLink = cwt.parentLink();
			parentLink.getOtherRelation().replace(parentLink, inv.parentLink());
		}
	}	

	private void transformToImplReference(CrossReference<?,?> tref) {
		if(tref instanceof SimpleNameMethodInvocation && ((SimpleNameMethodInvocation)tref).name().equals("massert")) {
			System.out.println("debug");
		}
		if(tref instanceof NonLocalTypeReference) {
			transformToImplReference(((NonLocalTypeReference)tref).actualReference());
		} else if(tref instanceof CrossReferenceWithName) {
		CrossReferenceWithName ref = (CrossReferenceWithName) tref;
		if(ref instanceof CrossReferenceWithTarget) {
			
			Element target = ((CrossReferenceWithTarget)ref).getTarget();
			if(target instanceof CrossReference) {
				transformToImplReference((CrossReference<?, ?>) target);
			}
		}
		if(! (ref instanceof MethodInvocation)) {
			
			boolean change;
			try {
				Declaration referencedElement = ref.getElement();
				if(referencedElement instanceof Type && isJLo((NamespaceElement) referencedElement)) {
					change = true;
				} else {
					change = false;
				}
			} catch(LookupException exc) {
				change = true;
			}
			if(change) {
				String name = ref.name();
				if(name.equals("last")) {
					System.out.println("debug");
				}
				if(! name.endsWith(IMPL)) {
					ref.setName(name+IMPL);
				}
				//			Import imp = new TypeImport((TypeReference) ref.clone());
				//			tref.nearestAncestor(NamespacePart.class).addImport(imp);
			}
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
	
	private void flushCache(Type type) {
		type.flushCache();
		type.language().flushCache();
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
		ensureConstructor(result);
		
		result.setOrigin(original);
		replaceSuperCalls(result);
		// Add explicit subobject constructor calls before rebinding the methods. Otherwise, this will generate
		// explicit subobject constructor calls for generated subobjects, which must use the subobject constructor call
		// of the super constructor that corresponds to the overridden subobject.
		subobjectConstructorTransformer().addDefaultSubobjectConstructorCalls(result);
		flushCache(result);
		rebindOverriddenMethods(result,original);
		flushCache(result);
		// must replace the static calls before the constructor because
		// the super class of an anonymous inner class (C_implementation) is not available
		// in the original model, where the lookup is done.
		replaceStaticCallTargets(result);
		replaceConstructorCalls(result);
		flushCache(result);
		List<ComponentRelation> relations = result.directlyDeclaredMembers(ComponentRelation.class);
		subobjectConstructorTransformer().replaceSubobjectConstructorCalls(result); // commented out the replaceSubobjectConstructorCalls below
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
			subobjectToClassTransformer().inner(result, relation);
			flushCache(result);
		}
		for(ComponentRelation relation: result.directlyDeclaredMembers(ComponentRelation.class)) {
			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation,result);
			if(fieldForComponent != null) {
				result.add(fieldForComponent);
			}
			// We must flush the cache because the type reference of the declared subobject type might be in the
			// language cache.
			// IDEA: should an actualtype argument flush the language cache after a disconnect? Or have a check in DEBUG mode
			//       to see if it is used in the language cache? It could be used in other caches though.
			relation.language().flushCache();
			relation.disconnect();
		}
		
		//JENS
		result.addAll(selectorCreator().selectorsFor(result));
		
		replaceConnectorAccess(result);
		for(SubtypeRelation rel: result.nonMemberInheritanceRelations(SubtypeRelation.class)) {
			processSuperComponentParameters(rel);
		}
		addStaticHooksForMethodsOverriddenInSuperSubobject(result,original);

		// The result is still temporarily attached to the original model.
		replaceThisLiterals(result); //M
		replaceComponentAccess(result);//N
		
		if(splitClass(result)) {
		  transformToImplRecursive(result);
		}
		
		expandReferences(result); //Y
		
		removeNonLocalReferences(result); //Z
		result.setUniParent(null);
		removeSubobjectParameters(result);
		return result;
	}
	
	//JENS
	private void replaceConnectorAccess(Type result) throws LookupException {
		List<ComponentParameterCall> calls = result.descendants(ComponentParameterCall.class);
		for(ComponentParameterCall call: calls) {
			FormalComponentParameter parameter = call.getElement();
			MethodInvocation expr = new JavaMethodInvocation(selectorCreator().selectorName(parameter),null);
			expr.addArgument((Expression) call.target());
			SingleAssociation pl = call.parentLink();
			pl.getOtherRelation().replace(pl, expr.parentLink());
		}
	}
	
	private void removeSubobjectParameters(Type type) {
		for(ComponentParameter par: type.descendants(ComponentParameter.class)) {
			par.disconnect();
		}
	}
	
	private void ensureConstructor(Type result) {
		Java language = result.language(Java.class);
		if(! result.isTrue(language.INTERFACE)) {
			List<Method> methods =result.directlyDeclaredElements(Method.class);
			boolean hasConstructor = false;
			for(Method method: methods) {
				if(method.isTrue(language.CONSTRUCTOR)) {
					hasConstructor = true;
				}
			}
			if(! hasConstructor) {
				DeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(result.getName());
				Method method = language.createNormalMethod(header, language.createTypeReference(result.getName()));
				method.addModifier(new Constructor());
				method.setImplementation(new RegularImplementation(new Block()));
				result.add(method);
			}
		}
	}

	
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,Type original) throws ModelException {
		for(ComponentRelation relation: original.descendants(ComponentRelation.class)) {
			addStaticHooksForMethodsOverriddenInSuperSubobject(result,relation);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
//	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,ComponentRelation relation) throws ModelException {
//		Type container = containerOfDefinition(result, relation.farthestAncestor(Type.class), relation.componentType().signature());
//		List<Member> members = relation.componentType().members();
//		members.removeAll(relation.componentType().directlyDeclaredMembers());
//		Java lang = relation.language(Java.class);
//		ChameleonProperty ov = lang.OVERRIDABLE;
//		ChameleonProperty def = lang.DEFINED;
//		incorporateImports(relation,result.nearestAncestor(NamespacePart.class));
//		for(Member member: members) {
//			if(member instanceof Method && member.ancestors().contains(relation) && member.isTrue(ov) && member.isTrue(def) && (!lang.isOperator((Method) member))) {
//				Method newMethod = staticMethod(container, (Method) member);
//				newMethod.setUniParent(member.parent());
//				incorporateImports(newMethod);
//				substituteTypeParameters(newMethod);
//				newMethod.setUniParent(null);
//				createSuperImplementation(newMethod,(Method) member);
//				container.add(newMethod);
//			}
//		}
//	}
	
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,ComponentRelation relation) throws ModelException {
		Type container = containerOfDefinition(result, relation.farthestAncestor(Type.class), relation.componentType().signature());
		Set<ComponentRelation> overriddenSubobjects = (Set<ComponentRelation>) relation.overriddenMembers();
		Set<ComponentRelation> originsOfOverriddenSubobjects = new HashSet<ComponentRelation>();
		for(ComponentRelation overriddenSubobject: overriddenSubobjects) {
			originsOfOverriddenSubobjects.add((ComponentRelation) overriddenSubobject.origin());
		}
		List<Member> members = relation.componentType().members();
		members.removeAll(relation.componentType().directlyDeclaredMembers());
		Java lang = relation.language(Java.class);
		ChameleonProperty ov = lang.OVERRIDABLE;
		ChameleonProperty def = lang.DEFINED;
		incorporateImports(relation,result.nearestAncestor(NamespacePart.class));
		for(Member<?,?> member: members) {
			Element farthestOrigin = member.farthestOrigin();
			ComponentRelation nearestSubobject = member.nearestAncestor(ComponentRelation.class);
			ComponentRelation originOfNearestSubobject = (nearestSubobject == null ? null : (ComponentRelation) nearestSubobject.origin());
			if(member instanceof Method && originsOfOverriddenSubobjects.contains(originOfNearestSubobject) && member.isTrue(ov) && member.isTrue(def) && (!lang.isOperator((Method) member))) {
				Method newMethod = staticMethod(originOfNearestSubobject.componentType(), (Method) member);
				newMethod.setUniParent(member.parent());
				incorporateImports(newMethod);
				substituteTypeParameters(newMethod);
				newMethod.setUniParent(null);
				createSuperImplementation(newMethod,(Method) member);
				container.add(newMethod);
			}
		}
	}
	private void incorporateImports(Method<?,?,?> method) throws LookupException {
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
	
  public void createSuperImplementation(Method<?,?,?> method, Method original) throws LookupException {
  	Block body = new Block();
  	method.setImplementation(new RegularImplementation(body));
  	MethodInvocation invocation = invocation(method, original.signature().name());
  	invocation.setTarget(new SuperTarget());
  	addImplementation(original, body, invocation);
}


	
	private void rebindOverriddenMethods(Type result, Type original) throws ModelException {
		for(final Method method: original.descendants(Method.class)) {
			if(method.nearestAncestor(AnonymousInnerClass.class) == null) {
			  rebindOverriddenMethodsOf(result, original, method);
			}
		}
	}

	private void rebindOverriddenMethodsOf(Type result, Type original, Method<?,?,?> method) throws Error, ModelException {
		flushCache(original);
		Set<? extends Member> overridden = method.overriddenMembers();
		Java language = method.language(Java.class);
		if(! overridden.isEmpty()) {
			if(! method.isTrue(language.CONSTRUCTOR) && (method.implementation()!=null)) {
				final Method tmp = method.clone();
				Type containerOfNewDefinition = typeOfDefinition(result,original, method); // OK: SUBOBJECT
				if(containerOfNewDefinition != null) {
					tmp.setUniParent(containerOfNewDefinition);
					DeclarationSelector<Method> selector = new SelectorWithoutOrder<Method>(Method.class) {
						@Override
						public Signature signature() {
							return tmp.signature();
						}
					};
					Method newDefinitionInResult = containerOfNewDefinition.members(selector).get(0);
					Method<?,?,?> stat = newDefinitionInResult.clone();
					String name = staticMethodName(method, containerOfNewDefinition);
					stat.setName(name);
					containerOfNewDefinition.add(stat);
				}
			}
		}
		for(Member toBeRebound: overridden) {
			rebind(result, original, method, (Method) toBeRebound);
		}
	}

	private void rebind(Type container, Type original, Method<?,?,?> newDefinition, Method toBeRebound) throws ModelException {
		try {
			String newwFQN = newDefinition.nearestAncestor(Type.class).getFullyQualifiedName()+"."+newDefinition.name();
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
			
			Method<?, ?, ?> staticReboundMethod = staticMethod(containerOfToBebound, reboundMethod);
			String name = containerOfNewDefinition.getFullyQualifiedName().replace('.', '_');
			for(SimpleNameMethodInvocation inv:staticReboundMethod.descendants(SimpleNameMethodInvocation.class)) {
				if(! (inv instanceof ConstructorInvocation)) {
					name = toImplName(name);
					inv.setName(staticMethodName(newDefinition, containerOfNewDefinition));
				}
			}
			containerOfToBebound.add(staticReboundMethod);
			flushCache(containerOfToBebound);
		}
		} catch(ModelException exc) {
			throw exc;
		}
	}

	private Method<?, ?, ?> staticMethod(Type containerOfToBebound, Method<?,?,?> reboundMethod) throws ModelException {
		Method<?,?,?> staticReboundMethod = reboundMethod.clone();
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

	private Type createOrGetInnerTypeForType(Type container, Type original, Type current, List<Element> elements, int baseOneIndex) throws LookupException {
//			Signature innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, original)));
			Signature innerName = current.signature().clone();
			SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
			tref.setUniParent(container);
			Type result;
//			try { 
				result= tref.getElement();
//			} catch(LookupException exc) {
//				// We add the imports to the original. They are copied later on to 'container'.
//				ComponentRelation relation = ((ComponentType)current).nearestAncestor(ComponentRelation.class);
//				result = innerClassCreator().emptyInnerClassFor(relation);
//				NamespacePart namespacePart = container.farthestAncestor(NamespacePart.class);
//				incorporateImports(relation, namespacePart);
//				// Since we are adding inner classes that were not written in the current namespacepart, their type
//				// may not have been imported yet. Therefore, we add an import of the referenced component type.
//				namespacePart.addImport(new TypeImport(container.language(Java.class).createTypeReference(relation.referencedComponentType().getFullyQualifiedName())));
//				container.add(result);
//				container.flushCache();
//			}
			return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
	}

	private Type createOrGetSubobjectForType(Type container, Type original, Type current, List<Element> elements, int baseOneIndex) throws LookupException {
	Signature innerName = current.signature().clone();
	SimpleReference<Type> tref = new SimpleReference<Type>(innerName, Type.class);
	tref.setUniParent(container);
	Type result = tref.getElement();
	return createOrGetSubobject(result, original, elements, baseOneIndex + 1);
}
	private Type createOrGetInnerTypeForComponent(Type container, Type original, ComponentRelation relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
			String innerName = innerClassName(relationBeingTranslated);
			Type result = null;
			List<Type> types = container.directlyDeclaredElements(Type.class);
			for(Type type: types) {
				if(type.signature().name().equals(innerName)) {
					result = type;
				}
			}
			return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
	}
	
	private Type createOrGetSubobjectForComponent(Type container, Type original, ComponentRelation relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
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
				transformToImplReference(relation.superClassReference());
//				BasicJavaTypeReference tref = (BasicJavaTypeReference) relation.superClassReference();
//				try {
//					Type referencedType = relation.superClassReference().getElement();
//					if((! relation.isTrue(lang.IMPLEMENTS_RELATION)) && isJLo(referencedType)) {
//						tref.setSignature(new SimpleNameSignature(tref.signature().name()+IMPL));
//					}
//				}
//				catch(LookupException exc) {
//					tref.setSignature(new SimpleNameSignature(tref.signature().name()+IMPL));
//				}
			}
			implementOwnInterface(type);
			for(TypeElement decl: type.directlyDeclaredElements()) {
				if((decl instanceof Method) && (decl.is(lang.CONSTRUCTOR) == Ternary.TRUE)) {
					((Method)decl).setName(name);
				}
				if(decl instanceof Member || decl instanceof MemberVariableDeclarator) {
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




	private void processSuperComponentParameters(AbstractInheritanceRelation<?> relation) throws LookupException {
		TypeReference tref = relation.superClassReference();
		Type type = relation.nearestAncestor(Type.class);
		if(tref instanceof ComponentParameterTypeReference) {
			ComponentParameterTypeReference ctref = (ComponentParameterTypeReference) tref;
			Type ctype = tref.getType();
			type.addAll(selectorCreator().selectorsForComponent(ctype));
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
					String name = tref.signature().name();
					if(! name.contains(SHADOW)) {
						Type element = null;
						if(name.contains(IMPL)) {
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
								tref.setTarget(new NamedTarget(predecessor));
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
	
	private Method createOutward(Method<?,?,?> method, String newName, String className) throws LookupException {
		NormalMethod<?,?,?> result;
		Java java = method.language(Java.class);
		if(//(method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
			 (method.is(java.OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, method.name());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			MethodInvocation invocation = invocation(result, newName);
			TypeReference ref = java.createTypeReference(className);
			ref = java.createNonLocalTypeReference(ref, java.defaultNamespace());
			ThisLiteral target = new ThisLiteral(ref);
			invocation.setTarget(target);
			substituteTypeParameters(method, result);
			addImplementation(method, body, invocation);
		} else {
			result = null;
		}
		return result;
	}
	
//	private void replaceSuperCalls(Type type) throws LookupException {
//		List<SuperTarget> superTargets = type.descendants(SuperTarget.class, new UnsafePredicate<SuperTarget,LookupException>() {
//			@Override
//			public boolean eval(SuperTarget superTarget) throws LookupException {
//				try {
//					return superTarget.getTargetDeclaration() instanceof ComponentRelation;
//				} catch(LookupException exc) {
//					throw exc;
//				}
//			}
//		}
//		);
//		for(SuperTarget superTarget: superTargets) {
//			Element<?> cr = superTarget.parent();
//			if(cr instanceof CrossReferenceWithArguments) {
//				Element<?> inv = cr.parent();
//				if(inv instanceof RegularMethodInvocation) {
//					RegularMethodInvocation call = (RegularMethodInvocation) inv;
//					ComponentRelation targetComponent = (ComponentRelation) superTarget.getTargetDeclaration();
//					MethodInvocation subObjectSelection = new JavaMethodInvocation(getterName(targetComponent), null);
//					call.setTarget(subObjectSelection);
//					String name = staticMethodName(call.name(), targetComponent.componentType());
//					call.setName(name);
//				}
//			}
//		}
//	}
	
	public void replaceSuperCalls(Type type) throws LookupException {
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
		for(SuperTarget superTarget : superTargets) {
			Element<?> cr = superTarget.parent();
			if(cr instanceof CrossReferenceWithArguments) {
				Element<?> inv = cr.parent();
				if(inv instanceof RegularMethodInvocation) {
					RegularMethodInvocation call = (RegularMethodInvocation) inv;
					ComponentRelation targetComponent = (ComponentRelation) superTarget.getTargetDeclaration();
					Method<?,?,?> invoked = call.getElement();
					List<ComponentRelation> trail = new ArrayList<ComponentRelation>();
					trail.add(targetComponent);
					Element el = invoked;
					while(el.origin() != el) {
						Element previous = el;
						el = el.origin();
						if(el.ancestors().contains(previous.nearestAncestor(Type.class))) {
							List<ComponentRelation> previousAncestors = previous.ancestors(ComponentRelation.class);
							List<ComponentRelation> currentAncestors = el.ancestors(ComponentRelation.class);
							currentAncestors.removeAll(previousAncestors);
							int size = currentAncestors.size();
							if(size > 0) {
								for(int i = size-1; i>=0;i--) {
									trail.add(currentAncestors.get(i));
								}
							}
						}
					}
//					Method<?,?,?,?> farthestIncorporatedOrigin = invoked;
//					while(farthestIncorporatedOrigin.origin().ancestors().contains(type)) {
//						farthestIncorporatedOrigin = (Method<?, ?, ?, ?>) farthestIncorporatedOrigin.origin();
//					}
					
					
					MethodInvocation subobjectSelection = null;
					for(ComponentRelation rel: trail) {
						subobjectSelection = new JavaMethodInvocation(getterName(rel), subobjectSelection);
					}
					Method<?,?,?> farthestOrigin = (Method) invoked.farthestOrigin();
					call.setTarget(subobjectSelection);
					String name = staticMethodName(call.name(), farthestOrigin.nearestAncestor(Type.class));
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
		BasicJavaTypeReference tref = innerClassTypeReference(relation);
		copyTypeParametersFromAncestors(outer,tref);
		transformToInterfaceReference(tref);
		return tref;
	}
	
	private String fieldName(ComponentRelation relation) {
		return relation.signature().name();
	}
}
