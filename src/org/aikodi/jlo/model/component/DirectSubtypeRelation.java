package org.aikodi.jlo.model.component;

import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;

public class DirectSubtypeRelation extends IncorporatingSubtypeRelation {

	public DirectSubtypeRelation(Type inherited) {
		_inherited = inherited;
	}
	
	private Type _inherited;
	
  
	
	@Override
	public Type superClass() {
		return _inherited;
	}

	@Override
	public TypeReference superClassReference() {
		throw new ChameleonProgrammerException();
	}

	protected SubtypeRelation cloneSelf() {
		return new DirectSubtypeRelation(superClass());
	}


}
