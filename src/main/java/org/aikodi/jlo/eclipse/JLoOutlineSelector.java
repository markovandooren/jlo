package org.aikodi.jlo.eclipse;

import java.util.List;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.java.eclipse.JavaOutlineSelector;
import org.aikodi.jlo.model.subobject.Subobject;
import org.aikodi.jlo.model.subobject.SubobjectType;

public class JLoOutlineSelector extends JavaOutlineSelector {

	
	
	@Override
	public boolean isAllowed(Element element) throws ModelException {
		return (! (element instanceof SubobjectType)) && super.isAllowed(element);
	}

	@Override
	public List<Element> outlineChildren(Element element) throws ModelException {
		List<Element> result = super.outlineChildren(element);
		if(element instanceof Type) {
			Type type = (Type) element;
			for(Subobject relation: type.directlyDeclaredElements(Subobject.class)) {
				result.addAll(relation.exportedMembers());
			}
		}
		return result;
	}
	
	

}
