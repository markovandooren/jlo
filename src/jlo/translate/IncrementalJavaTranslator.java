package jlo.translate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jlo.model.language.JLo;
import jnome.core.language.Java;
import jnome.core.language.JavaLanguageFactory;
import chameleon.core.document.Document;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.RootNamespace;
import chameleon.exception.ModelException;
import chameleon.plugin.build.BuildProgressHelper;
import chameleon.support.translate.IncrementalTranslator;
import chameleon.workspace.Project;
import chameleon.workspace.View;

public class IncrementalJavaTranslator extends IncrementalTranslator<JLo, Java> {

	public IncrementalJavaTranslator(View source, View target) {
		super(source, target);
		_translator = new JavaTranslator();
	}
	
//	public IncrementalJavaTranslator(JLo source) {
//		this(source,createJava(source));
//	}
	
//	private static Java createJava(View source) {
//		Java result = new JavaLanguageFactory().create();
//		Project project = new Project("clone", (RootNamespace) source.namespace().clone(), result, source.project().ro ot());
//		return result;
//	}

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
	public Collection<Document> build(Document source, List<Document> allProjectCompilationUnits,	BuildProgressHelper buildProgressHelper) throws ModelException {
		//initTargetLanguage();
		List<Document> result = translate(source,implementationCompilationUnit(source));
		Document ifaceCU = (result.size() > 1 ? result.get(1) : null); 
		store(source, ifaceCU,_interfaceMap);
		if(buildProgressHelper != null) {
			buildProgressHelper.addWorked(1);
		}
		return result;
	}

	public List<Document> translate(Document source, Document implementationCompilationUnit) throws LookupException, ModelException {
		return _translator.translate(source, implementationCompilationUnit);
	}
  
	private Map<Document,Document> _interfaceMap = new HashMap<Document,Document>();
}