package org.aikodi.jlo.model.language;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.relation.StrictPartialOrder;
import org.aikodi.chameleon.oo.type.*;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.java.core.language.Java7;
import org.aikodi.jlo.model.type.JLoCapturedType;
import org.aikodi.jlo.model.type.JLoTypeInstantiation;
import org.aikodi.rejuse.junit.BasicRevision;

public class JLo extends Java7 {

	public JLo() {
		this(NAME);
	}

	public final static String NAME = "JLo";
	
	protected JLo(String name) {
		super(name, new BasicRevision(0,1,0));
	}

	@Override
	public StrictPartialOrder<Declaration> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}
	
	@Override
	public <P extends Parameter> TypeInstantiation instantiatedType(Class<P> kind, List<P> parameters, Type baseType) {
		return new JLoTypeInstantiation(new FunctionalParameterSubstitution<P>(kind, parameters), baseType);
	}

	@Override
	public TypeInstantiation createDerivedType(Type baseType, List<TypeArgument> typeArguments) throws LookupException {
		return new JLoTypeInstantiation(baseType,typeArguments);
	}

	@Override
	public Type createdCapturedType(ParameterSubstitution<?> parameterSubstitution, Type base) {
		return new JLoCapturedType(parameterSubstitution, base);
	}
	
}
