package org.aikodi.jlo.translate;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.EqualityTypeArgument;
import org.aikodi.chameleon.oo.type.generics.FormalTypeParameter;
import org.aikodi.chameleon.oo.type.generics.InstantiatedTypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.chameleon.oo.type.generics.TypeConstraint;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.type.KeywordTypeReference;
import org.aikodi.jlo.model.type.TypeMemberDeclarator;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;

public abstract class AbstractJava8Generator {

  protected final String IMPLEMENTATION_SUFFIX = "Impl";

  protected String subobjectGetterName(Subobject subobject) {
    return subobject.name();
  }

  protected String subobjectFieldName(Subobject subobject) {
    return "subobject$"+subobject.origin().nearestAncestor(Type.class).name()+"$"+subobject.name();
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

  protected String implementationName(Type t) {
    return t.name() + IMPLEMENTATION_SUFFIX;
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

  protected Method createGetterTemplate(MemberVariableDeclarator d) throws LookupException {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    TypeReference tref = flattened(d.typeReference());
    return ooFactory(d).createNormalMethod(getterName(variableDeclaration), clone(d.typeReference()));
  }

  protected TypeReference flattened(TypeReference typeReference) throws LookupException {
    TypeReference tref = (TypeReference)typeReference.origin();
    Type tp = tref.nearestAncestor(Type.class);
    Declaration decl = tref.getDeclarator();
    if(decl instanceof FormalTypeParameter) {
      FormalTypeParameter param = (FormalTypeParameter) decl;
      TypeReference clone;
      if(param.nbConstraints() > 0) {
        TypeConstraint constraint = param.constraints().get(0);
        clone = clone(constraint.typeReference());
      } else {
        ObjectOrientedLanguage language = tref.language(ObjectOrientedLanguage.class);
        clone = language.createTypeReference(language.getDefaultSuperClassFQN());
      }
    } else if(decl instanceof InstantiatedTypeParameter) {
      InstantiatedTypeParameter param = (InstantiatedTypeParameter) decl;
      TypeArgument arg = param.argument();
    }
    
//    Type type = tref.getElement();
//    Type upperBound = type.upperBound();
//    TypeReference reference = typeReference.language(ObjectOrientedLanguage.class).reference(type);
//    Util.debug(typeReference.toString().equals("T"));
    return typeReference;
  }
  
  /**
   * @param subobject The subobject for which the getter template must be created.
   * @return a template for the getter of the given subobject. The resulting method
   * has a header, but not a body. It must be finished by the caller depending on whether
   * an interface or class is being created.
   * @throws LookupException 
   */
  protected Method createSubobjectGetterTemplate(Subobject subobject, ObjectOrientedLanguage targetLanguage) throws LookupException {
    //TypeReference subobjectTypeReference = subobject.clone(subobject.superClassReference());
    TypeReference subobjectTypeReference = subobjectTypeReference(subobject, targetLanguage);
    return ooFactory(subobject).createNormalMethod(subobjectGetterName(subobject), subobjectTypeReference);
  }
  
  protected TypeReference subobjectTypeReference(Subobject subobject, ObjectOrientedLanguage targetLanguage) throws LookupException {
    return targetLanguage.createTypeReference(subobject.name());
//    return expandedTypeReference(subobject.superClassReference(),targetLanguage);
  }

  protected ObjectOrientedFactory ooFactory(Element element) {
    return java(element).plugin(ObjectOrientedFactory.class);
  }

  protected ExpressionFactory expressionFactory(Element element) {
    return java(element).plugin(ExpressionFactory.class);
  }

  protected Method createSetterTemplate(MemberVariableDeclarator d) {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    ObjectOrientedFactory factory = java(d).plugin(ObjectOrientedFactory.class);
    TypeReference fieldType = d.clone(d.typeReference());
    Method result = factory.createNormalMethod(setterName(variableDeclaration), java(d).createTypeReference("void"));
    result.header().addFormalParameter(new FormalParameter("value", fieldType));
    return result;
  }

  protected boolean isGenerated(Element element) {
    return element.origin() == element;
  }

  protected TypeReference expandedTypeReference(TypeReference element, ObjectOrientedLanguage targetLanguage) throws LookupException {
    TypeReference result = targetLanguage.reference(element.getElement());
    //disconnect the type reference. Note that this might give problems inside anonymous inner classes/
    result.setUniParent(null);
    return result;
  }

  protected <E extends Element> E clone(E element) {
    return element.clone(element);
  }

  protected <E extends Element> E cloneAndSetOrigin(E element) {
    E result = element.clone(element);
    result.setOrigin(element);
    return result;
  }

  protected void addTypeParameterToOwnClass(Document javaDocument) {
    javaDocument.apply(TypeMemberDeclarator.class, d -> {
      d.disconnect();
    });
    javaDocument.apply(Type.class, t-> {
      if(! isGenerated(t)) {
        Type jloType = (Type) t.origin();
        try {
          Consumer<? super TypeMemberDeclarator> action = m -> {
            t.addParameter(TypeParameter.class, m.clone(m.parameter()));
          };
          applyToSortedTypeMemberDeclarators(jloType, action);
        } catch (LookupException e) {
          throw new ChameleonProgrammerException(e);
        }
      }
    });
    javaDocument.apply(SubtypeRelation.class, javaSubtypeRelation -> {
      if(! isGenerated(javaSubtypeRelation)) {
        SubtypeRelation jloInheritanceRelation = (SubtypeRelation) javaSubtypeRelation.origin();
        Type superClass;
        try {
          superClass = jloInheritanceRelation.superClass();
          addTypeParameters(javaSubtypeRelation, superClass);
        } catch (LookupException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * @param jloType
   * @param action
   * @throws LookupException
   */
  protected void applyToSortedTypeMemberDeclarators(Type jloType, Consumer<? super TypeMemberDeclarator> action)
      throws LookupException {
    jloType.members(TypeMemberDeclarator.class).stream().sorted((d1,d2) -> d1.name().compareTo(d2.name())).forEachOrdered(action);
  }

  protected void addTypeParameters(InheritanceRelation relation, Type jloType) throws LookupException {
    applyToSortedTypeMemberDeclarators(jloType, d -> {
      ((BasicJavaTypeReference)relation.superClassReference()).addArgument(new EqualityTypeArgument(java(relation).createTypeReference(d.parameter().name())));
    });
  }


  protected void transformKeywordTypeReferences(Document javaType) {
    javaType.apply(KeywordTypeReference.class, k -> transformKeywordTypeReference(k));
  }

  protected void transformKeywordTypeReference(KeywordTypeReference javaKeywordTypeReference) {
//    KeywordTypeReference original = (KeywordTypeReference) javaKeywordTypeReference.origin();
    BasicJavaTypeReference javaTypeReference = (BasicJavaTypeReference) javaKeywordTypeReference.typeConstructorReference();
    //    Type jloTypeConstructorInstantiation = original.getElement();
    //    List<TypeMemberDeclarator> typeMemberDeclarators = jloTypeConstructorInstantiation.members(TypeMemberDeclarator.class);
    javaKeywordTypeReference.arguments().stream().sorted((d1,d2) -> d1.name().compareTo(d2.name())).forEachOrdered(jloTypeArgument -> {
      javaTypeReference.addArgument(jloTypeArgument.argument());
    });
    javaKeywordTypeReference.parentLink().getOtherRelation().replace(javaKeywordTypeReference.parentLink(), javaTypeReference.parentLink());
    
  }

}
