package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;

public interface AbstractInstantiatedComponentParameter extends Element {

	public FormalComponentParameter formalParameter() throws LookupException;
	
	public ActualComponentArgument argument();
}
