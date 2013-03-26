package be.kuleuven.cs.distrinet.jlo.input;

import be.kuleuven.cs.distrinet.jlo.model.type.RegularJLoType;
import be.kuleuven.cs.distrinet.jnome.input.JavaFactory;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.oo.type.RegularType;

public class JLoFactory extends JavaFactory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(SimpleNameSignature signature) {
		return new RegularJLoType(signature);
	}
	
}
