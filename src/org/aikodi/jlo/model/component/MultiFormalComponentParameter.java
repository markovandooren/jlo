package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.ActualTypeArgument;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;

public class MultiFormalComponentParameter extends FormalComponentParameter {

	public MultiFormalComponentParameter(String name, TypeReference containerTypeReference,
			TypeReference componentTypeReference) {
		super(name,containerTypeReference,componentTypeReference);
	}

	@Override
	protected MultiFormalComponentParameter cloneSelf() {
		return new MultiFormalComponentParameter(name(),null,null);
	}

	public Type declarationType() throws LookupException {
		BasicJavaTypeReference list = (BasicJavaTypeReference) language(Java7.class).createTypeReference("java.util.List");
		TypeReference clone = clone(componentTypeReference());
		ActualTypeArgument arg = language(Java7.class).createExtendsWildcard(clone);
		list.addArgument(arg);
		list.setUniParent(this);
		return list.getElement();
	}


}
