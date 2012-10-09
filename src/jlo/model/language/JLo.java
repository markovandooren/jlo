package jlo.model.language;

import java.util.List;

import jlo.model.type.JLoCapturedType;
import jlo.model.type.JLoDerivedType;
import jnome.core.language.Java;

import org.rejuse.junit.BasicRevision;

import chameleon.core.lookup.LookupException;
import chameleon.core.relation.StrictPartialOrder;
import chameleon.oo.member.Member;
import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterSubstitution;
import chameleon.oo.type.Type;
import chameleon.oo.type.generics.ActualTypeArgument;

public class JLo extends Java {

	public JLo() {
		this("JLo");
	}

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
