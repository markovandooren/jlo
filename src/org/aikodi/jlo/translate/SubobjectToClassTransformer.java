package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.member.SignatureWithParameters;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeElement;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.support.expression.ThisLiteral;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.util.Util;
import org.aikodi.jlo.model.component.ConfigurationBlock;
import org.aikodi.jlo.model.component.ConfigurationClause;
import org.aikodi.jlo.model.component.Export;
import org.aikodi.jlo.model.component.RenamingClause;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.component.SubobjectType;
import org.aikodi.jlo.model.expression.AbstractTarget;

import be.kuleuven.cs.distrinet.jnome.core.expression.invocation.JavaMethodInvocation;
import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;
import be.kuleuven.cs.distrinet.rejuse.association.SingleAssociation;

public class SubobjectToClassTransformer extends AbstractTranslator {
	
	public SubobjectToClassTransformer(InnerClassCreator innerClassCreator, SubobjectConstructorTransformer subobjectConstructorTransformer) {
		_innerClassCreator = innerClassCreator;
		_subobjectConstructorTransformer = subobjectConstructorTransformer;
	}
	
	private InnerClassCreator _innerClassCreator;
	
	public InnerClassCreator innerClassCreator() {
		return _innerClassCreator;
	}
	
	private SubobjectConstructorTransformer _subobjectConstructorTransformer;
	
	public SubobjectConstructorTransformer subobjectConstructorTransformer() {
		return _subobjectConstructorTransformer;
	}

	public void addInnerClassForSubobject(Type javaType, Subobject relation, Subobject originalRelation) throws ChameleonProgrammerException, ModelException {
		Type innerClass = createInnerClassFor(relation,javaType, originalRelation);
		javaType.flushCache();
		Type componentType = relation.componentType();
		Type originalComponentType = null;
		List<Subobject> originalRelations = null;
		if(originalRelation != null) {
			originalComponentType = originalRelation.componentType();
			originalRelations = originalComponentType.directlyDeclaredElements(Subobject.class);
		}
		List<Subobject> relations = componentType.directlyDeclaredElements(Subobject.class);
		int size = relations.size();
		for(int i = 0; i < size; i++) {
			Subobject nestedRelation = relations.get(i);
			Subobject originalNestedRelation = null;
			if(originalRelations != null) {
				originalNestedRelation = originalRelations.get(i);
			}
			// subst parameters
			Subobject clonedNestedRelation = Util.clone(nestedRelation);
			clonedNestedRelation.setUniParent(nestedRelation.parent());
			try {
			  substituteTypeParameters(clonedNestedRelation);
			} catch(LookupException exc) {
			  substituteTypeParameters(clonedNestedRelation);
				throw exc;
			}
			addInnerClassForSubobject(innerClass, clonedNestedRelation, originalNestedRelation);
		}
		addAliasDelegations(relation, innerClass.nearestAncestor(Type.class),relation.nearestAncestor(Type.class));
	}
	
	private void addAliasDelegations(Subobject relation, Type outer, Type original) throws LookupException {
		List<RenamingClause> clauses = new ArrayList<RenamingClause>();
		ConfigurationBlock block = relation.configurationBlock();
		if(block != null) {
			for(ConfigurationClause clause: block.clauses()) {
				if(clause instanceof RenamingClause) {
					clauses.add((RenamingClause) clause);
				}
			}
		}
		for(Export exp: relation.componentType().directlyDeclaredElements(Export.class)) {
			clauses.addAll(exp.clauses());
		}
		for(RenamingClause renamingClause: clauses) {
			Declaration decl = renamingClause.oldDeclaration();
			if(decl instanceof Method) {
				final Method method = (Method) decl;
				Method alias = createAlias(relation, method, ((SignatureWithParameters)renamingClause.newSignature()).name());
				outer.add(alias);
			}
		}
	}
	
	private Method createAlias(Subobject relation, Method method, String newName) throws LookupException {
		NormalMethod result;
		result = innerMethod(method, newName);
		Block body = new Block();
		result.setImplementation(new RegularImplementation(body));
		MethodInvocation invocation = invocation(result, method.name(),method.language());
		Expression target = new JavaMethodInvocation(getterName(relation), null);
		invocation.setTarget(target);
		substituteTypeParameters(method, result);
		addImplementation(method, body, invocation);
		return result;
	}
	
	/**
	 * 
	 * @param relationBeingTranslated A component relation from either the original class, or one of its nested components.
	 * @param outerJavaType The outer class being generated.
	 * @throws ModelException 
	 */
	private Type createInnerClassFor(Subobject relationBeingTranslated, Type javaType, Subobject original) throws ChameleonProgrammerException, ModelException {
		Type result = innerClassCreator().emptyInnerClassFor(relationBeingTranslated);
		processComponentRelationBody(relationBeingTranslated, result, original);
		javaType.add(result);
		if(PROCESS_NESTED_CONSTRUCTORS) {
			List<InheritanceRelation> inheritanceRelations = result.nonMemberInheritanceRelations();
			int size = inheritanceRelations.size();
			for(int i=1; i< size; i++) {
				inheritanceRelations.get(i).setUniParent(null);
			}
			subobjectConstructorTransformer().replaceSubobjectConstructorCalls(result);
			removeSubobjects(result);
			for(int i=1; i< size; i++) {
				result.addInheritanceRelation(inheritanceRelations.get(i));
			}
		}
		return result;
	}
	
	

	private void removeSubobjects(Type result) {
		for(Subobject relation:result.directlyDeclaredElements(Subobject.class)) {
			relation.disconnect();
		}
	}

	private void processComponentRelationBody(Subobject relation, Type result, Subobject original)
	throws LookupException {
		SubobjectType ctype = relation.componentTypeDeclaration();
		SubobjectType clonedType = ctype.clone();
		clonedType.setUniParent(relation);
		// The outer and root targets are replaced now because they need to be in the subobjects themselves in order
		// to use the target semantics of the Outer call. Otherwise we must encode its semantics in the translator.
		replaceOuterAndRootTargets(clonedType);
		for(TypeElement typeElement:clonedType.body().elements()) {
			if((PROCESS_NESTED_CONSTRUCTORS || ! (typeElement instanceof Subobject)) && (! (typeElement instanceof Export))) {
				result.add(typeElement);
			}
		}
		processOverriddenSubobjects(relation,result,original);
	}
	
	private void processOverriddenSubobjects(Subobject unused, Type result, Subobject original) throws LookupException {
		if(original != null) {
			List<Subobject> todo = (List<Subobject>)original.directlyOverriddenMembers();
			List<Member> members = original.componentType().directlyDeclaredMembers();
			while(! todo.isEmpty()) {
				Subobject overridden = todo.get(0);
				todo.remove(0);
				SubobjectType ctype = overridden.componentTypeDeclaration();
				SubobjectType clonedType = ctype.clone();
				clonedType.setUniParent(overridden);
				// The outer and root targets are replaced now because they need to be in the subobjects themselves in order
				// to use the target semantics of the Outer call. Otherwise we must encode its semantics in the translator.
				replaceOuterAndRootTargets(clonedType);
				for(Member typeElement:clonedType.body().members()) {
					if((PROCESS_NESTED_CONSTRUCTORS || ! (typeElement instanceof Subobject)) && (! (typeElement instanceof Export))) {
						if(! alreadyContains(members,typeElement)) {
							Member toAdd = Util.clone(typeElement);
							toAdd.setUniParent(typeElement.parent());
							expandReferences(toAdd);
							rewriteAllThis(toAdd,unused, original);
							toAdd.setUniParent(null);
							result.add(toAdd);
							members.add(toAdd);
						}
					}
				}
			}
		}
	}
	
	private void rewriteAllThis(Member member, Subobject relation, Subobject original) throws LookupException {
		for(ThisLiteral literal: member.descendants(ThisLiteral.class)) {
			rewriteThis(literal,relation,original);
		}
	}
	
	private void rewriteThis(ThisLiteral literal, Subobject relation, Subobject original) throws LookupException {
		TypeReference tref = literal.getTypeReference();
		if(tref != null) {
			Type t = tref.getType();
//			Type type = original.nearestAncestor(Type.class);
			Type literalAncestor = relation.nearestAncestor(Type.class);
			Type lit = null;
			while(literalAncestor != null && lit == null) {
				if(literalAncestor.subTypeOf(t)) {
					lit = literalAncestor;
				} else {
					literalAncestor = relation.nearestAncestor(Type.class);
				}
			}
			Java7 language = relation.language(Java7.class);
			literal.setTypeReference(language.createTypeReference(lit.signature().name()));
		}
	}
	
  private boolean alreadyContains(List<Member> members, Member member) throws LookupException {
  	for(Member m: members) {
  		if(member.sameSignatureAs(m)) {
  			return true;
  		}
  	}
  	return false;
  }

	private static boolean PROCESS_NESTED_CONSTRUCTORS=true;

	private void replaceOuterAndRootTargets(TypeElement clone) {
		List<AbstractTarget> outers = clone.descendants(AbstractTarget.class);
		for(AbstractTarget o: outers) {
			String name = o.getTargetDeclaration().name();
			SingleAssociation parentLink = o.parentLink();
			ThisLiteral e = new ThisLiteral();
			e.setTypeReference(new BasicJavaTypeReference(name));
			parentLink.getOtherRelation().replace(parentLink, e.parentLink());
		}
	}

}
