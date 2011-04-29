package subobjectjava.translate;

import java.util.List;

import jnome.core.language.Java;
import jnome.core.type.BasicJavaTypeReference;

import org.rejuse.logic.ternary.Ternary;
import org.rejuse.property.Property;

import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.member.Member;
import chameleon.core.method.Method;
import chameleon.core.modifier.Modifier;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.reference.SimpleReference;
import chameleon.core.variable.VariableDeclarator;
import chameleon.exception.ModelException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.generics.TypeParameterBlock;
import chameleon.oo.type.inheritance.SubtypeRelation;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.modifier.Interface;

public class InterfaceTransformer extends AbstractTranslator {

	public CompilationUnit interfaceCompilationUnit(CompilationUnit original, CompilationUnit implementation) throws ModelException {
		original.flushCache();
		implementation.flushCache();
		CompilationUnit interfaceCompilationUnit = implementation.cloneTo(implementation.language());
		NamespacePart interfaceNamespacePart = interfaceCompilationUnit.namespacePart(1);
		Type interfaceType = interfaceNamespacePart.declarations(Type.class).get(0);
		interfaceType.addModifier(new Interface());
		transformToInterfaceDeep(interfaceType);
		for(BasicJavaTypeReference tref: interfaceType.descendants(BasicJavaTypeReference.class)) {
			String name = tref.signature().name();
			if(name.endsWith(IMPL)) {
			  tref.setSignature(new SimpleNameSignature(interfaceName(name)));
			}
		}
		for(SimpleReference tref: interfaceType.descendants(SimpleReference.class)) {
			String name = tref.signature().name();
			if(name.endsWith(IMPL)) {
			  tref.setSignature(new SimpleNameSignature(interfaceName(name)));
			}
		}
		interfaceCompilationUnit.flushCache();
		return interfaceCompilationUnit;
	}
	
	private void transformToInterfaceDeep(Type type) throws ModelException {
		List<Type> types = type.descendants(Type.class);
		types.add(type);
		for(Type t: types) {
			transformToInterface(t);
		}
	}
	
	private void transformToInterface(Type type) throws ModelException {
		String name = type.getName();
		Java language = type.language(Java.class);
		if(name.endsWith(IMPL)) {
			copyTypeParametersIfNecessary(type);
			makePublic(type);
			List<SubtypeRelation> inheritanceRelations = type.nonMemberInheritanceRelations(SubtypeRelation.class);
			int last = inheritanceRelations.size() - 1;
			inheritanceRelations.get(last).disconnect();
			
			for(TypeElement decl: type.directlyDeclaredElements()) {
				if(decl instanceof Method) {
					((Method) decl).setImplementation(null);
					if(decl.is(language.CLASS) == Ternary.TRUE) {
						decl.disconnect();
					}
				}
				if((decl.is(language.CONSTRUCTOR) == Ternary.TRUE) ||
					 (decl.is(language.PRIVATE) == Ternary.TRUE && (! (decl instanceof Type))) ||
					 (decl instanceof VariableDeclarator && (! (decl.is(language.CLASS) == Ternary.TRUE)))) {
					decl.disconnect();
				}
				makePublic(decl);
				removeFinal(decl);
			}
			type.signature().setName(interfaceName(name));
			if(! (type.is(language.INTERFACE) == Ternary.TRUE)) {
				type.addModifier(new Interface());
			} 
		}
	}

	private void removeFinal(TypeElement<?> element) throws ModelException {
		Property property = element.language(ObjectOrientedLanguage.class).OVERRIDABLE.inverse();
		for(Modifier modifier: element.modifiers(property)) {
			element.removeModifier(modifier);
		}
	}

	private void copyTypeParametersIfNecessary(Type type) {
		Type outmost = type.farthestAncestor(Type.class);
		if(outmost != null && (! type.isTrue(type.language(Java.class).CLASS))) {
			List<TypeParameter> typeParameters = outmost.parameters(TypeParameter.class);
			ParameterBlock tpars = type.parameterBlock(TypeParameter.class);
			if(tpars == null) {
				type.addParameterBlock(new TypeParameterBlock());
			}
			for(TypeParameter<?> typeParameter:typeParameters) {
				type.addParameter(TypeParameter.class, typeParameter.clone());
			}
		}
	}


}
