package org.aikodi.jlo.model.subobject;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.QualifiedName;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.lookup.DeclarationCollector;
import org.aikodi.chameleon.core.lookup.DeclarationSelector;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.lookup.NameSelector;
import org.aikodi.chameleon.core.validation.BasicProblem;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.util.association.Single;

public abstract class AbstractClause extends ConfigurationClause {

	public QualifiedName oldNameFor(Signature signature) throws LookupException {
		QualifiedName result = null;
		if(newSignature().sameAs(signature)) {
			result = oldFQN();
		}
		return result;
	}
	
	@Override
	public Verification verifySelf() {
		Verification result = Valid.create();
		if(newSignature() == null) {
			result = result.and(new BasicProblem(this, "The renaming clause does not contain a new name."));
		}
		QualifiedName fqn = oldFQN();
		if(fqn == null) {
			result = result.and(new BasicProblem(this, "The renaming clause does not contain an old name."));
		}
		try {
			oldDeclaration();
		} catch(LookupException exc) {
			String id = "";
			if(fqn != null) {
				id = fqn.toString();
			}
			result = result.and(new BasicProblem(this, "The exported declaration "+id+"cannot be found."));
		}
		return result;
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

	public void setOldFqn(QualifiedName fqn) {
	  set(_fqn, fqn);
	}
	
	public Declaration oldDeclaration() throws LookupException {
		Declaration container = nearestAncestor(Subobject.class).componentType();
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
			DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			if(i < size - 1) {
				container.targetContext().lookUp(collector);
				container = collector.result();
			} else {// i = size - 1, after which the iteration stops.
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

	/**
	 * Return the signature of this member.
	 */
	public QualifiedName oldFQN() {
	  return _fqn.getOtherEnd();
	}

	public AbstractClause() {
		super();
	}
	
  public Declaration originalDeclaration() throws LookupException {
		QualifiedName qn = oldFQN();
		final QualifiedName poppedName = qn.popped();
		int size = poppedName.length();
		Declaration container = nearestAncestor(Subobject.class).componentType();
		for(int i = 0; i < size; i++) {
			final int x = i;
			NameSelector<Declaration> selector = 
				
				new NameSelector<Declaration>(Declaration.class) {
				@Override
				protected boolean correctSignature(Declaration declaration)
						throws LookupException {
					return declaration.signature().sameAs(poppedName.signatureAt(x));
				}
					public String name() {
						return poppedName.signatureAt(x).name();
					}};

			//SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
			//					ref.setUniParent(relation.parent());
			DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			container.targetContext().lookUp(collector);
			container = collector.result();//x ref.getElement();
		}
		final Signature lastSignature = qn.lastSignature();
		NameSelector<Declaration> selector = 
			new NameSelector<Declaration>(Declaration.class) {
			
				@Override
				protected boolean correctSignature(Declaration declaration)
						throws LookupException {
					return declaration.signature().sameAs(lastSignature);
				}
			
				public String name() {
					return lastSignature.name();
				}};

		//				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
		//				ref.setUniParent(relation.parent());
	  DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
		container.targetContext().lookUp(collector);
		Declaration decl = collector.result();
		return decl;
  }

}
