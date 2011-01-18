package subobjectjava.input;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public class Parser extends SubobjectJavaParser {

	public Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		setFactory(new JLoFactory());
	}

	public Parser(TokenStream input) {
		super(input);
		setFactory(new JLoFactory());
	}

}
