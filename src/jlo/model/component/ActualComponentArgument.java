package jlo.model.component;

import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public abstract class ActualComponentArgument<E extends ActualComponentArgument<E>> extends NamespaceElementImpl<E> {

	
	public abstract DeclarationWithType declaration() throws LookupException;
	
	public FormalComponentParameter formalParameter() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).formalParameter(this);
	}
	
	public abstract E clone();
	
	protected Type containerType() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).containerType(this);
	}
	
}
