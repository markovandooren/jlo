package org.aikodi.jlo.model.subobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.declaration.Declarator;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiersImpl;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.util.association.Multi;

public class Export extends ElementWithModifiersImpl implements Declarator {

	private Multi<RenamingClause> _clauses = new Multi<RenamingClause>(this);
	
	public void add(RenamingClause clause) {
		add(_clauses,clause);
	}
	
	public void addAll(Collection<? extends RenamingClause> elements) {
		for(RenamingClause element:elements) {
			add(element);
		}
	}

	public List<Declaration> processedMembers() throws LookupException {
		List<Declaration> result = new ArrayList<>();
			for(ConfigurationClause clause: clauses()) {
				List<Declaration> renamedMembers = clause.introducedMembers();
				result.addAll(renamedMembers);
			}
		return result;
	}
	
	public void remove(RenamingClause clause) {
		remove(_clauses,clause);
	}

	public List<RenamingClause> clauses() {
		return _clauses.getOtherEnds();
	}

	/**
	 * A renaming member does not introduce members into its directly enclosing class, which is the subobject type.
	 */
	@Override
	public List<Declaration> declaredDeclarations() {
		return new ArrayList<>();
	}

	@Override
	protected Export cloneSelf() {
		return new Export();
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}
	
//	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(ConfigurationClause clause: clauses()) {
//			result.addAll(clause.membersDirectlyOverriddenBy(selector));
//		}
//		return result;
//	}
//
//	public <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(ConfigurationClause clause: clauses()) {
//			result.addAll(clause.membersDirectlyAliasedBy(selector));
//		}
//		return result;
//	}
//
//	public <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException {
//		List<D> result = new ArrayList<D>();
//		for(ConfigurationClause clause: clauses()) {
//			result.addAll(clause.membersDirectlyAliasing(selector));
//		}
//		return result;
//	}

}
