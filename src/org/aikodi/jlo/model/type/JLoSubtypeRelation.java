package org.aikodi.jlo.model.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.LookupRedirector;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.util.Util;
import org.aikodi.contract.Contracts;

public class JLoSubtypeRelation extends SubtypeRelation {

	public JLoSubtypeRelation(Type type) {
		super(new DirectTypeReference(type));
	}

	public static class DirectTypeReference extends ElementImpl implements TypeReference {
		private Type _type;
		
		public DirectTypeReference(Type type) {
			Contracts.notNull(type);
			_type = type;
		}
		
		public Type getElement() {
			return _type;
		}


		@Override
		protected Element cloneSelf() {
			return new DirectTypeReference(_type);
		}
		
		  @Override
		    public String toString(Set<Element> visited) {
		        return getElement().name();
		    }

	}
	
	public <M extends Declaration> List<M> potentiallyInheritedMembers(final Class<M> kind) throws LookupException {
		List<M> superMembers = superClass().members(kind);
		removeNonInheritableMembers((List)superMembers);
		return incorporated((List)superMembers);
	}
	
	public List<Declaration> potentiallyInheritedMembers() throws LookupException {
		List<Declaration> superMembers = superClass().members();
		removeNonInheritableMembers((List)superMembers);
		return incorporated((List)superMembers);
	}
	
	public <M extends Declaration> List<SelectionResult<M>> potentiallyInheritedMembers(
			final DeclarationSelector<M> selector) throws LookupException {
		List<SelectionResult<M>> superMembers = superClass().members(selector);
		return incorporated(superMembers);
	}

	private <M extends Declaration> List<SelectionResult<M>> incorporated(
			List<SelectionResult<M>> superMembers) throws LookupException {
		List<SelectionResult<M>> result = new ArrayList<>(superMembers.size());
		for(SelectionResult<M> m: superMembers) {
			result.add(incorporated(m));
		}
		return result;
	}
	
	public <X extends Declaration> SelectionResult<X> incorporated(SelectionResult<X> selection) throws LookupException {
		X cloned = (X) Util.clone(selection.template());
		LookupRedirector redirector = new LookupRedirector(lexical().nearestAncestor(Type.class), cloned);
		redirector.setUniParent(selection.finalDeclaration().lexical().parent());
		SelectionResult<X> result = selection.updatedTo(cloned);
		return result;
	}

	
}
