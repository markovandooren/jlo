package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.ActualTypeArgument;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.jlo.model.component.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.type.JavaDerivedType;

import com.google.common.collect.ImmutableList;

public class JLoDerivedType extends JavaDerivedType {

	public <P extends Parameter> JLoDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		super(kind,parameters,baseType);
	}

	public JLoDerivedType(List<ParameterSubstitution<?>> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoDerivedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution,baseType);
	}

	public JLoDerivedType(Type baseType, List<ActualTypeArgument> typeArguments) throws LookupException {
		super(baseType, typeArguments);
	}
	
	public JLoDerivedType clone() {
		List<ParameterSubstitution<?>> args = clonedParameters();
		return new JLoDerivedType(args,baseType());
	}

	@Override
	public List<InheritanceRelation> inheritanceRelations() throws LookupException {
		return ImmutableList.<InheritanceRelation>builder()
		// first take the subtype relations
		.addAll(super.inheritanceRelations())
		.addAll(body().members(Subobject.class))
		.build();
	}
	
}
