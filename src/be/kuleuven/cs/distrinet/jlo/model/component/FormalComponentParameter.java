package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.TargetDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LocalLookupContext;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.scope.Scope;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

public abstract class FormalComponentParameter extends ComponentParameter {

	public FormalComponentParameter(String name, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(name);
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
	public Verification verifySelf() {
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

	public LocalLookupContext<?> targetContext() throws LookupException {
		return declarationType().targetContext();
	}
	
  @Override
  public boolean complete() throws LookupException {
  	return false;
  }
}
