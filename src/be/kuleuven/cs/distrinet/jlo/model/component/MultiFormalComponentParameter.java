package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.ActualTypeArgument;

public class MultiFormalComponentParameter extends FormalComponentParameter {

	public MultiFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference,
			TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	protected MultiFormalComponentParameter cloneSelf() {
		return new MultiFormalComponentParameter(null,null,null);
	}

	public Type declarationType() throws LookupException {
		BasicJavaTypeReference list = (BasicJavaTypeReference) language(Java.class).createTypeReference("java.util.List");
		TypeReference clone = clone(componentTypeReference());
		ActualTypeArgument arg = language(Java.class).createExtendsWildcard(clone);
		list.addArgument(arg);
		list.setUniParent(this);
		return list.getElement();
	}


}
