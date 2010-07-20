package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.lookup.SelectorWithoutOrder.SignatureSelector;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public class SingleActualComponentArgument extends ActualComponentArgument<SingleActualComponentArgument> {

	public SingleActualComponentArgument(String name) {
		setName(name);
	}
	
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

	@Override
	public ComponentRelation declaration() throws LookupException {
		//Type enclosing = tref.target().getElement();
		Type enclosing = containerType();
		SignatureSelector signatureSelector = new SignatureSelector() {
			private Signature _signature = new SimpleNameSignature(_name);

			public Signature signature() {
				return _signature;
			}
		}; 
		ComponentRelation result = enclosing.targetContext().lookUp(new SelectorWithoutOrder<ComponentRelation>(signatureSelector, ComponentRelation.class));
		if(result == null) {
			System.out.println("debug");
		}
		return result;
	}

	@Override
	public SingleActualComponentArgument clone() {
		return new SingleActualComponentArgument(name());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

}
