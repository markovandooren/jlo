package org.aikodi.jlo.translate;

import java.util.List;
import java.util.Set;

import org.aikodi.chameleon.core.document.Document;
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
import org.aikodi.chameleon.oo.type.TypeReference;
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
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.jnome.core.type.RegularJavaType;

import com.google.common.collect.ImmutableSet;

public class Java8ClassGenerator extends AbstractJava8Generator {

  public Document createImplementation(Document javaDocument) {
    removeNormalMethods(javaDocument);
    //    replaceSubobjects(result);
    implementOwnInterfaces(javaDocument);
    replaceSubobjects(javaDocument);
    addFields(javaDocument);
    renameConstructorCalls(javaDocument);
    addTypeParameterToOwnClass(javaDocument);
    return javaDocument;
  }

  protected void replaceSubobjects(Document javaDocument) {
    javaDocument.apply(Subobject.class, javaSubobject -> javaSubobject.disconnect());
    javaDocument.apply(Type.class, javaType -> {
      if(! isGenerated(javaType)) {
        Type jloType = (Type) javaType.origin();
        expandSubobjects(javaType, jloType);
      }
    });
  }

  protected void expandSubobjects(Type javaType, Type jloType) {
    try {
      List<Subobject> jloSubobjects = jloType.members(Subobject.class);
      jloSubobjects.forEach(jloSubobject -> {
        //TypeReference subobjectTypeReference = jloSubobject.clone(jloSubobject.superClassReference());
        try {
          TypeReference subobjectTypeReference = expandedTypeReference(jloSubobject.superClassReference(), java(javaType));
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

  protected RegularJavaType createSubobjectImplementation(Subobject subobject, Type parent) {
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
    return subobjectImplementation;
  }

  private String subobjectImplementationName(Subobject subobject) {
    return subobject.name()+IMPLEMENTATION_SUFFIX;
  }

  protected void removeNormalMethods(Document result) {
    result.apply(Method.class, m -> {
      Member origin = (Member) m.origin();
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
      MemberVariableDeclarator f = new MemberVariableDeclarator(clone(jloMemberVariableDeclarator.typeReference()));
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
