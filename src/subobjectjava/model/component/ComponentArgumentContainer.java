package subobjectjava.model.component;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.Type;

public interface ComponentArgumentContainer<E extends ComponentArgumentContainer<E>> extends Element<E,Element> {

	public Type containerType(ActualComponentArgument argument) throws LookupException;
	
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException;
}
