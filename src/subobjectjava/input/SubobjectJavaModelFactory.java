package subobjectjava.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import jnome.core.language.Java;
import jnome.input.JavaModelFactory;
import jnome.input.parser.JavaLexer;
import jnome.input.parser.JavaParser;
import jnome.output.JavaCodeWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;

import subobjectjava.model.language.SubobjectJava;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.output.Syntax;
import chameleon.support.input.ChameleonParser;
import chameleon.support.input.ModelFactoryUsingANTLR;
import chameleon.tool.Connector;

public class SubobjectJavaModelFactory extends JavaModelFactory {

	/**
	 * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public SubobjectJavaModelFactory() {
		SubobjectJava lang = new SubobjectJava();
		lang.setConnector(Syntax.class, new JavaCodeWriter());
		setLanguage(lang, ModelFactory.class);
	}
	
	/**
	 * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public SubobjectJavaModelFactory(SubobjectJava language) throws IOException, ParseException {
		super(language);
	}
	
	
	/**
	 * Initialize a new Java model factory with the given collection of base classes.
	 * All predefined elements of the language will be initialized. 
	 */
	public SubobjectJavaModelFactory(Collection<File> base) throws IOException, ParseException {
		this(new SubobjectJava(), base);
	}
	
	//FIXME: Object and String must be parsed.
	public SubobjectJavaModelFactory(SubobjectJava language, Collection<File> base) throws IOException, ParseException {
		super(language, base);
	}
	
    // TODO: documentation (Jens)
	@Override
    public Lexer lexer(ANTLRInputStream input) {
    	return new SubobjectJavaLexer(input);
    }
    
    // TODO: documentation (Jens)
	@Override
    public ChameleonParser parser(CommonTokenStream tokens) {
    	return new SubobjectJavaParser(tokens);
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
