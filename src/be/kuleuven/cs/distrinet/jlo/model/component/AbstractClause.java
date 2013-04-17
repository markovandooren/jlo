package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.Declaration;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.QualifiedName;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.TargetDeclaration;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationCollector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.DeclarationSelector;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.SelectorWithoutOrder;
import be.kuleuven.cs.distrinet.chameleon.core.validation.BasicProblem;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Valid;
import be.kuleuven.cs.distrinet.chameleon.core.validation.Verification;
import be.kuleuven.cs.distrinet.chameleon.util.association.Single;

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
		TargetDeclaration container = nearestAncestor(ComponentRelation.class).componentType();
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
			DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			if(i < size - 1) {
				container.targetContext().lookUp(collector);
				container = (TargetDeclaration) collector.result();
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
		TargetDeclaration container = nearestAncestor(ComponentRelation.class).componentType();
		for(int i = 1; i<= size; i++) {
			final int x = i;
			SelectorWithoutOrder<Declaration> selector = 
				new SelectorWithoutOrder<Declaration>(Declaration.class) {
					public Signature signature() {
						return poppedName.signatureAt(x);
					}};

			//SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
			//					ref.setUniParent(relation.parent());
			DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
			container.targetContext().lookUp(collector);
			container = (TargetDeclaration) collector.result();//x ref.getElement();
		}
		final Signature lastSignature = qn.lastSignature();
		SelectorWithoutOrder<Declaration> selector = 
			new SelectorWithoutOrder<Declaration>(Declaration.class) {
				public Signature signature() {
					return lastSignature;
				}};

		//				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
		//				ref.setUniParent(relation.parent());
	  DeclarationCollector<Declaration> collector = new DeclarationCollector<Declaration>(selector);
		container.targetContext().lookUp(collector);
		Declaration decl = collector.result();
		return decl;
  }

}
