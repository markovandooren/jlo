package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.lookup.DeclarationCollector;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.NameSelector;
import org.aikodi.chameleon.core.validation.BasicProblem;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.oo.type.Type;

public class ComponentNameActualArgument extends SingleActualComponentArgument {

	public ComponentNameActualArgument(String name) {
		super(name);
	}
	
	@Override
	public Subobject declaration() throws LookupException {
		Type enclosing = containerType();
		DeclarationSelector<Subobject> selector = new NameSelector<Subobject>(Subobject.class) {
			@Override
			public String name() {
				return ComponentNameActualArgument.this.name();
			}
		 };
		 DeclarationCollector<Subobject> collector = new DeclarationCollector<Subobject>(selector);
		enclosing.targetContext().lookUp(collector);
		Subobject result = collector.result();
		if(result != null) {
			return result;
		} else {
			throw new LookupException("The referenced subobject cannot be found");
		}
	}


	@Override
	protected ComponentNameActualArgument cloneSelf() {
		return new ComponentNameActualArgument(name());
	}

	@Override
	public Verification verifySelf() {
		Verification result = Valid.create();
		FormalComponentParameter formal = null;
		try {
			formal = formalParameter();
		} catch (LookupException e1) {
			result = result.and(new BasicProblem(this, "Cannot determine the formal subobject parameter that corresponds to this actual subobject argument."));
		}
		if(formal != null) {
			Verification formalParameterVerificationResult = formal.verifySelf();
			formalParameterVerificationResult.setElement(this);
			// This is not ideal, the information about the original element is lost. That information
			// should be available to show to the user.
			result = result.and(formalParameterVerificationResult);
			if(result.equals(Valid.create())) {
				String typeName = formal.containerTypeReference().toString();
				Subobject rel = null;
				try {
					rel = declaration();
				} catch (LookupException e) {
					result = result.and(new BasicProblem(this, "The container type ("+typeName+") of subobject parameter "+formal.name()+" has no subobject with name "+name()));
				}
				if(rel != null) {
					try {
						Type formalType = formal.componentTypeReference().getElement();
						result = result.and(rel.componentType().verifySubtypeOf(formalType, "the type of the subobject", "the type of the formal subobject parameter", this));
					} catch (LookupException e) {
						// should not happen because the formal parameter is verified as well.
					}
				}
			}
		}
		return result;
	}

}
