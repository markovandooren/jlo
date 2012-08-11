package jlo.eclipse;

import java.io.IOException;

import jlo.build.JLoBuilder;
import jlo.model.language.JLo;
import jlo.model.language.JLoLanguageFactory;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.language.Language;
import chameleon.core.namespace.RootNamespace;
import chameleon.eclipse.connector.EclipseBootstrapper;
import chameleon.eclipse.connector.EclipseEditorExtension;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ParseException;
import chameleon.plugin.build.Builder;
import chameleon.test.provider.DirectoryProjectBuilder;
import chameleon.workspace.Project;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Bootstrapper() {
		super("JLo","0.1","jlo");
	}
	
	public Language createLanguage() throws IOException, ParseException {
		String extension = ".jlo";
		JLo result = new JLoLanguageFactory().create();
		
		Project project = new Project("x", new RootNamespace(new SimpleNameSignature("")), result);
		DirectoryProjectBuilder builder = new DirectoryProjectBuilder(project, extension);

		try {
			loadAPIFiles(extension, PLUGIN_ID, builder);
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension(getLanguageName()));
		result.setPlugin(Builder.class, new JLoBuilder());
		return result;
	}

}
