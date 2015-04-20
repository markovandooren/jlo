package org.aikodi.jlo.input;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.factory.Factory;
import org.aikodi.chameleon.core.modifier.Modifier;
import org.aikodi.chameleon.core.namespace.NamespaceReference;
import org.aikodi.chameleon.core.namespacedeclaration.Import;
import org.aikodi.chameleon.core.namespacedeclaration.NamespaceDeclaration;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.expression.ParExpression;
import org.aikodi.chameleon.oo.method.ExpressionImplementation;
import org.aikodi.chameleon.oo.method.MethodHeader;
import org.aikodi.chameleon.oo.method.RegularImplementation;
import org.aikodi.chameleon.oo.method.SimpleNameMethodHeader;
import org.aikodi.chameleon.oo.namespacedeclaration.TypeImport;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.statement.Statement;
import org.aikodi.chameleon.oo.type.ClassBody;
import org.aikodi.chameleon.oo.type.ClassWithBody;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeElement;
import org.aikodi.chameleon.oo.type.TypeReference;
import org.aikodi.chameleon.oo.type.inheritance.InheritanceRelation;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.expression.NullLiteral;
import org.aikodi.chameleon.support.expression.RegularLiteral;
import org.aikodi.chameleon.support.expression.SuperTarget;
import org.aikodi.chameleon.support.expression.ThisLiteral;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Constructor;
import org.aikodi.chameleon.support.modifier.Native;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.jlo.input.JLoParser.AbstractImplementationContext;
import org.aikodi.jlo.input.JLoParser.AbstractModifierContext;
import org.aikodi.jlo.input.JLoParser.AndExpressionContext;
import org.aikodi.jlo.input.JLoParser.ArgumentsContext;
import org.aikodi.jlo.input.JLoParser.AssignmentStatementContext;
import org.aikodi.jlo.input.JLoParser.BlockContext;
import org.aikodi.jlo.input.JLoParser.BlockImplementationContext;
import org.aikodi.jlo.input.JLoParser.CharacterLiteralContext;
import org.aikodi.jlo.input.JLoParser.ClassBodyContext;
import org.aikodi.jlo.input.JLoParser.CompilationUnitContext;
import org.aikodi.jlo.input.JLoParser.EqualityExpressionContext;
import org.aikodi.jlo.input.JLoParser.ExponentiationExpressionContext;
import org.aikodi.jlo.input.JLoParser.ExprImplementationContext;
import org.aikodi.jlo.input.JLoParser.ExpressionContext;
import org.aikodi.jlo.input.JLoParser.ExpressionStatementContext;
import org.aikodi.jlo.input.JLoParser.FalseLiteralContext;
import org.aikodi.jlo.input.JLoParser.FloatingPointLiteralContext;
import org.aikodi.jlo.input.JLoParser.HighPriorityNumbericalExpressionContext;
import org.aikodi.jlo.input.JLoParser.IdentifierExpressionContext;
import org.aikodi.jlo.input.JLoParser.ImplementationContext;
import org.aikodi.jlo.input.JLoParser.ImportDeclarationContext;
import org.aikodi.jlo.input.JLoParser.InheritanceRelationContext;
import org.aikodi.jlo.input.JLoParser.InitModifierContext;
import org.aikodi.jlo.input.JLoParser.IntegerLiteralContext;
import org.aikodi.jlo.input.JLoParser.IntegerNumberLiteralContext;
import org.aikodi.jlo.input.JLoParser.KeywordBlockContext;
import org.aikodi.jlo.input.JLoParser.KlassContext;
import org.aikodi.jlo.input.JLoParser.LiteralExpressionContext;
import org.aikodi.jlo.input.JLoParser.LowPriorityNumbericalExpressionContext;
import org.aikodi.jlo.input.JLoParser.MemberFieldContext;
import org.aikodi.jlo.input.JLoParser.MethodContext;
import org.aikodi.jlo.input.JLoParser.MethodHeaderContext;
import org.aikodi.jlo.input.JLoParser.ModifierContext;
import org.aikodi.jlo.input.JLoParser.NamespaceContext;
import org.aikodi.jlo.input.JLoParser.NamespaceReferenceContext;
import org.aikodi.jlo.input.JLoParser.NativeImplementationContext;
import org.aikodi.jlo.input.JLoParser.NullExpressionContext;
import org.aikodi.jlo.input.JLoParser.OrExpressionContext;
import org.aikodi.jlo.input.JLoParser.OrderExpressionContext;
import org.aikodi.jlo.input.JLoParser.ParExpressionContext;
import org.aikodi.jlo.input.JLoParser.ParameterContext;
import org.aikodi.jlo.input.JLoParser.ParameterListContext;
import org.aikodi.jlo.input.JLoParser.ParametersContext;
import org.aikodi.jlo.input.JLoParser.QualifiedCallExpressionContext;
import org.aikodi.jlo.input.JLoParser.ReturnStatementContext;
import org.aikodi.jlo.input.JLoParser.ReturnTypeContext;
import org.aikodi.jlo.input.JLoParser.SelfCallExpressionContext;
import org.aikodi.jlo.input.JLoParser.SelfExpressionContext;
import org.aikodi.jlo.input.JLoParser.ShiftExpressionContext;
import org.aikodi.jlo.input.JLoParser.StatementContext;
import org.aikodi.jlo.input.JLoParser.StringLiteralContext;
import org.aikodi.jlo.input.JLoParser.SubobjectContext;
import org.aikodi.jlo.input.JLoParser.SuperExpressionContext;
import org.aikodi.jlo.input.JLoParser.TrueLiteralContext;
import org.aikodi.jlo.input.JLoParser.TypeContext;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.language.JLo;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import be.kuleuven.cs.distrinet.jnome.workspace.JavaView;

public class JLoConvertor extends JLoBaseVisitor<Object> {

  private Document _document;
  
  private JLo _jlo;
  
  private JavaView _view;
  
  private CommonTokenStream _stream;
  
  public JLoConvertor(Document document, JavaView view, CommonTokenStream stream) {
    this._document = document;
    this._view = view;
    this._jlo = view.language(JLo.class);
    this._stream = stream;
  }
  
  public JavaView view() {
    return _view;
  }
  
  protected Factory factory() {
    return jlo().plugin(Factory.class);
  }
  
  protected ObjectOrientedFactory ooFactory() {
    return jlo().plugin(ObjectOrientedFactory.class);
  }
  
  protected ExpressionFactory expressionFactory() {
    return jlo().plugin(ExpressionFactory.class);
  }
  
  protected JLo jlo() {
    return _jlo;
  }
  

  protected <T> T processLayout(T element, ParserRuleContext ctx) {
//    List<Token> hiddenTokensToLeft = _stream.getHiddenTokensToLeft(ctx.start.getTokenIndex(), HIDDEN);
//    for(Token token: hiddenTokensToLeft) {
//      System.out.println(token.getText().length());
//    }
////    System.out.println(hiddenTokensToLeft);
    return element;
  }
  
  @Override
  public Element visitCompilationUnit(CompilationUnitContext ctx) {
    NamespaceDeclaration ns;
    if(ctx.namespace() != null) {
      ns = visitNamespace(ctx.namespace());
    } else {
      ns = factory().createRootNamespaceDeclaration();
    }
    for(ImportDeclarationContext id: ctx.importDeclaration()) {
      ns.addImport(visitImportDeclaration(id));
    }
    List<KlassContext> klasses = ctx.klass();
    for(KlassContext klas : klasses) {
      ns.add(visitKlass(klas));
    }
    _document.add(ns);
    return _document;
  }

  @Override
  public Import visitImportDeclaration(ImportDeclarationContext ctx) {
    return new TypeImport(visitType(ctx.type()));
  }
  
  @Override
  public NamespaceDeclaration visitNamespace(NamespaceContext ctx) {
    NamespaceReference namespaceReference = visitNamespaceReference(ctx.namespaceReference());
    return factory().createNamespaceDeclaration(namespaceReference);
  }
  
  @Override
  public NamespaceReference visitNamespaceReference(NamespaceReferenceContext ctx) {
    return processLayout(new NamespaceReference(ctx.getText()),ctx);
  }
  
  @Override
  public Type visitKlass(KlassContext ctx) {
    Type result = ooFactory().createRegularType(ctx.Identifier().getText());
    InheritanceRelationContext inheritanceCtx = ctx.inheritanceRelation();
    if(inheritanceCtx != null) {
      result.addInheritanceRelation(visitInheritanceRelation(inheritanceCtx));
    }
    visitClassBody(ctx.classBody()).accept(((ClassWithBody)result).body());
    for(ModifierContext m: ctx.modifier()) {
      result.addModifier((Modifier)visit(m));
    }
    return processLayout(result,ctx);
  }
  
  @Override
  public InheritanceRelation visitInheritanceRelation(InheritanceRelationContext ctx) {
    return processLayout(new SubtypeRelation(visitType(ctx.type())),ctx);
  }
  
  @Override
  public TypeReference visitType(TypeContext ctx) {
    return processLayout(jlo().createTypeReference(ctx.getText()),ctx);
  }
  
  @Override
  public Consumer<ClassBody> visitClassBody(ClassBodyContext ctx) {
    return b -> ctx.bodyElement().stream().forEach(e -> b.add((TypeElement)visitBodyElement(e)));
  }
  
  
  @Override
  public NormalMethod visitMethod(MethodContext ctx) {
    NormalMethod result = ooFactory().createNormalMethod(visitMethodHeader(ctx.methodHeader()));
    ImplementationContext implementation = ctx.implementation();
    if(implementation != null) {
      ((Consumer<NormalMethod>) visit(implementation)).accept(result);
    }
    for(ModifierContext mctx: ctx.modifier()) {
      Modifier modifier = (Modifier) visit(mctx);
      result.addModifier(modifier);
    }
    return result;
  }
  
  @Override
  public MethodHeader visitMethodHeader(MethodHeaderContext ctx) {
    MethodHeader result = visitKeywordBlock(ctx.keywordBlock());
    ReturnTypeContext returnType = ctx.returnType();
    if(returnType != null) {
      result.setReturnTypeReference(visitReturnType(returnType));
    }
    return result;
  }
  
  @Override
  public MethodHeader visitKeywordBlock(KeywordBlockContext ctx) {
    SimpleNameMethodHeader result = new SimpleNameMethodHeader(ctx.Identifier().getText(), null);
    ParametersContext parameters = ctx.parameters();
    if(parameters != null) {
      visitParameters(parameters).accept(result);
    }
    return result;
  }
  
  @Override
  public Consumer<MethodHeader> visitParameters(ParametersContext ctx) {
    return visitParameterList(ctx.parameterList());
  }
  
  @Override
  public Consumer<MethodHeader> visitParameterList(ParameterListContext ctx) {
    return h -> {
      if(ctx != null) {
        List<ParameterContext> parameters = ctx.parameter();
        if(parameters != null) {
          parameters.stream().forEach(p -> h.addFormalParameter(visitParameter(p)));
        }
      }
    }
    ;
  }
  
  @Override
  public FormalParameter visitParameter(ParameterContext ctx) {
    return new FormalParameter(ctx.Identifier().getText(), visitType(ctx.type()));
  }
  
  @Override
  public TypeReference visitReturnType(ReturnTypeContext ctx) {
    return visitType(ctx.type());
  }
  
  @Override
  public Consumer<NormalMethod> visitAbstractImplementation(AbstractImplementationContext ctx) {
    return nm -> {
      nm.addModifier(new Abstract());
    };
  }  

  @Override
  public Consumer<NormalMethod> visitNativeImplementation(NativeImplementationContext ctx) {
    return nm -> {
      nm.addModifier(new Native());
    };
  }
  
  @Override
  public Modifier visitAbstractModifier(AbstractModifierContext ctx) {
    return new Abstract();
  }

  @Override
  public Consumer<NormalMethod> visitBlockImplementation(BlockImplementationContext ctx) {
    return nm -> nm.setImplementation(new RegularImplementation(visitBlock(ctx.block())));
  }
  
  @Override
  public Consumer<NormalMethod> visitExprImplementation(ExprImplementationContext ctx) {
    return nm -> nm.setImplementation(new ExpressionImplementation((Expression) visit(ctx.expression())));
  }
  
  @Override
  public Block visitBlock(BlockContext ctx) {
    Block block = new Block();
    for(StatementContext sctx: ctx.statement()) {
      block.addStatement((Statement) visit(sctx));
    }
    return block;
  }
  
  @Override
  public StatementExpression visitExpressionStatement(ExpressionStatementContext ctx) {
    return new StatementExpression((Expression) visit(ctx.expression()));
  }
  
  @Override
  public Expression visitLiteralExpression(LiteralExpressionContext ctx) {
    return (Expression)visit(ctx.literal());
  }
  
  @Override
  public Object visitIntegerLiteral(IntegerLiteralContext ctx) {
    return (Expression)visit(ctx.integerNumberLiteral());
  }
  
  @Override
  public Expression visitNullExpression(NullExpressionContext ctx) {
    return new NullLiteral();
  }
  
  @Override
  public Expression visitTrueLiteral(TrueLiteralContext ctx) {
    return createLiteral(ctx, "boolean");
  }
  
  @Override
  public Expression visitFalseLiteral(FalseLiteralContext ctx) {
    return createLiteral(ctx, "boolean");
  }

  protected RegularLiteral createLiteral(ParseTree ctx, String name) {
    return new RegularLiteral(view().primitiveTypeReference(name), ctx.getText());
  }
  
  
  @Override
  public Expression visitIntegerNumberLiteral(IntegerNumberLiteralContext ctx) {
    return createIntegerLiteral(ctx);
  }

  protected Expression createIntegerLiteral(ParseTree ctx) {
    String text = ctx.getText();
    String typeName = "int";
    if(text.endsWith("l") || text.endsWith("L")) {
      typeName = "long";
    }
    return createLiteral(ctx, typeName);
  }
  
  @Override
  public Expression visitFloatingPointLiteral(FloatingPointLiteralContext ctx) {
    String text = ctx.getText();
    String typeName = "float";
    if(text.endsWith("d") || text.endsWith("D")) {
      typeName = "double";
    }
    return createLiteral(ctx, typeName);
  }
  
  @Override
  public Expression visitCharacterLiteral(CharacterLiteralContext ctx) {
    return createLiteral(ctx, "char");
  }
  
  @Override
  public Expression visitStringLiteral(StringLiteralContext ctx) {
    return new RegularLiteral(jlo().createTypeReference("java.lang.String"), ctx.getText());
  }
  
  @Override
  public Expression visitParExpression(ParExpressionContext ctx) {
    return new ParExpression((Expression) visit(ctx.expression()));
  }
  
  @Override
  public Expression visitSelfExpression(SelfExpressionContext ctx) {
    return new ThisLiteral();
  }
  
  @Override
  public Expression visitIdentifierExpression(IdentifierExpressionContext ctx) {
    return new NameExpression(ctx.getText());
  }
  
  @Override
  public Expression visitSelfCallExpression(SelfCallExpressionContext ctx) {
    MethodInvocation result = jlo().plugin(ExpressionFactory.class).createInvocation(ctx.name.getText(), null);
    for(Expression argument: visitArguments(ctx.args)) {
      result.addArgument(argument);
    }
    return result;
  }
  
  @Override
  public CrossReferenceTarget visitSuperExpression(SuperExpressionContext ctx) {
    return new SuperTarget();
  }
  
  @Override
  public Expression visitQualifiedCallExpression(QualifiedCallExpressionContext ctx) {
    Expression target = (Expression) visit(ctx.expression());
    Expression result;
    if(ctx.args != null) {
      result = expressionFactory().createInvocation(ctx.name.getText(), target);
      for(Expression argument: visitArguments(ctx.args)) {
        ((MethodInvocation)result).addArgument(argument);
      }
    } else {
      result = new NameExpression(ctx.name.getText(),target);
    }
    return result;
  }
  
  @Override
  public List<Expression> visitArguments(ArgumentsContext ctx) {
    List<Expression> result = new ArrayList<>();
    for(ExpressionContext c: ctx.expression()) {
      result.add((Expression) visit(c));
    }
    return result;
  }
  
  @Override
  public Expression visitExponentiationExpression(ExponentiationExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }

  @Override
  public Expression visitHighPriorityNumbericalExpression(HighPriorityNumbericalExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitLowPriorityNumbericalExpression(LowPriorityNumbericalExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitShiftExpression(ShiftExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitEqualityExpression(EqualityExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitOrderExpression(OrderExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitAndExpression(AndExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Expression visitOrExpression(OrExpressionContext ctx) {
    MethodInvocation result = expressionFactory().createInfixOperatorInvocation(ctx.op.getText(), (CrossReferenceTarget) visit(ctx.left));
    result.addArgument((Expression) visit(ctx.right));
    return result;
  }
  
  @Override
  public Subobject visitSubobject(SubobjectContext ctx) {
    Subobject result = new Subobject(ctx.Identifier().getText(), visitType(ctx.type()));
    ClassBody body = new ClassBody();
    ClassBodyContext subobjectBody = ctx.classBody();
    if(subobjectBody != null) {
      visitClassBody(subobjectBody).accept(body);
      result.setBody(body);
    }
    return result;
  }

  @Override
  public MemberVariableDeclarator visitMemberField(MemberFieldContext ctx) {
    MemberVariableDeclarator result = new MemberVariableDeclarator(visitType(ctx.type()));
    result.add(new VariableDeclaration(ctx.Identifier().getText()));
    return result;
  }
  
  @Override
  public ReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
    return new ReturnStatement((Expression) visit(ctx.expression()));
  }
  
  @Override
  public Object visitAssignmentStatement(AssignmentStatementContext ctx) {
    return new StatementExpression(new AssignmentExpression((Expression)visit(ctx.var), (Expression) visit(ctx.val)));
  }
  
  @Override
  public Modifier visitInitModifier(InitModifierContext ctx) {
    return new Constructor();
  }
}
