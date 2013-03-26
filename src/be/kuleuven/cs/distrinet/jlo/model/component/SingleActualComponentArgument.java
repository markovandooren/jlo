package be.kuleuven.cs.distrinet.jlo.model.component;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

public abstract class SingleActualComponentArgument extends ActualComponentArgument {

	public SingleActualComponentArgument(String name) {
		setSignature(new SimpleNameSignature(name));
	}
	
	public abstract SingleActualComponentArgument clone();
	
	private Single<Signature> _signature = new Single<Signature>(this);
	
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
		set(_signature, signature);
	}
}
