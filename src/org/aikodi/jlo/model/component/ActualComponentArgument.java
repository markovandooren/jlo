package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.Type;

public abstract class ActualComponentArgument extends ElementImpl {

	
	public abstract DeclarationWithType declaration() throws LookupException;
	
	public FormalComponentParameter formalParameter() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).formalParameter(this);
	}
	
	protected Type containerType() throws LookupException {
		return nearestAncestor(ComponentArgumentContainer.class).containerType(this);
	}
	
}
