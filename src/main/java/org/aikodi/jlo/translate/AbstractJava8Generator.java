package org.aikodi.jlo.translate;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.core.tag.Metadata;
import org.aikodi.chameleon.core.tag.TagImpl;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.*;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.type.BasicJavaTypeReference;
import org.aikodi.java.core.type.JavaInstantiatedParameterType;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.subobject.Subobject;
import org.aikodi.jlo.model.type.TypeMemberDeclarator;
import org.aikodi.rejuse.action.Action;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.function.Consumer;
import org.aikodi.rejuse.function.Function;
import org.aikodi.rejuse.predicate.Predicate;

import java.util.function.BiConsumer;

public abstract class AbstractJava8Generator {

  protected final String IMPLEMENTATION_SUFFIX = "Impl";
  protected final String IMPLEMENTATION_MARKER_NAME = "ImplementationClass";

  protected String subobjectGetterName(Subobject subobject) {
    return subobject.name();
  }

  protected String subobjectFieldName(Subobject subobject) {
    return "subobject$"+subobject.origin().lexical().nearestAncestor(Type.class).name()+"$"+subobject.name();
  }

  protected String fieldName(VariableDeclaration variableDeclaration) {
    return "field$"+declarationName(variableDeclaration);
  }

	private String declarationName(VariableDeclaration variableDeclaration) {
		return variableDeclaration.origin().lexical().nearestAncestor(Type.class).name()+"$"+variableDeclaration.name();
	}

  protected String getterName(VariableDeclaration variableDeclaration) {
    return "get$"+declarationName(variableDeclaration);
  }

  protected String setterName(VariableDeclaration variableDeclaration) {
    return "set$"+declarationName(variableDeclaration);
  }

  public static class GeneratedClassMarker extends TagImpl {
  }

  protected String implementationName(Type t) {
    return t.name() + IMPLEMENTATION_SUFFIX;
  }

  protected String interfaceName(Type t) {
    if (t.hasMetadata(IMPLEMENTATION_MARKER_NAME)) {
      return t.name().substring(0, t.name().length() - IMPLEMENTATION_SUFFIX.length());
    }
    else {
      return t.name();
    }
  }

  /**
   * Return the name of the interface that represents the subobject.
   * @param subobject
   * @return
   */
  protected String subobjectInterfaceName(Subobject subobject) {
    return subobject.name();
  }

  protected JLo jlo(Element element) {
    return element.language(JLo.class);
  }

  protected Java7 java(Element element) {
    return element.language(Java7.class);
  }


  protected void renameConstructorCalls(Document target) {
    target.lexical().apply(CrossReferenceWithName.class, c -> {
      CrossReferenceWithName origin = (CrossReferenceWithName) c.origin();
      if (origin != c.origin()) {
        Declaration element;
        try {
          element = origin.getElement();
          if (element.isTrue(java(element).CONSTRUCTOR())) {
            c.setName(implementationName((Type) element));
          }
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      }
    });
  }

  protected Adder<Modifier, ElementWithModifiers, Nothing> add(Modifier modifier) {
    return new Adder<>(container -> modifier.clone(modifier), ElementWithModifiers::addModifier);
  }

  protected Adder<Declaration, Type, LookupException> add(org.aikodi.rejuse.function.Function<Type, Declaration, LookupException> declaration) {
    return new Adder<>(declaration, Type::add);
  }

  public static class Adder<E extends Element, C extends Element, EX extends Exception> {
    private Function<C, E, EX> _supplier;
    private BiConsumer<C, E> _action;

    public Adder(Function<C, E, EX> element, BiConsumer<C, E> action) {
      _supplier = element;
      _action = action;
    }

    public <X extends C> Transformer<X, EX> to(Class<X> type) throws EX {
      return new Transformer<X, EX>(type, t -> _action.accept(t, _supplier.apply(t)));
    }
  }

  public static class Transformer<T extends Element, E extends Exception> {
    private Element _element;
    private Class<T> _type;
    private Consumer<T, E> _action;

    public Transformer(Class<T> type, Consumer<T, E> action) {
      _type = type;
      _action = action;
    }

    public Transformer<T, E> in(Element element) {
      this._element = element;
      return this;
    }

    protected Element element() {
      return _element;
    }

    public void always() throws E {
      element().lexical().apply(_type, t -> {
          _action.accept(t);
          t.flushCache();
      });
    }

    public void whenOrigin(Predicate<T, Nothing> predicate) throws E {
        element().lexical().apply(_type, t -> {
            if (predicate.eval((T) t.origin())) {
                _action.accept(t);
                t.flushCache();
            }
        });
    }

    public void whenTranslated(Predicate<T, Nothing> predicate) throws E {
        element().lexical().apply(_type, t -> {
            if (predicate.eval((T) t)) {
                _action.accept(t);
                t.flushCache();
            }
        });
    }
  }

  public ModifierStripper strip(ChameleonProperty property) {
    return new ModifierStripper(m -> m.impliesTrue(property));
  }

  public ModifierStripper strip(Class<? extends Modifier> type) {
    return new ModifierStripper(m -> type.isInstance(m));
  }

  /**
   * A fluent API for removing modifiers from elements.
   */
  public static class ModifierStripper {
    private Predicate<Modifier, Nothing> predicate;

    public ModifierStripper(Predicate<Modifier, Nothing> predicate) {
      this.predicate = predicate;
    }

    public <T extends ElementWithModifiers> ModifierStripperConfiguration<T> from(Class<T> elementType) {
      return new ModifierStripperConfiguration<T>(predicate, elementType);
    }
  }

  public static class ModifierStripperConfiguration<T extends ElementWithModifiers> {
    private Predicate<Modifier, Nothing> modifierPredicate;
    private Class<T> elementType;
    private Predicate<T, Nothing> predicate;

    public ModifierStripperConfiguration(Predicate<Modifier, Nothing> modifierPredicate, Class<T> elementType) {
      super();
      this.modifierPredicate = modifierPredicate;
      this.elementType = elementType;
    }

    public ModifierStripperConfiguration<T> when(Predicate<T, Nothing> predicate) {
      this.predicate = predicate;
      return this;
    }

    public void in(Element element) {
      element.lexical().apply(elementType, e -> {
        if (predicate == null || predicate.eval(e)) {
          try {
            e.modifiers().stream().filter(t -> modifierPredicate.eval(t)).forEach(x -> x.disconnect());
          } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
      } );
    }

  }

  protected Method createGetterTemplate(MemberVariableDeclarator d, Type javaContextType) throws LookupException {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    TypeReference tref = convertToContext(d.typeReference(), javaContextType);
    return ooFactory(d).createNormalMethod(getterName(variableDeclaration), tref);
  }

  protected TypeReference convertToContext(TypeReference typeReference, Type javaContextType) throws LookupException {
    Type type = typeReference.getElement();
    TypeReference result;
    if (type instanceof JavaInstantiatedParameterType) {
      JavaInstantiatedParameterType instantiated = (JavaInstantiatedParameterType) type;
      Type aliased = instantiated.aliasedType();
      result = java(javaContextType).reference(aliased);
      result.setUniParent(null);
    } else {
      result = clone(typeReference);
    }
    return result;
  }


  protected TypeReference flattened(TypeReference typeReference) throws LookupException {
    TypeReference tref = (TypeReference)typeReference.origin();
    Type tp = tref.lexical().nearestAncestor(Type.class);
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

    return typeReference;
  }

  /**
   * @param jloSubobject The subobject for which the getter template must be created.
   * @return a template for the getter of the given subobject. The resulting method
   * has a header, but not a body. It must be finished by the caller depending on whether
   * an interface or class is being created.
   * @throws LookupException
   */
  protected Method createSubobjectGetterTemplate(Subobject jloSubobject, Type javaType) throws LookupException {
    TypeReference subobjectTypeReference = subobjectTypeReference(jloSubobject, javaType);
    return ooFactory(jloSubobject).createNormalMethod(subobjectGetterName(jloSubobject), subobjectTypeReference);
  }

  protected TypeReference subobjectTypeReference(Subobject subobject, Type javaType) throws LookupException {
    Java7 java = java(javaType);
    BasicJavaTypeReference result = java.createTypeReference(subobject.name());
    for (TypeParameter typeParameter: javaType.parameters(TypeParameter.class)) {
      result.addArgument(java.createEqualityTypeArgument(java.createTypeReference(typeParameter.name())));
    }
    return result;
  }

  protected ObjectOrientedFactory ooFactory(Element element) {
    return java(element).plugin(ObjectOrientedFactory.class);
  }

  protected ExpressionFactory expressionFactory(Element element) {
    return java(element).plugin(ExpressionFactory.class);
  }

  protected Method createSetterTemplate(MemberVariableDeclarator d, Type javaContextType) throws LookupException {
    VariableDeclaration variableDeclaration = d.variableDeclarations().get(0);
    ObjectOrientedFactory factory = java(d).plugin(ObjectOrientedFactory.class);
//    TypeReference fieldType = d.clone(d.typeReference());
    TypeReference fieldType = convertToContext(d.typeReference(), javaContextType);
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
    E result = (E)element.clone((original, clone) -> clone.setOrigin(original), Element.class);
    return result;
  }

  protected void addTypeParameterToOwnClass(Document javaDocument) {
    javaDocument.lexical().apply(TypeMemberDeclarator.class, d -> d.disconnect());
    javaDocument.lexical().apply(Type.class, t-> {
      if(! isGenerated(t)) {
        Type jloType = (Type) t.origin();
        try {
          Consumer<? super TypeMemberDeclarator, Nothing> action = m -> {
            t.addParameter(TypeParameter.class, m.clone(m.parameter()));
          };
          applyToSortedTypeMemberDeclarators(jloType, action);
        } catch (LookupException e) {
          throw new ChameleonProgrammerException(e);
        }
      }
    });
    javaDocument.lexical().apply(SubtypeRelation.class, javaSubtypeRelation -> {
      if(! isGenerated(javaSubtypeRelation)) {
        SubtypeRelation jloInheritanceRelation = (SubtypeRelation) javaSubtypeRelation.origin();
        Type superClass;
        try {
          superClass = jloInheritanceRelation.superClass();
          addTypeParameters(javaSubtypeRelation, superClass);
        } catch (LookupException e) {
          throw new ChameleonProgrammerException(e);
        }
      }
    });
  }

  /**
   * @param jloType
   * @param action
   * @throws LookupException
   */
  protected void applyToSortedTypeMemberDeclarators(Type jloType, Consumer<? super TypeMemberDeclarator, Nothing> action)
      throws LookupException {
    jloType.members(TypeMemberDeclarator.class).stream().sorted((d1,d2) -> d1.name().compareTo(d2.name())).forEachOrdered(action::accept);
  }

  protected void addTypeParameters(BasicJavaTypeReference tref, Type jloType) throws LookupException {
    Java7 java = java(tref);
    addTypeParameters(java, tref, jloType);
  }
  protected void addTypeParameters(Java7 java, BasicJavaTypeReference tref, Type jloType) throws LookupException {
    jloType.parameters(TypeParameter.class).forEach(p -> tref.addArgument(java.createEqualityTypeArgument(java.createTypeReference(p.name()))));
  }

  protected void addTypeParameters(Java7 java, MethodHeader header, Type jloType) {
    jloType.parameters(TypeParameter.class).forEach(p -> header.addTypeParameter(p.clone(p)));
  }

  protected void addTypeArguments(Java7 java, BasicJavaTypeReference javaTypeReference, Type jloType) {
    jloType.parameters(TypeParameter.class).forEach(p -> javaTypeReference.addArgument(new EqualityTypeArgument(java.createTypeReference(p.name()))));
  }

  protected void addTypeParameters(InheritanceRelation relation, Type jloType) throws LookupException {
//    applyToSortedTypeMemberDeclarators(jloType, d -> {
//      ((BasicJavaTypeReference)relation.superClassReference()).addArgument(new EqualityTypeArgument(java(relation).createTypeReference(d.parameter().name())));
//    });
    addTypeParameters(((BasicJavaTypeReference)relation.superClassReference()), jloType);

//    applyToSortedTypeMemberDeclarators(jloType, d -> {
//      BasicJavaTypeReference basicJavaTypeReference = (BasicJavaTypeReference)relation.superClassReference();
//			TypeParameter parameter = d.parameter();
//			if(parameter instanceof InstantiatedTypeParameter) {
//			  basicJavaTypeReference.addArgument(d.clone(((InstantiatedTypeParameter)parameter).argument()));
//			} else if (parameter instanceof FormalTypeParameter) {
//				basicJavaTypeReference.addArgument(new EqualityTypeArgument(java(relation).createTypeReference(d.parameter().name())));
//			}
//    });
  }


//  protected void transformKeywordTypeReferences(Document javaType) {
//    javaType.lexical().apply(KeywordTypeReference.class, k -> transformKeywordTypeReference(k));
//  }
//
//  protected void transformKeywordTypeReference(KeywordTypeReference javaKeywordTypeReference) {
////    KeywordTypeReference original = (KeywordTypeReference) javaKeywordTypeReference.origin();
//    BasicJavaTypeReference javaTypeReference = (BasicJavaTypeReference) javaKeywordTypeReference.typeConstructorReference();
//    //    Type jloTypeConstructorInstantiation = original.getElement();
//    //    List<TypeMemberDeclarator> typeMemberDeclarators = jloTypeConstructorInstantiation.members(TypeMemberDeclarator.class);
//    javaKeywordTypeReference.explicitTypeArguments().stream().sorted((d1, d2) -> d1.keyword().compareTo(d2.keyword())).forEachOrdered(jloTypeArgument -> {
//      javaTypeReference.addArgument(jloTypeArgument.argument());
//    });
//    javaKeywordTypeReference.parentLink().getOtherRelation().replace(javaKeywordTypeReference.parentLink(), javaTypeReference.parentLink());
//
//  }

}
