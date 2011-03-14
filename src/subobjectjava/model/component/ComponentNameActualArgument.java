package subobjectjava.model.component;

import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;

public class ComponentNameActualArgument extends SingleActualComponentArgument<ComponentNameActualArgument> {

	public ComponentNameActualArgument(String name) {
		super(name);
	}
	
	@Override
	public ComponentRelation declaration() throws LookupException {
		Type enclosing = containerType();
		ComponentRelation result = enclosing.targetContext().lookUp(
				 new SelectorWithoutOrder<ComponentRelation>(ComponentRelation.class) {
					@Override
					public Signature signature() {
						return ComponentNameActualArgument.this.signature();
					}
				 }
		);
		return result;
	}


	@Override
	public ComponentNameActualArgument clone() {
		return new ComponentNameActualArgument(name());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

}
