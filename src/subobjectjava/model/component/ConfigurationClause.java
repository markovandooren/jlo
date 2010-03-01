package subobjectjava.model.component;

import java.util.List;

import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.core.type.Type;

public abstract class ConfigurationClause<E extends ConfigurationClause> extends NamespaceElementImpl<E, ConfigurationBlock> {

	public abstract List<Member> process(Type type) throws LookupException;

}
