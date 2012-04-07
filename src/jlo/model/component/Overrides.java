package jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.lookup.DeclarationCollector;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.member.Member;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElementImpl;
import chameleon.util.association.Single;

public class Overrides extends TypeElementImpl {

	public Overrides(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	public void setNewSignature(Signature signature) {
	  set(_signature, signature);
	}

	private Single<Signature> _signature = new Single<Signature>(this);

	/**
	 * Return the signature of this member.
	 */
	public Signature newSignature() {
	  Signature result = _signature.getOtherEnd();
	  QualifiedName oldFQN = oldFQN();
		if(result == null && oldFQN instanceof Signature) {
	  	result = (Signature) oldFQN;
	  }
	  return result;
	}

	private Single<QualifiedName> _fqn = new Single<QualifiedName>(this);

	public QualifiedName oldFQN() {
	  return _fqn.getOtherEnd();
	}
	
	public void setOldFqn(QualifiedName fqn) {
	  set(_fqn, fqn);
	}

	@Override
	public List<? extends Member> getIntroducedMembers() throws LookupException {
		return new ArrayList<Member>();
	}

	@Override
	public Overrides clone() {
		return new Overrides(newSignature().clone(), oldFQN().clone());
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public Declaration oldDeclaration() throws LookupException {
		TargetDeclaration container = nearestAncestor(Type.class);
		List<Signature> signatures = oldFQN().signatures();
		Declaration result = null;
		int size = signatures.size();
		for(int i = 0; i< size; i++) {
			final Signature sig = signatures.get(i);
			DeclarationSelector<Declaration> selector = new SelectorWithoutOrder<Declaration>(Declaration.class) {
				public Signature signature() {
					return sig;
				}
			};
			if(i < size - 1) {
				DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			  container.targetContext().lookUp(collector);
			  container = (TargetDeclaration) collector.result();
			} else {// i = size - 1, after which the iteration stops.
				DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
				container.targetContext().lookUp(collector);
				result = collector.result();
			}
		}
		if(result != null) {
		  return result;
		} else {
			throw new LookupException("The old declaration of "+ newSignature().name()+" cannot be found.");
		}
	}

}
