//package org.aikodi.jlo.model.type;
//
//import java.util.Set;
//
//import org.aikodi.chameleon.core.element.Element;
//import org.aikodi.chameleon.core.element.ElementImpl;
//import org.aikodi.chameleon.oo.type.generics.TypeArgument;
//import org.aikodi.chameleon.util.association.Single;
//import org.aikodi.rejuse.contract.Contracts;
//
///**
// *
// * @author Marko van Dooren
// */
//public class KeywordTypeArgument extends ElementImpl {
//
//  private String _name;
//
//  public KeywordTypeArgument(String name, TypeArgument argument) {
//    setKeyword(name);
//    setArgument(argument);
//  }
//
//  /**
//   * @return The name of this keyword type reference.
//   */
//  public String keyword() {
//    return _name;
//  }
//
//  /**
//   *
//   * @param keyword The new name of this keyword type reference.
//   *             The name cannot be null.
//   */
//  public void setKeyword(String keyword) {
//    Contracts.notNull(keyword, "The name of a keyword type expression cannot be null.");
//    _name = keyword;
//  }
//
//  private final Single<TypeArgument> _argument = new Single<>(this, "argument");
//
//  @Override
//  protected Element cloneSelf() {
//    return new KeywordTypeArgument(keyword(),null);
//  }
//
//  public TypeArgument argument() {
//    return _argument.getOtherEnd();
//  }
//
//  public void setArgument(TypeArgument argument) {
//    set(_argument,argument);
//  }
//
//  public String toString(Set<Element> visited) {
//    StringBuilder builder = new StringBuilder();
//    builder.append(keyword());
//    builder.append(' ');
//    builder.append(argument().toString(visited));
//    return builder.toString();
//  }
//}
