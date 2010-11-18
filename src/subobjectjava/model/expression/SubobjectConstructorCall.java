package subobjectjava.model.expression;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.logic.ternary.Ternary;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.DeclarationContainer;
import chameleon.core.declaration.Signature;
import chameleon.core.expression.CrossReferenceTarget;
import chameleon.core.expression.Expression;
import chameleon.core.expression.Invocation;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.TwoPhaseDeclarationSelector;
import chameleon.core.method.MethodSignature;
import chameleon.core.relation.WeakPartialOrder;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.type.Type;
import chameleon.support.member.MoreSpecificTypesOrder;
import chameleon.support.member.simplename.method.NormalMethod;

public class SubobjectConstructorCall extends Invocation<SubobjectConstructorCall, NormalMethod> {

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget) {
  	super(subobjectTarget);
  }

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget, List<Expression> arguments) {
  	super(subobjectTarget);
  	addAllArguments(arguments);
  }

  public SubobjectConstructorCall(String subobjectName) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  }

  public SubobjectConstructorCall(String subobjectName, List<Expression> arguments) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  	addAllArguments(arguments);
  }
  
  @Override
  public CrossReferenceTarget<ComponentRelation> getTarget() {
  	return (CrossReferenceTarget<ComponentRelation>) super.getTarget();
  }
	
	@Override
	protected SubobjectConstructorCall cloneInvocation(InvocationTarget target) {
		return new SubobjectConstructorCall((CrossReferenceTarget<ComponentRelation>)getTarget().clone(), new ArrayList<Expression>());
	}

	@Override
	public DeclarationSelector<NormalMethod> createSelector() {
		return new ConstructorSelector();
	}

	@Override
	protected Type actualType() throws LookupException {
		return language(ObjectOrientedLanguage.class).voidType();
	}
	
  public class ConstructorSelector extends TwoPhaseDeclarationSelector<NormalMethod> {
    
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
		public String selectionName(DeclarationContainer<?,?> container) throws LookupException {
			// FIXME: this is horribly inefficient, but otherwise we must add more redundancy
			//        in the source code.
			return getTarget().getElement().componentType().signature().name();
		}

  }

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
