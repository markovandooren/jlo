package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectionResult;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.SubtypeRelation;

public abstract class IncorporatingSubtypeRelation extends SubtypeRelation {
	
	public IncorporatingSubtypeRelation() {
		super(null);
	}

	
	/**
	 * The members in both lists are either incorporated, or not generated (those that are defined in the subobject itself). 
	 */
	protected <M extends Member> void removeNonMostSpecificMembers(List<SelectionResult> current, final List<? extends SelectionResult> potential) throws LookupException {
		final List<SelectionResult> toAdd = new ArrayList<SelectionResult>();
		for(SelectionResult mm: potential) {
			Member m = (Member)mm.finalDeclaration();
			M originOfM = (M) m.origin();
			boolean add = true;
			Iterator<SelectionResult> iterCurrent = current.iterator();
			while(add && iterCurrent.hasNext()) {
				M next = (M) iterCurrent.next().finalDeclaration();
				Element origin = next.origin();
				M alreadyInherited = next;
				//FIXME BAD DESIGN! 'origin()' seems overloaded need methods similar to nearestAncestor(Class) and nearestAncestorOrSelf.....???
				if(origin instanceof Member) {
					alreadyInherited = (M) origin;
				}
				// Remove the already inherited member if potentially inherited member m overrides or hides it.
				if((alreadyInherited.sameAs(originOfM) || alreadyInherited.overrides(originOfM) || alreadyInherited.canImplement(originOfM) || alreadyInherited.hides(originOfM))) {
					add = false;
				} else if((!alreadyInherited.sameAs(originOfM)) && (originOfM.overrides(alreadyInherited) || originOfM.canImplement(alreadyInherited) || originOfM.hides(alreadyInherited))) {
					iterCurrent.remove();
				}
			}
			if(add == true) {
				toAdd.add(mm);
			}
		}
		current.addAll(toAdd);
	}

}
