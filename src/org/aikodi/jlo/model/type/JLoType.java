package org.aikodi.jlo.model.type;

import static be.kuleuven.cs.distrinet.rejuse.collection.CollectionOperations.forAll;

import java.util.Comparator;
import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeFixer;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.jlo.model.subobject.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.type.JavaType;

import com.google.common.collect.ImmutableList;

public interface JLoType extends JavaType {

	public ClassBody body();
	
	public default List<InheritanceRelation> inheritanceRelations() throws LookupException {
		return ImmutableList.<InheritanceRelation>builder().addAll(JavaType.super.inheritanceRelations())
		.addAll(body().members(Subobject.class)).build();
	}

	@Override
	public default boolean compatibleParameters(Type second, TypeFixer trace)
			throws LookupException {
		List<TypeMemberDeclarator> members = members(TypeMemberDeclarator.class);
		List<TypeMemberDeclarator> otherMembers = second.members(TypeMemberDeclarator.class);
		Comparator<? super TypeMemberDeclarator> c = (d1,d2) -> d1.name().compareTo(d2.name());
		members.sort(c);
		otherMembers.sort(c);
		return forAll(members, otherMembers, (f,s) -> f.contains(s, trace));
	}
}
