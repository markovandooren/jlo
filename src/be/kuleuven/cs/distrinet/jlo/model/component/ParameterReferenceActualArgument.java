package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationCollector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectorWithoutOrder;
import be.kuleuven.cs.distrinet.chameleon.core.validation.BasicProblem;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.VerificationResult;

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
		DeclarationCollector<ComponentParameter> collector = new DeclarationCollector<ComponentParameter>(
			new SelectorWithoutOrder<ComponentParameter>(ComponentParameter.class){
			  public Signature signature() {
				  return ParameterReferenceActualArgument.this.signature();
			  }
		  });
		lexicalLookupStrategy().lookUp(collector);
		return collector.result();
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
