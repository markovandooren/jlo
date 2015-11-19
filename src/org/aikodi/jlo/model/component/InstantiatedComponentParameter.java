package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.scope.Scope;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.TypeInstantiation;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.ParameterBlock;
import org.aikodi.chameleon.oo.type.Type;


public class InstantiatedComponentParameter extends ComponentParameter implements AbstractInstantiatedComponentParameter {

	
	public InstantiatedComponentParameter(String name, ActualComponentArgument argument) {
		super(name);
		_argument = argument;
	}

	public Declaration actualDeclaration() throws LookupException {
		return declaration();
	}

	private ActualComponentArgument _argument;
	
	public ActualComponentArgument argument() {
		return _argument;
	}
	
	public DeclarationWithType declaration() throws LookupException {
		return argument().declaration();
	}
	
	public FormalComponentParameter formalParameter() throws LookupException {
		Type enclosing = nearestAncestor(TypeInstantiation.class);
		if(enclosing == null) {
			throw new LookupException("The instantiated single component parameter is not in a derived type.");
		} else {
			Type original = (Type) enclosing.origin();
			ParameterBlock<?> block = original.parameterBlock(ComponentParameter.class);
			FormalComponentParameter p = null;
			for(Parameter param: block.parameters()) {
				if(param.sameSignatureAs(this)) {
					p = (FormalComponentParameter) param;
				}
			}
			return p;
		}
	}

	public LocalLookupContext<?> targetContext() throws LookupException {
		return declaration().targetContext();
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	@Override
	public Declaration selectionDeclaration() throws LookupException {
		return this;
	}

	@Override
	protected InstantiatedComponentParameter cloneSelf() {
		return new InstantiatedComponentParameter(name(),argument());
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	public Type declarationType() throws LookupException {
		return formalParameter().declarationType();
	}

	@Override
	public boolean complete() throws LookupException {
		return true;
	}


}
