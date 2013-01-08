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
import jnome.workspace.JavaView;
import chameleon.core.document.Document;
import chameleon.core.lookup.LookupException;
import chameleon.core.namespace.LazyRootNamespace;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.exception.ModelException;
import chameleon.plugin.ViewPlugin;
import chameleon.plugin.ViewPluginImpl;
import chameleon.plugin.build.BuildException;
import chameleon.plugin.build.BuildProgressHelper;
import chameleon.plugin.build.Builder;
import chameleon.plugin.build.CompilationUnitWriter;
import chameleon.plugin.output.Syntax;
import chameleon.workspace.InputException;
import chameleon.workspace.View;

public class JLoBuilder extends ViewPluginImpl implements Builder {
	
	public JLoBuilder(View view) {
		setContainer(view, Builder.class);
	}
	
	private JLoBuilder() {
	}
	
	@Override
	public <T extends ViewPlugin> void setContainer(View view, Class<T> pluginInterface) {
		if(! (view.language() instanceof JLo)) {
			throw new ChameleonProgrammerException();
		}
		super.setContainer(view, pluginInterface);
		Java target = new JavaLanguageFactory().create();
		View targetView = new JavaView(new LazyRootNamespace(), target);
		//		target.setPlugin(Syntax.class, new JavaCodeWriter());
		target.setPlugin(Syntax.class, new JLoSyntax()); // DEBUG for viewing the intermediate steps, we attach the JLo syntax.
		_translator = new IncrementalJavaTranslator(view(), targetView);
	}

	public void build(Document compilationUnit, File outputDir) throws BuildException {
		build(compilationUnit,outputDir,null);
	}
	
	public void build(Document compilationUnit, File outputDir, BuildProgressHelper buildProgressHelper) throws BuildException {
		try {
			CompilationUnitWriter writer = new JavaCompilationUnitWriter(".java");
			String fileName = writer.fileName(compilationUnit);
			System.out.println("Building "+fileName);
			Collection<Document> compilationUnits = _translator.build(compilationUnit, buildProgressHelper);
			for(Document translated : compilationUnits) {
				translated.flushCache();
				writer.write(translated,outputDir);
			}
		} catch(Error e) {
			e.printStackTrace();
			throw e;
		} catch(RuntimeException e) {
			e.printStackTrace();
			throw e;
		} catch (LookupException e) {
			throw new BuildException(e);
		} catch (ModelException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		}
	}

	public View target() {
		return _translator.target();
	}

	public View source() {
		return _translator.source();
	}

	private IncrementalJavaTranslator _translator;

	@Override
	public JLoBuilder clone() {
		return new JLoBuilder();
	}

	public void buildAll(File outputDir, BuildProgressHelper buildProgressHelper) throws BuildException {
		try {
			build(view().sourceDocuments(), outputDir, buildProgressHelper);
		} catch (InputException e) {
			throw new BuildException(e);
		}
	}
	
	@Override
	public void build(List<Document> compilationUnits, File outputDir, BuildProgressHelper buildProgressHelper) throws BuildException {
		for (Document cu : compilationUnits) {
			buildProgressHelper.checkForCancellation();
			build(cu, outputDir,buildProgressHelper);
			buildProgressHelper.addWorked(1);
		}
	}

	@Override
	public int totalAmountOfWork(List<Document> compilationUnits, List<Document> allProjectCompilationUnits) {
		return compilationUnits.size();
	}
}
