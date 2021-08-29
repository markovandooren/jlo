package org.aikodi.jlo.model.type;

import java.util.Set;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.TypeArgument;
import org.aikodi.chameleon.util.association.Single;
import org.aikodi.rejuse.contract.Contracts;

/**
 *  
 * @author Marko van Dooren
 */
public class KeywordTypeArgument extends ElementImpl {

  private String _name;
  
//  public KeywordTypeReference(String name) {
//    setName(name);
//  }
  
  public KeywordTypeArgument(String name, TypeArgument argument) {
    setName(name);
    setArgument(argument);
  }
  
  /**
   * @return The name of this keyword type reference.
   */
  public String name() {
    return _name;
  }
  
  /**
   * 
   * @param name The new name of this keyword type reference.
   *             The name cannot be null.
   */
  public void setName(String name) {
    Contracts.notNull(name, "The name of a keyword type expression cannot be null.");
    _name = name;
  }
  
  private final Single<TypeArgument> _argument = new Single<>(this, "argument");

  @Override
  protected Element cloneSelf() {
    return new KeywordTypeArgument(name(),null);
  }
  
  public TypeArgument argument() {
    return _argument.getOtherEnd();
  }
  
  public void setArgument(TypeArgument argument) {
    set(_argument,argument);
  }
  
  public String toString(Set<Element> visited) {
    StringBuilder builder = new StringBuilder();
    builder.append(name());
    builder.append(' ');
    builder.append(argument().toString(visited));
    return builder.toString();
  }
}
