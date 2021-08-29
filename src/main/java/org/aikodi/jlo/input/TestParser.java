package org.aikodi.jlo.input;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.namespace.LazyRootNamespace;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.input.ModelFactory;
import org.aikodi.chameleon.input.ParseException;
import org.aikodi.chameleon.plugin.output.Syntax;
import org.aikodi.chameleon.workspace.DocumentScanner;
import org.aikodi.chameleon.workspace.FakeDocumentLoader;
import org.aikodi.chameleon.workspace.FakeDocumentScanner;
import org.aikodi.chameleon.workspace.Project;
import org.aikodi.chameleon.workspace.ProjectException;
import org.aikodi.java.workspace.JavaView;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.language.JLoLanguageFactory;

public class TestParser {

  
  public static void main(String[] args) {
    File file = new File(args[0]);
    
    JLo jlo = new JLoLanguageFactory().create();
    Project project = new Project("JLo Parser Test", null);
    JavaView view = new JavaView(new LazyRootNamespace(), jlo);
    project.addView(view);
    try {
      DocumentScanner scanner = new FakeDocumentScanner();
      Document document = new Document();
      new FakeDocumentLoader(document, scanner);
      view.addSource(scanner);
      jlo.plugin(ModelFactory.class).parse(new BufferedInputStream(new FileInputStream(file)), document);
      System.out.println(jlo.plugin(Syntax.class).toCode(document));
    } catch (IOException | ParseException | ProjectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
}
