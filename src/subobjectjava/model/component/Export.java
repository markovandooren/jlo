package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rejuse.association.OrderedMultiAssociation;

import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.TypeElementImpl;

public class Export extends TypeElementImpl<Export> {

	private OrderedMultiAssociation<Export, RenamingClause> _signature = new OrderedMultiAssociation<Export, RenamingClause>(this);
	
	public void add(RenamingClause clause) {
		setAsParent(_signature,clause);
	}
	
	public void addAll(Collection<? extends RenamingClause> elements) {
		for(RenamingClause element:elements) {
			add(element);
		}
	}

	public List<Member> processedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
			for(ConfigurationClause clause: clauses()) {
				List<Member> renamedMembers = clause.introducedMembers();
				result.addAll(renamedMembers);
			}
		return result;
	}
	
	public void remove(RenamingClause clause) {
		if(clause != null) {
			_signature.remove(clause.parentLink());
		}
	}

	public List<RenamingClause> clauses() {
		return _signature.getOtherEnds();
	}

	/**
	 * A renaming member does not introduce members into its directly enclosing class, which is the subobject type.
	 */
	@Override
	public List<? extends Member> getIntroducedMembers() throws LookupException {
		return new ArrayList<Member>();
	}

	@Override
	public Export clone() {
		Export result = new Export();
		for(RenamingClause clause: clauses()) {
			result.add(clause.clone());
		}
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}
	
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		for(ConfigurationClause clause: clauses()) {
			result.addAll(clause.membersDirectlyOverriddenBy(selector));
		}
		return result;
	}

	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		for(ConfigurationClause clause: clauses()) {
			result.addAll(clause.membersDirectlyAliasedBy(selector));
		}
		return result;
	}

	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = new ArrayList<D>();
		for(ConfigurationClause clause: clauses()) {
			result.addAll(clause.membersDirectlyAliasing(selector));
		}
		return result;
	}

}
