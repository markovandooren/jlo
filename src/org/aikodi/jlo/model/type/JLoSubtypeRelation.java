package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.contract.Contracts;

public class JLoSubtypeRelation extends SubtypeRelation {

	public JLoSubtypeRelation(Type type) {
		super(new DirectTypeReference(type));
	}

	public static class DirectTypeReference extends ElementImpl implements TypeReference {
		private Type _type;
		
		public DirectTypeReference(Type type) {
			Contracts.notNull(type);
		}
		
		public Type getElement() {
			return _type;
		}


		@Override
		protected Element cloneSelf() {
			return new DirectTypeReference(_type);
		}
	}
	
	public <M extends Member> List<M> potentiallyInheritedMembers(final Class<M> kind) throws LookupException {
		List<M> superMembers = superClass().members(kind);
		removeNonInheritableMembers((List)superMembers);
    return superMembers;
	}

	public List<Member> potentiallyInheritedMembers() throws LookupException {
		List<Member> superMembers = superClass().members();
		removeNonInheritableMembers((List)superMembers);
    return superMembers;
	}
	
	public <X extends Member> List<SelectionResult<X>> potentiallyInheritedMembers(
			final DeclarationSelector<X> selector) throws LookupException {
		List<SelectionResult<X>> superMembers = superClass().members(selector);
		return removeNonInheritableMembers((List)superMembers);
	}

	
}
