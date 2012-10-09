package jlo.eclipse;

import jlo.build.JLoBuilder;
import jlo.model.language.JLo;
import jlo.model.language.JLoLanguageFactory;
import chameleon.core.language.Language;
import chameleon.eclipse.connector.EclipseBootstrapper;
import chameleon.eclipse.connector.EclipseEditorExtension;
import chameleon.plugin.build.Builder;
import chameleon.workspace.ProjectException;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Bootstrapper() {
		super("JLo","0.1","jlo",PLUGIN_ID);
	}
	
	public Language createLanguage() throws ProjectException  {
//		String extension = ".jlo";
		JLo result = new JLoLanguageFactory().create();
		
//		Project project = new Project("Chameleon Eclipse project", new RootNamespace(new RegularNamespaceFactory()), result);
//		EagerJavaFileInputSourceFactory factory = new EagerJavaFileInputSourceFactory(result.plugin(ModelFactory.class));
//		project.addSource(new DirectoryLoader(extension,null, factory));
//		try {
//			loadAPIFiles(extension, PLUGIN_ID, project, factory);
//		} catch(ChameleonProgrammerException exc) {
//			// Object and String may not be present yet.
//		}
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension(getLanguageName()));
		result.setPlugin(Builder.class, new JLoBuilder());
		return result;
	}

}
