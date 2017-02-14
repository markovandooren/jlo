package org.aikodi.jlo.translate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.RegularMemberVariable;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Private;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.modifier.Implements;
import org.aikodi.java.core.type.BasicJavaTypeReference;
import org.aikodi.java.core.type.JavaPureWildcard;
import org.aikodi.java.core.type.RegularJavaType;
import org.aikodi.jlo.model.subobject.Subobject;
import org.aikodi.jlo.model.subobject.SubobjectType;
import org.aikodi.jlo.model.type.KeywordTypeArgument;
import org.aikodi.jlo.model.type.KeywordTypeReference;
import org.aikodi.jlo.model.type.TypeMemberDeclarator;

public class Java8ClassGenerator extends AbstractJava8Generator {

	public Document createImplementation(Document javaDocument) throws LookupException {
		removeNormalMethods(javaDocument);
		implementOwnInterfaces(javaDocument);
		replaceSubobjects(javaDocument);
		addFields(javaDocument);
		renameConstructorCalls(javaDocument);
		addTypeParameterToOwnClass(javaDocument);
		return javaDocument;
	}

	/**
	 * Replace each subobject with a class that represents the Java equivalent
	 * of the subobject class.
	 * 
	 * @param javaDocument The document in which the subobjects must be replaced.
	 */
	protected void replaceSubobjects(Document javaDocument) throws LookupException {
		javaDocument.apply(Subobject.class, javaSubobject -> javaSubobject.disconnect());
		javaDocument.apply(Type.class, javaType -> {
			if(! isGenerated(javaType)) {
				Type jloType = (Type) javaType.origin();
				expandSubobjects(javaType, jloType);
			}
		});
	}

	/**
	 * Expand the subobjects of the given JLo type an add them to the given Java type.
	 * 
	 * @param javaType The type in which the expansions must be added.
	 * @param jloType The type that contains the subobjects that must be expanded.
	 */
	protected void expandSubobjects(Type javaType, Type jloType) throws LookupException {
		try {
			List<Subobject> jloSubobjects = jloType.members(Subobject.class);
			jloSubobjects.forEach(jloSubobject -> {
				//TypeReference subobjectTypeReference = jloSubobject.clone(jloSubobject.superClassReference());
				try {
					TypeReference subobjectTypeReference = subobjectTypeReference(jloSubobject, java(javaType));
					MemberVariableDeclarator field = new MemberVariableDeclarator(subobjectTypeReference);
					field.add(new VariableDeclaration(subobjectFieldName(jloSubobject)));
					field.addModifier(new Private());
					javaType.add(field);
					Method getter = createSubobjectGetterTemplate(jloSubobject,java(javaType));
					createGetterImplementation(subobjectFieldName(jloSubobject), getter);
					javaType.add(getter);
					RegularJavaType subobjectImplementation = createSubobjectImplementation(jloSubobject, javaType);
					expandSubobjects(subobjectImplementation, jloSubobject.componentType());
				} catch (LookupException e) {
					throw new ChameleonProgrammerException(e);
				}
			});
		} catch (LookupException e) {
			throw new ChameleonProgrammerException(e);
		}
	}

	protected RegularJavaType createSubobjectImplementation(Subobject jloSubobject, Type javaParentType) throws LookupException {
		String subobjectImplementationName = subobjectImplementationName(jloSubobject);
		//Util.debug(subobjectImplementationName.equals("upperBoundImpl") && jloSubobject.lexical().farthestAncestor(Type.class).name().equals("Radio"));
		RegularJavaType javaSubobjectImplementation = (RegularJavaType) ooFactory(jloSubobject).createRegularType(subobjectImplementationName);
		javaSubobjectImplementation.setBody(new ClassBody());
		javaSubobjectImplementation.addModifier(new Public());
		javaParentType.add(javaSubobjectImplementation);
		addFields(javaSubobjectImplementation, jloSubobject.componentType());
		SubtypeRelation javaImplementsRelation = new SubtypeRelation(convertToJavaReference(jloSubobject.componentTypeReference()));
		javaImplementsRelation.addModifier(new Implements());
		javaSubobjectImplementation.addInheritanceRelation(javaImplementsRelation);
		Type jloParentType = jloSubobject.nearestAncestor(Type.class);
		addTypeParameters(javaImplementsRelation, jloParentType);
		return javaSubobjectImplementation;
	}
	
	/**
	 * <p>Convert a JLo reference to a Java reference.</p>
	 * <ul>
	 *   <li>Keyword references are replaced by generic type references.</li>
	 * </ul>
	 * 
	 * @param jloReference
	 * @return
	 * @throws LookupException
	 */
	protected TypeReference convertToJavaReference(TypeReference jloReference) throws LookupException {
		if(jloReference instanceof KeywordTypeReference) {
			KeywordTypeReference keywordReference = (KeywordTypeReference) jloReference;
			BasicJavaTypeReference result = (BasicJavaTypeReference) keywordReference.clone(keywordReference.typeConstructorReference());
			Type jloTypeConstructor = keywordReference.typeConstructorReference().getElement();
			List<TypeMemberDeclarator> jloTypeMembersInAlphabeticOrder = jloTypeConstructor.members(TypeMemberDeclarator.class).stream().sorted((d1,d2) -> d1.name().compareTo(d2.name())).collect(Collectors.toList());
			for(TypeMemberDeclarator jloTypeMember: jloTypeMembersInAlphabeticOrder) {
				String name = jloTypeMember.name();
				KeywordTypeArgument keyword = keywordReference.argument(name);
				if(keyword != null) {
				  TypeArgument argument = keyword.argument();
				  result.addArgument(argument.clone(argument));
				} else {
					result.addArgument(new JavaPureWildcard());
				}
			};
			Util.debug(result.typeArguments().size() == 2);
			return result;
		} else {
			return jloReference.clone(jloReference);
		}
	}

	private String subobjectImplementationName(Subobject subobject) {
		return subobject.name()+IMPLEMENTATION_SUFFIX;
	}

	protected void removeNormalMethods(Document result) {
		result.apply(Method.class, m -> {
			Element origin = m.origin();
			//      if (!origin.isTrue(jlo(origin).CONSTRUCTOR)) {
			m.disconnect();
			//      }
		});
	}

	protected void implementOwnInterfaces(Document javaDocument) {
		javaDocument.apply(Type.class, javaType -> {
			try {
				Java7 java = java(javaDocument);
				// Only disconnect inheritance relations that are explicit, and
				// that are no subobjects
				if(!isGenerated(javaType)) {
					Type jloType = (Type) javaType.origin();
					javaType.explicitNonMemberInheritanceRelations().forEach(javaInheritanceRelation -> javaInheritanceRelation.disconnect());
					BasicJavaTypeReference superTypeReference = java.createTypeReference(javaType.name());
					SubtypeRelation relation = new SubtypeRelation(superTypeReference);
					relation.addModifier(new Implements());
					javaType.addInheritanceRelation(relation);
					addTypeParameters(relation,jloType);
					javaType.setName(implementationName(javaType));
					javaType.modifiers(java.SCOPE_MUTEX).forEach(m -> m.disconnect());
				}
			} catch (ModelException e) {
				throw new ChameleonProgrammerException(e);
			}
			javaType.addModifier(new Public());
		});
	}

	protected void addFields(Document javaDocument) {
		javaDocument.apply(MemberVariableDeclarator.class, javaMethod -> {
			if(! isGenerated(javaMethod)) {
				javaMethod.disconnect();
			}
		});
		javaDocument.apply(Type.class, t -> {
			if(! (t instanceof SubobjectType) && ! isGenerated(t)) {
				Type originalType = (Type) t.origin();
				try {
					addFields(t, originalType);
				} catch (Exception e) {
					throw new ChameleonProgrammerException(e);
				}
			} 
		});
	}

	private void addFields(Type to, Type from) throws LookupException {
		Set<Type> allSuperTypes = from.getSelfAndAllSuperTypesView();
		List<RegularMemberVariable> collect = allSuperTypes.stream().<RegularMemberVariable>flatMap(x -> {
			try {
				return x.localMembers(RegularMemberVariable.class).stream();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ChameleonProgrammerException(e);
			}
		}).collect(Collectors.toList());

		for(RegularMemberVariable v : collect) {
			MemberVariableDeclarator jloMemberVariableDeclarator = v.nearestAncestor(MemberVariableDeclarator.class);
			MemberVariableDeclarator f = new MemberVariableDeclarator(clone(jloMemberVariableDeclarator.typeReference()));
			VariableDeclaration variableDeclaration = (VariableDeclaration) v.origin();
			String fieldName = fieldName(variableDeclaration);
			//      Util.debug(fieldName.contains(IMPLEMENTATION_SUFFIX));
			f.add(new VariableDeclaration(fieldName));
			f.addModifier(new Private());
			to.add(f);
			Method getter = createGetterTemplate(jloMemberVariableDeclarator);
			createGetterImplementation(fieldName, getter);
			to.add(getter);
			Method setter = createSetterTemplate(jloMemberVariableDeclarator);
			setter.addModifier(new Public());
			Block setterBody = new Block();
			setter.setImplementation(new RegularImplementation(setterBody));
			setterBody.addStatement(new StatementExpression(new AssignmentExpression(new NameExpression(fieldName), new NameExpression("value"))));
			to.add(setter);
		}
	}

	private void createGetterImplementation(String fieldName, Method getter) {
		getter.addModifier(new Public());
		Block getterBody = new Block();
		getter.setImplementation(new RegularImplementation(getterBody));
		getterBody.addStatement(new ReturnStatement(new NameExpression(fieldName)));
	}


}
