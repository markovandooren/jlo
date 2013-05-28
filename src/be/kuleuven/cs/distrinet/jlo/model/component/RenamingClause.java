package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.QualifiedName;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MemberRelationSelector;
import be.kuleuven.cs.distrinet.chameleon.util.Util;

public class RenamingClause extends AbstractClause {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public List<Member> introducedMembers() throws LookupException {
		return Util.createNonNullList(introducedMember());
	}
	
	@Override
	public String toString() {
		return oldFQN().toString() +" as "+newSignature().toString();
	}
	
	public Member introducedMember() throws LookupException {
		Member result = null;
		Member member = (Member) oldDeclaration();
		if(member != null) {
			result = nearestAncestor(ComponentRelation.class).incorporatedIntoContainerType(member);
			result.setName(newSignature().name());
		}
		return result;
	}

	@Override
	protected RenamingClause cloneSelf() {
		return new RenamingClause(null,null);
	}

	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		return result;
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		if(selector.selects(introducedMember())) {
			// Incorporating is done by the component type.
			result.add((D)oldDeclaration());
		}
		return result;
	}


	@Override
	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		if(selector.selects(oldDeclaration())) {
			result.add((D) introducedMember());
		}
		return result;
	}
	
}
