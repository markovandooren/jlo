package jlo.model.type;

import java.util.List;

import jlo.model.component.ComponentRelation;
import jnome.core.type.CapturedType;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.ParameterSubstitution;
import chameleon.oo.type.Type;
import chameleon.oo.type.generics.ActualTypeArgument;
import chameleon.oo.type.inheritance.InheritanceRelation;

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
		// first take the subtype relations
		List<InheritanceRelation> result = super.inheritanceRelations();
		// then add the component relations
		List<ComponentRelation> components;
			components = body().members(ComponentRelation.class);
			result.addAll(components);
		return result;
	}
	
	
}
