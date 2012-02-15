package jlo.eclipse;

import java.util.List;

import jlo.model.component.ComponentRelation;
import jlo.model.component.ComponentType;
import jnome.eclipse.JavaOutlineSelector;
import chameleon.core.element.Element;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;

public class JLoOutlineSelector extends JavaOutlineSelector {

	
	
	@Override
	public boolean isAllowed(Element<?> element) throws ModelException {
		return (! (element instanceof ComponentType)) && super.isAllowed(element);
	}

	@Override
	public List<Element> outlineChildren(Element<?> element) throws ModelException {
		List<Element> result = super.outlineChildren(element);
		if(element instanceof Type) {
			Type type = (Type) element;
			for(ComponentRelation relation: type.directlyDeclaredElements(ComponentRelation.class)) {
				result.addAll(relation.exportedMembers());
			}
		}
		return result;
	}
	
	

}
