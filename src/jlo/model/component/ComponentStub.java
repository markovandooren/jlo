package jlo.model.component;

import chameleon.core.declaration.Declaration;
import chameleon.core.element.Element;
import chameleon.core.element.ElementImpl;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.type.TypeElementStub;
import chameleon.util.association.Single;

public class ComponentStub extends ElementImpl implements TypeElementStub {
	
	public ComponentStub(ComponentRelation generator, Declaration child) {
		setChild(child);
		setGenerator(generator);
	}
	
	private ComponentRelation _generator;
	
	private void setGenerator(ComponentRelation generator) {
		_generator = generator;
	}
	
	public ComponentRelation generator() {
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
	public ComponentStub clone() {
		return new ComponentStub(generator(),child().clone());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	@Override
	public LookupStrategy lexicalLookupStrategy(Element child) throws LookupException {
		if(child.origin() == child) {
			System.out.println("debug");
			throw new ChameleonProgrammerException("A child of a component stub has itself as origin.");
		}
		return child.origin().lexicalLookupStrategy();
	}


}
