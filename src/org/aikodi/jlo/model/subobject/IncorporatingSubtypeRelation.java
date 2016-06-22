package org.aikodi.jlo.model.subobject;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.util.Lists;

public abstract class IncorporatingSubtypeRelation extends SubtypeRelation {
	
	public IncorporatingSubtypeRelation() {
		super(null);
	}

	
	/**
	 * The members in both lists are either incorporated, or not generated (those that are defined in the subobject itself). 
	 */
	protected <X extends Member> List<SelectionResult<X>> removeNonMostSpecificMembers(List<SelectionResult<X>> current, final List<SelectionResult<X>> potential) throws LookupException {
		if(current == Collections.EMPTY_LIST) {
			return (List)potential; 
		}
		final List<SelectionResult<X>> toAdd = Lists.create(potential.size());
		for(SelectionResult<X> mm: potential) {
			Member m = (Member)mm.finalDeclaration();
			Member originOfM = (Member) m.origin();
			boolean add = true;
			Iterator<SelectionResult<X>> iterCurrent = current.iterator();
			while(add && iterCurrent.hasNext()) {
				Member next = (Member) iterCurrent.next().finalDeclaration();
				Element origin = next.origin();
				Member alreadyInherited = next;
				//FIXME BAD DESIGN! 'origin()' seems overloaded need methods similar to nearestAncestor(Class) and nearestAncestorOrSelf.....???
				if(origin instanceof Member) {
					alreadyInherited = (Member) origin;
				}
				// Remove the already inherited member if potentially inherited member m overrides or hides it.
				if((alreadyInherited.sameAs(originOfM) || alreadyInherited.hasOverrideCompatibleSignature(originOfM) || alreadyInherited.canImplement(originOfM) || alreadyInherited.hides(originOfM))) {
					add = false;
				} else if((!alreadyInherited.sameAs(originOfM)) && (originOfM.hasOverrideCompatibleSignature(alreadyInherited) || originOfM.canImplement(alreadyInherited) || originOfM.hides(alreadyInherited))) {
					iterCurrent.remove();
				}
			}
			if(add == true) {
				toAdd.add(mm);
			}
		}
		if(current.size() > toAdd.size()) {
			current.addAll(toAdd);
			return current;
		} else {
			toAdd.addAll(current);
			return toAdd;
		}
	}

}
