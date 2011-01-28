package subobjectjava.model.component;



import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.Stub;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.oo.type.Type;
import chameleon.util.Util;

public class RenamingClause extends AbstractClause<RenamingClause> {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public List<Member> introducedMembers() throws LookupException {
		//Type type = nearestAncestor(ComponentRelation.class).componentType();
//		if(type.nearestAncestor(ComponentRelation.class) == nearestAncestor(ComponentRelation.class)) {
//			System.out.println("debug");
//		}
		return Util.createNonNullList(introducedMember());
	}
	
	public Member introducedMember() throws LookupException {
		Member member = (Member) oldDeclaration();
		Member result = null;
		if(member != null) {
			result = member.clone();
			result.setOrigin(member);
			result.setName(newSignature().name());
			
			Stub redirector = new ComponentStub(nearestAncestor(ComponentRelation.class), result);
			redirector.setUniParent(nearestAncestor(Type.class));

		}
		return result;
	}

	@Override
	public RenamingClause clone() {
		return new RenamingClause(newSignature().clone(), oldFqn().clone());
	}

	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		if(selector.selects(introducedMember())) {
			D member = selector.declaration();
			if(! member.ancestors().contains(nearestAncestor(Type.class))) {
				result.add((D)oldDeclaration());
			} 
		}
		return result;
	}

}
