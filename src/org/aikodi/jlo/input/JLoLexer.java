// Generated from /Users/marko/git/workspace/jlo/src/org/aikodi/jlo/input/JLo.g4 by ANTLR 4.5
package org.aikodi.jlo.input;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JLoLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, NAMESPACE=5, ABSTRACT=6, INIT=7, NATIVE=8, 
		FIELD=9, BLOCKING=10, PROPAGATING=11, LIKE=12, IMPORT=13, SELF=14, ARROW=15, 
		SUBOBJECT=16, EXTENDS=17, KLASS=18, LAYER=19, TRUE=20, FALSE=21, NULL=22, 
		UNDERSCORE=23, PIPE=24, MINUS=25, PLUS=26, SLASH=27, STAR=28, SEMI=29, 
		DOT=30, COMMA=31, LBRACE=32, RBRACE=33, LPAR=34, RPAR=35, ASSIGN=36, EQUAL=37, 
		NOTEQUAL=38, RETURN=39, EXPONENTIATION=40, AMPERSAND=41, TYPEANNOTATION=42, 
		SMALLER=43, BIGGER=44, LEFTSHIFT=45, RIGHTSHIFT=46, RIGHTRIGHTSHIFT=47, 
		HexLiteral=48, DecimalLiteral=49, OctalLiteral=50, BinaryLiteral=51, FloatingPointLiteral=52, 
		CharacterLiteral=53, StringLiteral=54, ASSERT=55, Identifier=56, WS=57, 
		COMMENT=58, LINE_COMMENT=59;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "NAMESPACE", "ABSTRACT", "INIT", "NATIVE", 
		"FIELD", "BLOCKING", "PROPAGATING", "LIKE", "IMPORT", "SELF", "ARROW", 
		"SUBOBJECT", "EXTENDS", "KLASS", "LAYER", "TRUE", "FALSE", "NULL", "UNDERSCORE", 
		"PIPE", "MINUS", "PLUS", "SLASH", "STAR", "SEMI", "DOT", "COMMA", "LBRACE", 
		"RBRACE", "LPAR", "RPAR", "ASSIGN", "EQUAL", "NOTEQUAL", "RETURN", "EXPONENTIATION", 
		"AMPERSAND", "TYPEANNOTATION", "SMALLER", "BIGGER", "LEFTSHIFT", "RIGHTSHIFT", 
		"RIGHTRIGHTSHIFT", "HexLiteral", "DecimalLiteral", "OctalLiteral", "BinaryLiteral", 
		"BinaryDigit", "HexDigits", "HexDigit", "Digits", "Digit", "IntegerTypeSuffix", 
		"FloatingPointLiteral", "Exponent", "FloatTypeSuffix", "CharacterLiteral", 
		"StringLiteral", "EscapeSequence", "OctalEscape", "UnicodeEscape", "ASSERT", 
		"Identifier", "Letter", "JavaIDDigit", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'super'", "'%'", "'<='", "'>='", "'namespace'", "'abstract'", "'init'", 
		"'native'", "'field'", "'blocking'", "'propagating'", "'like'", "'import'", 
		"'self'", "'->'", "'subobject'", "'extends'", "'class'", "'layer'", "'true'", 
		"'false'", "'null'", "'_'", "'|'", "'-'", "'+'", "'/'", "'*'", "';'", 
		"'.'", "','", "'{'", "'}'", "'('", "')'", "'='", "'=='", "'!='", "'return'", 
		"'^'", "'&'", "':'", "'<'", "'>'", "'<<'", "'>>'", "'>>>'", null, null, 
		null, null, null, null, null, "'assert'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "NAMESPACE", "ABSTRACT", "INIT", "NATIVE", 
		"FIELD", "BLOCKING", "PROPAGATING", "LIKE", "IMPORT", "SELF", "ARROW", 
		"SUBOBJECT", "EXTENDS", "KLASS", "LAYER", "TRUE", "FALSE", "NULL", "UNDERSCORE", 
		"PIPE", "MINUS", "PLUS", "SLASH", "STAR", "SEMI", "DOT", "COMMA", "LBRACE", 
		"RBRACE", "LPAR", "RPAR", "ASSIGN", "EQUAL", "NOTEQUAL", "RETURN", "EXPONENTIATION", 
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


	public JLoLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JLo.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2=\u0250\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3"+
		"\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3"+
		"\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3&\3\'\3\'\3\'\3"+
		"(\3(\3(\3(\3(\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\3.\3/\3/\3/\3"+
		"\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\5\61\u015f\n\61\3\62\3\62\3\62"+
		"\7\62\u0164\n\62\f\62\16\62\u0167\13\62\3\62\7\62\u016a\n\62\f\62\16\62"+
		"\u016d\13\62\5\62\u016f\n\62\3\62\5\62\u0172\n\62\3\63\3\63\7\63\u0176"+
		"\n\63\f\63\16\63\u0179\13\63\3\63\6\63\u017c\n\63\r\63\16\63\u017d\3\63"+
		"\5\63\u0181\n\63\3\64\3\64\3\64\3\64\7\64\u0187\n\64\f\64\16\64\u018a"+
		"\13\64\3\64\7\64\u018d\n\64\f\64\16\64\u0190\13\64\3\64\5\64\u0193\n\64"+
		"\3\65\3\65\3\66\3\66\7\66\u0199\n\66\f\66\16\66\u019c\13\66\3\66\7\66"+
		"\u019f\n\66\f\66\16\66\u01a2\13\66\3\67\3\67\5\67\u01a6\n\67\38\38\78"+
		"\u01aa\n8\f8\168\u01ad\138\38\78\u01b0\n8\f8\168\u01b3\138\39\39\3:\3"+
		":\3;\3;\3;\5;\u01bc\n;\3;\5;\u01bf\n;\3;\5;\u01c2\n;\3;\3;\3;\5;\u01c7"+
		"\n;\3;\5;\u01ca\n;\3;\3;\3;\5;\u01cf\n;\3;\3;\3;\3;\3;\3;\3;\5;\u01d8"+
		"\n;\3;\5;\u01db\n;\3;\3;\5;\u01df\n;\5;\u01e1\n;\3;\3;\5;\u01e5\n;\3;"+
		"\3;\5;\u01e9\n;\5;\u01eb\n;\3<\3<\5<\u01ef\n<\3<\3<\3=\3=\3>\3>\3>\5>"+
		"\u01f8\n>\3>\3>\3?\3?\3?\7?\u01ff\n?\f?\16?\u0202\13?\3?\3?\3@\3@\3@\3"+
		"@\5@\u020a\n@\3A\3A\3A\3A\3A\3A\3A\3A\3A\5A\u0215\nA\3B\3B\3B\3B\3B\3"+
		"B\3B\3C\3C\3C\3C\3C\3C\3C\3D\3D\3D\7D\u0228\nD\fD\16D\u022b\13D\3E\3E"+
		"\3F\3F\3G\6G\u0232\nG\rG\16G\u0233\3G\3G\3H\3H\3H\3H\7H\u023c\nH\fH\16"+
		"H\u023f\13H\3H\3H\3H\3H\3H\3I\3I\3I\3I\7I\u024a\nI\fI\16I\u024d\13I\3"+
		"I\3I\3\u023d\2J\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64"+
		"g\65i\2k\2m\2o\2q\2s\2u\66w\2y\2{\67}8\177\2\u0081\2\u0083\2\u00859\u0087"+
		":\u0089\2\u008b\2\u008d;\u008f<\u0091=\3\2\21\4\2ZZzz\4\2DDdd\4\2CHch"+
		"\4\2NNnn\4\2RRrr\4\2--//\4\2GGgg\6\2FFHHffhh\4\2))^^\4\2$$^^\n\2$$))^"+
		"^ddhhppttvv\16\2&&C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u2001\u3042\u3191"+
		"\u3302\u3381\u3402\u3d2f\u4e02\ua001\uf902\ufb01\21\2\62;\u0662\u066b"+
		"\u06f2\u06fb\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71"+
		"\u0be9\u0bf1\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb"+
		"\u1042\u104b\5\2\13\f\16\17\"\"\4\2\f\f\17\17\u026f\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2"+
		"?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3"+
		"\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2"+
		"\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2"+
		"e\3\2\2\2\2g\3\2\2\2\2u\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\u0085\3\2\2\2"+
		"\2\u0087\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\3\u0093"+
		"\3\2\2\2\5\u0099\3\2\2\2\7\u009b\3\2\2\2\t\u009e\3\2\2\2\13\u00a1\3\2"+
		"\2\2\r\u00ab\3\2\2\2\17\u00b4\3\2\2\2\21\u00b9\3\2\2\2\23\u00c0\3\2\2"+
		"\2\25\u00c6\3\2\2\2\27\u00cf\3\2\2\2\31\u00db\3\2\2\2\33\u00e0\3\2\2\2"+
		"\35\u00e7\3\2\2\2\37\u00ec\3\2\2\2!\u00ef\3\2\2\2#\u00f9\3\2\2\2%\u0101"+
		"\3\2\2\2\'\u0107\3\2\2\2)\u010d\3\2\2\2+\u0112\3\2\2\2-\u0118\3\2\2\2"+
		"/\u011d\3\2\2\2\61\u011f\3\2\2\2\63\u0121\3\2\2\2\65\u0123\3\2\2\2\67"+
		"\u0125\3\2\2\29\u0127\3\2\2\2;\u0129\3\2\2\2=\u012b\3\2\2\2?\u012d\3\2"+
		"\2\2A\u012f\3\2\2\2C\u0131\3\2\2\2E\u0133\3\2\2\2G\u0135\3\2\2\2I\u0137"+
		"\3\2\2\2K\u0139\3\2\2\2M\u013c\3\2\2\2O\u013f\3\2\2\2Q\u0146\3\2\2\2S"+
		"\u0148\3\2\2\2U\u014a\3\2\2\2W\u014c\3\2\2\2Y\u014e\3\2\2\2[\u0150\3\2"+
		"\2\2]\u0153\3\2\2\2_\u0156\3\2\2\2a\u015a\3\2\2\2c\u016e\3\2\2\2e\u0173"+
		"\3\2\2\2g\u0182\3\2\2\2i\u0194\3\2\2\2k\u0196\3\2\2\2m\u01a5\3\2\2\2o"+
		"\u01a7\3\2\2\2q\u01b4\3\2\2\2s\u01b6\3\2\2\2u\u01ea\3\2\2\2w\u01ec\3\2"+
		"\2\2y\u01f2\3\2\2\2{\u01f4\3\2\2\2}\u01fb\3\2\2\2\177\u0209\3\2\2\2\u0081"+
		"\u0214\3\2\2\2\u0083\u0216\3\2\2\2\u0085\u021d\3\2\2\2\u0087\u0224\3\2"+
		"\2\2\u0089\u022c\3\2\2\2\u008b\u022e\3\2\2\2\u008d\u0231\3\2\2\2\u008f"+
		"\u0237\3\2\2\2\u0091\u0245\3\2\2\2\u0093\u0094\7u\2\2\u0094\u0095\7w\2"+
		"\2\u0095\u0096\7r\2\2\u0096\u0097\7g\2\2\u0097\u0098\7t\2\2\u0098\4\3"+
		"\2\2\2\u0099\u009a\7\'\2\2\u009a\6\3\2\2\2\u009b\u009c\7>\2\2\u009c\u009d"+
		"\7?\2\2\u009d\b\3\2\2\2\u009e\u009f\7@\2\2\u009f\u00a0\7?\2\2\u00a0\n"+
		"\3\2\2\2\u00a1\u00a2\7p\2\2\u00a2\u00a3\7c\2\2\u00a3\u00a4\7o\2\2\u00a4"+
		"\u00a5\7g\2\2\u00a5\u00a6\7u\2\2\u00a6\u00a7\7r\2\2\u00a7\u00a8\7c\2\2"+
		"\u00a8\u00a9\7e\2\2\u00a9\u00aa\7g\2\2\u00aa\f\3\2\2\2\u00ab\u00ac\7c"+
		"\2\2\u00ac\u00ad\7d\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af\7v\2\2\u00af\u00b0"+
		"\7t\2\2\u00b0\u00b1\7c\2\2\u00b1\u00b2\7e\2\2\u00b2\u00b3\7v\2\2\u00b3"+
		"\16\3\2\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6\7p\2\2\u00b6\u00b7\7k\2\2\u00b7"+
		"\u00b8\7v\2\2\u00b8\20\3\2\2\2\u00b9\u00ba\7p\2\2\u00ba\u00bb\7c\2\2\u00bb"+
		"\u00bc\7v\2\2\u00bc\u00bd\7k\2\2\u00bd\u00be\7x\2\2\u00be\u00bf\7g\2\2"+
		"\u00bf\22\3\2\2\2\u00c0\u00c1\7h\2\2\u00c1\u00c2\7k\2\2\u00c2\u00c3\7"+
		"g\2\2\u00c3\u00c4\7n\2\2\u00c4\u00c5\7f\2\2\u00c5\24\3\2\2\2\u00c6\u00c7"+
		"\7d\2\2\u00c7\u00c8\7n\2\2\u00c8\u00c9\7q\2\2\u00c9\u00ca\7e\2\2\u00ca"+
		"\u00cb\7m\2\2\u00cb\u00cc\7k\2\2\u00cc\u00cd\7p\2\2\u00cd\u00ce\7i\2\2"+
		"\u00ce\26\3\2\2\2\u00cf\u00d0\7r\2\2\u00d0\u00d1\7t\2\2\u00d1\u00d2\7"+
		"q\2\2\u00d2\u00d3\7r\2\2\u00d3\u00d4\7c\2\2\u00d4\u00d5\7i\2\2\u00d5\u00d6"+
		"\7c\2\2\u00d6\u00d7\7v\2\2\u00d7\u00d8\7k\2\2\u00d8\u00d9\7p\2\2\u00d9"+
		"\u00da\7i\2\2\u00da\30\3\2\2\2\u00db\u00dc\7n\2\2\u00dc\u00dd\7k\2\2\u00dd"+
		"\u00de\7m\2\2\u00de\u00df\7g\2\2\u00df\32\3\2\2\2\u00e0\u00e1\7k\2\2\u00e1"+
		"\u00e2\7o\2\2\u00e2\u00e3\7r\2\2\u00e3\u00e4\7q\2\2\u00e4\u00e5\7t\2\2"+
		"\u00e5\u00e6\7v\2\2\u00e6\34\3\2\2\2\u00e7\u00e8\7u\2\2\u00e8\u00e9\7"+
		"g\2\2\u00e9\u00ea\7n\2\2\u00ea\u00eb\7h\2\2\u00eb\36\3\2\2\2\u00ec\u00ed"+
		"\7/\2\2\u00ed\u00ee\7@\2\2\u00ee \3\2\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f1"+
		"\7w\2\2\u00f1\u00f2\7d\2\2\u00f2\u00f3\7q\2\2\u00f3\u00f4\7d\2\2\u00f4"+
		"\u00f5\7l\2\2\u00f5\u00f6\7g\2\2\u00f6\u00f7\7e\2\2\u00f7\u00f8\7v\2\2"+
		"\u00f8\"\3\2\2\2\u00f9\u00fa\7g\2\2\u00fa\u00fb\7z\2\2\u00fb\u00fc\7v"+
		"\2\2\u00fc\u00fd\7g\2\2\u00fd\u00fe\7p\2\2\u00fe\u00ff\7f\2\2\u00ff\u0100"+
		"\7u\2\2\u0100$\3\2\2\2\u0101\u0102\7e\2\2\u0102\u0103\7n\2\2\u0103\u0104"+
		"\7c\2\2\u0104\u0105\7u\2\2\u0105\u0106\7u\2\2\u0106&\3\2\2\2\u0107\u0108"+
		"\7n\2\2\u0108\u0109\7c\2\2\u0109\u010a\7{\2\2\u010a\u010b\7g\2\2\u010b"+
		"\u010c\7t\2\2\u010c(\3\2\2\2\u010d\u010e\7v\2\2\u010e\u010f\7t\2\2\u010f"+
		"\u0110\7w\2\2\u0110\u0111\7g\2\2\u0111*\3\2\2\2\u0112\u0113\7h\2\2\u0113"+
		"\u0114\7c\2\2\u0114\u0115\7n\2\2\u0115\u0116\7u\2\2\u0116\u0117\7g\2\2"+
		"\u0117,\3\2\2\2\u0118\u0119\7p\2\2\u0119\u011a\7w\2\2\u011a\u011b\7n\2"+
		"\2\u011b\u011c\7n\2\2\u011c.\3\2\2\2\u011d\u011e\7a\2\2\u011e\60\3\2\2"+
		"\2\u011f\u0120\7~\2\2\u0120\62\3\2\2\2\u0121\u0122\7/\2\2\u0122\64\3\2"+
		"\2\2\u0123\u0124\7-\2\2\u0124\66\3\2\2\2\u0125\u0126\7\61\2\2\u01268\3"+
		"\2\2\2\u0127\u0128\7,\2\2\u0128:\3\2\2\2\u0129\u012a\7=\2\2\u012a<\3\2"+
		"\2\2\u012b\u012c\7\60\2\2\u012c>\3\2\2\2\u012d\u012e\7.\2\2\u012e@\3\2"+
		"\2\2\u012f\u0130\7}\2\2\u0130B\3\2\2\2\u0131\u0132\7\177\2\2\u0132D\3"+
		"\2\2\2\u0133\u0134\7*\2\2\u0134F\3\2\2\2\u0135\u0136\7+\2\2\u0136H\3\2"+
		"\2\2\u0137\u0138\7?\2\2\u0138J\3\2\2\2\u0139\u013a\7?\2\2\u013a\u013b"+
		"\7?\2\2\u013bL\3\2\2\2\u013c\u013d\7#\2\2\u013d\u013e\7?\2\2\u013eN\3"+
		"\2\2\2\u013f\u0140\7t\2\2\u0140\u0141\7g\2\2\u0141\u0142\7v\2\2\u0142"+
		"\u0143\7w\2\2\u0143\u0144\7t\2\2\u0144\u0145\7p\2\2\u0145P\3\2\2\2\u0146"+
		"\u0147\7`\2\2\u0147R\3\2\2\2\u0148\u0149\7(\2\2\u0149T\3\2\2\2\u014a\u014b"+
		"\7<\2\2\u014bV\3\2\2\2\u014c\u014d\7>\2\2\u014dX\3\2\2\2\u014e\u014f\7"+
		"@\2\2\u014fZ\3\2\2\2\u0150\u0151\7>\2\2\u0151\u0152\7>\2\2\u0152\\\3\2"+
		"\2\2\u0153\u0154\7@\2\2\u0154\u0155\7@\2\2\u0155^\3\2\2\2\u0156\u0157"+
		"\7@\2\2\u0157\u0158\7@\2\2\u0158\u0159\7@\2\2\u0159`\3\2\2\2\u015a\u015b"+
		"\7\62\2\2\u015b\u015c\t\2\2\2\u015c\u015e\5k\66\2\u015d\u015f\5s:\2\u015e"+
		"\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015fb\3\2\2\2\u0160\u016f\7\62\2\2"+
		"\u0161\u016b\4\63;\2\u0162\u0164\5/\30\2\u0163\u0162\3\2\2\2\u0164\u0167"+
		"\3\2\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0168\3\2\2\2\u0167"+
		"\u0165\3\2\2\2\u0168\u016a\5q9\2\u0169\u0165\3\2\2\2\u016a\u016d\3\2\2"+
		"\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016f\3\2\2\2\u016d\u016b"+
		"\3\2\2\2\u016e\u0160\3\2\2\2\u016e\u0161\3\2\2\2\u016f\u0171\3\2\2\2\u0170"+
		"\u0172\5s:\2\u0171\u0170\3\2\2\2\u0171\u0172\3\2\2\2\u0172d\3\2\2\2\u0173"+
		"\u017b\7\62\2\2\u0174\u0176\5/\30\2\u0175\u0174\3\2\2\2\u0176\u0179\3"+
		"\2\2\2\u0177\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u017a\3\2\2\2\u0179"+
		"\u0177\3\2\2\2\u017a\u017c\4\629\2\u017b\u0177\3\2\2\2\u017c\u017d\3\2"+
		"\2\2\u017d\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017e\u0180\3\2\2\2\u017f"+
		"\u0181\5s:\2\u0180\u017f\3\2\2\2\u0180\u0181\3\2\2\2\u0181f\3\2\2\2\u0182"+
		"\u0183\7\62\2\2\u0183\u0184\t\3\2\2\u0184\u018e\5i\65\2\u0185\u0187\5"+
		"/\30\2\u0186\u0185\3\2\2\2\u0187\u018a\3\2\2\2\u0188\u0186\3\2\2\2\u0188"+
		"\u0189\3\2\2\2\u0189\u018b\3\2\2\2\u018a\u0188\3\2\2\2\u018b\u018d\5i"+
		"\65\2\u018c\u0188\3\2\2\2\u018d\u0190\3\2\2\2\u018e\u018c\3\2\2\2\u018e"+
		"\u018f\3\2\2\2\u018f\u0192\3\2\2\2\u0190\u018e\3\2\2\2\u0191\u0193\5s"+
		":\2\u0192\u0191\3\2\2\2\u0192\u0193\3\2\2\2\u0193h\3\2\2\2\u0194\u0195"+
		"\4\62\63\2\u0195j\3\2\2\2\u0196\u01a0\5m\67\2\u0197\u0199\5/\30\2\u0198"+
		"\u0197\3\2\2\2\u0199\u019c\3\2\2\2\u019a\u0198\3\2\2\2\u019a\u019b\3\2"+
		"\2\2\u019b\u019d\3\2\2\2\u019c\u019a\3\2\2\2\u019d\u019f\5m\67\2\u019e"+
		"\u019a\3\2\2\2\u019f\u01a2\3\2\2\2\u01a0\u019e\3\2\2\2\u01a0\u01a1\3\2"+
		"\2\2\u01a1l\3\2\2\2\u01a2\u01a0\3\2\2\2\u01a3\u01a6\5q9\2\u01a4\u01a6"+
		"\t\4\2\2\u01a5\u01a3\3\2\2\2\u01a5\u01a4\3\2\2\2\u01a6n\3\2\2\2\u01a7"+
		"\u01b1\5q9\2\u01a8\u01aa\5/\30\2\u01a9\u01a8\3\2\2\2\u01aa\u01ad\3\2\2"+
		"\2\u01ab\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01ae\3\2\2\2\u01ad\u01ab"+
		"\3\2\2\2\u01ae\u01b0\5q9\2\u01af\u01ab\3\2\2\2\u01b0\u01b3\3\2\2\2\u01b1"+
		"\u01af\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2p\3\2\2\2\u01b3\u01b1\3\2\2\2"+
		"\u01b4\u01b5\4\62;\2\u01b5r\3\2\2\2\u01b6\u01b7\t\5\2\2\u01b7t\3\2\2\2"+
		"\u01b8\u01b9\5o8\2\u01b9\u01bb\7\60\2\2\u01ba\u01bc\5o8\2\u01bb\u01ba"+
		"\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc\u01be\3\2\2\2\u01bd\u01bf\5w<\2\u01be"+
		"\u01bd\3\2\2\2\u01be\u01bf\3\2\2\2\u01bf\u01c1\3\2\2\2\u01c0\u01c2\5y"+
		"=\2\u01c1\u01c0\3\2\2\2\u01c1\u01c2\3\2\2\2\u01c2\u01eb\3\2\2\2\u01c3"+
		"\u01c4\7\60\2\2\u01c4\u01c6\5o8\2\u01c5\u01c7\5w<\2\u01c6\u01c5\3\2\2"+
		"\2\u01c6\u01c7\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01ca\5y=\2\u01c9\u01c8"+
		"\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01eb\3\2\2\2\u01cb\u01cc\5o8\2\u01cc"+
		"\u01ce\5w<\2\u01cd\u01cf\5y=\2\u01ce\u01cd\3\2\2\2\u01ce\u01cf\3\2\2\2"+
		"\u01cf\u01eb\3\2\2\2\u01d0\u01d1\5o8\2\u01d1\u01d2\5y=\2\u01d2\u01eb\3"+
		"\2\2\2\u01d3\u01d4\7\62\2\2\u01d4\u01d8\7z\2\2\u01d5\u01d6\7\62\2\2\u01d6"+
		"\u01d8\7Z\2\2\u01d7\u01d3\3\2\2\2\u01d7\u01d5\3\2\2\2\u01d8\u01da\3\2"+
		"\2\2\u01d9\u01db\5k\66\2\u01da\u01d9\3\2\2\2\u01da\u01db\3\2\2\2\u01db"+
		"\u01e0\3\2\2\2\u01dc\u01de\7\60\2\2\u01dd\u01df\5k\66\2\u01de\u01dd\3"+
		"\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e1\3\2\2\2\u01e0\u01dc\3\2\2\2\u01e0"+
		"\u01e1\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e4\t\6\2\2\u01e3\u01e5\t\7"+
		"\2\2\u01e4\u01e3\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e6\3\2\2\2\u01e6"+
		"\u01e8\5o8\2\u01e7\u01e9\5y=\2\u01e8\u01e7\3\2\2\2\u01e8\u01e9\3\2\2\2"+
		"\u01e9\u01eb\3\2\2\2\u01ea\u01b8\3\2\2\2\u01ea\u01c3\3\2\2\2\u01ea\u01cb"+
		"\3\2\2\2\u01ea\u01d0\3\2\2\2\u01ea\u01d7\3\2\2\2\u01ebv\3\2\2\2\u01ec"+
		"\u01ee\t\b\2\2\u01ed\u01ef\t\7\2\2\u01ee\u01ed\3\2\2\2\u01ee\u01ef\3\2"+
		"\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01f1\5o8\2\u01f1x\3\2\2\2\u01f2\u01f3"+
		"\t\t\2\2\u01f3z\3\2\2\2\u01f4\u01f7\7)\2\2\u01f5\u01f8\5\177@\2\u01f6"+
		"\u01f8\n\n\2\2\u01f7\u01f5\3\2\2\2\u01f7\u01f6\3\2\2\2\u01f8\u01f9\3\2"+
		"\2\2\u01f9\u01fa\7)\2\2\u01fa|\3\2\2\2\u01fb\u0200\7$\2\2\u01fc\u01ff"+
		"\5\177@\2\u01fd\u01ff\n\13\2\2\u01fe\u01fc\3\2\2\2\u01fe\u01fd\3\2\2\2"+
		"\u01ff\u0202\3\2\2\2\u0200\u01fe\3\2\2\2\u0200\u0201\3\2\2\2\u0201\u0203"+
		"\3\2\2\2\u0202\u0200\3\2\2\2\u0203\u0204\7$\2\2\u0204~\3\2\2\2\u0205\u0206"+
		"\7^\2\2\u0206\u020a\t\f\2\2\u0207\u020a\5\u0083B\2\u0208\u020a\5\u0081"+
		"A\2\u0209\u0205\3\2\2\2\u0209\u0207\3\2\2\2\u0209\u0208\3\2\2\2\u020a"+
		"\u0080\3\2\2\2\u020b\u020c\7^\2\2\u020c\u020d\4\62\65\2\u020d\u020e\4"+
		"\629\2\u020e\u0215\4\629\2\u020f\u0210\7^\2\2\u0210\u0211\4\629\2\u0211"+
		"\u0215\4\629\2\u0212\u0213\7^\2\2\u0213\u0215\4\629\2\u0214\u020b\3\2"+
		"\2\2\u0214\u020f\3\2\2\2\u0214\u0212\3\2\2\2\u0215\u0082\3\2\2\2\u0216"+
		"\u0217\7^\2\2\u0217\u0218\7w\2\2\u0218\u0219\5m\67\2\u0219\u021a\5m\67"+
		"\2\u021a\u021b\5m\67\2\u021b\u021c\5m\67\2\u021c\u0084\3\2\2\2\u021d\u021e"+
		"\7c\2\2\u021e\u021f\7u\2\2\u021f\u0220\7u\2\2\u0220\u0221\7g\2\2\u0221"+
		"\u0222\7t\2\2\u0222\u0223\7v\2\2\u0223\u0086\3\2\2\2\u0224\u0229\5\u0089"+
		"E\2\u0225\u0228\5\u0089E\2\u0226\u0228\5\u008bF\2\u0227\u0225\3\2\2\2"+
		"\u0227\u0226\3\2\2\2\u0228\u022b\3\2\2\2\u0229\u0227\3\2\2\2\u0229\u022a"+
		"\3\2\2\2\u022a\u0088\3\2\2\2\u022b\u0229\3\2\2\2\u022c\u022d\t\r\2\2\u022d"+
		"\u008a\3\2\2\2\u022e\u022f\t\16\2\2\u022f\u008c\3\2\2\2\u0230\u0232\t"+
		"\17\2\2\u0231\u0230\3\2\2\2\u0232\u0233\3\2\2\2\u0233\u0231\3\2\2\2\u0233"+
		"\u0234\3\2\2\2\u0234\u0235\3\2\2\2\u0235\u0236\bG\2\2\u0236\u008e\3\2"+
		"\2\2\u0237\u0238\7\61\2\2\u0238\u0239\7,\2\2\u0239\u023d\3\2\2\2\u023a"+
		"\u023c\13\2\2\2\u023b\u023a\3\2\2\2\u023c\u023f\3\2\2\2\u023d\u023e\3"+
		"\2\2\2\u023d\u023b\3\2\2\2\u023e\u0240\3\2\2\2\u023f\u023d\3\2\2\2\u0240"+
		"\u0241\7,\2\2\u0241\u0242\7\61\2\2\u0242\u0243\3\2\2\2\u0243\u0244\bH"+
		"\2\2\u0244\u0090\3\2\2\2\u0245\u0246\7\61\2\2\u0246\u0247\7\61\2\2\u0247"+
		"\u024b\3\2\2\2\u0248\u024a\n\20\2\2\u0249\u0248\3\2\2\2\u024a\u024d\3"+
		"\2\2\2\u024b\u0249\3\2\2\2\u024b\u024c\3\2\2\2\u024c\u024e\3\2\2\2\u024d"+
		"\u024b\3\2\2\2\u024e\u024f\bI\2\2\u024f\u0092\3\2\2\2+\2\u015e\u0165\u016b"+
		"\u016e\u0171\u0177\u017d\u0180\u0188\u018e\u0192\u019a\u01a0\u01a5\u01ab"+
		"\u01b1\u01bb\u01be\u01c1\u01c6\u01c9\u01ce\u01d7\u01da\u01de\u01e0\u01e4"+
		"\u01e8\u01ea\u01ee\u01f7\u01fe\u0200\u0209\u0214\u0227\u0229\u0233\u023d"+
		"\u024b\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}