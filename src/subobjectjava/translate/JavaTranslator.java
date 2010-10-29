package subobjectjava.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.NonLocalJavaTypeReference;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaTypeReference;

import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;
import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.model.component.AbstractClause;
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
import subobjectjava.model.expression.OuterTarget;
import subobjectjava.model.expression.SubobjectConstructorCall;
import subobjectjava.model.language.SubobjectJavaOverridesRelation;
import chameleon.core.declaration.CompositeQualifiedName;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.expression.Expression;
import chameleon.core.expression.Invocation;
import chameleon.core.expression.NamedTarget;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.member.Member;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.method.RegularMethod;
import chameleon.core.method.exception.ExceptionClause;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.reference.SimpleReference;
import chameleon.core.statement.Block;
import chameleon.core.variable.FormalParameter;
import chameleon.core.variable.VariableDeclaration;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.BasicTypeReference;
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
import chameleon.oo.type.generics.TypeParameterSubstitution;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.SuperConstructorDelegation;
import chameleon.support.expression.SuperTarget;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.SimpleNameMethodHeader;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.member.simplename.SimpleNameMethodSignature;
import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.method.RegularMethodInvocation;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.modifier.Protected;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.variable.LocalVariableDeclarator;
import chameleon.util.Util;

public class JavaTranslator {

	/**
	 * Return a type that represents the translation of the given JLow class to a Java class.
	 */
	public Type translation(Type original) throws ChameleonProgrammerException, LookupException {
		Type type = original.clone();
		type.setUniParent(original.parent());
		List<ComponentRelation> relations = original.directlyDeclaredMembers(ComponentRelation.class);
		for(ComponentRelation relation : relations) {
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
			
			// Create the inner classes for the components
			inner(type, relation, type,original);
      type.flushCache();
  		addOutwardDelegations(relation, type);
  		type.flushCache();
		}
		replaceSuperCalls(type);
		for(ComponentRelation relation: type.directlyDeclaredMembers(ComponentRelation.class)) {
//			replaceSuperCalls(relation, type);
			replaceConstructorCalls(relation);

			MemberVariableDeclarator fieldForComponent = fieldForComponent(relation,type);
			if(fieldForComponent != null) {
			  type.add(fieldForComponent);
			}
			
			relation.disconnect();
		}
		
		List<Method> decls = selectorsFor(type);
		for(Method decl:decls) {
			type.add(decl);
		}

		List<ComponentParameterCall> calls = type.descendants(ComponentParameterCall.class);
		for(ComponentParameterCall call: calls) {
			FormalComponentParameter parameter = call.getElement();
			Invocation expr = new JavaMethodInvocation(selectorName(parameter),null);
			expr.addArgument((Expression) call.target());
			SingleAssociation pl = call.parentLink();
			pl.getOtherRelation().replace(pl, expr.parentLink());
		}
		for(InheritanceRelation rel: type.inheritanceRelations()) {
			processSuperComponentParameters(rel);
		}
		
		expandReferences(type);
		removeNonLocalReferences(type);
		type.setUniParent(null);
		
		// Remove non local references
		return type;
	}
	
	protected void processSuperComponentParameters(InheritanceRelation<?> relation) throws LookupException {
		TypeReference tref = relation.superClassReference();
		Type type = relation.nearestAncestor(Type.class);
		if(tref instanceof ComponentParameterTypeReference) {
			ComponentParameterTypeReference ctref = (ComponentParameterTypeReference) tref;
			Type ctype = tref.getType();
			type.addAll(selectorsForComponent(ctype));
			relation.setSuperClassReference(ctref.componentTypeReference());
		}
	}

	protected void removeNonLocalReferences(Type type) throws LookupException {
		for(NonLocalJavaTypeReference tref: type.descendants(NonLocalJavaTypeReference.class)) {
			SingleAssociation<NonLocalJavaTypeReference, Element> parentLink = tref.parentLink();
			parentLink.getOtherRelation().replace(parentLink, tref.actualReference().parentLink());
		}
	}

	protected void expandReferences(Element<?,?> type) throws LookupException {
		for(BasicJavaTypeReference tref: type.descendants(BasicJavaTypeReference.class)) {
			if(tref.getTarget() == null) {
				try {
					// Filthy hack, should add meta information to such references, and use that instead.
					if(! tref.signature().name().contains(SHADOW)) {
						String fullyQualifiedName = tref.getElement().getFullyQualifiedName();
						String predecessor = Util.getAllButLastPart(fullyQualifiedName);
						if(predecessor != null) {
							NamedTarget nt = new NamedTarget(predecessor);
							tref.setTarget(nt);
						}
					}
				} catch(LookupException exc) {
					// This occurs because a generated element cannot be resolved in the original model. E.g.
					// an inner class of another class than the one that has been generated.
				}
			}
		}
	}
	
	protected List<Method> selectorsFor(ComponentRelation rel) throws LookupException {
		Type t = rel.componentType();
		return selectorsForComponent(t);
	}

	public List<Method> selectorsForComponent(Type t) throws LookupException {
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
	
	protected Method realSelectorFor(InstantiatedComponentParameter<?> par) throws LookupException {
		SimpleNameMethodHeader header = new SimpleNameMethodHeader(selectorName(par));
		FormalComponentParameter formal = par.formalParameter();
		Java language = par.language(Java.class);
//		Method result = new NormalMethod(header,formal.componentTypeReference().clone());
		Type declarationType = formal.declarationType();
		JavaTypeReference reference = language.reference(declarationType);
		reference.setUniParent(null);
		Method result = new NormalMethod(header,reference);
		result.addModifier(new Protected());
//		result.addModifier(new Abstract());
		header.addFormalParameter(new FormalParameter("argument", formal.containerTypeReference().clone()));
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		NamedTargetExpression nt = new NamedTargetExpression("argument", null);
		ActualComponentArgument arg = par.argument();
		Expression expr;
		if(arg instanceof ComponentNameActualArgument) {
			ComponentNameActualArgument singarg = (ComponentNameActualArgument) arg;
			expr = new NamedTargetExpression(singarg.name(),nt);
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
	
	protected List<Method> selectorsFor(Type type) throws LookupException {
		ParameterBlock<?,ComponentParameter> block = type.parameterBlock(ComponentParameter.class);
		List<Method> result = new ArrayList<Method>();
		if(block != null) {
		  for(ComponentParameter par: block.parameters()) {
		  	result.add(selectorFor((FormalComponentParameter<?>) par));
		  }
		}
		return result;
	}

	protected String selectorName(ComponentParameter<?> par) {
		return "__select$"+ toUnderScore(par.nearestAncestor(Type.class).getFullyQualifiedName())+"$"+par.signature().name();
	}
	
	protected String toUnderScore(String string) {
		return string.replace('.', '_');
	}
	
	protected Method selectorFor(FormalComponentParameter<?> par) throws LookupException {
		SimpleNameMethodHeader header = new SimpleNameMethodHeader(selectorName(par));
		Java language = par.language(Java.class);
//	Method result = new NormalMethod(header,par.componentTypeReference().clone());
	JavaTypeReference reference = language.reference(par.declarationType());
	reference.setUniParent(null);
	Method result = new NormalMethod(header,reference);
		result.addModifier(new Protected());
//		result.addModifier(new Abstract());
		header.addFormalParameter(new FormalParameter("argument", par.containerTypeReference().clone()));
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		ConstructorInvocation cons = new ConstructorInvocation((BasicJavaTypeReference) par.language(Java.class).createTypeReference("java.lang.Error"), null);
		body.addStatement(new ThrowStatement(cons));
		return result;
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
			Invocation inv = new ConstructorInvocation((BasicJavaTypeReference) innerClassTypeReference(relation, type), null);
			// move actual arguments from subobject constructor call to new constructor call. 
			inv.addAllArguments(call.getActualParameters());
			Invocation setterCall = new JavaMethodInvocation(setterName(relation), null);
			setterCall.addArgument(inv);
			SingleAssociation<SubobjectConstructorCall, Element> parentLink = call.parentLink();
			parentLink.getOtherRelation().replace(parentLink, setterCall.parentLink());
		}
	}

	public void inner(Type type, ComponentRelation relation, Type outer, Type outerTypeBeingTranslated) throws LookupException {
		Type innerClass = createInnerClassFor(relation,type,outerTypeBeingTranslated);
		//_innerClassMap.put(relation, type);
		type.add(innerClass);
		Type componentType = relation.componentType();
		for(ComponentRelation nestedRelation: componentType.members(ComponentRelation.class)) {
			// subst parameters
			ComponentRelation clonedNestedRelation = nestedRelation.clone();
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			substituteTypeParameters(clonedNestedRelation);
			inner(innerClass, clonedNestedRelation, outer,outerTypeBeingTranslated);
		}
	}
	
	private Map<ComponentRelation, Type> _innerClassMap = new HashMap<ComponentRelation, Type>();
	
	public void addOutwardDelegations(ComponentRelation relation, Type outer) throws LookupException {
		ConfigurationBlock block = relation.configurationBlock();
		TypeWithBody componentTypeDeclaration = relation.componentTypeDeclaration();
		List<Method> elements;
		if(componentTypeDeclaration != null) {
			elements = componentTypeDeclaration.body().children(Method.class);
		} else {
			elements = new ArrayList<Method>();
		}
		for(ConfigurationClause clause: block.clauses()) {
			if(clause instanceof AbstractClause) {
				
				AbstractClause ov = (AbstractClause)clause;
				QualifiedName qn = ov.oldFqn();
				final QualifiedName poppedName = qn.popped();
				int size = poppedName.length();
				TargetDeclaration container = relation.componentType();
				for(int i = 1; i<= size; i++) {
					final int x = i;
					SelectorWithoutOrder<Declaration> selector = 
						new SelectorWithoutOrder<Declaration>(new SelectorWithoutOrder.SignatureSelector() {
							public Signature signature() {
								return poppedName.elementAt(x);
							}}, Declaration.class);
					
//SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
//					ref.setUniParent(relation.parent());
				    container = (TargetDeclaration) container.targetContext().lookUp(selector);//x ref.getElement();
				}
				final Signature lastSignature = qn.lastSignature();
				SelectorWithoutOrder<Declaration> selector = 
					new SelectorWithoutOrder<Declaration>(new SelectorWithoutOrder.SignatureSelector() {
						public Signature signature() {
							return lastSignature;
						}}, Declaration.class);
				
//				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
//				ref.setUniParent(relation.parent());
				Type targetInnerClass = targetInnerClass(outer, relation, poppedName);
				Declaration decl = container.targetContext().lookUp(selector);
				if(decl instanceof Method) {
					final Method<?,?,?,?> method = (Method<?, ?, ?, ?>) decl;
				  Method original = null;
				  boolean overriddenInSubobject = new UnsafePredicate<Method, LookupException>() {
						public boolean eval(Method object) throws LookupException {
							return object.signature().sameAs(method.signature());
						}
					}.exists(elements);
				  if(! overriddenInSubobject) {
				    original= createOriginal(method, original(method.name()));
				  }
				  if(original != null) {
				  	targetInnerClass.add(original);
				  }
				  Method outward = createOutward(method,((SimpleNameMethodSignature)ov.newSignature()).name(),relation);
				  if(outward != null) {
				  	targetInnerClass.add(outward);
				  }
				  if(ov instanceof RenamingClause) {
				  	outer.add(createAlias(relation, method, ((SimpleNameMethodSignature)ov.newSignature()).name()));
				  }
				}
			}
		}
	}
	
	public Method createAlias(ComponentRelation relation, Method<?,?,?,?> method, String newName) throws LookupException {
		NormalMethod<?,?,?> result;
		result = innerMethod(method, newName);
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		Invocation invocation = invocation(result, original(method.name()));
		TypeReference ref = getRelativeClassName(relation);
		Expression target = new JavaMethodInvocation(getterName(relation), null);
		invocation.setTarget(target);
		substituteTypeParameters(method, result);
		addImplementation(method, body, invocation);
		return result;
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
//		outer.setUniParent(relation.nearestAncestor(Type.class).parent());
		Type result = tref.getElement();
//		outer.setUniParent(null);
		return result;
	}

	/**
	 * 
	 * @param relation A component relation from either the original class, or one of its nested components.
	 * @param outer The outer class being generated.
	 */
	public Type createInnerClassFor(ComponentRelation relation, Type outer, Type outerTypeBeingTranslated) throws ChameleonProgrammerException, LookupException {
		NamespacePart nsp = relation.farthestAncestor(NamespacePart.class);
//		Type parentType = relation.nearestAncestor(Type.class);
		Type componentType = relation.referencedComponentType();
		Type baseT = componentType.baseType();
		NamespacePart originalNsp = baseT.farthestAncestor(NamespacePart.class);
		for(Import imp: originalNsp.imports()) {
			nsp.addImport(imp.clone());
		}
		Type stub = new RegularType(innerClassName(relation, outer));
		for(Modifier mod: relation.modifiers()) {
			stub.addModifier(mod.clone());
		}
		
		TypeReference superReference;
		if(relation.nearestAncestor(Type.class).signature().equals(outer.signature()) && (outer.nearestAncestor(Type.class) == null)) {
		  superReference = relation.componentTypeReference().clone();
		} else {
		   String innerClassName = innerClassName(relation, relation.nearestAncestor(Type.class));
		  superReference = relation.language(Java.class).createTypeReference(innerClassName);
		 }
		if(superReference instanceof ComponentParameterTypeReference) {
			superReference = ((ComponentParameterTypeReference) superReference).componentTypeReference();
		}
		stub.addInheritanceRelation(new SubtypeRelation(superReference));
		
		List<Method> selectors = selectorsFor(relation);
		for(Method selector:selectors) {
			stub.add(selector);
		}
		
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
				clone.setReturnTypeReference(relation.language(Java.class).createTypeReference(name));
				((SimpleNameMethodHeader)clone.header()).setName(name);
				stub.add(clone);
			}
		}
		ComponentType ctype = relation.componentTypeDeclaration();
		if(ctype != null) {
			if(ctype.ancestors().contains(outerTypeBeingTranslated)) {
			ComponentType clonedType = ctype.clone();
			clonedType.setUniParent(relation);
			replaceOuterAndRootTargets(relation,clonedType);
			for(TypeElement typeElement:clonedType.body().elements()) {
				if(typeElement instanceof Declaration) {
					Declaration clonedDeclaration = (Declaration) typeElement;
					clonedDeclaration.setName(original(clonedDeclaration.signature().name()));
				}
				stub.add(typeElement);
			}
			}
		}
		return stub;
	}

	private void replaceOuterAndRootTargets(ComponentRelation rel, TypeElement<?,?> clone) {
		List<AbstractTarget> outers = clone.descendants(AbstractTarget.class);
		for(AbstractTarget o: outers) {
			String name = o.getTargetDeclaration().getName();
			SingleAssociation parentLink = o.parentLink();
			ThisLiteral e = new ThisLiteral();
			e.setTypeReference(new BasicJavaTypeReference(name));
			parentLink.getOtherRelation().replace(parentLink, e.parentLink());
		}
	}
	public final static String SHADOW = "_subobject_";
	
	public Method createOutward(Method<?,?,?,?> method, String newName, ComponentRelation relation) throws LookupException {
		NormalMethod<?,?,?> result;
		if(//(method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
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
		return relation.language(Java.class).createTypeReference(relation.nearestAncestor(Type.class).signature().name());
	}
	
	public Method createOriginal(Method<?,?,?,?> method, String original) throws LookupException {
		NormalMethod<?,?,?> result;
		if(
			 (method.is(method.language(ObjectOrientedLanguage.class).DEFINED) == Ternary.TRUE) && 
			 (method.is(method.language(ObjectOrientedLanguage.class).OVERRIDABLE) == Ternary.TRUE)) {
			result = innerMethod(method, original);
			substituteTypeParameters(method, result);
			Block body = new Block();
			result.setImplementation(new RegularImplementation(body));
			Invocation invocation = invocation(result, method.name());
			invocation.setTarget(new SuperTarget());
			addImplementation(method, body, invocation);
		}
		else {
			result = null;
		}
		return result;
	}

	private void substituteTypeParameters(Method<?, ?, ?, ?> methodInTypeWhoseParametersMustBeSubstituted, NormalMethod<?, ?, ?> methodWhereActualTypeParametersMustBeFilledIn) throws LookupException {
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(methodInTypeWhoseParametersMustBeSubstituted);
		substituteTypeParameters(methodWhereActualTypeParametersMustBeFilledIn);
		methodWhereActualTypeParametersMustBeFilledIn.setUniParent(null);
	}

	private void addImplementation(Method<?, ?, ?, ?> method, Block body, Invocation invocation) throws LookupException {
		if(method.returnType().equals(method.language(Java.class).voidType())) {
			body.addStatement(new StatementExpression(invocation));
		} else {
			body.addStatement(new ReturnStatement(invocation));
		}
	}

	private NormalMethod<?, ?, ?> innerMethod(Method<?, ?, ?, ?> method, String original) throws LookupException {
		NormalMethod<?, ?, ?> result;
		TypeReference returnTypeReference = method.returnTypeReference();
		BasicJavaTypeReference tref = (BasicJavaTypeReference) returnTypeReference.clone();
		String fullyQualifiedName = returnTypeReference.getElement().getFullyQualifiedName();
		String predecessor = null;
		if(! (returnTypeReference.getDeclarator() instanceof TypeParameter)) {
			predecessor = Util.getAllButLastPart(fullyQualifiedName);
		}
		if(predecessor != null) {
			NamedTarget nt = new NamedTarget(predecessor);
			tref.setTarget(nt);
		}
		result = new NormalMethod(method.header().clone(), tref);
		((SimpleNameMethodHeader)result.header()).setName(original);
		ExceptionClause exceptionClause = method.getExceptionClause();
		ExceptionClause clone = (exceptionClause != null ? exceptionClause.clone(): null);
		result.setExceptionClause(clone);
		result.addModifier(new Public());
		return result;
	}


	public void substituteTypeParameters(Element<?, ?> element) throws LookupException {
		List<TypeReference> crossReferences = 
			 element.descendants(TypeReference.class, 
					              new UnsafePredicate<TypeReference,LookupException>() {
	
													@Override
													public boolean eval(TypeReference object) throws LookupException {
//														return object.getElement().sameAs(selectionDeclaration());
														return object.getDeclarator() instanceof InstantiatedTypeParameter;
													}
				 
			                  });
		for(TypeReference cref: crossReferences) {
			SingleAssociation parentLink = cref.parentLink();
			Association childLink = parentLink.getOtherRelation();
			InstantiatedTypeParameter declarator = (InstantiatedTypeParameter) cref.getDeclarator(); 
//			TypeReference namedTargetExpression = declarator.argument().substitutionReference().clone();
			Type type = cref.getElement();
			while(type instanceof ActualType) {
				type = ((ActualType)type).aliasedType();
			}
			TypeReference namedTargetExpression = element.language(ObjectOrientedLanguage.class).createTypeReference(type.getFullyQualifiedName());
			childLink.replace(parentLink, namedTargetExpression.parentLink());
		}
	}

	public void substituteTypeParameters(Element<?, ?> result, Type type) throws LookupException {
		List<TypeParameter> typeParameters = type.parameters(TypeParameter.class);
		List<TypeParameterSubstitution> substitutions = new ArrayList<TypeParameterSubstitution>();
		for(TypeParameter par: typeParameters) {
			if(par instanceof InstantiatedTypeParameter) {
				substitutions.add(((InstantiatedTypeParameter)par).substitution(result));
			}
		}
		for(TypeParameterSubstitution substitution: substitutions){
			substitution.apply();
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
	
	public void replaceSuperCalls(Type type) throws LookupException {
		List<SuperTarget> superTargets = type.descendants(SuperTarget.class, new UnsafePredicate<SuperTarget,LookupException>() {

			@Override
			public boolean eval(SuperTarget superTarget) throws LookupException {
//				return (superTarget.getTarget() != null) && ((NamedTarget)superTarget.getTarget()).getElement() instanceof ComponentRelation;
				return superTarget.getTargetDeclaration() instanceof ComponentRelation;
			}
			
		}
		);
		for(SuperTarget superTarget: superTargets) {
			Element<?,?> inv = superTarget.parent();
			if(inv instanceof RegularMethodInvocation) {
				RegularMethodInvocation call = (RegularMethodInvocation) inv;
			  Invocation subObjectSelection = new JavaMethodInvocation(getterName((ComponentRelation) superTarget.getTargetDeclaration()), null);
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
			MemberVariableDeclarator result = new MemberVariableDeclarator(innerClassTypeReference(relation, outer));
		  result.add(new VariableDeclaration(fieldName(relation)));
		  return result;
		} else {
			return null;
		}
	}

	private JavaTypeReference innerClassTypeReference(ComponentRelation relation, Type outer) throws LookupException {
		return relation.language(Java.class).createTypeReference(innerClassName(relation, outer));
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
		RegularMethod result = new NormalMethod(new SimpleNameMethodHeader(setterName(relation)), relation.language(Java.class).createTypeReference("void"));
		result.header().addFormalParameter(new FormalParameter(name, innerClassTypeReference(relation,outer)));
		result.addModifier(new Protected());
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
	public Method aliasFor(Member<?,?,?,?> member, ComponentRelation relation) throws LookupException{
		Java lang = member.language(Java.class);
		if(member instanceof Method) {
			Method<?,?,?,?> method = (Method) member;
			Method<?,?,?,?> origin = (Method) method.origin();
			String methodName = fieldName(relation);
			Method result = new NormalMethod(method.header().clone(), lang.createTypeReference(method.returnType().getFullyQualifiedName()));
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
		Invocation invocation = new JavaMethodInvocation(origin, null);
		// pass parameters.
		useParametersInInvocation(method, invocation);
		return invocation;
	}

	private void useParametersInInvocation(Method<?, ?, ?, ?> method, Invocation invocation) {
		for(FormalParameter param: method.formalParameters()) {
			invocation.addArgument(new NamedTargetExpression(param.signature().name(), null));
		}
	}
	
	public String fieldName(ComponentRelation relation) {
		return relation.signature().name();
	}
	
	
}
