package jlo.model.language;

import java.net.URL;

import jlo.input.JLoFactory;
import jlo.input.JLoModelFactory;
import jlo.output.JLoSyntax;
import chameleon.core.factory.Factory;
import chameleon.input.ModelFactory;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.plugin.output.Syntax;
import chameleon.workspace.ProjectConfigurator;

/**
 * A convenience class for creating an object that represents the JLo language.
 * 
 * @author Marko van Dooren
 */
public class JLoLanguageFactory {
	
	/**
	 * Create a JLo language object with the following plugins attached:
	 * <ul>
	 *  <li>Syntax: JLoSyntax</li>
	 *  <li>ModelFactory: JLoModelFactory</li>
	 *  <li>ObjectOrientedFactory: JLoFactory</li>
	 *  <li>ProjectConfigurator: JLoConfigLoader</li>
	 * </ul>
	 * @return
	 */
	public JLo create() {
		JLo result = new JLo();
		result.setPlugin(ModelFactory.class, new JLoModelFactory());
		result.setPlugin(Syntax.class, new JLoSyntax());
		result.setPlugin(Factory.class, new JLoFactory());
		result.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		URL objectLocation = Object.class.getResource("/java/lang/Object.class");
		String fileName = objectLocation.getFile();
		String jarName = fileName.substring(5,fileName.indexOf('!'));
		result.setPlugin(ProjectConfigurator.class, new JLoProjectConfigurator(jarName));
		return result;
	}

}
