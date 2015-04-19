// Generated from /home/marko/git/workspace/jlo/src/org/aikodi/jlo/input/JLo.g4 by ANTLR 4.5
package org.aikodi.jlo.input;
import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JLoParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, NAMESPACE=5, ABSTRACT=6, NATIVE=7, FIELD=8, 
		BLOCKING=9, PROPAGATING=10, LIKE=11, IMPORT=12, SELF=13, ARROW=14, SUBOBJECT=15, 
		EXTENDS=16, KLASS=17, LAYER=18, TRUE=19, FALSE=20, NULL=21, UNDERSCORE=22, 
		PIPE=23, MINUS=24, PLUS=25, SLASH=26, STAR=27, SEMI=28, DOT=29, COMMA=30, 
		LBRACE=31, RBRACE=32, LPAR=33, RPAR=34, ASSIGN=35, EQUAL=36, NOTEQUAL=37, 
		RETURN=38, EXPONENTIATION=39, AMPERSAND=40, TYPEANNOTATION=41, SMALLER=42, 
		BIGGER=43, LEFTSHIFT=44, RIGHTSHIFT=45, RIGHTRIGHTSHIFT=46, HexLiteral=47, 
		DecimalLiteral=48, OctalLiteral=49, BinaryLiteral=50, FloatingPointLiteral=51, 
		CharacterLiteral=52, StringLiteral=53, ASSERT=54, Identifier=55, WS=56, 
		COMMENT=57, LINE_COMMENT=58;
	public static final int
		RULE_compilationUnit = 0, RULE_namespace = 1, RULE_importDeclaration = 2, 
		RULE_namespaceReference = 3, RULE_klass = 4, RULE_classBody = 5, RULE_bodyElement = 6, 
		RULE_member = 7, RULE_method = 8, RULE_methodHeader = 9, RULE_returnType = 10, 
		RULE_keywordBlock = 11, RULE_modifier = 12, RULE_parameters = 13, RULE_parameterList = 14, 
		RULE_parameter = 15, RULE_implementation = 16, RULE_subobject = 17, RULE_inheritanceRelation = 18, 
		RULE_type = 19, RULE_qualifiedName = 20, RULE_expression = 21, RULE_block = 22, 
		RULE_arguments = 23, RULE_statement = 24, RULE_literal = 25, RULE_integerNumberLiteral = 26, 
		RULE_booleanLiteral = 27;
	public static final String[] ruleNames = {
		"compilationUnit", "namespace", "importDeclaration", "namespaceReference", 
		"klass", "classBody", "bodyElement", "member", "method", "methodHeader", 
		"returnType", "keywordBlock", "modifier", "parameters", "parameterList", 
		"parameter", "implementation", "subobject", "inheritanceRelation", "type", 
		"qualifiedName", "expression", "block", "arguments", "statement", "literal", 
		"integerNumberLiteral", "booleanLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'super'", "'%'", "'<='", "'>='", "'namespace'", "'abstract'", "'native'", 
		"'field'", "'blocking'", "'propagating'", "'like'", "'import'", "'self'", 
		"'->'", "'subobject'", "'extends'", "'class'", "'layer'", "'true'", "'false'", 
		"'null'", "'_'", "'|'", "'-'", "'+'", "'/'", "'*'", "';'", "'.'", "','", 
		"'{'", "'}'", "'('", "')'", "'='", "'=='", "'!='", "'return'", "'^'", 
		"'&'", "':'", "'<'", "'>'", "'<<'", "'>>'", "'>>>'", null, null, null, 
		null, null, null, null, "'assert'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "NAMESPACE", "ABSTRACT", "NATIVE", "FIELD", 
		"BLOCKING", "PROPAGATING", "LIKE", "IMPORT", "SELF", "ARROW", "SUBOBJECT", 
		"EXTENDS", "KLASS", "LAYER", "TRUE", "FALSE", "NULL", "UNDERSCORE", "PIPE", 
		"MINUS", "PLUS", "SLASH", "STAR", "SEMI", "DOT", "COMMA", "LBRACE", "RBRACE", 
		"LPAR", "RPAR", "ASSIGN", "EQUAL", "NOTEQUAL", "RETURN", "EXPONENTIATION", 
		"AMPERSAND", "TYPEANNOTATION", "SMALLER", "BIGGER", "LEFTSHIFT", "RIGHTSHIFT", 
		"RIGHTRIGHTSHIFT", "HexLiteral", "DecimalLiteral", "OctalLiteral", "BinaryLiteral", 
		"FloatingPointLiteral", "CharacterLiteral", "StringLiteral", "ASSERT", 
		"Identifier", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JLo.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JLoParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CompilationUnitContext extends ParserRuleContext {
		public NamespaceContext namespace() {
			return getRuleContext(NamespaceContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JLoParser.EOF, 0); }
		public List<ImportDeclarationContext> importDeclaration() {
			return getRuleContexts(ImportDeclarationContext.class);
		}
		public ImportDeclarationContext importDeclaration(int i) {
			return getRuleContext(ImportDeclarationContext.class,i);
		}
		public List<KlassContext> klass() {
			return getRuleContexts(KlassContext.class);
		}
		public KlassContext klass(int i) {
			return getRuleContext(KlassContext.class,i);
		}
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterCompilationUnit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitCompilationUnit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			namespace();
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(57);
				importDeclaration();
				}
				}
				setState(62);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ABSTRACT || _la==KLASS) {
				{
				{
				setState(63);
				klass();
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceContext extends ParserRuleContext {
		public TerminalNode NAMESPACE() { return getToken(JLoParser.NAMESPACE, 0); }
		public NamespaceReferenceContext namespaceReference() {
			return getRuleContext(NamespaceReferenceContext.class,0);
		}
		public NamespaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespace; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterNamespace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitNamespace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitNamespace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceContext namespace() throws RecognitionException {
		NamespaceContext _localctx = new NamespaceContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_namespace);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(NAMESPACE);
			setState(72);
			namespaceReference();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportDeclarationContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(JLoParser.IMPORT, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ImportDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterImportDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitImportDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitImportDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportDeclarationContext importDeclaration() throws RecognitionException {
		ImportDeclarationContext _localctx = new ImportDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_importDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(IMPORT);
			setState(75);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamespaceReferenceContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(JLoParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JLoParser.Identifier, i);
		}
		public List<TerminalNode> DOT() { return getTokens(JLoParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(JLoParser.DOT, i);
		}
		public NamespaceReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespaceReference; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterNamespaceReference(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitNamespaceReference(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitNamespaceReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamespaceReferenceContext namespaceReference() throws RecognitionException {
		NamespaceReferenceContext _localctx = new NamespaceReferenceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_namespaceReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(Identifier);
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(78);
				match(DOT);
				setState(79);
				match(Identifier);
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KlassContext extends ParserRuleContext {
		public TerminalNode KLASS() { return getToken(JLoParser.KLASS, 0); }
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public InheritanceRelationContext inheritanceRelation() {
			return getRuleContext(InheritanceRelationContext.class,0);
		}
		public KlassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_klass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterKlass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitKlass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitKlass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KlassContext klass() throws RecognitionException {
		KlassContext _localctx = new KlassContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_klass);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ABSTRACT) {
				{
				{
				setState(85);
				modifier();
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(91);
			match(KLASS);
			setState(92);
			match(Identifier);
			setState(94);
			_la = _input.LA(1);
			if (_la==EXTENDS) {
				{
				setState(93);
				inheritanceRelation();
				}
			}

			setState(96);
			classBody();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(JLoParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JLoParser.RBRACE, 0); }
		public List<BodyElementContext> bodyElement() {
			return getRuleContexts(BodyElementContext.class);
		}
		public BodyElementContext bodyElement(int i) {
			return getRuleContext(BodyElementContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterClassBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitClassBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(LBRACE);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ABSTRACT) | (1L << FIELD) | (1L << Identifier))) != 0)) {
				{
				{
				setState(99);
				bodyElement();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BodyElementContext extends ParserRuleContext {
		public MemberContext member() {
			return getRuleContext(MemberContext.class,0);
		}
		public BodyElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bodyElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterBodyElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitBodyElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitBodyElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyElementContext bodyElement() throws RecognitionException {
		BodyElementContext _localctx = new BodyElementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_bodyElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			member();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberContext extends ParserRuleContext {
		public MemberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_member; }
	 
		public MemberContext() { }
		public void copyFrom(MemberContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MemberMethodContext extends MemberContext {
		public MethodContext method() {
			return getRuleContext(MethodContext.class,0);
		}
		public MemberMethodContext(MemberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterMemberMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitMemberMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitMemberMethod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemberSubobjectContext extends MemberContext {
		public SubobjectContext subobject() {
			return getRuleContext(SubobjectContext.class,0);
		}
		public MemberSubobjectContext(MemberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterMemberSubobject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitMemberSubobject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitMemberSubobject(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MemberFieldContext extends MemberContext {
		public TerminalNode FIELD() { return getToken(JLoParser.FIELD, 0); }
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public TerminalNode TYPEANNOTATION() { return getToken(JLoParser.TYPEANNOTATION, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public MemberFieldContext(MemberContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterMemberField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitMemberField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitMemberField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberContext member() throws RecognitionException {
		MemberContext _localctx = new MemberContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_member);
		try {
			setState(115);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				_localctx = new MemberMethodContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(109);
				method();
				}
				break;
			case 2:
				_localctx = new MemberSubobjectContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				subobject();
				}
				break;
			case 3:
				_localctx = new MemberFieldContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(111);
				match(FIELD);
				setState(112);
				match(Identifier);
				setState(113);
				match(TYPEANNOTATION);
				setState(114);
				type();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodContext extends ParserRuleContext {
		public MethodHeaderContext methodHeader() {
			return getRuleContext(MethodHeaderContext.class,0);
		}
		public ImplementationContext implementation() {
			return getRuleContext(ImplementationContext.class,0);
		}
		public MethodContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_method; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterMethod(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitMethod(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitMethod(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodContext method() throws RecognitionException {
		MethodContext _localctx = new MethodContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_method);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			methodHeader();
			setState(118);
			implementation();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodHeaderContext extends ParserRuleContext {
		public KeywordBlockContext keywordBlock() {
			return getRuleContext(KeywordBlockContext.class,0);
		}
		public List<ModifierContext> modifier() {
			return getRuleContexts(ModifierContext.class);
		}
		public ModifierContext modifier(int i) {
			return getRuleContext(ModifierContext.class,i);
		}
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public MethodHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterMethodHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitMethodHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitMethodHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodHeaderContext methodHeader() throws RecognitionException {
		MethodHeaderContext _localctx = new MethodHeaderContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_methodHeader);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ABSTRACT) {
				{
				{
				setState(120);
				modifier();
				}
				}
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(126);
			keywordBlock();
			setState(128);
			_la = _input.LA(1);
			if (_la==TYPEANNOTATION) {
				{
				setState(127);
				returnType();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public TerminalNode TYPEANNOTATION() { return getToken(JLoParser.TYPEANNOTATION, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterReturnType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitReturnType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitReturnType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_returnType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(TYPEANNOTATION);
			setState(131);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeywordBlockContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public ParametersContext parameters() {
			return getRuleContext(ParametersContext.class,0);
		}
		public KeywordBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keywordBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterKeywordBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitKeywordBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitKeywordBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeywordBlockContext keywordBlock() throws RecognitionException {
		KeywordBlockContext _localctx = new KeywordBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_keywordBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(Identifier);
			setState(135);
			_la = _input.LA(1);
			if (_la==LPAR) {
				{
				setState(134);
				parameters();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModifierContext extends ParserRuleContext {
		public ModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modifier; }
	 
		public ModifierContext() { }
		public void copyFrom(ModifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AbstractModifierContext extends ModifierContext {
		public TerminalNode ABSTRACT() { return getToken(JLoParser.ABSTRACT, 0); }
		public AbstractModifierContext(ModifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterAbstractModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitAbstractModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitAbstractModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModifierContext modifier() throws RecognitionException {
		ModifierContext _localctx = new ModifierContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_modifier);
		try {
			_localctx = new AbstractModifierContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(ABSTRACT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParametersContext extends ParserRuleContext {
		public TerminalNode LPAR() { return getToken(JLoParser.LPAR, 0); }
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public TerminalNode RPAR() { return getToken(JLoParser.RPAR, 0); }
		public ParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametersContext parameters() throws RecognitionException {
		ParametersContext _localctx = new ParametersContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_parameters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(LPAR);
			setState(140);
			parameterList();
			setState(141);
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JLoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JLoParser.COMMA, i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitParameterList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitParameterList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			parameter();
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(144);
				match(COMMA);
				setState(145);
				parameter();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_parameter);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			type();
			setState(152);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImplementationContext extends ParserRuleContext {
		public ImplementationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_implementation; }
	 
		public ImplementationContext() { }
		public void copyFrom(ImplementationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NativeImplementationContext extends ImplementationContext {
		public TerminalNode ASSIGN() { return getToken(JLoParser.ASSIGN, 0); }
		public TerminalNode NATIVE() { return getToken(JLoParser.NATIVE, 0); }
		public NativeImplementationContext(ImplementationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterNativeImplementation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitNativeImplementation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitNativeImplementation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AbstractImplementationContext extends ImplementationContext {
		public TerminalNode ASSIGN() { return getToken(JLoParser.ASSIGN, 0); }
		public TerminalNode ABSTRACT() { return getToken(JLoParser.ABSTRACT, 0); }
		public AbstractImplementationContext(ImplementationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterAbstractImplementation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitAbstractImplementation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitAbstractImplementation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExprImplementationContext extends ImplementationContext {
		public TerminalNode ASSIGN() { return getToken(JLoParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprImplementationContext(ImplementationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterExprImplementation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitExprImplementation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitExprImplementation(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BlockImplementationContext extends ImplementationContext {
		public TerminalNode ASSIGN() { return getToken(JLoParser.ASSIGN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockImplementationContext(ImplementationContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterBlockImplementation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitBlockImplementation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitBlockImplementation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImplementationContext implementation() throws RecognitionException {
		ImplementationContext _localctx = new ImplementationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_implementation);
		try {
			setState(162);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new ExprImplementationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				match(ASSIGN);
				setState(155);
				expression(0);
				}
				break;
			case 2:
				_localctx = new AbstractImplementationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(156);
				match(ASSIGN);
				setState(157);
				match(ABSTRACT);
				}
				break;
			case 3:
				_localctx = new NativeImplementationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(158);
				match(ASSIGN);
				setState(159);
				match(NATIVE);
				}
				break;
			case 4:
				_localctx = new BlockImplementationContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(160);
				match(ASSIGN);
				setState(161);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubobjectContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public TerminalNode TYPEANNOTATION() { return getToken(JLoParser.TYPEANNOTATION, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public SubobjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subobject; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterSubobject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitSubobject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitSubobject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubobjectContext subobject() throws RecognitionException {
		SubobjectContext _localctx = new SubobjectContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_subobject);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			match(Identifier);
			setState(165);
			match(TYPEANNOTATION);
			setState(166);
			type();
			setState(168);
			_la = _input.LA(1);
			if (_la==LBRACE) {
				{
				setState(167);
				classBody();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InheritanceRelationContext extends ParserRuleContext {
		public TerminalNode EXTENDS() { return getToken(JLoParser.EXTENDS, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InheritanceRelationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_inheritanceRelation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterInheritanceRelation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitInheritanceRelation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitInheritanceRelation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InheritanceRelationContext inheritanceRelation() throws RecognitionException {
		InheritanceRelationContext _localctx = new InheritanceRelationContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_inheritanceRelation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(EXTENDS);
			setState(171);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			qualifiedName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(JLoParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JLoParser.Identifier, i);
		}
		public List<TerminalNode> DOT() { return getTokens(JLoParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(JLoParser.DOT, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitQualifiedName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitQualifiedName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_qualifiedName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			match(Identifier);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(176);
				match(DOT);
				setState(177);
				match(Identifier);
				}
				}
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ShiftExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LEFTSHIFT() { return getToken(JLoParser.LEFTSHIFT, 0); }
		public TerminalNode RIGHTRIGHTSHIFT() { return getToken(JLoParser.RIGHTRIGHTSHIFT, 0); }
		public TerminalNode RIGHTSHIFT() { return getToken(JLoParser.RIGHTSHIFT, 0); }
		public ShiftExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterShiftExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitShiftExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitShiftExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentifierExpressionContext extends ExpressionContext {
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public IdentifierExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterIdentifierExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitIdentifierExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitIdentifierExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class QualifiedCallExpressionContext extends ExpressionContext {
		public Token name;
		public ArgumentsContext args;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(JLoParser.DOT, 0); }
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public QualifiedCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterQualifiedCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitQualifiedCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitQualifiedCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LowPriorityNumbericalExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(JLoParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(JLoParser.MINUS, 0); }
		public LowPriorityNumbericalExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterLowPriorityNumbericalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitLowPriorityNumbericalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitLowPriorityNumbericalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelfCallExpressionContext extends ExpressionContext {
		public Token name;
		public ArgumentsContext args;
		public TerminalNode Identifier() { return getToken(JLoParser.Identifier, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public SelfCallExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterSelfCallExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitSelfCallExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitSelfCallExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class HighPriorityNumbericalExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(JLoParser.STAR, 0); }
		public HighPriorityNumbericalExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterHighPriorityNumbericalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitHighPriorityNumbericalExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitHighPriorityNumbericalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SuperExpressionContext extends ExpressionContext {
		public SuperExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterSuperExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitSuperExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitSuperExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PIPE() { return getToken(JLoParser.PIPE, 0); }
		public OrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitOrExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitOrExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AMPERSAND() { return getToken(JLoParser.AMPERSAND, 0); }
		public AndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitAndExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitAndExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SelfExpressionContext extends ExpressionContext {
		public TerminalNode SELF() { return getToken(JLoParser.SELF, 0); }
		public SelfExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterSelfExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitSelfExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitSelfExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullExpressionContext extends ExpressionContext {
		public TerminalNode NULL() { return getToken(JLoParser.NULL, 0); }
		public NullExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterNullExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitNullExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitNullExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrderExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public OrderExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterOrderExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitOrderExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitOrderExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqualityExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(JLoParser.EQUAL, 0); }
		public TerminalNode NOTEQUAL() { return getToken(JLoParser.NOTEQUAL, 0); }
		public EqualityExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterEqualityExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitEqualityExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExponentiationExpressionContext extends ExpressionContext {
		public ExpressionContext left;
		public Token op;
		public ExpressionContext right;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EXPONENTIATION() { return getToken(JLoParser.EXPONENTIATION, 0); }
		public ExponentiationExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterExponentiationExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitExponentiationExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitExponentiationExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LiteralExpressionContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterLiteralExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitLiteralExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitLiteralExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParExpressionContext extends ExpressionContext {
		public ExpressionContext e;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterParExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitParExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitParExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(184);
				literal();
				}
				break;
			case 2:
				{
				_localctx = new NullExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(185);
				match(NULL);
				}
				break;
			case 3:
				{
				_localctx = new ParExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(186);
				match(LPAR);
				setState(187);
				((ParExpressionContext)_localctx).e = expression(0);
				setState(188);
				match(RPAR);
				}
				break;
			case 4:
				{
				_localctx = new SelfExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(190);
				match(SELF);
				}
				break;
			case 5:
				{
				_localctx = new SuperExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(191);
				match(T__0);
				}
				break;
			case 6:
				{
				_localctx = new IdentifierExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(192);
				match(Identifier);
				}
				break;
			case 7:
				{
				_localctx = new SelfCallExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(193);
				((SelfCallExpressionContext)_localctx).name = match(Identifier);
				setState(194);
				((SelfCallExpressionContext)_localctx).args = arguments();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(229);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(227);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new ExponentiationExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ExponentiationExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(197);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(198);
						((ExponentiationExpressionContext)_localctx).op = match(EXPONENTIATION);
						setState(199);
						((ExponentiationExpressionContext)_localctx).right = expression(9);
						}
						break;
					case 2:
						{
						_localctx = new HighPriorityNumbericalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((HighPriorityNumbericalExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(200);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(201);
						((HighPriorityNumbericalExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << SLASH) | (1L << STAR))) != 0)) ) {
							((HighPriorityNumbericalExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(202);
						((HighPriorityNumbericalExpressionContext)_localctx).right = expression(8);
						}
						break;
					case 3:
						{
						_localctx = new LowPriorityNumbericalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((LowPriorityNumbericalExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(203);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(204);
						((LowPriorityNumbericalExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==MINUS || _la==PLUS) ) {
							((LowPriorityNumbericalExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(205);
						((LowPriorityNumbericalExpressionContext)_localctx).right = expression(7);
						}
						break;
					case 4:
						{
						_localctx = new ShiftExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((ShiftExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(206);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(207);
						((ShiftExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LEFTSHIFT) | (1L << RIGHTSHIFT) | (1L << RIGHTRIGHTSHIFT))) != 0)) ) {
							((ShiftExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(208);
						((ShiftExpressionContext)_localctx).right = expression(6);
						}
						break;
					case 5:
						{
						_localctx = new OrderExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((OrderExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(209);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(210);
						((OrderExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << SMALLER) | (1L << BIGGER))) != 0)) ) {
							((OrderExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(211);
						((OrderExpressionContext)_localctx).right = expression(5);
						}
						break;
					case 6:
						{
						_localctx = new EqualityExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((EqualityExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(212);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(213);
						((EqualityExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==NOTEQUAL) ) {
							((EqualityExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(214);
						((EqualityExpressionContext)_localctx).right = expression(4);
						}
						break;
					case 7:
						{
						_localctx = new AndExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((AndExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(215);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(216);
						((AndExpressionContext)_localctx).op = match(AMPERSAND);
						setState(217);
						((AndExpressionContext)_localctx).right = expression(3);
						}
						break;
					case 8:
						{
						_localctx = new OrExpressionContext(new ExpressionContext(_parentctx, _parentState));
						((OrExpressionContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(218);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(219);
						((OrExpressionContext)_localctx).op = match(PIPE);
						setState(220);
						((OrExpressionContext)_localctx).right = expression(2);
						}
						break;
					case 9:
						{
						_localctx = new QualifiedCallExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(221);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(222);
						match(DOT);
						setState(223);
						((QualifiedCallExpressionContext)_localctx).name = match(Identifier);
						setState(225);
						switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
						case 1:
							{
							setState(224);
							((QualifiedCallExpressionContext)_localctx).args = arguments();
							}
							break;
						}
						}
						break;
					}
					} 
				}
				setState(231);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(JLoParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(JLoParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(LBRACE);
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << SELF) | (1L << TRUE) | (1L << FALSE) | (1L << NULL) | (1L << LPAR) | (1L << RETURN) | (1L << HexLiteral) | (1L << DecimalLiteral) | (1L << OctalLiteral) | (1L << BinaryLiteral) | (1L << FloatingPointLiteral) | (1L << CharacterLiteral) | (1L << StringLiteral) | (1L << Identifier))) != 0)) {
				{
				{
				setState(233);
				statement();
				}
				}
				setState(238);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(239);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public ExpressionContext f;
		public ExpressionContext s;
		public TerminalNode LPAR() { return getToken(JLoParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(JLoParser.RPAR, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(JLoParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JLoParser.COMMA, i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(LPAR);
			setState(242);
			((ArgumentsContext)_localctx).f = expression(0);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(243);
				match(COMMA);
				setState(244);
				((ArgumentsContext)_localctx).s = expression(0);
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(250);
			match(RPAR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignmentStatementContext extends StatementContext {
		public ExpressionContext var;
		public ExpressionContext val;
		public TerminalNode ASSIGN() { return getToken(JLoParser.ASSIGN, 0); }
		public TerminalNode SEMI() { return getToken(JLoParser.SEMI, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AssignmentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionStatementContext extends StatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JLoParser.SEMI, 0); }
		public ExpressionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStatementContext extends StatementContext {
		public TerminalNode RETURN() { return getToken(JLoParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(JLoParser.SEMI, 0); }
		public ReturnStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_statement);
		try {
			setState(264);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new ExpressionStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(252);
				expression(0);
				setState(253);
				match(SEMI);
				}
				break;
			case 2:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(255);
				match(RETURN);
				setState(256);
				expression(0);
				setState(257);
				match(SEMI);
				}
				break;
			case 3:
				_localctx = new AssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(259);
				((AssignmentStatementContext)_localctx).var = expression(0);
				setState(260);
				match(ASSIGN);
				setState(261);
				((AssignmentStatementContext)_localctx).val = expression(0);
				setState(262);
				match(SEMI);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CharacterLiteralContext extends LiteralContext {
		public TerminalNode CharacterLiteral() { return getToken(JLoParser.CharacterLiteral, 0); }
		public CharacterLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterCharacterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitCharacterLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitCharacterLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringLiteralContext extends LiteralContext {
		public TerminalNode StringLiteral() { return getToken(JLoParser.StringLiteral, 0); }
		public StringLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerLiteralContext extends LiteralContext {
		public IntegerNumberLiteralContext integerNumberLiteral() {
			return getRuleContext(IntegerNumberLiteralContext.class,0);
		}
		public IntegerLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterIntegerLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitIntegerLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitIntegerLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolLiteralContext extends LiteralContext {
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public BoolLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterBoolLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitBoolLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitBoolLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FloatingPointLiteralContext extends LiteralContext {
		public TerminalNode FloatingPointLiteral() { return getToken(JLoParser.FloatingPointLiteral, 0); }
		public FloatingPointLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterFloatingPointLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitFloatingPointLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitFloatingPointLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_literal);
		try {
			setState(271);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
				_localctx = new BoolLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(266);
				booleanLiteral();
				}
				break;
			case HexLiteral:
			case DecimalLiteral:
			case OctalLiteral:
			case BinaryLiteral:
				_localctx = new IntegerLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				integerNumberLiteral();
				}
				break;
			case FloatingPointLiteral:
				_localctx = new FloatingPointLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(268);
				match(FloatingPointLiteral);
				}
				break;
			case CharacterLiteral:
				_localctx = new CharacterLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(269);
				match(CharacterLiteral);
				}
				break;
			case StringLiteral:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(270);
				match(StringLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntegerNumberLiteralContext extends ParserRuleContext {
		public TerminalNode DecimalLiteral() { return getToken(JLoParser.DecimalLiteral, 0); }
		public TerminalNode OctalLiteral() { return getToken(JLoParser.OctalLiteral, 0); }
		public TerminalNode HexLiteral() { return getToken(JLoParser.HexLiteral, 0); }
		public TerminalNode BinaryLiteral() { return getToken(JLoParser.BinaryLiteral, 0); }
		public IntegerNumberLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integerNumberLiteral; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterIntegerNumberLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitIntegerNumberLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitIntegerNumberLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntegerNumberLiteralContext integerNumberLiteral() throws RecognitionException {
		IntegerNumberLiteralContext _localctx = new IntegerNumberLiteralContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_integerNumberLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << HexLiteral) | (1L << DecimalLiteral) | (1L << OctalLiteral) | (1L << BinaryLiteral))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanLiteralContext extends ParserRuleContext {
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
	 
		public BooleanLiteralContext() { }
		public void copyFrom(BooleanLiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FalseLiteralContext extends BooleanLiteralContext {
		public TerminalNode FALSE() { return getToken(JLoParser.FALSE, 0); }
		public FalseLiteralContext(BooleanLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterFalseLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitFalseLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitFalseLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueLiteralContext extends BooleanLiteralContext {
		public TerminalNode TRUE() { return getToken(JLoParser.TRUE, 0); }
		public TrueLiteralContext(BooleanLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).enterTrueLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JLoListener ) ((JLoListener)listener).exitTrueLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JLoVisitor ) return ((JLoVisitor<? extends T>)visitor).visitTrueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_booleanLiteral);
		try {
			setState(277);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new TrueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(275);
				match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new FalseLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(276);
				match(FALSE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 21:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		case 8:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3<\u011a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\3\2\3\2\7\2=\n\2\f\2\16\2@\13"+
		"\2\3\2\7\2C\n\2\f\2\16\2F\13\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5"+
		"\3\5\7\5S\n\5\f\5\16\5V\13\5\3\6\7\6Y\n\6\f\6\16\6\\\13\6\3\6\3\6\3\6"+
		"\5\6a\n\6\3\6\3\6\3\7\3\7\7\7g\n\7\f\7\16\7j\13\7\3\7\3\7\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\5\tv\n\t\3\n\3\n\3\n\3\13\7\13|\n\13\f\13\16\13\177"+
		"\13\13\3\13\3\13\5\13\u0083\n\13\3\f\3\f\3\f\3\r\3\r\5\r\u008a\n\r\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\7\20\u0095\n\20\f\20\16\20\u0098"+
		"\13\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00a5"+
		"\n\22\3\23\3\23\3\23\3\23\5\23\u00ab\n\23\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\7\26\u00b5\n\26\f\26\16\26\u00b8\13\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00c6\n\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u00e4\n\27"+
		"\7\27\u00e6\n\27\f\27\16\27\u00e9\13\27\3\30\3\30\7\30\u00ed\n\30\f\30"+
		"\16\30\u00f0\13\30\3\30\3\30\3\31\3\31\3\31\3\31\7\31\u00f8\n\31\f\31"+
		"\16\31\u00fb\13\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3"+
		"\32\3\32\3\32\3\32\5\32\u010b\n\32\3\33\3\33\3\33\3\33\3\33\5\33\u0112"+
		"\n\33\3\34\3\34\3\35\3\35\5\35\u0118\n\35\3\35\2\3,\36\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668\2\b\4\2\4\4\34\35\3\2\32"+
		"\33\3\2.\60\4\2\5\6,-\3\2&\'\3\2\61\64\u0127\2:\3\2\2\2\4I\3\2\2\2\6L"+
		"\3\2\2\2\bO\3\2\2\2\nZ\3\2\2\2\fd\3\2\2\2\16m\3\2\2\2\20u\3\2\2\2\22w"+
		"\3\2\2\2\24}\3\2\2\2\26\u0084\3\2\2\2\30\u0087\3\2\2\2\32\u008b\3\2\2"+
		"\2\34\u008d\3\2\2\2\36\u0091\3\2\2\2 \u0099\3\2\2\2\"\u00a4\3\2\2\2$\u00a6"+
		"\3\2\2\2&\u00ac\3\2\2\2(\u00af\3\2\2\2*\u00b1\3\2\2\2,\u00c5\3\2\2\2."+
		"\u00ea\3\2\2\2\60\u00f3\3\2\2\2\62\u010a\3\2\2\2\64\u0111\3\2\2\2\66\u0113"+
		"\3\2\2\28\u0117\3\2\2\2:>\5\4\3\2;=\5\6\4\2<;\3\2\2\2=@\3\2\2\2><\3\2"+
		"\2\2>?\3\2\2\2?D\3\2\2\2@>\3\2\2\2AC\5\n\6\2BA\3\2\2\2CF\3\2\2\2DB\3\2"+
		"\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\2\2\3H\3\3\2\2\2IJ\7\7\2\2JK\5"+
		"\b\5\2K\5\3\2\2\2LM\7\16\2\2MN\5(\25\2N\7\3\2\2\2OT\79\2\2PQ\7\37\2\2"+
		"QS\79\2\2RP\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\t\3\2\2\2VT\3\2\2\2"+
		"WY\5\32\16\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2\2\\Z\3\2"+
		"\2\2]^\7\23\2\2^`\79\2\2_a\5&\24\2`_\3\2\2\2`a\3\2\2\2ab\3\2\2\2bc\5\f"+
		"\7\2c\13\3\2\2\2dh\7!\2\2eg\5\16\b\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3"+
		"\2\2\2ik\3\2\2\2jh\3\2\2\2kl\7\"\2\2l\r\3\2\2\2mn\5\20\t\2n\17\3\2\2\2"+
		"ov\5\22\n\2pv\5$\23\2qr\7\n\2\2rs\79\2\2st\7+\2\2tv\5(\25\2uo\3\2\2\2"+
		"up\3\2\2\2uq\3\2\2\2v\21\3\2\2\2wx\5\24\13\2xy\5\"\22\2y\23\3\2\2\2z|"+
		"\5\32\16\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u0080\3\2\2\2\177"+
		"}\3\2\2\2\u0080\u0082\5\30\r\2\u0081\u0083\5\26\f\2\u0082\u0081\3\2\2"+
		"\2\u0082\u0083\3\2\2\2\u0083\25\3\2\2\2\u0084\u0085\7+\2\2\u0085\u0086"+
		"\5(\25\2\u0086\27\3\2\2\2\u0087\u0089\79\2\2\u0088\u008a\5\34\17\2\u0089"+
		"\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\31\3\2\2\2\u008b\u008c\7\b\2"+
		"\2\u008c\33\3\2\2\2\u008d\u008e\7#\2\2\u008e\u008f\5\36\20\2\u008f\u0090"+
		"\7$\2\2\u0090\35\3\2\2\2\u0091\u0096\5 \21\2\u0092\u0093\7 \2\2\u0093"+
		"\u0095\5 \21\2\u0094\u0092\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2"+
		"\2\2\u0096\u0097\3\2\2\2\u0097\37\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a"+
		"\5(\25\2\u009a\u009b\79\2\2\u009b!\3\2\2\2\u009c\u009d\7%\2\2\u009d\u00a5"+
		"\5,\27\2\u009e\u009f\7%\2\2\u009f\u00a5\7\b\2\2\u00a0\u00a1\7%\2\2\u00a1"+
		"\u00a5\7\t\2\2\u00a2\u00a3\7%\2\2\u00a3\u00a5\5.\30\2\u00a4\u009c\3\2"+
		"\2\2\u00a4\u009e\3\2\2\2\u00a4\u00a0\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5"+
		"#\3\2\2\2\u00a6\u00a7\79\2\2\u00a7\u00a8\7+\2\2\u00a8\u00aa\5(\25\2\u00a9"+
		"\u00ab\5\f\7\2\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab%\3\2\2\2"+
		"\u00ac\u00ad\7\22\2\2\u00ad\u00ae\5(\25\2\u00ae\'\3\2\2\2\u00af\u00b0"+
		"\5*\26\2\u00b0)\3\2\2\2\u00b1\u00b6\79\2\2\u00b2\u00b3\7\37\2\2\u00b3"+
		"\u00b5\79\2\2\u00b4\u00b2\3\2\2\2\u00b5\u00b8\3\2\2\2\u00b6\u00b4\3\2"+
		"\2\2\u00b6\u00b7\3\2\2\2\u00b7+\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b9\u00ba"+
		"\b\27\1\2\u00ba\u00c6\5\64\33\2\u00bb\u00c6\7\27\2\2\u00bc\u00bd\7#\2"+
		"\2\u00bd\u00be\5,\27\2\u00be\u00bf\7$\2\2\u00bf\u00c6\3\2\2\2\u00c0\u00c6"+
		"\7\17\2\2\u00c1\u00c6\7\3\2\2\u00c2\u00c6\79\2\2\u00c3\u00c4\79\2\2\u00c4"+
		"\u00c6\5\60\31\2\u00c5\u00b9\3\2\2\2\u00c5\u00bb\3\2\2\2\u00c5\u00bc\3"+
		"\2\2\2\u00c5\u00c0\3\2\2\2\u00c5\u00c1\3\2\2\2\u00c5\u00c2\3\2\2\2\u00c5"+
		"\u00c3\3\2\2\2\u00c6\u00e7\3\2\2\2\u00c7\u00c8\f\n\2\2\u00c8\u00c9\7)"+
		"\2\2\u00c9\u00e6\5,\27\13\u00ca\u00cb\f\t\2\2\u00cb\u00cc\t\2\2\2\u00cc"+
		"\u00e6\5,\27\n\u00cd\u00ce\f\b\2\2\u00ce\u00cf\t\3\2\2\u00cf\u00e6\5,"+
		"\27\t\u00d0\u00d1\f\7\2\2\u00d1\u00d2\t\4\2\2\u00d2\u00e6\5,\27\b\u00d3"+
		"\u00d4\f\6\2\2\u00d4\u00d5\t\5\2\2\u00d5\u00e6\5,\27\7\u00d6\u00d7\f\5"+
		"\2\2\u00d7\u00d8\t\6\2\2\u00d8\u00e6\5,\27\6\u00d9\u00da\f\4\2\2\u00da"+
		"\u00db\7*\2\2\u00db\u00e6\5,\27\5\u00dc\u00dd\f\3\2\2\u00dd\u00de\7\31"+
		"\2\2\u00de\u00e6\5,\27\4\u00df\u00e0\f\f\2\2\u00e0\u00e1\7\37\2\2\u00e1"+
		"\u00e3\79\2\2\u00e2\u00e4\5\60\31\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3"+
		"\2\2\2\u00e4\u00e6\3\2\2\2\u00e5\u00c7\3\2\2\2\u00e5\u00ca\3\2\2\2\u00e5"+
		"\u00cd\3\2\2\2\u00e5\u00d0\3\2\2\2\u00e5\u00d3\3\2\2\2\u00e5\u00d6\3\2"+
		"\2\2\u00e5\u00d9\3\2\2\2\u00e5\u00dc\3\2\2\2\u00e5\u00df\3\2\2\2\u00e6"+
		"\u00e9\3\2\2\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8-\3\2\2\2"+
		"\u00e9\u00e7\3\2\2\2\u00ea\u00ee\7!\2\2\u00eb\u00ed\5\62\32\2\u00ec\u00eb"+
		"\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef"+
		"\u00f1\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1\u00f2\7\"\2\2\u00f2/\3\2\2\2"+
		"\u00f3\u00f4\7#\2\2\u00f4\u00f9\5,\27\2\u00f5\u00f6\7 \2\2\u00f6\u00f8"+
		"\5,\27\2\u00f7\u00f5\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\u00fc\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00fd\7$"+
		"\2\2\u00fd\61\3\2\2\2\u00fe\u00ff\5,\27\2\u00ff\u0100\7\36\2\2\u0100\u010b"+
		"\3\2\2\2\u0101\u0102\7(\2\2\u0102\u0103\5,\27\2\u0103\u0104\7\36\2\2\u0104"+
		"\u010b\3\2\2\2\u0105\u0106\5,\27\2\u0106\u0107\7%\2\2\u0107\u0108\5,\27"+
		"\2\u0108\u0109\7\36\2\2\u0109\u010b\3\2\2\2\u010a\u00fe\3\2\2\2\u010a"+
		"\u0101\3\2\2\2\u010a\u0105\3\2\2\2\u010b\63\3\2\2\2\u010c\u0112\58\35"+
		"\2\u010d\u0112\5\66\34\2\u010e\u0112\7\65\2\2\u010f\u0112\7\66\2\2\u0110"+
		"\u0112\7\67\2\2\u0111\u010c\3\2\2\2\u0111\u010d\3\2\2\2\u0111\u010e\3"+
		"\2\2\2\u0111\u010f\3\2\2\2\u0111\u0110\3\2\2\2\u0112\65\3\2\2\2\u0113"+
		"\u0114\t\7\2\2\u0114\67\3\2\2\2\u0115\u0118\7\25\2\2\u0116\u0118\7\26"+
		"\2\2\u0117\u0115\3\2\2\2\u0117\u0116\3\2\2\2\u01189\3\2\2\2\31>DTZ`hu"+
		"}\u0082\u0089\u0096\u00a4\u00aa\u00b6\u00c5\u00e3\u00e5\u00e7\u00ee\u00f9"+
		"\u010a\u0111\u0117";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}