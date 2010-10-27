package subobjectjava.model.expression;

import java.util.ArrayList;
import java.util.List;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.statement.CheckedExceptionList;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;

/**
 * @author Marko van Dooren
 */
public class OuterTarget extends NamespaceElementImpl<OuterTarget,Element> implements InvocationTarget<OuterTarget,Element> {

  public OuterTarget() {
	}
  

  public List<Element> children() {
  	return new ArrayList<Element>();
  }

  public OuterTarget clone() {
    return new OuterTarget();
  }

  public CheckedExceptionList getCEL() {
    return new CheckedExceptionList();
  }

  public CheckedExceptionList getAbsCEL() {
    return new CheckedExceptionList();
  }

  public TargetDeclaration getTargetDeclaration() throws LookupException {
    return nearestAncestor(ComponentRelation.class).nearestAncestor(Type.class);
  }

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
