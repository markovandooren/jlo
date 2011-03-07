package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.oo.type.inheritance.SubtypeRelation;

public abstract class IncorporatingSubtypeRelation extends SubtypeRelation {
	
	public IncorporatingSubtypeRelation() {
		super(null);
	}

	
	/**
	 * The members in both lists are either incorporated, or not generated (those that are defined in the subobject itself). 
	 */
	protected <M extends Member> void removeNonMostSpecificMembers(List<M> current, final List<M> potential) throws LookupException {
		final List<M> toAdd = new ArrayList<M>();
		for(M m: potential) {
			M originOfM = (M) m.origin();
			boolean add = true;
			Iterator<M> iterCurrent = current.iterator();
			while(add && iterCurrent.hasNext()) {
				M alreadyInherited = (M) iterCurrent.next().origin();
				// Remove the already inherited member if potentially inherited member m overrides or hides it.
				if((alreadyInherited.sameAs(originOfM) || alreadyInherited.overrides(originOfM) || alreadyInherited.canImplement(originOfM) || alreadyInherited.hides(originOfM))) {
					add = false;
				} else if((!alreadyInherited.sameAs(originOfM)) && (originOfM.overrides(alreadyInherited) || originOfM.canImplement(alreadyInherited) || originOfM.hides(alreadyInherited))) {
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
