package be.kuleuven.cs.distrinet.jlo.input;

import be.kuleuven.cs.distrinet.chameleon.oo.type.RegularType;
import be.kuleuven.cs.distrinet.jlo.model.type.RegularJLoType;
import be.kuleuven.cs.distrinet.jnome.input.JavaFactory;

public class JLoFactory extends JavaFactory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(String name) {
		return new RegularJLoType(name);
	}
	
}
