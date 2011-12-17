package subobjectjava.model.type;

import java.util.List;

import jnome.core.type.RegularJavaType;
import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.Overrides;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.oo.member.Member;
import chameleon.oo.member.MemberRelationSelector;
import chameleon.oo.type.Type;
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
	
	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = super.membersDirectlyOverriddenBy(selector);
		D declaration = selector.declaration();
		if(declaration.nearestAncestor(Type.class).sameAs(this)) {
			for(Overrides ov: directlyDeclaredElements(Overrides.class)) {
				if(ov.newSignature().sameAs(declaration.signature())) {
					result.add((D)ov.oldDeclaration());
				}
			}
		}
		return result;
	}

}
