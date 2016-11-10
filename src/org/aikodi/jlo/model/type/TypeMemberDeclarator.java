package org.aikodi.jlo.model.type;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LocalLookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiersImpl;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeFixer;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeParameterFixer;
import org.aikodi.chameleon.util.Lists;
import org.aikodi.chameleon.util.association.Single;

public class TypeMemberDeclarator extends ElementWithModifiersImpl implements Declaration {

  private final class TypeMemberParameterFixer extends  TypeParameterFixer {

    private Single<TypeParameter> _parameter = new Single<>(this);

    public TypeMemberParameterFixer(TypeParameter parameter) {
      set(_parameter,parameter);
    }

    @Override
    protected List<TypeParameter> parameters() throws LookupException {
      List<TypeMemberDeclarator> members = nearestAncestor(Type.class).members(TypeMemberDeclarator.class);
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

  public TypeMemberDeclarator(SimpleNameSignature signature, TypeParameter parameter) {
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
  public Type selectionDeclaration() throws LookupException {
    //		TypeParameter parameter = createParameter();
    //		TypeMemberParameterFixer fixer = new TypeMemberParameterFixer();
    //		parameter.setUniParent(fixer);
    return parameter().selectionDeclaration();
  }

  private Single<TypeMemberParameterFixer> _fixer = new Single(this);

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

//  private Single<Signature> _signature = new Single<>(this);
//
//  private Single<TypeConstraint> _constraint = new Single<>(this);
//
//  public TypeConstraint constraint() {
//    return _constraint.getOtherEnd();
//  }
//
//  public void setConstraint(TypeConstraint constraint) {
//    set(_constraint, constraint);
//  }

  @Override
  protected Element cloneSelf() {
    return new TypeMemberDeclarator(null,null);
  }

  public boolean contains(TypeMemberDeclarator s, TypeFixer trace) throws LookupException {
    return selectionDeclaration().contains(s.selectionDeclaration(), trace);
  }



}
