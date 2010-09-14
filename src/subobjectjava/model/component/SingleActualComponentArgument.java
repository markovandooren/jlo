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
	
	private String _name;
	
	public String name() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public List<? extends Element> children() {
		return new ArrayList<Element>();
	}

	protected SignatureSelector selector() {
		SignatureSelector signatureSelector = new SignatureSelector() {
			private Signature _signature = new SimpleNameSignature(name());

			public Signature signature() {
				return _signature;
			}
		};
		return signatureSelector;
	}

}
