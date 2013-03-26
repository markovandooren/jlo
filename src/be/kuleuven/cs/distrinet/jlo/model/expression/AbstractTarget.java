package be.kuleuven.cs.distrinet.jlo.model.expression;

import be.kuleuven.cs.distrinet.chameleon.core.element.ElementImpl;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LocalLookupStrategy;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.reference.CrossReferenceTarget;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.VerificationResult;
import be.kuleuven.cs.distrinet.chameleon.oo.statement.CheckedExceptionList;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;

public abstract class AbstractTarget extends ElementImpl implements CrossReferenceTarget {

	public AbstractTarget() {
		super();
	}
	
	public abstract AbstractTarget clone();

	public CheckedExceptionList getCEL() {
	  return new CheckedExceptionList();
	}

	public CheckedExceptionList getAbsCEL() {
	  return new CheckedExceptionList();
	}

	public abstract Type getTargetDeclaration();
	
	public LocalLookupStrategy<?> targetContext() throws LookupException {
	  return getTargetDeclaration().targetContext();
	}

	/**
	 * A super target is always valid. If invocations on a super target must always resolve to an effective declaration, 
	 * as is the case in Java, then the language must add that rule. For mixins, for example, that must only be the case for
	 * an actual combination of mixins.
	 */
	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

}
