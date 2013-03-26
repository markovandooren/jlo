package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;

public interface AbstractInstantiatedComponentParameter extends Element {

	public FormalComponentParameter formalParameter() throws LookupException;
	
	public ActualComponentArgument argument();
}
