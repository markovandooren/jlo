package subobjectjava.model.expression;


import subobjectjava.model.component.ComponentRelation;
import chameleon.oo.type.Type;

/**
 * @author Marko van Dooren
 */
public class OuterTarget extends AbstractTarget<OuterTarget> {

  public OuterTarget() {
	}
  

  public Type getTargetDeclaration() {
    return nearestAncestor(ComponentRelation.class).nearestAncestor(Type.class);
  }

	public OuterTarget clone() {
	  return new OuterTarget();
	}


}
