package jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.property.Property;
import org.rejuse.property.PropertyMutex;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.core.modifier.Modifier;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.member.Member;
import chameleon.oo.statement.CheckedExceptionList;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.util.Util;

public class SingleFormalComponentParameter extends FormalComponentParameter<SingleFormalComponentParameter> {

	public SingleFormalComponentParameter(SimpleNameSignature signature, TypeReference containerTypeReference, TypeReference componentTypeReference) {
		super(signature,containerTypeReference,componentTypeReference);
	}

	@Override
	public SingleFormalComponentParameter clone() {
		return new SingleFormalComponentParameter(signature().clone(), containerTypeReference().clone(), componentTypeReference().clone());
	}

	public Type declarationType() throws LookupException {
		return componentTypeReference().getElement();
	}

}
