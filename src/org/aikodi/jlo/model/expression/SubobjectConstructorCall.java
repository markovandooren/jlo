package org.aikodi.jlo.model.expression;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.DeclarationContainer;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.SelectionResult;
import org.aikodi.chameleon.core.reference.NameReference;
import org.aikodi.chameleon.core.relation.WeakPartialOrder;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.member.MoreSpecificTypesOrder;
import org.aikodi.chameleon.oo.member.SignatureWithParameters;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.jlo.model.component.Subobject;

import be.kuleuven.cs.distrinet.rejuse.logic.ternary.Ternary;

public class SubobjectConstructorCall extends MethodInvocation<NormalMethod> {

  public SubobjectConstructorCall(NameReference<Subobject> subobjectTarget) {
  	super(subobjectTarget);
  }

  public SubobjectConstructorCall(NameReference<Subobject> subobjectTarget, List<Expression> arguments) {
  	super(subobjectTarget);
  	addAllArguments(arguments);
  }

  public SubobjectConstructorCall(String subobjectName) {
  	super(new NameReference<Subobject>(subobjectName, Subobject.class));
  }

  public SubobjectConstructorCall(String subobjectName, List<Expression> arguments) {
  	super(new NameReference<Subobject>(subobjectName, Subobject.class));
  	addAllArguments(arguments);
  }
  
  @Override
  public NameReference<Subobject> getTarget() {
  	return (NameReference<Subobject>) super.getTarget();
  }
	
	@Override
	protected SubobjectConstructorCall cloneSelf() {
		SubobjectConstructorCall result = new SubobjectConstructorCall((NameReference)null);
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
	
  public class ConstructorSelector implements DeclarationSelector<NormalMethod> {
    
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
			if(signature instanceof SignatureWithParameters) {
				List<Type> actuals = getActualParameterTypes();
				List<Type> formals = ((SignatureWithParameters)signature).parameterTypes();
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

  }
}