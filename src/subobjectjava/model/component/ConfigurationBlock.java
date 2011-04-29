package subobjectjava.model.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.rejuse.association.OrderedMultiAssociation;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.element.Element;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;
import chameleon.oo.type.Type;
import chameleon.util.Pair;

public class ConfigurationBlock extends NamespaceElementImpl<ConfigurationBlock> {

	@Override
	public ConfigurationBlock clone() {
		ConfigurationBlock result = new ConfigurationBlock();
		for(ConfigurationClause<?> clause:localClauses()) {
			result.add(clause.clone());
		}
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public List<? extends Element> children() {
		return localClauses();
	}

	public void addAll(Collection<? extends ConfigurationClause> elements) {
		for(ConfigurationClause element:elements) {
			add(element);
		}
	}
	
	private OrderedMultiAssociation<ConfigurationBlock, ConfigurationClause> _elements = new OrderedMultiAssociation<ConfigurationBlock, ConfigurationClause>(this);

	public void add(ConfigurationClause element) {
	  if(element != null) {
	    _elements.add(element.parentLink());
	  }
	}
	
	public void remove(ConfigurationClause element) {
	  if(element != null) {
	    _elements.remove(element.parentLink());
	  }
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
