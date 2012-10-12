package jlo.model.component;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;

public class SingleFormalComponentParameter extends FormalComponentParameter {

	public SingleFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	public SingleFormalComponentParameter clone() {
		return new SingleFormalComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
	}

	public Type declarationType() throws LookupException {
		return componentTypeReference().getElement();
	}

}
