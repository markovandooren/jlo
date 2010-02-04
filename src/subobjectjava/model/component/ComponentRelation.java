package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.DeclarationContainer;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.member.Member;
import chameleon.core.member.MemberImpl;
import chameleon.core.relation.WeakPartialOrder;
import chameleon.core.type.DeclarationWithType;
import chameleon.core.type.Type;
import chameleon.core.type.TypeReference;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.util.Util;

public class ComponentRelation extends MemberImpl<ComponentRelation,Element,SimpleNameSignature, ComponentRelation> implements DeclarationWithType<ComponentRelation,Element,SimpleNameSignature, ComponentRelation>{

	public ComponentRelation(SimpleNameSignature signature, TypeReference type) {
		setSignature(signature);
		setComponentType(type);
	}
	
	@Override
	public ComponentRelation clone() {
		ComponentRelation result = new ComponentRelation(signature().clone(), componentTypeReference().clone());
		result.setConfigurationBlock(configurationBlock().clone());
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	@Override
	public LookupStrategy lexicalLookupStrategy(Element child) throws LookupException {
		LookupStrategy result = parent().lexicalLookupStrategy(this);
//		if(child == componentTypeReference()) {
			result = new ComponentTypeLookupStrategy(result, nearestAncestor(Type.class));
//		}
		return result;
	}

  //PAPER: customize lookup
	public static class ComponentTypeLookupStrategy extends LookupStrategy {

		public ComponentTypeLookupStrategy(LookupStrategy parentStrategy, Type type) {
			_parentStrategy = parentStrategy;
			_type = type;
		}
		
		private Type _type;

		@Override
		public <D extends Declaration> D lookUp(DeclarationSelector<D> selector) throws LookupException {
			return _parentStrategy.lookUp(new DeclarationContainerSkipper<D>(selector, _type));
		}
		
		private LookupStrategy _parentStrategy;
		
	}
	
	public static class DeclarationContainerSkipper<D extends Declaration> extends DeclarationSelector<D> {

		private DeclarationSelector<D> _original;
		
		private DeclarationContainer _skipped;
		
		public DeclarationContainerSkipper(DeclarationSelector<D> original, DeclarationContainer skipped) {
			_original = original;
			_skipped = skipped;
		}

		@Override
		public List<D> declarations(DeclarationContainer container) throws LookupException {
			if(container.equals(_skipped)) {
				return new ArrayList<D>();
			} else {
				return super.declarations(container);
			}
		}

		@Override
		public WeakPartialOrder order() {
			return _original.order();
		}

		@Override
		public boolean selected(Signature signature) throws LookupException {
			return _original.selected(signature);
		}

		@Override
		public Class selectedClass() {
			return _original.selectedClass();
		}

		@Override
		public boolean selectedRegardlessOfSignature(D declaration) throws LookupException {
			return _original.selectedRegardlessOfSignature(declaration);
		}
		
	}

	public List<? extends Member> getIntroducedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
		result.add(this);
		List<Member> superMembers = componentType().members();
		ConfigurationBlock configurationBlock = configurationBlock();
		if(configurationBlock != null) {
		  result.addAll(configurationBlock.processedMembers(superMembers));
		}
		return result;
//		return declaredMembers();
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
  
  @Override
  @SuppressWarnings("unchecked")
  public List<Element> children() {
  	List<Element> result = super.children();
  	Util.addNonNull(signature(), result);
  	Util.addNonNull(componentTypeReference(), result);
  	Util.addNonNull(configurationBlock(), result);
  	return result;
  }
  
  private SingleAssociation<ComponentRelation, SimpleNameSignature> _signature = new SingleAssociation<ComponentRelation, SimpleNameSignature>(this);


  public void setConfigurationBlock(ConfigurationBlock configurationBlock) {
  	setAsParent(_configurationBlock, configurationBlock);
  }
  
  /**
   * Return the ConfigurationBlock of this member.
   */
  public ConfigurationBlock configurationBlock() {
    return _configurationBlock.getOtherEnd();
  }
  
  private SingleAssociation<ComponentRelation, ConfigurationBlock> _configurationBlock = new SingleAssociation<ComponentRelation, ConfigurationBlock>(this);

	public LookupStrategy targetContext() throws LookupException {
		return componentType().localStrategy();
	}

	public Type declarationType() throws LookupException {
		return componentType();
	}

}
