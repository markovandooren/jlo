/**
 * 
 */
package subobjectjava.model.language;

import jnome.core.language.JavaOverridesRelation;

import org.rejuse.logic.ternary.Ternary;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.relation.StrictPartialOrder;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.Type;

public class SubobjectJavaOverridesRelation extends JavaOverridesRelation {

	// FIXME extends JavaOverridesRelation when Eclipse 3.5.2 or 2.6 is released.
	//       a bug in 3.5.1 makes the super call invalid. The hyperlink, however, points
	//       to the correct method.
	
//	private JavaOverridesRelation _wrapped = new JavaOverridesRelation();
	
	@Override
	public boolean contains(Member first, Member second) throws LookupException {
		if(first instanceof ComponentRelation && second instanceof ComponentRelation) {
	    Ternary temp = second.is(((ComponentRelation)second).language(ObjectOrientedLanguage.class).OVERRIDABLE);
	    boolean overridable;
	    if(temp == Ternary.TRUE) {
	      overridable = true;
	    } else if (temp == Ternary.FALSE) {
	      overridable = false;
	    } else {
	      throw new LookupException("The overridability of the other compent relation could not be determined.");
	    }
			boolean result = overridable && 
			first.signature().sameAs(second.signature()) && 
			((ComponentRelation)first).nearestAncestor(Type.class).subTypeOf(((ComponentRelation)second).nearestAncestor(Type.class));
			return result;
		} else {
			return super.contains(first, second);
//			return _wrapped.contains(first, second);
		}
	}

	@Override
	public boolean equal(Member first, Member second) throws LookupException {
		return first.equals(second);
	}
	
}