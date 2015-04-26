// $ANTLR 3.3 Nov 30, 2010 12:45:30 JLoP.g 2013-09-25 11:38:56

package org.aikodi.jlo.input;

import org.aikodi.chameleon.core.declaration.CompositeQualifiedName;
import org.aikodi.chameleon.core.declaration.QualifiedName;
import org.aikodi.chameleon.core.declaration.Signature;
import org.aikodi.chameleon.core.declaration.SimpleNameSignature;
import org.aikodi.chameleon.core.declaration.TargetDeclaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.core.namespace.Namespace;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NamedTarget;
import org.aikodi.chameleon.oo.expression.TargetedExpression;
import org.aikodi.chameleon.oo.member.SignatureWithParameters;
import org.aikodi.chameleon.oo.type.TypeElement;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.input.ChameleonANTLR3Parser;
import org.aikodi.chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import org.aikodi.chameleon.workspace.View;
import org.aikodi.jlo.model.component.ActualComponentArgument;
import org.aikodi.jlo.model.component.ComponentNameActualArgument;
import org.aikodi.jlo.model.component.ComponentParameter;
import org.aikodi.jlo.model.component.ConfigurationBlock;
import org.aikodi.jlo.model.component.ConfigurationClause;
import org.aikodi.jlo.model.component.Export;
import org.aikodi.jlo.model.component.InstantiatedMemberSubobjectParameter;
import org.aikodi.jlo.model.component.MultiActualComponentArgument;
import org.aikodi.jlo.model.component.MultiFormalComponentParameter;
import org.aikodi.jlo.model.component.Overrides;
import org.aikodi.jlo.model.component.OverridesClause;
import org.aikodi.jlo.model.component.ParameterReferenceActualArgument;
import org.aikodi.jlo.model.component.RenamingClause;
import org.aikodi.jlo.model.component.SingleActualComponentArgument;
import org.aikodi.jlo.model.component.SingleFormalComponentParameter;
import org.aikodi.jlo.model.component.Subobject;
import org.aikodi.jlo.model.expression.ComponentParameterCall;
import org.aikodi.jlo.model.expression.OuterTarget;
import org.aikodi.jlo.model.expression.RootTarget;
import org.aikodi.jlo.model.expression.SubobjectConstructorCall;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.TreeAdaptor;

import be.kuleuven.cs.distrinet.jnome.core.type.JavaTypeReference;
import be.kuleuven.cs.distrinet.jnome.input.Java7Factory;

public class JLo_JLoP extends ChameleonANTLR3Parser<Language> {
    public static final int EOF=-1;
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
    public static final int T__126=126;
    public static final int T__127=127;
    public static final int T__128=128;
    public static final int T__129=129;
    public static final int T__130=130;
    public static final int T__131=131;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int T__136=136;
    public static final int T__137=137;
    public static final int T__138=138;
    public static final int T__139=139;
    public static final int T__140=140;
    public static final int T__141=141;
    public static final int T__142=142;
    public static final int T__143=143;
    public static final int T__144=144;
    public static final int T__145=145;
    public static final int T__146=146;
    public static final int T__147=147;
    public static final int Identifier=4;
    public static final int ENUM=5;
    public static final int FloatingPointLiteral=6;
    public static final int CharacterLiteral=7;
    public static final int StringLiteral=8;
    public static final int IntegerLiteral=9;
    public static final int ASSERT=10;
    public static final int Export=11;
    public static final int Connector=12;
    public static final int Connect=13;
    public static final int Name=14;
    public static final int Overrides=15;
    public static final int DecimalIntegerLiteral=16;
    public static final int HexIntegerLiteral=17;
    public static final int OctalIntegerLiteral=18;
    public static final int BinaryIntegerLiteral=19;
    public static final int DecimalNumeral=20;
    public static final int IntegerTypeSuffix=21;
    public static final int NonZeroDigit=22;
    public static final int Digits=23;
    public static final int Digit=24;
    public static final int OctalNumeral=25;
    public static final int OctalDigits=26;
    public static final int OctalDigit=27;
    public static final int HexNumeral=28;
    public static final int HexDigits=29;
    public static final int HexDigit=30;
    public static final int BinaryNumeral=31;
    public static final int BinaryDigits=32;
    public static final int BinaryDigit=33;
    public static final int DecimalFloatingPointLiteral=34;
    public static final int HexadecimalFloatingPointLiteral=35;
    public static final int ExponentPart=36;
    public static final int FloatTypeSuffix=37;
    public static final int ExponentIndicator=38;
    public static final int SignedInteger=39;
    public static final int Sign=40;
    public static final int HexSignificand=41;
    public static final int BinaryExponent=42;
    public static final int BinaryExponentIndicator=43;
    public static final int EscapeSequence=44;
    public static final int UnicodeEscape=45;
    public static final int OctalEscape=46;
    public static final int Refines=47;
    public static final int Letter=48;
    public static final int JavaIDDigit=49;
    public static final int WS=50;
    public static final int COMMENT=51;
    public static final int LINE_COMMENT=52;

    // delegates
    public JLo_JLoP_JavaP gJavaP;
    // delegators
    public OldJLoParser gJLo;
    public OldJLoParser gParent;


        public JLo_JLoP(TokenStream input, OldJLoParser gJLo) {
            this(input, new RecognizerSharedState(), gJLo);
        }
        public JLo_JLoP(TokenStream input, RecognizerSharedState state, OldJLoParser gJLo) {
            super(input, state);
            this.gJLo = gJLo;
            gJavaP = new JLo_JLoP_JavaP(input, state, gJLo, this);         
            gParent = gJLo;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
        gJavaP.setTreeAdaptor(this.adaptor);
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return OldJLoParser.tokenNames; }
    public String getGrammarFileName() { return "JLoP.g"; }



      @Override
      public void setView(View view) {
        super.setView(view);
        gJavaP.setView(view);
      }
      
      @Override
      public View view() {
        return gJavaP.view();
      }
      
      public Language language() {
        return view().language();
      }
      
      public Document getDocument() {
        return gJavaP.getDocument();
      }
    	   
      public void setDocument(Document compilationUnit) {
        gJavaP.setDocument(compilationUnit);
      }
      
      public Namespace getDefaultNamespace() {
        return gJavaP.getDefaultNamespace();
      }
      
        public void setFactory(Java7Factory factory) {
        gJavaP.setFactory(factory);
      }
      
      public Java7Factory factory() {
        return gJavaP.factory();
      }

      public JavaTypeReference typeRef(String qn) {
        return gJavaP.typeRef(qn);
      }

      public JavaTypeReference createTypeReference(CrossReference<? extends TargetDeclaration> target, String name) {
        return gJavaP.createTypeReference(target,name);
      }
      
      public JavaTypeReference createTypeReference(NamedTarget target) {
        return gJavaP.createTypeReference(target);
      }

      public CrossReferenceTarget cloneTarget(CrossReferenceTarget target) {
        return gJavaP.cloneTarget(target);
      }
      
      public MethodInvocation invocation(String name, CrossReferenceTarget target) {
        return gJavaP.invocation(name,target);
      }


    public static class identifierRule_return extends ParserRuleReturnScope {
        public String element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "identifierRule"
    // JLoP.g:79:1: identifierRule returns [String element] : (id= Identifier | ex= Export | co= Connector | ctc= Connect | n= Name | o= Overrides );
    public final JLo_JLoP.identifierRule_return identifierRule() throws RecognitionException {
        JLo_JLoP.identifierRule_return retval = new JLo_JLoP.identifierRule_return();
        retval.start = input.LT(1);
        int identifierRule_StartIndex = input.index();
        Object root_0 = null;

        Token id=null;
        Token ex=null;
        Token co=null;
        Token ctc=null;
        Token n=null;
        Token o=null;

        Object id_tree=null;
        Object ex_tree=null;
        Object co_tree=null;
        Object ctc_tree=null;
        Object n_tree=null;
        Object o_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 416) ) { return retval; }
            // JLoP.g:80:5: (id= Identifier | ex= Export | co= Connector | ctc= Connect | n= Name | o= Overrides )
            int alt1=6;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                alt1=1;
                }
                break;
            case Export:
                {
                alt1=2;
                }
                break;
            case Connector:
                {
                alt1=3;
                }
                break;
            case Connect:
                {
                alt1=4;
                }
                break;
            case Name:
                {
                alt1=5;
                }
                break;
            case Overrides:
                {
                alt1=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // JLoP.g:80:7: id= Identifier
                    {
                    root_0 = (Object)adaptor.nil();

                    id=(Token)match(input,Identifier,FOLLOW_Identifier_in_identifierRule74); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    id_tree = (Object)adaptor.create(id);
                    adaptor.addChild(root_0, id_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (id!=null?id.getText():null);
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:81:9: ex= Export
                    {
                    root_0 = (Object)adaptor.nil();

                    ex=(Token)match(input,Export,FOLLOW_Export_in_identifierRule89); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ex_tree = (Object)adaptor.create(ex);
                    adaptor.addChild(root_0, ex_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (ex!=null?ex.getText():null);
                    }

                    }
                    break;
                case 3 :
                    // JLoP.g:82:9: co= Connector
                    {
                    root_0 = (Object)adaptor.nil();

                    co=(Token)match(input,Connector,FOLLOW_Connector_in_identifierRule105); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    co_tree = (Object)adaptor.create(co);
                    adaptor.addChild(root_0, co_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (co!=null?co.getText():null);
                    }

                    }
                    break;
                case 4 :
                    // JLoP.g:83:9: ctc= Connect
                    {
                    root_0 = (Object)adaptor.nil();

                    ctc=(Token)match(input,Connect,FOLLOW_Connect_in_identifierRule121); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ctc_tree = (Object)adaptor.create(ctc);
                    adaptor.addChild(root_0, ctc_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (ctc!=null?ctc.getText():null);
                    }

                    }
                    break;
                case 5 :
                    // JLoP.g:84:9: n= Name
                    {
                    root_0 = (Object)adaptor.nil();

                    n=(Token)match(input,Name,FOLLOW_Name_in_identifierRule137); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    n_tree = (Object)adaptor.create(n);
                    adaptor.addChild(root_0, n_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (n!=null?n.getText():null);
                    }

                    }
                    break;
                case 6 :
                    // JLoP.g:85:9: o= Overrides
                    {
                    root_0 = (Object)adaptor.nil();

                    o=(Token)match(input,Overrides,FOLLOW_Overrides_in_identifierRule153); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    o_tree = (Object)adaptor.create(o);
                    adaptor.addChild(root_0, o_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = (o!=null?o.getText():null);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 416, identifierRule_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "identifierRule"

    public static class memberDecl_return extends ParserRuleReturnScope {
        public TypeElement element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "memberDecl"
    // JLoP.g:89:1: memberDecl returns [TypeElement element] : (gen= genericMethodOrConstructorDecl | mem= memberDeclaration | vmd= voidMethodDeclaration | cs= constructorDeclaration | id= interfaceDeclaration | cd= classDeclaration | comp= subobjectDeclaration | exp= exportDeclaration | ov= overridesClause | np= nameParameter | con= connector | cnct= connection );
    public final JLo_JLoP.memberDecl_return memberDecl() throws RecognitionException {
        JLo_JLoP.memberDecl_return retval = new JLo_JLoP.memberDecl_return();
        retval.start = input.LT(1);
        int memberDecl_StartIndex = input.index();
        Object root_0 = null;

        JLo_JLoP_JavaP.genericMethodOrConstructorDecl_return gen = null;

        JLo_JLoP_JavaP.memberDeclaration_return mem = null;

        JLo_JLoP_JavaP.voidMethodDeclaration_return vmd = null;

        JLo_JLoP_JavaP.constructorDeclaration_return cs = null;

        JLo_JLoP_JavaP.interfaceDeclaration_return id = null;

        JLo_JLoP_JavaP.classDeclaration_return cd = null;

        JLo_JLoP.subobjectDeclaration_return comp = null;

        JLo_JLoP.exportDeclaration_return exp = null;

        JLo_JLoP.overridesClause_return ov = null;

        JLo_JLoP.nameParameter_return np = null;

        JLo_JLoP.connector_return con = null;

        JLo_JLoP.connection_return cnct = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 417) ) { return retval; }
            // JLoP.g:91:5: (gen= genericMethodOrConstructorDecl | mem= memberDeclaration | vmd= voidMethodDeclaration | cs= constructorDeclaration | id= interfaceDeclaration | cd= classDeclaration | comp= subobjectDeclaration | exp= exportDeclaration | ov= overridesClause | np= nameParameter | con= connector | cnct= connection )
            int alt2=12;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // JLoP.g:91:9: gen= genericMethodOrConstructorDecl
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_genericMethodOrConstructorDecl_in_memberDecl190);
                    gen=gJLo.genericMethodOrConstructorDecl();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, gen.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = gen.element;
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:92:9: mem= memberDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_memberDeclaration_in_memberDecl204);
                    mem=gJLo.memberDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, mem.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = mem.element;
                    }

                    }
                    break;
                case 3 :
                    // JLoP.g:93:9: vmd= voidMethodDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_voidMethodDeclaration_in_memberDecl218);
                    vmd=gJLo.voidMethodDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, vmd.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = vmd.element;
                    }

                    }
                    break;
                case 4 :
                    // JLoP.g:94:9: cs= constructorDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constructorDeclaration_in_memberDecl232);
                    cs=gJLo.constructorDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cs.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = cs.element;
                    }

                    }
                    break;
                case 5 :
                    // JLoP.g:95:9: id= interfaceDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_interfaceDeclaration_in_memberDecl246);
                    id=gJLo.interfaceDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=id.element; 
                    }

                    }
                    break;
                case 6 :
                    // JLoP.g:96:9: cd= classDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_classDeclaration_in_memberDecl260);
                    cd=gJLo.classDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cd.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=cd.element;
                    }

                    }
                    break;
                case 7 :
                    // JLoP.g:97:9: comp= subobjectDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_subobjectDeclaration_in_memberDecl274);
                    comp=subobjectDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=comp.element;
                    }

                    }
                    break;
                case 8 :
                    // JLoP.g:98:9: exp= exportDeclaration
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_exportDeclaration_in_memberDecl288);
                    exp=exportDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=exp.element;
                    }

                    }
                    break;
                case 9 :
                    // JLoP.g:99:9: ov= overridesClause
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_overridesClause_in_memberDecl302);
                    ov=overridesClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ov.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=ov.element;
                    }

                    }
                    break;
                case 10 :
                    // JLoP.g:100:9: np= nameParameter
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_nameParameter_in_memberDecl316);
                    np=nameParameter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, np.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=np.element;
                    }

                    }
                    break;
                case 11 :
                    // JLoP.g:101:9: con= connector
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_connector_in_memberDecl330);
                    con=connector();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, con.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = con.element;
                    }

                    }
                    break;
                case 12 :
                    // JLoP.g:102:9: cnct= connection
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_connection_in_memberDecl344);
                    cnct=connection();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cnct.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = cnct.element;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {
              setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 417, memberDecl_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "memberDecl"

    public static class connector_return extends ParserRuleReturnScope {
        public TypeElement element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connector"
    // JLoP.g:105:1: connector returns [TypeElement element] : ctkw= Connector cp= subobjectParameter ';' ;
    public final JLo_JLoP.connector_return connector() throws RecognitionException {
        JLo_JLoP.connector_return retval = new JLo_JLoP.connector_return();
        retval.start = input.LT(1);
        int connector_StartIndex = input.index();
        Object root_0 = null;

        Token ctkw=null;
        Token char_literal1=null;
        JLo_JLoP.subobjectParameter_return cp = null;


        Object ctkw_tree=null;
        Object char_literal1_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 418) ) { return retval; }
            // JLoP.g:106:3: (ctkw= Connector cp= subobjectParameter ';' )
            // JLoP.g:106:5: ctkw= Connector cp= subobjectParameter ';'
            {
            root_0 = (Object)adaptor.nil();

            ctkw=(Token)match(input,Connector,FOLLOW_Connector_in_connector367); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ctkw_tree = (Object)adaptor.create(ctkw);
            adaptor.addChild(root_0, ctkw_tree);
            }
            pushFollow(FOLLOW_subobjectParameter_in_connector371);
            cp=subobjectParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, cp.getTree());
            if ( state.backtracking==0 ) {
              retval.element = cp.element; setKeyword(retval.element,ctkw);
            }
            char_literal1=(Token)match(input,54,FOLLOW_54_in_connector374); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal1_tree = (Object)adaptor.create(char_literal1);
            adaptor.addChild(root_0, char_literal1_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 418, connector_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "connector"

    public static class connection_return extends ParserRuleReturnScope {
        public TypeElement element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "connection"
    // JLoP.g:109:1: connection returns [TypeElement element] : ctkw= Connect name= identifierRule tokw= identifierRule arg= subobjectArgument cl= ';' ;
    public final JLo_JLoP.connection_return connection() throws RecognitionException {
        JLo_JLoP.connection_return retval = new JLo_JLoP.connection_return();
        retval.start = input.LT(1);
        int connection_StartIndex = input.index();
        Object root_0 = null;

        Token ctkw=null;
        Token cl=null;
        JLo_JLoP.identifierRule_return name = null;

        JLo_JLoP.identifierRule_return tokw = null;

        JLo_JLoP.subobjectArgument_return arg = null;


        Object ctkw_tree=null;
        Object cl_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 419) ) { return retval; }
            // JLoP.g:110:3: (ctkw= Connect name= identifierRule tokw= identifierRule arg= subobjectArgument cl= ';' )
            // JLoP.g:110:5: ctkw= Connect name= identifierRule tokw= identifierRule arg= subobjectArgument cl= ';'
            {
            root_0 = (Object)adaptor.nil();

            ctkw=(Token)match(input,Connect,FOLLOW_Connect_in_connection394); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ctkw_tree = (Object)adaptor.create(ctkw);
            adaptor.addChild(root_0, ctkw_tree);
            }
            pushFollow(FOLLOW_identifierRule_in_connection398);
            name=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name.getTree());
            pushFollow(FOLLOW_identifierRule_in_connection402);
            tokw=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tokw.getTree());
            pushFollow(FOLLOW_subobjectArgument_in_connection406);
            arg=subobjectArgument();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, arg.getTree());
            cl=(Token)match(input,54,FOLLOW_54_in_connection410); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            cl_tree = (Object)adaptor.create(cl);
            adaptor.addChild(root_0, cl_tree);
            }
            if ( state.backtracking==0 ) {
              retval.element = new InstantiatedMemberSubobjectParameter(((name!=null?input.toString(name.start,name.stop):null)),arg.element);
               	   setKeyword(retval.element,ctkw);
              	   setName(retval.element,name.start);
               	   if((tokw!=null?input.toString(tokw.start,tokw.stop):null).equals("to")) {setKeyword(arg.element,tokw.start);}
               	   setLocation(retval.element, ctkw,cl);
               	  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 419, connection_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "connection"

    public static class nameParameter_return extends ParserRuleReturnScope {
        public TypeElement element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nameParameter"
    // JLoP.g:119:1: nameParameter returns [TypeElement element] : Name identifierRule ( '=' memberName )? ';' ;
    public final JLo_JLoP.nameParameter_return nameParameter() throws RecognitionException {
        JLo_JLoP.nameParameter_return retval = new JLo_JLoP.nameParameter_return();
        retval.start = input.LT(1);
        int nameParameter_StartIndex = input.index();
        Object root_0 = null;

        Token Name2=null;
        Token char_literal4=null;
        Token char_literal6=null;
        JLo_JLoP.identifierRule_return identifierRule3 = null;

        JLo_JLoP.memberName_return memberName5 = null;


        Object Name2_tree=null;
        Object char_literal4_tree=null;
        Object char_literal6_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 420) ) { return retval; }
            // JLoP.g:120:6: ( Name identifierRule ( '=' memberName )? ';' )
            // JLoP.g:120:8: Name identifierRule ( '=' memberName )? ';'
            {
            root_0 = (Object)adaptor.nil();

            Name2=(Token)match(input,Name,FOLLOW_Name_in_nameParameter437); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            Name2_tree = (Object)adaptor.create(Name2);
            adaptor.addChild(root_0, Name2_tree);
            }
            pushFollow(FOLLOW_identifierRule_in_nameParameter439);
            identifierRule3=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, identifierRule3.getTree());
            // JLoP.g:120:28: ( '=' memberName )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==79) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // JLoP.g:120:29: '=' memberName
                    {
                    char_literal4=(Token)match(input,79,FOLLOW_79_in_nameParameter442); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal4_tree = (Object)adaptor.create(char_literal4);
                    adaptor.addChild(root_0, char_literal4_tree);
                    }
                    pushFollow(FOLLOW_memberName_in_nameParameter444);
                    memberName5=memberName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, memberName5.getTree());

                    }
                    break;

            }

            char_literal6=(Token)match(input,54,FOLLOW_54_in_nameParameter448); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal6_tree = (Object)adaptor.create(char_literal6);
            adaptor.addChild(root_0, char_literal6_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 420, nameParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "nameParameter"

    public static class memberName_return extends ParserRuleReturnScope {
        public Object element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "memberName"
    // JLoP.g:123:1: memberName returns [Object element] : identifierRule ;
    public final JLo_JLoP.memberName_return memberName() throws RecognitionException {
        JLo_JLoP.memberName_return retval = new JLo_JLoP.memberName_return();
        retval.start = input.LT(1);
        int memberName_StartIndex = input.index();
        Object root_0 = null;

        JLo_JLoP.identifierRule_return identifierRule7 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 421) ) { return retval; }
            // JLoP.g:124:3: ( identifierRule )
            // JLoP.g:124:5: identifierRule
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_identifierRule_in_memberName473);
            identifierRule7=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, identifierRule7.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 421, memberName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "memberName"

    public static class exportDeclaration_return extends ParserRuleReturnScope {
        public Export element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exportDeclaration"
    // JLoP.g:127:1: exportDeclaration returns [Export element] : xp= Export m= map ( ',' mm= map )* ';' ;
    public final JLo_JLoP.exportDeclaration_return exportDeclaration() throws RecognitionException {
        JLo_JLoP.exportDeclaration_return retval = new JLo_JLoP.exportDeclaration_return();
        retval.start = input.LT(1);
        int exportDeclaration_StartIndex = input.index();
        Object root_0 = null;

        Token xp=null;
        Token char_literal8=null;
        Token char_literal9=null;
        JLo_JLoP.map_return m = null;

        JLo_JLoP.map_return mm = null;


        Object xp_tree=null;
        Object char_literal8_tree=null;
        Object char_literal9_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 422) ) { return retval; }
            // JLoP.g:128:6: (xp= Export m= map ( ',' mm= map )* ';' )
            // JLoP.g:128:8: xp= Export m= map ( ',' mm= map )* ';'
            {
            root_0 = (Object)adaptor.nil();

            xp=(Token)match(input,Export,FOLLOW_Export_in_exportDeclaration500); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            xp_tree = (Object)adaptor.create(xp);
            adaptor.addChild(root_0, xp_tree);
            }
            if ( state.backtracking==0 ) {
              retval.element = new Export(); setKeyword(retval.element,xp);
            }
            pushFollow(FOLLOW_map_in_exportDeclaration506);
            m=map();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, m.getTree());
            if ( state.backtracking==0 ) {
              retval.element.add(m.element);
            }
            // JLoP.g:128:121: ( ',' mm= map )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==69) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // JLoP.g:128:122: ',' mm= map
            	    {
            	    char_literal8=(Token)match(input,69,FOLLOW_69_in_exportDeclaration511); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    char_literal8_tree = (Object)adaptor.create(char_literal8);
            	    adaptor.addChild(root_0, char_literal8_tree);
            	    }
            	    pushFollow(FOLLOW_map_in_exportDeclaration515);
            	    mm=map();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, mm.getTree());
            	    if ( state.backtracking==0 ) {
            	      retval.element.add(mm.element);
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            char_literal9=(Token)match(input,54,FOLLOW_54_in_exportDeclaration521); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal9_tree = (Object)adaptor.create(char_literal9);
            adaptor.addChild(root_0, char_literal9_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 422, exportDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "exportDeclaration"

    public static class overridesClause_return extends ParserRuleReturnScope {
        public Overrides element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "overridesClause"
    // JLoP.g:131:1: overridesClause returns [Overrides element] : newSig= signature ov= Overrides oldFQN= fqn ;
    public final JLo_JLoP.overridesClause_return overridesClause() throws RecognitionException {
        JLo_JLoP.overridesClause_return retval = new JLo_JLoP.overridesClause_return();
        retval.start = input.LT(1);
        int overridesClause_StartIndex = input.index();
        Object root_0 = null;

        Token ov=null;
        JLo_JLoP.signature_return newSig = null;

        JLo_JLoP.fqn_return oldFQN = null;


        Object ov_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 423) ) { return retval; }
            // JLoP.g:132:7: (newSig= signature ov= Overrides oldFQN= fqn )
            // JLoP.g:132:9: newSig= signature ov= Overrides oldFQN= fqn
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_signature_in_overridesClause547);
            newSig=signature();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, newSig.getTree());
            ov=(Token)match(input,Overrides,FOLLOW_Overrides_in_overridesClause551); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ov_tree = (Object)adaptor.create(ov);
            adaptor.addChild(root_0, ov_tree);
            }
            pushFollow(FOLLOW_fqn_in_overridesClause555);
            oldFQN=fqn();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, oldFQN.getTree());
            if ( state.backtracking==0 ) {
              retval.element = new Overrides(newSig.element,oldFQN.element); setKeyword(retval.element,ov);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 423, overridesClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "overridesClause"

    public static class map_return extends ParserRuleReturnScope {
        public RenamingClause element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "map"
    // JLoP.g:135:1: map returns [RenamingClause element] : oldFQN= fqn (id= identifierRule newSig= signature )? ;
    public final JLo_JLoP.map_return map() throws RecognitionException {
        JLo_JLoP.map_return retval = new JLo_JLoP.map_return();
        retval.start = input.LT(1);
        int map_StartIndex = input.index();
        Object root_0 = null;

        JLo_JLoP.fqn_return oldFQN = null;

        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP.signature_return newSig = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 424) ) { return retval; }
            // JLoP.g:136:2: (oldFQN= fqn (id= identifierRule newSig= signature )? )
            // JLoP.g:136:5: oldFQN= fqn (id= identifierRule newSig= signature )?
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_fqn_in_map580);
            oldFQN=fqn();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, oldFQN.getTree());
            if ( state.backtracking==0 ) {
              retval.element = new RenamingClause(null, oldFQN.element);
            }
            // JLoP.g:137:5: (id= identifierRule newSig= signature )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==Identifier||(LA5_0>=Export && LA5_0<=Overrides)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // JLoP.g:137:6: id= identifierRule newSig= signature
                    {
                    pushFollow(FOLLOW_identifierRule_in_map592);
                    id=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    pushFollow(FOLLOW_signature_in_map596);
                    newSig=signature();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, newSig.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element.setNewSignature(newSig.element);
                      	    if((id!=null?input.toString(id.start,id.stop):null).equals("as")) {setKeyword(retval.element, id.start);}
                      	   
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 424, map_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "map"

    public static class subobjectDeclaration_return extends ParserRuleReturnScope {
        public Subobject element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subobjectDeclaration"
    // JLoP.g:143:1: subobjectDeclaration returns [Subobject element] : cp= 'subobject' name= identifierRule (tp= type )? (body= classBody | ';' ) ;
    public final JLo_JLoP.subobjectDeclaration_return subobjectDeclaration() throws RecognitionException {
        JLo_JLoP.subobjectDeclaration_return retval = new JLo_JLoP.subobjectDeclaration_return();
        retval.start = input.LT(1);
        int subobjectDeclaration_StartIndex = input.index();
        Object root_0 = null;

        Token cp=null;
        Token char_literal10=null;
        JLo_JLoP.identifierRule_return name = null;

        JLo_JLoP_JavaP.type_return tp = null;

        JLo_JLoP_JavaP.classBody_return body = null;


        Object cp_tree=null;
        Object char_literal10_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 425) ) { return retval; }
            // JLoP.g:144:6: (cp= 'subobject' name= identifierRule (tp= type )? (body= classBody | ';' ) )
            // JLoP.g:144:8: cp= 'subobject' name= identifierRule (tp= type )? (body= classBody | ';' )
            {
            root_0 = (Object)adaptor.nil();

            cp=(Token)match(input,142,FOLLOW_142_in_subobjectDeclaration631); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            cp_tree = (Object)adaptor.create(cp);
            adaptor.addChild(root_0, cp_tree);
            }
            pushFollow(FOLLOW_identifierRule_in_subobjectDeclaration635);
            name=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name.getTree());
            // JLoP.g:144:45: (tp= type )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==Identifier||(LA6_0>=Export && LA6_0<=Overrides)||(LA6_0>=85 && LA6_0<=92)) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // JLoP.g:0:0: tp= type
                    {
                    pushFollow(FOLLOW_type_in_subobjectDeclaration639);
                    tp=gJLo.type();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tp.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              retval.element = new Subobject(new SimpleNameSignature((name!=null?input.toString(name.start,name.stop):null)), (tp!=null?tp.element:null));
                  	         setKeyword(retval.element,cp);
                  	         setName(retval.element,name.start);
                  	        
            }
            // JLoP.g:149:14: (body= classBody | ';' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==72) ) {
                alt7=1;
            }
            else if ( (LA7_0==54) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // JLoP.g:149:15: body= classBody
                    {
                    pushFollow(FOLLOW_classBody_in_subobjectDeclaration673);
                    body=gJLo.classBody();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, body.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element.setBody((body!=null?body.element:null));
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:149:73: ';'
                    {
                    char_literal10=(Token)match(input,54,FOLLOW_54_in_subobjectDeclaration679); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal10_tree = (Object)adaptor.create(char_literal10);
                    adaptor.addChild(root_0, char_literal10_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 425, subobjectDeclaration_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "subobjectDeclaration"

    public static class configurationBlock_return extends ParserRuleReturnScope {
        public ConfigurationBlock element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "configurationBlock"
    // JLoP.g:153:1: configurationBlock returns [ConfigurationBlock element] : al= 'alias' '{' (cl= configurationClause ( ',' cll= configurationClause )* )? '}' ;
    public final JLo_JLoP.configurationBlock_return configurationBlock() throws RecognitionException {
        JLo_JLoP.configurationBlock_return retval = new JLo_JLoP.configurationBlock_return();
        retval.start = input.LT(1);
        int configurationBlock_StartIndex = input.index();
        Object root_0 = null;

        Token al=null;
        Token char_literal11=null;
        Token char_literal12=null;
        Token char_literal13=null;
        JLo_JLoP.configurationClause_return cl = null;

        JLo_JLoP.configurationClause_return cll = null;


        Object al_tree=null;
        Object char_literal11_tree=null;
        Object char_literal12_tree=null;
        Object char_literal13_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 426) ) { return retval; }
            // JLoP.g:155:9: (al= 'alias' '{' (cl= configurationClause ( ',' cll= configurationClause )* )? '}' )
            // JLoP.g:155:11: al= 'alias' '{' (cl= configurationClause ( ',' cll= configurationClause )* )? '}'
            {
            root_0 = (Object)adaptor.nil();

            al=(Token)match(input,143,FOLLOW_143_in_configurationBlock728); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            al_tree = (Object)adaptor.create(al);
            adaptor.addChild(root_0, al_tree);
            }
            char_literal11=(Token)match(input,72,FOLLOW_72_in_configurationBlock730); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal11_tree = (Object)adaptor.create(char_literal11);
            adaptor.addChild(root_0, char_literal11_tree);
            }
            if ( state.backtracking==0 ) {
              retval.element = new ConfigurationBlock(); setKeyword(retval.element,al);
            }
            // JLoP.g:155:102: (cl= configurationClause ( ',' cll= configurationClause )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==Identifier||(LA9_0>=Export && LA9_0<=Overrides)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // JLoP.g:155:103: cl= configurationClause ( ',' cll= configurationClause )*
                    {
                    pushFollow(FOLLOW_configurationClause_in_configurationBlock737);
                    cl=configurationClause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cl.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element.add(cl.element);
                    }
                    // JLoP.g:155:159: ( ',' cll= configurationClause )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==69) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // JLoP.g:155:160: ',' cll= configurationClause
                    	    {
                    	    char_literal12=(Token)match(input,69,FOLLOW_69_in_configurationBlock741); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal12_tree = (Object)adaptor.create(char_literal12);
                    	    adaptor.addChild(root_0, char_literal12_tree);
                    	    }
                    	    pushFollow(FOLLOW_configurationClause_in_configurationBlock745);
                    	    cll=configurationClause();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, cll.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      retval.element.add(cll.element);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            char_literal13=(Token)match(input,73,FOLLOW_73_in_configurationBlock752); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal13_tree = (Object)adaptor.create(char_literal13);
            adaptor.addChild(root_0, char_literal13_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {
              setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 426, configurationBlock_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "configurationBlock"

    public static class configurationClause_return extends ParserRuleReturnScope {
        public ConfigurationClause element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "configurationClause"
    // JLoP.g:158:1: configurationClause returns [ConfigurationClause element] : (sig= signature ov= '>' f= fqn | sigg= signature al= '=' ff= fqn );
    public final JLo_JLoP.configurationClause_return configurationClause() throws RecognitionException {
        JLo_JLoP.configurationClause_return retval = new JLo_JLoP.configurationClause_return();
        retval.start = input.LT(1);
        int configurationClause_StartIndex = input.index();
        Object root_0 = null;

        Token ov=null;
        Token al=null;
        JLo_JLoP.signature_return sig = null;

        JLo_JLoP.fqn_return f = null;

        JLo_JLoP.signature_return sigg = null;

        JLo_JLoP.fqn_return ff = null;


        Object ov_tree=null;
        Object al_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 427) ) { return retval; }
            // JLoP.g:160:2: (sig= signature ov= '>' f= fqn | sigg= signature al= '=' ff= fqn )
            int alt10=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA10_1 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
                }
                break;
            case Export:
                {
                int LA10_2 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 2, input);

                    throw nvae;
                }
                }
                break;
            case Connector:
                {
                int LA10_3 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 3, input);

                    throw nvae;
                }
                }
                break;
            case Connect:
                {
                int LA10_4 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 4, input);

                    throw nvae;
                }
                }
                break;
            case Name:
                {
                int LA10_5 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 5, input);

                    throw nvae;
                }
                }
                break;
            case Overrides:
                {
                int LA10_6 = input.LA(2);

                if ( (synpred24_JLoP()) ) {
                    alt10=1;
                }
                else if ( (true) ) {
                    alt10=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // JLoP.g:160:4: sig= signature ov= '>' f= fqn
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_signature_in_configurationClause788);
                    sig=signature();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sig.getTree());
                    ov=(Token)match(input,70,FOLLOW_70_in_configurationClause792); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ov_tree = (Object)adaptor.create(ov);
                    adaptor.addChild(root_0, ov_tree);
                    }
                    pushFollow(FOLLOW_fqn_in_configurationClause796);
                    f=fqn();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new OverridesClause(sig.element, f.element);
                      	      setKeyword(retval.element, ov);
                      	     
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:165:3: sigg= signature al= '=' ff= fqn
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_signature_in_configurationClause813);
                    sigg=signature();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sigg.getTree());
                    al=(Token)match(input,79,FOLLOW_79_in_configurationClause817); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    al_tree = (Object)adaptor.create(al);
                    adaptor.addChild(root_0, al_tree);
                    }
                    pushFollow(FOLLOW_fqn_in_configurationClause821);
                    ff=fqn();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ff.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new RenamingClause(sigg.element, ff.element);
                      	      setKeyword(retval.element, al);
                      	     
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {
              setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 427, configurationClause_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "configurationClause"

    public static class signature_return extends ParserRuleReturnScope {
        public Signature element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "signature"
    // JLoP.g:171:1: signature returns [Signature element] : (sig= identifierRule | sigg= identifierRule '(' (t= type ( ',' tt= type )* )? ')' );
    public final JLo_JLoP.signature_return signature() throws RecognitionException {
        JLo_JLoP.signature_return retval = new JLo_JLoP.signature_return();
        retval.start = input.LT(1);
        int signature_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal14=null;
        Token char_literal15=null;
        Token char_literal16=null;
        JLo_JLoP.identifierRule_return sig = null;

        JLo_JLoP.identifierRule_return sigg = null;

        JLo_JLoP_JavaP.type_return t = null;

        JLo_JLoP_JavaP.type_return tt = null;


        Object char_literal14_tree=null;
        Object char_literal15_tree=null;
        Object char_literal16_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 428) ) { return retval; }
            // JLoP.g:172:9: (sig= identifierRule | sigg= identifierRule '(' (t= type ( ',' tt= type )* )? ')' )
            int alt13=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==EOF||(LA13_1>=Identifier && LA13_1<=ENUM)||(LA13_1>=Export && LA13_1<=Overrides)||LA13_1==54||LA13_1==56||(LA13_1>=59 && LA13_1<=65)||(LA13_1>=68 && LA13_1<=70)||(LA13_1>=72 && LA13_1<=75)||(LA13_1>=79 && LA13_1<=83)||(LA13_1>=85 && LA13_1<=92)||LA13_1==102||LA13_1==142) ) {
                    alt13=1;
                }
                else if ( (LA13_1==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
                }
                break;
            case Export:
                {
                int LA13_2 = input.LA(2);

                if ( (LA13_2==EOF||(LA13_2>=Identifier && LA13_2<=ENUM)||(LA13_2>=Export && LA13_2<=Overrides)||LA13_2==54||LA13_2==56||(LA13_2>=59 && LA13_2<=65)||(LA13_2>=68 && LA13_2<=70)||(LA13_2>=72 && LA13_2<=75)||(LA13_2>=79 && LA13_2<=83)||(LA13_2>=85 && LA13_2<=92)||LA13_2==102||LA13_2==142) ) {
                    alt13=1;
                }
                else if ( (LA13_2==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 2, input);

                    throw nvae;
                }
                }
                break;
            case Connector:
                {
                int LA13_3 = input.LA(2);

                if ( (LA13_3==EOF||(LA13_3>=Identifier && LA13_3<=ENUM)||(LA13_3>=Export && LA13_3<=Overrides)||LA13_3==54||LA13_3==56||(LA13_3>=59 && LA13_3<=65)||(LA13_3>=68 && LA13_3<=70)||(LA13_3>=72 && LA13_3<=75)||(LA13_3>=79 && LA13_3<=83)||(LA13_3>=85 && LA13_3<=92)||LA13_3==102||LA13_3==142) ) {
                    alt13=1;
                }
                else if ( (LA13_3==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 3, input);

                    throw nvae;
                }
                }
                break;
            case Connect:
                {
                int LA13_4 = input.LA(2);

                if ( (LA13_4==EOF||(LA13_4>=Identifier && LA13_4<=ENUM)||(LA13_4>=Export && LA13_4<=Overrides)||LA13_4==54||LA13_4==56||(LA13_4>=59 && LA13_4<=65)||(LA13_4>=68 && LA13_4<=70)||(LA13_4>=72 && LA13_4<=75)||(LA13_4>=79 && LA13_4<=83)||(LA13_4>=85 && LA13_4<=92)||LA13_4==102||LA13_4==142) ) {
                    alt13=1;
                }
                else if ( (LA13_4==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 4, input);

                    throw nvae;
                }
                }
                break;
            case Name:
                {
                int LA13_5 = input.LA(2);

                if ( (LA13_5==EOF||(LA13_5>=Identifier && LA13_5<=ENUM)||(LA13_5>=Export && LA13_5<=Overrides)||LA13_5==54||LA13_5==56||(LA13_5>=59 && LA13_5<=65)||(LA13_5>=68 && LA13_5<=70)||(LA13_5>=72 && LA13_5<=75)||(LA13_5>=79 && LA13_5<=83)||(LA13_5>=85 && LA13_5<=92)||LA13_5==102||LA13_5==142) ) {
                    alt13=1;
                }
                else if ( (LA13_5==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 5, input);

                    throw nvae;
                }
                }
                break;
            case Overrides:
                {
                int LA13_6 = input.LA(2);

                if ( (LA13_6==EOF||(LA13_6>=Identifier && LA13_6<=ENUM)||(LA13_6>=Export && LA13_6<=Overrides)||LA13_6==54||LA13_6==56||(LA13_6>=59 && LA13_6<=65)||(LA13_6>=68 && LA13_6<=70)||(LA13_6>=72 && LA13_6<=75)||(LA13_6>=79 && LA13_6<=83)||(LA13_6>=85 && LA13_6<=92)||LA13_6==102||LA13_6==142) ) {
                    alt13=1;
                }
                else if ( (LA13_6==95) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // JLoP.g:172:11: sig= identifierRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifierRule_in_signature855);
                    sig=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sig.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new SimpleNameSignature((sig!=null?input.toString(sig.start,sig.stop):null));
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:173:11: sigg= identifierRule '(' (t= type ( ',' tt= type )* )? ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifierRule_in_signature871);
                    sigg=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sigg.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new SignatureWithParameters((sigg!=null?input.toString(sigg.start,sigg.stop):null));
                    }
                    char_literal14=(Token)match(input,95,FOLLOW_95_in_signature892); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal14_tree = (Object)adaptor.create(char_literal14);
                    adaptor.addChild(root_0, char_literal14_tree);
                    }
                    // JLoP.g:174:21: (t= type ( ',' tt= type )* )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==Identifier||(LA12_0>=Export && LA12_0<=Overrides)||(LA12_0>=85 && LA12_0<=92)) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // JLoP.g:174:22: t= type ( ',' tt= type )*
                            {
                            pushFollow(FOLLOW_type_in_signature897);
                            t=gJLo.type();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                            if ( state.backtracking==0 ) {
                              ((SignatureWithParameters)retval.element).add(t.element);
                            }
                            // JLoP.g:175:18: ( ',' tt= type )*
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( (LA11_0==69) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // JLoP.g:175:19: ',' tt= type
                            	    {
                            	    char_literal15=(Token)match(input,69,FOLLOW_69_in_signature920); if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	    char_literal15_tree = (Object)adaptor.create(char_literal15);
                            	    adaptor.addChild(root_0, char_literal15_tree);
                            	    }
                            	    pushFollow(FOLLOW_type_in_signature924);
                            	    tt=gJLo.type();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, tt.getTree());
                            	    if ( state.backtracking==0 ) {
                            	      ((SignatureWithParameters)retval.element).add(tt.element);
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop11;
                                }
                            } while (true);


                            }
                            break;

                    }

                    char_literal16=(Token)match(input,96,FOLLOW_96_in_signature931); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal16_tree = (Object)adaptor.create(char_literal16);
                    adaptor.addChild(root_0, char_literal16_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 428, signature_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "signature"

    public static class fqn_return extends ParserRuleReturnScope {
        public QualifiedName element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fqn"
    // JLoP.g:178:1: fqn returns [QualifiedName element] : (sig= signature | id= identifierRule '.' ff= fqn );
    public final JLo_JLoP.fqn_return fqn() throws RecognitionException {
        JLo_JLoP.fqn_return retval = new JLo_JLoP.fqn_return();
        retval.start = input.LT(1);
        int fqn_StartIndex = input.index();
        Object root_0 = null;

        Token char_literal17=null;
        JLo_JLoP.signature_return sig = null;

        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP.fqn_return ff = null;


        Object char_literal17_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 429) ) { return retval; }
            // JLoP.g:179:9: (sig= signature | id= identifierRule '.' ff= fqn )
            int alt14=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA14_1 = input.LA(2);

                if ( (LA14_1==EOF||(LA14_1>=Identifier && LA14_1<=ENUM)||(LA14_1>=Export && LA14_1<=Overrides)||LA14_1==54||LA14_1==56||(LA14_1>=59 && LA14_1<=65)||(LA14_1>=68 && LA14_1<=69)||(LA14_1>=72 && LA14_1<=75)||(LA14_1>=80 && LA14_1<=83)||(LA14_1>=85 && LA14_1<=92)||LA14_1==95||LA14_1==102||LA14_1==142) ) {
                    alt14=1;
                }
                else if ( (LA14_1==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 1, input);

                    throw nvae;
                }
                }
                break;
            case Export:
                {
                int LA14_2 = input.LA(2);

                if ( (LA14_2==EOF||(LA14_2>=Identifier && LA14_2<=ENUM)||(LA14_2>=Export && LA14_2<=Overrides)||LA14_2==54||LA14_2==56||(LA14_2>=59 && LA14_2<=65)||(LA14_2>=68 && LA14_2<=69)||(LA14_2>=72 && LA14_2<=75)||(LA14_2>=80 && LA14_2<=83)||(LA14_2>=85 && LA14_2<=92)||LA14_2==95||LA14_2==102||LA14_2==142) ) {
                    alt14=1;
                }
                else if ( (LA14_2==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 2, input);

                    throw nvae;
                }
                }
                break;
            case Connector:
                {
                int LA14_3 = input.LA(2);

                if ( (LA14_3==EOF||(LA14_3>=Identifier && LA14_3<=ENUM)||(LA14_3>=Export && LA14_3<=Overrides)||LA14_3==54||LA14_3==56||(LA14_3>=59 && LA14_3<=65)||(LA14_3>=68 && LA14_3<=69)||(LA14_3>=72 && LA14_3<=75)||(LA14_3>=80 && LA14_3<=83)||(LA14_3>=85 && LA14_3<=92)||LA14_3==95||LA14_3==102||LA14_3==142) ) {
                    alt14=1;
                }
                else if ( (LA14_3==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 3, input);

                    throw nvae;
                }
                }
                break;
            case Connect:
                {
                int LA14_4 = input.LA(2);

                if ( (LA14_4==EOF||(LA14_4>=Identifier && LA14_4<=ENUM)||(LA14_4>=Export && LA14_4<=Overrides)||LA14_4==54||LA14_4==56||(LA14_4>=59 && LA14_4<=65)||(LA14_4>=68 && LA14_4<=69)||(LA14_4>=72 && LA14_4<=75)||(LA14_4>=80 && LA14_4<=83)||(LA14_4>=85 && LA14_4<=92)||LA14_4==95||LA14_4==102||LA14_4==142) ) {
                    alt14=1;
                }
                else if ( (LA14_4==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 4, input);

                    throw nvae;
                }
                }
                break;
            case Name:
                {
                int LA14_5 = input.LA(2);

                if ( (LA14_5==EOF||(LA14_5>=Identifier && LA14_5<=ENUM)||(LA14_5>=Export && LA14_5<=Overrides)||LA14_5==54||LA14_5==56||(LA14_5>=59 && LA14_5<=65)||(LA14_5>=68 && LA14_5<=69)||(LA14_5>=72 && LA14_5<=75)||(LA14_5>=80 && LA14_5<=83)||(LA14_5>=85 && LA14_5<=92)||LA14_5==95||LA14_5==102||LA14_5==142) ) {
                    alt14=1;
                }
                else if ( (LA14_5==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 5, input);

                    throw nvae;
                }
                }
                break;
            case Overrides:
                {
                int LA14_6 = input.LA(2);

                if ( (LA14_6==EOF||(LA14_6>=Identifier && LA14_6<=ENUM)||(LA14_6>=Export && LA14_6<=Overrides)||LA14_6==54||LA14_6==56||(LA14_6>=59 && LA14_6<=65)||(LA14_6>=68 && LA14_6<=69)||(LA14_6>=72 && LA14_6<=75)||(LA14_6>=80 && LA14_6<=83)||(LA14_6>=85 && LA14_6<=92)||LA14_6==95||LA14_6==102||LA14_6==142) ) {
                    alt14=1;
                }
                else if ( (LA14_6==57) ) {
                    alt14=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // JLoP.g:179:11: sig= signature
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_signature_in_fqn971);
                    sig=signature();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sig.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=sig.element;
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:180:15: id= identifierRule '.' ff= fqn
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifierRule_in_fqn991);
                    id=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    char_literal17=(Token)match(input,57,FOLLOW_57_in_fqn993); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal17_tree = (Object)adaptor.create(char_literal17);
                    adaptor.addChild(root_0, char_literal17_tree);
                    }
                    pushFollow(FOLLOW_fqn_in_fqn997);
                    ff=fqn();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ff.getTree());
                    if ( state.backtracking==0 ) {

                                    Signature signature = new SimpleNameSignature((id!=null?input.toString(id.start,id.stop):null));
                                    if(ff.element instanceof CompositeQualifiedName) {
                                      ((CompositeQualifiedName)ff.element).prefix(signature); 
                                      retval.element = ff.element;
                                    } else {
                                      retval.element=new CompositeQualifiedName();
                                      ((CompositeQualifiedName)retval.element).append(signature);
                                      ((CompositeQualifiedName)retval.element).append((Signature)ff.element);
                                    }
                                    
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 429, fqn_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "fqn"

    public static class expression_return extends ParserRuleReturnScope {
        public Expression element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // JLoP.g:193:1: expression returns [Expression element] : (ex= conditionalExpression (op= assignmentOperator exx= expression )? | sb= 'subobject' '.' id= identifierRule args= arguments );
    public final JLo_JLoP.expression_return expression() throws RecognitionException {
        JLo_JLoP.expression_return retval = new JLo_JLoP.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        Object root_0 = null;

        Token sb=null;
        Token char_literal18=null;
        JLo_JLoP_JavaP.conditionalExpression_return ex = null;

        JLo_JLoP_JavaP.assignmentOperator_return op = null;

        JLo_JLoP.expression_return exx = null;

        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP_JavaP.arguments_return args = null;


        Object sb_tree=null;
        Object char_literal18_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 430) ) { return retval; }
            // JLoP.g:197:5: (ex= conditionalExpression (op= assignmentOperator exx= expression )? | sb= 'subobject' '.' id= identifierRule args= arguments )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==Identifier||(LA16_0>=FloatingPointLiteral && LA16_0<=IntegerLiteral)||(LA16_0>=Export && LA16_0<=Overrides)||LA16_0==75||(LA16_0>=85 && LA16_0<=92)||(LA16_0>=94 && LA16_0<=95)||(LA16_0>=98 && LA16_0<=101)||(LA16_0>=133 && LA16_0<=134)||(LA16_0>=137 && LA16_0<=141)||(LA16_0>=144 && LA16_0<=146)) ) {
                alt16=1;
            }
            else if ( (LA16_0==142) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // JLoP.g:197:9: ex= conditionalExpression (op= assignmentOperator exx= expression )?
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_conditionalExpression_in_expression1040);
                    ex=gJLo.conditionalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ex.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=ex.element;
                    }
                    // JLoP.g:197:63: (op= assignmentOperator exx= expression )?
                    int alt15=2;
                    alt15 = dfa15.predict(input);
                    switch (alt15) {
                        case 1 :
                            // JLoP.g:197:64: op= assignmentOperator exx= expression
                            {
                            pushFollow(FOLLOW_assignmentOperator_in_expression1047);
                            op=gJLo.assignmentOperator();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, op.getTree());
                            pushFollow(FOLLOW_expression_in_expression1051);
                            exx=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, exx.getTree());
                            if ( state.backtracking==0 ) {
                              String txt = (op!=null?input.toString(op.start,op.stop):null); 
                                       if(txt.equals("=")) {
                                         retval.element = new AssignmentExpression(ex.element,exx.element);
                                       } else {
                                         retval.element = new InfixOperatorInvocation((op!=null?input.toString(op.start,op.stop):null),ex.element);
                                         ((InfixOperatorInvocation)retval.element).addArgument(exx.element);
                                       }
                                       setLocation(retval.element,op.start,op.stop,"__NAME");
                                       setLocation(retval.element,retval.start,exx.stop);
                                      
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // JLoP.g:209:11: sb= 'subobject' '.' id= identifierRule args= arguments
                    {
                    root_0 = (Object)adaptor.nil();

                    sb=(Token)match(input,142,FOLLOW_142_in_expression1087); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    sb_tree = (Object)adaptor.create(sb);
                    adaptor.addChild(root_0, sb_tree);
                    }
                    char_literal18=(Token)match(input,57,FOLLOW_57_in_expression1089); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal18_tree = (Object)adaptor.create(char_literal18);
                    adaptor.addChild(root_0, char_literal18_tree);
                    }
                    pushFollow(FOLLOW_identifierRule_in_expression1093);
                    id=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    pushFollow(FOLLOW_arguments_in_expression1097);
                    args=gJLo.arguments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, args.getTree());
                    if ( state.backtracking==0 ) {

                                retval.element = new SubobjectConstructorCall((id!=null?input.toString(id.start,id.stop):null), args.element);
                               setLocation(retval.element,sb,args.stop);
                               setKeyword(retval.element,sb);
                                 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                check_null(retval.element);

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 430, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class nonTargetPrimary_return extends ParserRuleReturnScope {
        public Expression element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "nonTargetPrimary"
    // JLoP.g:217:1: nonTargetPrimary returns [Expression element] : (lit= literal | at= '#' id= identifierRule '(' ex= expression stop= ')' | okw= 'outer' supsuf= superSuffix | rkw= 'rooot' supsuf= superSuffix );
    public final JLo_JLoP.nonTargetPrimary_return nonTargetPrimary() throws RecognitionException {
        JLo_JLoP.nonTargetPrimary_return retval = new JLo_JLoP.nonTargetPrimary_return();
        retval.start = input.LT(1);
        int nonTargetPrimary_StartIndex = input.index();
        Object root_0 = null;

        Token at=null;
        Token stop=null;
        Token okw=null;
        Token rkw=null;
        Token char_literal19=null;
        JLo_JLoP_JavaP.literal_return lit = null;

        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP.expression_return ex = null;

        JLo_JLoP_JavaP.superSuffix_return supsuf = null;


        Object at_tree=null;
        Object stop_tree=null;
        Object okw_tree=null;
        Object rkw_tree=null;
        Object char_literal19_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 431) ) { return retval; }
            // JLoP.g:221:4: (lit= literal | at= '#' id= identifierRule '(' ex= expression stop= ')' | okw= 'outer' supsuf= superSuffix | rkw= 'rooot' supsuf= superSuffix )
            int alt17=4;
            switch ( input.LA(1) ) {
            case FloatingPointLiteral:
            case CharacterLiteral:
            case StringLiteral:
            case IntegerLiteral:
            case 99:
            case 100:
            case 101:
                {
                alt17=1;
                }
                break;
            case 144:
                {
                alt17=2;
                }
                break;
            case 145:
                {
                alt17=3;
                }
                break;
            case 146:
                {
                alt17=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // JLoP.g:222:4: lit= literal
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_nonTargetPrimary1133);
                    lit=gJLo.literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lit.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = lit.element;
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:223:6: at= '#' id= identifierRule '(' ex= expression stop= ')'
                    {
                    root_0 = (Object)adaptor.nil();

                    at=(Token)match(input,144,FOLLOW_144_in_nonTargetPrimary1144); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    at_tree = (Object)adaptor.create(at);
                    adaptor.addChild(root_0, at_tree);
                    }
                    pushFollow(FOLLOW_identifierRule_in_nonTargetPrimary1148);
                    id=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    char_literal19=(Token)match(input,95,FOLLOW_95_in_nonTargetPrimary1150); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    char_literal19_tree = (Object)adaptor.create(char_literal19);
                    adaptor.addChild(root_0, char_literal19_tree);
                    }
                    pushFollow(FOLLOW_expression_in_nonTargetPrimary1154);
                    ex=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ex.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = ex.element;
                    }
                    stop=(Token)match(input,96,FOLLOW_96_in_nonTargetPrimary1160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    stop_tree = (Object)adaptor.create(stop);
                    adaptor.addChild(root_0, stop_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.element = new ComponentParameterCall(ex.element, (id!=null?input.toString(id.start,id.stop):null));
                                    setLocation(retval.element,at,stop);
                                    setKeyword(retval.element,at);
                                 
                    }

                    }
                    break;
                case 3 :
                    // JLoP.g:228:11: okw= 'outer' supsuf= superSuffix
                    {
                    root_0 = (Object)adaptor.nil();

                    okw=(Token)match(input,145,FOLLOW_145_in_nonTargetPrimary1187); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    okw_tree = (Object)adaptor.create(okw);
                    adaptor.addChild(root_0, okw_tree);
                    }
                    pushFollow(FOLLOW_superSuffix_in_nonTargetPrimary1191);
                    supsuf=gJLo.superSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, supsuf.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = supsuf.element;
                                  CrossReferenceTarget tar = new OuterTarget();
                                  ((TargetedExpression)retval.element).setTarget(tar);
                                  setLocation(retval.element,okw,okw); // put locations on the SuperTarget.
                                  setKeyword(tar,okw);
                                 
                    }

                    }
                    break;
                case 4 :
                    // JLoP.g:235:11: rkw= 'rooot' supsuf= superSuffix
                    {
                    root_0 = (Object)adaptor.nil();

                    rkw=(Token)match(input,146,FOLLOW_146_in_nonTargetPrimary1219); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    rkw_tree = (Object)adaptor.create(rkw);
                    adaptor.addChild(root_0, rkw_tree);
                    }
                    pushFollow(FOLLOW_superSuffix_in_nonTargetPrimary1223);
                    supsuf=gJLo.superSuffix();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, supsuf.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = supsuf.element;
                                  CrossReferenceTarget tar = new RootTarget();
                                  ((TargetedExpression)retval.element).setTarget(tar);
                                  setLocation(retval.element,rkw,rkw); // put locations on the SuperTarget.
                                  setKeyword(tar,rkw);
                                 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                check_null(retval.element);

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 431, nonTargetPrimary_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "nonTargetPrimary"

    public static class subobjectParameter_return extends ParserRuleReturnScope {
        public ComponentParameter element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subobjectParameter"
    // JLoP.g:245:1: subobjectParameter returns [ComponentParameter element] : (single= singleSubobjectParameter | multi= multiSubobjectParameter );
    public final JLo_JLoP.subobjectParameter_return subobjectParameter() throws RecognitionException {
        JLo_JLoP.subobjectParameter_return retval = new JLo_JLoP.subobjectParameter_return();
        retval.start = input.LT(1);
        int subobjectParameter_StartIndex = input.index();
        Object root_0 = null;

        JLo_JLoP.singleSubobjectParameter_return single = null;

        JLo_JLoP.multiSubobjectParameter_return multi = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 432) ) { return retval; }
            // JLoP.g:246:2: (single= singleSubobjectParameter | multi= multiSubobjectParameter )
            int alt18=2;
            switch ( input.LA(1) ) {
            case Identifier:
                {
                int LA18_1 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;
                }
                }
                break;
            case Export:
                {
                int LA18_2 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 2, input);

                    throw nvae;
                }
                }
                break;
            case Connector:
                {
                int LA18_3 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 3, input);

                    throw nvae;
                }
                }
                break;
            case Connect:
                {
                int LA18_4 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 4, input);

                    throw nvae;
                }
                }
                break;
            case Name:
                {
                int LA18_5 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 5, input);

                    throw nvae;
                }
                }
                break;
            case Overrides:
                {
                int LA18_6 = input.LA(2);

                if ( (synpred34_JLoP()) ) {
                    alt18=1;
                }
                else if ( (true) ) {
                    alt18=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 6, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // JLoP.g:246:4: single= singleSubobjectParameter
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_singleSubobjectParameter_in_subobjectParameter1260);
                    single=singleSubobjectParameter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, single.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = single.element;
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:247:5: multi= multiSubobjectParameter
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_multiSubobjectParameter_in_subobjectParameter1270);
                    multi=multiSubobjectParameter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, multi.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = multi.element;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 432, subobjectParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "subobjectParameter"

    public static class singleSubobjectParameter_return extends ParserRuleReturnScope {
        public ComponentParameter element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "singleSubobjectParameter"
    // JLoP.g:250:1: singleSubobjectParameter returns [ComponentParameter element] : id= identifierRule tcontainer= type arrow= '->' tcomp= type ;
    public final JLo_JLoP.singleSubobjectParameter_return singleSubobjectParameter() throws RecognitionException {
        JLo_JLoP.singleSubobjectParameter_return retval = new JLo_JLoP.singleSubobjectParameter_return();
        retval.start = input.LT(1);
        int singleSubobjectParameter_StartIndex = input.index();
        Object root_0 = null;

        Token arrow=null;
        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP_JavaP.type_return tcontainer = null;

        JLo_JLoP_JavaP.type_return tcomp = null;


        Object arrow_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 433) ) { return retval; }
            // JLoP.g:251:2: (id= identifierRule tcontainer= type arrow= '->' tcomp= type )
            // JLoP.g:251:4: id= identifierRule tcontainer= type arrow= '->' tcomp= type
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_identifierRule_in_singleSubobjectParameter1289);
            id=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
            pushFollow(FOLLOW_type_in_singleSubobjectParameter1293);
            tcontainer=gJLo.type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tcontainer.getTree());
            arrow=(Token)match(input,147,FOLLOW_147_in_singleSubobjectParameter1297); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            arrow_tree = (Object)adaptor.create(arrow);
            adaptor.addChild(root_0, arrow_tree);
            }
            pushFollow(FOLLOW_type_in_singleSubobjectParameter1301);
            tcomp=gJLo.type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tcomp.getTree());
            if ( state.backtracking==0 ) {
              retval.element = new SingleFormalComponentParameter(((id!=null?input.toString(id.start,id.stop):null)),tcontainer.element,tcomp.element);
              	   setLocation(retval.element,id.start,tcomp.stop);
              	   setKeyword(tcontainer.element,arrow);
              	   setName(retval.element,id.start);
              	   
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 433, singleSubobjectParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "singleSubobjectParameter"

    public static class multiSubobjectParameter_return extends ParserRuleReturnScope {
        public ComponentParameter element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiSubobjectParameter"
    // JLoP.g:259:1: multiSubobjectParameter returns [ComponentParameter element] : id= identifierRule tcontainer= type arrow= '->' '[' tcomp= type fin= ']' ;
    public final JLo_JLoP.multiSubobjectParameter_return multiSubobjectParameter() throws RecognitionException {
        JLo_JLoP.multiSubobjectParameter_return retval = new JLo_JLoP.multiSubobjectParameter_return();
        retval.start = input.LT(1);
        int multiSubobjectParameter_StartIndex = input.index();
        Object root_0 = null;

        Token arrow=null;
        Token fin=null;
        Token char_literal20=null;
        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP_JavaP.type_return tcontainer = null;

        JLo_JLoP_JavaP.type_return tcomp = null;


        Object arrow_tree=null;
        Object fin_tree=null;
        Object char_literal20_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 434) ) { return retval; }
            // JLoP.g:260:2: (id= identifierRule tcontainer= type arrow= '->' '[' tcomp= type fin= ']' )
            // JLoP.g:260:5: id= identifierRule tcontainer= type arrow= '->' '[' tcomp= type fin= ']'
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_identifierRule_in_multiSubobjectParameter1325);
            id=identifierRule();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
            pushFollow(FOLLOW_type_in_multiSubobjectParameter1329);
            tcontainer=gJLo.type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tcontainer.getTree());
            arrow=(Token)match(input,147,FOLLOW_147_in_multiSubobjectParameter1333); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            arrow_tree = (Object)adaptor.create(arrow);
            adaptor.addChild(root_0, arrow_tree);
            }
            char_literal20=(Token)match(input,76,FOLLOW_76_in_multiSubobjectParameter1335); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal20_tree = (Object)adaptor.create(char_literal20);
            adaptor.addChild(root_0, char_literal20_tree);
            }
            pushFollow(FOLLOW_type_in_multiSubobjectParameter1339);
            tcomp=gJLo.type();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tcomp.getTree());
            fin=(Token)match(input,77,FOLLOW_77_in_multiSubobjectParameter1343); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            fin_tree = (Object)adaptor.create(fin);
            adaptor.addChild(root_0, fin_tree);
            }
            if ( state.backtracking==0 ) {
              retval.element = new MultiFormalComponentParameter(((id!=null?input.toString(id.start,id.stop):null)),tcontainer.element,tcomp.element);
              	   setLocation(retval.element,id.start,fin);
              	   setKeyword(tcontainer.element,arrow);
              	   setName(retval.element,id.start);
              	   
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 434, multiSubobjectParameter_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiSubobjectParameter"

    public static class subobjectArgument_return extends ParserRuleReturnScope {
        public ActualComponentArgument element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subobjectArgument"
    // JLoP.g:268:1: subobjectArgument returns [ActualComponentArgument element] : (s= singleSubobjectArgument | ss= multiSubobjectArgument );
    public final JLo_JLoP.subobjectArgument_return subobjectArgument() throws RecognitionException {
        JLo_JLoP.subobjectArgument_return retval = new JLo_JLoP.subobjectArgument_return();
        retval.start = input.LT(1);
        int subobjectArgument_StartIndex = input.index();
        Object root_0 = null;

        JLo_JLoP.singleSubobjectArgument_return s = null;

        JLo_JLoP.multiSubobjectArgument_return ss = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 435) ) { return retval; }
            // JLoP.g:269:3: (s= singleSubobjectArgument | ss= multiSubobjectArgument )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==Identifier||(LA19_0>=Export && LA19_0<=Overrides)||LA19_0==102) ) {
                alt19=1;
            }
            else if ( (LA19_0==76) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // JLoP.g:270:5: s= singleSubobjectArgument
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_singleSubobjectArgument_in_subobjectArgument1370);
                    s=singleSubobjectArgument();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=s.element;
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:271:6: ss= multiSubobjectArgument
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_multiSubobjectArgument_in_subobjectArgument1382);
                    ss=multiSubobjectArgument();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ss.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element=ss.element;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 435, subobjectArgument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "subobjectArgument"

    public static class singleSubobjectArgument_return extends ParserRuleReturnScope {
        public SingleActualComponentArgument element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "singleSubobjectArgument"
    // JLoP.g:274:1: singleSubobjectArgument returns [SingleActualComponentArgument element] : (id= identifierRule | at= '@' idd= identifierRule );
    public final JLo_JLoP.singleSubobjectArgument_return singleSubobjectArgument() throws RecognitionException {
        JLo_JLoP.singleSubobjectArgument_return retval = new JLo_JLoP.singleSubobjectArgument_return();
        retval.start = input.LT(1);
        int singleSubobjectArgument_StartIndex = input.index();
        Object root_0 = null;

        Token at=null;
        JLo_JLoP.identifierRule_return id = null;

        JLo_JLoP.identifierRule_return idd = null;


        Object at_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 436) ) { return retval; }
            // JLoP.g:275:2: (id= identifierRule | at= '@' idd= identifierRule )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==Identifier||(LA20_0>=Export && LA20_0<=Overrides)) ) {
                alt20=1;
            }
            else if ( (LA20_0==102) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // JLoP.g:276:3: id= identifierRule
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_identifierRule_in_singleSubobjectArgument1406);
                    id=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, id.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new ComponentNameActualArgument((id!=null?input.toString(id.start,id.stop):null));
                      	                     setLocation(retval.element,id.start,id.stop);
                                                 
                    }

                    }
                    break;
                case 2 :
                    // JLoP.g:279:11: at= '@' idd= identifierRule
                    {
                    root_0 = (Object)adaptor.nil();

                    at=(Token)match(input,102,FOLLOW_102_in_singleSubobjectArgument1422); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    at_tree = (Object)adaptor.create(at);
                    adaptor.addChild(root_0, at_tree);
                    }
                    pushFollow(FOLLOW_identifierRule_in_singleSubobjectArgument1426);
                    idd=identifierRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, idd.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element = new ParameterReferenceActualArgument((idd!=null?input.toString(idd.start,idd.stop):null));
                              			     setLocation(retval.element,at,idd.stop);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 436, singleSubobjectArgument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "singleSubobjectArgument"

    public static class multiSubobjectArgument_return extends ParserRuleReturnScope {
        public MultiActualComponentArgument element;
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "multiSubobjectArgument"
    // JLoP.g:283:1: multiSubobjectArgument returns [MultiActualComponentArgument element] : start= '[' (single= singleSubobjectArgument ( ',' singlee= singleSubobjectArgument )* )? stop= ']' ;
    public final JLo_JLoP.multiSubobjectArgument_return multiSubobjectArgument() throws RecognitionException {
        JLo_JLoP.multiSubobjectArgument_return retval = new JLo_JLoP.multiSubobjectArgument_return();
        retval.start = input.LT(1);
        int multiSubobjectArgument_StartIndex = input.index();
        Object root_0 = null;

        Token start=null;
        Token stop=null;
        Token char_literal21=null;
        JLo_JLoP.singleSubobjectArgument_return single = null;

        JLo_JLoP.singleSubobjectArgument_return singlee = null;


        Object start_tree=null;
        Object stop_tree=null;
        Object char_literal21_tree=null;

        retval.element = new MultiActualComponentArgument();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 437) ) { return retval; }
            // JLoP.g:285:2: (start= '[' (single= singleSubobjectArgument ( ',' singlee= singleSubobjectArgument )* )? stop= ']' )
            // JLoP.g:286:3: start= '[' (single= singleSubobjectArgument ( ',' singlee= singleSubobjectArgument )* )? stop= ']'
            {
            root_0 = (Object)adaptor.nil();

            start=(Token)match(input,76,FOLLOW_76_in_multiSubobjectArgument1452); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            start_tree = (Object)adaptor.create(start);
            adaptor.addChild(root_0, start_tree);
            }
            // JLoP.g:286:13: (single= singleSubobjectArgument ( ',' singlee= singleSubobjectArgument )* )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==Identifier||(LA22_0>=Export && LA22_0<=Overrides)||LA22_0==102) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // JLoP.g:286:14: single= singleSubobjectArgument ( ',' singlee= singleSubobjectArgument )*
                    {
                    pushFollow(FOLLOW_singleSubobjectArgument_in_multiSubobjectArgument1457);
                    single=singleSubobjectArgument();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, single.getTree());
                    if ( state.backtracking==0 ) {
                      retval.element.add(single.element);
                    }
                    // JLoP.g:287:7: ( ',' singlee= singleSubobjectArgument )*
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( (LA21_0==69) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // JLoP.g:287:8: ',' singlee= singleSubobjectArgument
                    	    {
                    	    char_literal21=(Token)match(input,69,FOLLOW_69_in_multiSubobjectArgument1469); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal21_tree = (Object)adaptor.create(char_literal21);
                    	    adaptor.addChild(root_0, char_literal21_tree);
                    	    }
                    	    pushFollow(FOLLOW_singleSubobjectArgument_in_multiSubobjectArgument1473);
                    	    singlee=singleSubobjectArgument();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, singlee.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      retval.element.add(singlee.element);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop21;
                        }
                    } while (true);


                    }
                    break;

            }

            stop=(Token)match(input,77,FOLLOW_77_in_multiSubobjectArgument1487); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            stop_tree = (Object)adaptor.create(stop);
            adaptor.addChild(root_0, stop_tree);
            }
            if ( state.backtracking==0 ) {
               setLocation(retval.element,start,stop);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 437, multiSubobjectArgument_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "multiSubobjectArgument"

    // $ANTLR start synpred7_JLoP
    public final void synpred7_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP_JavaP.memberDeclaration_return mem = null;


        // JLoP.g:92:9: (mem= memberDeclaration )
        // JLoP.g:92:9: mem= memberDeclaration
        {
        pushFollow(FOLLOW_memberDeclaration_in_synpred7_JLoP204);
        mem=gJLo.memberDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_JLoP

    // $ANTLR start synpred9_JLoP
    public final void synpred9_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP_JavaP.constructorDeclaration_return cs = null;


        // JLoP.g:94:9: (cs= constructorDeclaration )
        // JLoP.g:94:9: cs= constructorDeclaration
        {
        pushFollow(FOLLOW_constructorDeclaration_in_synpred9_JLoP232);
        cs=gJLo.constructorDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_JLoP

    // $ANTLR start synpred13_JLoP
    public final void synpred13_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP.exportDeclaration_return exp = null;


        // JLoP.g:98:9: (exp= exportDeclaration )
        // JLoP.g:98:9: exp= exportDeclaration
        {
        pushFollow(FOLLOW_exportDeclaration_in_synpred13_JLoP288);
        exp=exportDeclaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_JLoP

    // $ANTLR start synpred14_JLoP
    public final void synpred14_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP.overridesClause_return ov = null;


        // JLoP.g:99:9: (ov= overridesClause )
        // JLoP.g:99:9: ov= overridesClause
        {
        pushFollow(FOLLOW_overridesClause_in_synpred14_JLoP302);
        ov=overridesClause();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_JLoP

    // $ANTLR start synpred15_JLoP
    public final void synpred15_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP.nameParameter_return np = null;


        // JLoP.g:100:9: (np= nameParameter )
        // JLoP.g:100:9: np= nameParameter
        {
        pushFollow(FOLLOW_nameParameter_in_synpred15_JLoP316);
        np=nameParameter();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_JLoP

    // $ANTLR start synpred16_JLoP
    public final void synpred16_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP.connector_return con = null;


        // JLoP.g:101:9: (con= connector )
        // JLoP.g:101:9: con= connector
        {
        pushFollow(FOLLOW_connector_in_synpred16_JLoP330);
        con=connector();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_JLoP

    // $ANTLR start synpred24_JLoP
    public final void synpred24_JLoP_fragment() throws RecognitionException {   
        Token ov=null;
        JLo_JLoP.signature_return sig = null;

        JLo_JLoP.fqn_return f = null;


        // JLoP.g:160:4: (sig= signature ov= '>' f= fqn )
        // JLoP.g:160:4: sig= signature ov= '>' f= fqn
        {
        pushFollow(FOLLOW_signature_in_synpred24_JLoP788);
        sig=signature();

        state._fsp--;
        if (state.failed) return ;
        ov=(Token)match(input,70,FOLLOW_70_in_synpred24_JLoP792); if (state.failed) return ;
        pushFollow(FOLLOW_fqn_in_synpred24_JLoP796);
        f=fqn();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred24_JLoP

    // $ANTLR start synpred29_JLoP
    public final void synpred29_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP_JavaP.assignmentOperator_return op = null;

        JLo_JLoP.expression_return exx = null;


        // JLoP.g:197:64: (op= assignmentOperator exx= expression )
        // JLoP.g:197:64: op= assignmentOperator exx= expression
        {
        pushFollow(FOLLOW_assignmentOperator_in_synpred29_JLoP1047);
        op=gJLo.assignmentOperator();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred29_JLoP1051);
        exx=expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred29_JLoP

    // $ANTLR start synpred34_JLoP
    public final void synpred34_JLoP_fragment() throws RecognitionException {   
        JLo_JLoP.singleSubobjectParameter_return single = null;


        // JLoP.g:246:4: (single= singleSubobjectParameter )
        // JLoP.g:246:4: single= singleSubobjectParameter
        {
        pushFollow(FOLLOW_singleSubobjectParameter_in_synpred34_JLoP1260);
        single=singleSubobjectParameter();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred34_JLoP

    // Delegated rules

    public final boolean synpred29_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred29_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_JLoP() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_JLoP_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA2_eotS =
        "\34\uffff";
    static final String DFA2_eofS =
        "\34\uffff";
    static final String DFA2_minS =
        "\1\4\1\uffff\6\0\24\uffff";
    static final String DFA2_maxS =
        "\1\u008e\1\uffff\6\0\24\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\6\uffff\1\2\7\uffff\1\3\1\5\1\uffff\1\6\1\uffff\1\7"+
        "\1\4\1\11\1\10\1\13\1\14\1\12";
    static final String DFA2_specialS =
        "\2\uffff\1\0\1\1\1\2\1\3\1\4\1\5\24\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\2\1\23\5\uffff\1\3\1\4\1\5\1\6\1\7\61\uffff\1\23\2\uffff"+
            "\1\1\5\uffff\1\21\1\20\11\uffff\10\10\11\uffff\1\21\47\uffff"+
            "\1\25",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "89:1: memberDecl returns [TypeElement element] : (gen= genericMethodOrConstructorDecl | mem= memberDeclaration | vmd= voidMethodDeclaration | cs= constructorDeclaration | id= interfaceDeclaration | cd= classDeclaration | comp= subobjectDeclaration | exp= exportDeclaration | ov= overridesClause | np= nameParameter | con= connector | cnct= connection );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_2 = input.LA(1);

                         
                        int index2_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                         
                        input.seek(index2_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_3 = input.LA(1);

                         
                        int index2_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred13_JLoP()) ) {s = 24;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                         
                        input.seek(index2_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_4 = input.LA(1);

                         
                        int index2_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                        else if ( (synpred16_JLoP()) ) {s = 25;}

                         
                        input.seek(index2_4);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_5 = input.LA(1);

                         
                        int index2_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                        else if ( (true) ) {s = 26;}

                         
                        input.seek(index2_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_6 = input.LA(1);

                         
                        int index2_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                        else if ( (synpred15_JLoP()) ) {s = 27;}

                         
                        input.seek(index2_6);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA2_7 = input.LA(1);

                         
                        int index2_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_JLoP()) ) {s = 8;}

                        else if ( (synpred9_JLoP()) ) {s = 22;}

                        else if ( (synpred14_JLoP()) ) {s = 23;}

                         
                        input.seek(index2_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 2, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA15_eotS =
        "\16\uffff";
    static final String DFA15_eofS =
        "\1\14\15\uffff";
    static final String DFA15_minS =
        "\1\66\13\0\2\uffff";
    static final String DFA15_maxS =
        "\1\176\13\0\2\uffff";
    static final String DFA15_acceptS =
        "\14\uffff\1\2\1\1";
    static final String DFA15_specialS =
        "\1\uffff\1\12\1\4\1\11\1\3\1\6\1\1\1\5\1\0\1\10\1\7\1\2\2\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\14\15\uffff\1\12\1\14\1\13\2\uffff\1\14\3\uffff\1\14\1\uffff"+
            "\1\1\20\uffff\1\14\7\uffff\1\14\16\uffff\1\2\1\3\1\4\1\5\1\6"+
            "\1\7\1\10\1\11",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "197:63: (op= assignmentOperator exx= expression )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA15_8 = input.LA(1);

                         
                        int index15_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_8);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA15_6 = input.LA(1);

                         
                        int index15_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA15_11 = input.LA(1);

                         
                        int index15_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA15_4 = input.LA(1);

                         
                        int index15_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA15_2 = input.LA(1);

                         
                        int index15_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_2);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA15_7 = input.LA(1);

                         
                        int index15_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_7);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA15_5 = input.LA(1);

                         
                        int index15_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_5);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA15_10 = input.LA(1);

                         
                        int index15_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_10);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA15_9 = input.LA(1);

                         
                        int index15_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA15_3 = input.LA(1);

                         
                        int index15_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_3);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA15_1 = input.LA(1);

                         
                        int index15_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred29_JLoP()) ) {s = 13;}

                        else if ( (true) ) {s = 12;}

                         
                        input.seek(index15_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 15, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_Identifier_in_identifierRule74 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Export_in_identifierRule89 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Connector_in_identifierRule105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Connect_in_identifierRule121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Name_in_identifierRule137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Overrides_in_identifierRule153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_genericMethodOrConstructorDecl_in_memberDecl190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_memberDeclaration_in_memberDecl204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_voidMethodDeclaration_in_memberDecl218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constructorDeclaration_in_memberDecl232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_interfaceDeclaration_in_memberDecl246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classDeclaration_in_memberDecl260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subobjectDeclaration_in_memberDecl274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exportDeclaration_in_memberDecl288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_overridesClause_in_memberDecl302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameParameter_in_memberDecl316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_connector_in_memberDecl330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_connection_in_memberDecl344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Connector_in_connector367 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_subobjectParameter_in_connector371 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_connector374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Connect_in_connection394 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_connection398 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_connection402 = new BitSet(new long[]{0x000000000000F810L,0x0000004000001000L});
    public static final BitSet FOLLOW_subobjectArgument_in_connection406 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_connection410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Name_in_nameParameter437 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_nameParameter439 = new BitSet(new long[]{0x0040000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_nameParameter442 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_memberName_in_nameParameter444 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_nameParameter448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_memberName473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Export_in_exportDeclaration500 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_map_in_exportDeclaration506 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_exportDeclaration511 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_map_in_exportDeclaration515 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_54_in_exportDeclaration521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signature_in_overridesClause547 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_Overrides_in_overridesClause551 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_fqn_in_overridesClause555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fqn_in_map580 = new BitSet(new long[]{0x000000000000F812L});
    public static final BitSet FOLLOW_identifierRule_in_map592 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_signature_in_map596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_subobjectDeclaration631 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_subobjectDeclaration635 = new BitSet(new long[]{0x004000000000F810L,0x000000001FE00100L});
    public static final BitSet FOLLOW_type_in_subobjectDeclaration639 = new BitSet(new long[]{0x0040000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_classBody_in_subobjectDeclaration673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_subobjectDeclaration679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_143_in_configurationBlock728 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_configurationBlock730 = new BitSet(new long[]{0x000000000000F810L,0x0000000000000200L});
    public static final BitSet FOLLOW_configurationClause_in_configurationBlock737 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000220L});
    public static final BitSet FOLLOW_69_in_configurationBlock741 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_configurationClause_in_configurationBlock745 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000220L});
    public static final BitSet FOLLOW_73_in_configurationBlock752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signature_in_configurationClause788 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_configurationClause792 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_fqn_in_configurationClause796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signature_in_configurationClause813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_configurationClause817 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_fqn_in_configurationClause821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_signature855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_signature871 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_signature892 = new BitSet(new long[]{0x000000000000F810L,0x000000011FE00000L});
    public static final BitSet FOLLOW_type_in_signature897 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000020L});
    public static final BitSet FOLLOW_69_in_signature920 = new BitSet(new long[]{0x000000000000F810L,0x000000001FE00000L});
    public static final BitSet FOLLOW_type_in_signature924 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000020L});
    public static final BitSet FOLLOW_96_in_signature931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signature_in_fqn971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_fqn991 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_fqn993 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_fqn_in_fqn997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalExpression_in_expression1040 = new BitSet(new long[]{0x0000000000000002L,0x7F80000000008050L});
    public static final BitSet FOLLOW_assignmentOperator_in_expression1047 = new BitSet(new long[]{0x000000000000FBD0L,0x0000003CDFE00800L,0x0000000000077E60L});
    public static final BitSet FOLLOW_expression_in_expression1051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_142_in_expression1087 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_expression1089 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_expression1093 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_arguments_in_expression1097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_nonTargetPrimary1133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_144_in_nonTargetPrimary1144 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_nonTargetPrimary1148 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_nonTargetPrimary1150 = new BitSet(new long[]{0x000000000000FBD0L,0x0000003CDFE00800L,0x0000000000077E60L});
    public static final BitSet FOLLOW_expression_in_nonTargetPrimary1154 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_nonTargetPrimary1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_145_in_nonTargetPrimary1187 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_superSuffix_in_nonTargetPrimary1191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_146_in_nonTargetPrimary1219 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_superSuffix_in_nonTargetPrimary1223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_singleSubobjectParameter_in_subobjectParameter1260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiSubobjectParameter_in_subobjectParameter1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_singleSubobjectParameter1289 = new BitSet(new long[]{0x000000000000F810L,0x000000001FE00000L});
    public static final BitSet FOLLOW_type_in_singleSubobjectParameter1293 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_147_in_singleSubobjectParameter1297 = new BitSet(new long[]{0x000000000000F810L,0x000000001FE00000L});
    public static final BitSet FOLLOW_type_in_singleSubobjectParameter1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_multiSubobjectParameter1325 = new BitSet(new long[]{0x000000000000F810L,0x000000001FE00000L});
    public static final BitSet FOLLOW_type_in_multiSubobjectParameter1329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_147_in_multiSubobjectParameter1333 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_multiSubobjectParameter1335 = new BitSet(new long[]{0x000000000000F810L,0x000000001FE00000L});
    public static final BitSet FOLLOW_type_in_multiSubobjectParameter1339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_multiSubobjectParameter1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_singleSubobjectArgument_in_subobjectArgument1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiSubobjectArgument_in_subobjectArgument1382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identifierRule_in_singleSubobjectArgument1406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_singleSubobjectArgument1422 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_identifierRule_in_singleSubobjectArgument1426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_multiSubobjectArgument1452 = new BitSet(new long[]{0x000000000000F810L,0x0000004000002000L});
    public static final BitSet FOLLOW_singleSubobjectArgument_in_multiSubobjectArgument1457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002020L});
    public static final BitSet FOLLOW_69_in_multiSubobjectArgument1469 = new BitSet(new long[]{0x000000000000F810L,0x0000004000000000L});
    public static final BitSet FOLLOW_singleSubobjectArgument_in_multiSubobjectArgument1473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002020L});
    public static final BitSet FOLLOW_77_in_multiSubobjectArgument1487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_memberDeclaration_in_synpred7_JLoP204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constructorDeclaration_in_synpred9_JLoP232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exportDeclaration_in_synpred13_JLoP288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_overridesClause_in_synpred14_JLoP302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nameParameter_in_synpred15_JLoP316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_connector_in_synpred16_JLoP330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_signature_in_synpred24_JLoP788 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_synpred24_JLoP792 = new BitSet(new long[]{0x000000000000F810L});
    public static final BitSet FOLLOW_fqn_in_synpred24_JLoP796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentOperator_in_synpred29_JLoP1047 = new BitSet(new long[]{0x000000000000FBD0L,0x0000003CDFE00800L,0x0000000000077E60L});
    public static final BitSet FOLLOW_expression_in_synpred29_JLoP1051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_singleSubobjectParameter_in_synpred34_JLoP1260 = new BitSet(new long[]{0x0000000000000002L});

}
