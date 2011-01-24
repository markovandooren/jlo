package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.SelectorWithoutOrder.SignatureSelector;
import chameleon.exception.ChameleonProgrammerException;

public abstract class SingleActualComponentArgument<E extends SingleActualComponentArgument<E>> extends ActualComponentArgument<E> {

	public SingleActualComponentArgument(String name) {
		setName(name);
	}
	
	public abstract E clone();
	
	private Signature _signature;
	public String name() {
		return _signature.name();
	}
	
	public Signature signature() {
		return _signature;
	}
	
	public void setName(String name) {
		_signature.setName(name);
	}
	
	public List<? extends Element> children() {
		return new ArrayList<Element>();
	}


}
