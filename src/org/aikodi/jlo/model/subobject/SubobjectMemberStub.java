package org.aikodi.jlo.model.subobject;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.type.DeclaratorStub;
import org.aikodi.chameleon.util.association.Single;

/**
 * A stub for members of subobjects. Members of the formal type of
 * a subobject are copied into the subobjects. Otherwise, there
 * could be two members that override the same member. For example, a subobject
 * named 's' will introduce member 's.equals(Object)'. Another subobject
 * 't' introduces 't.equals(Object)'. 
 * 
 * @author Marko van Dooren
 */
public class SubobjectMemberStub extends ElementImpl implements DeclaratorStub {
	
	public SubobjectMemberStub(Subobject generator, Declaration child) {
		setChild(child);
		setGenerator(generator);
	}
	
	private Subobject _generator;
	
	private void setGenerator(Subobject generator) {
		_generator = generator;
	}

	/**
	 * @return The subobject that generated the member.
	 */
	public Subobject generator() {
		return _generator;
	}

	private Single<Declaration> _element = new Single<Declaration>(this);

	/**
	 * Set the child of this subobject member stub.
	 * @param element
	 */
	private void setChild(Declaration element) {
		set(_element, element);
	}
	
	public Declaration child() {
		return _element.getOtherEnd();
	}

	@Override
	protected SubobjectMemberStub cloneSelf() {
		return new SubobjectMemberStub(generator(),null);
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
		// FIXME TODO this is wrong. It should behave like a subclass: look for inheritable declarations
		return child.origin().lexicalContext(); 
	}


}
