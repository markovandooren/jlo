package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.SubtypeRelation;

/**
 * This class represents the subtype relation between a component type, and the declared type of
 * the component. This subtype relation incorporates the inherited members into the component type.
 * @author Marko van Dooren
 *
 */
public class ComponentSubtypeRelation extends SubtypeRelation {
	
	public ComponentSubtypeRelation() {
		super(null);
	}
	
	public TypeReference superClassReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
	}
	
	public Type componentType() {
		return nearestAncestor(Type.class);
	}
	
	private ComponentType _componentType;

	/**
	 * A subtype component relation equals another element if the other element
	 * is a subtype relation and its parent is the component type.
	 */
	@Override
	public boolean uniSameAs(Element other) throws LookupException {
		//FIXME: the type of the instanceof should probably be changed to ComponentSubtypeRelation.
		return other instanceof SubtypeRelation && other.parent() == componentType();
	}

	private <M extends Member> List<M> incorporated(List<M> tmp) throws LookupException {
		ComponentRelation componentRelation = componentType().nearestAncestor(ComponentRelation.class);
		List<M> result = new ArrayList<M>();
		for(M m:tmp) {
			result.add((M) componentRelation.incorporatedIntoComponentType(m));
		}
		return result;
	}

	@Override
	public <M extends Member> List<M> potentiallyInheritedMembers(DeclarationSelector<M> selector) throws LookupException {
		List<M> potentiallyInheritedMembers = super.potentiallyInheritedMembers(selector);
//		filterMembersIncorporatedInOverriddenSubobjects(potentiallyInheritedMembers, selector);
		return incorporated(potentiallyInheritedMembers);
	}

	@Override
	public <M extends Member> List<M> potentiallyInheritedMembers(Class<M> kind) throws LookupException {
		List<M> potentiallyInheritedMembers = super.potentiallyInheritedMembers(kind);
//		filterMembersIncorporatedInOverriddenSubobjects(potentiallyInheritedMembers, kind);
		return incorporated(potentiallyInheritedMembers);
	}

	@Override
	public List<Member> potentiallyInheritedMembers() throws LookupException {
		List<Member> potentiallyInheritedMembers = super.potentiallyInheritedMembers();
//		filterMembersIncorporatedInOverriddenSubobjects(potentiallyInheritedMembers);
		return incorporated(potentiallyInheritedMembers);
	}

	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		return incorporated(super.membersDirectlyOverriddenBy(selector));
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		return incorporated(super.membersDirectlyAliasedBy(selector));
		return new ArrayList<D>();
	}
	
	/**
	 * Remove the members that are already incorporated into a subobject that is overridden by the subobject that contains
	 * this subtype relation.
	 * 
	 * @param <M>
	 * @param potentiallyInherited
	 * @param selector
	 * @throws LookupException
	 */
	protected void filterMembersIncorporatedInOverriddenSubobjects(List<Member> potentiallyInherited) throws LookupException {
		for(Type superSubobjectType: typesOfOverriddenSubobjects()) {
			filterOverriddenSubobject(superSubobjectType, potentiallyInherited);
		}
	}

	/**
	 * Remove the members that are already incorporated into a subobject that is overridden by the subobject that contains
	 * this subtype relation.
	 * 
	 * @param <M>
	 * @param potentiallyInherited
	 * @param selector
	 * @throws LookupException
	 */
	protected <M extends Member> void filterMembersIncorporatedInOverriddenSubobjects(List<M> potentiallyInherited, DeclarationSelector<M> selector) throws LookupException {
		if(nearestAncestor(Type.class).getFullyQualifiedName().equals("cityroad.City.cityTocityOne")) {
			System.out.println("debug");
		}
		for(Type superSubobjectType: typesOfOverriddenSubobjects()) {
			filterOverriddenSubobject(superSubobjectType, potentiallyInherited, selector);
		}
	}

	/**
	 * Remove the members that are already incorporated into a subobject that is overridden by the subobject that contains
	 * this subtype relation.
	 * 
	 * @param <M>
	 * @param potentiallyInherited
	 * @param selector
	 * @throws LookupException
	 */
	protected <M extends Member> void filterMembersIncorporatedInOverriddenSubobjects(List<M> potentiallyInherited, Class<M> kind) throws LookupException {
		for(Type superSubobjectType: typesOfOverriddenSubobjects()) {
			filterOverriddenSubobject(superSubobjectType, potentiallyInherited, kind);
		}
	}

	/**
	 * Filter the potentially inherited members by removing those that are already incorporated by
	 * a subobject that is overridden by the subobject that contains this subtype relation.
	 *  
	 * @param <M> The type of the member that is being looked up
	 * @param superSubobjectType The type of the overridden subobject
	 * @param potentiallyInherited A list containing the members of the super class of this subtype relation
	 * @param selector The selector that selects the member being looked up.
	 * @throws LookupException
	 */
	protected void filterOverriddenSubobject(Type superSubobjectType, List<Member> potentiallyInherited) throws LookupException {
		List<Member> potentiallyOverridden = superSubobjectType.members();
		filterOverridden(potentiallyInherited, potentiallyOverridden);
	}

	/**
	 * Filter the potentially inherited members by removing those that are already incorporated by
	 * a subobject that is overridden by the subobject that contains this subtype relation.
	 *  
	 * @param <M> The type of the member that is being looked up
	 * @param superSubobjectType The type of the overridden subobject
	 * @param potentiallyInherited A list containing the members of the super class of this subtype relation
	 * @param selector The selector that selects the member being looked up.
	 * @throws LookupException
	 */
	protected <M extends Member> void filterOverriddenSubobject(Type superSubobjectType, List<M> potentiallyInherited, DeclarationSelector<M> selector) throws LookupException {
		List<M> potentiallyOverridden = superSubobjectType.members(selector);
		filterOverridden(potentiallyInherited, potentiallyOverridden);
	}

	/**
	 * Filter the potentially inherited members by removing those that are already incorporated by
	 * a subobject that is overridden by the subobject that contains this subtype relation.
	 *  
	 * @param <M> The type of the member that is being looked up
	 * @param superSubobjectType The type of the overridden subobject
	 * @param potentiallyInherited A list containing the members of the super class of this subtype relation
	 * @param selector The kind of the objects being requested.
	 * @throws LookupException
	 */
	protected <M extends Member> void filterOverriddenSubobject(Type superSubobjectType, List<M> potentiallyInherited, Class<M> kind) throws LookupException {
		List<M> potentiallyOverridden = superSubobjectType.members(kind);
		filterOverridden(potentiallyInherited, potentiallyOverridden);
	}

	/**
	 * Filter the potentially inherited members by removing those that are already incorporated by
	 * a subobject that is overridden by the subobject that contains this subtype relation.
	 */  
	protected <M extends Member> void filterOverridden(List<M> potentiallyInherited, List<M> potentiallyOverriding) throws LookupException {
		for(M m: potentiallyOverriding) {
			Iterator<M> iterator = potentiallyInherited.iterator(); 
			while(iterator.hasNext()) {
				M p = iterator.next();
				if(m.aliasedMembers().contains(p) || m.overriddenMembers().contains(p)) {
					iterator.remove();
				}
			}
		}
	}
	
	/**
	 * Return the types of the subobjects that are overridden by the subobject that contains this subtype relation.
	 * @return
	 * @throws LookupException
	 */
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
		ComponentRelation subobjectRelation = nearestAncestor(ComponentRelation.class);
		Set<ComponentRelation> superSubobjectRelations = (Set)subobjectRelation.overriddenMembers();
		List<Type> result = new ArrayList<Type>();
		for(ComponentRelation superSubobjectRelation: superSubobjectRelations) {
			result.add(superSubobjectRelation.componentType());
		}
		return result;
	}
	
	protected <M extends Member>
  void removeNonMostSpecificMembers(List<M> current, final List<M> potential) throws LookupException {
	final List<M> toAdd = new ArrayList<M>();
	for(M m: potential) {
		boolean add = true;
		Iterator<M> iterCurrent = current.iterator();
		while(add && iterCurrent.hasNext()) {
			M alreadyInherited = (M) iterCurrent.next().origin();
			// Remove the already inherited member if potentially inherited member m overrides or hides it.
			if((alreadyInherited.sameAs(m) || alreadyInherited.overrides(m) || alreadyInherited.canImplement(m) || alreadyInherited.hides(m))) {
				add = false;
			} else if((!alreadyInherited.sameAs(m)) && (m.overrides(alreadyInherited) || m.canImplement(alreadyInherited) || m.hides(alreadyInherited))) {
				iterCurrent.remove();
			}
		}
		if(add == true) {
			toAdd.add(m);
		}
	}
	current.addAll(toAdd);
}

}