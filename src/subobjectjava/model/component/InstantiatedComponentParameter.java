package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupStrategy;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ModelException;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.DerivedType;
import chameleon.oo.type.Parameter;
import chameleon.oo.type.ParameterBlock;
import chameleon.oo.type.Type;


public class InstantiatedComponentParameter<E extends InstantiatedComponentParameter<E>> extends ComponentParameter<E> implements AbstractInstantiatedComponentParameter<E> {

	
	public InstantiatedComponentParameter(SimpleNameSignature sig, ActualComponentArgument argument) {
		super(sig);
		_argument = argument;
	}

	public TargetDeclaration actualDeclaration() throws LookupException {
		return declaration();
	}

	private ActualComponentArgument _argument;
	
	public ActualComponentArgument argument() {
		return _argument;
	}
	
	public List<? extends Element> children() {
		return new ArrayList<Element>();
	}
	
	public DeclarationWithType declaration() throws LookupException {
		return argument().declaration();
	}
	
	public FormalComponentParameter formalParameter() throws LookupException {
		Type enclosing = nearestAncestor(DerivedType.class);
		if(enclosing == null) {
			throw new LookupException("The instantiated single component parameter is not in a derived type.");
		} else {
			ComponentRelation result = null;
			Type original = (Type) enclosing.origin();
			ParameterBlock<?,?> block = original.parameterBlock(ComponentParameter.class);
			FormalComponentParameter p = null;
			for(Parameter param: block.parameters()) {
				if(param.signature().sameAs(signature())) {
					p = (FormalComponentParameter) param;
				}
			}
			return p;
		}
	}

	public LocalLookupStrategy<?> targetContext() throws LookupException {
		return declaration().targetContext();
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	@Override
	public TargetDeclaration selectionDeclaration() throws LookupException {
		return this;
	}

	@Override
	public E clone() {
		return (E) new InstantiatedComponentParameter(signature().clone(),argument());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public Type declarationType() throws LookupException {
		return formalParameter().declarationType();
	}

	@Override
	public boolean complete() throws LookupException {
		return true;
	}


}
