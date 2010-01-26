package subobjectjava.model.component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.rejuse.association.OrderedMultiAssociation;

import chameleon.core.element.Element;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;

public class ConfigurationBlock extends NamespaceElementImpl<ConfigurationBlock,Element> {

	@Override
	public ConfigurationBlock clone() {
		ConfigurationBlock result = new ConfigurationBlock();
		for(ConfigurationClause<?> clause:clauses()) {
			result.add(clause.clone());
		}
		return result;
	}

	@Override
	public VerificationResult verifySelf() {
		return Valid.create();
	}

	public List<? extends Element> children() {
		return clauses();
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
	
	public List<ConfigurationClause> clauses() {
		return _elements.getOtherEnds();
	}

	public Collection<? extends Member> processedMembers(List<Member> members) throws LookupException {
		Set<Member> result = new HashSet<Member>();
		for(Member member: members) {
			for(ConfigurationClause clause: clauses()) {
				Member renamedMember = clause.process(member);
				if(renamedMember != null) {
					result.add(renamedMember);
				}
				else {
					defaultProcess(member, result);
				}
			}
		}
		return result;
	}

	protected void defaultProcess(Member member, Collection<Member> result) {
		
	}
}
