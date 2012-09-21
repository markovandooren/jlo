package jlo.eclipse;

import java.io.IOException;

import jlo.build.JLoBuilder;
import jlo.model.language.JLo;
import jlo.model.language.JLoLanguageFactory;
import jnome.input.JavaFileInputSourceFactory;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.language.Language;
import chameleon.core.namespace.RegularNamespaceFactory;
import chameleon.core.namespace.RootNamespace;
import chameleon.eclipse.connector.EclipseBootstrapper;
import chameleon.eclipse.connector.EclipseEditorExtension;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.plugin.build.Builder;
import chameleon.workspace.DirectoryLoader;
import chameleon.workspace.Project;
import chameleon.workspace.ProjectException;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Bootstrapper() {
		super("JLo","0.1","jlo");
	}
	
	public Language createLanguage() throws IOException, ParseException, ProjectException  {
		String extension = ".jlo";
		JLo result = new JLoLanguageFactory().create();
		
		Project project = new Project("Chameleon Eclipse project", new RootNamespace(new RegularNamespaceFactory()), result);
		JavaFileInputSourceFactory factory = new JavaFileInputSourceFactory(result.plugin(ModelFactory.class));
		DirectoryLoader builder = new DirectoryLoader(project, extension,null, factory);
		try {
			loadAPIFiles(extension, PLUGIN_ID, project, factory);
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension(getLanguageName()));
		result.setPlugin(Builder.class, new JLoBuilder());
		return result;
	}

}
