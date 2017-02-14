package org.aikodi.jlo.build;

import java.util.Collection;
import java.util.List;

import java.io.File;
import java.io.IOException;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.namespace.LazyRootNamespace;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.plugin.ViewPlugin;
import org.aikodi.chameleon.plugin.ViewPluginImpl;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.plugin.build.BuildProgressHelper;
import org.aikodi.chameleon.plugin.build.Builder;
import org.aikodi.chameleon.plugin.build.DocumentWriter;
import org.aikodi.chameleon.workspace.InputException;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.language.Java7LanguageFactory;
import org.aikodi.java.output.JavaDocumentWriter;
import org.aikodi.java.workspace.JavaView;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.translate.JLoToJava8Translator;

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
		Java7 target = new Java7LanguageFactory().create();
		JavaView targetView = new JavaView(new LazyRootNamespace(), target);
//		target.setPlugin(Syntax.class, new JLoSyntax()); // DEBUG for viewing the intermediate steps, we attach the JLo syntax.
		_translator = new JLoToJava8Translator(view(), targetView);
	}

	public void build(Document compilationUnit, File outputDir) throws BuildException {
		build(compilationUnit,outputDir,null);
	}
	
	public void build(Document compilationUnit, File outputDir, BuildProgressHelper buildProgressHelper) throws BuildException {
		try {
			DocumentWriter writer = new JavaDocumentWriter(".java");
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

	private JLoToJava8Translator _translator;

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
	public int totalAmountOfWork(List<Document> documents, List<Document> allDocuments) {
		return documents.size();
	}
}
