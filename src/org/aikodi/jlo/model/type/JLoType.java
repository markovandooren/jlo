package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.jlo.model.component.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.type.JavaType;

import com.google.common.collect.ImmutableList;

public interface JLoType extends JavaType {

	public ClassBody body();
	
	public default List<InheritanceRelation> inheritanceRelations() throws LookupException {
		return ImmutableList.<InheritanceRelation>builder().addAll(JavaType.super.inheritanceRelations())
		.addAll(body().members(Subobject.class)).build();
	}
	

}
