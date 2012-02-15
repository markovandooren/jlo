package jlo.model.expression;

import java.util.List;

import jlo.model.component.FormalComponentParameter;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
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
import chameleon.util.Util;

public class ComponentParameterCall extends Expression<ComponentParameterCall> implements CrossReference<ComponentParameterCall,FormalComponentParameter> {

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
		setAsParent(_signature, signature);
	}
	
	public SimpleNameSignature signature() {
		return _signature.getOtherEnd();
	}
	
	public void setName(String name) {
		signature().setName(name);
	}
	
	private SingleAssociation<ComponentParameterCall,SimpleNameSignature> _signature = new SingleAssociation<ComponentParameterCall,SimpleNameSignature>(this);

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
//    InvocationTarget target = target();
    X result;
//    if(target != null) {
//      result = target.targetContext().lookUp(selector);//findElement(getName());
//    } else {
      result = lexicalLookupStrategy().lookUp(selector);//findElement(getName());
//    }
    if(result != null) {
      return result;
    } else {
    	// repeat for debugging purposes
//      if(target != null) {
//        result = target.targetContext().lookUp(selector);//findElement(getName());
//      } else {
        result = lexicalLookupStrategy().lookUp(selector);//findElement(getName());
//      }
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

	public List<? extends Element> children() {
		return Util.createNonNullList(target());
	}

	/**
	 * TARGET
	 */
	private SingleAssociation<ComponentParameterCall,CrossReferenceTarget> _target = new SingleAssociation<ComponentParameterCall,CrossReferenceTarget>(this);


  public CrossReferenceTarget target() {
    return _target.getOtherEnd();
  }

  public void setTarget(CrossReferenceTarget target) {
    if (target != null) {
      _target.connectTo(target.parentLink());
    }
    else {
      _target.connectTo(null);
    }
  }

}
