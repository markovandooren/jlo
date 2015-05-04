package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.NameSelector;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.namespacedeclaration.Import;
import org.aikodi.chameleon.core.namespacedeclaration.NamespaceDeclaration;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.core.reference.CrossReferenceWithTarget;
import org.aikodi.chameleon.core.reference.ElementReference;
import org.aikodi.chameleon.core.reference.NameReference;
import org.aikodi.chameleon.core.tag.TagImpl;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.Implementation;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.method.RegularMethod;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.namespacedeclaration.TypeImport;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.RegularType;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeElement;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.AbstractInheritanceRelation;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.MemberVariable;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.plugin.output.Syntax;
import org.aikodi.chameleon.stub.StubDeclarationContainer;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.expression.SuperTarget;
import org.aikodi.chameleon.support.expression.ThisLiteral;
import org.aikodi.chameleon.support.member.simplename.SimpleNameMethodInvocation;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.member.simplename.method.RegularMethodInvocation;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Constructor;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.component.ComponentParameter;
import org.aikodi.jlo.model.component.ComponentParameterTypeReference;
import org.aikodi.jlo.model.component.FormalComponentParameter;
import org.aikodi.jlo.model.component.Overrides;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.component.SubobjectType;
import org.aikodi.jlo.model.expression.ComponentParameterCall;
import org.aikodi.jlo.model.expression.SubobjectConstructorCall;
import org.aikodi.jlo.model.language.JLo;

import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.ConstructorInvocation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.JavaMethodInvocation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.NonLocalJavaTypeReference;
import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Implements;
import be.kuleuven.cs.distrinet.jnome.core.type.AnonymousInnerClass;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.jnome.core.type.JavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.action.Nothing;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.SafePredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.TypePredicate;
import be.kuleuven.cs.distrinet.rejuse.predicate.UniversalPredicate;

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

  public List<Document> translate(Document source, Document implementationCompilationUnit) throws LookupException, ModelException {
//  	BasicJavaTypeReference.TRACE = true;
  	List<Document> result = new ArrayList<Document>();
  	// Remove a possible old translation of the given compilation unit
  	// from the target model.
  	NamespaceDeclaration originalNamespacePart = source.namespaceDeclarations().get(0);
  	NamespaceDeclaration newNamespacePart = implementationCompilationUnit.namespaceDeclarations().get(0);

  	Iterator<Type> originalTypes = originalNamespacePart.children(Type.class).iterator();
  	Iterator<Type> newTypes = newNamespacePart.children(Type.class).iterator();
  	while(originalTypes.hasNext()) {
  		SingleAssociation newParentLink = newTypes.next().parentLink();
  		Type next = originalTypes.next();
			Type translated = translatedImplementation(next);
  		newParentLink.getOtherRelation().replace(newParentLink, translated.parentLink());
  	}
  	List<? extends Import> explicitImports = originalNamespacePart.explicitImports();
		for(Import imp: explicitImports) {
  		Import clone = Util.clone(imp);
			newNamespacePart.addImport(clone);
  	}
  	implementationCompilationUnit.flushCache();
  	result.add(implementationCompilationUnit);
  	//implementationCompilationUnit.namespacePart(1).getNamespaceLink().lock();
  	List<NamespaceDeclaration> parts = implementationCompilationUnit.descendants(NamespaceDeclaration.class);
  	boolean iface = true;
  	if(iface) {
  		Document interfaceCompilationUnit = interfaceTransformer().interfaceCompilationUnit(source, implementationCompilationUnit);
  		if(interfaceCompilationUnit != null) {
  			parts.addAll(interfaceCompilationUnit.descendants(NamespaceDeclaration.class));
  			result.add(interfaceCompilationUnit);
  		}
  	}
  	for(Type type: implementationCompilationUnit.descendants(Type.class)) {
  		if(type.hasMetadata(REMOVE)) {
  			type.disconnect();
  		}
  	}
  	for(NamespaceDeclaration part: parts) {
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
  private void removeDuplicateImports(NamespaceDeclaration nsp) throws ModelException {
  	Syntax syntax = nsp.language(Java7.class).plugin(Syntax.class);
  	List<? extends Import> imports = nsp.explicitImports();
  	Set<String> importStrings = new HashSet<String>();
  	for(Import imp: imports) {
  		String code = syntax.toCode(imp);
  		if(! importStrings.add(code)) {
  			imp.disconnect();
  		}
  	}
//  	nsp.removeDuplicateImports();
  }
  
	private void replaceStaticCallTargets(Element element) throws LookupException {
		List<MethodInvocation> invocations = element.descendants(MethodInvocation.class);
		for(MethodInvocation invocation: invocations) {
			transformToImplReference(invocation);
		}
		List<NameExpression> tes = element.descendants(NameExpression.class);
		for(NameExpression nte: tes) {
			if(nte.getElement() instanceof MemberVariable) {
				transformToImplReference(nte);
			}
		}
	}
	
	private void replaceConstructorCalls(Element type) throws LookupException {
		List<ConstructorInvocation> invocations = type.descendants(ConstructorInvocation.class);
		Collections.reverse(invocations);
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

	private void replaceThisLiterals(Element type) throws LookupException {
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

	@SuppressWarnings("rawtypes")
	private void replaceSubobjectAccess(Element type) {
		List<CrossReferenceWithTarget> crossReferences = type.nearestDescendants(CrossReferenceWithTarget.class);
		TYPEFILTER.negation().filter(crossReferences);
		for(CrossReferenceWithTarget literal: crossReferences) {
			replaceSubobjectAccessors(literal);
		}
	}
	
	private final static TypePredicate<TypeReference> TYPEFILTER = new TypePredicate<TypeReference>(TypeReference.class);
	
	private final static String SUBOBJECT_READ="SubobjectRead";

	private final static String NO_SUBOBJECT_READ = "NoSubobjectRead";
	
	//JENS
	private void replaceSubobjectAccessors(CrossReferenceWithTarget<?> cwt) {
		Element target = cwt.getTarget();
		for(CrossReferenceWithTarget element: cwt.nearestDescendants(CrossReferenceWithTarget.class)) {
			replaceSubobjectAccessors(element);
		}
		if(! cwt.hasMetadata(NO_SUBOBJECT_READ) && cwt instanceof CrossReferenceWithName) {
			boolean rewrite = cwt.hasMetadata(SUBOBJECT_READ);
			String name = ((CrossReferenceWithName)cwt).name();
			if((! rewrite) && considerForComponentAccessorTransformation(cwt)) {
				try {
					Declaration decl = cwt.getElement();
					if(decl instanceof Subobject || isReadOfFieldCreatedForSubobject(decl,cwt)) {
						rewrite = true;
					}
				}catch(LookupException exc) {
				}
			}
			if(rewrite) {
				String getterName = getterName(name);
				MethodInvocation inv = new JavaMethodInvocation(getterName,(CrossReferenceTarget) target);
				SingleAssociation parentLink = cwt.parentLink();
				parentLink.getOtherRelation().replace(parentLink, inv.parentLink());
			}
		}
	}

	private boolean isReadOfFieldCreatedForSubobject(Declaration decl,CrossReference<?> cref) {
		boolean fieldIsCreatedForSubobject = decl.origin().parent().hasMetadata(SUBOBJECT_READ);
		AssignmentExpression assignment = cref.nearestAncestor(AssignmentExpression.class);
		boolean isRead = (assignment == null) || 
				             (assignment.getVariableExpression() != cref &&
				            	! assignment.getVariableExpression().descendants().contains(cref)); 
		return fieldIsCreatedForSubobject && isRead;
	}

	private boolean considerForComponentAccessorTransformation(CrossReferenceWithTarget<?> cwt) {
		boolean a = ! (cwt instanceof MethodInvocation);
		boolean b = ! (cwt instanceof TypeReference);
		InheritanceRelation nearestInheritanceRelation = cwt.nearestAncestor(InheritanceRelation.class);
		ClassBody nearestClassBody = cwt.nearestAncestor(ClassBody.class);
		boolean c = nearestInheritanceRelation != null || (nearestClassBody != null && nearestClassBody.nearestAncestor(InheritanceRelation.class) == nearestInheritanceRelation);
		SubobjectConstructorCall nearestAncestor = cwt.nearestAncestor(SubobjectConstructorCall.class);
		boolean d = nearestAncestor == null;
		boolean e = nearestAncestor != null && ! nearestAncestor.descendants().contains(cwt);
		boolean f = d || e;
		return a && b && c && f;
	}	

	private void markSubobjectAccess(Element type) {
		List<CrossReferenceWithTarget> literals = type.nearestDescendants(CrossReferenceWithTarget.class);
		for(CrossReferenceWithTarget literal: literals) {
			markSubobjectAccessors(literal);
		}
	}
	
	private void markSubobjectAccessors(CrossReferenceWithTarget<?> cwt) {
		for(Element element: cwt.children()) {
			markSubobjectAccess(element);
		}
		if(considerForComponentAccessorTransformation(cwt)) {
			try {
				Declaration decl = cwt.getElement();
				if(decl instanceof Subobject) {
					cwt.setMetadata(new TagImpl(), SUBOBJECT_READ);
				}
			}catch(LookupException exc) {
			}
		}
	}	


	private void transformToInterfaceReference(ElementReference ref) {
		ElementReference target = (ElementReference) ref.getTarget();
		if(target != null) {
			transformToInterfaceReference(target);
		}
		String name = ref.name();
		if(name.endsWith(IMPL)) {
			ref.setName(interfaceName(name));
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
		List<Subobject> rs = original.directlyDeclaredMembers(Subobject.class);
//		for(ComponentRelation r: rs) {
//			r.parentLink().lock();
//		}
		Type result = Util.clone(original);
//		result.setUniParent(original.parent());
		Java7 lang = original.language(Java7.class);
		if(! original.isTrue(lang.INTERFACE)) {
		StubDeclarationContainer stub = new StubDeclarationContainer();
		stub.add(result);
		stub.setUniParent(original.parent());
		markSubobjectAccess(result);
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
		List<Subobject> relations = result.directlyDeclaredMembers(Subobject.class);
		List<Subobject> originalRelations = original.directlyDeclaredMembers(Subobject.class);
		subobjectConstructorTransformer().replaceSubobjectConstructorCalls(result); // commented out the replaceSubobjectConstructorCalls below
		int size = relations.size();
		for(int i=0; i< size; i++) {
			Subobject relation = relations.get(i);
			// Add a getter for subobject
			Method getterForComponent = getterForSubobject(relation,result);
			if(getterForComponent != null) {
				result.add(getterForComponent);
			}

			// Add a setter for subobject
			Method setterForComponent = setterForSubobject(relation,result);
			if(setterForComponent != null) {
				result.add(setterForComponent);
			}

			// Create the inner classes for the components
			Subobject originalRelation = null;
			int s = originalRelations.size();
			for(int j = 0; j<s;j++) {
				Subobject tmp = originalRelations.get(j);
				if(tmp.name().equals(relation.name())) {
					originalRelation = tmp;
				}
			}
			subobjectToClassTransformer().addInnerClassForSubobject(result, relation, originalRelation);
			flushCache(result);
		}
		for(Subobject relation: result.directlyDeclaredMembers(Subobject.class)) {
			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation,result);
			if(fieldForComponent != null) {
				result.add(fieldForComponent);
				fieldForComponent.setMetadata(new TagImpl(), SUBOBJECT_READ);
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
		replaceSubobjectAccess(result);//N
		
		if(splitClass(result)) {
		  transformToImplRecursive(result);
		}
		
		expandReferences(result); //Y
		
		removeNonLocalReferences(result); //Z
		result.setUniParent(null);
		removeSubobjectParameters(result);
		removeOverridesClauses(result);
		removeSubobjectConstructorCalls(result);
		}
		return result;
	}
	
	private void removeOverridesClauses(Type type) {
		for(Overrides ov: type.descendants(Overrides.class)) {
			ov.disconnect();
		}
	}
	
	private void removeSubobjectConstructorCalls(Type type) {
		for(SubobjectConstructorCall subobectConstructorCall: type.descendants(SubobjectConstructorCall.class)) {
			subobectConstructorCall.parent().disconnect();
		}
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
		Java7 language = result.language(Java7.class);
		if(! result.isTrue(language.INTERFACE)) {
			List<Method> methods =result.directlyDeclaredElements(Method.class);
			boolean hasConstructor = false;
			for(Method method: methods) {
				if(method.isTrue(language.CONSTRUCTOR)) {
					hasConstructor = true;
				}
			}
			if(! hasConstructor) {
				MethodHeader header = new SimpleNameMethodHeader(result.name(), language.createTypeReference(result.name()));
				Method method = language.plugin(ObjectOrientedFactory.class).createNormalMethod(header);
				method.addModifier(new Constructor());
				method.setImplementation(new RegularImplementation(new Block()));
				result.add(method);
			}
		}
	}

	
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,Type original) throws ModelException {
		for(Subobject relation: original.descendants(Subobject.class)) {
			addStaticHooksForMethodsOverriddenInSuperSubobject(result,relation);
		}
	}
	
	private void addStaticHooksForMethodsOverriddenInSuperSubobject(Type result,Subobject relation) throws ModelException {
		Type container = containerOfDefinition(result, relation.farthestAncestor(Type.class), relation.componentType().signature());
		Set<Subobject> overriddenSubobjects = (Set<Subobject>) relation.overriddenMembers();
		Set<Subobject> originsOfOverriddenSubobjects = new HashSet<Subobject>();
		for(Subobject overriddenSubobject: overriddenSubobjects) {
			originsOfOverriddenSubobjects.add((Subobject) overriddenSubobject.origin());
		}
		List<Member> members = relation.componentType().members();
		members.removeAll(relation.componentType().directlyDeclaredMembers());
		Java7 lang = relation.language(Java7.class);
		ChameleonProperty ov = lang.OVERRIDABLE;
		ChameleonProperty def = lang.DEFINED;
		incorporateImports(relation,result.nearestAncestor(NamespaceDeclaration.class));
		for(Member member: members) {
			Element farthestOrigin = member.farthestOrigin();
			Subobject nearestSubobject = member.nearestAncestor(Subobject.class);
			Subobject originOfNearestSubobject = (nearestSubobject == null ? null : (Subobject) nearestSubobject.origin());
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
	private void incorporateImports(Method method) throws LookupException {
		Java7 java = method.language(Java7.class);
		NamespaceDeclaration namespacePart = method.nearestAncestor(NamespaceDeclaration.class);
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
	
  private void createSuperImplementation(Method method, Method original) throws LookupException {
  	Block body = new Block();
  	method.setImplementation(new RegularImplementation(body));
  	MethodInvocation invocation = invocation(method, original.signature().name(),original.language());
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

	private void rebindOverriddenMethodsOf(Type result, Type original, Method method) throws Error, ModelException {
		flushCache(original);
		Util.debug(original.name().equals("NestedRefinedMistunedRadio") && method.name().equals("setValue"));
		
		//FIXME this does not include the setValue of the the intermediate Interval class.
		Set<? extends Member> overridden = method.overriddenMembers();
		Java7 language = method.language(Java7.class);
		if(! overridden.isEmpty()) {
			if(! method.isTrue(language.CONSTRUCTOR) && (method.implementation()!=null)) {
				final Method tmp = Util.clone(method);
				Type containerOfNewDefinition = typeOfDefinition(result,original, method); // OK: SUBOBJECT
				if(containerOfNewDefinition != null) {
					tmp.setUniParent(containerOfNewDefinition);
					DeclarationSelector<Method> selector = new NameSelector<Method>(Method.class) {
						@Override
						protected boolean correctSignature(Declaration declaration)
								throws LookupException {
							return declaration.signature().sameAs(tmp.signature());
						}
						@Override
						public String name() {
							return tmp.name();
						}
					};
					Method newDefinitionInResult = (Method) containerOfNewDefinition.members(selector).get(0).finalDeclaration();
					Method stat = Util.clone(newDefinitionInResult);
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

	private void rebind(Type container, Type original, Method newDefinition, Method toBeRebound) throws ModelException {
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
			Implementation impl = reboundMethod.implementation();
			reboundMethod.setImplementation(null);
			substituteTypeParameters(reboundMethod);
			reboundMethod.setImplementation(impl);
			reboundMethod.setUniParent(null);
			containerOfToBebound.add(reboundMethod);
			
			Method staticReboundMethod = staticMethod(containerOfToBebound, reboundMethod);
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

	private Method staticMethod(Type containerOfToBebound, Method reboundMethod) throws ModelException {
		Method staticReboundMethod = Util.clone(reboundMethod);
		staticReboundMethod.setOrigin(reboundMethod);
		String newName = staticMethodName(reboundMethod,containerOfToBebound);
//		staticReboundMethod.addModifier(new Final());
		ChameleonProperty nat = reboundMethod.language(Java7.class).NATIVE;
		staticReboundMethod.setUniParent(reboundMethod.parent());
		for(Modifier modifier: staticReboundMethod.modifiers(nat)) {
			modifier.disconnect();
		}
		staticReboundMethod.setUniParent(null);
		staticReboundMethod.setName(newName);
		return staticReboundMethod;
	}

	private Type typeOfDefinition(Type container, Type original, Element newDefinition) throws LookupException {
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
    new TypePredicate<Subobject>(Subobject.class).filter(ancestors);
		int size = ancestors.size();
		for(int i=0; i< size; i++) {
			Subobject originalRelation = (Subobject) ancestors.get(size-1-i);
			Subobject newRelation = createOrGetSubobject(result, originalRelation);
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
	
	private Subobject createOrGetSubobject(Type container, Subobject originalRelation) throws LookupException {
		List<Subobject> relations = container.members(Subobject.class);
		Subobject result = null;
		for(Subobject relation: relations) {
			if(isLocallyDefined(relation, container) && relation.signature().sameAs(originalRelation.signature())) {
				result = relation;
				break;
			}
		}
		if((result == null) || (result.nearestAncestor(Type.class) != container)){
			result = Util.clone(originalRelation);
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
	
	private Type levelOfDefinition(Element element) {
		Type result = element.nearestAncestor(new UniversalPredicate<Type,Nothing>(Type.class){
			@Override
			public boolean uncheckedEval(Type object) {
				return ! (object instanceof SubobjectType);
			}});
		return result;
	}

	private Type createOrGetInnerTypeForType(Type container, Type original, Type current, List<Element> elements, int baseOneIndex) throws LookupException {
//			Signature innerName = (new SimpleNameSignature(innerClassName(relationBeingTranslated, original)));
			NameReference<Type> tref = new NameReference<Type>(current.name(), Type.class);
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
	NameReference<Type> tref = new NameReference<Type>(current.name(), Type.class);
	tref.setUniParent(container);
	Type result = tref.getElement();
	return createOrGetSubobject(result, original, elements, baseOneIndex + 1);
}
	private Type createOrGetInnerTypeForComponent(Type container, Type original, Subobject relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
			String innerName = innerClassName(relationBeingTranslated);
			Type result = null;
			List<Type> types = container.directlyDeclaredElements(Type.class);
			for(Type type: types) {
				if(type.name().equals(innerName)) {
					result = type;
				}
			}
			return createOrGetInnerTypeAux(result, original, elements, baseOneIndex + 1);
	}
	
	private Type createOrGetSubobjectForComponent(Type container, Type original, Subobject relationBeingTranslated, List<Element> elements, int baseOneIndex) throws LookupException {
		Subobject relation = createOrGetSubobject(container, relationBeingTranslated);
		Type result = relation.componentType();
		return createOrGetSubobject(result, original, elements, baseOneIndex + 1);
}
	private List<Element> filterAncestors(Element element) {
		SafePredicate<Element> predicate = componentRelationOrNonComponentTypePredicate();
		return element.<Element,Nothing>ancestors(predicate.makeUniversal(Element.class));
	}

	private SafePredicate<Element> componentRelationOrNonComponentTypePredicate() {
		return new SafePredicate<Element>(){
			@Override
			public boolean eval(Element object) {
				return (object instanceof Subobject) || (object instanceof Type && !(object instanceof SubobjectType));
			}};
	}
	
	private Type createOrGetInnerTypeAux(Type container, Type original, List<Element> elements, int baseOneIndex) throws LookupException {
		int index = elements.size()-baseOneIndex;
		if(index >= 0) {
			Element element = elements.get(index);
			if(element instanceof Subobject) {
				return createOrGetInnerTypeForComponent(container,original, (Subobject) element, elements,baseOneIndex);
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
			if(element instanceof Subobject) {
				return createOrGetSubobjectForComponent(container,original, (Subobject) element, elements,baseOneIndex);
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
	
	public final static String REMOVE="REMOVE";

	private void transformToImpl(Type type) throws ModelException {
		JLo lang = type.language(JLo.class);
		if(type.isTrue(lang.INTERFACE)) {
			type.setMetadata(new TagImpl(), REMOVE);
		}
//		if(! type.isTrue(lang.PRIVATE)) {
			// Change the name of the outer type.
			// What a crappy code. I would probably be easier to not add IMPL
			// to the generated subobject class in the first place, but add
			// it afterwards.
			String oldName = type.name();
			String name = oldName;
			if(! name.endsWith(IMPL)) {
				name = name +IMPL;
				type.setName(name);
			}
			for(SubtypeRelation relation: type.nonMemberInheritanceRelations(SubtypeRelation.class)) {
				transformToImplReference(relation.superClassReference());
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




	private void processSuperComponentParameters(AbstractInheritanceRelation relation) throws LookupException {
		TypeReference tref = relation.superClassReference();
		Type type = relation.nearestAncestor(Type.class);
		if(tref instanceof ComponentParameterTypeReference) {
			ComponentParameterTypeReference ctref = (ComponentParameterTypeReference) tref;
			Type ctype = tref.getElement();
			type.addAll(selectorCreator().selectorsForComponent(ctype));
			relation.setSuperClassReference(ctref.componentTypeReference());
		}
	}

	private void removeNonLocalReferences(Type type) throws LookupException {
		for(NonLocalJavaTypeReference tref: type.descendants(NonLocalJavaTypeReference.class)) {
			SingleAssociation parentLink = tref.parentLink();
			TypeReference actualReference = tref.actualReference();
			if(tref.hasMetadata(IMPL)) {
				actualReference.setMetadata(new TagImpl(), IMPL);
			}
			parentLink.getOtherRelation().replace(parentLink, actualReference.parentLink());
		}
	}

	private Method createOutward(Method method, String newName, String className) throws LookupException {
		NormalMethod result;
		View view = method.view();
		Java7 java = view.language(Java7.class);
		if(//(method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
			 (method.is(java.OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, method.name());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			MethodInvocation invocation = invocation(result, newName,java);
			TypeReference ref = java.createTypeReference(className);
			ref = java.createNonLocalTypeReference(ref, view.namespace());
			ThisLiteral target = new ThisLiteral(ref);
			invocation.setTarget(target);
			substituteTypeParameters(method, result);
			addImplementation(method, body, invocation);
		} else {
			result = null;
		}
		return result;
	}
	
	private void replaceSuperCalls(Type type) throws LookupException {
		List<SuperTarget> superTargets = type.descendants(SuperTarget.class, new AbstractPredicate<SuperTarget,LookupException>() {
			@Override
			public boolean eval(SuperTarget superTarget) throws LookupException {
				try {
					return superTarget.getTargetDeclaration() instanceof Subobject;
				} catch(LookupException exc) {
					throw exc;
				}
			}
		}
		);
		for(SuperTarget superTarget : superTargets) {
			Element cr = superTarget.parent();
			if(cr instanceof RegularMethodInvocation) {
					RegularMethodInvocation call = (RegularMethodInvocation) cr;
					Subobject targetComponent = (Subobject) superTarget.getTargetDeclaration();
					Method invoked = call.getElement();
					List<Subobject> trail = new ArrayList<Subobject>();
					trail.add(targetComponent);
					Element el = invoked;
					while(el.origin() != el) {
						Element previous = el;
						el = el.origin();
						if(el.ancestors().contains(previous.nearestAncestor(Type.class))) {
							List<Subobject> previousAncestors = previous.ancestors(Subobject.class);
							List<Subobject> currentAncestors = el.ancestors(Subobject.class);
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
					for(Subobject rel: trail) {
						subobjectSelection = new JavaMethodInvocation(getterName(rel), subobjectSelection);
					}
					Method farthestOrigin = (Method) invoked.farthestOrigin();
					call.setTarget(subobjectSelection);
					String name = staticMethodName(call.name(), farthestOrigin.nearestAncestor(Type.class));
					call.setName(name);
			}
		}
	}

	
	private MemberVariableDeclarator fieldForComponent(Subobject relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			MemberVariableDeclarator result = new MemberVariableDeclarator(componentTypeReference(relation, outer));
		  result.add(new VariableDeclaration(fieldName(relation)));
		  return result;
		} else {
			return null;
		}
	}

	private Method getterForSubobject(Subobject relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			JavaTypeReference returnTypeReference = componentTypeReference(relation, outer);
			RegularMethod result = relation.language(Java7.class).plugin(ObjectOrientedFactory.class).createNormalMethod(new SimpleNameMethodHeader(getterName(relation), returnTypeReference));
			result.addModifier(new Public());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			ExpressionFactory expressionFactory = relation.language().plugin(ExpressionFactory.class);
			NameExpression fieldAccessor = expressionFactory.createNameExpression(fieldName(relation), null);
			fieldAccessor.setMetadata(new TagImpl(), NO_SUBOBJECT_READ);
			body.addStatement(new ReturnStatement(fieldAccessor));
			return result;
		} else {
			return null;
		}
	}
	
	private Method setterForSubobject(Subobject relation, Type outer) throws LookupException {
		if(relation.overriddenMembers().isEmpty()) {
			String name = relation.name();
			Java7 language = relation.language(Java7.class);
			RegularMethod result = language.plugin(ObjectOrientedFactory.class).createNormalMethod(new SimpleNameMethodHeader(setterName(relation), relation.language(Java7.class).createTypeReference("void")));
			BasicJavaTypeReference tref = componentTypeReference(relation, outer);
			result.header().addFormalParameter(new FormalParameter(name, tref));
			result.addModifier(new Public());
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			ExpressionFactory expressionFactory = language.plugin(ExpressionFactory.class);
			NameExpression componentFieldRef = expressionFactory.createNameExpression(fieldName(relation));
			componentFieldRef.setTarget(new ThisLiteral());
			body.addStatement(new StatementExpression(new AssignmentExpression(componentFieldRef, expressionFactory.createNameExpression(name))));
			return result;
		} else {
			return null;
		}
	}

	private BasicJavaTypeReference componentTypeReference(Subobject relation, Type outer) throws LookupException {
		BasicJavaTypeReference tref = innerClassTypeReference(relation);
		copyTypeParametersFromAncestors(outer,tref);
		transformToInterfaceReference(tref);
		return tref;
	}
	
	private String fieldName(Subobject relation) {
		return relation.name();
	}
}
