package be.kuleuven.cs.distrinet.jlo.model.expression;


import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;

/**
 * @author Marko van Dooren
 */
public class OuterTarget extends AbstractTarget {

  public OuterTarget() {
	}
  

  public Type getTargetDeclaration() {
    return nearestAncestor(ComponentRelation.class).nearestAncestor(Type.class);
  }

  protected OuterTarget cloneSelf() {
	  return new OuterTarget();
	}

}
