package subobjectjava.model.type;

import java.util.List;

import subobjectjava.model.component.ComponentRelation;

import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterSubstitution;
import chameleon.oo.type.Type;
import chameleon.oo.type.generics.ActualTypeArgument;
import chameleon.oo.type.inheritance.InheritanceRelation;

public class JLoDerivedType extends DerivedType {

	public <P extends Parameter> JLoDerivedType(Class<P> kind, List<P> parameters, Type baseType) {
		super(kind,parameters,baseType);
	}

	public JLoDerivedType(List<ParameterSubstitution> parameters, Type baseType) {
		super(parameters,baseType);
	}

	public JLoDerivedType(ParameterSubstitution substitution, Type baseType) {
		super(substitution,baseType);
	}

	public JLoDerivedType(Type baseType, List<ActualTypeArgument> typeArguments) {
		super(baseType, typeArguments);
	}
	
	public JLoDerivedType clone() {
		List<ParameterSubstitution> args = clonedParameters();
		return new JLoDerivedType(args,baseType());
	}

//	@Override
//	public List<InheritanceRelation> inheritanceRelations() {
//		// first take the subtype relations
//		List<InheritanceRelation> result = super.inheritanceRelations();
//		// then add the component relations
//		List<ComponentRelation> components = directlyDeclaredElements(ComponentRelation.class);
//		result.addAll(components);
//		return result;
//	}
//	
//	public List<InheritanceRelation> nonMemberInheritanceRelations() {
//		return super.inheritanceRelations();
//	}
	

}
