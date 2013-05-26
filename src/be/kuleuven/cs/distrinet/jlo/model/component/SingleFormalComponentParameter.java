package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;

public class SingleFormalComponentParameter extends FormalComponentParameter {

	public SingleFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	protected SingleFormalComponentParameter cloneSelf() {
		return new SingleFormalComponentParameter(null,null,null);
	}

	public Type declarationType() throws LookupException {
		return componentTypeReference().getElement();
	}

}
