package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.member.MemberRelationSelector;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.jlo.model.component.Overrides;
import org.aikodi.jlo.model.component.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.type.RegularJavaType;

import com.google.common.collect.ImmutableList;

public class RegularJLoType extends RegularJavaType implements JLoType {
	
//	private CreationStackTrace _trace;

	public RegularJLoType(String name) {
		super(name);
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

	@Override
	protected RegularJLoType cloneSelf() {
		//FIXME use lazy instantiation of parameter blocks to avoid this hack
		//FIXME do something about the need to clone the signature.
		RegularJLoType regularJLoType = new RegularJLoType(name());
		regularJLoType.parameterBlock(TypeParameter.class).disconnect();
		return regularJLoType;
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
