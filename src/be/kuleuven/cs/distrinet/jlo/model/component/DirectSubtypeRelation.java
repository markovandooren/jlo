package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.oo.type.TypeReference;
import be.kuleuven.cs.distrinet.chameleon.oo.type.inheritance.SubtypeRelation;

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
