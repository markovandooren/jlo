package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.LookupRedirector;
import chameleon.core.member.Member;
import chameleon.core.type.Type;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.util.Util;

public class RenamingClause extends ConfigurationClause<RenamingClause> {

	public RenamingClause(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	@Override
	public Member process(Member member) throws LookupException {
		Member result = null;
		if(member.signature().sameAs(oldFqn())) {
			result = member.clone();
			SingleAssociation originalSignatureCloneLink = result.signature().parentLink();
			originalSignatureCloneLink.getOtherRelation().replace(originalSignatureCloneLink, newSignature().clone().parentLink());
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

	@Override
	public VerificationResult verifySelf() {
		VerificationResult result = Valid.create();
		if(newSignature() == null) {
			result = result.and(new BasicProblem(this, "The renaming clause does not contain a new name."));
		}
		if(oldFqn() == null) {
			result = result.and(new BasicProblem(this, "The renaming clause does not contain an old name."));
		}
		return result;
	}

	public List<? extends Element> children() {
		List<Element> result = new ArrayList<Element>();
		Util.addNonNull(newSignature(), result);
		Util.addNonNull(oldFqn(), result);
		return result;
	}

  public void setNewSignature(Signature signature) {
    setAsParent(_signature, signature);
  }
  
  /**
   * Return the signature of this member.
   */
  public Signature newSignature() {
    return _signature.getOtherEnd();
  }
  
  private SingleAssociation<RenamingClause, Signature> _signature = new SingleAssociation<RenamingClause, Signature>(this);

  public void setOldFqn(QualifiedName fqn) {
    setAsParent(_fqn, fqn);
  }
  
  /**
   * Return the signature of this member.
   */
  public QualifiedName oldFqn() {
    return _fqn.getOtherEnd();
  }
  
  private SingleAssociation<RenamingClause, QualifiedName> _fqn = new SingleAssociation<RenamingClause, QualifiedName>(this);

}
