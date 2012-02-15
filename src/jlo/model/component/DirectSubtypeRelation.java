package jlo.model.component;

import chameleon.exception.ChameleonProgrammerException;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.inheritance.SubtypeRelation;

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

	protected SubtypeRelation cloneThis() {
		return new DirectSubtypeRelation(superClass());
	}


}
