package org.aikodi.jlo.translate;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.property.StaticChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.chameleon.oo.method.Implementation;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Constructor;
import org.aikodi.chameleon.support.modifier.Interface;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.modifier.Static;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.support.variable.LocalVariableDeclarator;
import org.aikodi.chameleon.util.Util;
import org.aikodi.java.core.expression.invocation.ConstructorInvocation;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.method.JavaMethod;
import org.aikodi.java.core.modifier.Default;
import org.aikodi.java.core.type.ArrayTypeReference;
import org.aikodi.java.core.type.BasicJavaTypeReference;
import org.aikodi.jlo.model.subobject.Subobject;
import org.aikodi.jlo.model.subobject.SubobjectType;
import org.aikodi.jlo.model.type.TypeMemberDeclarator;
import org.aikodi.rejuse.action.Action;

public class Java8InterfaceGenerator extends AbstractJava8Generator {

  public Document createInterface(Document javaDocument) throws LookupException {
    changeClassesToInterfaces(javaDocument);
    replaceSubobjects(javaDocument);
    replaceFields(javaDocument);
    makeNonPrivateMethodsPublic(javaDocument);
    renameConstructorCalls(javaDocument);
    inferMissingReturnTypes(javaDocument);
    replaceExpressionImplementations(javaDocument);
    // makeImplicitlyAbstractMethodsAbstract(result);
    makeMethodsDefault(javaDocument);
    createConstructors(javaDocument);
    createDefaultConstructor(javaDocument);
    addTypeParameterToOwnClass(javaDocument);
    transformKeywordTypeReferences(javaDocument);
    return javaDocument;
  }

  protected void createConstructors(Document javaDocument) throws LookupException {
    javaDocument.apply(new Action<Method,LookupException>(Method.class) {
      /**
       * @{inheritDoc}
       */
      @Override
      public void doPerform(Method javaMethod) throws LookupException {
        Method jloMethod = (Method) javaMethod.origin();
        if (!isGenerated(javaMethod) && jloMethod.isTrue(jlo(jloMethod).CONSTRUCTOR)) {
          Java7 java = java(javaDocument);
          Type javaParentType = javaMethod.lexical().nearestAncestor(Type.class);
          if (jloMethod.nbFormalParameters() == 0) {
            createMainInterface(javaMethod);
          }
          BasicJavaTypeReference typeRef = java.createTypeReference(javaParentType.name());
          strip(Constructor.class).from(Method.class).in(javaMethod);
          Method javaInstanceMethod = Java8InterfaceGenerator.this.clone(javaMethod);
          javaParentType.add(javaInstanceMethod);
          javaInstanceMethod.setName(constructorName(javaMethod));
          javaInstanceMethod.addModifier(new Default());
          javaInstanceMethod.setReturnTypeReference(java.createTypeReference("void"));
          javaMethod.setReturnTypeReference(Java8InterfaceGenerator.this.clone(typeRef));
          javaMethod.addModifier(new Static());

        ((Type)javaParentType.origin()).members(TypeMemberDeclarator.class).forEach(m -> {
          //t.addParameter(TypeParameter.class, new FormalTypeParameter(m.name()));
          javaMethod.header().addTypeParameter(m.clone(m.parameter()));
        });

          
          
          /**
           * Overwrite body:
           *
           * T result = new T(); result.init$... return result;
           */
          Block javaBody = new Block();
          LocalVariableDeclarator localVariableDeclarator = new LocalVariableDeclarator(typeRef);
          VariableDeclaration declaration = new VariableDeclaration(resultVariableName());
          declaration.setInitialization(
              new ConstructorInvocation(java.createTypeReference(implementationName(javaParentType)), null));
          localVariableDeclarator.add(declaration);
          javaBody.addStatement(localVariableDeclarator);
          MethodInvocation invocation = expressionFactory(javaMethod).createInvocation(constructorName(javaMethod),
              new NameExpression(resultVariableName()));
          for (FormalParameter parameter : javaMethod.formalParameters()) {
            invocation.addArgument(new NameExpression(parameter.name()));
          }
          javaBody.addStatement(new StatementExpression(invocation));
          javaBody.addStatement(new ReturnStatement(new NameExpression(resultVariableName())));
          ((RegularImplementation) ((NormalMethod) javaMethod).implementation()).setBody(javaBody);
        }
      }
    } );
  }

  protected void createMainInterface(Method javaMethod) {
    Java7 java = java(javaMethod);
    Type javaParentType = javaMethod.lexical().nearestAncestor(Type.class);
    ObjectOrientedFactory factory = java.plugin(ObjectOrientedFactory.class);
    Type javaMainType = factory.createRegularType(javaMethod.name());
    javaMainType.addModifier(new Interface());
    javaParentType.add(javaMainType);
    Method javaMainMethod = factory.createNormalMethod("main", java.createTypeReference("void"));
    javaMainType.add(javaMainMethod);
    javaMainMethod.addModifier(new Public());
    javaMainMethod.addModifier(new Static());
    Block body = new Block();
    javaMainMethod.setImplementation(new RegularImplementation(body));
    javaMainMethod.header().addFormalParameter(new FormalParameter("args", new ArrayTypeReference(java.createTypeReference("String"))));
  }

  protected void createDefaultConstructor(Document javaDocument) {
    javaDocument.apply(Type.class, t -> {
      if (!isGenerated(t)) {
        boolean hasConstructor = t.directlyDeclaredMembers().stream().anyMatch(m -> {
        	Declaration jloMember = (Declaration) m.origin();
          return !isGenerated(m) && jloMember.isTrue(jlo(jloMember).CONSTRUCTOR);
        } );
        if (!hasConstructor) {
          Method method = new JavaMethod(new SimpleNameMethodHeader("init$new", java(t).createTypeReference(t)));
          method.addModifier(new Static());
          method.addModifier(new Public());
          t.add(method);
          Block body = new Block();
          method.setImplementation(new RegularImplementation(body));
          body.addStatement(new ReturnStatement(new ConstructorInvocation(java(javaDocument).createTypeReference(implementationName(t)), null)));
        }
      }
    } );
  }

  protected String resultVariableName() {
    return "$result";
  }

  protected String constructorName(Method method) {
    return "init$" + method.name();
  }

  protected void changeClassesToInterfaces(Document javaDocument) {
    add(new Interface()).to(Type.class).in(javaDocument).whenOrigin(t -> !t.isTrue(java(t).INTERFACE));
    strip(Abstract.class).from(Type.class).in(javaDocument);
    strip(java(javaDocument).PUBLIC).from(Type.class).in(javaDocument);
    add(new Public()).to(Type.class).in(javaDocument).whenOrigin(t -> true);
  }

  protected void makeMethodsDefault(Document javaDocument) {
    add(new Default()).to(Method.class).in(javaDocument).whenTranslated(m -> {
      return !m.isTrue(java(m).CONSTRUCTOR) && !m.isTrue(java(m).ABSTRACT);
    } );
  }

  protected void replaceExpressionImplementations(Document javaDocument) {
    javaDocument.apply(ExpressionImplementation.class, implementation -> {
      Block body = new Block();
      // We move the expression instead of cloning it
      body.addStatement(new ReturnStatement(implementation.expression()));
      RegularImplementation replacement = new RegularImplementation(body);
      implementation.replaceWith(replacement);
    } );
  }

  protected void replaceFields(Document javaDocument) throws LookupException {
    javaDocument.apply(new Action<MemberVariableDeclarator,LookupException>(MemberVariableDeclarator.class) {
      /**
       * @throws LookupException 
       * @{inheritDoc}
       */
      @Override
      public void doPerform(MemberVariableDeclarator javaMemberVariableDeclarator) throws LookupException {
        VariableDeclaration variableDeclaration = javaMemberVariableDeclarator.variableDeclarations().get(0);
        replaceFieldReferences(javaDocument, variableDeclaration);
        Method getter = createGetterTemplate(javaMemberVariableDeclarator);
        getter.addModifier(new Abstract());
        getter.addModifier(new Public());
        javaMemberVariableDeclarator.replaceWith(getter);
        Method setter = createSetterTemplate(javaMemberVariableDeclarator);
        setter.addModifier(new Abstract());
        setter.addModifier(new Public());
        getter.lexical().nearestAncestor(Type.class).add(setter);
      }
    } );
  }

  protected void replaceFieldReferences(Document javaDocument, VariableDeclaration variableDeclaration) {
    ExpressionFactory expressionFactory = java(variableDeclaration).plugin(ExpressionFactory.class);
    javaDocument.apply(CrossReference.class, ref -> {
      CrossReference<?> origin = (CrossReference<?>) ref.origin();
      if (!isGenerated(ref)) {
        try {
          if (origin.getElement().lexical().nearestAncestor(MemberVariableDeclarator.class) == variableDeclaration
              .lexical().nearestAncestor(MemberVariableDeclarator.class).origin()) {
            if (!(origin.parent() instanceof AssignmentExpression)) {
              ref.replaceWith(expressionFactory.createInvocation(getterName(variableDeclaration), null));
            } else {
              AssignmentExpression assignment = (AssignmentExpression) ref.parent();
              MethodInvocation createInvocation = expressionFactory.createInvocation(setterName(variableDeclaration),
                  null);
              createInvocation.addArgument(assignment.getValue());
              assignment.replaceWith(createInvocation);
            }
          }
        } catch (LookupException e) {
          throw new ChameleonProgrammerException(e);
        }
      }
    } );
  }

  protected void makeNonPrivateMethodsPublic(Document javaDocument) {
    javaDocument.apply(Method.class, m -> {
      Method origin = (Method) m.origin();
      if (!isGenerated(m)) {
        StaticChameleonProperty priv = jlo(origin).PRIVATE;
        if (!origin.isTrue(priv)) {
          try {
            m.modifiers(m.language(Java7.class).SCOPE_MUTEX).forEach(e -> e.disconnect());
            m.addModifier(new Public());
            m.flushCache();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    } );
  }

  protected void inferMissingReturnTypes(Document javaDocument) {
    javaDocument.apply(MethodHeader.class, h -> {
      if (h.returnTypeReference() == null) {
        Implementation implementation = h.lexical().nearestAncestor(Method.class).implementation();
        if (implementation instanceof ExpressionImplementation) {
          Expression expression = ((ExpressionImplementation) implementation).expression();
          Expression origin = (Expression) expression.origin();
          try {
            BasicJavaTypeReference typeReference = java(origin).createTypeReference(origin.getType());
            h.setReturnTypeReference(typeReference);
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        } else {
          h.setReturnTypeReference(java(javaDocument).createTypeReference("void"));
        }
      }
    } );
  }

  protected void replaceSubobjects(Element javaElement) {
    List<Subobject> subobjects = javaElement.lexical().nearestDescendants(Subobject.class);
    for(Subobject javaSubobject : subobjects) {
      try {
        String subobjectInterfaceName = subobjectInterfaceName(javaSubobject);
				Type javaSubobjectInterface = ooFactory(javaSubobject).createRegularType(subobjectInterfaceName);
        Subobject jloSubobject = (Subobject) javaSubobject.origin();
        Util.debug(jloSubobject.componentType().getFullyQualifiedName().equals("example.Radio.volume.upperBound"));
        javaSubobjectInterface.addModifier(new Interface());
        SubtypeRelation javaSubtypeRelation = new SubtypeRelation(clone(javaSubobject.superClassReference()));
        javaSubobjectInterface.addInheritanceRelation(javaSubtypeRelation);
        Method getter = createSubobjectGetterTemplate(jloSubobject, java(javaSubobject));
        getter.addModifier(new Abstract());
        getter.addModifier(new Public());
        Type nearestAncestor = javaSubobject.lexical().nearestAncestor(Type.class);
        nearestAncestor.add(getter);
        applyToSortedTypeMemberDeclarators(jloSubobject.lexical().nearestAncestor(Type.class), m -> {
          javaSubobjectInterface.addParameter(TypeParameter.class, clone(m.parameter()));
        });
        javaSubobject.replaceWith(javaSubobjectInterface);
        SubobjectType jloSubobjectType = jloSubobject.lexical().nearestDescendants(SubobjectType.class).get(0);
        List<Declaration> jloSubobjectMembers = jloSubobjectType.directlyDeclaredMembers();
        for(Declaration jloSubobjectMember: jloSubobjectMembers) {
        	Declaration member = cloneAndSetOrigin(jloSubobjectMember);
          javaSubobjectInterface.add(member);
        }
        replaceSubobjects(javaSubobjectInterface);
      } catch (LookupException e) {
        throw new ChameleonProgrammerException(e);
      }
    }
  }
  
  protected void replaceSubobjectsOld(Document javaDocument) {
    javaDocument.apply(Subobject.class, javaSubobject -> {
      try {
        Type subobjectInterface = ooFactory(javaSubobject).createRegularType(subobjectInterfaceName(javaSubobject));
        Subobject jloSubobject = (Subobject) javaSubobject.origin();
        subobjectInterface.addModifier(new Interface());
        SubtypeRelation javaSubtypeRelation = new SubtypeRelation(clone(javaSubobject.superClassReference()));
        subobjectInterface.addInheritanceRelation(javaSubtypeRelation);
        Method getter = createSubobjectGetterTemplate(jloSubobject, java(javaSubobject));
        getter.addModifier(new Abstract());
        getter.addModifier(new Public());
        Type nearestAncestor = javaSubobject.lexical().nearestAncestor(Type.class);
        nearestAncestor.add(getter);
        applyToSortedTypeMemberDeclarators(jloSubobject.lexical().nearestAncestor(Type.class), m -> {
          subobjectInterface.addParameter(TypeParameter.class, clone(m.parameter()));
        });
        javaSubobject.replaceWith(subobjectInterface);
      } catch (LookupException e) {
        throw new ChameleonProgrammerException(e);
      }
    } );
  }

}
