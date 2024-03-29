package org.aikodi.jlo.model.subobject;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;

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
		return lexical().nearestAncestor(Subobject.class).componentTypeReference();
	}
	
	public Type componentType() {
		return lexical().nearestAncestor(Type.class);
	}
	
	/**
	 * A subtype component relation equals another element if the other element
	 * is a subtype relation and its parent is the component type.
	 */
	@Override
	public boolean uniSameAs(Element other) throws LookupException {
		//FIXME: the type of the instanceof should probably be changed to ComponentSubtypeRelation.
		return other instanceof SubtypeRelation && other.lexical().parent() == componentType();
	}

	private <M extends Declaration> List<SelectionResult<M>> incorporated(List<? extends SelectionResult<M>> tmp) throws LookupException {
		Subobject componentRelation = componentType().lexical().nearestAncestor(Subobject.class);
		List<SelectionResult<M>> result = new ArrayList<>();
		for(SelectionResult<M> r:tmp) {
			Declaration decl = r.template();
			SelectionResult<M> inc = r.updatedTo(componentRelation.incorporatedIntoComponentType((Declaration)decl));
			result.add(inc);
		}
		return result;
	}
	

	@Override
	public <M extends Declaration> List<SelectionResult<M>> potentiallyInheritedMembers(DeclarationSelector<M> selector) throws LookupException {
		List<? extends SelectionResult<M>> potentiallyInheritedMembers = super.potentiallyInheritedMembers(selector);
		return incorporated(potentiallyInheritedMembers);
	}

	@Override
	public <M extends Declaration> List<M> potentiallyInheritedMembers(Class<M> kind) throws LookupException {
		List<M> potentiallyInheritedMembers = super.potentiallyInheritedMembers(kind);
		return (List)incorporated((List)potentiallyInheritedMembers);
	}

	@Override
	public List<Declaration> potentiallyInheritedMembers() throws LookupException {
		List<Declaration> potentiallyInheritedMembers = super.potentiallyInheritedMembers();
		return (List)incorporated((List)potentiallyInheritedMembers);
	}

//	@Override
//	public <D extends Declaration> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
//		return (List)incorporated((List)super.membersDirectlyOverriddenBy(selector));
//	}
//
//	@Override
//	public <D extends Declaration> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		return new ArrayList<D>();
//	}
	
	protected SubtypeRelation cloneSelf() {
		return new ComponentSubtypeRelation();
	}


}
