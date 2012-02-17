package jlo.model.expression;


import jlo.model.component.ComponentRelation;
import chameleon.oo.type.Type;

/**
 * @author Marko van Dooren
 */
public class OuterTarget extends AbstractTarget {

  public OuterTarget() {
	}
  

  public Type getTargetDeclaration() {
    return nearestAncestor(ComponentRelation.class).nearestAncestor(Type.class);
  }

	public OuterTarget clone() {
	  return new OuterTarget();
	}

}
