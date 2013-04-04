package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.element.ElementImpl;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupContext;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.VerificationResult;
import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeElementStub;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

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
	public LookupContext lookupContext(Element child) throws LookupException {
		if(child.origin() == child) {
			throw new ChameleonProgrammerException("A child of a component stub has itself as origin.");
		}
		return child.origin().lexicalContext();
	}


}
