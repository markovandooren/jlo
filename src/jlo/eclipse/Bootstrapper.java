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
	
	public void registerFileExtensions() {
//		addExtension("java"); This causes problems with the generated files after a refresh. Until
//		                      we have a source path, I will simply rename the API files to .jlo
		addExtension("jlo");
	}
	
	public String getLanguageName() {
		return "J.Lo";
	}

	public String getLanguageVersion() {
		return "1.0";
	}

	public String getVersion() {
		return "1.0";
	}

	public String getDescription() {
		return "Java with subobjects";
	}
	
	public String getLicense() {
		return "";
	}

	public Syntax getCodeWriter() {
		return null;
	}

	public Language createLanguage() throws IOException, ParseException {
		JLo result = new JLo();
		ModelFactory factory = new JLoModelFactory(result);
		factory.setLanguage(result, ModelFactory.class);
		try {
			loadAPIFiles(".jlo", PLUGIN_ID, factory);
		} catch(ChameleonProgrammerException exc) {
			// Object and String may not be present yet.
		}
		result.setPlugin(EclipseEditorExtension.class, new JLoEditorExtension());
		result.setPlugin(Syntax.class, new JLoSyntax());
		result.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		return result;
	}

	public Builder createBuilder(Language source, File projectDirectory) {
//		RootNamespace clone = source.defaultNamespace().clone();
//		result.cloneConnectorsFrom(source);
//		result.cloneProcessorsFrom(source);
//		result.setDefaultNamespace(clone);
		File outputDirectory = new File(projectDirectory.getAbsolutePath()+File.separator+"java");
		return new JLoBuilder((JLo) source, outputDirectory);
	}

}
