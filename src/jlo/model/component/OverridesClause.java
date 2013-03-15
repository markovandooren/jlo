package jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.distrinet.rejuse.predicate.UnsafePredicate;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.LookupException;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.member.Member;
import chameleon.oo.member.MemberRelationSelector;
import chameleon.oo.type.Type;

public class OverridesClause extends AbstractClause {

	public OverridesClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public List<Member> introducedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
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
		return new OverridesClause(newSignature().clone(), oldFQN().clone());
	}

	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
//		if(selector.selects(newSignature(),oldDeclaration())) {
		if(selector.selects(newSignature(),null)) {
			// Incorporating is done by the component type.
			result.add((D)oldDeclaration());
//			Member oldDeclaration = (Member) oldDeclaration();
//			if(oldDeclaration != null) {
//				Member overridden = oldDeclaration.clone();
//				overridden.setOrigin(oldDeclaration);
//				ComponentRelation componentRelation = nearestAncestor(ComponentRelation.class);
//				Stub redirector = new ComponentStub(componentRelation, overridden);
//				redirector.setUniParent(componentRelation.componentType());
//				result.add((D) overridden);
//			} else {
//				throw new LookupException("Cannot find aliased declaration");
//			}
		}
		return result;
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
		return new ArrayList<D>();
	}

	@Override
	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		return new ArrayList<D>();
	}
}
