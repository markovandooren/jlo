package jlo.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jlo.model.component.AbstractInstantiatedComponentParameter;
import jlo.model.component.ActualComponentArgument;
import jlo.model.component.ComponentNameActualArgument;
import jlo.model.component.ComponentParameter;
import jlo.model.component.ComponentRelation;
import jlo.model.component.ComponentRelationSet;
import jlo.model.component.ComponentType;
import jlo.model.component.FormalComponentParameter;
import jlo.model.component.MultiActualComponentArgument;
import jlo.model.component.MultiFormalComponentParameter;
import jlo.model.component.ParameterReferenceActualArgument;
import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaTypeReference;
import chameleon.core.lookup.LookupException;
import chameleon.oo.expression.Expression;
import chameleon.oo.expression.NamedTargetExpression;
import chameleon.oo.member.Member;
import chameleon.oo.method.Method;
import chameleon.oo.method.MethodHeader;
import chameleon.oo.method.RegularImplementation;
import chameleon.oo.method.SimpleNameMethodHeader;
import chameleon.oo.statement.Block;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;
import chameleon.oo.variable.FormalParameter;
import chameleon.oo.variable.VariableDeclaration;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.variable.LocalVariableDeclarator;

public class SelectorCreator extends AbstractTranslator {

	public List<Method> selectorsFor(ComponentRelation rel) throws LookupException {
		if(rel.componentType().descendants(ComponentParameter.class).isEmpty()) {
			Type t = rel.referencedComponentType();
			return selectorsForComponent(t);
		} else {
			return selectorsForComponent(rel.componentType());
		}
	}

	private FormalComponentParameter highestSubobjectParameter(FormalComponentParameter par) throws LookupException {
		FormalComponentParameter result = par;
		Set<? extends Member> overridden = result.overriddenMembers();
		while(! overridden.isEmpty()) {
			result = (FormalComponentParameter) overridden.iterator().next();
			overridden = result.overriddenMembers();
		}
		return result;
	}
	
	public List<Method> selectorsForComponent(Type t) throws LookupException {
		List<Method> result = new ArrayList<Method>();
		List<ComponentParameter> parameters = t.parameters(ComponentParameter.class);
		parameters.addAll(t.members(ComponentParameter.class));
		// HACK that must be removed (as in "always used" so no if statement) when functional
		// style parameters are removed.
		for(ComponentParameter par: parameters) {
			AbstractInstantiatedComponentParameter instantiatedPar = (AbstractInstantiatedComponentParameter) par;
			FormalComponentParameter formalParameter = instantiatedPar.formalParameter();
			FormalComponentParameter originalFormalParameter = (FormalComponentParameter) formalParameter.origin();
//			FormalComponentParameter<?> highestFormalParameter = highestSubobjectParameter(originalFormalParameter);
			Type p = t;
			if(t instanceof ComponentType) {
				p = originalFormalParameter.nearestAncestor(Type.class);
			}
			Method realSelector= realSelectorFor(instantiatedPar);
			realSelector.setUniParent(p);
			substituteTypeParameters(realSelector);
			realSelector.setUniParent(null);
			result.add(realSelector);
		}
		return result;
	}
	
	private Method realSelectorFor(AbstractInstantiatedComponentParameter par) throws LookupException {
		FormalComponentParameter formal = par.formalParameter();
		Java language = par.language(Java.class);
//		Method result = new NormalMethod(header,formal.componentTypeReference().clone());
		Type declarationType = formal.declarationType();
		JavaTypeReference reference = language.reference(declarationType);
		reference.setUniParent(null);
		MethodHeader header = new SimpleNameMethodHeader(selectorName((ComponentParameter) par),reference);
		Method result = par.language(Java.class).createNormalMethod(header);
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
			
//			BasicTypeArgument targ = language.createBasicTypeArgument(componentType);
//			arrayList.addArgument(targ);
			
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
	
	public List<Method> selectorsFor(Type type) throws LookupException {
		List<Method> result = new ArrayList<Method>();
		List<ComponentParameter> parameters = new ArrayList<ComponentParameter>(); 
		ParameterBlock<ComponentParameter> block = type.parameterBlock(ComponentParameter.class);
		if(block != null) {
		  parameters.addAll(block.parameters());
		}
		parameters.addAll(type.members(ComponentParameter.class));
		for(ComponentParameter par: parameters) {
	  	result.add(selectorFor((FormalComponentParameter) par));
	  }
		
		return result;
	}

	public String selectorName(ComponentParameter par) throws LookupException {
//		Type type;
//		if(par instanceof InstantiatedMemberSubobjectParameter) {
//			type = (Type) ((InstantiatedMemberSubobjectParameter) par).formalParameter().origin().nearestAncestor(Type.class);
//		} else {
//			type = par.nearestAncestor(Type.class);
//		}
//		return "__select$"+ toUnderScore(type.getFullyQualifiedName())+"$"+par.signature().name();
		return "__select$" + par.signature().name();
	}
	
	private Method selectorFor(FormalComponentParameter par) throws LookupException {
		Java language = par.language(Java.class);
		JavaTypeReference reference = language.reference(par.declarationType());
		reference.setUniParent(null);
		MethodHeader header = new SimpleNameMethodHeader(selectorName(par),reference);
		Method result = par.language(Java.class).createNormalMethod(header);
		result.addModifier(new Public());
		header.addFormalParameter(new FormalParameter("argument", par.containerTypeReference().clone()));
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		ConstructorInvocation cons = new ConstructorInvocation((BasicJavaTypeReference) par.language(Java.class).createTypeReference("java.lang.Error"), null);
		body.addStatement(new ThrowStatement(cons));
		return result;
	}


}
