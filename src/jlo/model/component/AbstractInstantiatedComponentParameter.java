package jlo.model.component;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;

public interface AbstractInstantiatedComponentParameter<E extends AbstractInstantiatedComponentParameter<E>> extends Element<E> {

	public FormalComponentParameter<?> formalParameter() throws LookupException;
	
	public ActualComponentArgument argument();
}
