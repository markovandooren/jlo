package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.DeclarationWithParametersHeader;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.statement.Statement;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.support.expression.ClassCastExpression;
import org.aikodi.chameleon.support.expression.ConditionalExpression;
import org.aikodi.chameleon.support.expression.NullLiteral;
import org.aikodi.chameleon.support.expression.ThisLiteral;
import org.aikodi.chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.modifier.Static;
import org.aikodi.chameleon.support.statement.IfThenElseStatement;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.expression.SubobjectConstructorCall;

import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.ConstructorDelegation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.ConstructorInvocation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.JavaMethodInvocation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.SuperConstructorDelegation;
import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.ThisConstructorDelegation;
import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;
import be.kuleuven.cs.distrinet.rejuse.predicate.AbstractPredicate;

public class SubobjectConstructorTransformer extends AbstractTranslator {

	public void replaceSubobjectConstructorCalls(Type type) throws ModelException {
		Java7 lang = type.language(Java7.class);
		List<Method> constructors = type.descendants(Method.class, lang.CONSTRUCTOR);
		Collections.sort(constructors, new DelegationComparator());
		for(Method constructor: constructors) {
			createStrategyCloneOfConstructor(constructor);
			replaceSubobjectConstructorCalls(constructor, false);
		}
	}
	
	/**
	 * Create a clone of the constructor and add parameters for the strategies for subobject construction
	 * such that subclasses can override construction of subobjects.
	 * 
	 * Any subobject constructor calls are replaced with expressions that first check whether a strategy
	 * is present, and use that to initialize the subobject.
	 */
	private void createStrategyCloneOfConstructor(Method constructor) throws ModelException {
		MethodInvocation superCall = superDelegation(constructor);
		Java7 language = constructor.language(Java7.class);
	  Type container = constructor.nearestAncestor(Type.class);
	  Method clone = Util.clone(constructor);
	  DeclarationWithParametersHeader header = clone.header();
	  boolean added = false;
	  for(Subobject relation: container.members(Subobject.class)) {
		  ClassBody body = relation.nearestAncestor(ClassBody.class);
		  if((body != null) && container.subtypeOf(body.nearestAncestor(Type.class))) {
		  	/*
		  	 * If the relation is defined locally, we must create a strategy interface.
		  	 */
			  if(body.nearestAncestor(Type.class) == container) {
				  createStrategy(relation);
			  }
			  
			  Method cons = null;
			  for(SubobjectConstructorCall call: constructor.descendants(SubobjectConstructorCall.class)) {
			  	if(call.getTarget().getElement() == relation) {
			  		Method originalCons = call.getElement();
			  		cons = Util.clone(originalCons);
			  		cons.setUniParent(originalCons.parent());
			  		substituteTypeParameters(cons);
			  		cons.setUniParent(relation.nearestAncestor(Type.class));
			  	}
			  }
			  // Add a strategy parameter for the explicit initialization. 
			  header.addFormalParameter(new FormalParameter(constructorArgumentName(relation), language.createTypeReference(interfaceName(strategyName(relation)))));
			  String interfaceName;
			  if(cons != null) {
			  	createSpecificStrategy(relation,cons);
			  	interfaceName = interfaceName(defaultStrategyName(relation, cons));
			  } else {
			  	interfaceName = interfaceName(defaultStrategyNameWhenNoLocalSubobjectConstruction(relation, superCall));
			  }
			  // Add the default strategy parameter whic is used when its subclass does not
			  // have an explicit constructor call. In this case, the arguments of the local subobject
			  // constructor call are passed to the default strategy.
			  header.addFormalParameter(new FormalParameter(defaultConstructorArgumentName(relation), language.createTypeReference(interfaceName)));
			  added = true;
		  }
	  }
	  if(added) {
	  	container.add(clone);
	  	// Replace the subobject constructor calls
	  	replaceSubobjectConstructorCalls(clone, true);
	  }
  }

	private String defaultConstructorArgumentName(Subobject relation)
			throws LookupException {
		return "default"+constructorArgumentName(relation);
	}

	private String constructorArgumentName(Subobject relation) throws LookupException {
		return "strategyFor"+toUnderScore(relation.componentType().getFullyQualifiedName());
	}
	
	private void createStrategy(Subobject relation) throws LookupException {
		String name = strategyName(relation);
		Type type = relation.nearestAncestor(Type.class);
		if(strategyDoesNotExistFor(relation,strategyName(relation))) {
			String componentTypeName = interfaceName(relation.componentType().getFullyQualifiedName());
			Java7 language = type.language(Java7.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(name);
			MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, language.createTypeReference(componentTypeName));
			TypeReference typeRef = language.createTypeReference("java.lang.Object");
			header.addFormalParameter(new FormalParameter("object", typeRef));
			Method constructor = language.plugin(ObjectOrientedFactory.class).createNormalMethod(header);
			constructor.setImplementation(null);
			constructor.addModifier(new Abstract());
			strategy.add(constructor);
			strategy.addModifier(new Abstract());
			strategy.addModifier(new Static());
			for(Member member: relation.directlyOverriddenMembers()) {
				if(type.subtypeOf(member.nearestAncestor(Type.class))) {
					strategy.addInheritanceRelation(new SubtypeRelation(language.createTypeReference(strategyNameFQN((Subobject) member))));
				}
			}
			type.farthestAncestorOrSelf(Type.class).add(strategy);
		}
	}

	private boolean strategyDoesNotExistFor(Subobject relation, String name) throws LookupException {
		boolean notExists = true;
		for(Member member: relation.nearestAncestor(Type.class).directlyDeclaredMembers()) {
			if(member.signature().name().equals(name)) {
				notExists = false;
				break;
			}
		}
		return notExists;
	}

	private void createSpecificStrategy(Subobject relation, Method constructor) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		String componentTypeName = interfaceName(relation.componentType().getFullyQualifiedName());
		String name = defaultStrategyName(relation, constructor);

		if(strategyDoesNotExistFor(relation,name)) {
			Java7 language = type.language(Java7.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(name);
			MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, language.createTypeReference(componentTypeName));
			Method factoryMethod = language.plugin(ObjectOrientedFactory.class).createNormalMethod(header);
			TypeReference typeRef = language.createTypeReference("java.lang.Object");
			header.addFormalParameter(new FormalParameter("objectafrkuscggfjsdk", typeRef));
			for(FormalParameter param: constructor.formalParameters()) {
				factoryMethod.header().addFormalParameter(Util.clone(param));
			}
			factoryMethod.setImplementation(null);
			factoryMethod.addModifier(new Abstract());
			strategy.add(factoryMethod);
			strategy.addModifier(new Abstract());
			strategy.addModifier(new Static());
			for(TypeParameter parameter: constructor.nearestAncestor(Type.class).parameters(TypeParameter.class)) {
				strategy.addParameter(TypeParameter.class, Util.clone(parameter));
			}
			type.farthestAncestorOrSelf(Type.class).add(strategy);
		}
	}

	private String defaultStrategyName(Subobject relation, Method constructor) throws LookupException {
		String name = strategyName(relation);
		List<Type> parameterTypes;
		try {
			parameterTypes = constructor.formalParameterTypes();
			for(Type t: parameterTypes) {
				name += toUnderScore(t.name());
			}
			return name;
		} catch (LookupException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Return the name of the default strategy if there is no local subobject constructor call.
	 * @param relation
	 * @param superCall
	 * @return
	 * @throws LookupException
	 */
	@SuppressWarnings("unchecked")
	private String defaultStrategyNameWhenNoLocalSubobjectConstruction(Subobject relation,	MethodInvocation<?> superCall) throws LookupException {
		Java7 lang = relation.language(Java7.class);
		SubobjectConstructorCall currentSubobjectConstructorCall = subobjectConstructorCall(relation, superCall);
		if(currentSubobjectConstructorCall != null) {
			SubobjectConstructorCall subobjectConstructorCall = (SubobjectConstructorCall) currentSubobjectConstructorCall.farthestOrigin();
			SubobjectConstructorCall subobjectConstructorCallX = (SubobjectConstructorCall) currentSubobjectConstructorCall.clone();
			Type out = currentSubobjectConstructorCall.nearestAncestor(Type.class);
			Type origin = (Type) out.farthestOrigin();
			subobjectConstructorCallX.setUniParent(origin);
			
				Subobject actuallyConstructedSubobject = subobjectConstructorCall.getTarget().getElement();
				Method cons = subobjectConstructorCall.getElement();
				Type nearestAncestor = superCall.nearestAncestor(Type.class);
				Type originalOuter = (Type) nearestAncestor.farthestOrigin();
				Type erasedTypeOfOriginalSubobject = lang.erasure(subobjectConstructorCall.nearestAncestor(Type.class));
				// do we need to do the actual substitution with subobjectConstructorCall.nearestAncestor(Type.class)
				// instead of its erasure ????
				if(originalOuter.subtypeOf(erasedTypeOfOriginalSubobject)) {
					Method originalCons = cons;
					cons = Util.clone(originalCons);
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

	private SubobjectConstructorCall subobjectConstructorCall(Subobject relation, MethodInvocation<?> superCall)
			throws LookupException {
		SubobjectConstructorCall subobjectConstructorCall = null;
		Set<Subobject> allRelatedSubobjects = (Set<Subobject>) relation.overriddenMembers();
		allRelatedSubobjects.add(relation);
		Method constructor = null;
		try {
			constructor = (Method) superCall.getElement();
		} catch(LookupException exc) {
			superCall.getElement();
			throw exc;
		}
		while(constructor != null && (subobjectConstructorCall == null)) { // && constructor.nearestAncestor(Type.class) != type) {
		  for(SubobjectConstructorCall call: constructor.descendants(SubobjectConstructorCall.class)) {
		  	Subobject subobject = call.getTarget().getElement();
		  	boolean contains = false;
		  	for(Subobject sub: allRelatedSubobjects) {
		  		if((subobject.signature().sameAs(sub.signature())) && (subobject.nearestAncestor(Type.class).baseType().sameAs(sub.nearestAncestor(Type.class).baseType()))) {
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
				SuperConstructorDelegation superDelegation = superDelegation(constructor);
				constructor = (superDelegation == null ? null : superDelegation.getElement());
			}
		}
		return subobjectConstructorCall;
	}


	private String strategyName(Subobject relation) throws LookupException {
		String name = toUnderScore(relation.componentType().getFullyQualifiedName()) + STRATEGY;
		return name;
	}
	
	private String strategyNameFQN(Subobject relation) throws LookupException {
		return relation.farthestAncestor(Type.class).getFullyQualifiedName()+"."+ strategyName(relation); 
	}
	
	public final String STRATEGY = "_constructor";

	private SuperConstructorDelegation superDelegation(Method constructor) throws LookupException {
		Method consch = constructor;
		SuperConstructorDelegation superCall = null;
		while(superCall == null) {
			superCall = delegation(consch,SuperConstructorDelegation.class);
			if(superCall == null) {
				ConstructorDelegation thisDelegation = delegation(consch,ThisConstructorDelegation.class);
				if(thisDelegation == null) {
					Type type = constructor.nearestAncestor(Type.class);
					if(type.getDirectSuperTypes().isEmpty()) {
						return null;
					}
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
	
	protected static <C extends ConstructorDelegation> C delegation(Method constructor, Class<C> kind) {
		C result = null;
		Block body = constructor.body();
		if(body != null && body.nbStatements() > 0) {
			Statement first = body.statement(0);
			if(first instanceof StatementExpression) {
				Expression expression = ((StatementExpression) first).getExpression();
				if(kind.isInstance(expression)) {
					result = (C) expression; 
				}
			}
		}
		return result;
	}
	
	private void replaceSubobjectConstructorCalls(Method constructor, boolean clonedConstructor)
	throws ModelException {
		ExpressionFactory expressionFactory = constructor.language().plugin(ExpressionFactory.class);
		SuperConstructorDelegation superCall = superDelegation(constructor);
		// The method invocation above will guarantee that there is an explicit constructor delegation.
		ConstructorDelegation callToBeModified = (ConstructorDelegation) ((StatementExpression)constructor.body().statement(0)).getExpression();
		Type type = constructor.nearestAncestor(Type.class);
		List<Subobject> members = type.members(Subobject.class);
		List<FormalParameter> formalParameters = constructor.formalParameters();
		int firstStrategyIndex = constructor.nbFormalParameters()- (2*members.size());
		// We need the list of subobjects of the super class because we must pass the strategies in the correct order.
		List<Subobject> superSubobjects = type.getDirectSuperTypes().iterator().next().members(Subobject.class);
		// We store the additional arguments for the super constructor call locally, and add them
		// at the end. Otherwise I have to add a setter to OrderedMultiAssociation, and I am too tired
		// now to do that correctly.
		Expression[] arguments = new Expression[2*superSubobjects.size()];
		int indexInCurrent = firstStrategyIndex;
		for(Subobject relation: members) {
			int relativeIndexInSuper = -1;
			int size = superSubobjects.size();
			for(int i = 0; i < size; i++) {
				if(superSubobjects.get(i).signature().sameAs(relation.signature())) {
					relativeIndexInSuper = 2*i;
					break;
				}
			}
			ClassBody body = relation.nearestAncestor(ClassBody.class);
			int levelOfRelation = relation.ancestors(Type.class).size();
			if(body != null && type.subtypeOf(body.nearestAncestor(Type.class))) {
				List<SubobjectConstructorCall> subCalls = constructorCallsOfRelation(relation, constructor);
				if(!isLocallyDefined(relation,type)) {
					if(relativeIndexInSuper == -1) {
						throw new Error();
					}
					if(subCalls.isEmpty() && clonedConstructor) {
						// Neither strategy is set.
						
							arguments[relativeIndexInSuper] = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent).name());
							arguments[relativeIndexInSuper+1] = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent+1).name());
					} else {
						arguments[relativeIndexInSuper] = new NullLiteral();
						arguments[relativeIndexInSuper+1] = new NullLiteral();
					}
				} else if (! relation.overriddenMembers().isEmpty()) {
					// LOCALLY DEFINED & THERE IS A SUPER SUBOBJECT
					// create a strategy that will create the new subobject.
					if(subCalls.isEmpty()) {
						ConstructorInvocation strategy = new DefaultSubobjectFactoryFactory().strategy(null, constructor, superCall, formalParameters, relation);
						if(! clonedConstructor) {
							arguments[relativeIndexInSuper] = new NullLiteral();
							// add default strategy
							arguments[relativeIndexInSuper+1] = strategy;
						} else {
							arguments[relativeIndexInSuper] = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent).name());

							Expression arg = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent+1).name());
							MethodInvocation conditionRight = new InfixOperatorInvocation("==", Util.clone(arg));
							conditionRight.addArgument(new NullLiteral());
							Expression explicitArg = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent).name());
							MethodInvocation conditionLeft = new InfixOperatorInvocation("==", Util.clone(explicitArg));
							conditionLeft.addArgument(new NullLiteral());
							MethodInvocation condition = new InfixOperatorInvocation("&&", conditionLeft);
							condition.addArgument(conditionRight);
							Expression conditional = new ConditionalExpression(condition, strategy, arg);
							arguments[relativeIndexInSuper+1] = conditional;
						}
					} else {
						if(! clonedConstructor) {
						// add explicit strategy
						arguments[relativeIndexInSuper] = new ExplicitSubobjectFactoryFactory().strategy(subCalls.get(0),constructor, superCall,formalParameters, relation);
						
						// default is null;
						arguments[relativeIndexInSuper+1] = new NullLiteral();
						subCalls.get(0).parent().disconnect();
						
						} else {
							arguments[relativeIndexInSuper] = expressionFactory.createNameExpression(formalParameters.get(indexInCurrent).name());
							// default is to propagate the argument.
							arguments[relativeIndexInSuper+1] = new NullLiteral();
						}
					}
				} else {
					// Create and set the subobject
					for(SubobjectConstructorCall call: subCalls) {
						MethodInvocation<?> inv = new ConstructorInvocation((BasicJavaTypeReference) innerClassTypeReference(relation), null);
						inv.addAllArguments(call.getActualParameters());
						MethodInvocation setterCall = new JavaMethodInvocation(setterName(relation), null);
						setterCall.addArgument(inv);
						if(clonedConstructor) {
							MethodInvocation expression = new InfixOperatorInvocation("==", expressionFactory.createNameExpression(constructorArgumentName(relation)));
							expression.addArgument(new NullLiteral());
							Block ifBlock = new Block();
							
							MethodInvocation nestedExpression = new InfixOperatorInvocation("==", expressionFactory.createNameExpression(defaultConstructorArgumentName(relation)));
							nestedExpression.addArgument(new NullLiteral());
							Block nestedIfBlock = new Block();
							nestedIfBlock.addStatement(new StatementExpression(setterCall));
							Block nestedElseBlock = new Block();
							MethodInvocation<?> defaultStrategyCall = Util.clone(setterCall);
							defaultStrategyCall.getActualParameters().get(0).disconnect();
							nestedElseBlock.addStatement(new StatementExpression(defaultStrategyCall));
							MethodInvocation defaultStrategyInvocation = new JavaMethodInvocation(CONSTRUCT, expressionFactory.createNameExpression(defaultConstructorArgumentName(relation)));
							defaultStrategyInvocation.addArgument(new ThisLiteral());
							defaultStrategyCall.addArgument(defaultStrategyInvocation);
							for(Expression arg: inv.getActualParameters()) {
								defaultStrategyInvocation.addArgument(Util.clone(arg));
							}
							Statement ifthenelseDefault = new IfThenElseStatement(nestedExpression, nestedIfBlock, nestedElseBlock);
							
							ifBlock.addStatement(ifthenelseDefault);
							Block elseBlock = new Block();
							MethodInvocation<?> strategyCall = Util.clone(setterCall);
							strategyCall.getActualParameters().get(0).disconnect();
							elseBlock.addStatement(new StatementExpression(strategyCall));
							MethodInvocation strategyInvocation = new JavaMethodInvocation(CONSTRUCT, expressionFactory.createNameExpression(constructorArgumentName(relation)));
							strategyInvocation.addArgument(new ThisLiteral());
							strategyCall.addArgument(strategyInvocation);
							Statement ifthenelse = new IfThenElseStatement(expression, ifBlock, elseBlock);
							
							SingleAssociation parentLink = call.parent().parentLink();
							parentLink.getOtherRelation().replace(parentLink, ifthenelse.parentLink());
						} else {
							SingleAssociation parentLink = call.parentLink();
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
	
	private abstract class SubobjectFactoryFactory {
		public ConstructorInvocation strategy(SubobjectConstructorCall constructorCall, Method constructor,	SuperConstructorDelegation superCall,
				List<FormalParameter> formalParameters, Subobject relation) throws LookupException {
			Java7 language = constructor.language(Java7.class);

			Method method = createConstructMethod(relation, superCall);

			// Add a method body		
			Block methodBody = new Block();
			method.setImplementation(new RegularImplementation(methodBody));
			// -> public SubobjectType construct(Object o, ..., Type_i parameter_i, ...) {
			//    }

			// Cast the first parameter to the type of the outer class.
			BasicJavaTypeReference castTypeReference = language.createTypeReference(toImplName(constructor.nearestAncestor(Type.class).name()));
			ExpressionFactory expressionFactory = language.plugin(ExpressionFactory.class);
			ClassCastExpression cast = new ClassCastExpression(castTypeReference, expressionFactory.createNameExpression("o"));
			// -> ((OuterType)o)

			BasicJavaTypeReference subobjectTypeReference = language.createTypeReference(toImplName(relation.componentType().name()));
			ConstructorInvocation constructorInvocation = new ConstructorInvocation(subobjectTypeReference, cast);
			// -> ((OuterType)o).new SubobjectType()

			addArguments(constructorCall, superCall, relation, constructorInvocation);
			
			// -> ((OuterType)o).new SubobjectType(..., parameter_i, ...)
			methodBody.addStatement(new ReturnStatement(constructorInvocation));
			// -> public SubobjectType construct(Object o, ..., Type_i parameter_i, ...) {
			//      return ((OuterType)o).new SubobjectType(..., parameter_i, ...)
			//    }

			// Create the body of the anonymous constructor call that creates the strategy
			ClassBody b = new ClassBody();
			b.add(method);
			// -> {
			//      public SubobjectType construct(Object o, ..., Type_i parameter_i, ...) {
			//        return ((OuterType)o).new SubobjectType(..., parameter_i, ...)
			//      }
			//    }

			// Create a type reference for the strategy type
			String strategyName = strategyName(superCall, relation);
			BasicJavaTypeReference strategyType = language.createTypeReference(strategyName);
			Declaration superElement = relation.nearestAncestor(Type.class).inheritanceRelations().get(0).superElement();
			copyTypeParametersFromAncestors(superElement, strategyType);
			strategyType.setUniParent(superElement);
			substituteTypeParameters(strategyType);
			strategyType.setUniParent(null);
			// -> StrategyType

			// Create the strategy constructor
			ConstructorInvocation strategyConstructor = new ConstructorInvocation(strategyType, null);
			strategyConstructor.setBody(b);
			// -> new StrategyType() {
			//      public SubobjectType construct(Object o, ..., Type_i parameter_i, ...) {
			//        return ((OuterType)o).new SubobjectType(..., parameter_i, ...)
			//      }
			//    }
			return strategyConstructor;
		}

		protected abstract void addArguments(SubobjectConstructorCall constructorCall, SuperConstructorDelegation superCall, Subobject relation, ConstructorInvocation constructorInvocation)
				throws LookupException;

		/**
		 * Create the "construct" method of the subobject factory. The method will have a single formal parameter
		 * with name 'o' and type 'Object'.
		 * @param relation
		 * @return
		 * @throws LookupException
		 */
		protected Method createConstructMethod(Subobject relation,SuperConstructorDelegation superCall) throws LookupException {
			Java7 language = relation.language(Java7.class);
			BasicJavaTypeReference returnTypeReference = language.createTypeReference(interfaceName(relation.componentType().getFullyQualifiedName()));
			// -> SubobjectType

			// Create the header of the "construct" method which will create the actual subobject
			MethodHeader header = new SimpleNameMethodHeader(CONSTRUCT, returnTypeReference);
			// -> SubobjectType construct()

			// Add the formal parameter for the outer object. Its type is simply Object, as it will be casted later on.
			header.addFormalParameter(new FormalParameter("o",language.createTypeReference("java.lang.Object")));
			// SubobjectType ->construct(Object o)

			addParametersToConstructMethod(header,relation,superCall);
			
			// Create the constructor method using the previously constructed header
			Method method = language.plugin(ObjectOrientedFactory.class).createNormalMethod(header);
			method.addModifier(new Public());
			// -> public SubobjectType construct(Object o, ..., Type_i parameter_i, ...)
			return method;
		}
		
		protected Method subobjectConstructor(Subobject relation,
				SuperConstructorDelegation superCall) throws LookupException {
			SubobjectConstructorCall subobjectConstructorCall=subobjectConstructorCall(relation,superCall);
			Method subobjectConstructor = subobjectConstructorCall.getElement();
			return subobjectConstructor;
		}

		
		protected abstract String strategyName(SuperConstructorDelegation superCall, Subobject relation) throws LookupException;
//		protected abstract Method<?, ?, ?> subobjectConstructor(ComponentRelation relation, SuperConstructorDelegation superCall) throws LookupException;
		protected abstract void addParametersToConstructMethod(MethodHeader header, Subobject relation, SuperConstructorDelegation superCall) throws LookupException;
	}
	
  private class ExplicitSubobjectFactoryFactory extends SubobjectFactoryFactory {

	@Override
	protected String strategyName(SuperConstructorDelegation superCall, Subobject relation) throws LookupException {
		return interfaceName(SubobjectConstructorTransformer.this.strategyName(relation));
	}

	@Override
	protected void addParametersToConstructMethod(MethodHeader header, Subobject relation, SuperConstructorDelegation superCall)
			throws LookupException {
		
	}

	@Override
	protected void addArguments(SubobjectConstructorCall constructorCall, SuperConstructorDelegation superCall, Subobject relation, ConstructorInvocation constructorInvocation)
			throws LookupException {
		for(Expression e : constructorCall.getActualParameters()) {
			constructorInvocation.addArgument(e);
		}
	}
	
}
	
	private class DefaultSubobjectFactoryFactory extends SubobjectFactoryFactory {
	
		protected String strategyName(SuperConstructorDelegation superCall, Subobject relation) throws LookupException {
			return defaultStrategyNameWhenNoLocalSubobjectConstruction(relation, superCall);
		}

		@Override
		public void addParametersToConstructMethod(MethodHeader header, Subobject relation, SuperConstructorDelegation superCall) throws LookupException {
			// Copy the formal parameters of the subobject constructor that is invoked in the delegated constructor
			// (either via a super(...) delegation or a this(...) delegation).
			//
			Method subobjectConstructor = subobjectConstructor(relation,
					superCall);
			for(FormalParameter param: subobjectConstructor.formalParameters()) {
				FormalParameter clone = Util.clone(param);
				clone.setUniParent(param.parent());
				substituteTypeParameters(clone);
				clone.setUniParent(null);
				header.addFormalParameter(clone);
			}
			// -> SubobjectType construct(Object o, ..., Type_i parameter_i, ...)
		}

		@Override
		protected void addArguments(SubobjectConstructorCall constructorCall, SuperConstructorDelegation superCall, Subobject relation, ConstructorInvocation constructorInvocation)
				throws LookupException {
			Method subobjectConstructor = subobjectConstructor(relation,	superCall);
			Language language = relation.language();
			ExpressionFactory expressionFactory = language.plugin(ExpressionFactory.class);
			for(FormalParameter param: subobjectConstructor.formalParameters()) {
				NameExpression constructorArgument = expressionFactory.createNameExpression(param.name());
				constructorInvocation.addArgument(constructorArgument);
			}
		}

	}
	

	private List<SubobjectConstructorCall> constructorCallsOfRelation(
			final Subobject relation, Method constructor)
			throws LookupException {
		List<SubobjectConstructorCall> constructorCalls = constructor.descendants(SubobjectConstructorCall.class, new AbstractPredicate<SubobjectConstructorCall,LookupException>() {
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
		for(Subobject relation: result.directlyDeclaredMembers(Subobject.class)) {
			addDefaultSubobjectConstructorCalls(relation);
		}
	}

	private void addDefaultSubobjectConstructorCalls(Subobject relation) throws LookupException {
		Java7 lang = relation.language(Java7.class);
		Type subobjectType = relation.referencedComponentType();
		List<Method> constructorsOfReferencedSubobjectType = subobjectType.directlyDeclaredMembers(Method.class, lang.CONSTRUCTOR);
		boolean defaultConstructor = constructorsOfReferencedSubobjectType.isEmpty();
		for(Method cons: constructorsOfReferencedSubobjectType) {
			if(cons.nbFormalParameters() == 0) {
				defaultConstructor = true;
				break;
			}
		}
		if(defaultConstructor) {
			Set<? extends Member> overridden = relation.overriddenMembers();
			boolean differentDeclaredType = overridden.isEmpty();
			for(Member m: overridden) {
				Subobject r = (Subobject) m;
				if(! r.referencedComponentType().sameAs(subobjectType)) {
					differentDeclaredType = true;
				}
			}
			if(differentDeclaredType) {
				Type type = relation.nearestAncestor(Type.class);
				for(Method cons: type.directlyDeclaredMembers(Method.class, lang.CONSTRUCTOR)) {
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
