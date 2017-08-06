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

	/**
	 * Convert the given document, which is a clone from
	 * a JLo document to Java. The elements in the document
	 * must have their origin set to the corresponding element
	 * in the JLo document such that the translation can
	 * query the original model if needed. The given
	 * document cannot be used for that, as it will be modified.
	 * 
	 * @param javaDocument The document to be converted to Java.
	 * @return
	 * @throws LookupException
	 */
	public Document createImplementation(Document javaDocument) throws LookupException {
		// All normal methodes will be part of the interface as default methods.
		// The support for multiple inheritance makes it much easier as we
		// don't have to clone methods out of their context.
		removeNormalMethods(javaDocument);
		// Each JLo class is represented by a Java interface and a class that
		// implements the interface, and contains the fields.
		// The class must implement the interface.
		implementOwnInterfaces(javaDocument);
		replaceSubobjects(javaDocument);
		addFields(javaDocument);
		renameConstructorCalls(javaDocument);
		addTypeParameterToOwnClass(javaDocument);
		return javaDocument;
	}

	/**
	 * All subobjects are removed. All non-generated types  
	 * Replace each subobject with a class that represents the Java equivalent
	 * of the subobject class.
	 * 
	 * @param javaDocument The document in which the subobjects must be replaced.
	 */
	protected void replaceSubobjects(Document javaDocument) throws LookupException {
		// We remove the subobjects from the document, and work directly with
		// the subobjects from the JLo document.
		javaDocument.lexical().apply(Subobject.class, javaSubobject -> {
			javaSubobject.disconnect();
			});
		javaDocument.lexical().apply(Type.class, javaType -> {
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
					// 1. Add a field to store the subobject.
					TypeReference subobjectTypeReference = subobjectTypeReference(jloSubobject, java(javaType));
					MemberVariableDeclarator field = new MemberVariableDeclarator(subobjectTypeReference);
					field.add(new VariableDeclaration(subobjectFieldName(jloSubobject)));
					field.addModifier(new Private());
					javaType.add(field);
					// 2. Add a getter for the subobject.
					Method getter = createSubobjectGetterTemplate(jloSubobject,java(javaType));
					createGetterImplementation(subobjectFieldName(jloSubobject), getter);
					javaType.add(getter);
					// 3. Create a class for the subobject. It contains the fields of the
					//    subobject and implements the subobject interface.
					//    FIXME The constructors should also be added.
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

	/**
	 * Create a class for the given subobject, and add it to the given Java parent type.
	 * 
	 * @param jloSubobject
	 * @param javaParentType
	 * @return
	 * @throws LookupException
	 */
	protected RegularJavaType createSubobjectImplementation(Subobject jloSubobject, Type javaParentType) throws LookupException {
		// 1. Compute the name of the class that will represent the subobject.
		String subobjectImplementationName = subobjectImplementationName(jloSubobject);
		// 2. Create a public class.
//		Util.debug(subobjectImplementationName.equals("upperBoundImpl") && jloSubobject.lexical().farthestAncestor(Type.class).name().equals("Radio"));
		RegularJavaType javaSubobjectImplementation = (RegularJavaType) ooFactory(jloSubobject).createRegularType(subobjectImplementationName);
		javaSubobjectImplementation.setBody(new ClassBody());
		javaSubobjectImplementation.addModifier(new Public());
		// 3. Add the class to the given parent type.
		javaParentType.add(javaSubobjectImplementation);
		// 4. Add the fields to the class that represents the subobject.
		addFields(javaSubobjectImplementation, jloSubobject.componentType());
		// 5. Add the 'implements' relation.
		SubtypeRelation javaImplementsRelation = new SubtypeRelation(java(javaParentType)
				.createTypeReference(subobjectInterfaceName(jloSubobject)));
		javaImplementsRelation.addModifier(new Implements());
		javaSubobjectImplementation.addInheritanceRelation(javaImplementsRelation);
		Type jloParentType = jloSubobject.lexical().nearestAncestor(Type.class);
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
		result.lexical().apply(Method.class, m -> {
			Element origin = m.origin();
			//      if (!origin.isTrue(jlo(origin).CONSTRUCTOR)) {
			m.disconnect();
			//      }
		});
	}

	protected void implementOwnInterfaces(Document javaDocument) {
		javaDocument.lexical().apply(Type.class, javaType -> {
			try {
				Java7 java = java(javaDocument);
				// Only disconnect inheritance relations that are explicit, and
				// that are no subobjects.
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
		javaDocument.lexical().apply(MemberVariableDeclarator.class, javaMethod -> {
			if(! isGenerated(javaMethod)) {
				javaMethod.disconnect();
			}
		});
		javaDocument.lexical().apply(Type.class, t -> {
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

	private void addFields(Type javaType, Type jloType) throws LookupException {
		Set<Type> allSuperTypes = jloType.getSelfAndAllSuperTypesView();
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
			MemberVariableDeclarator jloMemberVariableDeclarator = v.lexical().nearestAncestor(MemberVariableDeclarator.class);
			MemberVariableDeclarator f = new MemberVariableDeclarator(clone(jloMemberVariableDeclarator.typeReference()));
			VariableDeclaration variableDeclaration = (VariableDeclaration) v.origin(); 
			String fieldName = fieldName(variableDeclaration);
			Util.debug(fieldName.contains("field$Property$value"));
			f.add(new VariableDeclaration(fieldName));
			f.addModifier(new Private());
			javaType.add(f);
			Method getter = createGetterTemplate(jloMemberVariableDeclarator);
			createGetterImplementation(fieldName, getter);
			javaType.add(getter);
			Method setter = createSetterTemplate(jloMemberVariableDeclarator);
			setter.addModifier(new Public());
			Block setterBody = new Block();
			setter.setImplementation(new RegularImplementation(setterBody));
			setterBody.addStatement(new StatementExpression(new AssignmentExpression(new NameExpression(fieldName), new NameExpression("value"))));
			javaType.add(setter);
		}
	}

	/**
	 * <p>Turn the given method into a getter for a field
	 * with the given name.</p>
	 * 
	 * <ul>
	 *   <li>The method gets a public modifier</li>
	 *   <li>The implementation is a block with a return statement.</li>
	 *   <li>The return statement returns the given field.</li>
	 * </ul>
	 * 
	 * @param fieldName The name of the field that must be returned
	 *                  by the getter.
	 * @param getter The getter to be modified.
	 */
	private void createGetterImplementation(String fieldName, Method getter) {
		getter.addModifier(new Public());
		Block getterBody = new Block();
		getter.setImplementation(new RegularImplementation(getterBody));
		getterBody.addStatement(new ReturnStatement(new NameExpression(fieldName)));
	}


}
