package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;

public class SingleFormalComponentParameter extends FormalComponentParameter {

	public SingleFormalComponentParameter(String name, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(name,containerTypeReference,componentTypeReference);
	}

	@Override
	protected SingleFormalComponentParameter cloneSelf() {
		return new SingleFormalComponentParameter(name(),null,null);
	}

	public Type declarationType() throws LookupException {
		return componentTypeReference().getElement();
	}

}
