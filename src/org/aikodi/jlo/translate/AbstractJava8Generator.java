package org.aikodi.jlo.translate;

import java.util.function.Predicate;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.jlo.model.language.JLo;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;

public abstract class AbstractJava8Generator {

  protected String fieldName(VariableDeclaration variableDeclaration) {
    return "field$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
  }

  protected String getterName(VariableDeclaration variableDeclaration) {
    return "get$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
  }

  protected String setterName(VariableDeclaration variableDeclaration) {
    return "set$"+variableDeclaration.origin().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
  }

  protected String implementationName(Type t) {
    return t.name() + "Impl";
  }

  protected JLo jlo(Element element) {
    return element.language(JLo.class);
  }

  protected Java7 java(Element element) {
    return element.language(Java7.class);
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

  protected ModifierAdder add(Modifier modifier) {
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


}
