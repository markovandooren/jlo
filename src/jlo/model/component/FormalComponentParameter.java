package jlo.model.component;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.util.association.Single;

public abstract class FormalComponentParameter extends ComponentParameter {

	public FormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature);
		setContainerType(containerTypeReference);
		setComponentType(componentTypeReference);
	}
	
	private Single<TypeReference> _containerTypeReference = new Single<TypeReference>(this);

	public TypeReference containerTypeReference() {
		return _containerTypeReference.getOtherEnd();
	}
	
	public Type containerType() throws LookupException {
		return containerTypeReference().getType();
	}

	public void setContainerType(TypeReference type) {
		set(_containerTypeReference,type);
	}

	private Single<TypeReference> _typeReference = new Single<TypeReference>(this);

	public TypeReference componentTypeReference() {
		return _typeReference.getOtherEnd();
	}
	
	public void setComponentType(TypeReference type) {
		set(_typeReference,type);
	}

//	@Override
//	public E clone() {
//		return (E) new ComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
//	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public FormalComponentParameter actualDeclaration() throws LookupException {
		return this;
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	public TargetDeclaration selectionDeclaration() throws LookupException {
		return this;
	}

	public LocalLookupStrategy<?> targetContext() throws LookupException {
		return declarationType().targetContext();
	}
	
  @Override
  public boolean complete() throws LookupException {
  	return false;
  }
}
