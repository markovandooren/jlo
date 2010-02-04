package subobjectjava.input;

import jnome.input.JavaFactory;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public class Parser extends SubobjectJavaParser {

	public Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		setFactory(new JavaFactory());
	}

	public Parser(TokenStream input) {
		super(input);
		setFactory(new JavaFactory());
	}

}
