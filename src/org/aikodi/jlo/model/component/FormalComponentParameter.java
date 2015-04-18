package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.TargetDeclaration;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.scope.Scope;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.util.association.Single;

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

	public Declaration selectionDeclaration() throws LookupException {
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
