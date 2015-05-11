package org.aikodi.jlo.model.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.language.ObjectOrientedLanguage;
import org.aikodi.chameleon.oo.member.DeclarationComparator;
import org.aikodi.chameleon.oo.member.HidesRelation;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.member.MemberRelationSelector;
import org.aikodi.chameleon.oo.member.OverridesRelation;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.Parameter;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.util.Util;

import be.kuleuven.cs.distrinet.rejuse.property.Property;
import be.kuleuven.cs.distrinet.rejuse.property.PropertyMutex;

@Deprecated
public abstract class ComponentParameter extends Parameter implements DeclarationWithType, Member {

	public ComponentParameter(String name) {
		super(name);
	}

	@Override
	public List<? extends Member> getIntroducedMembers() {
		return Util.createNonNullList(this);
	}

	@Override
	public List<? extends Member> declaredMembers() {
		return getIntroducedMembers();
	}

	@Override
	public List<Modifier> modifiers() {
		return new ArrayList<Modifier>();
	}

	@Override
	public void addModifier(Modifier modifier) {
		//throw new ChameleonProgrammerException();
	}

	@Override
	public void removeModifier(Modifier modifier) {
		throw new ChameleonProgrammerException();
	}

	@Override
	public void addModifiers(List<Modifier> modifiers) {
		if(! modifiers.isEmpty()) {throw new ChameleonProgrammerException();};
	}

  public MemberRelationSelector<? extends Member> overridesSelector() {
		return new MemberRelationSelector<Member>(Member.class,this,_overridesRelation);
  }
  
  public final boolean overrides(Member other) throws LookupException {
  	return overridesRelation().contains(this,other);
  }

  
  public OverridesRelation<? extends Member> overridesRelation() {
  	return _overridesRelation;
  }
  
  private static OverridesRelation<Member> _overridesRelation = new OverridesRelation<Member>(Member.class);
  public MemberRelationSelector<? extends Member> aliasSelector() {
		return new MemberRelationSelector<Member>(Member.class,this,_aliasSelector);
  }
	
  private static DeclarationComparator<Member> _aliasSelector = new DeclarationComparator<Member>(Member.class);

  public final boolean hides(Member other) throws LookupException {
	  return ((HidesRelation)hidesRelation()).contains(this,other);
  }
  public HidesRelation<? extends Member> hidesRelation() {
		return _hidesSelector;
  }
  
  private static HidesRelation<Member> _hidesSelector = new HidesRelation<Member>(Member.class);


  public final boolean canImplement(Member other) throws LookupException {
  	return language(ObjectOrientedLanguage.class).implementsRelation().contains(this,other);
  }

  public List<? extends Member> directlyOverriddenMembers() throws LookupException {
    return nearestAncestor(Type.class).membersDirectlyOverriddenBy(overridesSelector());
  }
  
  public List<? extends Member> directlyAliasedMembers() throws LookupException {
    return nearestAncestor(Type.class).membersDirectlyAliasedBy(aliasSelector());
  }
  
  public List<? extends Member> directlyAliasingMembers() throws LookupException {
    return nearestAncestor(Type.class).membersDirectlyAliasing(aliasSelector());
  }
  
  public Set<? extends Member> overriddenMembers() throws LookupException {
  	List<Member> todo = (List<Member>) directlyOverriddenMembers();
  	Map<Type,List<Member>> visitedTypes = new HashMap<Type,List<Member>>();
  	while(! todo.isEmpty()) {
  		Member m = todo.get(0);
  		todo.remove(0);
  		Type containingType = m.nearestAncestor(Type.class);
		if(! visitedTypes.containsKey(containingType)) {
			visitedTypes.put(containingType, new ArrayList<Member>());
		}
		List<Member> done = visitedTypes.get(containingType);
		boolean contains = false;
		for(Member member:done) {
			if(member.signature().sameAs(m.signature())) {
				contains = true;
				break;
			}
		}
		if(! contains) {
			done.add(m);
			todo.addAll(m.directlyOverriddenMembers());
			todo.addAll(m.aliasedMembers());
			todo.addAll(m.aliasingMembers());
		}
  	}
  	Set<Member> result = new HashSet<Member>();
  	for(List<Member> members: visitedTypes.values()) {
  		result.addAll(members);
  	}
  	return result;
  }
  
  public Set<? extends Member> aliasedMembers() throws LookupException {
	  List<Member> todo = (List<Member>) directlyAliasedMembers();
	  Set<Member> result = new HashSet<Member>();
	  while(! todo.isEmpty()) {
		  Member m = todo.get(0);
		  System.out.println(m.nearestAncestor(Type.class).getFullyQualifiedName()+"."+m.name());
		  todo.remove(0);
		  if(result.add(m)) {
			  todo.addAll(m.directlyAliasedMembers());
		  }
	  }
	  return result;
  }

  public Set<? extends Member> aliasingMembers() throws LookupException {
	  List<Member> todo = (List<Member>) directlyAliasingMembers();
	  Set<Member> result = new HashSet<Member>();
	  while(! todo.isEmpty()) {
		  Member m = todo.get(0);
		  todo.remove(0);
		  if(result.add(m)) {
			  todo.addAll(m.directlyAliasingMembers());
		  }
	  }
	  return result;
  }


}
