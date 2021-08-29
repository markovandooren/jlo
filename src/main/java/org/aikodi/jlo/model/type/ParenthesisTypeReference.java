package org.aikodi.jlo.model.type;

import java.util.Set;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.util.association.Single;

/**
 * <p>A type reference that wraps another type reference in parentheses.
 * This language construct is used for keyword-style type expressions.
 * Instead of writing <code>List&lt;T&gt;</code> or <code>Map&lt;T&gt;</p>
 * 
 * @author Marko van Dooren
 */
public class ParenthesisTypeReference extends ElementImpl implements TypeReference {

	private Single<TypeReference> _typeReference = new Single<>(this);

	/**
	 * Create a new parenthesis type reference that wraps the given type reference
	 * 
	 * @param wrappedReference The type reference to be wrapped.
	 */
	public ParenthesisTypeReference(TypeReference wrappedReference) {
	  set(_typeReference, wrappedReference);
	}
	
	/**
	 * @return The type reference that is wrapped by this parenthesis type
	 * reference.
	 */
	public TypeReference wrappedReference() {
		return _typeReference.getOtherEnd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return The type referenced by the wrapped type reference.
	 */
	@Override
	public Type getElement() throws LookupException {
		return wrappedReference().getElement();
	}

	@Override
	protected Element cloneSelf() {
		return new ParenthesisTypeReference(null);
	}
	
	   @Override
	    public String toString(Set<Element> visited) {
	        return "("+wrappedReference().toString(visited)+")";
	    }


}
