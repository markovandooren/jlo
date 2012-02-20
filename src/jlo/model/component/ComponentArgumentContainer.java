package jlo.model.component;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.Type;

public interface ComponentArgumentContainer extends Element {

	public Type containerType(ActualComponentArgument argument) throws LookupException;
	
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException;
}
