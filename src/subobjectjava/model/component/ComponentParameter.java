package subobjectjava.model.component;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.oo.type.Parameter;

public abstract class ComponentParameter<E extends ComponentParameter<E>> extends Parameter<E,TargetDeclaration> implements TargetDeclaration<E, Element, SimpleNameSignature, TargetDeclaration> {

	public ComponentParameter(SimpleNameSignature sig) {
		super(sig);
	}


}
