
package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.Set;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.TargetDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupContext;
import be.kuleuven.cs.distrinet.chameleon.core.scope.Scope;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DeclarationWithType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;


public class InstantiatedMemberSubobjectParameter extends ComponentParameter implements ComponentArgumentContainer, AbstractInstantiatedComponentParameter {


	public InstantiatedMemberSubobjectParameter(SimpleNameSignature sig, ActualComponentArgument argument) {
		super(sig);
		setArgument(argument);
	}

	public TargetDeclaration actualDeclaration() throws LookupException {
		return declaration();
	}

	private Single<ActualComponentArgument> _argument = new Single<ActualComponentArgument>(this);

	public ActualComponentArgument argument() {
		return _argument.getOtherEnd();
	}
	
	public void setArgument(ActualComponentArgument argument) {
		set(_argument, argument);
	}

	public DeclarationWithType declaration() throws LookupException {
		return argument().declaration();
	}

	public FormalComponentParameter formalParameter() throws LookupException {
		Set<? extends Member> overridden = overriddenMembers();
		if(overridden.size() == 1) {
			return (FormalComponentParameter) overridden.iterator().next();
		} else if(overridden.size() > 1) {
			throw new LookupException("More than 1 overridden connector found.");
		} else {
			throw new LookupException("No overridden connector found.");
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
	public InstantiatedMemberSubobjectParameter clone() {
		return new InstantiatedMemberSubobjectParameter(signature().clone(),argument().clone());
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	public Type declarationType() throws LookupException {
		return formalParameter().declarationType();
	}

	@Override
	public Type containerType(ActualComponentArgument argument) throws LookupException {
		Set<? extends Member> overridden = overriddenMembers();
		if(overridden.size() == 1) {
			FormalComponentParameter par = (FormalComponentParameter) overridden.iterator().next();
			return par.containerType();
		} else if(overridden.size() > 1) {
			throw new LookupException("More than 1 refined connector found.");
		} else {
			throw new LookupException("No refined connector found.");
		}
	}

	@Override
	public FormalComponentParameter formalParameter(ActualComponentArgument argument) throws LookupException {
		Set<? extends Member> overridden = overriddenMembers();
		if(overridden.size() == 1) {
			return (FormalComponentParameter) overridden.iterator().next();
		} else if(overridden.size() > 1) {
			throw new LookupException("More than 1 refined connector found.");
		} else {
			throw new LookupException("No refined connector found.");
		}
	}

	@Override
	public boolean complete() throws LookupException {
		return true;
	}


}

