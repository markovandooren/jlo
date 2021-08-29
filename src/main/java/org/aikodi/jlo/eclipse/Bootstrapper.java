package org.aikodi.jlo.eclipse;

import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.eclipse.connector.EclipseBootstrapper;
import org.aikodi.chameleon.eclipse.connector.EclipseEditorExtension;
import org.aikodi.chameleon.workspace.ProjectException;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.language.JLoLanguageFactory;


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
