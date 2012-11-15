package jlo.model.type;

import java.util.List;

import org.rejuse.logic.ternary.Ternary;

import jlo.model.component.ComponentRelation;
import jlo.model.component.Overrides;
import jnome.core.type.RegularJavaType;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.lookup.LookupException;
import chameleon.core.property.ChameleonProperty;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.member.Member;
import chameleon.oo.member.MemberRelationSelector;
import chameleon.oo.type.Type;
import chameleon.oo.type.inheritance.InheritanceRelation;

public class RegularJLoType extends RegularJavaType {
	
//	private CreationStackTrace _trace;

	public RegularJLoType(SimpleNameSignature sig) {
		super(sig);
//		sig.parentLink().addListener(new AssociationListener() {
//
//			@Override
//			public void notifyElementAdded(Object element) {
//				
//			}
//
//			@Override
//			public void notifyElementRemoved(Object element) {
//				RegularJLoType.this._trace = new CreationStackTrace();
//			}
//
//			@Override
//			public void notifyElementReplaced(Object oldElement, Object newElement) {
//				RegularJLoType.this._trace = new CreationStackTrace();
//			}
//
//		});
	}

	public RegularJLoType(String name) {
		super(name);
	}
	
//	@Override
//	public synchronized Ternary is(ChameleonProperty property) {
//		if(property == language(ObjectOrientedLanguage.class).OVERRIDABLE) {
//			System.out.println("debug");
//		}
//		Ternary result = super.is(property);
//		if(property == language(ObjectOrientedLanguage.class).OVERRIDABLE && result == Ternary.UNKNOWN) {
//			flushLocalCache();
//			System.out.println("debug");
//		}
//		return result;
//	}

	public List<InheritanceRelation> inheritanceRelations() throws LookupException {
		// first take the subtype relations
		List<InheritanceRelation> result = super.inheritanceRelations();
		// then add the component relations
		List<ComponentRelation> components;
			components = body().members(ComponentRelation.class);
			result.addAll(components);
		return result;
	}
	
	protected RegularJLoType cloneThis() {
		return new RegularJLoType(signature().clone());
	}
	
	@Override
	public <D extends Member> List<D> membersDirectlyOverriddenBy(MemberRelationSelector<D> selector) throws LookupException {
		List<D> result = super.membersDirectlyOverriddenBy(selector);
		D declaration = selector.declaration();
		if(declaration.nearestAncestor(Type.class).sameAs(this)) {
			for(Overrides ov: directlyDeclaredElements(Overrides.class)) {
				if(ov.newSignature().sameAs(declaration.signature())) {
					result.add((D)ov.oldDeclaration());
				}
			}
		}
		return result;
	}

}
