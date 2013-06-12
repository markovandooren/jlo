package be.kuleuven.cs.distrinet.jlo.model.type;

import java.util.List;

import com.google.common.collect.ImmutableList;

import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.jnome.core.type.CapturedType;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ParameterSubstitution;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.ActualTypeArgument;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.InheritanceRelation;

public class JLoCapturedType extends CapturedType {

	public JLoCapturedType(Type baseType, List<ActualTypeArgument> typeParameters) throws LookupException {
		super(baseType,typeParameters);
	}

	public JLoCapturedType(List<ParameterSubstitution> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoCapturedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution, baseType);
	}
	
	@Override
	public JLoCapturedType clone() {
		return new JLoCapturedType(clonedParameters(),baseType());
	}

	public List<InheritanceRelation> inheritanceRelations() throws LookupException {
		return ImmutableList.<InheritanceRelation>builder()
		.addAll(super.inheritanceRelations())
		.addAll(body().members(ComponentRelation.class))
		.build();
	}
	
	
}
