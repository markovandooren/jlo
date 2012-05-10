package jlo.eclipse;

import java.io.File;
import java.io.IOException;

import jlo.build.JLoBuilder;
import jlo.input.JLoFactory;
import jlo.input.JLoModelFactory;
import jlo.model.language.JLo;
import jlo.output.JLoSyntax;
import chameleon.core.language.Language;
import chameleon.eclipse.connector.EclipseBootstrapper;
import chameleon.eclipse.connector.EclipseEditorExtension;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.plugin.build.Builder;
import chameleon.plugin.output.Syntax;


public class Bootstrapper extends EclipseBootstrapper {
	
	public final static String PLUGIN_ID="be.chameleon.eclipse.jlo";
	
	public Bootstrapper() {
		super("JLo","0.1","jlo");
	}
	
	public Language createLanguage() throws IOException, ParseException {
		ModelFactory factory = new JLoModelFactory();
		JLo result = (JLo) factory.language();
		factory.setLanguage(result, ModelFactory.class);
		try {
			loadAPIFiles(".jlo", PLUGIN_ID, factory);
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension(getLanguageName()));
		return result;
	}

	public Builder createBuilder(Language source, File projectDirectory) {
		File outputDirectory = new File(projectDirectory.getAbsolutePath()+File.separator+"java");
		return new JLoBuilder((JLo) source, outputDirectory);
	}

}
