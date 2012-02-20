package jlo.model.component;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;

public interface AbstractInstantiatedComponentParameter extends Element {

	public FormalComponentParameter formalParameter() throws LookupException;
	
	public ActualComponentArgument argument();
}
