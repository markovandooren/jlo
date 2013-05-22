package be.kuleuven.cs.distrinet.jlo.model.expression;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.DeclarationContainer;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.TwoPhaseDeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.reference.CrossReferenceTarget;
import be.kuleuven.cs.distrinet.chameleon.core.reference.SimpleReference;
import be.kuleuven.cs.distrinet.chameleon.core.relation.WeakPartialOrder;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.Expression;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.MethodInvocation;
import be.kuleuven.cs.distrinet.chameleon.oo.language.ObjectOrientedLanguage;
import be.kuleuven.cs.distrinet.chameleon.oo.member.DeclarationWithParametersSignature;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MoreSpecificTypesOrder;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.support.member.simplename.method.NormalMethod;

public class SubobjectConstructorCall extends MethodInvocation<NormalMethod> {

  public SubobjectConstructorCall(SimpleReference<ComponentRelation> subobjectTarget) {
  	super(subobjectTarget);
  }

  public SubobjectConstructorCall(SimpleReference<ComponentRelation> subobjectTarget, List<Expression> arguments) {
  	super(subobjectTarget);
  	addAllArguments(arguments);
  }

  public SubobjectConstructorCall(String subobjectName) {
  	super(new SimpleReference<ComponentRelation>(subobjectName, ComponentRelation.class));
  }

  public SubobjectConstructorCall(String subobjectName, List<Expression> arguments) {
  	super(new SimpleReference<ComponentRelation>(subobjectName, ComponentRelation.class));
  	addAllArguments(arguments);
  }
  
  @Override
  public SimpleReference<ComponentRelation> getTarget() {
  	return (SimpleReference<ComponentRelation>) super.getTarget();
  }
	
	@Override
	protected SubobjectConstructorCall cloneSelf() {
		SubobjectConstructorCall result = new SubobjectConstructorCall((SimpleReference)null);
		//FIXME This is a hack iirc. Remove it!
		result.setOrigin(this);
		return result;
	}

	@Override
	public DeclarationSelector<NormalMethod> createSelector() {
		return new ConstructorSelector();
	}

	@Override
	protected Type actualType() throws LookupException {
		return language(ObjectOrientedLanguage.class).voidType(view().namespace());
	}
	
  public class ConstructorSelector extends TwoPhaseDeclarationSelector<NormalMethod> {
    
    public boolean selectedRegardlessOfName(NormalMethod declaration) throws LookupException {
    	return declaration.is(language(ObjectOrientedLanguage.class).CONSTRUCTOR)==Ternary.TRUE;
    }
    
		@Override
		public boolean selectedBasedOnName(Signature signature) throws LookupException {
    	boolean result = false;
			if(signature instanceof DeclarationWithParametersSignature) {
				List<Type> actuals = getActualParameterTypes();
				List<Type> formals = ((DeclarationWithParametersSignature)signature).parameterTypes();
				if (MoreSpecificTypesOrder.create().contains(actuals, formals)) {
						result = true;
				}
			}
      return result;
		}

    @Override
    public WeakPartialOrder<NormalMethod> order() {
      return new WeakPartialOrder<NormalMethod>() {
        @Override
        public boolean contains(NormalMethod first, NormalMethod second)
            throws LookupException {
          return MoreSpecificTypesOrder.create().contains(first.header().formalParameterTypes(), second.header().formalParameterTypes());
        }
      };
    }

		@Override
		public Class<NormalMethod> selectedClass() {
			return NormalMethod.class;
		}

		@Override
		public String selectionName(DeclarationContainer container) throws LookupException {
			// FIXME: this is horribly inefficient, but otherwise we must add more redundancy
			//        in the source code.
			return getTarget().getElement().componentType().signature().name();
		}

  }

//	@Override
//	public void setName(String name) {
//		getTarget().setName(name);
//	}
//
//	@Override
//	public String name() {
//		return getTarget().name();
//	}

}


/*
  
public class SubobjectConstructorCall extends Invocation<SubobjectConstructorCall, NormalMethod> {

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget, String name) {
  	super(subobjectTarget);
  	_name = name;
  }

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget, List<ActualArgument> arguments, String name) {
  	super(subobjectTarget);
  	addAllArguments(arguments);
  	_name = name;
  }

  public SubobjectConstructorCall(String subobjectName, String name) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  	_name = name;
  }

  public SubobjectConstructorCall(String subobjectName, List<ActualArgument> arguments, String name) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  	addAllArguments(arguments);
  	_name = name;
  }
  
  @Override
  public CrossReferenceTarget<ComponentRelation> getTarget() {
  	return (CrossReferenceTarget<ComponentRelation>) super.getTarget();
  }
  
  private String _name;
  
  public String name() {
  	return _name;
  }
	
	@Override
	protected SubobjectConstructorCall cloneInvocation(InvocationTarget target) {
		List<ActualArgument> arguments = new ArrayList<ActualArgument>();
		return new SubobjectConstructorCall((CrossReferenceTarget<ComponentRelation>)getTarget().clone(), arguments,name());
	}

	@Override
	public DeclarationSelector<NormalMethod> createSelector() {
		return new ConstructorSelector();
	}

	@Override
	protected Type actualType() throws LookupException {
		return language(ObjectOrientedLanguage.class).voidType();
	}
	
  public class ConstructorSelector extends DeclarationSelector<NormalMethod> {
    
    public boolean selectedRegardlessOfName(NormalMethod declaration) throws LookupException {
    	return declaration.is(language(ObjectOrientedLanguage.class).CONSTRUCTOR)==Ternary.TRUE;
    }
    
		@Override
		public boolean selectedBasedOnName(Signature signature) throws LookupException {
    	boolean result = false;
			if(signature instanceof MethodSignature) {
				MethodSignature<?,?> sig = (MethodSignature<?,?>)signature;
				List<Type> actuals = getActualParameterTypes();
				List<Type> formals = ((MethodSignature)signature).parameterTypes();
				if (new MoreSpecificTypesOrder().contains(actuals, formals)) {
						result = true;
				}
			}
      return result;
		}

    @Override
    public WeakPartialOrder<NormalMethod> order() {
      return new WeakPartialOrder<NormalMethod>() {
        @Override
        public boolean contains(NormalMethod first, NormalMethod second)
            throws LookupException {
          return new MoreSpecificTypesOrder().contains(first.header().getParameterTypes(), second.header().getParameterTypes());
        }
      };
    }

		@Override
		public Class<NormalMethod> selectedClass() {
			return NormalMethod.class;
		}

		@Override
		public String selectionName() {
			return name();
		}



  }


}

 
 */
