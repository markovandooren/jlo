package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationCollector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.NameSelector;
import be.kuleuven.cs.distrinet.chameleon.core.validation.BasicProblem;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;

public class ParameterReferenceActualArgument extends SingleActualComponentArgument {

	public ParameterReferenceActualArgument(String name) {
		super(name);
	}

	@Override
	protected ParameterReferenceActualArgument cloneSelf() {
		return new ParameterReferenceActualArgument(name());
	}

	@Override
	public ComponentParameter declaration() throws LookupException {
		DeclarationCollector<ComponentParameter> collector = new DeclarationCollector<ComponentParameter>(
			new NameSelector<ComponentParameter>(ComponentParameter.class){
			  public String name() {
				  return ParameterReferenceActualArgument.this.name();
			  }
		  });
		lexicalContext().lookUp(collector);
		return collector.result();
	}

	@Override
	public Verification verifySelf() {
		Verification result = Valid.create();
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
