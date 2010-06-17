package subobjectjava.model.expression;

import java.util.List;

import org.rejuse.association.SingleAssociation;

import subobjectjava.model.component.ComponentParameter;
import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.expression.Expression;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.DeclaratorSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.reference.CrossReference;
import chameleon.core.reference.UnresolvableCrossReference;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;
import chameleon.util.Util;

public class ComponentParameterCall extends Expression<ComponentParameterCall> implements CrossReference<ComponentParameterCall,Element,ComponentParameter> {

	public ComponentParameterCall(SimpleNameSignature signature) {
		setSignature(signature);
	}
	
	public ComponentParameterCall(InvocationTarget target, String name) {
		this(name);
		setTarget(target);
	}

	public ComponentParameterCall(String name) {
		this(new SimpleNameSignature(name));
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
		return getElement().componentType();
	}

	@Override
	public ComponentParameterCall clone() {
		return new ComponentParameterCall(signature().clone());
	}

	@Override
	public VerificationResult verifySelf() {
		ComponentParameter referencedElement;
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

  public ComponentParameter getElement() throws LookupException {
  	return getElement(selector());
  }
  
	public Declaration getDeclarator() throws LookupException {
		return getElement(new DeclaratorSelector(selector()));
	}
  
  @SuppressWarnings("unchecked")
  public <X extends Declaration> X getElement(DeclarationSelector<X> selector) throws LookupException {
    InvocationTarget target = target();
    X result;
    if(target != null) {
      result = target.targetContext().lookUp(selector);//findElement(getName());
    } else {
      result = lexicalLookupStrategy().lookUp(selector);//findElement(getName());
    }
    if(result != null) {
      return result;
    } else {
    	// repeat for debugging purposes
      if(target != null) {
        result = target.targetContext().lookUp(selector);//findElement(getName());
      } else {
        result = lexicalLookupStrategy().lookUp(selector);//findElement(getName());
      }
    	throw new LookupException("Lookup of component parameter with name: "+name()+" returned null.");
    }
  }

	public DeclarationSelector<ComponentParameter> selector() {
		return new SelectorWithoutOrder<ComponentParameter>(new SelectorWithoutOrder.SignatureSelector() {
			public Signature signature() {
				return ComponentParameterCall.this.signature();
			}
		}, ComponentParameter.class);
	}

	public List<? extends Element> children() {
		return Util.createNonNullList(target());
	}

	/**
	 * TARGET
	 */
	private SingleAssociation<ComponentParameterCall,InvocationTarget> _target = new SingleAssociation<ComponentParameterCall,InvocationTarget>(this);


  public InvocationTarget target() {
    return _target.getOtherEnd();
  }

  public void setTarget(InvocationTarget target) {
    if (target != null) {
      _target.connectTo(target.parentLink());
    }
    else {
      _target.connectTo(null);
    }
  }

}
