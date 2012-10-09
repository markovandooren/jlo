package jlo.model.expression;

import java.util.ArrayList;
import java.util.List;

import jlo.model.component.ComponentRelation;

import org.rejuse.logic.ternary.Ternary;

import chameleon.core.declaration.DeclarationContainer;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.TwoPhaseDeclarationSelector;
import chameleon.core.reference.CrossReferenceTarget;
import chameleon.core.reference.SimpleReference;
import chameleon.core.relation.WeakPartialOrder;
import chameleon.oo.expression.Expression;
import chameleon.oo.expression.MethodInvocation;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.member.DeclarationWithParametersSignature;
import chameleon.oo.member.MoreSpecificTypesOrder;
import chameleon.oo.type.Type;
import chameleon.support.member.simplename.method.NormalMethod;

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
	protected SubobjectConstructorCall cloneInvocation(CrossReferenceTarget target) {
		SubobjectConstructorCall result = new SubobjectConstructorCall((SimpleReference<ComponentRelation>)getTarget().clone(), new ArrayList<Expression>());
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
