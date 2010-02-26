package subobjectjava.translate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jnome.core.language.Java;

import org.rejuse.association.SingleAssociation;

import subobjectjava.model.language.SubobjectJava;
import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.element.Element;
import chameleon.core.language.Language;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.Namespace;
import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.type.Type;

public class IncrementalJavaTranslator {

	public IncrementalJavaTranslator(SubobjectJava source, Java target) {
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
			fullyCloneCompilationUnit(compilationUnit);
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
	
	public JavaTranslator basicTranslator() {
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
	public CompilationUnit build(CompilationUnit compilationUnit) throws LookupException {
		if(!_initialized) {
			initTargetLanguage();
		}
		// Remove a possible old translation of the given compilation unit
		// from the target model.
		CompilationUnit clone = fullyCloneCompilationUnit(compilationUnit);
		NamespacePart nsp = compilationUnit.namespaceParts().get(0);
		NamespacePart newNamespacePart = clone.namespaceParts().get(0);

		List<Type> originalTypes = nsp.children(Type.class);
		List<Type> newTypes = newNamespacePart.children(Type.class);
		int size = originalTypes.size();
		for(int i=0; i<size;i++) {
			SingleAssociation<Type, Element> newParentLink = newTypes.get(i).parentLink();
			Type translated = basicTranslator().translation(originalTypes.get(i));
			newParentLink.getOtherRelation().replace(newParentLink, translated.parentLink());
		}
		return clone;
	}
	
	private Map<CompilationUnit,CompilationUnit> _cuMap = new HashMap<CompilationUnit,CompilationUnit>();

	
	public CompilationUnit fullyCloneCompilationUnit(CompilationUnit compilationUnit) throws LookupException {
		CompilationUnit old = _cuMap.get(compilationUnit);
		if(old != null) {
			old.disconnect();
		}
		CompilationUnit clone = compilationUnit.clone();
		// connect the namespacepart of the clone compilation unit
		// to the proper namespace in the target model. The cloned
		// namespace part is not connected to a namespace, so we
		// need the original namespacepart to obtain the fqn.
		NamespacePart nsp = compilationUnit.namespaceParts().get(0);
		Namespace ns = nsp.namespace();
		String fqn = ns.getFullyQualifiedName();
		Namespace newNs = targetLanguage().defaultNamespace().getOrCreateNamespace(fqn);
		NamespacePart newNamespacePart = clone.namespaceParts().get(0);
		newNs.addNamespacePart(newNamespacePart);
		_cuMap.put(compilationUnit, clone);
    return clone;
	}
}
