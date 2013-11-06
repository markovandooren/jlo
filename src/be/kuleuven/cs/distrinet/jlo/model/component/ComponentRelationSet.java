package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.BasicDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.SimpleNameSignature;
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

public class ComponentRelationSet extends BasicDeclaration implements DeclarationWithType{

	
	public ComponentRelationSet(List<DeclarationWithType> relations, FormalComponentParameter formal) {
		super(formal.name());
		_relations = new ArrayList<DeclarationWithType>(relations);
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
