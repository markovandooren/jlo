package subobjectjava.model.type;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.type.RegularType;

public class SubobjectJavaType extends RegularType {

	public SubobjectJavaType(SimpleNameSignature sig) {
		super(sig);
	}

	public SubobjectJavaType(String name) {
		super(name);
	}

	@Override
	public LookupStrategy lexicalLookupStrategy(Element element) throws LookupException {
		if(element instanceof ComponentRelation) {
			return lexicalParametersLookupStrategy();
		} else {
			return super.lexicalLookupStrategy(element);
		}
	}
}
