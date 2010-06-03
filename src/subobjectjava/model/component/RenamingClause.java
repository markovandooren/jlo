package subobjectjava.model.component;



import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupRedirector;
import chameleon.core.member.Member;
import chameleon.oo.type.Type;

public class RenamingClause extends AbstractClause<RenamingClause> {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public List<Member> process(Type type) throws LookupException {
		List<Member> resultList = new ArrayList<Member>();
		
		Member member = (Member) oldDeclaration();
		if(member != null) {
			Member resulting = member.clone();
			resulting.setOrigin(member);
			resulting.setSignature(newSignature().clone());
			// reroute lookup from within the clone (result) to the parent of the original member. 
			LookupRedirector redirector = new LookupRedirector(member.parent());
			redirector.add(resulting);
			redirector.setUniParent(nearestAncestor(Type.class));
			resultList.add(resulting);
		}
		
//		for(Member member: nearestAncestor(ComponentRelation.class).componentType().members()) {
//			Member result = null;
//			if(member.signature().sameAs(oldFqn())) {
//				result = member.clone();
//				result.setOrigin(member);
//				result.setSignature(newSignature().clone());
//				// reroute lookup from within the clone (result) to the parent of the original member. 
//				LookupRedirector redirector = new LookupRedirector(member.parent());
//				redirector.add(result);
//				redirector.setUniParent(nearestAncestor(Type.class));
//				resultList.add(result);
//			}
//		}

		return resultList;
	}

	@Override
	public RenamingClause clone() {
		return new RenamingClause(newSignature().clone(), oldFqn().clone());
	}

}
