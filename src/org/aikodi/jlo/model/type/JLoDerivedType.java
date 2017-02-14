package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.java.core.type.JavaTypeInstantiation;

public class JLoDerivedType extends JavaTypeInstantiation implements JLoType {

	public <P extends Parameter> JLoDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		super(kind,parameters,baseType);
	}

	public JLoDerivedType(List<ParameterSubstitution<?>> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoDerivedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution,baseType);
	}

	public JLoDerivedType(Type baseType, List<TypeArgument> typeArguments) throws LookupException {
		super(baseType, typeArguments);
	}
	
	public JLoDerivedType clone() {
		List<ParameterSubstitution<?>> args = clonedParameters();
		return new JLoDerivedType(args,baseType());
	}

}
