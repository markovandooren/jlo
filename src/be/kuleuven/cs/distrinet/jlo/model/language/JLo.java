package be.kuleuven.cs.distrinet.jlo.model.language;

import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.type.JLoCapturedType;
import be.kuleuven.cs.distrinet.jlo.model.type.JLoDerivedType;
import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.rejuse.junit.BasicRevision;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.relation.StrictPartialOrder;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DerivedType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Parameter;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ParameterSubstitution;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.ActualTypeArgument;

public class JLo extends Java {

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
