package be.kuleuven.cs.distrinet.jlo.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Collection;

import be.kuleuven.cs.distrinet.jlo.model.language.JLo;
import be.kuleuven.cs.distrinet.jnome.input.JavaModelFactory;
import be.kuleuven.cs.distrinet.jnome.output.JavaCodeWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import be.kuleuven.cs.distrinet.chameleon.core.document.Document;
import be.kuleuven.cs.distrinet.chameleon.core.element.Element;
import be.kuleuven.cs.distrinet.chameleon.core.tag.Metadata;
import be.kuleuven.cs.distrinet.chameleon.eclipse.connector.EclipseEditorTag;
import be.kuleuven.cs.distrinet.chameleon.exception.ChameleonProgrammerException;
import be.kuleuven.cs.distrinet.chameleon.input.ModelFactory;
import be.kuleuven.cs.distrinet.chameleon.input.ParseException;
import be.kuleuven.cs.distrinet.chameleon.input.PositionMetadata;
import be.kuleuven.cs.distrinet.chameleon.oo.member.Member;
import be.kuleuven.cs.distrinet.chameleon.oo.plugin.ObjectOrientedFactory;
import be.kuleuven.cs.distrinet.chameleon.plugin.output.Syntax;
import be.kuleuven.cs.distrinet.chameleon.support.input.ChameleonParser;
import be.kuleuven.cs.distrinet.chameleon.support.input.ModelFactoryUsingANTLR;
import be.kuleuven.cs.distrinet.chameleon.workspace.View;

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
  public ChameleonParser getParser(InputStream inputStream,View view) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(inputStream);
    JLoLexer lexer = new JLoLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    JLoParser parser = new Parser(tokens);
    parser.setView(view);
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
		  	EclipseEditorTag all = (EclipseEditorTag) element.metadata(PositionMetadata.ALL);
		  	// If there is no ALL tag on the element, we cannot correctly shift the offset.
		  	if(all != null) {
		  		Parser parser = (Parser)getParser(inputStream, element.view());
		  		parser.setDocument(element.nearestAncestor(Document.class));
		  		result = parser.memberDecl().element;
		  		if(result != null) {
		  			shiftOffset(result, all.getOffset());
		  		}
		  	}
			}
			return result;
		} catch(RecognitionException exc) {
			throw new ParseException(element.nearestAncestor(Document.class));
		} catch(IOException exc) {
			throw new ChameleonProgrammerException("Parsing of a string caused an IOException",exc);
		}
	}

	private void shiftOffset(Element element, int shift) {
		Collection<Element> c = element.descendants();
		c.add(element);
		for(Element e: c) {
			for(Metadata m : e.metadata()) {
				if(m instanceof EclipseEditorTag) {
					((EclipseEditorTag)m).shift(shift);
				}
			}
		}
	}
}
