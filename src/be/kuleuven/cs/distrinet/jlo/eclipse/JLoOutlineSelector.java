package be.kuleuven.cs.distrinet.jlo.eclipse;

import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.component.ComponentRelation;
import be.kuleuven.cs.distrinet.jlo.model.component.ComponentType;
import be.kuleuven.cs.distrinet.jnome.eclipse.JavaOutlineSelector;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.oo.type.Type;

public class JLoOutlineSelector extends JavaOutlineSelector {

	
	
	@Override
	public boolean isAllowed(Element element) throws ModelException {
		return (! (element instanceof ComponentType)) && super.isAllowed(element);
	}

	@Override
	public List<Element> outlineChildren(Element element) throws ModelException {
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
