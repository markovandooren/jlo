package subobjectjava.model.component;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.oo.type.TypeReference;

public class SingleFormalComponentParameter extends FormalComponentParameter<SingleFormalComponentParameter> {

	public SingleFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	public SingleFormalComponentParameter clone() {
		return new SingleFormalComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
	}

}
