package jlo.model.component;

import chameleon.core.element.ElementImpl;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public abstract class ActualComponentArgument extends ElementImpl {

	
	public abstract DeclarationWithType declaration() throws LookupException;
	
	public FormalComponentParameter formalParameter() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).formalParameter(this);
	}
	
	public abstract ActualComponentArgument clone();
	
	protected Type containerType() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).containerType(this);
	}
	
}
