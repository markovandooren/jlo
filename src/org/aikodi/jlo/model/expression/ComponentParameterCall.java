package org.aikodi.jlo.model.expression;

import org.aikodi.chameleon.core.lookup.DeclarationCollector;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.NameSelector;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.core.reference.UnresolvableCrossReference;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.util.association.Single;
import org.aikodi.jlo.model.component.FormalComponentParameter;

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

	/**
	* @{inheritDoc}
	*/
	@Override
	public Class<FormalComponentParameter> referencedType() {
	  return FormalComponentParameter.class;
	}
	
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
  	DeclarationSelector<FormalComponentParameter> selector = new NameSelector<FormalComponentParameter>(FormalComponentParameter.class) {
    	public String name() {
    		return ComponentParameterCall.this.name();
    	}
    };
    FormalComponentParameter result;
    DeclarationCollector<FormalComponentParameter> collector = new DeclarationCollector<FormalComponentParameter>(selector);
    lexicalContext().lookUp(collector);
    result = collector.result();//findElement(getName());
    if(result != null) {
      return result;
    } else {
    	// repeat for debugging purposes
    	collector = new DeclarationCollector<FormalComponentParameter>(selector);
    	lexicalContext().lookUp(collector);//findElement(getName());
    	result = collector.result(); 
    	throw new LookupException("Lookup of component parameter with name: "+name()+" returned null.");
    }
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
