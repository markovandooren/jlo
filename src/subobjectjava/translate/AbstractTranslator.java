package subobjectjava.translate;

import jnome.core.language.Java;

import org.rejuse.property.Property;

import chameleon.core.modifier.ElementWithModifiers;
import chameleon.core.modifier.Modifier;
import chameleon.exception.ModelException;
import chameleon.support.modifier.Public;

public class AbstractTranslator {

	public AbstractTranslator() {
	}

	
	public final static String SHADOW = "_subobject_";
	
	public final static String IMPL = "_implementation";

	protected void makePublic(ElementWithModifiers<?> element) throws ModelException {
		Java language = element.language(Java.class);
		Property access = element.property(language.SCOPE_MUTEX);
		if(access != language.PUBLIC) {
			for(Modifier mod: element.modifiers(language.SCOPE_MUTEX)) {
				mod.disconnect();
			}
			element.addModifier(new Public());
		}
	}

	protected String interfaceName(String name) {
		if(! name.endsWith(IMPL)) {
			throw new IllegalArgumentException();
		}
		//return name.substring(0, name.length()-IMPL.length());
		return name.replaceAll(IMPL,"");
	}


}