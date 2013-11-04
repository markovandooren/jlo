package be.kuleuven.cs.distrinet.jlo.model.expression;

import be.kuleuven.cs.distrinet.jlo.model.component.FormalComponentParameter;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationCollector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclaratorSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.NameSelector;
import be.kuleuven.cs.distrinet.chameleon.core.reference.CrossReference;
import be.kuleuven.cs.distrinet.chameleon.core.reference.CrossReferenceTarget;
import be.kuleuven.cs.distrinet.chameleon.core.reference.UnresolvableCrossReference;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.Expression;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

public class ComponentParameterCall extends Expression implements CrossReference<FormalComponentParameter> {

	public ComponentParameterCall(CrossReferenceTarget target, String name) {
		setName(name);
		setTarget(target);
	}
	public ComponentParameterCall(String name) {
		this(null,name);
	}
	
	public String name() {
		return _name;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	private String _name;

	@Override
	protected Type actualType() throws LookupException {
		return getElement().declarationType();
	}

	@Override
	protected ComponentParameterCall cloneSelf() {
		return new ComponentParameterCall(null,name());
	}

	@Override
	public Verification verifySelf() {
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
    lexicalContext().lookUp(collector);
    result = collector.result();//findElement(getName());
    if(result != null) {
      return result;
    } else {
    	// repeat for debugging purposes
    	collector = new DeclarationCollector<X>(selector);
    	lexicalContext().lookUp(collector);//findElement(getName());
    	result = collector.result(); 
    	throw new LookupException("Lookup of component parameter with name: "+name()+" returned null.");
    }
  }

	public DeclarationSelector<FormalComponentParameter> selector() {
		return new NameSelector<FormalComponentParameter>(FormalComponentParameter.class) {
			public String name() {
				return ComponentParameterCall.this.name();
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
