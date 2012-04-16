// $ANTLR 3.3 Nov 30, 2010 12:45:30 /Users/marko/git/jlo/src/jlo/input/JLo.g 2012-02-17 16:35:23

package jlo.input;

import chameleon.exception.ModelException;
import chameleon.exception.ChameleonProgrammerException;

import chameleon.core.lookup.LookupStrategyFactory;


import chameleon.oo.member.SimpleNameDeclarationWithParametersSignature;
import chameleon.oo.member.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.CompositeQualifiedName;
import chameleon.core.declaration.TargetDeclaration;
import chameleon.core.document.Document;

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

import chameleon.core.namespacedeclaration.DemandImport;
import chameleon.core.namespacedeclaration.Import;
import chameleon.core.namespacedeclaration.NamespaceDeclaration;

import chameleon.core.reference.CrossReference;

import chameleon.oo.statement.Block;
import chameleon.oo.statement.Statement;

import chameleon.oo.modifier.AnnotationModifier;

import chameleon.oo.namespacedeclaration.TypeImport;

import chameleon.oo.type.ClassBody;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeWithBody;
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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class JLoParser extends ChameleonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "Identifier", "ENUM", "FloatingPointLiteral", "CharacterLiteral", "StringLiteral", "HexLiteral", "OctalLiteral", "DecimalLiteral", "ASSERT", "Export", "Connector", "Connect", "Name", "Overrides", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "Refines", "Letter", "JavaIDDigit", "WS", "COMMENT", "LINE_COMMENT", "'package'", "';'", "'import'", "'static'", "'.'", "'*'", "'public'", "'protected'", "'private'", "'abstract'", "'final'", "'strictfp'", "'class'", "'extends'", "'implements'", "'<'", "','", "'>'", "'&'", "'{'", "'}'", "'interface'", "'void'", "'['", "']'", "'throws'", "'='", "'native'", "'synchronized'", "'transient'", "'volatile'", "'boolean'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'", "'?'", "'super'", "'('", "')'", "'...'", "'this'", "'null'", "'true'", "'false'", "'@'", "'default'", "':'", "'if'", "'else'", "'for'", "'while'", "'do'", "'try'", "'finally'", "'switch'", "'return'", "'throw'", "'break'", "'continue'", "'catch'", "'case'", "'+='", "'-='", "'*='", "'/='", "'&='", "'|='", "'^='", "'%='", "'||'", "'&&'", "'|'", "'^'", "'=='", "'!='", "'instanceof'", "'+'", "'-'", "'/'", "'%'", "'++'", "'--'", "'~'", "'!'", "'new'", "'subobject'", "'alias'", "'#'", "'outer'", "'rooot'", "'->'"
    };
    public static final int EOF=-1;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__50=50;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__59=59;
    public static final int T__60=60;
    public static final int T__61=61;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__90=90;
    public static final int T__91=91;
    public static final int T__92=92;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__99=99;
    public static final int T__100=100;
    public static final int T__101=101;
    public static final int T__102=102;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__110=110;
    public static final int T__111=111;
    public static final int T__112=112;
    public static final int T__113=113;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__120=120;
    public static final int T__121=121;
    public static final int T__122=122;
    public static final int T__123=123;
    public static final int T__124=124;
    public static final int T__125=125;
    public static final int Identifier=4;
    public static final int ENUM=5;
    public static final int FloatingPointLiteral=6;
    public static final int CharacterLiteral=7;
    public static final int StringLiteral=8;
    public static final int HexLiteral=9;
    public static final int OctalLiteral=10;
    public static final int DecimalLiteral=11;
    public static final int ASSERT=12;
    public static final int Export=13;
    public static final int Connector=14;
    public static final int Connect=15;
    public static final int Name=16;
    public static final int Overrides=17;
    public static final int HexDigit=18;
    public static final int IntegerTypeSuffix=19;
    public static final int Exponent=20;
    public static final int FloatTypeSuffix=21;
    public static final int EscapeSequence=22;
    public static final int UnicodeEscape=23;
    public static final int OctalEscape=24;
    public static final int Refines=25;
    public static final int Letter=26;
    public static final int JavaIDDigit=27;
    public static final int WS=28;
    public static final int COMMENT=29;
    public static final int LINE_COMMENT=30;

    // delegates
    public JLo_JLoP_JavaP gJavaP;
    public JLo_JLoP gJLoP;
    // delegators


        public JLoParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public JLoParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[465+1];
             
            gJLoP = new JLo_JLoP(input, state, this);         
            gJavaP = gJLoP.gJavaP;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
        gJLoP.setTreeAdaptor(this.adaptor);
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return JLoParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/marko/git/jlo/src/jlo/input/JLo.g"; }



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


    public static class dummy_return extends ParserRuleReturnScope {
        public Object element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dummy"
    // /Users/marko/git/jlo/src/jlo/input/JLo.g:286:1: dummy returns [Object element] : ;
    public final JLoParser.dummy_return dummy() throws RecognitionException {
        JLoParser.dummy_return retval = new JLoParser.dummy_return();
        retval.start = input.LT(1);
        int dummy_StartIndex = input.index();
        Object root_0 = null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 469) ) { return retval; }
            // /Users/marko/git/jlo/src/jlo/input/JLo.g:287:5: ()
            // /Users/marko/git/jlo/src/jlo/input/JLo.g:288:5: 
            {
            root_0 = (Object)adaptor.nil();

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 469, dummy_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "dummy"

    // Delegated rules
    public JLo_JLoP_JavaP.normalInterfaceDeclaration_return normalInterfaceDeclaration() throws RecognitionException { return gJavaP.normalInterfaceDeclaration(); }
    public JLo_JLoP.configurationClause_return configurationClause() throws RecognitionException { return gJLoP.configurationClause(); }
    public JLo_JLoP_JavaP.unaryExpressionNotPlusMinus_return unaryExpressionNotPlusMinus() throws RecognitionException { return gJavaP.unaryExpressionNotPlusMinus(); }
    public JLo_JLoP_JavaP.switchBlockStatementGroups_return switchBlockStatementGroups() throws RecognitionException { return gJavaP.switchBlockStatementGroups(); }
    public JLo_JLoP_JavaP.catchClause_return catchClause() throws RecognitionException { return gJavaP.catchClause(); }
    public JLo_JLoP_JavaP.andExpression_return andExpression() throws RecognitionException { return gJavaP.andExpression(); }
    public JLo_JLoP_JavaP.moreidentifierRuleSuffixRubbish_return moreidentifierRuleSuffixRubbish() throws RecognitionException { return gJavaP.moreidentifierRuleSuffixRubbish(); }
    public JLo_JLoP_JavaP.typeBound_return typeBound() throws RecognitionException { return gJavaP.typeBound(); }
    public JLo_JLoP_JavaP.methodDeclaratorRest_return methodDeclaratorRest() throws RecognitionException { return gJavaP.methodDeclaratorRest(); }
    public JLo_JLoP_JavaP.annotationTypeElementDeclaration_return annotationTypeElementDeclaration() throws RecognitionException { return gJavaP.annotationTypeElementDeclaration(); }
    public JLo_JLoP_JavaP.voidMethodDeclaration_return voidMethodDeclaration() throws RecognitionException { return gJavaP.voidMethodDeclaration(); }
    public JLo_JLoP_JavaP.unaryExpression_return unaryExpression() throws RecognitionException { return gJavaP.unaryExpression(); }
    public JLo_JLoP_JavaP.classOrInterfaceModifiers_return classOrInterfaceModifiers() throws RecognitionException { return gJavaP.classOrInterfaceModifiers(); }
    public JLo_JLoP_JavaP.annotationConstantRest_return annotationConstantRest(TypeReference type) throws RecognitionException { return gJavaP.annotationConstantRest(type); }
    public JLo_JLoP_JavaP.annotation_return annotation() throws RecognitionException { return gJavaP.annotation(); }
    public JLo_JLoP_JavaP.annotationMethodOrConstantRest_return annotationMethodOrConstantRest(TypeReference type) throws RecognitionException { return gJavaP.annotationMethodOrConstantRest(type); }
    public JLo_JLoP_JavaP.classBodyDeclaration_return classBodyDeclaration() throws RecognitionException { return gJavaP.classBodyDeclaration(); }
    public JLo_JLoP_JavaP.primary_return primary() throws RecognitionException { return gJavaP.primary(); }
    public JLo_JLoP_JavaP.enumConstant_return enumConstant() throws RecognitionException { return gJavaP.enumConstant(); }
    public JLo_JLoP_JavaP.variableDeclarator_return variableDeclarator() throws RecognitionException { return gJavaP.variableDeclarator(); }
    public JLo_JLoP_JavaP.innerCreator_return innerCreator() throws RecognitionException { return gJavaP.innerCreator(); }
    public JLo_JLoP_JavaP.voidMethodDeclaratorRest_return voidMethodDeclaratorRest() throws RecognitionException { return gJavaP.voidMethodDeclaratorRest(); }
    public JLo_JLoP_JavaP.integerLiteral_return integerLiteral() throws RecognitionException { return gJavaP.integerLiteral(); }
    public JLo_JLoP_JavaP.elementValueArrayInitializer_return elementValueArrayInitializer() throws RecognitionException { return gJavaP.elementValueArrayInitializer(); }
    public JLo_JLoP_JavaP.variableModifier_return variableModifier() throws RecognitionException { return gJavaP.variableModifier(); }
    public JLo_JLoP_JavaP.variableDeclaratorId_return variableDeclaratorId() throws RecognitionException { return gJavaP.variableDeclaratorId(); }
    public JLo_JLoP_JavaP.modifier_return modifier() throws RecognitionException { return gJavaP.modifier(); }
    public JLo_JLoP_JavaP.localVariableDeclaration_return localVariableDeclaration() throws RecognitionException { return gJavaP.localVariableDeclaration(); }
    public JLo_JLoP_JavaP.classOrInterfaceModifier_return classOrInterfaceModifier() throws RecognitionException { return gJavaP.classOrInterfaceModifier(); }
    public JLo_JLoP_JavaP.additiveExpression_return additiveExpression() throws RecognitionException { return gJavaP.additiveExpression(); }
    public JLo_JLoP.expression_return expression() throws RecognitionException { return gJLoP.expression(); }
    public JLo_JLoP_JavaP.enhancedForControl_return enhancedForControl() throws RecognitionException { return gJavaP.enhancedForControl(); }
    public JLo_JLoP_JavaP.blockStatement_return blockStatement() throws RecognitionException { return gJavaP.blockStatement(); }
    public JLo_JLoP_JavaP.voidInterfaceMethodDeclaration_return voidInterfaceMethodDeclaration() throws RecognitionException { return gJavaP.voidInterfaceMethodDeclaration(); }
    public JLo_JLoP.memberName_return memberName() throws RecognitionException { return gJLoP.memberName(); }
    public JLo_JLoP_JavaP.qualifiedNameList_return qualifiedNameList() throws RecognitionException { return gJavaP.qualifiedNameList(); }
    public JLo_JLoP_JavaP.createdName_return createdName() throws RecognitionException { return gJavaP.createdName(); }
    public JLo_JLoP_JavaP.relationalOp_return relationalOp() throws RecognitionException { return gJavaP.relationalOp(); }
    public JLo_JLoP_JavaP.enumConstantName_return enumConstantName() throws RecognitionException { return gJavaP.enumConstantName(); }
    public JLo_JLoP.overridesClause_return overridesClause() throws RecognitionException { return gJLoP.overridesClause(); }
    public JLo_JLoP_JavaP.catches_return catches() throws RecognitionException { return gJavaP.catches(); }
    public JLo_JLoP_JavaP.parExpression_return parExpression() throws RecognitionException { return gJavaP.parExpression(); }
    public JLo_JLoP.subobjectParameter_return subobjectParameter() throws RecognitionException { return gJLoP.subobjectParameter(); }
    public JLo_JLoP_JavaP.typeName_return typeName() throws RecognitionException { return gJavaP.typeName(); }
    public JLo_JLoP_JavaP.constantExpression_return constantExpression() throws RecognitionException { return gJavaP.constantExpression(); }
    public JLo_JLoP_JavaP.interfaceBodyDeclaration_return interfaceBodyDeclaration() throws RecognitionException { return gJavaP.interfaceBodyDeclaration(); }
    public JLo_JLoP_JavaP.superSuffix_return superSuffix() throws RecognitionException { return gJavaP.superSuffix(); }
    public JLo_JLoP_JavaP.arrayAccessSuffixRubbish_return arrayAccessSuffixRubbish() throws RecognitionException { return gJavaP.arrayAccessSuffixRubbish(); }
    public JLo_JLoP_JavaP.voidInterfaceMethodDeclaratorRest_return voidInterfaceMethodDeclaratorRest() throws RecognitionException { return gJavaP.voidInterfaceMethodDeclaratorRest(); }
    public JLo_JLoP.connector_return connector() throws RecognitionException { return gJLoP.connector(); }
    public JLo_JLoP_JavaP.annotationTypeBody_return annotationTypeBody() throws RecognitionException { return gJavaP.annotationTypeBody(); }
    public JLo_JLoP.signature_return signature() throws RecognitionException { return gJLoP.signature(); }
    public JLo_JLoP_JavaP.switchCase_return switchCase() throws RecognitionException { return gJavaP.switchCase(); }
    public JLo_JLoP_JavaP.annotations_return annotations() throws RecognitionException { return gJavaP.annotations(); }
    public JLo_JLoP.singleSubobjectArgument_return singleSubobjectArgument() throws RecognitionException { return gJLoP.singleSubobjectArgument(); }
    public JLo_JLoP_JavaP.interfaceGenericMethodDecl_return interfaceGenericMethodDecl() throws RecognitionException { return gJavaP.interfaceGenericMethodDecl(); }
    public JLo_JLoP_JavaP.enumDeclaration_return enumDeclaration() throws RecognitionException { return gJavaP.enumDeclaration(); }
    public JLo_JLoP_JavaP.instanceOfExpression_return instanceOfExpression() throws RecognitionException { return gJavaP.instanceOfExpression(); }
    public JLo_JLoP_JavaP.typeArguments_return typeArguments() throws RecognitionException { return gJavaP.typeArguments(); }
    public JLo_JLoP_JavaP.constantDeclarator_return constantDeclarator() throws RecognitionException { return gJavaP.constantDeclarator(); }
    public JLo_JLoP_JavaP.shiftExpression_return shiftExpression() throws RecognitionException { return gJavaP.shiftExpression(); }
    public JLo_JLoP_JavaP.primitiveType_return primitiveType() throws RecognitionException { return gJavaP.primitiveType(); }
    public JLo_JLoP_JavaP.genericMethodOrConstructorRest_return genericMethodOrConstructorRest() throws RecognitionException { return gJavaP.genericMethodOrConstructorRest(); }
    public JLo_JLoP_JavaP.fieldDeclaration_return fieldDeclaration() throws RecognitionException { return gJavaP.fieldDeclaration(); }
    public JLo_JLoP_JavaP.forUpdate_return forUpdate() throws RecognitionException { return gJavaP.forUpdate(); }
    public JLo_JLoP_JavaP.interfaceMemberDecl_return interfaceMemberDecl() throws RecognitionException { return gJavaP.interfaceMemberDecl(); }
    public JLo_JLoP_JavaP.nonWildcardTypeArguments_return nonWildcardTypeArguments() throws RecognitionException { return gJavaP.nonWildcardTypeArguments(); }
    public JLo_JLoP_JavaP.annotationName_return annotationName() throws RecognitionException { return gJavaP.annotationName(); }
    public JLo_JLoP_JavaP.variableInitializer_return variableInitializer() throws RecognitionException { return gJavaP.variableInitializer(); }
    public JLo_JLoP_JavaP.statement_return statement() throws RecognitionException { return gJavaP.statement(); }
    public JLo_JLoP_JavaP.switchLabel_return switchLabel() throws RecognitionException { return gJavaP.switchLabel(); }
    public JLo_JLoP_JavaP.formalParameter_return formalParameter() throws RecognitionException { return gJavaP.formalParameter(); }
    public JLo_JLoP.fqn_return fqn() throws RecognitionException { return gJLoP.fqn(); }
    public JLo_JLoP.map_return map() throws RecognitionException { return gJLoP.map(); }
    public JLo_JLoP_JavaP.inclusiveOrExpression_return inclusiveOrExpression() throws RecognitionException { return gJavaP.inclusiveOrExpression(); }
    public JLo_JLoP_JavaP.conditionalAndExpression_return conditionalAndExpression() throws RecognitionException { return gJavaP.conditionalAndExpression(); }
    public JLo_JLoP_JavaP.formalParameterDecls_return formalParameterDecls() throws RecognitionException { return gJavaP.formalParameterDecls(); }
    public JLo_JLoP.memberDecl_return memberDecl() throws RecognitionException { return gJLoP.memberDecl(); }
    public JLo_JLoP_JavaP.typeList_return typeList() throws RecognitionException { return gJavaP.typeList(); }
    public JLo_JLoP_JavaP.methodDeclaration_return methodDeclaration() throws RecognitionException { return gJavaP.methodDeclaration(); }
    public JLo_JLoP_JavaP.qualifiedName_return qualifiedName() throws RecognitionException { return gJavaP.qualifiedName(); }
    public JLo_JLoP_JavaP.packageDeclaration_return packageDeclaration() throws RecognitionException { return gJavaP.packageDeclaration(); }
    public JLo_JLoP_JavaP.classDeclaration_return classDeclaration() throws RecognitionException { return gJavaP.classDeclaration(); }
    public JLo_JLoP.multiSubobjectArgument_return multiSubobjectArgument() throws RecognitionException { return gJLoP.multiSubobjectArgument(); }
    public JLo_JLoP_JavaP.variableDeclarators_return variableDeclarators() throws RecognitionException { return gJavaP.variableDeclarators(); }
    public JLo_JLoP_JavaP.enumConstants_return enumConstants() throws RecognitionException { return gJavaP.enumConstants(); }
    public JLo_JLoP_JavaP.voidType_return voidType() throws RecognitionException { return gJavaP.voidType(); }
    public JLo_JLoP_JavaP.argumentsSuffixRubbish_return argumentsSuffixRubbish() throws RecognitionException { return gJavaP.argumentsSuffixRubbish(); }
    public JLo_JLoP_JavaP.annotationTypeDeclaration_return annotationTypeDeclaration() throws RecognitionException { return gJavaP.annotationTypeDeclaration(); }
    public JLo_JLoP_JavaP.identifierSuffixRubbush_return identifierSuffixRubbush() throws RecognitionException { return gJavaP.identifierSuffixRubbush(); }
    public JLo_JLoP_JavaP.formalParameters_return formalParameters() throws RecognitionException { return gJavaP.formalParameters(); }
    public JLo_JLoP_JavaP.genericMethodOrConstructorDecl_return genericMethodOrConstructorDecl() throws RecognitionException { return gJavaP.genericMethodOrConstructorDecl(); }
    public JLo_JLoP.exportDeclaration_return exportDeclaration() throws RecognitionException { return gJLoP.exportDeclaration(); }
    public JLo_JLoP_JavaP.classBody_return classBody() throws RecognitionException { return gJavaP.classBody(); }
    public JLo_JLoP.singleSubobjectParameter_return singleSubobjectParameter() throws RecognitionException { return gJLoP.singleSubobjectParameter(); }
    public JLo_JLoP_JavaP.importDeclaration_return importDeclaration() throws RecognitionException { return gJavaP.importDeclaration(); }
    public JLo_JLoP_JavaP.constructorDeclaratorRest_return constructorDeclaratorRest() throws RecognitionException { return gJavaP.constructorDeclaratorRest(); }
    public JLo_JLoP.configurationBlock_return configurationBlock() throws RecognitionException { return gJLoP.configurationBlock(); }
    public JLo_JLoP_JavaP.enumBodyDeclarations_return enumBodyDeclarations() throws RecognitionException { return gJavaP.enumBodyDeclarations(); }
    public JLo_JLoP_JavaP.createClassHereBecauseANTLRisAnnoying_return createClassHereBecauseANTLRisAnnoying() throws RecognitionException { return gJavaP.createClassHereBecauseANTLRisAnnoying(); }
    public JLo_JLoP_JavaP.creator_return creator() throws RecognitionException { return gJavaP.creator(); }
    public JLo_JLoP_JavaP.type_return type() throws RecognitionException { return gJavaP.type(); }
    public JLo_JLoP_JavaP.nameAndParams_return nameAndParams() throws RecognitionException { return gJavaP.nameAndParams(); }
    public JLo_JLoP_JavaP.conditionalOrExpression_return conditionalOrExpression() throws RecognitionException { return gJavaP.conditionalOrExpression(); }
    public JLo_JLoP_JavaP.memberDeclaration_return memberDeclaration() throws RecognitionException { return gJavaP.memberDeclaration(); }
    public JLo_JLoP_JavaP.exclusiveOrExpression_return exclusiveOrExpression() throws RecognitionException { return gJavaP.exclusiveOrExpression(); }
    public JLo_JLoP_JavaP.interfaceBody_return interfaceBody() throws RecognitionException { return gJavaP.interfaceBody(); }
    public JLo_JLoP_JavaP.defaultValue_return defaultValue() throws RecognitionException { return gJavaP.defaultValue(); }
    public JLo_JLoP_JavaP.classOrInterfaceType_return classOrInterfaceType() throws RecognitionException { return gJavaP.classOrInterfaceType(); }
    public JLo_JLoP_JavaP.forInit_return forInit() throws RecognitionException { return gJavaP.forInit(); }
    public JLo_JLoP_JavaP.classCreatorRest_return classCreatorRest() throws RecognitionException { return gJavaP.classCreatorRest(); }
    public JLo_JLoP_JavaP.relationalExpression_return relationalExpression() throws RecognitionException { return gJavaP.relationalExpression(); }
    public JLo_JLoP_JavaP.typeParameter_return typeParameter() throws RecognitionException { return gJavaP.typeParameter(); }
    public JLo_JLoP_JavaP.localVariableDeclarationStatement_return localVariableDeclarationStatement() throws RecognitionException { return gJavaP.localVariableDeclarationStatement(); }
    public JLo_JLoP.multiSubobjectParameter_return multiSubobjectParameter() throws RecognitionException { return gJLoP.multiSubobjectParameter(); }
    public JLo_JLoP_JavaP.booleanLiteral_return booleanLiteral() throws RecognitionException { return gJavaP.booleanLiteral(); }
    public JLo_JLoP_JavaP.interfaceMethodOrFieldDecl_return interfaceMethodOrFieldDecl() throws RecognitionException { return gJavaP.interfaceMethodOrFieldDecl(); }
    public JLo_JLoP_JavaP.selector_return selector() throws RecognitionException { return gJavaP.selector(); }
    public JLo_JLoP_JavaP.conditionalExpression_return conditionalExpression() throws RecognitionException { return gJavaP.conditionalExpression(); }
    public JLo_JLoP_JavaP.forControl_return forControl() throws RecognitionException { return gJavaP.forControl(); }
    public JLo_JLoP.subobjectArgument_return subobjectArgument() throws RecognitionException { return gJLoP.subobjectArgument(); }
    public JLo_JLoP_JavaP.methodBody_return methodBody() throws RecognitionException { return gJavaP.methodBody(); }
    public JLo_JLoP_JavaP.equalityExpression_return equalityExpression() throws RecognitionException { return gJavaP.equalityExpression(); }
    public JLo_JLoP_JavaP.constructorDeclaration_return constructorDeclaration() throws RecognitionException { return gJavaP.constructorDeclaration(); }
    public JLo_JLoP_JavaP.assignmentOperator_return assignmentOperator() throws RecognitionException { return gJavaP.assignmentOperator(); }
    public JLo_JLoP_JavaP.interfaceMethod_return interfaceMethod() throws RecognitionException { return gJavaP.interfaceMethod(); }
    public JLo_JLoP_JavaP.classOrInterfaceDeclaration_return classOrInterfaceDeclaration() throws RecognitionException { return gJavaP.classOrInterfaceDeclaration(); }
    public JLo_JLoP_JavaP.elementValue_return elementValue() throws RecognitionException { return gJavaP.elementValue(); }
    public JLo_JLoP.subobjectDeclaration_return subobjectDeclaration() throws RecognitionException { return gJLoP.subobjectDeclaration(); }
    public JLo_JLoP.nonTargetPrimary_return nonTargetPrimary() throws RecognitionException { return gJLoP.nonTargetPrimary(); }
    public JLo_JLoP_JavaP.arrayInitializer_return arrayInitializer() throws RecognitionException { return gJavaP.arrayInitializer(); }
    public JLo_JLoP_JavaP.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gJavaP.multiplicativeExpression(); }
    public JLo_JLoP_JavaP.elementValuePairs_return elementValuePairs() throws RecognitionException { return gJavaP.elementValuePairs(); }
    public JLo_JLoP_JavaP.modifiers_return modifiers() throws RecognitionException { return gJavaP.modifiers(); }
    public JLo_JLoP_JavaP.typeParameters_return typeParameters() throws RecognitionException { return gJavaP.typeParameters(); }
    public JLo_JLoP_JavaP.statementExpression_return statementExpression() throws RecognitionException { return gJavaP.statementExpression(); }
    public JLo_JLoP.connection_return connection() throws RecognitionException { return gJLoP.connection(); }
    public JLo_JLoP.identifierRule_return identifierRule() throws RecognitionException { return gJLoP.identifierRule(); }
    public JLo_JLoP_JavaP.annotationTypeElementRest_return annotationTypeElementRest() throws RecognitionException { return gJavaP.annotationTypeElementRest(); }
    public JLo_JLoP_JavaP.block_return block() throws RecognitionException { return gJavaP.block(); }
    public JLo_JLoP_JavaP.elementValuePair_return elementValuePair() throws RecognitionException { return gJavaP.elementValuePair(); }
    public JLo_JLoP_JavaP.interfaceMethodDeclaratorRest_return interfaceMethodDeclaratorRest() throws RecognitionException { return gJavaP.interfaceMethodDeclaratorRest(); }
    public JLo_JLoP_JavaP.typeDeclaration_return typeDeclaration() throws RecognitionException { return gJavaP.typeDeclaration(); }
    public JLo_JLoP.nameParameter_return nameParameter() throws RecognitionException { return gJLoP.nameParameter(); }
    public JLo_JLoP_JavaP.castExpression_return castExpression() throws RecognitionException { return gJavaP.castExpression(); }
    public JLo_JLoP_JavaP.literal_return literal() throws RecognitionException { return gJavaP.literal(); }
    public JLo_JLoP_JavaP.annotationMethodRest_return annotationMethodRest(TypeReference type) throws RecognitionException { return gJavaP.annotationMethodRest(type); }
    public JLo_JLoP_JavaP.variableModifiers_return variableModifiers() throws RecognitionException { return gJavaP.variableModifiers(); }
    public JLo_JLoP_JavaP.normalClassDeclaration_return normalClassDeclaration() throws RecognitionException { return gJavaP.normalClassDeclaration(); }
    public JLo_JLoP_JavaP.shiftOp_return shiftOp() throws RecognitionException { return gJavaP.shiftOp(); }
    public JLo_JLoP_JavaP.expressionList_return expressionList() throws RecognitionException { return gJavaP.expressionList(); }
    public JLo_JLoP_JavaP.interfaceConstant_return interfaceConstant() throws RecognitionException { return gJavaP.interfaceConstant(); }
    public JLo_JLoP_JavaP.enumBody_return enumBody() throws RecognitionException { return gJavaP.enumBody(); }
    public JLo_JLoP_JavaP.interfaceDeclaration_return interfaceDeclaration() throws RecognitionException { return gJavaP.interfaceDeclaration(); }
    public JLo_JLoP_JavaP.explicitGenericInvocation_return explicitGenericInvocation() throws RecognitionException { return gJavaP.explicitGenericInvocation(); }
    public JLo_JLoP_JavaP.constructorBody_return constructorBody() throws RecognitionException { return gJavaP.constructorBody(); }
    public JLo_JLoP_JavaP.typeArgument_return typeArgument() throws RecognitionException { return gJavaP.typeArgument(); }
    public JLo_JLoP_JavaP.arguments_return arguments() throws RecognitionException { return gJavaP.arguments(); }
    public JLo_JLoP_JavaP.explicitConstructorInvocation_return explicitConstructorInvocation() throws RecognitionException { return gJavaP.explicitConstructorInvocation(); }
    public JLo_JLoP_JavaP.compilationUnit_return compilationUnit() throws RecognitionException { return gJavaP.compilationUnit(); }


 

}