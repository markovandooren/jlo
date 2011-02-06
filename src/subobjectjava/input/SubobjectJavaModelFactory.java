package subobjectjava.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import jnome.core.language.Java;
import jnome.input.JavaModelFactory;
import jnome.output.JavaCodeWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import subobjectjava.model.language.JLo;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.plugin.Plugin;
import chameleon.plugin.output.Syntax;
import chameleon.support.input.ChameleonParser;
import chameleon.support.input.ModelFactoryUsingANTLR;

public class SubobjectJavaModelFactory extends JavaModelFactory {

	/**
	 * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public SubobjectJavaModelFactory() {
		JLo lang = new JLo();
		lang.setPlugin(Syntax.class, new JavaCodeWriter());
		setLanguage(lang, ModelFactory.class);
	}
	
	/**
	 * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public SubobjectJavaModelFactory(JLo language) throws IOException, ParseException {
		super(language);
	}
	
	
	/**
	 * Initialize a new Java model factory with the given collection of base classes.
	 * All predefined elements of the language will be initialized. 
	 */
	public SubobjectJavaModelFactory(Collection<File> base) throws IOException, ParseException {
		this(new JLo(), base);
	}
	
	//FIXME: Object and String must be parsed.
	public SubobjectJavaModelFactory(JLo language, Collection<File> base) throws IOException, ParseException {
		super(language, base);
	}

	@Override
  public ChameleonParser getParser(InputStream inputStream, String fileName) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(inputStream);
    SubobjectJavaLexer lexer = new SubobjectJavaLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    SubobjectJavaParser parser = new Parser(tokens);
    parser.setLanguage((ObjectOrientedLanguage) language());
    return parser;
  }
	
  @Override
	public ModelFactoryUsingANTLR clone() {
		try {
			SubobjectJavaModelFactory result = new SubobjectJavaModelFactory();
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Exception while cloning a JavaModelFactory", e);
		}
	}

//	@Override
//	//FIXME: remove this method when the API is parsed
//	public void initializePredefinedElements() {
//	  addPrimitives(language().defaultNamespace());
//	}
  

}
