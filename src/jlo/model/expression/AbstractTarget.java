package jlo.model.expression;

import chameleon.core.element.ElementImpl;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.reference.CrossReferenceTarget;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.statement.CheckedExceptionList;
import chameleon.oo.type.Type;

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