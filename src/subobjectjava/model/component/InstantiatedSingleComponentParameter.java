package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.lookup.SelectorWithoutOrder.SignatureSelector;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ModelException;
import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;

public class InstantiatedSingleComponentParameter extends InstantiatedComponentParameter<InstantiatedSingleComponentParameter> {
	
	public InstantiatedSingleComponentParameter(SimpleNameSignature sig, String name) {
		super(sig);
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
	
	public ComponentRelation relation() throws LookupException {
		Type enclosing = nearestAncestor(DerivedType.class);
		if(enclosing == null) {
			throw new LookupException("The instantiated single component parameter is not in a derived type.");
		} else {
			ComponentRelation result = null;
			Type original = (Type) enclosing.origin();
			ParameterBlock<?,?> block = original.parameterBlock(ComponentParameter.class);
			FormalComponentParameter p = null;
			for(Parameter param: block.parameters()) {
				if(param.signature().name().equals(_name)) {
					p = (FormalComponentParameter) param;
				}
			}
			if(p == null) {
				throw new LookupException("There is no formal component parameter with a matching signature. Cannot find the type of the component container.");
			} else {
				// enclosing now becomes the enclosing type of the actual component relation.
				enclosing = p.componentType();
				SignatureSelector signatureSelector = new SignatureSelector() {
					
					private Signature _signature = new SimpleNameSignature(_name);

					public Signature signature() {
						return _signature;
					}
				}; 
				result = enclosing.targetContext().lookUp(new SelectorWithoutOrder<ComponentRelation>(signatureSelector, ComponentRelation.class));
			}
			return result;
		}
		
	}

	public LookupStrategy targetContext() throws LookupException {
		return relation().targetContext();
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	@Override
	public TargetDeclaration selectionDeclaration() throws LookupException {
		return relation();
	}

	@Override
	public InstantiatedSingleComponentParameter clone() {
		return new InstantiatedSingleComponentParameter(signature().clone(),name());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

}
