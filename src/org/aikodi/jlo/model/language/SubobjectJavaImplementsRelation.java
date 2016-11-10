package org.aikodi.jlo.model.language;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.relation.StrictPartialOrder;
import org.aikodi.jlo.model.subobject.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.language.JavaImplementsRelation;

public class SubobjectJavaImplementsRelation extends StrictPartialOrder<Declaration> {

	private JavaImplementsRelation _wrapped = new JavaImplementsRelation();
	
	@Override
	public boolean contains(Declaration first, Declaration second) throws LookupException {
		boolean result;
		if(first instanceof Subobject && second instanceof Subobject) {
			boolean defined1 = _wrapped.checkDefined(first);
			if(defined1) {
				boolean defined2 = _wrapped.checkDefined(second);
				return (!defined2) && first.sameSignatureAs(second);
			} else {
			  result = false;
			}
		} else {
			result = _wrapped.contains(first, second);
		}
		return result;
	}

	@Override
	public boolean equal(Declaration first, Declaration second) throws LookupException {
		return _wrapped.equal(first, second);
	}


}
