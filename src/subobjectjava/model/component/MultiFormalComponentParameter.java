package subobjectjava.model.component;

import jnome.core.language.Java;
import jnome.core.type.ArrayType;
import jnome.core.type.BasicJavaTypeReference;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.generics.BasicTypeArgument;

public class MultiFormalComponentParameter extends FormalComponentParameter<MultiFormalComponentParameter> {

	public MultiFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference,
			TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	public MultiFormalComponentParameter clone() {
		return new MultiFormalComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
	}

	public Type declarationType() throws LookupException {
		BasicJavaTypeReference list = (BasicJavaTypeReference) language(Java.class).createTypeReference("java.util.List");
		TypeReference clone = componentTypeReference().clone();
		BasicTypeArgument arg = language(Java.class).createBasicTypeArgument(clone);
		list.addArgument(arg);
		list.setUniParent(this);
		return list.getElement();
	}


}
