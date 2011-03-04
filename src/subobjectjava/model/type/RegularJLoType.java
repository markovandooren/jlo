package subobjectjava.model.type;

import java.util.List;

import jnome.core.type.RegularJavaType;
import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.inheritance.InheritanceRelation;

public class RegularJLoType extends RegularJavaType {

	public RegularJLoType(SimpleNameSignature sig) {
		super(sig);
	}

	public RegularJLoType(String name) {
		super(name);
	}

	public List<InheritanceRelation> inheritanceRelations() throws LookupException {
		// first take the subtype relations
		List<InheritanceRelation> result = super.inheritanceRelations();
		// then add the component relations
		List<ComponentRelation> components;
			components = body().members(ComponentRelation.class);
			result.addAll(components);
		return result;
	}
	
	protected RegularJLoType cloneThis() {
		return new RegularJLoType(signature().clone());
	}

	
}
