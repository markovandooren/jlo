package org.aikodi.jlo.input;

import org.aikodi.chameleon.oo.type.RegularType;
import org.aikodi.java.input.Java7Factory;
import org.aikodi.jlo.model.type.RegularJLoType;

public class JLoFactory extends Java7Factory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(String name) {
		return new RegularJLoType(name);
	}
	
}
