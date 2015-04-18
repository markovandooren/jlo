package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;

public interface ComponentArgumentContainer extends Element {

	public Type containerType(ActualComponentArgument argument) throws LookupException;
	
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException;
}
