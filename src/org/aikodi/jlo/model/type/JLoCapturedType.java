package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.jlo.model.subobject.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.type.CapturedType;

import com.google.common.collect.ImmutableList;

public class JLoCapturedType extends CapturedType implements JLoType {

	public JLoCapturedType(List<ParameterSubstitution<?>> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoCapturedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution, baseType);
	}
	
	@Override
	public JLoCapturedType clone() {
		return new JLoCapturedType(clonedParameters(),baseType());
	}

}
