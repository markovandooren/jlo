/**
 * 
 */
package org.aikodi.jlo.model.type;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.LookupRedirector;
import org.aikodi.chameleon.core.reference.NameReference;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.generics.InstantiatedTypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.util.association.Multi;
import org.aikodi.chameleon.util.association.Single;
import org.aikodi.java.core.type.JavaTypeReference;

/**
 * @author Marko van Dooren
 *
 */
public class KeywordTypeReference extends ElementImpl implements JavaTypeReference {

  public KeywordTypeReference(TypeReference constructorReference) {
    setConstructorReference(constructorReference);
  }
  
  private final Multi<KeywordTypeArgument> _typeArguments = new Multi<>(this);
  
  public KeywordTypeArgument argument(String name) {
  	Optional<KeywordTypeArgument> result = arguments().stream().filter(a -> a.name().equals(name)).findAny();
  	if(result.isPresent()) {
  		return result.get();
  	} else {
  		throw new IllegalArgumentException("There is no keyword type reference with the given name.");
  	}
  }
  
  public boolean hasArgument(String name) {
  	return _typeArguments.size() > 0;
  }
  
  public void add(KeywordTypeArgument argument) {
    add(_typeArguments, argument);
  }
  
  public List<KeywordTypeArgument> arguments() {
    return _typeArguments.getOtherEnds();
  }
  
  private Single<TypeReference> _typeConstructorReference = new Single<>(this);
  
  public void setConstructorReference(TypeReference typeReference) {
    set(_typeConstructorReference, typeReference);
  }
  
  public TypeReference typeConstructorReference() {
    return _typeConstructorReference.getOtherEnd();
  }
  
  @Override
  public Type getElement() throws LookupException {
    JLoType typeConstructor = (JLoType) typeConstructorReference().getElement();
    Type result = new JLoTypeRefinement(typeConstructor);
    for(KeywordTypeArgument a: arguments()) {
    	// The name is wrong. It won't be found!
    	NameReference<TypeMemberDeclarator> nameReference = new NameReference<>(a.name(), TypeMemberDeclarator.class);
    	// The constructor sets the unidirectional parent of the name reference.
    	new LookupRedirector(typeConstructor,nameReference);
    	TypeMemberDeclarator element = nameReference.getElement();
      TypeParameter typeArgument = new InstantiatedTypeParameter(element.parameter().name(), a.argument());
      TypeMemberDeclarator declarator = new TypeMemberDeclarator(new SimpleNameSignature(a.name()), typeArgument);
      result.add(declarator);
    }
    return result;
  }

	@Override
	public String toString() {
		return toString(new HashSet<>());
	}
	
  /**
   * @{inheritDoc}
   */
  @Override
  public String toString(Set<Element> visited) {
    StringBuilder builder = new StringBuilder();
    builder.append(typeConstructorReference().toString(visited)).append(' ');
    arguments().forEach(a -> builder.append(a.toString(visited)));
    return builder.toString();
  }

  /**
  /**
   * @{inheritDoc}
   */
  @Override
  public JavaTypeReference erasedReference() {
    return this;
  }

  /**
   * @{inheritDoc}
   */
  @Override
  public JavaTypeReference componentTypeReference() {
    return this;
  }

  /**
   * @{inheritDoc}
   */
  @Override
  protected Element cloneSelf() {
    return new KeywordTypeReference(null);
  }

}
