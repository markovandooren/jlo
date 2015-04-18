package org.aikodi.jlo.model.language;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.relation.StrictPartialOrder;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.DerivedType;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.ParameterSubstitution;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.ActualTypeArgument;
import org.aikodi.jlo.model.type.JLoCapturedType;
import org.aikodi.jlo.model.type.JLoDerivedType;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.rejuse.junit.BasicRevision;

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
	public StrictPartialOrder<Member> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}
	
	public <P extends Parameter> DerivedType createDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		return new JLoDerivedType(kind, parameters, baseType);
	}

	public DerivedType createDerivedType(Type baseType, List<ActualTypeArgument> typeArguments) throws LookupException {
		return new JLoDerivedType(baseType,typeArguments);
	}

	public Type createdCapturedType(ParameterSubstitution parameterSubstitution, Type base) {
		return new JLoCapturedType(parameterSubstitution, base);
	}

}
