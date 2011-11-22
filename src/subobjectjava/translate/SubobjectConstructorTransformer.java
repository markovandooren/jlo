package subobjectjava.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import jnome.core.expression.invocation.ConstructorDelegation;
import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.SuperConstructorDelegation;
import jnome.core.expression.invocation.ThisConstructorDelegation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.association.SingleAssociation;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.expression.SubobjectConstructorCall;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.expression.Expression;
import chameleon.oo.expression.MethodInvocation;
import chameleon.oo.expression.NamedTargetExpression;
import chameleon.oo.member.DeclarationWithParametersHeader;
import chameleon.oo.member.Member;
import chameleon.oo.method.Method;
import chameleon.oo.method.MethodHeader;
import chameleon.oo.method.RegularImplementation;
import chameleon.oo.method.SimpleNameMethodHeader;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.oo.statement.Block;
import chameleon.oo.statement.Statement;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.oo.variable.FormalParameter;
import chameleon.support.expression.ClassCastExpression;
import chameleon.support.expression.ConditionalExpression;
import chameleon.support.expression.NullLiteral;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import chameleon.support.modifier.Abstract;
import chameleon.support.modifier.Public;
import chameleon.support.modifier.Static;
import chameleon.support.statement.IfThenElseStatement;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;

public class SubobjectConstructorTransformer extends AbstractTranslator {

	public void replaceSubobjectConstructorCalls(Type type) throws ModelException {
		if(type.getName().equals("RefinedCounter")) {
			System.out.println("debug");
		}
		Java lang = type.language(Java.class);
		List<Method> constructors = type.descendants(Method.class, lang.CONSTRUCTOR);
		Collections.sort(constructors, new DelegationComparator());
		for(Method constructor: constructors) {
			createStrategyCloneOfConstructor(constructor);
			replaceSubobjectConstructorCalls(constructor, false);
		}
	}
	
	private void createStrategyCloneOfConstructor(Method<?,?,?> constructor) throws ModelException {
		MethodInvocation superCall = superDelegation(constructor);
		Java language = constructor.language(Java.class);
	  Type container = constructor.nearestAncestor(Type.class);
	  Method<?,?,?> clone = constructor.clone();
	  DeclarationWithParametersHeader header = clone.header();
	  boolean added = false;
	  for(ComponentRelation relation: container.members(ComponentRelation.class)) {
		  ClassBody body = relation.nearestAncestor(ClassBody.class);
		  if((body != null) && 
		  		container.subTypeOf(body.nearestAncestor(Type.class))
      ) {
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
			  header.addFormalParameter(new FormalParameter(new SimpleNameSignature(constructorArgumentName(relation)), language.createTypeReference(interfaceName(strategyName(relation)))));
			  String interfaceName;
			  if(cons != null) {
			  	createSpecificStrategy(relation,cons);
			  	interfaceName = interfaceName(defaultStrategyName(relation, cons));
			  } else {
			  	interfaceName = interfaceName(defaultStrategyNameWhenNoLocalSubobjectConstruction(relation, superCall));
			  }
			  header.addFormalParameter(new FormalParameter(new SimpleNameSignature(defaultConstructorArgumentName(relation)), language.createTypeReference(interfaceName)));
			  added = true;
		  }
	  }
	  if(added) {
	  	container.add(clone);
	  	replaceSubobjectConstructorCalls(clone, true);
	  }
  }

	private String defaultConstructorArgumentName(ComponentRelation relation)
			throws LookupException {
		return "default"+constructorArgumentName(relation);
	}

	private String constructorArgumentName(ComponentRelation relation) throws LookupException {
		return "strategyFor"+toUnderScore(relation.componentType().getFullyQualifiedName());
	}
	
	private void createStrategy(ComponentRelation relation) throws LookupException {
		String name = strategyName(relation);
		Type type = relation.nearestAncestor(Type.class);
		if(strategyDoesNotExistFor(relation,strategyName(relation))) {
			String componentTypeName = interfaceName(relation.componentType().getFullyQualifiedName());
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, language.createTypeReference(componentTypeName));
			TypeReference typeRef = language.createTypeReference("java.lang.Object");
			header.addFormalParameter(new FormalParameter(new SimpleNameSignature("object"), typeRef));
			Method constructor = language.createNormalMethod(header);
			constructor.setImplementation(null);
			constructor.addModifier(new Abstract());
			strategy.add(constructor);
			strategy.addModifier(new Abstract());
			strategy.addModifier(new Static());
			for(Member<?,?> member: relation.directlyOverriddenMembers()) {
				if(type.subTypeOf(member.nearestAncestor(Type.class))) {
					strategy.addInheritanceRelation(new SubtypeRelation(language.createTypeReference(strategyNameFQN((ComponentRelation) member))));
				}
			}
			type.farthestAncestorOrSelf(Type.class).add(strategy);
		}
	}

	private boolean strategyDoesNotExistFor(ComponentRelation relation, String name) throws LookupException {
		boolean notExists = true;
		for(Member member: relation.nearestAncestor(Type.class).directlyDeclaredMembers()) {
			if(member.signature().name().equals(name)) {
				notExists = false;
				break;
			}
		}
		return notExists;
	}

	private void createSpecificStrategy(ComponentRelation relation, Method<?,?,?> constructor) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		String componentTypeName = interfaceName(relation.componentType().getFullyQualifiedName());
		String name = defaultStrategyName(relation, constructor);

		if(strategyDoesNotExistFor(relation,name)) {
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, language.createTypeReference(componentTypeName));
			Method factoryMethod = language.createNormalMethod(header);
			TypeReference typeRef = language.createTypeReference("java.lang.Object");
			header.addFormalParameter(new FormalParameter(new SimpleNameSignature("objectafrkuscggfjsdk"), typeRef));
			for(FormalParameter param: constructor.formalParameters()) {
				factoryMethod.header().addFormalParameter(param.clone());
			}
			factoryMethod.setImplementation(null);
			factoryMethod.addModifier(new Abstract());
			strategy.add(factoryMethod);
			strategy.addModifier(new Abstract());
			strategy.addModifier(new Static());
			for(TypeParameter<?> parameter: constructor.nearestAncestor(Type.class).parameters(TypeParameter.class)) {
				strategy.addParameter(TypeParameter.class, parameter.clone());
			}
			type.farthestAncestorOrSelf(Type.class).add(strategy);
		}
	}

	private String defaultStrategyName(ComponentRelation relation, Method<?, ?, ?> constructor) throws LookupException {
		String name = strategyName(relation);
		List<Type> parameterTypes;
		try {
			parameterTypes = constructor.formalParameterTypes();
			for(Type t: parameterTypes) {
				name += toUnderScore(t.getName());
			}
			return name;
		} catch (LookupException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private String defaultStrategyNameWhenNoLocalSubobjectConstruction(ComponentRelation relation,	MethodInvocation<?,?> superCall) throws LookupException {
		Java lang = relation.language(Java.class);
		SubobjectConstructorCall currentSubobjectConstructorCall = subobjectConstructorCall(relation, superCall);
		if(currentSubobjectConstructorCall != null) {
			SubobjectConstructorCall subobjectConstructorCall = (SubobjectConstructorCall) currentSubobjectConstructorCall.farthestOrigin();
			SubobjectConstructorCall subobjectConstructorCallX = currentSubobjectConstructorCall.clone();
			Type out = currentSubobjectConstructorCall.nearestAncestor(Type.class);
			Type origin = (Type) out.farthestOrigin();
			subobjectConstructorCallX.setUniParent(origin);
			
				ComponentRelation actuallyConstructedSubobject = subobjectConstructorCall.getTarget().getElement();
				Method cons = subobjectConstructorCall.getElement();
				Type nearestAncestor = superCall.nearestAncestor(Type.class);
				Type originalOuter = (Type) nearestAncestor.farthestOrigin();
				Type erasedTypeOfOriginalSubobject = lang.erasure(subobjectConstructorCall.nearestAncestor(Type.class));
				// do we need to do the actual substitution with subobjectConstructorCall.nearestAncestor(Type.class)
				// instead of its erasure ????
				if(originalOuter.subTypeOf(erasedTypeOfOriginalSubobject)) {
					Method originalCons = cons;
					cons = originalCons.clone();
					Element parent = originalCons.parent();//.origin();
					cons.setUniParent(parent);
					substituteTypeParameters(cons);
					cons.setUniParent(erasedTypeOfOriginalSubobject);
				}
//			}
			return defaultStrategyName(actuallyConstructedSubobject, cons);
		} else {
		  return "ERROR: No subobject constructor was found for subobject: "+relation.componentType().getFullyQualifiedName();
		}
	}

	private SubobjectConstructorCall subobjectConstructorCall(ComponentRelation relation, MethodInvocation<?, ?> superCall)
			throws LookupException {
		SubobjectConstructorCall subobjectConstructorCall = null;
		Set<ComponentRelation> allRelatedSubobjects = (Set<ComponentRelation>) relation.overriddenMembers();
		allRelatedSubobjects.add(relation);
		Method<?,?,?> constructor = null;
		try {
			constructor = superCall.getElement();
		} catch(LookupException exc) {
			superCall.getElement();
			throw exc;
		}
		while(constructor != null && (subobjectConstructorCall == null)) { // && constructor.nearestAncestor(Type.class) != type) {
		  for(SubobjectConstructorCall call: constructor.descendants(SubobjectConstructorCall.class)) {
		  	ComponentRelation subobject = call.getTarget().getElement();
		  	boolean contains = false;
		  	for(ComponentRelation sub: allRelatedSubobjects) {
		  		if((subobject.signature().sameAs(sub.signature())) && (subobject.nearestAncestor(Type.class).sameAs(sub.nearestAncestor(Type.class)))) {
		  			contains = true;
		  			break;
		  		}
		  	}
		  	if(contains) {
					subobjectConstructorCall = call;
		  		break;
		  	}
		  }
		  // If we haven't found the appropriate constructor yet, we continue searching in the super class hierarchy by following
		  // the super constructor delegations.
			if(subobjectConstructorCall == null) {
				constructor = superDelegation(constructor).getElement();
			}
		}
		return subobjectConstructorCall;
	}


	private String strategyName(ComponentRelation relation) throws LookupException {
		String name = toUnderScore(relation.componentType().getFullyQualifiedName()) + STRATEGY;
		return name;
	}
	
	private String strategyNameFQN(ComponentRelation relation) throws LookupException {
		return relation.farthestAncestor(Type.class).getFullyQualifiedName()+"."+ strategyName(relation); 
	}
	
	public final String STRATEGY = "_constructor";

	private SuperConstructorDelegation superDelegation(Method<?,?,?> constructor) throws LookupException {
		Method<?,?,?> consch = constructor;
		SuperConstructorDelegation superCall = null;
		while(superCall == null) {
			superCall = delegation(consch,SuperConstructorDelegation.class);
			if(superCall == null) {
				ConstructorDelegation thisDelegation = delegation(consch,ThisConstructorDelegation.class);
				if(thisDelegation == null) {
					superCall = new SuperConstructorDelegation();
					Block body = ((RegularImplementation)consch.implementation()).getBody();
					body.addInFront(new StatementExpression(superCall));
				} else {
					consch = thisDelegation.getElement();
				}
			}
		}
		return superCall;
	}
	
	public static class DelegationComparator implements Comparator<Method> {

		//INEFFICIENT
	@Override
	public int compare(Method first, Method second) {
		try {
			if(constructorChain(first).contains(second)) {
				return -1;
			} else if (constructorChain(second).contains(first)) {
				return 1;
			} else {
				return 0;
			}
		} catch (LookupException e) {
			throw new ChameleonProgrammerException();
		}
	}
		
	}
	
	public static List<Method> constructorChain(Method method) throws LookupException {
		List<Method> result = new ArrayList<Method>();
		result.add(method);
		ThisConstructorDelegation thisDelegation = delegation(method, ThisConstructorDelegation.class);
		if(thisDelegation != null) {
			Method next = thisDelegation.getElement();
			result.addAll(constructorChain(next));
		}
		return result;
	}
	
	protected static <C extends ConstructorDelegation> C delegation(Method<?,?,?> constructor, Class<C> kind) {
		C result = null;
		Block body = constructor.body();
		if(body.nbStatements() > 0) {
			Statement first = body.statement(1);
			if(first instanceof StatementExpression) {
				Expression expression = ((StatementExpression) first).getExpression();
				if(kind.isInstance(expression)) {
					result = (C) expression; 
				}
			}
		}
		return result;
	}
	
	private void replaceSubobjectConstructorCalls(Method<?,?,?> constructor, boolean clonedConstructor)
	throws ModelException {
		SuperConstructorDelegation superCall = superDelegation(constructor);
		// The method invocation above will guarantee that there is an explicit constructor delegation.
		ConstructorDelegation callToBeModified = (ConstructorDelegation) ((StatementExpression)constructor.body().statement(1)).getExpression();
		Type type = constructor.nearestAncestor(Type.class);
		List<ComponentRelation> members = type.members(ComponentRelation.class);
		List<FormalParameter> formalParameters = constructor.formalParameters();
		int firstStrategyIndex = constructor.nbFormalParameters()- (2*members.size());
		// We need the list of subobjects of the super class because we must pass the strategies in the correct order.
		List<ComponentRelation> superSubobjects = type.getDirectSuperTypes().iterator().next().members(ComponentRelation.class);
		// We store the additional arguments for the super constructor call locally, and add them
		// at the end. Otherwise I have to add a setter to OrderedMultiAssociation, and I am too tired
		// now to do that correctly.
		Expression[] arguments = new Expression[2*superSubobjects.size()];
		int indexInCurrent = firstStrategyIndex;
		for(ComponentRelation relation: members) {
			int relativeIndexInSuper = -1;
			int size = superSubobjects.size();
			for(int i = 0; i < size; i++) {
				if(superSubobjects.get(i).signature().sameAs(relation.signature())) {
					relativeIndexInSuper = 2*i;
				}
			}
			ClassBody body = relation.nearestAncestor(ClassBody.class);
			int levelOfRelation = relation.ancestors(Type.class).size();
			if(body != null && type.subTypeOf(body.nearestAncestor(Type.class))) {
				List<SubobjectConstructorCall> subCalls = constructorCallsOfRelation(relation, constructor);
				if(!isLocallyDefined(relation,type)) {
					if(relativeIndexInSuper == -1) {
						throw new Error();
					}
					if(subCalls.isEmpty()) {
						// Neither strategy is set.
						if(! clonedConstructor) {
							arguments[relativeIndexInSuper] = new NullLiteral();
							arguments[relativeIndexInSuper+1] = new NullLiteral();
						} else {
							arguments[relativeIndexInSuper] = new NamedTargetExpression(formalParameters.get(indexInCurrent).getName());
							arguments[relativeIndexInSuper+1] = new NamedTargetExpression(formalParameters.get(indexInCurrent+1).getName());
						}
					}
				} else if (! relation.overriddenMembers().isEmpty()) {
					// LOCALLY DEFINED & THERE IS A SUPER SUBOBJECT
					// create a strategy that will create the new subobject.
					if(subCalls.isEmpty()) {
						if(! clonedConstructor) {
							arguments[relativeIndexInSuper] = new NullLiteral();
							// add default strategy
							ConstructorInvocation strategy = defaultConstructionStrategy(
									constructor, superCall,
									formalParameters, relation);
							arguments[relativeIndexInSuper+1] = strategy;
						} else {
							arguments[relativeIndexInSuper] = new NamedTargetExpression(formalParameters.get(indexInCurrent).getName());
							ConstructorInvocation strategy = defaultConstructionStrategy(
									constructor, superCall,
									formalParameters, relation);

							Expression arg = new NamedTargetExpression(formalParameters.get(indexInCurrent+1).getName());
							MethodInvocation conditionRight = new InfixOperatorInvocation("==", arg.clone());
							conditionRight.addArgument(new NullLiteral());
							Expression explicitArg = new NamedTargetExpression(formalParameters.get(indexInCurrent).getName());
							MethodInvocation conditionLeft = new InfixOperatorInvocation("==", explicitArg.clone());
							conditionLeft.addArgument(new NullLiteral());
							MethodInvocation condition = new InfixOperatorInvocation("&&", conditionLeft);
							condition.addArgument(conditionRight);
							Expression conditional = new ConditionalExpression(condition, strategy, arg);
							arguments[relativeIndexInSuper+1] = conditional;
						}
					} else {
						if(! clonedConstructor) {
						// add explicit strategy
						arguments[relativeIndexInSuper] = new NullLiteral(); // FIX
						// default is null;
						arguments[relativeIndexInSuper+1] = new NullLiteral();
						} else {
							arguments[relativeIndexInSuper] = new NullLiteral();
							// default is to propagate the argument.
							arguments[relativeIndexInSuper+1] = new NamedTargetExpression(formalParameters.get(indexInCurrent+1).getName());
						}
					}
				} else {
					// Create and set the subobject
					for(SubobjectConstructorCall call: subCalls) {
						MethodInvocation<?,?> inv = new ConstructorInvocation((BasicJavaTypeReference) innerClassTypeReference(relation), null);
						inv.addAllArguments(call.getActualParameters());
						MethodInvocation setterCall = new JavaMethodInvocation(setterName(relation), null);
						setterCall.addArgument(inv);
						if(clonedConstructor) {
							MethodInvocation expression = new InfixOperatorInvocation("==", new NamedTargetExpression(constructorArgumentName(relation)));
							expression.addArgument(new NullLiteral());
							Block ifBlock = new Block();
							
							MethodInvocation nestedExpression = new InfixOperatorInvocation("==", new NamedTargetExpression(defaultConstructorArgumentName(relation)));
							nestedExpression.addArgument(new NullLiteral());
							Block nestedIfBlock = new Block();
							nestedIfBlock.addStatement(new StatementExpression(setterCall));
							Block nestedElseBlock = new Block();
							MethodInvocation<?,?> defaultStrategyCall = setterCall.clone();
							defaultStrategyCall.getActualParameters().get(0).disconnect();
							nestedElseBlock.addStatement(new StatementExpression(defaultStrategyCall));
							MethodInvocation defaultStrategyInvocation = new JavaMethodInvocation(CONSTRUCT, new NamedTargetExpression(defaultConstructorArgumentName(relation)));
							defaultStrategyInvocation.addArgument(new ThisLiteral());
							defaultStrategyCall.addArgument(defaultStrategyInvocation);
							for(Expression arg: inv.getActualParameters()) {
								defaultStrategyInvocation.addArgument(arg.clone());
							}
							Statement ifthenelseDefault = new IfThenElseStatement(nestedExpression, nestedIfBlock, nestedElseBlock);
							
							ifBlock.addStatement(ifthenelseDefault);
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
			indexInCurrent += 2;
		}
		for(Expression argument: arguments) {
			callToBeModified.addArgument(argument);
		}
	}

	private ConstructorInvocation defaultConstructionStrategy(
			Method<?, ?, ?> constructor, 
			SuperConstructorDelegation superCall,
			List<FormalParameter> formalParameters, 
			ComponentRelation relation) throws LookupException {
		Java language = constructor.language(Java.class);
		String strategyName = defaultStrategyNameWhenNoLocalSubobjectConstruction(relation, superCall);
		BasicJavaTypeReference strategyType = language.createTypeReference(strategyName);
		Declaration superElement = relation.nearestAncestor(Type.class).inheritanceRelations().get(0).superElement();
		copyTypeParametersFromAncestors(superElement, strategyType);
		strategyType.setUniParent(superElement);
		substituteTypeParameters(strategyType);
		strategyType.setUniParent(null);
		// Add type parameters.
//		TypeReference componentTypeReference = relation.componentTypeReference().clone();
//		if(componentTypeReference instanceof ComponentParameterTypeReference) {
//			componentTypeReference = ((ComponentParameterTypeReference) componentTypeReference).componentTypeReference();
//		}
//		BasicJavaTypeReference jTref = (BasicJavaTypeReference) componentTypeReference;
//		for(ActualTypeArgument arg: jTref.typeArguments()) {
//			strategyType.addArgument(arg);
//		}
		
		ConstructorInvocation strategy = new ConstructorInvocation(strategyType, null);
		BasicJavaTypeReference returnTypeReference = language.createTypeReference(interfaceName(relation.componentType().getFullyQualifiedName()));
		MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, returnTypeReference);
		header.addFormalParameter(new FormalParameter("o",language.createTypeReference("java.lang.Object")));
		SubobjectConstructorCall subobjectConstructorCall=subobjectConstructorCall(relation,superCall);
		Method<?,?,?> subobjectConstructor = subobjectConstructorCall.getElement();
		for(FormalParameter param: subobjectConstructor.formalParameters()) {
			FormalParameter clone = param.clone();
			clone.setUniParent(param.parent());
			substituteTypeParameters(clone);
			clone.setUniParent(null);
			header.addFormalParameter(clone);
		}
		
		Method method = language.createNormalMethod(header);
		method.addModifier(new Public());
		Block methodBody = new Block();
		BasicJavaTypeReference castTypeReference = language.createTypeReference(toImplName(constructor.nearestAncestor(Type.class).getName()));
		ClassCastExpression cast = new ClassCastExpression(castTypeReference, new NamedTargetExpression("o"));
		BasicJavaTypeReference subobjectTypeReference = language.createTypeReference(toImplName(relation.componentType().getName()));
		
		ConstructorInvocation constructorInvocation = new ConstructorInvocation(subobjectTypeReference, cast);
		for(FormalParameter param: subobjectConstructor.formalParameters()) {
			NamedTargetExpression constructorArgument = new NamedTargetExpression(param.getName());
			constructorInvocation.addArgument(constructorArgument);
		}
		methodBody.addStatement(new ReturnStatement(constructorInvocation));
		method.setImplementation(new RegularImplementation(methodBody));
		ClassBody b = new ClassBody();
		strategy.setBody(b);
		b.add(method);
		return strategy;
	}
	
	private List<SubobjectConstructorCall> constructorCallsOfRelation(
			final ComponentRelation relation, Method<?, ?, ?> constructor)
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
	
	public final String CONSTRUCT = "construct";

	public void addDefaultSubobjectConstructorCalls(Type result) throws LookupException {
		for(ComponentRelation relation: result.directlyDeclaredMembers(ComponentRelation.class)) {
			addDefaultSubobjectConstructorCalls(relation);
		}
	}

	private void addDefaultSubobjectConstructorCalls(ComponentRelation relation) throws LookupException {
		Java lang = relation.language(Java.class);
		Type subobjectType = relation.referencedComponentType();
		List<Method> constructorsOfReferencedSubobjectType = subobjectType.directlyDeclaredMembers(Method.class, lang.CONSTRUCTOR);
		boolean defaultConstructor = constructorsOfReferencedSubobjectType.isEmpty();
		for(Method<?,?,?> cons: constructorsOfReferencedSubobjectType) {
			if(cons.nbFormalParameters() == 0) {
				defaultConstructor = true;
				break;
			}
		}
		if(defaultConstructor) {
			Set<? extends Member> overridden = relation.overriddenMembers();
			boolean differentDeclaredType = overridden.isEmpty();
			for(Member m: overridden) {
				ComponentRelation r = (ComponentRelation) m;
				if(! r.referencedComponentType().sameAs(subobjectType)) {
					differentDeclaredType = true;
				}
			}
			if(differentDeclaredType) {
				Type type = relation.nearestAncestor(Type.class);
				for(Method<?,?,?> cons: type.directlyDeclaredMembers(Method.class, lang.CONSTRUCTOR)) {
					boolean hasThisConstructorDelegation = false;
					for(ThisConstructorDelegation deleg: cons.descendants(ThisConstructorDelegation.class)) {
						if(deleg.nearestAncestor(Method.class) == cons) {
							hasThisConstructorDelegation = true;
						}
					}
					if(! hasThisConstructorDelegation) {
						List<SubobjectConstructorCall> calls = constructorCallsOfRelation(relation, cons);
						if(calls.isEmpty()) {
							SubobjectConstructorCall subobjectConstructorCall = new SubobjectConstructorCall(relation.name());
							Block body = cons.implementation().getBody();
							body.addStatement(new StatementExpression(subobjectConstructorCall));
						}
					}
				}
			}
		}
	}
}
