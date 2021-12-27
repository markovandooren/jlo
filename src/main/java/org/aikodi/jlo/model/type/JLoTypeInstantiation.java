package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.FunctionalParameterSubstitution;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.java.core.type.JavaTypeInstantiation;
import org.aikodi.jlo.model.language.JLo;

public class JLoTypeInstantiation extends JavaTypeInstantiation implements JLoType {

	public JLoTypeInstantiation(List<ParameterSubstitution<?>> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoTypeInstantiation(ParameterSubstitution<?> substitution, Type baseType) {
		super(substitution,baseType);
	}

	public JLoTypeInstantiation(Type baseType, List<TypeArgument> typeArguments) throws LookupException {
		super(baseType, typeArguments);
	}
	
	public JLoTypeInstantiation clone() {
		List<ParameterSubstitution<?>> args = clonedParameters();
		return new JLoTypeInstantiation(args,baseType());
	}

	protected Type createCapturedType(List<TypeParameter> typeParameters, Type base) {
		return language(JLo.class).createdCapturedType(new FunctionalParameterSubstitution<>(TypeParameter.class,typeParameters), base);
	}

}
