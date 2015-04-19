package org.aikodi.jlo.translate;

import java.util.Set;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.statement.Block;
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
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.component.SubobjectType;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Implements;

public class Java8ClassGenerator extends AbstractJava8Generator {

  protected Document createImplementation(Document result) {
    implementOwnInterfaces(result);
    removeNormalMethods(result);
    replaceSubobjects(result);
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
    	s.disconnect();
    });
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
    });
  }

  protected void addFields(Document target) {
    target.apply(MemberVariableDeclarator.class, m -> {
			if(! isGenerated(m)) {
    		m.disconnect();
    	}
    });
    target.apply(Type.class, t -> {
    	Type originalType;
    	if(t instanceof SubobjectType) {
    		Subobject originalSubobect = (Subobject) t.nearestAncestor(Subobject.class).origin();
    		try {
					originalType = originalSubobect.componentType();
				} catch (Exception e) {
					throw new ChameleonProgrammerException(e);
				}
    	} else {
    		originalType = (Type) t.origin();
    	}
      try {
        Set<Type> allSuperTypes = originalType.getAllSuperTypes();
        allSuperTypes.add((Type) t.origin());
        allSuperTypes.stream().<MemberVariable>flatMap(x -> {
          try {
            return x.localMembers(MemberVariable.class).stream();
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ChameleonProgrammerException(e);
          }
        }).forEach(v -> {
          MemberVariableDeclarator decl = v.nearestAncestor(MemberVariableDeclarator.class);
          MemberVariableDeclarator f = new MemberVariableDeclarator(decl.clone(decl.typeReference()));
          VariableDeclaration variableDeclaration = (VariableDeclaration) v.origin();
          String fieldName = fieldName(variableDeclaration);
					f.add(new VariableDeclaration(fieldName));
          f.addModifier(new Private());
          t.add(f);
          Method getter = createGetterTemplate(decl);
          createGetterImplementation(fieldName, getter);
          t.add(getter);
          Method setter = createSetterTemplate(decl);
          setter.addModifier(new Public());
          Block setterBody = new Block();
          setter.setImplementation(new RegularImplementation(setterBody));
          setterBody.addStatement(new StatementExpression(new AssignmentExpression(new NameExpression(fieldName), new NameExpression("value"))));
          t.add(setter);
        });
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
  }

	private void createGetterImplementation(String fieldName, Method getter) {
		getter.addModifier(new Public());
		Block getterBody = new Block();
		getter.setImplementation(new RegularImplementation(getterBody));
		getterBody.addStatement(new ReturnStatement(new NameExpression(fieldName)));
	}


}
