package subobjectjava.model.component;

import java.util.Collection;
import java.util.List;

import org.rejuse.association.OrderedMultiAssociation;

import chameleon.core.element.Element;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.type.ClassBody;
import chameleon.core.type.TypeElement;
import chameleon.core.validation.Valid;
import chameleon.core.validation.VerificationResult;

public class ConfigurationBlock extends NamespaceElementImpl<ConfigurationBlock,Element> {

	@Override
	public ConfigurationBlock clone() {
		ConfigurationBlock result = new ConfigurationBlock();
		for(ConfigurationClause clause:clauses()) {
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

}
