package subobjectjava.translate;

import jnome.core.language.Java;
import subobjectjava.model.language.SubobjectJava;
import chameleon.core.namespace.RootNamespace;

public class JavaTranslator {

	public Java translate(SubobjectJava lang) {
		RootNamespace clone = lang.defaultNamespace().clone();
		Java result = new Java();
		result.setDefaultNamespace(clone);
		return result;
	}
}
