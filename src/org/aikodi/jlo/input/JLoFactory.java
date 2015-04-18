package org.aikodi.jlo.input;

import org.aikodi.chameleon.oo.type.RegularType;
import org.aikodi.jlo.model.type.RegularJLoType;

import be.kuleuven.cs.distrinet.jnome.input.Java7Factory;

public class JLoFactory extends Java7Factory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(String name) {
		return new RegularJLoType(name);
	}
	
}
