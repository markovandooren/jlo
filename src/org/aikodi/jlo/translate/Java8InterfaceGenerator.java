package org.aikodi.jlo.translate;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.property.StaticChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.chameleon.oo.method.Implementation;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Interface;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.language.JLo;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Default;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;

public class Java8InterfaceGenerator extends AbstractJava8Generator {

  public Document createInterface(Document result) {
    changeClassesToInterfaces(result);
    replaceFields(result);
    makeNonPrivateMethodsPublic(result);
    renameConstructorCalls(result);
    replaceSubobjects(result);
    inferMissingReturnTypes(result);
    replaceExpressionImplementations(result);
    // makeImplicitlyAbstractMethodsAbstract(result);
    makeMethodsDefault(result);
    return result;
  }

  protected void changeClassesToInterfaces(Document target) {
    add(new Interface()).to(Type.class).in(target).whenOrigin(t -> !t.isTrue(java(t).INTERFACE));
    strip(Abstract.class).from(Type.class).in(target);
    strip(java(target).PUBLIC).from(Type.class).in(target);
    add(new Public()).to(Type.class).in(target).whenOrigin(t -> true);
  }

  protected void makeMethodsDefault(Document target) {
    add(new Default()).to(Method.class).in(target).whenTranslated(m -> {
      return !m.isTrue(java(m).CONSTRUCTOR) && !m.isTrue(java(m).ABSTRACT);
    });
  }

  protected void replaceExpressionImplementations(Document targetDocument) {
    targetDocument.apply(ExpressionImplementation.class, implementation -> {
      Block body = new Block();
      // We move the expression instead of cloning it
      body.addStatement(new ReturnStatement(implementation.expression()));
      RegularImplementation replacement = new RegularImplementation(body);
      implementation.replaceWith(replacement);
    });
  }

  protected void replaceFields(Document target) {
    target.apply(MemberVariableDeclarator.class, d -> {
      VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
      replaceFieldReferences(target, variableDeclaration);
      Method getter = createGetterTemplate(d);
      getter.addModifier(new Abstract());
      getter.addModifier(new Public());
      d.replaceWith(getter);
      Method setter = createSetterTemplate(d);
      setter.addModifier(new Abstract());
      setter.addModifier(new Public());
      getter.nearestAncestor(Type.class).add(setter);
    });
  }

  protected void replaceFieldReferences(Document target, VariableDeclaration variableDeclaration) {
    ExpressionFactory expressionFactory = java(variableDeclaration).plugin(ExpressionFactory.class);
    target.apply(CrossReference.class, ref -> {
      CrossReference<?> origin = (CrossReference<?>) ref.origin();
      if(origin != ref) {
        try {
          if(origin.getElement().nearestAncestor(MemberVariableDeclarator.class) == variableDeclaration.nearestAncestor(MemberVariableDeclarator.class).origin()) {
            if(! (origin.parent() instanceof AssignmentExpression)) {
              ref.replaceWith(expressionFactory.createInvocation(getterName(variableDeclaration), null));
            } else {
              AssignmentExpression assignment = (AssignmentExpression) ref.parent();
              MethodInvocation createInvocation = expressionFactory.createInvocation(setterName(variableDeclaration), null);
              createInvocation.addArgument(assignment.getValue());
              assignment.replaceWith(createInvocation);
            }
          }
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
  }

  protected void makeNonPrivateMethodsPublic(Document target) {
    target.apply(Method.class, m -> {
      Method origin = (Method) m.origin();
      if(origin != m) {
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
    });
  }

  protected void inferMissingReturnTypes(Document target) {
    target.apply(MethodHeader.class, h -> {
      if (h.returnTypeReference() == null) {
        Implementation implementation = h.nearestAncestor(Method.class).implementation();
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
          h.setReturnTypeReference(java(target).createTypeReference("void"));
        }
      }
    }   );
  }

  protected void replaceSubobjects(Document target) {
    target.apply(Subobject.class, s -> {
      JLo jlo = jlo(s.origin());
      ObjectOrientedFactory factory = jlo.plugin(ObjectOrientedFactory.class);
      Type subobjectInterface = factory.createRegularType(s.name());
      subobjectInterface.addModifier(new Interface());
      subobjectInterface.addInheritanceRelation(new SubtypeRelation(s.clone(s.superClassReference())));
      Method getter = createSubobjectGetterTemplate(s);
      getter.addModifier(new Abstract());
      getter.addModifier(new Public());
      Type nearestAncestor = s.nearestAncestor(Type.class);
			nearestAncestor.add(getter);
      s.replaceWith(subobjectInterface);
    });
  }

}
