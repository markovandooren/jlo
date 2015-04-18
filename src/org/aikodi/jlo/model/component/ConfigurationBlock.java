package org.aikodi.jlo.model.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aikodi.chameleon.core.element.ElementImpl;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.validation.Valid;
import org.aikodi.chameleon.core.validation.Verification;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.member.MemberRelationSelector;
import org.aikodi.chameleon.util.association.Multi;

public class ConfigurationBlock extends ElementImpl {

	@Override
protected ConfigurationBlock cloneSelf() {
		return new ConfigurationBlock();
	}

	@Override
	public Verification verifySelf() {
		return Valid.create();
	}

	public void addAll(Collection<? extends ConfigurationClause> elements) {
		for(ConfigurationClause element:elements) {
			add(element);
		}
	}
	
	private Multi<ConfigurationClause> _elements = new Multi<ConfigurationClause>(this);

	public void add(ConfigurationClause element) {
	  add(_elements,element);
	}
	
	public void remove(ConfigurationClause element) {
	  remove(_elements,element);
	}
	
	public List<ConfigurationClause> clauses() throws LookupException {
		//FIXME implement furtherbinding by including the clauses of overriden component relations.
		List<ConfigurationClause> result = localClauses();
		return result;
	}

	public List<ConfigurationClause> localClauses() {
		return _elements.getOtherEnds();
	}

	public List<Member> processedMembers() throws LookupException {
		List<Member> result = new ArrayList<Member>();
			for(ConfigurationClause clause: clauses()) {
				List<Member> renamedMembers = clause.introducedMembers();
				result.addAll(renamedMembers);
			}
		return result;
	}

//	public QualifiedName oldNameFor(Signature signature) throws LookupException {
//		QualifiedName result = null;
//		for(ConfigurationClause clause: clauses()) {
//			result = clause.oldNameFor(signature);
//			if(result != null) {
//				break;
//			}
//		}
//		return result;
//	}
	
//	public <D extends Declaration> Pair<Set<D>,Set<D>> selected(DeclarationSelector<D> selector, Type type) throws LookupException {
//		Pair<Set<D>,Set<D>> result = new Pair<Set<D>,Set<D>>(new HashSet<D>(), new HashSet<D>());
//		for(ConfigurationClause clause: clauses()) {
//			Pair<Set<D>,Set<D>> pair = clause.selected(selector, type);
//			result.first().addAll(pair.first());
//			result.second().addAll(pair.second());
//		}
//		return result;
//	}
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
