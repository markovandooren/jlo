package subobjectjava.translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jnome.core.language.Java;

import org.rejuse.association.SingleAssociation;

import subobjectjava.model.language.JLo;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.element.Element;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.exception.ModelException;
import chameleon.oo.type.Type;

public class IncrementalJavaTranslator {

	public IncrementalJavaTranslator(JLo source, Java target) {
		_sourceLanguage = source;
		_targetLanguage = target;
		_translator = new JavaTranslator();
	}
	
	private boolean _initialized=false;
	
	private void initTargetLanguage() throws LookupException {
		Set<CompilationUnit> compilationUnits = new HashSet<CompilationUnit>();
		for(NamespacePart nsp: sourceLanguage().defaultNamespace().descendants(NamespacePart.class)) {
			CompilationUnit cu = nsp.nearestAncestor(CompilationUnit.class);
			if(cu != null) {
				compilationUnits.add(cu);
			}
		}
		for(CompilationUnit compilationUnit: compilationUnits) {
			implementationCompilationUnit(compilationUnit);
		}
		_initialized=true;
	}
	
	public Language sourceLanguage() {
		return _sourceLanguage;
	}
	
	private Language _sourceLanguage;
	
	public Language targetLanguage() {
		return _targetLanguage;
	}
	
	private Language _targetLanguage;
	
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
	public List<CompilationUnit> build(CompilationUnit source) throws ModelException {
		if(!_initialized) {
			initTargetLanguage();
		}
		List<CompilationUnit> result = translate(source,implementationCompilationUnit(source));
		store(source, result.get(1),_interfaceMap);
		return result;
	}

  public List<CompilationUnit> translate(CompilationUnit source, CompilationUnit implementationCompilationUnit) throws LookupException, ModelException {
    return _translator.translate(source, implementationCompilationUnit);
  }
	
//	private CompilationUnit interfaceCompilationUnit(CompilationUnit key, CompilationUnit implementation) throws ModelException {
//		CompilationUnit result = _translator.interfaceCompilationUnit(key, implementation);
//		store(key, result,_interfaceMap);
//		return result;
//  }
	
	private Map<CompilationUnit,CompilationUnit> _implementationMap = new HashMap<CompilationUnit,CompilationUnit>();

	private Map<CompilationUnit,CompilationUnit> _interfaceMap = new HashMap<CompilationUnit,CompilationUnit>();

	public CompilationUnit implementationCompilationUnit(CompilationUnit compilationUnit) throws LookupException {
		CompilationUnit clone = compilationUnit.cloneTo(targetLanguage());
		store(compilationUnit, clone,_implementationMap);
		targetLanguage().flushCache();
    return clone;
	}

	private void store(CompilationUnit compilationUnit, CompilationUnit generated, Map<CompilationUnit,CompilationUnit> storage) throws LookupException {
		CompilationUnit old = storage.get(compilationUnit);
		if(old != null) {
			if(generated != old) {
				old.namespacePart(1).getNamespaceLink().unlock();
			}
			old.disconnect();
		}
		// connect the namespacepart of the clone compilation unit
		// to the proper namespace in the target model. The cloned
		// namespace part is not connected to a namespace, so we
		// need the original namespacepart to obtain the fqn.
		storage.put(compilationUnit, generated);
	}
}
