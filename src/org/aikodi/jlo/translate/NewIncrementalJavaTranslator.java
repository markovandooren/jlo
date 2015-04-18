package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.property.StaticChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.chameleon.oo.method.Implementation;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.MemberVariable;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.plugin.build.BuildProgressHelper;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Interface;
import org.aikodi.chameleon.support.modifier.Private;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.support.translate.IncrementalTranslator;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.language.JLo;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Default;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Implements;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;

public class NewIncrementalJavaTranslator extends IncrementalTranslator<JLo, Java7> {

  public NewIncrementalJavaTranslator(View source, View target) {
    super(source, target);
  }

  @Override
  public Collection<Document> build(Document source, BuildProgressHelper buildProgressHelper) throws BuildException {
    List<Document> result;
    try {
      result = translate(source);
    } catch (ModelException e) {
      throw new BuildException(e);
    }
    return result;
  }

  public List<Document> translate(Document sourceDocument) throws LookupException, ModelException {
    List<Document> result = new ArrayList<Document>();
    Document interfaceDocument = createInterface(sourceDocument);
    result.add(interfaceDocument);
    Document implementationDocument = createImplementation(sourceDocument);
    if (implementationDocument != null) {
      result.add(implementationDocument);
    }
    return result;
  }

  protected Document createImplementation(Document sourceDocument) {
    Document result = sourceDocument.cloneTo(target());
    implementOwnInterfaces(result);
    removeNormalMethods(result);
    removeSubobjects(result);
    renameConstructorCalls(result);
    //    makeFieldsPrivate(result);
    addFields(result);
    return result;
  }

  protected void addFields(Document target) {
    target.apply(MemberVariableDeclarator.class, m -> m.disconnect());
    target.apply(Type.class, t -> {
      Type originalType = (Type) t.origin();
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
          f.add(new VariableDeclaration(fieldName(variableDeclaration)));
          f.addModifier(new Private());
          t.add(f);
          Method getter = createGetterTemplate(decl);
          getter.addModifier(new Public());
          Block getterBody = new Block();
          getter.setImplementation(new RegularImplementation(getterBody));
          getterBody.addStatement(new ReturnStatement(new NameExpression(fieldName(variableDeclaration))));
          t.add(getter);
          Method setter = createSetterTemplate(decl);
          setter.addModifier(new Public());
          Block setterBody = new Block();
          setter.setImplementation(new RegularImplementation(setterBody));
          setterBody.addStatement(new StatementExpression(new AssignmentExpression(new NameExpression(fieldName(variableDeclaration)), new NameExpression("value"))));
          t.add(setter);
        });
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
  }

  protected void makeFieldsPrivate(Document target) {
    target.apply(MemberVariableDeclarator.class, d -> d.addModifier(new Private()));
  }

  protected void implementOwnInterfaces(Document result) {
    result.apply(Type.class, t -> {
      SubtypeRelation relation = new SubtypeRelation(t.language(Java7.class).createTypeReference(t.name()));
      relation.addModifier(new Implements());
      t.addInheritanceRelation(relation);
      t.setName(implementationName(t));
    });
  }

  protected void removeSubobjects(Document result) {
    result.apply(Subobject.class, s -> s.disconnect());
  }

  protected void removeNormalMethods(Document result) {
    result.apply(Method.class, m -> {
      Member origin = (Member) m.origin();
      if (!origin.isTrue(jlo(origin).CONSTRUCTOR)) {
        m.disconnect();
      }
    });
  }

  protected String implementationName(Type t) {
    return t.name() + "Impl";
  }

  protected Document createInterface(Document sourceDocument) {
    Document result = sourceDocument.cloneTo(target());
    changeClassesToInterfaces(result);
    replaceFields(result);
    makeNonPrivateMethodsPublic(result);
    renameConstructorCalls(result);
    createSubobjectInterfaces(result);
    inferMissingReturnTypes(result);
    replaceExpressionImplementations(result);
    // makeImplicitlyAbstractMethodsAbstract(result);
    makeMethodsDefault(result);
    return result;
  }

  // protected void makeImplicitlyAbstractMethodsAbstract(Document target) {
  // target.apply(Method.class, m -> {
  // if(m.implementation() == null) {
  // m.addModifier(new Abstract());
  // m.flushCache();
  // }
  // });
  // }

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

  protected void createSubobjectInterfaces(Document target) {
    target.apply(Subobject.class, s -> {
      JLo jlo = jlo(s.origin());
      ObjectOrientedFactory factory = jlo.plugin(ObjectOrientedFactory.class);
      Type subobjectInterface = factory.createRegularType(s.name());
      subobjectInterface.addModifier(new Interface());
      // We strip the superclass reference from the subobject relation
      subobjectInterface.addInheritanceRelation(new SubtypeRelation(s.superClassReference()));
      s.replaceWith(subobjectInterface);
    });
  }

  protected void renameConstructorCalls(Document target) {
    target.apply(CrossReferenceWithName.class, c -> {
      CrossReferenceWithName origin = (CrossReferenceWithName) c.origin();
      if (origin != c.origin()) {
        Declaration element;
        try {
          element = origin.getElement();
          if (element.isTrue(java(element).CONSTRUCTOR)) {
            c.setName(implementationName((Type) element));
          }
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      }
    });

  }

  protected void changeClassesToInterfaces(Document target) {
    add(new Interface()).to(Type.class).in(target).whenOrigin(t -> !t.isTrue(java(t).INTERFACE));
    strip(Abstract.class).from(Type.class).in(target);
    strip(java(target).PUBLIC).from(Type.class).in(target);
    add(new Public()).to(Type.class).in(target).whenOrigin(t -> true);
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

  protected Method createGetterTemplate(MemberVariableDeclarator d) {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    ObjectOrientedFactory factory = java(d).plugin(ObjectOrientedFactory.class);
    return factory.createNormalMethod(getterName(variableDeclaration), d.clone(d.typeReference()));
  }

  protected Method createSetterTemplate(MemberVariableDeclarator d) {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    ObjectOrientedFactory factory = java(d).plugin(ObjectOrientedFactory.class);
    TypeReference fieldType = d.clone(d.typeReference());
    Method result = factory.createNormalMethod(setterName(variableDeclaration), java(d).createTypeReference("void"));
    result.header().addFormalParameter(new FormalParameter("value", fieldType));
    return result;
  }

  protected String fieldName(VariableDeclaration variableDeclaration) {
    return "field$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
  }

  protected String getterName(VariableDeclaration variableDeclaration) {
    return "get$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
  }

  protected String setterName(VariableDeclaration variableDeclaration) {
    return "set$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
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

  protected void makeMethodsDefault(Document target) {
    add(new Default()).to(Method.class).in(target).whenTranslated(m -> {
      return !m.isTrue(java(m).CONSTRUCTOR) && !m.isTrue(java(m).ABSTRACT);
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

  protected void replaceExpressionImplementations(Document targetDocument) {
    targetDocument.apply(ExpressionImplementation.class, implementation -> {
      Block body = new Block();
      // We move the expression instead of cloning it
      body.addStatement(new ReturnStatement(implementation.expression()));
      RegularImplementation replacement = new RegularImplementation(body);
      implementation.replaceWith(replacement);
    });
  }

  public ModifierStripper strip(ChameleonProperty property) {
    return new ModifierStripper(m -> m.impliesTrue(property));
  }

  public ModifierStripper strip(Class<? extends Modifier> type) {
    return new ModifierStripper(m -> type.isInstance(m));
  }

  public static class ModifierStripper {
    private Predicate<Modifier> predicate;

    public ModifierStripper(Predicate<Modifier> predicate) {
      this.predicate = predicate;
    }

    public <T extends ElementWithModifiers> ModifierStripperConfiguration<T> from(Class<T> elementType) {
      return new ModifierStripperConfiguration<T>(predicate, elementType);
    }
  }

  public static class ModifierStripperConfiguration<T extends ElementWithModifiers> {
    private Predicate<Modifier> modifierPredicate;
    private Class<T> elementType;
    private Predicate<T> predicate;

    public ModifierStripperConfiguration(Predicate<Modifier> modifierPredicate, Class<T> elementType) {
      super();
      this.modifierPredicate = modifierPredicate;
      this.elementType = elementType;
    }

    public ModifierStripperConfiguration<T> when(Predicate<T> predicate) {
      this.predicate = predicate;
      return this;
    }

    public void in(Element element) {
      element.apply(elementType, e -> {
        if (predicate == null || predicate.test(e)) {
          try {
            e.modifiers().stream().filter(modifierPredicate).forEach(x -> x.disconnect());
          } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
      } );
    }

  }

  private ModifierAdder add(Modifier modifier) {
    return new ModifierAdder(modifier);
  }

  public static class ModifierAdder {
    private Modifier modifier;

    public ModifierAdder(Modifier modifier) {
      this.modifier = modifier;
    }

    public <T extends ElementWithModifiers> ModifierConfiguration<T> to(Class<T> type) {
      return new ModifierConfiguration<>(modifier, type);
    }
  }

  public static class ModifierConfiguration<T extends ElementWithModifiers> {
    private Predicate<T> predicate;
    private Element element;
    private Class<T> type;
    private Modifier modifier;

    public ModifierConfiguration(Modifier modifier, Class<T> type) {
      this.type = type;
      this.modifier = modifier;
    }

    public ModifierConfiguration<T> in(Element element) {
      this.element = element;
      return this;
    }

    public void whenOrigin(Predicate<T> predicate) {
      this.predicate = predicate;
      element.apply(type, t -> {
        if (predicate.test((T) t.origin())) {
          t.addModifier(modifier.clone(modifier));
          t.flushCache();
        }
        ;
      });
    }

    public void whenTranslated(Predicate<T> predicate) {
      this.predicate = predicate;
      element.apply(type, t -> {
        if (predicate.test((T) t)) {
          t.addModifier(modifier.clone(modifier));
          t.flushCache();
        }
        ;
      });
    }
  }

  protected JLo jlo(Element element) {
    return element.language(JLo.class);
  }

  protected Java7 java(Element element) {
    return element.language(Java7.class);
  }

}
