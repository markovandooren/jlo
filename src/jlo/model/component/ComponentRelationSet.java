package jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LocalLookupStrategy;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.scope.Scope;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.oo.type.DeclarationWithType;
import chameleon.oo.type.Type;

public class ComponentRelationSet extends NamespaceElementImpl<ComponentRelationSet> implements DeclarationWithType<ComponentRelationSet, SimpleNameSignature>{

	
	public ComponentRelationSet(List<DeclarationWithType> relations, FormalComponentParameter formal) {
		_relations = new ArrayList<DeclarationWithType>(relations);
		setSignature(formal.signature().clone());
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
	
	public List<? extends Element> children() {
		return new ArrayList<Element>();
	}

	@Override
	public ComponentRelationSet clone() {
		return new ComponentRelationSet(relations(),formalParameter());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public LocalLookupStrategy<?> targetContext() throws LookupException {
		return declarationType().targetContext();
	}

	public SimpleNameSignature signature() {
		return _signature.getOtherEnd();
	}
	
	private SingleAssociation<ComponentRelationSet, SimpleNameSignature> _signature = new SingleAssociation<ComponentRelationSet, SimpleNameSignature>(this); 

	public void setSignature(Signature signature) {
		if(signature instanceof SimpleNameSignature) {
			setAsParent(_signature,(SimpleNameSignature) signature);
		} else {
			throw new ChameleonProgrammerException("Not a valid signature type for a component parameter set: "+ signature == null ? "" : signature.getClass().getName());
		}
	}

	public void setName(String name) {
		setSignature(new SimpleNameSignature(name));
	}

	public Declaration<?, ?> selectionDeclaration() throws LookupException {
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

}
