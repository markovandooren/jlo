package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.element.ElementImpl;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DeclarationWithType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;

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
