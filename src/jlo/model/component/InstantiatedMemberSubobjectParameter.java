
package jlo.model.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ModelException;
import chameleon.oo.member.Member;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;
import chameleon.util.Util;


public class InstantiatedMemberSubobjectParameter extends ComponentParameter implements ComponentArgumentContainer, AbstractInstantiatedComponentParameter {


	public InstantiatedMemberSubobjectParameter(SimpleNameSignature sig, ActualComponentArgument argument) {
		super(sig);
		setArgument(argument);
	}

	public TargetDeclaration actualDeclaration() throws LookupException {
		return declaration();
	}

	private SingleAssociation<InstantiatedMemberSubobjectParameter, ActualComponentArgument> _argument = new SingleAssociation<InstantiatedMemberSubobjectParameter, ActualComponentArgument>(this);

	public ActualComponentArgument argument() {
		return _argument.getOtherEnd();
	}
	
	public void setArgument(ActualComponentArgument argument) {
		setAsParent(_argument, argument);
	}

	public List<? extends Element> children() {
		return Util.createNonNullList(argument());
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

	public LocalLookupStrategy<?> targetContext() throws LookupException {
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
	public VerificationResult verifySelf() {
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

