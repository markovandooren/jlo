package subobjectjava.model.language;

import jnome.core.language.Java;


import chameleon.core.language.Language;
import chameleon.core.member.Member;
import chameleon.core.relation.StrictPartialOrder;

public class SubobjectJava extends Java {

	@Override
	protected Language cloneThis() {
		return new SubobjectJava();
	}

	@Override
	public StrictPartialOrder<Member> overridesRelation() {
		return new SubobjectJavaOverridesRelation();
	}

	@Override
	public StrictPartialOrder<Member> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}
	
	
}
