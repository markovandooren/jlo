package subobjectjava.model.language;

import java.util.List;

import jnome.core.language.Java;
import subobjectjava.model.type.JLoCapturedType;
import subobjectjava.model.type.JLoDerivedType;
import chameleon.core.language.Language;
import chameleon.core.member.Member;
import chameleon.core.relation.StrictPartialOrder;
import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterSubstitution;
import chameleon.oo.type.Type;

public class SubobjectJava extends Java {

	public SubobjectJava() {
		this("JLow");
	}

	protected SubobjectJava(String name) {
		super(name);
	}

	@Override
	protected Language cloneThis() {
		return new SubobjectJava();
	}

	@Override
	public StrictPartialOrder<Member> implementsRelation() {
		return new SubobjectJavaImplementsRelation();
	}
	
	public <P extends Parameter> DerivedType createDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		return new JLoDerivedType(kind, parameters, baseType);
	}

	public Type createdCapturedType(ParameterSubstitution parameterSubstitution, Type base) {
		return new JLoCapturedType(parameterSubstitution, base);
	}

}
