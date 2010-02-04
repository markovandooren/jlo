package subobjectjava.model.component;

import java.util.List;

import org.rejuse.predicate.UnsafePredicate;

import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.type.Type;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.VerificationResult;

public class OverridesClause extends AbstractClause<OverridesClause> {

	public OverridesClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public Member process(Member member) throws LookupException {
		Member result = null;
//		if(member.signature().sameAs(oldFqn())) {
//			result = member.clone();
//			result.setSignature(newSignature().clone());
//			// reroute lookup from within the clone (result) to the parent of the original member. 
//			LookupRedirector redirector = new LookupRedirector(member.parent());
//			redirector.add(result);
//			redirector.setUniParent(nearestAncestor(Type.class));
//		}
		return result;
	}
	
	

	@Override
	public VerificationResult verifySelf() {
		VerificationResult result = super.verifySelf();
		final Signature newSignature = newSignature();
		if(newSignature != null) {
			List<Member> members = nearestAncestor(Type.class).directlyDeclaredMembers();
			boolean overridden;
			try {
			overridden = new UnsafePredicate<Member, LookupException>() {
				@Override
				public boolean eval(Member object) throws LookupException {
					return object.signature().sameAs(newSignature);
				}
			}.exists(members);
			} catch(LookupException exc) {
				overridden = false; 
			}
			if(! overridden) {
				result = result.and(new BasicProblem(this, "There is no local definition of "+newSignature + "."));
			}
		}
		return result;
	}

	@Override
	public OverridesClause clone() {
		return new OverridesClause(newSignature().clone(), oldFqn().clone());
	}

}
