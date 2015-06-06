package org.aikodi.jlo.model.type;

import java.util.Set;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.util.association.Single;

import be.kuleuven.cs.distrinet.rejuse.contract.Contracts;

/**
 *  
 * @author Marko van Dooren
 */
public class KeywordTypeReference extends ElementImpl implements TypeReference {

  private String _name;
  
  public KeywordTypeReference(String name) {
    setName(name);
  }
  
  public KeywordTypeReference(String name, TypeReference typeReference) {
    this(name);
    setTypeConstructorReference(typeReference);
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
  
  private final Single<TypeReference> _typeConstructorReference = new Single<>(this);

  @Override
  public Type getElement() throws LookupException {
    JLoType typeConstructor = (JLoType) typeConstructorReference().getElement();
    Type result = new JLoTypeRefinement(typeConstructor);
    result.add(new TypeMemberDeclarator(name()));
    return result;
  }

  @Override
  protected Element cloneSelf() {
    return new KeywordTypeReference(name());
  }
  
  public TypeReference typeConstructorReference() {
    return _typeConstructorReference.getOtherEnd();
  }
  
  public void setTypeConstructorReference(TypeReference typeReference) {
    set(_typeConstructorReference,typeReference);
  }
  
  @Override
  public String toString(Set<Element> visited) {
      return name();
  }

}
