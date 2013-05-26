package be.kuleuven.cs.distrinet.jlo.translate;

import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.document.Document;
import be.kuleuven.cs.distrinet.chameleon.core.modifier.Modifier;
import be.kuleuven.cs.distrinet.chameleon.core.namespacedeclaration.NamespaceDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.reference.SimpleReference;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.language.ObjectOrientedLanguage;
import be.kuleuven.cs.distrinet.chameleon.oo.method.Method;
import be.kuleuven.cs.distrinet.chameleon.oo.type.BasicTypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.ParameterBlock;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeElement;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.TypeParameter;
import be.kuleuven.cs.distrinet.chameleon.oo.type.generics.TypeParameterBlock;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.InheritanceRelation;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.SubtypeRelation;
import be.kuleuven.cs.distrinet.chameleon.oo.variable.VariableDeclarator;
import be.kuleuven.cs.distrinet.chameleon.support.modifier.Interface;
import be.kuleuven.cs.distrinet.chameleon.util.Util;
import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;
import be.kuleuven.cs.distrinet.rejuse.property.Property;

public class InterfaceTransformer extends AbstractTranslator {

	public Document interfaceCompilationUnit(Document original, Document implementation) throws ModelException {
		Document interfaceCompilationUnit = null;
		Java lang = original.language(Java.class);
		if(! original.isTrue(lang.INTERFACE)) {
			original.flushCache();
			implementation.flushCache();

			Type implementationType = original.namespaceDeclaration(1).declarations(Type.class).get(0);
			if(splitClass(implementationType)) {
				interfaceCompilationUnit = implementation.cloneTo(implementation.view());
				NamespaceDeclaration interfaceNamespacePart = interfaceCompilationUnit.namespaceDeclaration(1);
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
			} else {
				for(Type type: implementation.descendants(Type.class)) {
					List<InheritanceRelation> relations = type.inheritanceRelations();
					for(InheritanceRelation relation: relations) {
						TypeReference t = relation.superClassReference();
						if(t.hasMetadata(IMPL)) {
							if(t instanceof BasicTypeReference) {
								BasicTypeReference b = (BasicTypeReference) t;
								b.setName(b.name()+IMPL);
							}
						}
					}
				}
			}
		}
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
		String name = type.name();
		Java language = type.language(Java.class);
		if(name.endsWith(IMPL)) {
			copyTypeParametersIfNecessary(type);
			makePublic(type);
			List<SubtypeRelation> inheritanceRelations = type.nonMemberInheritanceRelations(SubtypeRelation.class);
			int last = inheritanceRelations.size() - 1;
			inheritanceRelations.get(last).disconnect();
			
			for(TypeElement decl: type.directlyDeclaredElements()) {
				transform(language, decl);
			}
			type.signature().setName(interfaceName(name));
			if(! (type.is(language.INTERFACE) == Ternary.TRUE)) {
				type.addModifier(new Interface());
			} 
		}
	}

	private void transform(Java language, TypeElement decl) throws ModelException {
		if(decl instanceof Method) {
			((Method) decl).setImplementation(null);
			if(decl.is(language.CLASS) == Ternary.TRUE) {
				decl.disconnect();
			}
		}
		if(
			 (decl.parent() != null) && (
			 (decl.is(language.CONSTRUCTOR) == Ternary.TRUE) ||
			 (decl.is(language.PRIVATE) == Ternary.TRUE && (! (decl instanceof Type))) ||
			 (decl instanceof VariableDeclarator ))) { // && (! (decl.is(language.CLASS) == Ternary.TRUE))
			decl.disconnect();
		} 
		if(decl.parent() != null) {
			makePublic(decl);
			removeNonInterfaceModifiers(decl);
		}
	}

	private void removeNonInterfaceModifiers(TypeElement element) throws ModelException {
		removeFinal(element);
		removeSynchronized(element);
	}

	private void removeSynchronized(TypeElement element) throws ModelException {
		Property property = element.language(Java.class).SYNCHRONIZED;
		for(Modifier modifier: element.modifiers(property)) {
			element.removeModifier(modifier);
		}
	}
	
	private void removeFinal(TypeElement element) throws ModelException {
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
			for(TypeParameter typeParameter:typeParameters) {
				type.addParameter(TypeParameter.class, Util.clone(typeParameter));
			}
		}
	}


}
