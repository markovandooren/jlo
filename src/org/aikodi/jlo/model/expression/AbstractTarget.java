package org.aikodi.jlo.model.expression;

import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.oo.statement.CheckedExceptionList;
import org.aikodi.chameleon.oo.type.Type;

public abstract class AbstractTarget extends ElementImpl implements CrossReferenceTarget {

	public AbstractTarget() {
		super();
	}
	
	public abstract Type getTargetDeclaration();
	
	public LocalLookupContext<?> targetContext() throws LookupException {
	  return getTargetDeclaration().targetContext();
	}

	/**
	 * A super target is always valid. If invocations on a super target must always resolve to an effective declaration, 
	 * as is the case in Java, then the language must add that rule. For mixins, for example, that must only be the case for
	 * an actual combination of mixins.
	 */
	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

}
