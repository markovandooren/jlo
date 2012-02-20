package jlo.model.component;

import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public abstract class ActualComponentArgument extends NamespaceElementImpl {

	
	public abstract DeclarationWithType declaration() throws LookupException;
	
	public FormalComponentParameter formalParameter() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).formalParameter(this);
	}
	
	public abstract ActualComponentArgument clone();
	
	protected Type containerType() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).containerType(this);
	}
	
}
