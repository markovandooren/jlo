package org.aikodi.jlo.translate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.modifier.ElementWithModifiers;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.property.ChameleonProperty;
import org.aikodi.chameleon.core.property.StaticChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceWithName;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.exception.ModelException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.member.Member;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.chameleon.oo.method.Implementation;
import org.aikodi.chameleon.oo.method.Method;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.MemberVariable;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.plugin.build.BuildException;
import org.aikodi.chameleon.plugin.build.BuildProgressHelper;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Interface;
import org.aikodi.chameleon.support.modifier.Private;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.support.translate.IncrementalTranslator;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.language.JLo;
import org.aikodi.jlo.model.subobject.Subobject;

import be.kuleuven.cs.distrinet.jnome.core.language.Java7;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Default;
import be.kuleuven.cs.distrinet.jnome.core.modifier.Implements;
import be.kuleuven.cs.distrinet.jnome.core.type.BasicJavaTypeReference;

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

  protected Document createImplementation(Document sourceDocument) {
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
