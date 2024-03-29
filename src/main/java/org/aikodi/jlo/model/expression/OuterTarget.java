package org.aikodi.jlo.model.expression;


import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.jlo.model.subobject.Subobject;

/**
 * @author Marko van Dooren
 */
public class OuterTarget extends AbstractTarget {

  public OuterTarget() {
	}
  

  public Type getTargetDeclaration() {
    return lexical().nearestAncestor(Subobject.class).lexical().nearestAncestor(Type.class);
  }

  protected OuterTarget cloneSelf() {
	  return new OuterTarget();
	}

}
