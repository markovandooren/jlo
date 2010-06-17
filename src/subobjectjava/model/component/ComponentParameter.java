package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.util.Util;

public class ComponentParameter extends NamespaceElementImpl<ComponentParameter,Element> implements TargetDeclaration<ComponentParameter, Element, SimpleNameSignature, ComponentParameter> {

	public ComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		setSignature(signature);
		setContainerType(containerTypeReference);
		setComponentType(componentTypeReference);
	}
	
	private SingleAssociation<ComponentParameter,TypeReference> _containerTypeReference = new SingleAssociation<ComponentParameter,TypeReference>(this);

	public TypeReference containerTypeReference() {
		return _containerTypeReference.getOtherEnd();
	}
	
	public Type containerType() throws LookupException {
		return containerTypeReference().getType();
	}

	public void setContainerType(TypeReference type) {
		if(type != null) {
			_containerTypeReference.connectTo(type.parentLink());
		}
		else {
			_containerTypeReference.connectTo(null);
		}
	}

	private SingleAssociation<ComponentParameter,TypeReference> _typeReference = new SingleAssociation<ComponentParameter,TypeReference>(this);

	public TypeReference componentTypeReference() {
		return _typeReference.getOtherEnd();
	}
	
	public Type componentType() throws LookupException {
		return componentTypeReference().getType();
	}

	public void setComponentType(TypeReference type) {
		if(type != null) {
			_typeReference.connectTo(type.parentLink());
		}
		else {
			_typeReference.connectTo(null);
		}
	}

  public void setSignature(Signature signature) {
  	if(signature instanceof SimpleNameSignature) {
  		if(signature != null) {
  			_signature.connectTo(signature.parentLink());
  		} else {
  			_signature.connectTo(null);
  		}
  	} else {
  		throw new ChameleonProgrammerException("Setting wrong type of signature. Provided: "+(signature == null ? null :signature.getClass().getName())+" Expected SimpleNameSignature");
  	}
  }
  
  /**
   * Return the signature of this member.
   */
  public SimpleNameSignature signature() {
    return _signature.getOtherEnd();
  }

  private SingleAssociation<ComponentParameter, SimpleNameSignature> _signature = new SingleAssociation<ComponentParameter, SimpleNameSignature>(this);

	@Override
	public ComponentParameter clone() {
		return new ComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
	}

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

	public ComponentParameter actualDeclaration() throws LookupException {
		return this;
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	public Declaration<?, ?, ?, ComponentParameter> selectionDeclaration() throws LookupException {
		return this;
	}

	public LookupStrategy targetContext() throws LookupException {
		return componentType().targetContext();
	}

}
