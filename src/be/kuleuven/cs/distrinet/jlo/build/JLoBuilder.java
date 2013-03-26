package be.kuleuven.cs.distrinet.jlo.build;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import be.kuleuven.cs.distrinet.jlo.model.language.JLo;
import be.kuleuven.cs.distrinet.jlo.output.JLoSyntax;
import be.kuleuven.cs.distrinet.jlo.translate.IncrementalJavaTranslator;
import be.kuleuven.cs.distrinet.jnome.core.language.Java;
import be.kuleuven.cs.distrinet.jnome.core.language.JavaLanguageFactory;
import be.kuleuven.cs.distrinet.jnome.output.JavaCompilationUnitWriter;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaView;
import be.kuleuven.cs.distrinet.chameleon.core.document.Document;
import be.kuleuven.cs.distrinet.chameleon.core.lookup.LookupException;
import be.kuleuven.cs.distrinet.chameleon.core.namespace.LazyRootNamespace;
import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.exception.ModelException;
import be.kuleuven.cs.distrinet.chameleon.plugin.ViewPlugin;
import be.kuleuven.cs.distrinet.chameleon.plugin.ViewPluginImpl;
import be.kuleuven.cs.distrinet.chameleon.plugin.build.BuildException;
import be.kuleuven.cs.distrinet.chameleon.plugin.build.BuildProgressHelper;
import be.kuleuven.cs.distrinet.chameleon.plugin.build.Builder;
import be.kuleuven.cs.distrinet.chameleon.plugin.build.CompilationUnitWriter;
import be.kuleuven.cs.distrinet.chameleon.plugin.output.Syntax;
import be.kuleuven.cs.distrinet.chameleon.workspace.InputException;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;

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
			if(buildProgressHelper != null) {
				buildProgressHelper.checkForCancellation();
			}
			build(cu, outputDir,buildProgressHelper);
			if(buildProgressHelper != null) {
				buildProgressHelper.addWorked(1);
			}
		}
	}

	@Override
	public int totalAmountOfWork(List<Document> compilationUnits, List<Document> allProjectCompilationUnits) {
		return compilationUnits.size();
	}
}
