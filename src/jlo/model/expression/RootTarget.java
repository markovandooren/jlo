package jlo.model.expression;

import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.lookup.LookupException;
import chameleon.oo.type.Type;

public class RootTarget extends AbstractTarget<RootTarget> {

	@Override
	public Type getTargetDeclaration() {
		return farthestAncestor(Type.class);
	}

	@Override
	public RootTarget clone() {
		return new RootTarget();
	}

}
