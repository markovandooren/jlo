package org.aikodi.jlo.model.type;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.IntersectionTypeReference;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.util.association.Single;

public class ParenthesisTypeReference extends ElementImpl implements TypeReference {

	private Single<TypeReference> _typeReference = new Single<>(this);
	
	@Override
	public Declaration getDeclarator() throws LookupException {
		return typeReference().getDeclarator();
	}
	
	public TypeReference typeReference() {
		return _typeReference.getOtherEnd();
	}

	@Override
	public LookupContext targetContext() throws LookupException {
		return getType().targetContext();
	}

	@Override
	public Type getType() throws LookupException {
		return typeReference().getType();
	}

	@Override
	public Type getElement() throws LookupException {
		return typeReference().getElement();
	}

	@Override
	public TypeReference intersectionDoubleDispatch(TypeReference other) {
		
	}

	@Override
	protected Element cloneSelf() {
		return new ParenthesisTypeReference();
	}

}
