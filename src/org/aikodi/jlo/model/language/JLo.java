package org.aikodi.jlo.model.language;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.relation.StrictPartialOrder;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeInstantiation;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.type.JavaTypeReference;
import org.aikodi.jlo.model.type.JLoCapturedType;
import org.aikodi.jlo.model.type.JLoDerivedType;
import org.aikodi.rejuse.junit.BasicRevision;

public class JLo extends Java7 {

	public JLo() {
		this(NAME);
	}

	public final static String NAME = "JLo";
	
	protected JLo(String name) {
		super(name, new BasicRevision(0,1,0));
	}

//	@Override
//	protected Language cloneThis() {
//		return new JLo(null);
//	}

	@Override
	public StrictPartialOrder<Declaration> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}
	
	public <P extends Parameter> TypeInstantiation createDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		return new JLoDerivedType(kind, parameters, baseType);
	}

	public TypeInstantiation createDerivedType(Type baseType, List<TypeArgument> typeArguments) throws LookupException {
		return new JLoDerivedType(baseType,typeArguments);
	}

	public Type createdCapturedType(ParameterSubstitution parameterSubstitution, Type base) {
		return new JLoCapturedType(parameterSubstitution, base);
	}
	
}
