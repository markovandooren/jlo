package be.kuleuven.cs.distrinet.jlo.model.component;

import java.util.List;

import be.kuleuven.cs.distrinet.chameleon.core.declaration.QualifiedName;
import be.kuleuven.cs.distrinet.chameleon.core.declaration.Signature;
import be.kuleuven.cs.distrinet.chameleon.core.element.ElementImpl;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.member.MemberRelationSelector;

public abstract class ConfigurationClause extends ElementImpl {

	public abstract ConfigurationClause clone();
	
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
	
	public abstract <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException;

	public abstract <D extends Member> List<D> membersDirectlyAliasedBy(MemberRelationSelector<D> selector) throws LookupException;
	
	public abstract <D extends Member> List<D> membersDirectlyAliasing(MemberRelationSelector<D> selector) throws LookupException;
}
