package jlo.model.language;

import java.net.URL;

import jlo.input.JLoFactory;
import jlo.input.JLoModelFactory;
import jlo.output.JLoSyntax;
import jnome.workspace.JavaConfigLoader;
import chameleon.input.ModelFactory;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.plugin.output.Syntax;
import chameleon.workspace.ConfigLoader;

public class JLoLanguageFactory {
	
	public JLo create() {
		JLo result = new JLo();
		result.setPlugin(ModelFactory.class, new JLoModelFactory());
		result.setPlugin(Syntax.class, new JLoSyntax());
		result.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		URL objectLocation = Object.class.getResource("/java/lang/Object.class");
		String fileName = objectLocation.getFile();
		String jarName = fileName.substring(5,fileName.indexOf('!'));
		result.setPlugin(ConfigLoader.class, new JLoConfigLoader(jarName));
		return result;
	}

}
