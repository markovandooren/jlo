package subobjectjava.model.component;



import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupRedirector;
import chameleon.core.member.Member;
import chameleon.core.type.Type;

public class RenamingClause extends AbstractClause<RenamingClause> {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public Member process(Member member) throws LookupException {
		Member result = null;
		if(member.signature().sameAs(oldFqn())) {
			result = member.clone();
			result.setOrigin(member);
			result.setSignature(newSignature().clone());
			// reroute lookup from within the clone (result) to the parent of the original member. 
			LookupRedirector redirector = new LookupRedirector(member.parent());
			redirector.add(result);
			redirector.setUniParent(nearestAncestor(Type.class));
		}
		return result;
	}

	@Override
	public RenamingClause clone() {
		return new RenamingClause(newSignature().clone(), oldFqn().clone());
	}

}
