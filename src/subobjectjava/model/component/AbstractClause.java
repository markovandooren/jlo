package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.List;

import org.rejuse.association.SingleAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.lookup.SelectorWithoutOrder;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.validation.BasicProblem;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.util.Util;

public abstract class AbstractClause<E extends AbstractClause> extends ConfigurationClause<E> {

	private SingleAssociation<AbstractClause, Signature> _signature = new SingleAssociation<AbstractClause, Signature>(this);

	public QualifiedName oldNameFor(Signature signature) throws LookupException {
		QualifiedName result = null;
		if(newSignature().sameAs(signature)) {
			result = oldFqn();
		}
		return result;
	}
	
//  public <D extends Declaration> Pair<Set<D>,Set<D>> selected(DeclarationSelector<D> selector, Type type) throws LookupException {
//  	List<D> list = selector.selection(process(type));
//  	Set old = new HashSet();
//  	old.add(oldDeclaration());
//  	Pair<Set<D>,Set<D>> result = new Pair<Set<D>,Set<D>>(new HashSet<D>(list),old);
//  	return result;
//  }
	
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

	private SingleAssociation<AbstractClause, QualifiedName> _fqn = new SingleAssociation<AbstractClause, QualifiedName>(this);

	public void setOldFqn(QualifiedName fqn) {
	  setAsParent(_fqn, fqn);
	}
	
	public Declaration oldDeclaration() throws LookupException {
		TargetDeclaration container = nearestAncestor(ComponentRelation.class).componentType();
		List<Signature> signatures = oldFqn().signatures();
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
			} else {
				result = container.targetContext().lookUp(selector);
			}
		}
		return result;
	}

	/**
	 * Return the signature of this member.
	 */
	public QualifiedName<?> oldFqn() {
	  return _fqn.getOtherEnd();
	}

	public AbstractClause() {
		super();
	}
	
  public Declaration originalDeclaration() throws LookupException {
		QualifiedName qn = oldFqn();
		final QualifiedName poppedName = qn.popped();
		int size = poppedName.length();
		TargetDeclaration container = nearestAncestor(ComponentRelation.class).componentType();
		for(int i = 1; i<= size; i++) {
			final int x = i;
			SelectorWithoutOrder<Declaration> selector = 
				new SelectorWithoutOrder<Declaration>(Declaration.class) {
					public Signature signature() {
						return poppedName.elementAt(x);
					}};

			//SimpleReference<Declaration> ref = new SimpleReference<Declaration>(poppedName, Declaration.class);
			//					ref.setUniParent(relation.parent());
			container = (TargetDeclaration) container.targetContext().lookUp(selector);//x ref.getElement();
		}
		final Signature lastSignature = qn.lastSignature();
		SelectorWithoutOrder<Declaration> selector = 
			new SelectorWithoutOrder<Declaration>(Declaration.class) {
				public Signature signature() {
					return lastSignature;
				}};

		//				SimpleReference<Declaration> ref = new SimpleReference<Declaration>(null, lastSignature.clone(), Declaration.class);
		//				ref.setUniParent(relation.parent());
		Declaration decl = container.targetContext().lookUp(selector);
		return decl;
  }

}