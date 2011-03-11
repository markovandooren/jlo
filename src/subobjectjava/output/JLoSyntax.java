package subobjectjava.output;

import java.util.List;

import jnome.output.JavaCodeWriter;
import subobjectjava.model.component.ActualComponentArgument;
import subobjectjava.model.component.ComponentParameterTypeReference;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.MultiActualComponentArgument;
import subobjectjava.model.component.SingleActualComponentArgument;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;

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
		} else {
			result = super.toCode(element);
		}
		return result;
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
}
