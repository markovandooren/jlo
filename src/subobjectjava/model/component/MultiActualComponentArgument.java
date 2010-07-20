package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rejuse.association.OrderedMultiAssociation;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public class MultiActualComponentArgument extends ActualComponentArgument<MultiActualComponentArgument> implements ComponentArgumentContainer<MultiActualComponentArgument> {

	public MultiActualComponentArgument() {
		
	}
	public MultiActualComponentArgument(List<SingleActualComponentArgument> arguments) {
		addAll(arguments);
	}
	
	public List<? extends Element> children() {
		return arguments();
	}

	@Override
	public ComponentRelationSet declaration() throws LookupException {
		List<ComponentRelation> relations = new ArrayList<ComponentRelation>();
		for(SingleActualComponentArgument argument : arguments()) {
			relations.add(argument.declaration());
		}
		return new ComponentRelationSet(relations, formalParameter());
	}

	@Override
	public MultiActualComponentArgument clone() {
		MultiActualComponentArgument result = new MultiActualComponentArgument();
		for(SingleActualComponentArgument arg:arguments()) {
			result.add(arg.clone());
		}
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	
	public void addAll(Collection<? extends SingleActualComponentArgument> elements) {
		for(SingleActualComponentArgument element:elements) {
			add(element);
		}
	}
	
	private OrderedMultiAssociation<MultiActualComponentArgument, SingleActualComponentArgument> _elements = new OrderedMultiAssociation<MultiActualComponentArgument, SingleActualComponentArgument>(this);

	public void add(SingleActualComponentArgument element) {
	  if(element != null) {
	    _elements.add(element.parentLink());
	  }
	}
	
	public void remove(SingleActualComponentArgument element) {
	  if(element != null) {
	    _elements.remove(element.parentLink());
	  }
	}
	
	public List<SingleActualComponentArgument> arguments() {
		return _elements.getOtherEnds();
	}
	public Type containerType(ActualComponentArgument argument) throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).containerType(this);
	}
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).formalParameter(this);
	}

}
