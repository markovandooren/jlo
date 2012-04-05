package jlo.model.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jnome.core.type.AnonymousType;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.Stub;
import chameleon.oo.member.Member;
import chameleon.oo.member.MemberRelationSelector;
import chameleon.oo.type.ClassBody;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.InheritanceRelation;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.util.Util;

public class ComponentType extends AnonymousType {

	public ComponentType() {
		super("");
		addInheritanceRelation(new ComponentSubtypeRelation());
	}
	
	public List<Member> processedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
			for(Export exp: directlyDeclaredElements(Export.class)) {
				List<Member> renamedMembers = exp.processedMembers();
				result.addAll(renamedMembers);
			}
		return result;
	}
	@Override
	public List<InheritanceRelation> inheritanceRelations() throws LookupException {
		List<InheritanceRelation> result = super.inheritanceRelations();
		List<Type> superTypes = typesOfOverriddenSubobjects();
		for(Type superType:superTypes) {
			InheritanceRelation relation = new DirectSubtypeRelation(superType);
			relation.setUniParent(this);
			result.add(relation);
		}
		return result;
	}

	private List<Type> typesOfOverriddenSubobjects() throws LookupException {
		ComponentRelation relation = (ComponentRelation) nearestAncestor(ComponentRelation.class).origin();
		Set<ComponentRelation> overridden = (Set<ComponentRelation>)relation.overriddenMembers();
		List<ComponentRelation> superSubobjectRelations = new ArrayList<ComponentRelation>();
		Type outer = farthestAncestor(Type.class);
		for(ComponentRelation overriddenRelation: overridden) {
			ComponentRelation sup;
			if(overriddenRelation.farthestAncestor(Type.class) != outer) {
				sup = nearestAncestor(ComponentRelation.class).incorporatedIntoComponentType(overriddenRelation);
			} else {
				sup = overriddenRelation;
			}
			superSubobjectRelations.add(sup);
		}
		List<Type> result = new ArrayList<Type>();
		for(ComponentRelation superSubobjectRelation: superSubobjectRelations) {
			result.add(superSubobjectRelation.componentType());
		}
		return result;
	}
	
	@Override
	public boolean hasInheritanceRelation(InheritanceRelation relation) {
		return relation.parent() == this;
	}
	
	public SimpleNameSignature signature() {
		SimpleNameSignature clone = nearestAncestor(ComponentRelation.class).signature().clone();
		clone.setUniParent(this);
		return clone;
	}
	
	public TypeReference typeReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
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
