package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;

public interface ComponentArgumentContainer extends Element {

	public Type containerType(ActualComponentArgument argument) throws LookupException;
	
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException;
}
