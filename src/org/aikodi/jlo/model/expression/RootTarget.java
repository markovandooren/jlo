package org.aikodi.jlo.model.expression;

import org.aikodi.chameleon.oo.type.Type;

public class RootTarget extends AbstractTarget {

	@Override
	public Type getTargetDeclaration() {
		return lexical().farthestAncestor(Type.class);
	}

	@Override
	protected RootTarget cloneSelf() {
		return new RootTarget();
	}

}
