package org.aikodi.jlo.translate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.plugin.build.BuildProgressHelper;
import org.aikodi.chameleon.support.translate.IncrementalTranslator;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.language.JLo;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;

public class IncrementalJavaTranslator extends IncrementalTranslator<JLo, Java7> {

	public IncrementalJavaTranslator(View source, View target) {
		super(source, target);
		_translator = new JavaTranslator();
	}
	
	private JavaTranslator _translator;
	
	public AbstractTranslator basicTranslator() {
		return _translator;
	}
	
 /*@
   @ public behavior
   @
   @ pre compilationUnit != null;
   @
   @ post \result != null;
   @ post \fresh(\result);
   @*/
	public Collection<Document> build(Document source, BuildProgressHelper buildProgressHelper) throws BuildException {
		//initTargetLanguage();
		try {
			List<Document> result = translate(source,targetDocument(source));
			Document ifaceCU = (result.size() > 1 ? result.get(1) : null); 
			store(source, ifaceCU,_interfaceMap);
			if(buildProgressHelper != null) {
				buildProgressHelper.addWorked(1);
			}
			return result;
		} catch (LookupException e) {
			throw new BuildException(e);
		} catch (ModelException e) {
			throw new BuildException(e);
		}
	}

	public List<Document> translate(Document source, Document implementationCompilationUnit) throws LookupException, ModelException {
		return _translator.translate(source, implementationCompilationUnit);
	}
  
	private Map<Document,Document> _interfaceMap = new HashMap<Document,Document>();
}
