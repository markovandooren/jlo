package be.kuleuven.cs.distrinet.jlo.eclipse;

import be.kuleuven.cs.distrinet.jlo.model.language.JLo;
import be.kuleuven.cs.distrinet.jlo.model.language.JLoLanguageFactory;
import be.kuleuven.cs.distrinet.jlo.model.language.JLoProjectConfigurator;
import be.kuleuven.cs.distrinet.chameleon.core.language.Language;
import be.kuleuven.cs.distrinet.chameleon.eclipse.connector.EclipseBootstrapper;
import be.kuleuven.cs.distrinet.chameleon.eclipse.connector.EclipseEditorExtension;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectConfigurator;
import be.kuleuven.cs.distrinet.chameleon.workspace.ProjectException;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Language createLanguage() throws ProjectException  {
		JLo result = new JLoLanguageFactory().create();
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension());
		// When running in development mode (from classes), the base library is located
		// one level higher. This method marks the configurator to search one level higher
		// when it is not run from within a jar file. When run form within a jar, there is no
		// problem.
		return result;
	}

}
