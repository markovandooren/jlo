package jlo.model.component;

import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;

public class ComponentNameActualArgument extends SingleActualComponentArgument {

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
		if(result != null) {
			return result;
		} else {
			throw new LookupException("The referenced subobject cannot be found");
		}
	}


	@Override
	public ComponentNameActualArgument clone() {
		return new ComponentNameActualArgument(name());
	}

	@Override
	public VerificationResult verifySelf() {
		VerificationResult result = Valid.create();
		FormalComponentParameter formal = null;
		try {
			formal = formalParameter();
		} catch (LookupException e1) {
			result = result.and(new BasicProblem(this, "Cannot determine the formal subobject parameter that corresponds to this actual subobject argument."));
		}
		if(formal != null) {
			VerificationResult formalParameterVerification = formal.verifySelf();
			formalParameterVerification.setElement(this);
			// This is not ideal, the information about the original element is lost. That information
			// should be available to show to the user.
			result = result.and(formalParameterVerification);
			if(result.equals(Valid.create())) {
				String typeName = formal.containerTypeReference().toString();
				ComponentRelation rel = null;
				try {
					rel = declaration();
				} catch (LookupException e) {
					result = result.and(new BasicProblem(this, "The container type ("+typeName+") of subobject parameter "+formal.signature()+" has no subobject with name "+signature().name()));
				}
				if(rel != null) {
					try {
						Type formalType = formal.componentTypeReference().getElement();
						result = result.and(rel.componentType().verifySubtypeOf(formalType, "the type of the subobject", "the type of the formal subobject parameter"));
					} catch (LookupException e) {
						// should not happen because the formal parameter is verified as well.
					}
				}
			}
		}
		return result;
	}

}