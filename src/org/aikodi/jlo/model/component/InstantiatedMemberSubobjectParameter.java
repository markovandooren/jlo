
package org.aikodi.jlo.model.component;

import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.scope.Scope;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.util.association.Single;


public class InstantiatedMemberSubobjectParameter extends ComponentParameter implements ComponentArgumentContainer, AbstractInstantiatedComponentParameter {


	public InstantiatedMemberSubobjectParameter(String name, ActualComponentArgument argument) {
		super(name);
		setArgument(argument);
	}

	public Declaration actualDeclaration() throws LookupException {
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
	protected InstantiatedMemberSubobjectParameter cloneSelf() {
		return new InstantiatedMemberSubobjectParameter(name(),null);
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

