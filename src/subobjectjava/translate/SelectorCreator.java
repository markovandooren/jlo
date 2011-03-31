package subobjectjava.translate;

import java.util.ArrayList;
import java.util.List;

import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaTypeReference;
import subobjectjava.model.component.ActualComponentArgument;
import subobjectjava.model.component.ComponentNameActualArgument;
import subobjectjava.model.component.ComponentParameter;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ComponentRelationSet;
import subobjectjava.model.component.FormalComponentParameter;
import subobjectjava.model.component.InstantiatedComponentParameter;
import subobjectjava.model.component.MultiActualComponentArgument;
import subobjectjava.model.component.MultiFormalComponentParameter;
import subobjectjava.model.component.ParameterReferenceActualArgument;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.expression.Expression;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.Method;
import chameleon.core.method.RegularImplementation;
import chameleon.core.statement.Block;
import chameleon.core.variable.FormalParameter;
import chameleon.core.variable.VariableDeclaration;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;
import chameleon.oo.type.generics.BasicTypeArgument;
import chameleon.support.member.simplename.SimpleNameMethodInvocation;
import chameleon.support.modifier.Public;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.variable.LocalVariableDeclarator;

public class SelectorCreator extends AbstractTranslator {

	public List<Method> selectorsFor(ComponentRelation rel) throws LookupException {
		Type t = rel.referencedComponentType();
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
			if(singarg.declaration() == null) {
				System.out.println("debug");
				singarg.declaration();
			}
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
	
	public List<Method> selectorsFor(Type type) throws LookupException {
		ParameterBlock<?,ComponentParameter> block = type.parameterBlock(ComponentParameter.class);
		List<Method> result = new ArrayList<Method>();
		if(block != null) {
		  for(ComponentParameter par: block.parameters()) {
		  	result.add(selectorFor((FormalComponentParameter<?>) par));
		  }
		}
		return result;
	}

	public String selectorName(ComponentParameter<?> par) {
		return "__select$"+ toUnderScore(par.nearestAncestor(Type.class).getFullyQualifiedName())+"$"+par.signature().name();
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


}
