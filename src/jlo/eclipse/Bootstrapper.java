package jlo.eclipse;

import jlo.model.language.JLo;
import jlo.model.language.JLoLanguageFactory;
import chameleon.core.language.Language;
import chameleon.eclipse.connector.EclipseBootstrapper;
import chameleon.eclipse.connector.EclipseEditorExtension;
import chameleon.workspace.ProjectException;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Bootstrapper() {
		super("JLo","0.1",PLUGIN_ID);
	}
	
	public Language createLanguage() throws ProjectException  {
//		String extension = ".jlo";
		JLo result = new JLoLanguageFactory().create();
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension(getLanguageName()));
		return result;
	}

}
