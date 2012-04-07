package jlo.model.expression;

import jlo.model.component.FormalComponentParameter;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.DeclarationCollector;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.DeclaratorSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.reference.CrossReference;
import chameleon.core.reference.CrossReferenceTarget;
import chameleon.core.reference.UnresolvableCrossReference;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.expression.Expression;
import chameleon.oo.type.Type;
import chameleon.util.association.Single;

public class ComponentParameterCall extends Expression implements CrossReference<FormalComponentParameter> {

	public ComponentParameterCall(CrossReferenceTarget target, SimpleNameSignature signature) {
		setSignature(signature);
		setTarget(target);
	}
	public ComponentParameterCall(SimpleNameSignature signature) {
		this(null,signature);
	}
	
	public ComponentParameterCall(CrossReferenceTarget target, String name) {
		this(target,new SimpleNameSignature(name));
	}

	public ComponentParameterCall(String name) {
		this(null,name);
	}

	public String name() {
		return signature().name();
	}
	
	public void setSignature(SimpleNameSignature signature) {
		set(_signature, signature);
	}
	
	public SimpleNameSignature signature() {
		return _signature.getOtherEnd();
	}
	
	public void setName(String name) {
		signature().setName(name);
	}
	
	private Single<SimpleNameSignature> _signature = new Single<SimpleNameSignature>(this);

	@Override
	protected Type actualType() throws LookupException {
		return getElement().declarationType();
	}

	@Override
	public ComponentParameterCall clone() {
		return new ComponentParameterCall(target().clone(),signature().clone());
	}

	@Override
	public VerificationResult verifySelf() {
		FormalComponentParameter referencedElement;
		try {
			referencedElement = getElement();
			if(referencedElement != null) {
				return Valid.create();
			} else {
				return new UnresolvableCrossReference(this);
			}
		} catch (LookupException e) {
			return new UnresolvableCrossReference(this);
		}
	}

  public FormalComponentParameter getElement() throws LookupException {
  	return getElement(selector());
  }
  
	public Declaration getDeclarator() throws LookupException {
		return getElement(new DeclaratorSelector(selector()));
	}
  
  @SuppressWarnings("unchecked")
  public <X extends Declaration> X getElement(DeclarationSelector<X> selector) throws LookupException {
    X result;
    DeclarationCollector<X> collector = new DeclarationCollector<X>(selector);
    lexicalLookupStrategy().lookUp(collector);
    result = collector.result();//findElement(getName());
    if(result != null) {
      return result;
    } else {
    	// repeat for debugging purposes
    	collector = new DeclarationCollector<X>(selector);
    	lexicalLookupStrategy().lookUp(collector);//findElement(getName());
    	result = collector.result(); 
    	throw new LookupException("Lookup of component parameter with name: "+name()+" returned null.");
    }
  }

	public DeclarationSelector<FormalComponentParameter> selector() {
		return new SelectorWithoutOrder<FormalComponentParameter>(FormalComponentParameter.class) {
			public Signature signature() {
				return ComponentParameterCall.this.signature();
			}
		};
	}

	/**
	 * TARGET
	 */
	private Single<CrossReferenceTarget> _target = new Single<CrossReferenceTarget>(this);


  public CrossReferenceTarget target() {
    return _target.getOtherEnd();
  }

  public void setTarget(CrossReferenceTarget target) {
    set(_target,target);
  }

}
