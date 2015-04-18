package org.aikodi.jlo.model.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.factory.Factory;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.reference.NameReference;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.member.MemberRelationSelector;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;

import be.kuleuven.cs.distrinet.jnome.core.type.AnonymousType;
import be.kuleuven.cs.distrinet.jnome.core.type.JavaType;
import be.kuleuven.cs.distrinet.jnome.core.type.RawType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class SubobjectType extends AnonymousType {

	public SubobjectType() {
		super("");
		addInheritanceRelation(new ComponentSubtypeRelation());
	}
	
	@Override
	public void setName(String name) {
		//Ugly hack
	}
	
	@Override
	public String name() {
		return nearestAncestor(Subobject.class).name();
	}
	
	@Override
	public boolean uniSameAs(Element other) throws LookupException {
		return (other instanceof SubobjectType) &&
				   ((SubobjectType)other).name().equals(name()) && 
				   nearestAncestor(Type.class).sameAs(other.nearestAncestor(Type.class));
	}
	
	// Should be used for cloneSelf() refactoring
	private SubobjectType(boolean bogus) {
		super("");
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
		Builder<InheritanceRelation> builder = ImmutableList.<InheritanceRelation>builder();
		builder.addAll(super.inheritanceRelations());
		List<Type> superTypes = typesOfOverriddenSubobjects();
		for(Type superType:superTypes) {
			InheritanceRelation relation = new DirectSubtypeRelation(superType);
			relation.setUniParent(this);
			builder.add(relation);
		}
		return builder.build();
	}

	private List<Type> typesOfOverriddenSubobjects() throws LookupException {
		Subobject relation = (Subobject) nearestAncestor(Subobject.class).origin();
		Set<Subobject> overridden = (Set<Subobject>)relation.overriddenMembers();
		List<Subobject> superSubobjectRelations = new ArrayList<Subobject>();
		Type outer = farthestAncestor(Type.class);
		for(Subobject overriddenRelation: overridden) {
			Subobject sup;
//			if(overriddenRelation.farthestAncestor(Type.class) != outer) {
			if(!outer.subTypeOf(overriddenRelation.farthestAncestor(Type.class))) {
				sup = nearestAncestor(Subobject.class).incorporatedIntoComponentType(overriddenRelation);
			} else {
				sup = overriddenRelation;
			}
			superSubobjectRelations.add(sup);
		}
		List<Type> result = new ArrayList<Type>();
		for(Subobject superSubobjectRelation: superSubobjectRelations) {
			result.add(superSubobjectRelation.componentType());
		}
		return result;
	}
	
	@Override
	public boolean hasInheritanceRelation(InheritanceRelation relation) {
		return relation.parent() == this;
	}
	
	public SimpleNameSignature signature() {
		SimpleNameSignature clone = clone(nearestAncestor(Subobject.class).signature());
		clone.setUniParent(this);
		return clone;
	}
	
	public TypeReference typeReference() {
		return nearestAncestor(Subobject.class).componentTypeReference();
	}

	//FIXME: This can probably be done with cloneSelf() but I'll look 
	//       at it after clone is refactored.
	@Override
	public SubobjectType clone() {
		SubobjectType result = new SubobjectType();
		result.copyEverythingExceptInheritanceRelations(this, false);
		return result;
	}

	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		if(selector.declaration().nearestAncestor(ClassBody.class) == body()) {
			return nearestAncestor(Subobject.class).membersDirectlyAliasing(selector);
		} else {
			return new ArrayList<D>();//FIXME I think this is wrong and should look for deeper nested declarations.
		}
	}

	@Override
	public Type erasure() {
		Element el = farthestOrigin();
		if(el != this) {
			return ((JavaType)el).erasure();
		}
		// I am not sure whether this is correct. The memberInheritanceRelations are not erased in RawType.
		Type outmostType = farthestAncestor(Type.class);
		if(outmostType == null) {
			outmostType = this;
		}
		RawType outer;
		if(outmostType instanceof RawType) {
			outer = (RawType) outmostType;
		} else {
			outer = new RawType(outmostType);
		}
		RawType current = outer;
		List<Type> outerTypes = ancestors(Type.class);
		outerTypes.add(0, this);

		int size = outerTypes.size();
		Factory expressionFactory = language(ObjectOrientedLanguage.class).plugin(Factory.class);
		for(int i = size - 2; i>=0;i--) {
			NameReference<RawType> simpleRef = expressionFactory.createNameReference(outerTypes.get(i).name(), RawType.class);
			simpleRef.setUniParent(current);
			try {
				current = simpleRef.getElement();
			} catch (LookupException e) {
				e.printStackTrace();
				throw new ChameleonProgrammerException("An inner type of a newly created outer raw type cannot be found",e);
			}
		}
		return current;	
	}
	
//	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(InheritanceRelation relation:inheritanceRelations()) {
//			result.addAll(relation.membersDirectlyAliasedBy(selector));
//		}
//		return result;
//	}


}
