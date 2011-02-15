package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
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

	@Override
	public <M extends Member> List<M> potentiallyInheritedMembers(Class<M> kind) throws LookupException {
		return incorporated(super.potentiallyInheritedMembers(kind));
	}

	private <M extends Member> List<M> incorporated(List<M> tmp) throws LookupException {
		ComponentRelation componentRelation = componentType().nearestAncestor(ComponentRelation.class);
		List<M> result = new ArrayList<M>();
		for(M m:tmp) {
			M incorporated = (M) componentRelation.incorporatedIntoComponentType(m);
			result.add(incorporated);
		}
		return result;
	}

	@Override
	public <M extends Member> List<M> potentiallyInheritedMembers(DeclarationSelector<M> selector) throws LookupException {
		return incorporated(super.potentiallyInheritedMembers(selector));
	}
	
	
}