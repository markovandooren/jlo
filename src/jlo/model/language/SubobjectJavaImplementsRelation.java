package jlo.model.language;

import jlo.model.component.ComponentRelation;
import jnome.core.language.JavaImplementsRelation;
import chameleon.core.lookup.LookupException;
import chameleon.core.relation.StrictPartialOrder;
import chameleon.oo.member.Member;

public class SubobjectJavaImplementsRelation extends StrictPartialOrder<Member> {

	private JavaImplementsRelation _wrapped = new JavaImplementsRelation();
	
	@Override
	public boolean contains(Member first, Member second) throws LookupException {
		boolean result;
		if(first instanceof ComponentRelation && second instanceof ComponentRelation) {
			boolean defined1 = _wrapped.checkDefined(first);
			if(defined1) {
				boolean defined2 = _wrapped.checkDefined(second);
				return (!defined2) && first.signature().sameAs(second.signature());
			} else {
			  result = false;
			}
		} else {
			result = _wrapped.contains(first, second);
		}
		return result;
	}

	@Override
	public boolean equal(Member first, Member second) throws LookupException {
		return _wrapped.equal(first, second);
	}


}
