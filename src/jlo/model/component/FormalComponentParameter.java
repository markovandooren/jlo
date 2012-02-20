package jlo.model.component;

import java.util.ArrayList;
import java.util.List;

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
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.util.Util;

public abstract class FormalComponentParameter extends ComponentParameter {

	public FormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature);
		setContainerType(containerTypeReference);
		setComponentType(componentTypeReference);
	}
	
	private SingleAssociation<FormalComponentParameter,TypeReference> _containerTypeReference = new SingleAssociation<FormalComponentParameter,TypeReference>(this);

	public TypeReference containerTypeReference() {
		return _containerTypeReference.getOtherEnd();
	}
	
	public Type containerType() throws LookupException {
		return containerTypeReference().getType();
	}

	public void setContainerType(TypeReference type) {
		setAsParent(_containerTypeReference,type);
	}

	private SingleAssociation<FormalComponentParameter,TypeReference> _typeReference = new SingleAssociation<FormalComponentParameter,TypeReference>(this);

	public TypeReference componentTypeReference() {
		return _typeReference.getOtherEnd();
	}
	
	public void setComponentType(TypeReference type) {
		setAsParent(_typeReference,type);
	}

//	@Override
//	public E clone() {
//		return (E) new ComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
//	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public List<? extends Element> children() {
		List<Element> result = new ArrayList<Element>();
		Util.addNonNull(containerTypeReference(), result);
		Util.addNonNull(componentTypeReference(), result);
		Util.addNonNull(signature(), result);
		return result;
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
