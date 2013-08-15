package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
import be.kuleuven.cs.distrinet.chameleon.core.element.ElementImpl;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LocalLookupContext;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectionResult;
import be.kuleuven.cs.distrinet.chameleon.core.scope.Scope;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;
import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.DeclarationWithType;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;
import be.kuleuven.cs.distrinet.chameleon.util.Util;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

public class ComponentRelationSet extends ElementImpl implements DeclarationWithType{

	
	public ComponentRelationSet(List<DeclarationWithType> relations, FormalComponentParameter formal) {
		_relations = new ArrayList<DeclarationWithType>(relations);
		setSignature(Util.clone(formal.signature()));
		_formal = formal;
	}
	
	public FormalComponentParameter formalParameter() {
		return _formal;
	}
	
	private FormalComponentParameter _formal;
	
	public List<DeclarationWithType> relations() {
		return new ArrayList<DeclarationWithType>(_relations);
	}
	
	private List<DeclarationWithType> _relations;
	
	@Override
	public ComponentRelationSet cloneSelf() {
		return new ComponentRelationSet(relations(),formalParameter());
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	public LocalLookupContext<?> targetContext() throws LookupException {
		return declarationType().targetContext();
	}

	public SimpleNameSignature signature() {
		return _signature.getOtherEnd();
	}
	
	@Override
	public String name() {
		return signature().name();
	}

	private Single<SimpleNameSignature> _signature = new Single<SimpleNameSignature>(this); 

	public void setSignature(Signature signature) {
		if(signature instanceof SimpleNameSignature) {
			set(_signature,(SimpleNameSignature) signature);
		} else {
			throw new ChameleonProgrammerException("Not a valid signature type for a component parameter set: "+ signature == null ? "" : signature.getClass().getName());
		}
	}

	public void setName(String name) {
		setSignature(new SimpleNameSignature(name));
	}

	public Declaration selectionDeclaration() throws LookupException {
		return this;
	}

	public ComponentRelationSet actualDeclaration() throws LookupException {
		return this;
	}

	public Declaration declarator() {
		return this;
	}

	public Scope scope() throws ModelException {
		return nearestAncestor(Type.class).scope();
	}

	public Type declarationType() throws LookupException {
		return formalParameter().declarationType();
	}

	@Override
	public boolean complete() throws LookupException {
		return true;
	}
	
	@Override
	public Declaration finalDeclaration() {
		return this;
	}
	
	@Override
	public Declaration template() {
		return finalDeclaration();
	}

	@Override
	public SelectionResult updatedTo(Declaration declaration) {
		return declaration;
	}

}
