package be.kuleuven.cs.distrinet.jlo.output;

import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.component.ActualComponentArgument;
import be.kuleuven.cs.distrinet.jlo.model.component.ComponentParameterTypeReference;
import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.jlo.model.component.Export;
import be.kuleuven.cs.distrinet.jlo.model.component.InstantiatedMemberSubobjectParameter;
import be.kuleuven.cs.distrinet.jlo.model.component.MultiActualComponentArgument;
import be.kuleuven.cs.distrinet.jlo.model.component.MultiFormalComponentParameter;
import be.kuleuven.cs.distrinet.jlo.model.component.RenamingClause;
import be.kuleuven.cs.distrinet.jlo.model.component.SingleActualComponentArgument;
import be.kuleuven.cs.distrinet.jlo.model.component.SingleFormalComponentParameter;
import be.kuleuven.cs.distrinet.jlo.model.expression.ComponentParameterCall;
import be.kuleuven.cs.distrinet.jlo.model.expression.SubobjectConstructorCall;
import be.kuleuven.cs.distrinet.jnome.output.JavaCodeWriter;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;

public class JLoSyntax extends JavaCodeWriter {

	@Override
	public String toCode(Element element) throws LookupException {
		String result;
		if(isSubobject(element)) {
			result = toCodeComponentRelation((ComponentRelation) element);
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
		} else {
			result = super.toCode(element);
		}
		return result;
	}
	
	public boolean isInstantiatedMemberSubobjectParameter(Element element) {
		return element instanceof InstantiatedMemberSubobjectParameter;
	}
	
	public String toCodeInstantiatedMemberSubobjectParameter(InstantiatedMemberSubobjectParameter element) throws LookupException {
		return "connect " + element.signature() +" to " +toCode(element.argument());
	}
	
	public boolean isSingleFormalComponentParameter(Element element) {
		return element instanceof SingleFormalComponentParameter;
	}
	
	public String toCodeSingleFormalComponentParameter(SingleFormalComponentParameter element) throws LookupException {
		return "connector " +element.signature().name() +" "+ toCode(element.containerTypeReference()) + " -> " + toCode(element.componentTypeReference());
	}
	
	public boolean isMultiFormalComponentParameter(Element element) {
		return element instanceof SingleFormalComponentParameter;
	}
	
	public String toCodeMultiFormalComponentParameter(MultiFormalComponentParameter element) throws LookupException {
		return "connector " +element.signature().name() +" "+ toCode(element.containerTypeReference()) + " -> [" + toCode(element.componentTypeReference())+"]";
	}
	
  public boolean isRenamingClause(Element element) {
  	return element instanceof RenamingClause;
  }
  
  public String toCodeRenamingClause(RenamingClause element) throws LookupException {
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
		return element instanceof ComponentRelation;
	}
	
	
	public boolean isComponentParameterTypeReference(Element element) {
		return element instanceof ComponentParameterTypeReference;
	}
	
	public String toCodeComponentParameterTypeReference(ComponentParameterTypeReference reference) throws LookupException {
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
	
	public String toCodeComponentRelation(ComponentRelation relation) throws LookupException {
		StringBuffer result = new StringBuffer();
		addModifiers(relation, result);
		result.append( "subobject ");
		result.append(relation.signature());
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

	public String toCodeMultiActualComponentArgument(MultiActualComponentArgument argument) throws LookupException {
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
	
	public String toCodeSubobjectConstructorCall(SubobjectConstructorCall argument) throws LookupException {
		return "subobject."+toCode(argument.getTarget())+getActualArgs(argument);
	}

	public boolean isComponentParameterCall(Element element) {
		return element instanceof ComponentParameterCall;
	}
	
	public String toCodeComponentParameterCall(ComponentParameterCall argument) throws LookupException {
		return "#"+ argument.name() + "(" + toCode(argument.target()) + ")";
	}

}
