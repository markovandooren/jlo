package jlo.model.expression;

import chameleon.oo.type.Type;

public class RootTarget extends AbstractTarget {

	@Override
	public Type getTargetDeclaration() {
		return farthestAncestor(Type.class);
	}

	@Override
	public RootTarget clone() {
		return new RootTarget();
	}

}
