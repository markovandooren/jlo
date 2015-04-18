package org.aikodi.jlo.input;

import java.io.IOException;
import java.io.InputStream;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.input.ParseException;
import org.aikodi.chameleon.input.ModelFactory;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.plugin.LanguagePluginImpl;
import org.aikodi.chameleon.plugin.output.Syntax;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.input.JLoParser.CompilationUnitContext;
import org.aikodi.jlo.model.language.JLo;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import be.kuleuven.cs.distrinet.jnome.output.Java7Syntax;
import be.kuleuven.cs.distrinet.jnome.workspace.JavaView;

public class JLoModelFactory extends LanguagePluginImpl implements ModelFactory {

  /**
   * BE SURE TO CALL INIT() IF YOU USE THIS CONSTRUCTOR.
   * 
   * @throws IOException
   * @throws ParseException
   */
  public JLoModelFactory() {
  }

  public JLoParser getParser(InputStream inputStream, View view) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(inputStream);
    JLoLexer lexer = new JLoLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    JLoParser parser = new JLoParser(tokens);
//    parser.addParseListener(new JLoParseListener());
    return parser;
  }

  @Override
  public JLoModelFactory clone() {
    return new JLoModelFactory();
  }

//  public <P extends Element> Element parse(Element element, String text) throws ParseException {
//    try {
//      InputStream inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
//      Element result = null;
//      if (element instanceof Member) {
//        EclipseEditorTag all = (EclipseEditorTag) element.metadata(PositionMetadata.ALL);
//        // If there is no ALL tag on the element, we cannot correctly shift the
//        // offset.
//        if (all != null) {
//          JLoParser parser = getParser(inputStream, element.view());
//          
//          parser.setDocument(element.nearestAncestor(Document.class));
//          result = parser.memberDecl().element;
//          if (result != null) {
//            shiftOffset(result, all.getOffset());
//          }
//        }
//      }
//      return result;
//    } catch (RecognitionException exc) {
//      throw new ParseException(element.nearestAncestor(Document.class));
//    } catch (IOException exc) {
//      throw new ChameleonProgrammerException("Parsing of a string caused an IOException", exc);
//    }
//  }

  @Override
  public void parse(InputStream inputStream, Document document) throws IOException, ParseException {
    JavaView view = document.view(JavaView.class);
    JLoParser parser = getParser(inputStream, view);
    CompilationUnitContext compilationUnit = parser.compilationUnit();
    new JLoConvertor(document, view, (CommonTokenStream) parser.getTokenStream()).visit(compilationUnit);
  }
}
