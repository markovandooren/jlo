package jlo.input;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Collection;

import jlo.model.language.JLo;
import jnome.input.JavaModelFactory;
import jnome.output.JavaCodeWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import chameleon.core.document.Document;
import chameleon.core.element.Element;
import chameleon.exception.ChameleonProgrammerException;
import chameleon.input.ModelFactory;
import chameleon.input.ParseException;
import chameleon.oo.language.ObjectOrientedLanguage;
import chameleon.oo.member.Member;
import chameleon.oo.plugin.ObjectOrientedFactory;
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
		lang.setPlugin(ObjectOrientedFactory.class, new JLoFactory());
		setLanguage(lang, ModelFactory.class);
	}
	
	@Override
  public ChameleonParser getParser(InputStream inputStream) throws IOException {
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
  
	protected <P extends Element> Element parse(Element element, String text) throws ParseException {
		try {
		  InputStream inputStream = new StringBufferInputStream(text);
		  Element result = null;
		  if(element instanceof Member) {
	  		Parser parser = (Parser)getParser(inputStream);
	  		parser.setDocument(element.nearestAncestor(Document.class));
				result = parser.memberDecl().element;
			}
			return result;
		} catch(RecognitionException exc) {
			throw new ParseException(element.nearestAncestor(Document.class));
		} catch(IOException exc) {
			throw new ChameleonProgrammerException("Parsing of a string caused an IOException",exc);
		}
	}

}
