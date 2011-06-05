package subobjectjava.translate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jnome.core.language.Java;
import subobjectjava.model.language.JLo;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.lookup.LookupException;
import chameleon.exception.ModelException;
import chameleon.support.translate.IncrementalTranslator;

public class IncrementalJavaTranslator extends IncrementalTranslator<JLo, Java> {

	public IncrementalJavaTranslator(JLo source, Java target) {
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
	public Collection<CompilationUnit> build(CompilationUnit source, List<CompilationUnit> allProjectCompilationUnits) throws ModelException {
		//initTargetLanguage();

		List<CompilationUnit> result = translate(source,implementationCompilationUnit(source));
		store(source, result.get(1),_interfaceMap);
		return result;
	}

	public List<CompilationUnit> translate(CompilationUnit source, CompilationUnit implementationCompilationUnit) throws LookupException, ModelException {
		return _translator.translate(source, implementationCompilationUnit);
	}
  
	private Map<CompilationUnit,CompilationUnit> _interfaceMap = new HashMap<CompilationUnit,CompilationUnit>();
}