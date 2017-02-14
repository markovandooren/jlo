package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.plugin.build.BuildProgressHelper;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Private;
import org.aikodi.chameleon.support.translate.IncrementalTranslator;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.java.core.language.Java7;
import org.aikodi.jlo.model.language.JLo;

public class JLoToJava8Translator extends IncrementalTranslator<JLo, Java7> {

  public JLoToJava8Translator(View source, View target) {
    super(source, target);
  }

  @Override
  public Collection<Document> build(Document source, BuildProgressHelper buildProgressHelper) throws BuildException {
    List<Document> result;
    try {
      result = translate(source);
    } catch (ModelException e) {
      throw new BuildException(e);
    }
    return result;
  }

  public List<Document> translate(Document sourceDocument) throws LookupException, ModelException {
    List<Document> result = new ArrayList<Document>();
    Document interfaceDocument = createInterface(sourceDocument);
    result.add(interfaceDocument);
    Document implementationDocument = createImplementation(sourceDocument);
    if (implementationDocument != null) {
      result.add(implementationDocument);
    }
    return result;
  }

  protected Document createImplementation(Document sourceDocument) throws LookupException {
    Document result = sourceDocument.cloneTo(target());
    return new Java8ClassGenerator().createImplementation(result);
  }

  protected void makeFieldsPrivate(Document target) {
    target.apply(MemberVariableDeclarator.class, d -> d.addModifier(new Private()));
  }

  protected Document createInterface(Document sourceDocument) throws LookupException {
    Document result = sourceDocument.cloneTo(target());
    return new Java8InterfaceGenerator().createInterface(result);
  }

  // protected void makeImplicitlyAbstractMethodsAbstract(Document target) {
  // target.apply(Method.class, m -> {
  // if(m.implementation() == null) {
  // m.addModifier(new Abstract());
  // m.flushCache();
  // }
  // });
  // }

}
