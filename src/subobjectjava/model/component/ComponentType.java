package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import jnome.core.type.AnonymousType;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.util.Util;

public class ComponentType extends AnonymousType {

	public ComponentType() {
		super("");
	}
	
	public SimpleNameSignature signature() {
		SimpleNameSignature clone = nearestAncestor(ComponentRelation.class).signature().clone();
		clone.setUniParent(this);
		return clone;
	}
	
	public TypeReference typeReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
//		TypeReference result = _typeReference.getOtherEnd();
////		if(result == null) {
////			
////		}
//		return result;
	}

//	/**
//	 * TARGET
//	 */
//	private SingleAssociation<ComponentType,TypeReference> _typeReference = new SingleAssociation<ComponentType,TypeReference>(this);
//
//  
//  public void setTypeReference(TypeReference type) {
//    _typeReference.connectTo(type.parentLink());
//  }
  
	@Override
	public List<InheritanceRelation> inheritanceRelations() {
		List<InheritanceRelation> result = new ArrayList<InheritanceRelation>();
		SubtypeRelation subtypeRelation = new SubtypeRelation(typeReference().clone()){

			@Override
			public boolean uniSameAs(Element other) throws LookupException {
				return other instanceof SubtypeRelation && other.parent() == ComponentType.this;
			}
			
		};
		subtypeRelation.setUniParent(this);
		result.add(subtypeRelation);
		return result;
	}

	public List<Element> children() {
		List<Element> result = new ArrayList<Element>();
		result.addAll(modifiers());
		result.addAll(parameterBlocks());
		Util.addNonNull(body(), result);
		return result;
	}

	@Override
	public ComponentType clone() {
		ComponentType result = new ComponentType();
		result.copyEverythingExceptInheritanceRelations(this, false);
		return result;
	}

}
