package org.aikodi.jlo.model.subobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.DeclarationRelation;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.language.LanguageImpl;
import org.aikodi.chameleon.core.lookup.Collector;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.core.lookup.Skipper;
import org.aikodi.chameleon.core.lookup.Stub;
import org.aikodi.chameleon.core.modifier.ElementWithModifiersImpl;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.member.DeclarationComparator;
import org.aikodi.chameleon.oo.member.HidesRelation;
import org.aikodi.chameleon.oo.member.MemberRelationSelector;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.ClassWithBody;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.RegularType;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.util.Util;
import org.aikodi.chameleon.util.association.Single;
import org.aikodi.rejuse.association.Association;
import org.aikodi.rejuse.predicate.TypePredicate;

/**
 * A class of subobjects.
 * 
 * @author Marko van Dooren
 */
public class Subobject extends ElementWithModifiersImpl implements DeclarationWithType, InheritanceRelation {

	/**
	 * Create a new subobject with the given name and type reference.
	 * 
	 * @param name The name of the subobject.
	 * @param typeReference A reference to the type of the subobject.
	 */
	public Subobject(String name, TypeReference typeReference) {
		this(new SimpleNameSignature(name), typeReference);
	}

	public Subobject(SimpleNameSignature signature, TypeReference type) {
		setSignature(signature);
		setComponentType(type);
		setBody(new ClassBody());
	}

	@Override
	protected Subobject cloneSelf() {
		return new Subobject((SimpleNameSignature)null,null);
	}

	public String toString() {
		try {
			return componentType().toString();
		} catch(NullPointerException exc) {
			return "";
		}
	}

//	@Override
//	public Verification verifySelf() {
//		Verification result = Valid.create();
//		Set<? extends Member> overriddenMembers = null;
//		try {
//			overriddenMembers = overriddenMembers();
//		}
//		catch(LookupException exc) {
//			result = result.and(new BasicProblem(this, "Cannot determine the (possibly empty) set of subobjects that are refined by this subobject."));
//		}
//		Type reftype = null;
//		try {
//			reftype = referencedComponentType(); 
//		} catch(LookupException exc) {
//			result = result.and(new BasicProblem(this, "Cannot determine the subobject type."));
//		}
//		if(overriddenMembers != null) {
//			for(Member rel: overriddenMembers) {
//				Subobject overridden = (Subobject) rel;
//				if(reftype != null) {
//					Type overriddenRefType = null;
//					try {
//						overriddenRefType = overridden.referencedComponentType();
//					} catch(LookupException exc) {
//						result = result.and(new BasicProblem(this, "Cannot determine the type of refined subobject "+overridden.toString()));
//					}
//					if(overriddenRefType != null) {
//						result = result.and(reftype.verifySubtypeOf(overriddenRefType,"the declared subobject type","the declared subobject type of refined subobject "+overridden.toString(), componentTypeReference())); 
//					}
//				}
//			}
//			if(overriddenMembers.isEmpty() && componentTypeReference() == null) {
//				result = result.and(new BasicProblem(this, "This subobject does not refine any subobjects and has no explicitly declared subobject type."));
//			}
//		}
//		return result;
//	}

	@Override
	public LookupContext lookupContext(Element child) throws LookupException {
		LookupContext result = parent().lookupContext(this);
		result = new ComponentTypeLookupStrategy(result, nearestAncestor(Type.class));
		return result;
	}

	//PAPER: customize lookup
	public static class ComponentTypeLookupStrategy extends LookupContext {

		public ComponentTypeLookupStrategy(LookupContext parentStrategy, Type type) {
			_parentStrategy = parentStrategy;
			_type = type;
		}

		private Type _type;

		private LookupContext _parentStrategy;

		@Override
		public <D extends Declaration> void lookUp(Collector<D> collector) throws LookupException {
			DeclarationSelector<D> selector = collector.selector();
			DeclarationSelector<D> skipper = new Skipper<D>(selector, _type);
			collector.setSelector(skipper);
			_parentStrategy.lookUp(collector);
			collector.setSelector(selector);
		}

	}

	private Single<TypeReference> _typeReference = new Single<TypeReference>(this);

	public TypeReference componentTypeReference() {
		return _typeReference.getOtherEnd();
	}

	public TypeReference superClassReference() {
		return componentTypeReference();
	}

	public Type referencedComponentType() throws LookupException {
		return componentTypeReference().getElement();
	}

	public SubobjectType componentType() {
		return componentTypeDeclaration();
	}

	public void setComponentType(TypeReference type) {
		set(_typeReference,type);
	}

	public void setName(String name) {
		setSignature(new SimpleNameSignature(name));
	}


	public void setSignature(Signature signature) {
		if(signature instanceof SimpleNameSignature || signature == null) {
			set(_signature,(SimpleNameSignature)signature);
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

	private Single<SimpleNameSignature> _signature = new Single<SimpleNameSignature>(this);


	public List<ConfigurationClause> clauses() throws LookupException {
		List<ConfigurationClause> result = new ArrayList<ConfigurationClause>();
		return result;
	}

	public LocalLookupContext<?> targetContext() throws LookupException {
		return componentType().targetContext();
	}

	public Type declarationType() throws LookupException {
		return componentType();
	}

	private Single<SubobjectType> _componentType = new Single<SubobjectType>(this, SubobjectType.class, "component type");

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

	private ClassWithBody createComponentType(ClassBody body) {
		RegularType anon = new SubobjectType();
		anon.setBody(body);
		return anon;
	}

	public SubobjectType componentTypeDeclaration() {
		return _componentType.getOtherEnd();
	}


	private void setComponentTypeDeclaration(SubobjectType componentType) {
		if(componentType == null) {
			_componentType.connectTo(null);
		} else {
			_componentType.connectTo((Association) componentType.parentLink());
		}
	}

	@Override
	public Type target() throws LookupException {
		return referencedComponentType(); 
	}

	@Override
	public <X extends Declaration> List<X> accumulateInheritedMembers(Class<X> kind, List<X> current) throws LookupException {
		List members = componentType().processedMembers();
		new TypePredicate<>(kind).filter(members);
		current.addAll((Collection<? extends X>) members);
		return current;
	}

	@Override
	public <X extends Declaration> List<SelectionResult<X>> accumulateInheritedMembers(DeclarationSelector<X> selector, List<SelectionResult<X>> current) throws LookupException {
		final List<SelectionResult<X>> potential = (List)selector.selection(componentType().processedMembers());
		return removeNonMostSpecificMembers(current, potential);
	}

	protected 
	<X extends Declaration> List<SelectionResult<X>> removeNonMostSpecificMembers(List<SelectionResult<X>> current, final List<SelectionResult<X>> potential) throws LookupException {
		final List<SelectionResult<X>> toAdd = new ArrayList<SelectionResult<X>>();
		for(SelectionResult<X> mm: potential) {
			Declaration m = (Declaration) mm.finalDeclaration();
			boolean add = true;
			Iterator<SelectionResult<X>> iterCurrent = current.iterator();
			while(add && iterCurrent.hasNext()) {
				Declaration alreadyInherited = (Declaration) iterCurrent.next().finalDeclaration();
				// Remove the already inherited member if potentially inherited member m overrides or hides it.
				if((alreadyInherited.sameAs(m) || alreadyInherited.compatibleSignature(m) || alreadyInherited.hides(m))) {
					add = false;
				} else if((!alreadyInherited.sameAs(m)) && (m.compatibleSignature(alreadyInherited) || m.hides(alreadyInherited))) {
					iterCurrent.remove();
				}
			}
			if(add == true) {
				toAdd.add(mm);
			}
		}
		current.addAll(toAdd);
		return current;
	}

	public List<? extends Declaration> getIntroducedMembers() {
		return Util.createSingletonList(this);
	}

	public List<? extends Declaration> exportedMembers() throws LookupException {
		return componentType().processedMembers();
	}

//	@Override
//	public <D extends Declaration> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		if(selector.declaration() != this) {
//			for(Export export: componentType().directlyDeclaredElements(Export.class)) {
//				result.addAll(export.membersDirectlyOverriddenBy(selector));
//			}
//		}
//		return result;
//	}
//
//	@Override
//	public <D extends Declaration> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(Export member: componentType().directlyDeclaredElements(Export.class)) {
//			result.addAll(member.membersDirectlyAliasedBy(selector));
//		}
//		return result;
//	}
//
//	public <D extends Declaration> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(Export member: componentType().directlyDeclaredElements(Export.class)) {
//			result.addAll(member.membersDirectlyAliasing(selector));
//		}
//		return result;
//	}

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
	public <M extends Declaration> M incorporatedIntoComponentType(M toBeIncorporated) throws LookupException {
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
	public <M extends Declaration> M incorporatedIntoContainerType(M toBeIncorporated) throws LookupException {
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
	protected <M extends Declaration> M incorporatedInto(M toBeIncorporated, Type incorporatingType) throws LookupException {
		M result = (M) toBeIncorporated.clone();
		result.setOrigin(toBeIncorporated);
		Stub redirector = new SubobjectMemberStub(this, result);
		redirector.setUniParent(incorporatingType);
		return result;
	}

	//	public String toString() {
	//		Type nearestAncestor = nearestAncestor(Type.class);
	//		return (nearestAncestor != null ? nearestAncestor.getFullyQualifiedName() + "." + signature().name() : signature().name());
	//	}

//	public MemberRelationSelector<Subobject> overridesSelector() {
//		return new MemberRelationSelector<Subobject>(Subobject.class,this,_overridesSelector);
//	}

	/**
	 * A subobject can override only other subobjects.
	 */
	public DeclarationRelation overridesRelation() {
		return new DeclarationRelation(true){
			@Override
			public boolean contains(Declaration overriding, Declaration overridden) throws LookupException {
	       return overridden instanceof Subobject && overridden.isTrue(overridden.language(LanguageImpl.class).OVERRIDABLE)
			&& overridden.compatibleSignature(overriding);
			}
		};
	}

//	private static OverridesRelation<Subobject> _overridesSelector = new OverridesRelation<Subobject>(Subobject.class);

	public HidesRelation<? extends Subobject> hidesRelation() {
		return _hidesSelector;
	}

	private static HidesRelation<Subobject> _hidesSelector = new HidesRelation<Subobject>(Subobject.class);

	public MemberRelationSelector<? extends Declaration> aliasSelector() {
		return new MemberRelationSelector<Subobject>(Subobject.class,this,_aliasRelation);
	};

	private static DeclarationComparator<Subobject> _aliasRelation = new DeclarationComparator<Subobject>(Subobject.class);


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
//	public List<Type> typesOfOverriddenSubobjects() throws LookupException {
//		Set<Subobject> superSubobjectRelations = (Set)overriddenMembers();
//		List<Type> result = new ArrayList<Type>();
//		for(Subobject superSubobjectRelation: superSubobjectRelations) {
//			result.add(superSubobjectRelation.componentType());
//		}
//		return result;
//	}

	@Override
	public Type superType() throws LookupException {
		return null;
	}

	@Override
	public void directlyAliasedDeclarations(Declaration declaration, Set<Declaration> accumulator)
			throws LookupException {
		throw new ChameleonProgrammerException("Not implemented yet.");
	}

	@Override
	public void directlyAliasingDeclarations(Declaration declaration, Set<Declaration> accumulator)
			throws LookupException {
		throw new ChameleonProgrammerException("Not implemented yet.");
	}

}
