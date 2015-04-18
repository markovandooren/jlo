package org.aikodi.jlo.output;

import java.util.List;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.jlo.model.component.ActualComponentArgument;
import org.aikodi.jlo.model.component.ComponentParameterTypeReference;
import org.aikodi.jlo.model.component.Export;
import org.aikodi.jlo.model.component.InstantiatedMemberSubobjectParameter;
import org.aikodi.jlo.model.component.MultiActualComponentArgument;
import org.aikodi.jlo.model.component.MultiFormalComponentParameter;
import org.aikodi.jlo.model.component.RenamingClause;
import org.aikodi.jlo.model.component.SingleActualComponentArgument;
import org.aikodi.jlo.model.component.SingleFormalComponentParameter;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.expression.ComponentParameterCall;
import org.aikodi.jlo.model.expression.SubobjectConstructorCall;

import be.kuleuven.cs.distrinet.jnome.output.Java7Syntax;

public class JLoSyntax extends Java7Syntax {

	@Override
	public String toCode(Element element) {
		String result;
		if(isSubobject(element)) {
			result = toCodeSubobject((Subobject) element);
		} else if(isComponentParameterTypeReference(element)) {
			result = toCodeComponentParameterTypeReference((ComponentParameterTypeReference) element);
		} else if(isMultiActualComponentArgument(element)) {
			result = toCodeMultiActualComponentArgument((MultiActualComponentArgument) element);
		} else if(isSingleActualComponentArgument(element)) {
			result = toCodeSingleActualComponentArgument((SingleActualComponentArgument) element);
		} else if(isSubobjectConstructorCall(element)) {
			result = toCodeSubobjectConstructorCall((SubobjectConstructorCall) element);
		} else if(isComponentParameterCall(element)) {
			result = toCodeComponentParameterCall((ComponentParameterCall) element);
		} else if(isSingleFormalComponentParameter(element)) {
			result = toCodeSingleFormalComponentParameter((SingleFormalComponentParameter) element);
		} else if(isMultiFormalComponentParameter(element)) {
			result = toCodeMultiFormalComponentParameter((MultiFormalComponentParameter) element);
		} else if(isInstantiatedMemberSubobjectParameter(element)) {
			result = toCodeInstantiatedMemberSubobjectParameter((InstantiatedMemberSubobjectParameter) element);
		} else if(isExpressionImplementation(element)) {
		  result = toCodeExpressionImplementation((ExpressionImplementation)element);
		}
		else {
			result = super.toCode(element);
		}
		return result;
	}
	
	public boolean isExpressionImplementation(Element element) {
	  return element instanceof ExpressionImplementation;
	}
	
	public String toCodeExpressionImplementation(ExpressionImplementation impl) {
	  return "= "+toCode(impl.expression());
	}
	
	public boolean isInstantiatedMemberSubobjectParameter(Element element) {
		return element instanceof InstantiatedMemberSubobjectParameter;
	}
	
	public String toCodeInstantiatedMemberSubobjectParameter(InstantiatedMemberSubobjectParameter element) {
		return "connect " + element.name() +" to " +toCode(element.argument());
	}
	
	public boolean isSingleFormalComponentParameter(Element element) {
		return element instanceof SingleFormalComponentParameter;
	}
	
	public String toCodeSingleFormalComponentParameter(SingleFormalComponentParameter element) {
		return "connector " +element.name() +" "+ toCode(element.containerTypeReference()) + " -> " + toCode(element.componentTypeReference());
	}
	
	public boolean isMultiFormalComponentParameter(Element element) {
		return element instanceof SingleFormalComponentParameter;
	}
	
	public String toCodeMultiFormalComponentParameter(MultiFormalComponentParameter element) {
		return "connector " +element.name() +" "+ toCode(element.containerTypeReference()) + " -> [" + toCode(element.componentTypeReference())+"]";
	}
	
  public boolean isRenamingClause(Element element) {
  	return element instanceof RenamingClause;
  }
  
  public String toCodeRenamingClause(RenamingClause element) {
  	return toCode(element.oldFQN()) +" as " + toCode(element.newSignature());
  }
	
	public boolean isExport(Element element) {
		return element instanceof Export;
	}
	
	public String toCodeExport(Export element) throws LookupException {
		StringBuffer result = new StringBuffer();
		result.append("export ");
		List<RenamingClause> clauses = element.clauses();
		for(int i=0; i < clauses.size();i++) {
			result.append(toCode(clauses.get(i)));
			if(i<clauses.size()-1) {
				result.append(",\n");
			}
		}
		result.append(";");
		return result.toString();
	}
	
	public boolean isSubobject(Element element) {
		return element instanceof Subobject;
	}
	
	
	public boolean isComponentParameterTypeReference(Element element) {
		return element instanceof ComponentParameterTypeReference;
	}
	
	public String toCodeComponentParameterTypeReference(ComponentParameterTypeReference reference)  {
		StringBuffer result = new StringBuffer();
		result.append(toCode(reference.componentTypeReference()));
		result.append("##");
		List<ActualComponentArgument> componentArguments = reference.componentArguments();
		int size = componentArguments.size();
		for(int i = 0; i < size; i++) {
			result.append(toCode(componentArguments.get(i)));
			if(i<size -1) {
				result.append(",");
			}
		}
		result.append("##");
		return result.toString();
	}
	
	public String toCodeSubobject(Subobject relation)  {
		StringBuffer result = new StringBuffer();
		addModifiers(relation, result);
		result.append( "subobject ");
		result.append(relation.name());
		result.append(" ");
		result.append(toCode(relation.componentTypeReference()));
		return result.toString();
	}
	
	public boolean isSingleActualComponentArgument(Element element) {
		return element instanceof SingleActualComponentArgument;
	}
	
	public String toCodeSingleActualComponentArgument(SingleActualComponentArgument argument) {
		return argument.name();
	}

	public boolean isMultiActualComponentArgument(Element element) {
		return element instanceof MultiActualComponentArgument;
	}

	public String toCodeMultiActualComponentArgument(MultiActualComponentArgument argument)  {
		StringBuffer result = new StringBuffer();
		List<SingleActualComponentArgument> componentArguments = argument.arguments();
		int size = componentArguments.size();
		for(int i = 0; i < size; i++) {
			result.append(toCode(componentArguments.get(i)));
			if(i<size -1) {
				result.append(",");
			}
		}
		return result.toString();
	}
	
	public boolean isSubobjectConstructorCall(Element element) {
		return element instanceof SubobjectConstructorCall;
	}
	
	public String toCodeSubobjectConstructorCall(SubobjectConstructorCall argument) {
		return "subobject."+toCode(argument.getTarget())+getActualArgs(argument);
	}

	public boolean isComponentParameterCall(Element element) {
		return element instanceof ComponentParameterCall;
	}
	
	public String toCodeComponentParameterCall(ComponentParameterCall argument) {
		return "#"+ argument.name() + "(" + toCode(argument.target()) + ")";
	}

}
