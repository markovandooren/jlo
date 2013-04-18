package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.TargetDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupContext;
import be.kuleuven.cs.distrinet.chameleon.core.scope.Scope;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.VerificationResult;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DeclarationWithType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DerivedType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Parameter;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ParameterBlock;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;


public class InstantiatedComponentParameter extends ComponentParameter implements AbstractInstantiatedComponentParameter {

	
	public InstantiatedComponentParameter(SimpleNameSignature sig, ActualComponentArgument argument) {
		super(sig);
		_argument = argument;
	}

	public TargetDeclaration actualDeclaration() throws LookupException {
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
		Type enclosing = nearestAncestor(DerivedType.class);
		if(enclosing == null) {
			throw new LookupException("The instantiated single component parameter is not in a derived type.");
		} else {
			ComponentRelation result = null;
			Type original = (Type) enclosing.origin();
			ParameterBlock<?> block = original.parameterBlock(ComponentParameter.class);
			FormalComponentParameter p = null;
			for(Parameter param: block.parameters()) {
				if(param.signature().sameAs(signature())) {
					p = (FormalComponentParameter) param;
				}
			}
			return p;
		}
	}

	public LookupContext targetContext() throws LookupException {
		return declaration().targetContext();
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	@Override
	public TargetDeclaration selectionDeclaration() throws LookupException {
		return this;
	}

	@Override
	public InstantiatedComponentParameter clone() {
		return new InstantiatedComponentParameter(signature().clone(),argument());
	}

	@Override
	public VerificationResult verifySelf() {
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
