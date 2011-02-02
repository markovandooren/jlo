package subobjectjava.model.expression;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.element.Element;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.statement.CheckedExceptionList;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;

public abstract class AbstractTarget<E extends AbstractTarget<E>> extends NamespaceElementImpl<E> implements InvocationTarget<E> {

	public AbstractTarget() {
		super();
	}
	
	public abstract E clone();

	public List<Element> children() {
		return new ArrayList<Element>();
	}

	public CheckedExceptionList getCEL() {
	  return new CheckedExceptionList();
	}

	public CheckedExceptionList getAbsCEL() {
	  return new CheckedExceptionList();
	}

	public abstract Type getTargetDeclaration();
	
	public LookupStrategy targetContext() throws LookupException {
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