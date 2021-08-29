package org.aikodi.jlo.model.subobject;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.QualifiedName;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.util.Util;

public class RenamingClause extends AbstractClause {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public List<Declaration> introducedMembers() throws LookupException {
		return Util.createNonNullList(introducedMember());
	}
	
	@Override
	public String toString() {
		return oldFQN().toString() +" as "+newSignature().toString();
	}
	
	public Declaration introducedMember() throws LookupException {
		Declaration result = null;
		Declaration member = (Declaration) oldDeclaration();
		if(member != null) {
			result = lexical().nearestAncestor(Subobject.class).incorporatedIntoContainerType(member);
			result.setName(newSignature().name());
		}
		return result;
	}

	@Override
	protected RenamingClause cloneSelf() {
		return new RenamingClause(null,null);
	}

//	@Override
//	public <D extends Declaration> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
//		return new ArrayList<D>();
//	}
//
//	@Override
//	public <D extends Declaration> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		if(selector.selects(introducedMember())) {
//			// Incorporating is done by the component type.
//			result.add((D)oldDeclaration());
//		}
//		return result;
//	}
//
//
//	@Override
//	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		if(selector.selects(oldDeclaration())) {
//			result.add((D) introducedMember());
//		}
//		return result;
//	}
	
}
