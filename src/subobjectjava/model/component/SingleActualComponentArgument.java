package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;

public abstract class SingleActualComponentArgument<E extends SingleActualComponentArgument<E>> extends ActualComponentArgument<E> {

	public SingleActualComponentArgument(String name) {
		setSignature(new SimpleNameSignature(name));
	}
	
	public abstract E clone();
	
	private SingleAssociation<SingleActualComponentArgument,Signature> _signature = new SingleAssociation<SingleActualComponentArgument,Signature>(this);
	
	public String name() {
		return signature().name();
	}
	
	public Signature signature() {
		return _signature.getOtherEnd();
	}
	
	public void setName(String name) {
		signature().setName(name);
	}
	
	public void setSignature(Signature signature) {
		setAsParent(_signature, signature);
	}
	
	public List<? extends Element> children() {
		return new ArrayList<Element>();
	}


}
