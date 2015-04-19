package org.aikodi.jlo.translate;

import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.MemberVariable;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Private;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.util.Util;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.component.SubobjectType;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Implements;
import be.kuleuven.cs.distrinet.jnome.core.type.RegularJavaType;

public class Java8ClassGenerator extends AbstractJava8Generator {

	protected Document createImplementation(Document result) {
		implementOwnInterfaces(result);
		removeNormalMethods(result);
		//    replaceSubobjects(result);
		replaceSubobjects2(result);
		addFields(result);
		renameConstructorCalls(result);
		return result;
	}

	protected void replaceSubobjects(Document result) {
		result.apply(Subobject.class, s -> {
			MemberVariableDeclarator field = new MemberVariableDeclarator(s.clone(s.superClassReference()));
			field.add(new VariableDeclaration(subobjectFieldName(s)));
			field.addModifier(new Private());
			Type type = s.nearestAncestor(Type.class);
			type.add(field);
			Method getter = createSubobjectGetterTemplate(s);
			createGetterImplementation(subobjectFieldName(s), getter);
			type.add(getter);
			createSubobjectImplementation(s, type);

			s.disconnect();
		});
	}

	protected void replaceSubobjects2(Document result) {
		result.apply(Subobject.class, s -> s.disconnect());
		result.apply(Type.class, t -> {
			if(! isGenerated(t)) {
				Type jloType = (Type) t.origin();
				try {
					List<Subobject> jloSubobjects = jloType.members(Subobject.class);
					jloSubobjects.forEach(s -> {
						MemberVariableDeclarator field = new MemberVariableDeclarator(s.clone(s.superClassReference()));
						field.add(new VariableDeclaration(subobjectFieldName(s)));
						field.addModifier(new Private());
						t.add(field);
						Method getter = createSubobjectGetterTemplate(s);
						createGetterImplementation(subobjectFieldName(s), getter);
						t.add(getter);
						createSubobjectImplementation(s, t);
					});
				} catch (Exception e) {
					throw new ChameleonProgrammerException(e);
				}
			}
		});

	}

	protected void createSubobjectImplementation(Subobject subobject, Type parent) {
		RegularJavaType subobjectImplementation = (RegularJavaType) ooFactory(subobject).createRegularType(subobjectImplementationName(subobject));
		subobjectImplementation.setBody(new ClassBody());
		subobjectImplementation.addModifier(new Public());
		parent.add(subobjectImplementation);
		Subobject originalSubobject = (Subobject)subobject.origin();
		try {
			addFields(subobjectImplementation, originalSubobject.componentType());
		} catch (LookupException e) {
			throw new ChameleonProgrammerException(e);
		}
		SubtypeRelation implementsRelation = new SubtypeRelation(java(subobject).createTypeReference(originalSubobject.componentType().getFullyQualifiedName()));
		implementsRelation.addModifier(new Implements());
		subobjectImplementation.addInheritanceRelation(implementsRelation);
	}

	private String subobjectImplementationName(Subobject subobject) {
		return subobject.name()+IMPLEMENTATION_SUFFIX;
	}

	protected void removeNormalMethods(Document result) {
		result.apply(Method.class, m -> {
			Member origin = (Member) m.origin();
			if (!origin.isTrue(jlo(origin).CONSTRUCTOR)) {
				m.disconnect();
			}
		});
	}

	protected void implementOwnInterfaces(Document result) {
		result.apply(Type.class, t -> {
			SubtypeRelation relation = new SubtypeRelation(t.language(Java7.class).createTypeReference(t.name()));
			relation.addModifier(new Implements());
			t.addInheritanceRelation(relation);
			t.setName(implementationName(t));
			try {
				t.modifiers(java(result).SCOPE_MUTEX).forEach(m -> m.disconnect());
			} catch (ModelException e) {
				throw new ChameleonProgrammerException(e);
			}
			t.addModifier(new Public());
		});
	}

	protected void addFields(Document target) {
		target.apply(MemberVariableDeclarator.class, m -> {
			if(! isGenerated(m)) {
				m.disconnect();
			}
		});
		target.apply(Type.class, t -> {
			if(! (t instanceof SubobjectType) && ! isGenerated(t)) {
				Type originalType = (Type) t.origin();
				try {
					addFields(t, originalType);
				} catch (Exception e) {
					throw new ChameleonProgrammerException(e);
				}
				//    		Subobject originalSubobect = (Subobject) t.nearestAncestor(Subobject.class).origin();
				//    		try {
				//					originalType = originalSubobect.componentType();
				//				} catch (Exception e) {
				//					throw new ChameleonProgrammerException(e);
				//				}
			} 
		});
	}

	private void addFields(Type to, Type from) throws LookupException {
		Set<Type> allSuperTypes = from.getAllSuperTypes();
		allSuperTypes.add(from);
		allSuperTypes.stream().<MemberVariable>flatMap(x -> {
			try {
				return x.localMembers(MemberVariable.class).stream();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ChameleonProgrammerException(e);
			}
		}).forEach(v -> {
			MemberVariableDeclarator jloMemberVariableDeclarator = v.nearestAncestor(MemberVariableDeclarator.class);
			MemberVariableDeclarator f = new MemberVariableDeclarator(jloMemberVariableDeclarator.clone(jloMemberVariableDeclarator.typeReference()));
			VariableDeclaration variableDeclaration = (VariableDeclaration) v.origin();
			String fieldName = fieldName(variableDeclaration);
			Util.debug(fieldName.contains(IMPLEMENTATION_SUFFIX));
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
		});
	}

	private void createGetterImplementation(String fieldName, Method getter) {
		getter.addModifier(new Public());
		Block getterBody = new Block();
		getter.setImplementation(new RegularImplementation(getterBody));
		getterBody.addStatement(new ReturnStatement(new NameExpression(fieldName)));
	}


}
