package subobjectjava.input;

import subobjectjava.model.type.RegularJLoType;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.oo.type.RegularType;
import jnome.input.JavaFactory;

public class JLoFactory extends JavaFactory {

	@Override
	public RegularType createRegularType(SimpleNameSignature signature) {
		return new RegularJLoType(signature);
	}

	
}
