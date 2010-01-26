package subobjectjava.model.component;

import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.namespace.NamespaceElementImpl;

public abstract class ConfigurationClause<E extends ConfigurationClause> extends NamespaceElementImpl<E, ConfigurationBlock> {

	public abstract Member process(Member member) throws LookupException;

}
