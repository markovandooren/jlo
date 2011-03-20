package subobjectjava.translate;

import java.util.List;
import java.util.Set;

import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.SuperConstructorDelegation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.association.SingleAssociation;
import org.rejuse.predicate.UnsafePredicate;

import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.expression.SubobjectConstructorCall;
import chameleon.core.declaration.DeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.expression.Expression;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.statement.Block;
import chameleon.core.statement.Statement;
import chameleon.core.variable.FormalParameter;
import chameleon.exception.ModelException;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.support.expression.ConditionalExpression;
import chameleon.support.expression.NullLiteral;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import chameleon.support.modifier.Abstract;
import chameleon.support.modifier.Static;
import chameleon.support.statement.IfThenElseStatement;
import chameleon.support.statement.StatementExpression;

public class SubobjectConstructorTransformer extends AbstractTranslator {

	public void replaceSubobjectConstructorCalls(Type type) throws ModelException {
		Java lang = type.language(Java.class);
		for(Method constructor: type.descendants(Method.class, lang.CONSTRUCTOR)) {
			createStrategyCloneOfConstructor(constructor);
			replaceSubobjectConstructorCalls(constructor, false);
		}
	}
	
	private void createStrategyCloneOfConstructor(Method<?,?,?,?> constructor) throws ModelException {
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
	  	if(relation.name().equals("frequency")) {
	  		System.out.println("debug");
	  	}
		  ClassBody body = relation.nearestAncestor(ClassBody.class);
		  if((body != null) && container.subTypeOf(body.nearestAncestor(Type.class))) { // (relation.origin() == relation) &&
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
			  header.addFormalParameter(new FormalParameter(new SimpleNameSignature(constructorArgumentName(relation)), language.createTypeReference(strategyName(relation))));
			  if(cons != null) {
			  	createSpecificStrategy(relation,cons);
			  	header.addFormalParameter(new FormalParameter(new SimpleNameSignature("default"+constructorArgumentName(relation)), language.createTypeReference(defaultStrategyName(relation, cons))));
			  } else {
			  	header.addFormalParameter(new FormalParameter(new SimpleNameSignature("default"+constructorArgumentName(relation)), language.createTypeReference(defaultStrategyNameWhenNoLocalSubobjectConstruction(relation, superCall))));
			  }
			  added = true;
		  }
	  }
	  if(added) {
	  	container.add(clone);
	  	replaceSubobjectConstructorCalls(clone, true);
	  }
  }

	private String constructorArgumentName(ComponentRelation relation) throws LookupException {
		return "strategyFor"+toUnderScore(relation.componentType().getFullyQualifiedName());
	}
	
	private void createStrategy(ComponentRelation relation) throws LookupException {
//		Type result = null;
		String name = strategyName(relation);
		Type type = relation.nearestAncestor(Type.class);
		if(strategyDoesNotExistFor(relation,strategyName(relation))) {
			String componentTypeName = relation.componentType().getFullyQualifiedName();
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			DeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(CONSTRUCT);
//			TypeReference typeRef = language.createTypeReference(relation.nearestAncestor(Type.class).getFullyQualifiedName());
			TypeReference typeRef = language.createTypeReference("java.lang.Object");
			header.addFormalParameter(new FormalParameter(new SimpleNameSignature("object"), typeRef));
			Method constructor = language.createNormalMethod(header, language.createTypeReference(componentTypeName));
			constructor.setImplementation(null);
			constructor.addModifier(new Abstract());
			strategy.add(constructor);
			strategy.addModifier(new Abstract());
			strategy.addModifier(new Static());
			for(Member<?,?,?> member: relation.directlyOverriddenMembers()) {
				if(type.subTypeOf(member.nearestAncestor(Type.class))) {
					strategy.addInheritanceRelation(new SubtypeRelation(language.createTypeReference(strategyName((ComponentRelation) member))));
				}
			}
			type.add(strategy);
//			result = strategy;
		}
//		return result;
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

	private void createSpecificStrategy(ComponentRelation relation, Method<?,?,?,?> constructor) throws LookupException {
		Type type = relation.nearestAncestor(Type.class);
		String componentTypeName = relation.componentType().getFullyQualifiedName();
		String name = defaultStrategyName(relation, constructor);

		if(strategyDoesNotExistFor(relation,defaultStrategyName(relation,constructor))) {
			Java language = type.language(Java.class);
			Type strategy = language.plugin(ObjectOrientedFactory.class).createRegularType(new SimpleNameSignature(name));
			type.add(strategy);
			DeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(CONSTRUCT);
			Method factoryMethod = language.createNormalMethod(header, language.createTypeReference(componentTypeName));
//			TypeReference typeRef = language.createTypeReference(relation.nearestAncestor(Type.class).getFullyQualifiedName());
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
		}
	}

	private String defaultStrategyName(ComponentRelation relation, Method<?, ?, ?, ?> constructor) throws LookupException {
		String name = strategyName(relation);
		List<Type> parameterTypes = constructor.formalParameterTypes();
		for(Type t: parameterTypes) {
			name += toUnderScore(t.getName());
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	private String defaultStrategyNameWhenNoLocalSubobjectConstruction(ComponentRelation relation,	MethodInvocation<?,?> superCall) throws LookupException {
		Set<ComponentRelation> allRelatedSubobjects = (Set<ComponentRelation>) relation.overriddenMembers();
		allRelatedSubobjects.add(relation);
		Type originalOuter = superCall.farthestAncestor(Type.class);
		Method<?,?,?,?> constructor = superCall.getElement();
		ComponentRelation actuallyConstructedSubobject = null;
		MethodInvocation subobjectConstructorCall = null;
		while(constructor != null && (subobjectConstructorCall == null)) { // && constructor.nearestAncestor(Type.class) != type) {
		  for(SubobjectConstructorCall call: constructor.descendants(SubobjectConstructorCall.class)) {
		  	ComponentRelation subobject = call.getTarget().getElement();
				if(allRelatedSubobjects.contains(subobject)) {
					actuallyConstructedSubobject = subobject;
					subobjectConstructorCall = call;
		  		break;
		  	}
		  }
		  // If we haven't found the appropriate constructor yet, we continue searching in the super class hierarchy by following
		  // the super constructor delegations.
			if(subobjectConstructorCall == null) {
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
		if(constructor == null) {
			return strategyName(relation);
		} else {
		  Method cons = subobjectConstructorCall.getElement();
		  // Only substitute parameters if we are not in the context of the subobject type
		  if(originalOuter.subTypeOf(constructor.nearestAncestor(Type.class))) {
			  Method originalCons = subobjectConstructorCall.getElement();
			  cons = originalCons.clone();
			  cons.setUniParent(originalCons.parent());
			  substituteTypeParameters(cons);
			  cons.setUniParent(relation.nearestAncestor(Type.class));
		  }
		  String defaultStrategyName = defaultStrategyName(actuallyConstructedSubobject, cons);
		  if(defaultStrategyName.equals("radio_SpecialRadio_frequency_constructorFloatFloatFloat")) {
			  System.out.println("debug");
		  }
		  return defaultStrategyName;
		}
	}


	private String strategyName(ComponentRelation relation) throws LookupException {
		String name = toUnderScore(relation.componentType().getFullyQualifiedName()) + STRATEGY;
		return name;
	}
	
	public final String STRATEGY = "_constructor";

	private void replaceSubobjectConstructorCalls(Method<?,?,?,?> constructor, boolean clonedConstructor)
	throws ModelException {
		Java language = constructor.language(Java.class);
		if(constructor.name().equals("SpecialRadio")) {
			System.out.println("debug");
		}
		SuperConstructorDelegation superCall = constructor.descendants(SuperConstructorDelegation.class).get(0);
		Type type = constructor.nearestAncestor(Type.class);
		List<ComponentRelation> members = type.members(ComponentRelation.class);
		List<FormalParameter> formalParameters = constructor.formalParameters();
		int firstStrategyIndex = constructor.nbFormalParameters()- (2*members.size());
		// We need the list of subobjects of the super class because we must pass the strategies in the correct order.
		List<ComponentRelation> superSubobjects = type.getDirectSuperTypes().iterator().next().members(ComponentRelation.class);
		// We store the additional arguments for the super constructor call locally, and add them
		// at the end. Otherwise I have to add a setter to OrderedMultiAssociation, and I am too tired
		// now to do that correctly.
//		List<Expression> arguments = new ArrayList<Expression>();
		Expression[] arguments = new Expression[2*superSubobjects.size()];
//		for(int i=0; i< 2*superSubobjects.size();i++) {
//			arguments.add(null);
//		}
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
							arguments[relativeIndexInSuper+1] = new NullLiteral(); // FIX
						} else {
							arguments[relativeIndexInSuper] = new NamedTargetExpression(formalParameters.get(indexInCurrent).getName());
							Expression arg = new NamedTargetExpression(formalParameters.get(indexInCurrent+1).getName());
							MethodInvocation conditionRight = new InfixOperatorInvocation("==", arg.clone());
							conditionRight.addArgument(new NullLiteral());
							Expression explicitArg = new NamedTargetExpression(formalParameters.get(indexInCurrent).getName());
							MethodInvocation conditionLeft = new InfixOperatorInvocation("==", explicitArg.clone());
							conditionLeft.addArgument(new NullLiteral());
							MethodInvocation condition = new InfixOperatorInvocation("&&", conditionLeft);
							condition.addArgument(conditionRight);
							ConstructorInvocation strategy = new ConstructorInvocation((BasicJavaTypeReference) formalParameters.get(indexInCurrent+1).getTypeReference().clone(), null);
							Expression conditional = new ConditionalExpression(condition, strategy, arg);
							ClassBody b = new ClassBody();
							strategy.setBody(b);
							SimpleNameDeclarationWithParametersHeader header = new SimpleNameDeclarationWithParametersHeader(CONSTRUCT);
							header.addFormalParameter(new FormalParameter("o",language.createTypeReference("java.lang.Object")));
							Method method = language.createNormalMethod(header, language.createTypeReference(relation.componentType().getFullyQualifiedName()));
							b.add(method);
							Block methodBody = new Block();
							method.setImplementation(new RegularImplementation(methodBody));
							arguments[relativeIndexInSuper+1] = conditional;
						}
					} else {
						if(! clonedConstructor) {
						// add explicit strategy
						arguments[relativeIndexInSuper] = new NullLiteral(); // FIX
						// default is null;
						arguments[relativeIndexInSuper+1] = new NullLiteral();
						} else {
							arguments[relativeIndexInSuper] = new NullLiteral(); // FIX
							// default is to propagate the argument.
							arguments[relativeIndexInSuper+1] = new NamedTargetExpression(formalParameters.get(indexInCurrent+1).getName());
						}
					}
				} else {
					// Create and set the subobject
					for(SubobjectConstructorCall call: subCalls) {
						MethodInvocation inv = new ConstructorInvocation((BasicJavaTypeReference) innerClassTypeReference(relation), null);
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
			indexInCurrent += 2;
		}
		for(Expression argument: arguments) {
		  superCall.addArgument(argument);
		}
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
	
	public final String CONSTRUCT = "construct";

}
