package jlo.model.language;

import jlo.input.JLoFactory;
import jlo.input.JLoModelFactory;
import jlo.output.JLoSyntax;
import chameleon.input.ModelFactory;
import chameleon.oo.plugin.ObjectOrientedFactory;
import chameleon.plugin.output.Syntax;

public class JLoLanguageFactory {
	
	public JLo create() {
		JLo result = new JLo();
		result.setPlugin(ModelFactory.class, new JLoModelFactory());
		result.setPlugin(Syntax.class, new JLoSyntax());
		result.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		return result;
	}

}
