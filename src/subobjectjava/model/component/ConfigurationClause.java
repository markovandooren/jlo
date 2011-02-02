package subobjectjava.model.component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import chameleon.core.declaration.Declaration;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.Signature;
import chameleon.core.lookup.DeclarationSelector;
import chameleon.core.lookup.LookupException;
import chameleon.core.member.Member;
import chameleon.core.member.MemberRelationSelector;
import chameleon.core.namespace.NamespaceElementImpl;
import chameleon.oo.type.Type;
import chameleon.util.Pair;

public abstract class ConfigurationClause<E extends ConfigurationClause> extends NamespaceElementImpl<E> {

	/**
	 * Return a list of members that are inherited from the given type because of the current
	 * configuration clause.
	 * 
	 * For example, an alias clause will return a list that contains a member that behaves like a
	 * member in the given type, but that is lexically in the enclosing type and has a "new" name (of 
	 * course it is possible that the new member has the same name as the old one).
   *
	 * @throws LookupException
	 */
	public abstract List<Member> introducedMembers() throws LookupException;
	
	/**
	 * Return the inverse mapping of the given signature. It does not matter whether or not the member of the
	 * super class is actually inherited or not. If the configuration clause does not map signatures, or if the
	 * given signature does not match the mapping, then null is returned.
	 */
	public abstract QualifiedName oldNameFor(Signature signature) throws LookupException;
	
//	public abstract <D extends Declaration> Pair<Set<D>,Set<D>> selected(DeclarationSelector<D> selector, Type type) throws LookupException;
	public abstract <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException;

}
