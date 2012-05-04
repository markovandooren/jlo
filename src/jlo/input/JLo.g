grammar JLo;
options {
  backtrack=true; 
  memoize=true;
  output=AST;
  superClass = ChameleonParser;
}

import JLoP,JLoL;
@header {
package jlo.input;

import chameleon.exception.ModelException;
import chameleon.exception.ChameleonProgrammerException;

import chameleon.core.lookup.LookupStrategyFactory;

import chameleon.core.document.Document;

import chameleon.oo.member.SimpleNameDeclarationWithParametersSignature;
import chameleon.oo.member.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.CompositeQualifiedName;
import chameleon.core.declaration.TargetDeclaration;

import chameleon.core.element.Element;

import chameleon.oo.expression.Expression;
import chameleon.oo.expression.MethodInvocation;
import chameleon.oo.expression.Literal;
import chameleon.oo.expression.Assignable;
import chameleon.oo.expression.NamedTarget;
import chameleon.oo.expression.NamedTargetExpression;
import chameleon.core.reference.CrossReferenceTarget;
import chameleon.oo.expression.TargetedExpression;
import chameleon.oo.expression.VariableReference;

import chameleon.core.language.Language;

import chameleon.oo.member.Member;

import chameleon.oo.method.Method;
import chameleon.oo.method.MethodHeader;
import chameleon.oo.method.SimpleNameMethodHeader;
import chameleon.oo.method.Implementation;
import chameleon.oo.method.RegularImplementation;

import chameleon.oo.method.exception.ExceptionClause;
import chameleon.oo.method.exception.TypeExceptionDeclaration;

import chameleon.core.modifier.Modifier;

import chameleon.core.namespace.Namespace;
import chameleon.core.namespace.RootNamespace;
import chameleon.core.namespace.NamespaceReference;

import chameleon.core.namespacedeclaration.NamespaceDeclaration;
import chameleon.core.namespacedeclaration.Import;
import chameleon.core.namespacedeclaration.DemandImport;

import chameleon.core.reference.CrossReference;

import chameleon.oo.statement.Block;
import chameleon.oo.statement.Statement;

import chameleon.oo.modifier.AnnotationModifier;

import chameleon.oo.namespacedeclaration.TypeImport;

import chameleon.oo.plugin.ObjectOrientedFactory;

import chameleon.oo.type.ClassBody;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.ClassWithBody;
import chameleon.oo.type.ParameterBlock;

import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.generics.FormalTypeParameter;
import chameleon.oo.type.generics.ActualTypeArgument;
import chameleon.oo.type.generics.BasicTypeArgument;
import chameleon.oo.type.generics.TypeConstraint;
import chameleon.oo.type.generics.ExtendsConstraint;
import chameleon.oo.type.generics.ExtendsWildcard;
import chameleon.oo.type.generics.SuperWildcard;
import chameleon.oo.type.inheritance.SubtypeRelation;

import chameleon.oo.variable.Variable;
import chameleon.oo.variable.FormalParameter;
import chameleon.oo.variable.VariableDeclaration;
import chameleon.oo.variable.VariableDeclarator;

import chameleon.input.InputProcessor;
import chameleon.input.Position2D;

import chameleon.support.expression.RegularLiteral;
import chameleon.support.expression.NullLiteral;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.ConditionalExpression;
import chameleon.support.expression.ConditionalAndExpression;
import chameleon.support.expression.ConditionalOrExpression;
import chameleon.support.expression.InstanceofExpression;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.expression.FilledArrayIndex;
import chameleon.support.expression.EmptyArrayIndex;
import chameleon.support.expression.ArrayIndex;
import chameleon.support.expression.ClassCastExpression;
import chameleon.support.expression.SuperTarget;

import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import chameleon.support.member.simplename.operator.prefix.PrefixOperatorInvocation;
import chameleon.support.member.simplename.operator.postfix.PostfixOperatorInvocation;
import chameleon.support.member.simplename.method.RegularMethodInvocation;

import chameleon.support.modifier.Abstract;
import chameleon.support.modifier.Final;
import chameleon.support.modifier.Private;
import chameleon.support.modifier.Protected;
import chameleon.support.modifier.Public;
import chameleon.support.modifier.Static;
import chameleon.support.modifier.Native;
import chameleon.support.modifier.Enum;
import chameleon.support.modifier.Interface;

import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.LocalClassStatement;
import chameleon.support.statement.AssertStatement;
import chameleon.support.statement.IfThenElseStatement;
import chameleon.support.statement.ForStatement;
import chameleon.support.statement.ForControl;
import chameleon.support.statement.ForInit;
import chameleon.support.statement.SimpleForControl;
import chameleon.support.statement.EnhancedForControl;
import chameleon.support.statement.StatementExprList;
import chameleon.support.statement.TryStatement;
import chameleon.support.statement.CatchClause;
import chameleon.support.statement.FinallyClause;
import chameleon.support.statement.DoStatement;
import chameleon.support.statement.WhileStatement;
import chameleon.support.statement.SwitchStatement;
import chameleon.support.statement.SwitchCase;
import chameleon.support.statement.SwitchLabel;
import chameleon.support.statement.CaseLabel;
import chameleon.support.statement.DefaultLabel;
import chameleon.support.statement.EnumLabel;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.statement.BreakStatement;
import chameleon.support.statement.ContinueStatement;
import chameleon.support.statement.SynchronizedStatement;
import chameleon.support.statement.EmptyStatement;
import chameleon.support.statement.LabeledStatement;

import chameleon.support.type.EmptyTypeElement;
import chameleon.support.type.StaticInitializer;

import chameleon.support.variable.LocalVariableDeclarator;

import chameleon.support.input.ChameleonParser;

import chameleon.util.Util;

import jnome.core.expression.ArrayInitializer;
import jnome.core.expression.ClassLiteral;
import jnome.core.expression.ArrayAccessExpression;
import jnome.core.expression.ArrayCreationExpression;
import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.ThisConstructorDelegation;
import jnome.core.expression.invocation.SuperConstructorDelegation;

import jnome.core.imports.SingleStaticImport;

import jnome.core.language.Java;

import jnome.core.modifier.StrictFP;
import jnome.core.modifier.Transient;
import jnome.core.modifier.Volatile;
import jnome.core.modifier.Synchronized;
import jnome.core.modifier.JavaConstructor;
import jnome.core.modifier.Implements;
import jnome.core.modifier.AnnotationType;

import jnome.core.type.JavaTypeReference;
import jnome.core.type.ArrayTypeReference;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaIntersectionTypeReference;
import jnome.core.type.PureWildcard;

import jnome.core.enumeration.EnumConstant;

import jnome.core.variable.JavaVariableDeclaration;
import jnome.core.variable.MultiFormalParameter;

import jnome.input.JavaFactory;

import jlo.model.component.ComponentRelation;
import jlo.model.component.ConfigurationBlock;
import jlo.model.component.ConfigurationClause;
import jlo.model.component.RenamingClause;
import jlo.model.component.OverridesClause;
import jlo.model.expression.SubobjectConstructorCall;
import jlo.model.expression.ComponentParameterCall;
import jlo.model.expression.OuterTarget;
import jlo.model.expression.RootTarget;
import jlo.model.component.ComponentParameter;
import jlo.model.component.FormalComponentParameter;
import jlo.model.component.SingleFormalComponentParameter;
import jlo.model.component.MultiFormalComponentParameter;
import jlo.model.component.ActualComponentArgument;
import jlo.model.component.SingleActualComponentArgument;
import jlo.model.component.MultiActualComponentArgument;
import jlo.model.component.ComponentParameterTypeReference;
import jlo.model.component.ComponentNameActualArgument;
import jlo.model.component.ParameterReferenceActualArgument;
import jlo.model.component.Export;
import jlo.model.component.Overrides;
import jlo.model.component.InstantiatedMemberSubobjectParameter;

import java.util.List;
import java.util.ArrayList;
}
@members{

  @Override
  public void setLanguage(Language language) {
    gJLoP.setLanguage(language);
  }
  
  @Override
  public Language language() {
    return gJLoP.language();
  }
  
  public Document getDocument() {
    return gJLoP.getDocument();
  }
	   
  public void setDocument(Document compilationUnit) {
    gJLoP.setDocument(compilationUnit);
    gJavaP.setDocument(compilationUnit);
    super.setDocument(compilationUnit);
  }
  
  public Namespace getDefaultNamespace() {
    return gJLoP.getDefaultNamespace();
  }
  
    public void setFactory(JavaFactory factory) {
    gJLoP.setFactory(factory);
  }
  
  public JavaFactory factory() {
    return gJLoP.factory();
  }

  public JavaTypeReference typeRef(String qn) {
    return gJLoP.typeRef(qn);
  }

  public JavaTypeReference createTypeReference(CrossReference<? extends TargetDeclaration> target, String name) {
    return gJLoP.createTypeReference(target,name);
  }
  
  public JavaTypeReference createTypeReference(CrossReference<? extends TargetDeclaration> target, SimpleNameSignature signature) {
    return gJLoP.createTypeReference(target,signature);
  }

  public JavaTypeReference createTypeReference(NamedTarget target) {
    return gJLoP.createTypeReference(target);
  }

  public CrossReferenceTarget cloneTarget(CrossReferenceTarget target) {
    return gJLoP.cloneTarget(target);
  }
  
  public RegularMethodInvocation invocation(String name, CrossReferenceTarget target) {
    return gJLoP.invocation(name,target);
  }
}

dummy returns [Object element]
   	:	
   	;