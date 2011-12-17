package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.member.Member;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeElementImpl;

public class Overrides extends TypeElementImpl<Overrides> {

	public Overrides(Signature newSignature, QualifiedName oldFqn) {
		setNewSignature(newSignature);
		setOldFqn(oldFqn);
	}
	
	public void setNewSignature(Signature signature) {
	  setAsParent(_signature, signature);
	}

	private SingleAssociation<Overrides, Signature> _signature = new SingleAssociation<Overrides, Signature>(this);

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

	private SingleAssociation<Overrides, QualifiedName> _fqn = new SingleAssociation<Overrides, QualifiedName>(this);

	public QualifiedName<?> oldFQN() {
	  return _fqn.getOtherEnd();
	}
	
	public void setOldFqn(QualifiedName fqn) {
	  setAsParent(_fqn, fqn);
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
			container = (TargetDeclaration) container.targetContext().lookUp(selector);
			} else {// i = size - 1, after which the iteration stops.
				result = container.targetContext().lookUp(selector);
			}
		}
		if(result != null) {
		  return result;
		} else {
			throw new LookupException("The old declaration of "+ newSignature().name()+" cannot be found.");
		}
	}

}
