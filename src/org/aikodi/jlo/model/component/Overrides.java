package org.aikodi.jlo.model.component;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.QualifiedName;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.lookup.DeclarationCollector;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.NameSelector;
import org.aikodi.chameleon.core.modifier.ElementWithModifiersImpl;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeElement;
import org.aikodi.chameleon.util.association.Single;

public class Overrides extends ElementWithModifiersImpl implements TypeElement {

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
	protected Overrides cloneSelf() {
		return new Overrides(null,null);
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	public Declaration oldDeclaration() throws LookupException {
		Declaration container = nearestAncestor(Type.class);
		List<Signature> signatures = oldFQN().signatures();
		Declaration result = null;
		int size = signatures.size();
		for(int i = 0; i< size; i++) {
			final Signature sig = signatures.get(i);
			DeclarationSelector<Declaration> selector = new NameSelector<Declaration>(Declaration.class) {
				@Override
				protected boolean correctSignature(Declaration declaration)
						throws LookupException {
					return declaration.signature().sameAs(sig);
				}
				public String name() {
					return sig.name();
				}
			};
			if(i < size - 1) {
				DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			  container.targetContext().lookUp(collector);
			  container = collector.result();
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
