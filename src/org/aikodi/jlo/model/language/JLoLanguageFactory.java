package org.aikodi.jlo.model.language;

import org.aikodi.chameleon.core.factory.Factory;
import org.aikodi.chameleon.input.ModelFactory;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.plugin.output.Syntax;
import org.aikodi.chameleon.workspace.ProjectConfigurator;
import org.aikodi.jlo.input.JLoFactory;
import org.aikodi.jlo.input.JLoModelFactory;
import org.aikodi.jlo.output.JLoSyntax;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7LanguageFactory;
import be.kuleuven.cs.distrinet.jnome.input.JavaExpressionFactory;

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
    *  <li>Factory: JLoFactory</li>
	 *  <li>ObjectOrientedFactory: JLoFactory</li>
	 *  <li>ProjectConfigurator: JLoConfigLoader</li>
	 * </ul>
	 * @return
	 */
	public JLo create() {
		JLo result = new JLo();
		result.setPlugin(ModelFactory.class, new JLoModelFactory());
//    result.setPlugin(ModelFactory.class, new OldJLoModelFactory());
		result.setPlugin(Syntax.class, new JLoSyntax());
		JLoFactory factory = new JLoFactory();
      result.setPlugin(Factory.class, factory);
		result.setPlugin(ObjectOrientedFactory.class, factory);
		result.setPlugin(ExpressionFactory.class, new JavaExpressionFactory());
		result.setPlugin(ProjectConfigurator.class, new JLoProjectConfigurator(Java7LanguageFactory.javaBaseJar()));
		return result;
	}

}
