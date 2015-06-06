package org.aikodi.jlo.model.type;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupContext;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiersImpl;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeFixer;
import org.aikodi.chameleon.oo.type.generics.EqualityConstraint;
import org.aikodi.chameleon.oo.type.generics.FormalTypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeConstraint;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.generics.TypeParameterFixer;
import org.aikodi.chameleon.util.Lists;
import org.aikodi.chameleon.util.association.Single;

public class TypeMemberDeclarator extends ElementWithModifiersImpl implements Member {

	private final class TypeMemberParameterFixer extends
	TypeParameterFixer {
		@Override
		protected List<TypeParameter> parameters() throws LookupException {
			List<TypeMemberDeclarator> members = nearestAncestor(Type.class).members(TypeMemberDeclarator.class);
			List<TypeParameter> result = Lists.create(members.size());
			// FIXME inefficient. Just store an immutable list.
			members.forEach(m -> {
				FormalTypeParameter parameter = m.createParameter();
				parameter.setUniParent(this);
				result.add(parameter);
			});
			return result;
		}

		@Override
		protected Element cloneSelf() {
			return new TypeMemberParameterFixer();
		}
	}

	public TypeMemberDeclarator(String name) {
		setSignature(new SimpleNameSignature(name));
	}

	public TypeMemberDeclarator(SimpleNameSignature signature) {
		setSignature(signature);
	}

	/**
	 * A type variable introduces a type parameter.
	 */
	@Override
	public List<? extends Member> getIntroducedMembers() {
		return Lists.create(this);
		//		TypeParameter param = null;
		//		TypeParameter cloneForStub = param.cloneForStub();
		//		new TypeParameterFixer(){
		//		
		//			@Override
		//			protected Element cloneSelf() {
		//				todo
		//			}
		//		
		//			@Override
		//			protected List<TypeParameter> parameters() {
		//				return nearestAncestor(Type.class).members(TypePar)
		//			}
		//		};
	}

	@Override
	public LookupContext targetContext() throws LookupException {
		return selectionDeclaration().targetContext();
	}

	@Override
	public FormalTypeParameter selectionDeclaration() throws LookupException {
		FormalTypeParameter result = createParameter();
		TypeMemberParameterFixer fixer = new TypeMemberParameterFixer();
		result.setUniParent(fixer);
		return result;
	}
	
	private FormalTypeParameter createParameter() {
		FormalTypeParameter formalTypeParameter = new FormalTypeParameter(signature().name());
		formalTypeParameter.addConstraint(clone(constraint()));
		return formalTypeParameter;
	}

	@Override
	public Signature signature() {
		return _signature.getOtherEnd();
	}

	@Override
	public void setSignature(Signature signature) {
		set(_signature,signature);
	}

	private Single<Signature> _signature = new Single<>(this);

	private Single<TypeConstraint> _constraint = new Single<>(this);

	public TypeConstraint constraint() {
		return _constraint.getOtherEnd();
	}
	
	public void setConstraint(TypeConstraint constraint) {
		set(_constraint, constraint);
	}
	
	@Override
	protected Element cloneSelf() {
		return new TypeMemberDeclarator((SimpleNameSignature)null);
	}

	public boolean contains(TypeMemberDeclarator s, TypeFixer trace) throws LookupException {
		return selectionDeclaration().contains(s.selectionDeclaration(), trace);
	}

}
