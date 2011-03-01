package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import jnome.core.type.AnonymousType;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.Stub;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.util.Util;

public class ComponentType extends AnonymousType {

	public ComponentType() {
		super("");
		addInheritanceRelation(new ComponentSubtypeRelation());
	}
	
	public SimpleNameSignature signature() {
		SimpleNameSignature clone = nearestAncestor(ComponentRelation.class).signature().clone();
		clone.setUniParent(this);
		return clone;
	}
	
	public TypeReference typeReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
	}

	public List<Element> children() {
		List<Element> result = (List)modifiers();
		result.addAll(parameterBlocks());
		Util.addNonNull(body(), result);
		return result;
	}

	@Override
	public ComponentType clone() {
		ComponentType result = new ComponentType();
		result.copyEverythingExceptInheritanceRelations(this, false);
		return result;
	}

	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		if(selector.declaration().nearestAncestor(ClassBody.class) == body()) {
			return nearestAncestor(ComponentRelation.class).membersDirectlyAliasing(selector);
		} else {
			return new ArrayList<D>();//FIXME I think this is wrong and should look for deeper nested declarations.
		}
	}
	
//	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(InheritanceRelation relation:inheritanceRelations()) {
//			result.addAll(relation.membersDirectlyAliasedBy(selector));
//		}
//		return result;
//	}


}
