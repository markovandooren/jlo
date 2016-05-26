package org.aikodi.jlo.model.subobject;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.type.TypeElementStub;
import org.aikodi.chameleon.util.association.Single;

public class ComponentStub extends ElementImpl implements TypeElementStub {
	
	public ComponentStub(Subobject generator, Declaration child) {
		setChild(child);
		setGenerator(generator);
	}
	
	private Subobject _generator;
	
	private void setGenerator(Subobject generator) {
		_generator = generator;
	}
	
	public Subobject generator() {
		return _generator;
	}

	private Single<Declaration> _element = new Single<Declaration>(this);

	public void setChild(Declaration element) {
		set(_element, element);
	}
	
	public Declaration child() {
		return _element.getOtherEnd();
	}

	@Override
	protected ComponentStub cloneSelf() {
		return new ComponentStub(generator(),null);
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	@Override
	public LookupContext lookupContext(Element child) throws LookupException {
		if(child.origin() == child) {
			throw new ChameleonProgrammerException("A child of a component stub has itself as origin.");
		}
		return child.origin().lexicalContext(); // this is wrong. It should behave like a subclass: look for inheritable declarations,
	}


}
