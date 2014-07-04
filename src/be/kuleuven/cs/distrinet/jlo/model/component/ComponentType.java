package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.jnome.core.type.AnonymousType;
import be.kuleuven.cs.distrinet.jnome.core.type.JavaType;
import be.kuleuven.cs.distrinet.jnome.core.type.RawType;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.reference.SimpleReference;
import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.ExpressionFactory;
import be.kuleuven.cs.distrinet.chameleon.oo.language.ObjectOrientedLanguage;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MemberRelationSelector;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ClassBody;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.InheritanceRelation;

public class ComponentType extends AnonymousType {

	public ComponentType() {
		super("");
		addInheritanceRelation(new ComponentSubtypeRelation());
	}
	
	@Override
	public void setName(String name) {
		//Ugly hack
	}
	
	@Override
	public String name() {
		return nearestAncestor(ComponentRelation.class).name();
	}
	
	// Should be used for cloneSelf() refactoring
	private ComponentType(boolean bogus) {
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
		SimpleNameSignature clone = clone(nearestAncestor(ComponentRelation.class).signature());
		clone.setUniParent(this);
		return clone;
	}
	
	public TypeReference typeReference() {
		return nearestAncestor(ComponentRelation.class).componentTypeReference();
	}

	//FIXME: This can probably be done with cloneSelf() but I'll look 
	//       at it after clone is refactored.
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
		ExpressionFactory expressionFactory = language(ObjectOrientedLanguage.class).plugin(ExpressionFactory.class);
		for(int i = size - 2; i>=0;i--) {
			SimpleReference<RawType> simpleRef = expressionFactory.createSimpleReference(outerTypes.get(i).name(), RawType.class);
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
