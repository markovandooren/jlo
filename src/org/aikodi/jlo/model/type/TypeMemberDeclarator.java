package org.aikodi.jlo.model.type;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.DeclarationImpl;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.Name;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeFixer;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeParameterFixer;
import org.aikodi.chameleon.util.Lists;
import org.aikodi.chameleon.util.association.Single;

/**
 * A class of elements that introduces a type member into a class.
 * 
 * @author Marko van Dooren
 */
public class TypeMemberDeclarator extends DeclarationImpl implements Declaration {

	/**
	 * A class for providing the fixed point computation for type members.
	 * 
	 * @author Marko van Dooren
	 */
  private final class TypeMemberParameterFixer extends TypeParameterFixer {
    private Single<TypeParameter> _parameter = new Single<>(this, "parameter");

    public TypeMemberParameterFixer(TypeParameter parameter) {
      set(_parameter,parameter);
    }

    @Override
    protected List<TypeParameter> parameters() throws LookupException {
      List<TypeMemberDeclarator> members = lexical().nearestAncestor(Type.class).members(TypeMemberDeclarator.class);
      List<TypeParameter> result = Lists.create(members.size());
      result.add(parameter());
      // FIXME inefficient. Just store an immutable list.
      members.forEach(m -> {
        if(m != TypeMemberDeclarator.this) {
          TypeParameter parameter = clone(m.parameter());
          parameter.setUniParent(this);
          result.add(parameter);
        }
      });
      return result;
    }

    /**
     * @return
     */
    private TypeParameter parameter() {
      return _parameter.getOtherEnd();
    }

    @Override
    protected Element cloneSelf() {
      return new TypeMemberParameterFixer(null);
    }
  }

  public TypeMemberDeclarator(Name signature, TypeParameter parameter) {
  	set(_signature, signature);
    set(_fixer, new TypeMemberParameterFixer(parameter));
  }

  @Override
  public List<Declaration> declaredDeclarations() {
  	List<Declaration> result = new ArrayList<>();
  	result.add(this);
  	result.add(parameter());
  	return result;
  }

  @Override
  public LocalLookupContext<?> targetContext() throws LookupException {
    return selectionDeclaration().targetContext();
  }

  @Override
  public Declaration selectionDeclaration() throws LookupException {
    //		TypeParameter parameter = createParameter();
    //		TypeMemberParameterFixer fixer = new TypeMemberParameterFixer();
    //		parameter.setUniParent(fixer);
  	
//    return parameter().selectionDeclaration();
  	return this;
  }

  private Single<TypeMemberParameterFixer> _fixer = new Single<>(this, "fixer");

  /**
   * @return The type parameter introduced by this type member declarator.
   */
  public TypeParameter parameter() {
    return _fixer.getOtherEnd().parameter();
  }

  @Override
  public Signature signature() {
    return _signature.getOtherEnd();
  }
  
  private Single<Signature> _signature = new Single<>(this);
  
  @Override
  public void setSignature(Signature signature) {
    set(_signature, signature);
  }

  @Override
  protected Element cloneSelf() {
    return new TypeMemberDeclarator(null,null);
  }

  public boolean contains(TypeMemberDeclarator s, TypeFixer trace) throws LookupException {
    return parameter().selectionDeclaration().contains(s.parameter().selectionDeclaration(), trace);
  }

}
