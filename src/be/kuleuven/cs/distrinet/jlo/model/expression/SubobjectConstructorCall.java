package be.kuleuven.cs.distrinet.jlo.model.expression;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.DeclarationContainer;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectionResult;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.TwoPhaseDeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.reference.SimpleReference;
import be.kuleuven.cs.distrinet.chameleon.core.relation.WeakPartialOrder;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.Expression;
import be.kuleuven.cs.distrinet.chameleon.oo.expression.MethodInvocation;
import be.kuleuven.cs.distrinet.chameleon.oo.language.ObjectOrientedLanguage;
import be.kuleuven.cs.distrinet.chameleon.oo.member.DeclarationWithParametersSignature;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MoreSpecificTypesOrder;
import be.kuleuven.cs.distrinet.chameleon.oo.method.Method;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.support.member.simplename.method.NormalMethod;
import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;

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
	
  public class ConstructorSelector extends DeclarationSelector<NormalMethod> {
    
  	//must create method selection result.
  	
    protected class SubobjectConstructorSelectionResult implements SelectionResult {
			private final Declaration _declaration;

			protected SubobjectConstructorSelectionResult(Declaration declaration) {
				this._declaration = declaration;
			}

			@Override
			public Declaration finalDeclaration() {
				return _declaration;
			}

			@Override
			public SelectionResult updatedTo(Declaration declaration) {
				return new SubobjectConstructorSelectionResult(declaration);
			}

			@Override
			public Declaration template() {
				return finalDeclaration();
			}
		}

		public boolean selectedRegardlessOfName(NormalMethod declaration) throws LookupException {
    	return declaration.is(language(ObjectOrientedLanguage.class).CONSTRUCTOR)==Ternary.TRUE;
    }
    
    public List<? extends SelectionResult> selection(List<? extends Declaration> declarators) throws LookupException {
    	List<SelectionResult> tmp = new ArrayList<SelectionResult>();
    	for(Declaration decl: declarators) {
    		SelectionResult e = selection(decl);
    		if(e != null) {
    			tmp.add(e);
    		}
    	}
    	applyOrder(tmp);
      return tmp;
    }
    
    protected SelectionResult selection(Declaration declarator) throws LookupException {
    	// We first perform the checks on the selectionDeclaration, since a signature check may be
    	// very expensive.
    	SelectionResult result = null;
    	if(selectedBasedOnName(declarator.signature())) {
    		Declaration selectionDeclaration = declarator.selectionDeclaration();
    		if(canSelect(selectionDeclaration.getClass())) {
    			if(selectedRegardlessOfName((NormalMethod)selectionDeclaration)) {
    				result = createSelectionResult(selectionDeclaration.actualDeclaration());
    			}
    		}
    	}
    	return result;
    }
    
    protected SelectionResult createSelectionResult(final Declaration declaration) {
    	return new SubobjectConstructorSelectionResult(declaration);
    }

		private boolean selectedBasedOnName(Signature signature) throws LookupException {
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

    public WeakPartialOrder<SelectionResult> order() {
      return new WeakPartialOrder<SelectionResult>() {
        @Override
        public boolean contains(SelectionResult first, SelectionResult second)
            throws LookupException {
          return MoreSpecificTypesOrder.create().contains(((Method)first.finalDeclaration()).header().formalParameterTypes(), ((Method)second.finalDeclaration()).header().formalParameterTypes());
        }
      };
    }

		@Override
		public String selectionName(DeclarationContainer container) throws LookupException {
			// FIXME: this is horribly inefficient, but otherwise we must add more redundancy
			//        in the source code.
			return getTarget().getElement().componentType().signature().name();
		}

		protected void applyOrder(List<SelectionResult> tmp) throws LookupException {
			order().removeBiggerElements((List)tmp);
		}

		@Override
		public boolean canSelect(Class<? extends Declaration> type) {
			return NormalMethod.class.isAssignableFrom(type);
		}

		@Override
		public List<? extends SelectionResult> declarators(List<? extends Declaration> selectionCandidates) throws LookupException {
			List<SelectionResult> result = new ArrayList<>();
			for(SelectionResult r: selection(selectionCandidates)) {
				result.add(((SubobjectConstructorSelectionResult)r).template().declarator());
			}
			return result;
		}
  }
}