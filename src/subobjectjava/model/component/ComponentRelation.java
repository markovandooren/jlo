package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.rejuse.association.Association;
import org.rejuse.association.SingleAssociation;
import org.rejuse.logic.ternary.Ternary;
import org.rejuse.predicate.TypePredicate;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Definition;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationContainerSkipper;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.lookup.Stub;
import chameleon.core.member.HidesRelation;
import chameleon.core.member.Member;
import chameleon.core.member.MemberImpl;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.member.OverridesRelation;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.TypeWithBody;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.util.Util;

public class ComponentRelation extends MemberImpl<ComponentRelation,SimpleNameSignature, ComponentRelation> implements DeclarationWithType<ComponentRelation,SimpleNameSignature, ComponentRelation>, Definition<ComponentRelation,SimpleNameSignature, ComponentRelation>, InheritanceRelation<ComponentRelation,Type>{

	public ComponentRelation(SimpleNameSignature signature, TypeReference type) {
		setSignature(signature);
		setComponentType(type);
		setBody(new ClassBody());
	}
	
	@Override
	public ComponentRelation clone() {
		ComponentRelation result = new ComponentRelation(signature().clone(), componentTypeReference().clone());
		ConfigurationBlock configurationBlock = configurationBlock();
		if(configurationBlock != null) {
		  result.setConfigurationBlock(configurationBlock.clone());
		}
		ComponentType t = componentTypeDeclaration();
		if(t != null) {
			result.setComponentTypeDeclaration(t.clone());
		}
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}
	
	@Override
	public LookupStrategy lexicalLookupStrategy(Element child) throws LookupException {
		LookupStrategy result = parent().lexicalLookupStrategy(this);
		result = new ComponentTypeLookupStrategy(result, nearestAncestor(Type.class));
//    if(child.nearestAncestor(ClassBody.class).sameAs(body())) {
//    	LookupStrategy componentStrategy = componentType().localStrategy();
//    	final LookupStrategy lexical = result;
//    	result = language().lookupFactory().createLexicalLookupStrategy(componentStrategy, this, new LookupStrategySelector(){
//				public LookupStrategy strategy() throws LookupException {
//					return lexical;
//				}
//			});
//    }
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
	
	@Override
	public List<? extends Member> declaredMembers() {
		return Util.<Member>createSingletonList(this);
	}
	
	private SingleAssociation<ComponentRelation,TypeReference> _typeReference = new SingleAssociation<ComponentRelation,TypeReference>(this);

	public TypeReference componentTypeReference() {
		return _typeReference.getOtherEnd();
	}
	
	public Type referencedComponentType() throws LookupException {
		return componentTypeReference().getElement();
	}
	
	public ComponentType componentType() throws LookupException {
		ComponentType result = componentTypeDeclaration();
//		if(result == null) {
//		 result = referencedComponentType();
//		}
		return result;
	}

	public void setComponentType(TypeReference type) {
		if(type != null) {
			_typeReference.connectTo(type.parentLink());
		}
		else {
			_typeReference.connectTo(null);
		}
	}
	
	public void setName(String name) {
		setSignature(new SimpleNameSignature(name));
	}

	
  public void setSignature(Signature signature) {
  	if(signature instanceof SimpleNameSignature) {
  			_signature.connectTo(signature.parentLink());
  	} else if(signature == null) {
			_signature.connectTo(null);
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
  	Util.addNonNull(componentTypeDeclaration(), result);
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
  
  public List<ConfigurationClause> clauses() throws LookupException {
	  List<ConfigurationClause> result;
	  ConfigurationBlock block = configurationBlock();
	  if(block == null) {
		  result = new ArrayList<ConfigurationClause>();
	  } else {
		  result = block.clauses();
	  }
	  Set<ComponentRelation> overridden = (Set<ComponentRelation>) overriddenMembers();
	  for(ComponentRelation relation: overridden) {
		  result.addAll(relation.configurationBlock().clauses());
	  }
	  return result;
  }
  
  private SingleAssociation<ComponentRelation, ConfigurationBlock> _configurationBlock = new SingleAssociation<ComponentRelation, ConfigurationBlock>(this);

	public LocalLookupStrategy<?> targetContext() throws LookupException {
		return componentType().targetContext();
	}

	public Type declarationType() throws LookupException {
		return componentType();
	}

	public Ternary complete() {
		return Ternary.TRUE;
	}

	public Declaration declarator() {
		return this;
	}

	private SingleAssociation<ComponentRelation,ComponentType> _componentType = new SingleAssociation<ComponentRelation,ComponentType>(this);
	
	/**
	 * Set the body of this component relation.
	 */
	public void setBody(ClassBody body) {
		if(body == null) {
			_componentType.connectTo((Association)createComponentType(new ClassBody()).parentLink());
		} else {
			_componentType.connectTo((Association) createComponentType(body).parentLink());
		}
	}

  private TypeWithBody createComponentType(ClassBody body) {
  	RegularType anon = new ComponentType();
	  anon.setBody(body);
		return anon;
	}
  
  public ComponentType componentTypeDeclaration() {
  	return _componentType.getOtherEnd();
  }

  
  private void setComponentTypeDeclaration(ComponentType componentType) {
  	if(componentType == null) {
  		_componentType.connectTo(null);
  	} else {
  		_componentType.connectTo((Association<? extends ComponentType, ? super ComponentRelation>) componentType.parentLink());
  	}
  }

	@Override
	public Type superElement() throws LookupException {
		return referencedComponentType(); 
	}

	@Override
	public <X extends Member> void accumulateInheritedMembers(Class<X> kind, List<X> current) throws LookupException {
		ConfigurationBlock configurationBlock = configurationBlock();
		if(configurationBlock != null) {
			List<Member> members = configurationBlock.processedMembers();
			new TypePredicate(kind).filter(members);
		  current.addAll((Collection<? extends X>) members);
		}
		List<Member> members = componentType().processedMembers();
		new TypePredicate(kind).filter(members);
	  current.addAll((Collection<? extends X>) members);
	}

	@Override
	public <X extends Member> void accumulateInheritedMembers(DeclarationSelector<X> selector, List<X> current) throws LookupException {
		ConfigurationBlock configurationBlock = configurationBlock();
		if(configurationBlock != null) {
		  current.addAll(selector.selection(configurationBlock.processedMembers()));
		}
	  current.addAll(selector.selection(componentType().processedMembers()));
	}

	public List<? extends Member> getIntroducedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
		result.add(this);
		return result;
	}
	
	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		ConfigurationBlock configurationBlock = configurationBlock();
		List<D> result = new ArrayList<D>();
		if(selector.declaration() != this) {
			if(configurationBlock != null) {
				result = configurationBlock.membersDirectlyOverriddenBy(selector);
			}
			for(Export member: componentType().directlyDeclaredElements(Export.class)) {
				result.addAll(member.membersDirectlyOverriddenBy(selector));
			}
		}
		return result;
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
		ConfigurationBlock configurationBlock = configurationBlock();
		List<D> result = new ArrayList<D>();
		if(configurationBlock != null) {
			result = configurationBlock.membersDirectlyAliasedBy(selector);
		} 
		for(Export member: componentType().directlyDeclaredElements(Export.class)) {
			result.addAll(member.membersDirectlyAliasedBy(selector));
		}
		return result;
	}

	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		ConfigurationBlock configurationBlock = configurationBlock();
		List<D> result = new ArrayList<D>();
		if(configurationBlock != null) {
			result = configurationBlock.membersDirectlyAliasing(selector);
		}
		for(Export member: componentType().directlyDeclaredElements(Export.class)) {
			result.addAll(member.membersDirectlyAliasing(selector));
		}
		return result;
	}
	
	/**
	 * Return a clone of the given member that is 'incorporated' into the component type of this component
	 * relation. The parent of the result is a ComponentStub that is unidirectionally connected to the
	 * component type, and whose generator is set to this.
	 * 
	 * @param toBeIncorporated
	 *        The member that must be incorporated into the component type.
	 */
 /*@
   @ public behavior
   @
   @ pre toBeIncorporated != null;
   @
   @ post \result != null;
   @ post \result == toBeIncorporated.clone();
   @ post \result.parent() instanceof ComponentStub;
   @ post ((ComponentStub)\result.parent()).parent() == componentType();
   @ post ((ComponentStub)\result.parent()).generator() == this;
   @*/
	public <M extends Member> M incorporatedIntoComponentType(M toBeIncorporated) throws LookupException {
		return incorporatedInto(toBeIncorporated, componentType());
	}
	
	/**
	 * Return a clone of the given member that is 'incorporated' into the type containing this component
	 * relation. The parent of the result is a ComponentStub that is unidirectionally connected to the
	 * containing type, and whose generator is set to this.
	 * 
	 * @param toBeIncorporated
	 *        The member that must be incorporated into the containing type.
	 */
 /*@
   @ public behavior
   @
   @ pre toBeIncorporated != null;
   @
   @ post \result != null;
   @ post \result == toBeIncorporated.clone();
   @ post \result.parent() instanceof ComponentStub;
   @ post ((ComponentStub)\result.parent()).parent() == nearestAncestor(Type.class);
   @ post ((ComponentStub)\result.parent()).generator() == this;
   @*/
	public <M extends Member> M incorporatedIntoContainerType(M toBeIncorporated) throws LookupException {
		return incorporatedInto(toBeIncorporated, nearestAncestor(Type.class));
	}
	
	/**
	 * Return a clone of the given member that is 'incorporated' into the given type. 
	 * The parent of the result is a ComponentStub that is unidirectionally connected to the
	 * given type, and whose generator is set to this.
	 * 
	 * @param toBeIncorporated
	 *        The member that must be incorporated into the containing type.
	 */
 /*@
   @ public behavior
   @
   @ pre toBeIncorporated != null;
   @
   @ post \result != null;
   @ post \result == toBeIncorporated.clone();
   @ post \result.parent() instanceof ComponentStub;
   @ post ((ComponentStub)\result.parent()).parent() == type;
   @ post ((ComponentStub)\result.parent()).generator() == this;
   @*/
	protected <M extends Member> M incorporatedInto(M toBeIncorporated, Type incorporatingType) throws LookupException {
		M result = (M) toBeIncorporated.clone();
		result.setOrigin(toBeIncorporated);
		Stub redirector = new ComponentStub(this, result);
		redirector.setUniParent(incorporatingType);
		return result;
	}
	
	/**
	 * For debugging purposes because Eclipse detail formatters simply don't work.
	 */
	public String toString() {
		Type nearestAncestor = nearestAncestor(Type.class);
		return (nearestAncestor != null ? nearestAncestor.getFullyQualifiedName() + "." + signature().name() : signature().name());
	}

	public MemberRelationSelector<ComponentRelation> overridesSelector() {
		return new MemberRelationSelector<ComponentRelation>(ComponentRelation.class,this,_overridesSelector);
	}

  public OverridesRelation<? extends ComponentRelation> overridesRelation() {
  	return _overridesSelector;
  }
  
	private static OverridesRelation<ComponentRelation> _overridesSelector = new OverridesRelation<ComponentRelation>(ComponentRelation.class) {
		
		@Override
		public boolean containsBasedOnRest(ComponentRelation first, ComponentRelation second) throws LookupException {
			return true;
		}

		@Override
		public boolean containsBasedOnName(Signature first, Signature second) throws LookupException {
			return first.sameAs(second);
		}
	};
	
  public HidesRelation<? extends ComponentRelation> hidesRelation() {
		return _hidesSelector;
  }
  
  private static HidesRelation<ComponentRelation> _hidesSelector = new HidesRelation<ComponentRelation>(ComponentRelation.class) {
		
		public boolean containsBasedOnRest(ComponentRelation first, ComponentRelation second) throws LookupException {
			return true;
		}

	};

	 /*@
	   @ public behavior
	   @
	   @ post \fresh(\result);
	   @ post (\forall ComponentRelation c; 
	   @               nearestAncestors(ComponentRelation.class).overriddenMembers().contains(c); 
	   @               \result.contains(c.componentType())); 
	   @ post (\forall Type t;
	   @               \result.contains(t); 
	   @               (\exists ComponentRelation c;
	   @                        nearestAncestors(ComponentRelation.class).overriddenMembers().contains(c);
	   @                        c.componentType() == t)); 
	   @*/
	public List<Type> typesOfOverriddenSubobjects() throws LookupException {
		Set<ComponentRelation> superSubobjectRelations = (Set)overriddenMembers();
		List<Type> result = new ArrayList<Type>();
		for(ComponentRelation superSubobjectRelation: superSubobjectRelations) {
			result.add(superSubobjectRelation.componentType());
		}
		return result;
	}

	@Override
	public Type superType() throws LookupException {
		return null;
	}

}
