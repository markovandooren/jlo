package subobjectjava.model.component;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.lookup.LookupException;
import chameleon.exception.ChameleonProgrammerException;


public abstract class InstantiatedComponentParameter<E extends InstantiatedComponentParameter<E>> extends ComponentParameter<E> {

	
	public InstantiatedComponentParameter(SimpleNameSignature sig) {
		super(sig);
	}

	public TargetDeclaration actualDeclaration() throws LookupException {
		throw new ChameleonProgrammerException();
	}


}
