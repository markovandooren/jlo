package subobjectjava.model.expression;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.logic.ternary.Ternary;

import subobjectjava.model.component.ComponentRelation;
import chameleon.core.declaration.Signature;
import chameleon.core.expression.ActualArgument;
import chameleon.core.expression.CrossReferenceTarget;
import chameleon.core.expression.Invocation;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.expression.NamedTarget;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.method.MethodSignature;
import chameleon.core.relation.WeakPartialOrder;
import chameleon.core.type.Type;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.support.member.MoreSpecificTypesOrder;
import chameleon.support.member.simplename.method.NormalMethod;

public class SubobjectConstructorCall extends Invocation<SubobjectConstructorCall, NormalMethod> {

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget) {
  	super(subobjectTarget);
  }

  public SubobjectConstructorCall(CrossReferenceTarget<ComponentRelation> subobjectTarget, List<ActualArgument> arguments) {
  	super(subobjectTarget);
  	addAllArguments(arguments);
  }

  public SubobjectConstructorCall(String subobjectName) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  }

  public SubobjectConstructorCall(String subobjectName, List<ActualArgument> arguments) {
  	super(new CrossReferenceTarget<ComponentRelation>(subobjectName, ComponentRelation.class));
  	addAllArguments(arguments);
  }
  
  @Override
  public CrossReferenceTarget<ComponentRelation> getTarget() {
  	return (CrossReferenceTarget<ComponentRelation>) super.getTarget();
  }
	
	@Override
	protected SubobjectConstructorCall cloneInvocation(InvocationTarget target) {
		List<ActualArgument> arguments = new ArrayList<ActualArgument>();
		return new SubobjectConstructorCall((CrossReferenceTarget<ComponentRelation>)getTarget().clone(), arguments);
	}

	@Override
	public DeclarationSelector<NormalMethod> selector() {
		return new ConstructorSelector();
	}

	@Override
	protected Type actualType() throws LookupException {
		return language(ObjectOrientedLanguage.class).voidType();
	}
	
  public class ConstructorSelector extends DeclarationSelector<NormalMethod> {
    
    public boolean selectedRegardlessOfSignature(NormalMethod declaration) throws LookupException {
    	return declaration.is(language(ObjectOrientedLanguage.class).CONSTRUCTOR)==Ternary.TRUE;
    }
    
		@Override
		public boolean selected(Signature signature) throws LookupException {
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



  }


}
