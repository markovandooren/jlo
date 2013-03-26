package be.kuleuven.cs.distrinet.jlo.model.type;

import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.jnome.core.type.JavaDerivedType;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Parameter;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ParameterSubstitution;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.ActualTypeArgument;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.InheritanceRelation;

public class JLoDerivedType extends JavaDerivedType {

	public <P extends Parameter> JLoDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		super(kind,parameters,baseType);
	}

	public JLoDerivedType(List<ParameterSubstitution> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoDerivedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution,baseType);
	}

	public JLoDerivedType(Type baseType, List<ActualTypeArgument> typeArguments) throws LookupException {
		super(baseType, typeArguments);
	}
	
	public JLoDerivedType clone() {
		List<ParameterSubstitution> args = clonedParameters();
		return new JLoDerivedType(args,baseType());
	}

	@Override
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
