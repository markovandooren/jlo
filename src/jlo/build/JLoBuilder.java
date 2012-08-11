package jlo.build;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import jlo.model.language.JLo;
import jlo.output.JLoSyntax;
import jlo.translate.IncrementalJavaTranslator;
import jnome.core.language.Java;
import jnome.core.language.JavaLanguageFactory;
import jnome.output.JavaCompilationUnitWriter;
import chameleon.core.document.Document;
import chameleon.core.language.Language;
import chameleon.core.namespace.RootNamespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.plugin.Plugin;
import chameleon.plugin.PluginImpl;
import chameleon.plugin.build.BuildProgressHelper;
import chameleon.plugin.build.Builder;
import chameleon.plugin.build.CompilationUnitWriter;
import chameleon.plugin.output.Syntax;
import chameleon.workspace.Project;

public class JLoBuilder extends PluginImpl implements Builder {
	
	public JLoBuilder(Project project) {
		setLanguage(project.language(), Builder.class);
	}
	
	public JLoBuilder() {
		
	}	
	
	@Override
	public <T extends Plugin> void setLanguage(Language lang, Class<T> pluginInterface) {
		if(! (lang instanceof JLo)) {
			throw new ChameleonProgrammerException();
		}
		super.setLanguage(lang, pluginInterface);
		Java target = new JavaLanguageFactory().create();
		Project project = new Project("target", new RootNamespace(), target);
		//		target.setPlugin(Syntax.class, new JavaCodeWriter());
		target.setPlugin(Syntax.class, new JLoSyntax()); // DEBUG for viewing the intermediate steps, we attach the JLo syntax.
		_translator = new IncrementalJavaTranslator((JLo) lang, target);
	}

	public void build(Document compilationUnit, List<Document> allProjectCompilationUnits, File outputDir) throws ModelException, IOException {
		build(compilationUnit,allProjectCompilationUnits,outputDir,null);
	}
	
	public void build(Document compilationUnit, List<Document> allProjectCompilationUnits, File outputDir, BuildProgressHelper buildProgressHelper) throws ModelException, IOException {
		try {
			CompilationUnitWriter writer = new JavaCompilationUnitWriter(".java");
			String fileName = writer.fileName(compilationUnit);
			System.out.println("Building "+fileName);
			Collection<Document> compilationUnits = _translator.build(compilationUnit, allProjectCompilationUnits,buildProgressHelper);
			for(Document translated : compilationUnits) {
				writer.write(translated,outputDir);
			}
		} catch(Error e) {
			e.printStackTrace();
			throw e;
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Language targetLanguage() {
		return _translator.targetLanguage();
	}

	public Language sourceLanguage() {
		return _translator.sourceLanguage();
	}

	private IncrementalJavaTranslator _translator;

	@Override
	public Plugin clone() {
		return new JLoBuilder();
	}

	@Override
	public void build(List<Document> compilationUnits, List<Document> allProjectCompilationUnits,	File outputDir, BuildProgressHelper buildProgressHelper) throws ModelException,	IOException {
		for (Document cu : compilationUnits) {
			buildProgressHelper.checkForCancellation();
			build(cu, allProjectCompilationUnits,outputDir,buildProgressHelper);
			buildProgressHelper.addWorked(1);
		}
	}

	@Override
	public int totalAmountOfWork(List<Document> compilationUnits, List<Document> allProjectCompilationUnits) {
		return compilationUnits.size();
	}
}
