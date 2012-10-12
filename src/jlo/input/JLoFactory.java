package jlo.input;

import jlo.model.type.RegularJLoType;
import jnome.input.JavaFactory;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.oo.type.RegularType;

public class JLoFactory extends JavaFactory {

	public JLoFactory() {
		
	}
	@Override
	public RegularType createRegularType(SimpleNameSignature signature) {
		return new RegularJLoType(signature);
	}
	
}
