package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.member.MemberImpl;
import chameleon.core.type.Type;
import chameleon.core.type.TypeReference;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.util.Util;

public class ComponentRelation extends MemberImpl<ComponentRelation,Element,SimpleNameSignature, ComponentRelation> {

	public ComponentRelation(SimpleNameSignature signature, TypeReference type) {
		setSignature(signature);
		setComponentType(type);
	}
	
	@Override
	public ComponentRelation clone() {
		ComponentRelation result = new ComponentRelation(signature().clone(), componentTypeReference().clone());
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public List<? extends Member> getIntroducedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
		result.add(this);
		List<Member> superMembers = componentType().members();
		return result;
	}
	
	@Override
	public List<? extends Member> declaredMembers() {
		return Util.<Member>createSingletonList(this);
	}
	
	private SingleAssociation<ComponentRelation,TypeReference> _typeReference = new SingleAssociation<ComponentRelation,TypeReference>(this);

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
	
  public void setSignature(SimpleNameSignature signature) {
    if(signature != null) {
      _signature.connectTo(signature.parentLink());
    } else {
      _signature.connectTo(null);
    }
  }
  
  /**
   * Return the signature of this member.
   */
  public SimpleNameSignature signature() {
    return _signature.getOtherEnd();
  }
  
  private SingleAssociation<ComponentRelation, SimpleNameSignature> _signature = new SingleAssociation<ComponentRelation, SimpleNameSignature>(this);


  public void setConfigurationBlock(ConfigurationBlock configurationBlock) {
  	setAsParent(_configurationBlock, configurationBlock);
  }
  
  /**
   * Return the ConfigurationBlock of this member.
   */
  public ConfigurationBlock ConfigurationBlock() {
    return _configurationBlock.getOtherEnd();
  }
  
  private SingleAssociation<ComponentRelation, ConfigurationBlock> _configurationBlock = new SingleAssociation<ComponentRelation, ConfigurationBlock>(this);

}
