package jlo.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Collection;

import jlo.model.language.JLo;
import jnome.core.language.Java;
import jnome.input.JavaModelFactory;
import jnome.input.parser.JavaParser;
import jnome.output.JavaCodeWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import chameleon.core.compilationunit.CompilationUnit;
import chameleon.core.element.Element;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.member.Member;
import chameleon.plugin.Plugin;
import chameleon.plugin.output.Syntax;
import chameleon.support.input.ChameleonParser;
import chameleon.support.input.ModelFactoryUsingANTLR;

public class JLoModelFactory extends JavaModelFactory {

	/**
	 * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	public JLoModelFactory() {
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
	public JLoModelFactory(JLo language) throws IOException, ParseException {
		super(language);
	}
	
	
	/**
	 * Initialize a new Java model factory with the given collection of base classes.
	 * All predefined elements of the language will be initialized. 
	 */
	public JLoModelFactory(Collection<File> base) throws IOException, ParseException {
		this(new JLo(), base);
	}
	
	//FIXME: Object and String must be parsed.
	public JLoModelFactory(JLo language, Collection<File> base) throws IOException, ParseException {
		super(language, base);
	}

	@Override
  public ChameleonParser getParser(InputStream inputStream, String fileName) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(inputStream);
    JLoLexer lexer = new JLoLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    JLoParser parser = new Parser(tokens);
    parser.setLanguage((ObjectOrientedLanguage) language());
    return parser;
  }
	
  @Override
	public ModelFactoryUsingANTLR clone() {
		try {
			JLoModelFactory result = new JLoModelFactory();
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
  
	protected <P extends Element> Element parse(Element<?> element, String text) throws ParseException {
		try {
		  InputStream inputStream = new StringBufferInputStream(text);
		  Element result = null;
		  if(element instanceof Member) {
	  		Parser parser = (Parser)getParser(inputStream, "document");
	  		parser.setCompilationUnit(element.nearestAncestor(CompilationUnit.class));
				result = parser.memberDecl().element;
			}
			return result;
		} catch(RecognitionException exc) {
			throw new ParseException(element.nearestAncestor(CompilationUnit.class));
		} catch(IOException exc) {
			throw new ChameleonProgrammerException("Parsing of a string caused an IOException",exc);
		}
	}

}
