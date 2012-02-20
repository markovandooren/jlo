package jlo.model.component;

import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;

public class ParameterReferenceActualArgument extends SingleActualComponentArgument {

	public ParameterReferenceActualArgument(String name) {
		super(name);
	}

	@Override
	public ParameterReferenceActualArgument clone() {
		return new ParameterReferenceActualArgument(name());
	}

	@Override
	public ComponentParameter declaration() throws LookupException {
		return lexicalLookupStrategy().lookUp(new SelectorWithoutOrder<ComponentParameter>(ComponentParameter.class){
			public Signature signature() {
				return ParameterReferenceActualArgument.this.signature();
			}
		});
	}

	@Override
	public VerificationResult verifySelf() {
		VerificationResult result = Valid.create();
		try {
			if(declaration() == null) {
				result = result.and(new BasicProblem(this, "Cannot resolve component parameter reference."));
			}
		} catch (LookupException e) {
			result = result.and(new BasicProblem(this, "Cannot resolve component parameter reference."));
		}
		return result;
	}
}
