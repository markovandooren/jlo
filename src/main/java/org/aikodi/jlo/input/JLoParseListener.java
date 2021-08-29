package org.aikodi.jlo.input;

import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.namespace.NamespaceReference;
import org.aikodi.jlo.input.JLoParser.KlassContext;
import org.aikodi.jlo.input.JLoParser.NamespaceReferenceContext;
import org.antlr.v4.runtime.ParserRuleContext;

public class JLoParseListener extends JLoBaseListener {

  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    System.out.println("enter every "+ctx.getClass().getSimpleName());
  }
  
  @Override
  public void exitEveryRule(ParserRuleContext ctx) {
    System.out.println("exit every "+ctx.getClass().getSimpleName());
  }
  
  @Override
  public void enterKlass(KlassContext ctx) {
    System.out.println("enter klass "+ctx.getClass().getSimpleName());
  }
  
  @Override
  public void exitKlass(KlassContext ctx) {
    System.out.println("exit klass "+ctx.getClass().getSimpleName());
  }
  
  @Override
  public void exitNamespaceReference(NamespaceReferenceContext ctx) {
    store(new NamespaceReference(ctx.getText()));
  }
  
  private void store(Element element) {
    
  }

}
