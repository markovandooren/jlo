package jlo.input;

import chameleon.core.declaration.SimpleNameSignature;
import chameleon.oo.type.RegularType;
import jlo.model.type.RegularJLoType;
import jnome.input.JavaFactory;

public class JLoFactory extends JavaFactory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(SimpleNameSignature signature) {
		return new RegularJLoType(signature);
	}
	
}
