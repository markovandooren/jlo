package jlo.model.component;

import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.element.Element;
import chameleon.core.element.ElementImpl;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.type.TypeElementStub;
import chameleon.util.Util;

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

	private SingleAssociation<ComponentStub, Declaration> _element = new SingleAssociation<ComponentStub, Declaration>(this);

	public void setChild(Declaration element) {
		setAsParent(_element, element);
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

	public List<? extends Element> children() {
		return Util.createNonNullList(child());
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
