package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectionResult;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MemberRelationSelector;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.SubtypeRelation;

/**
 * This class represents the subtype relation between a component type, and the declared type of
 * the component. This subtype relation incorporates the inherited members into the component type.
 * @author Marko van Dooren
 *
 */
public class ComponentSubtypeRelation extends IncorporatingSubtypeRelation {
	
	public ComponentSubtypeRelation() {
	}
	
	@Override
	public TypeReference superClassReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
	}
	
	public Type componentType() {
		return nearestAncestor(Type.class);
	}
	
	/**
	 * A subtype component relation equals another element if the other element
	 * is a subtype relation and its parent is the component type.
	 */
	@Override
	public boolean uniSameAs(Element other) throws LookupException {
		//FIXME: the type of the instanceof should probably be changed to ComponentSubtypeRelation.
		return other instanceof SubtypeRelation && other.parent() == componentType();
	}

	private List<SelectionResult> incorporated(List<? extends SelectionResult> tmp) throws LookupException {
		ComponentRelation componentRelation = componentType().nearestAncestor(ComponentRelation.class);
		List<SelectionResult> result = new ArrayList<SelectionResult>();
		for(SelectionResult r:tmp) {
			Declaration decl = r.finalDeclaration();
			SelectionResult inc = r.updatedTo(componentRelation.incorporatedIntoComponentType((Member)decl));
			result.add(inc);
		}
		return result;
	}
	

	@Override
	public <M extends Member> List<? extends SelectionResult> potentiallyInheritedMembers(DeclarationSelector<M> selector) throws LookupException {
		List<? extends SelectionResult> potentiallyInheritedMembers = super.potentiallyInheritedMembers(selector);
		return incorporated(potentiallyInheritedMembers);
	}

	@Override
	public <M extends Member> List<M> potentiallyInheritedMembers(Class<M> kind) throws LookupException {
		List<M> potentiallyInheritedMembers = super.potentiallyInheritedMembers(kind);
		return (List)incorporated(potentiallyInheritedMembers);
	}

	@Override
	public List<Member> potentiallyInheritedMembers() throws LookupException {
		List<Member> potentiallyInheritedMembers = super.potentiallyInheritedMembers();
		return (List)incorporated(potentiallyInheritedMembers);
	}

	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		return (List)incorporated(super.membersDirectlyOverriddenBy(selector));
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
		return new ArrayList<D>();
	}
	
	protected SubtypeRelation cloneSelf() {
		return new ComponentSubtypeRelation();
	}


}
