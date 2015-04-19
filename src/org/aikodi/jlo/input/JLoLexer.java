// Generated from /home/marko/git/workspace/jlo/src/org/aikodi/jlo/input/JLo.g4 by ANTLR 4.5
package org.aikodi.jlo.input;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JLoLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "NAMESPACE", "ABSTRACT", "NATIVE", "FIELD", 
		"BLOCKING", "PROPAGATING", "LIKE", "IMPORT", "SELF", "ARROW", "SUBOBJECT", 
		"EXTENDS", "KLASS", "LAYER", "TRUE", "FALSE", "NULL", "UNDERSCORE", "PIPE", 
		"MINUS", "PLUS", "SLASH", "STAR", "SEMI", "DOT", "COMMA", "LBRACE", "RBRACE", 
		"LPAR", "RPAR", "ASSIGN", "EQUAL", "NOTEQUAL", "RETURN", "EXPONENTIATION", 
		"AMPERSAND", "TYPEANNOTATION", "SMALLER", "BIGGER", "LEFTSHIFT", "RIGHTSHIFT", 
		"RIGHTRIGHTSHIFT", "HexLiteral", "DecimalLiteral", "OctalLiteral", "BinaryLiteral", 
		"BinaryDigit", "HexDigits", "HexDigit", "Digits", "Digit", "IntegerTypeSuffix", 
		"FloatingPointLiteral", "Exponent", "FloatTypeSuffix", "CharacterLiteral", 
		"StringLiteral", "EscapeSequence", "OctalEscape", "UnicodeEscape", "ASSERT", 
		"Identifier", "Letter", "JavaIDDigit", "WS", "COMMENT", "LINE_COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2<\u0249\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3"+
		"\"\3\"\3#\3#\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3"+
		"(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3-\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60"+
		"\3\60\5\60\u0158\n\60\3\61\3\61\3\61\7\61\u015d\n\61\f\61\16\61\u0160"+
		"\13\61\3\61\7\61\u0163\n\61\f\61\16\61\u0166\13\61\5\61\u0168\n\61\3\61"+
		"\5\61\u016b\n\61\3\62\3\62\7\62\u016f\n\62\f\62\16\62\u0172\13\62\3\62"+
		"\6\62\u0175\n\62\r\62\16\62\u0176\3\62\5\62\u017a\n\62\3\63\3\63\3\63"+
		"\3\63\7\63\u0180\n\63\f\63\16\63\u0183\13\63\3\63\7\63\u0186\n\63\f\63"+
		"\16\63\u0189\13\63\3\63\5\63\u018c\n\63\3\64\3\64\3\65\3\65\7\65\u0192"+
		"\n\65\f\65\16\65\u0195\13\65\3\65\7\65\u0198\n\65\f\65\16\65\u019b\13"+
		"\65\3\66\3\66\5\66\u019f\n\66\3\67\3\67\7\67\u01a3\n\67\f\67\16\67\u01a6"+
		"\13\67\3\67\7\67\u01a9\n\67\f\67\16\67\u01ac\13\67\38\38\39\39\3:\3:\3"+
		":\5:\u01b5\n:\3:\5:\u01b8\n:\3:\5:\u01bb\n:\3:\3:\3:\5:\u01c0\n:\3:\5"+
		":\u01c3\n:\3:\3:\3:\5:\u01c8\n:\3:\3:\3:\3:\3:\3:\3:\5:\u01d1\n:\3:\5"+
		":\u01d4\n:\3:\3:\5:\u01d8\n:\5:\u01da\n:\3:\3:\5:\u01de\n:\3:\3:\5:\u01e2"+
		"\n:\5:\u01e4\n:\3;\3;\5;\u01e8\n;\3;\3;\3<\3<\3=\3=\3=\5=\u01f1\n=\3="+
		"\3=\3>\3>\3>\7>\u01f8\n>\f>\16>\u01fb\13>\3>\3>\3?\3?\3?\3?\5?\u0203\n"+
		"?\3@\3@\3@\3@\3@\3@\3@\3@\3@\5@\u020e\n@\3A\3A\3A\3A\3A\3A\3A\3B\3B\3"+
		"B\3B\3B\3B\3B\3C\3C\3C\7C\u0221\nC\fC\16C\u0224\13C\3D\3D\3E\3E\3F\6F"+
		"\u022b\nF\rF\16F\u022c\3F\3F\3G\3G\3G\3G\7G\u0235\nG\fG\16G\u0238\13G"+
		"\3G\3G\3G\3G\3G\3H\3H\3H\3H\7H\u0243\nH\fH\16H\u0246\13H\3H\3H\3\u0236"+
		"\2I\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\2i\2k\2m\2"+
		"o\2q\2s\65u\2w\2y\66{\67}\2\177\2\u0081\2\u00838\u00859\u0087\2\u0089"+
		"\2\u008b:\u008d;\u008f<\3\2\21\4\2ZZzz\4\2DDdd\4\2CHch\4\2NNnn\4\2RRr"+
		"r\4\2--//\4\2GGgg\6\2FFHHffhh\4\2))^^\4\2$$^^\n\2$$))^^ddhhppttvv\16\2"+
		"&&C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u2001\u3042\u3191\u3302\u3381"+
		"\u3402\u3d2f\u4e02\ua001\uf902\ufb01\21\2\62;\u0662\u066b\u06f2\u06fb"+
		"\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71\u0be9\u0bf1"+
		"\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb\u1042\u104b"+
		"\5\2\13\f\16\17\"\"\4\2\f\f\17\17\u0268\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)"+
		"\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2"+
		"\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2"+
		"A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2"+
		"\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2"+
		"s\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u008b"+
		"\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\3\u0091\3\2\2\2\5\u0097\3\2\2"+
		"\2\7\u0099\3\2\2\2\t\u009c\3\2\2\2\13\u009f\3\2\2\2\r\u00a9\3\2\2\2\17"+
		"\u00b2\3\2\2\2\21\u00b9\3\2\2\2\23\u00bf\3\2\2\2\25\u00c8\3\2\2\2\27\u00d4"+
		"\3\2\2\2\31\u00d9\3\2\2\2\33\u00e0\3\2\2\2\35\u00e5\3\2\2\2\37\u00e8\3"+
		"\2\2\2!\u00f2\3\2\2\2#\u00fa\3\2\2\2%\u0100\3\2\2\2\'\u0106\3\2\2\2)\u010b"+
		"\3\2\2\2+\u0111\3\2\2\2-\u0116\3\2\2\2/\u0118\3\2\2\2\61\u011a\3\2\2\2"+
		"\63\u011c\3\2\2\2\65\u011e\3\2\2\2\67\u0120\3\2\2\29\u0122\3\2\2\2;\u0124"+
		"\3\2\2\2=\u0126\3\2\2\2?\u0128\3\2\2\2A\u012a\3\2\2\2C\u012c\3\2\2\2E"+
		"\u012e\3\2\2\2G\u0130\3\2\2\2I\u0132\3\2\2\2K\u0135\3\2\2\2M\u0138\3\2"+
		"\2\2O\u013f\3\2\2\2Q\u0141\3\2\2\2S\u0143\3\2\2\2U\u0145\3\2\2\2W\u0147"+
		"\3\2\2\2Y\u0149\3\2\2\2[\u014c\3\2\2\2]\u014f\3\2\2\2_\u0153\3\2\2\2a"+
		"\u0167\3\2\2\2c\u016c\3\2\2\2e\u017b\3\2\2\2g\u018d\3\2\2\2i\u018f\3\2"+
		"\2\2k\u019e\3\2\2\2m\u01a0\3\2\2\2o\u01ad\3\2\2\2q\u01af\3\2\2\2s\u01e3"+
		"\3\2\2\2u\u01e5\3\2\2\2w\u01eb\3\2\2\2y\u01ed\3\2\2\2{\u01f4\3\2\2\2}"+
		"\u0202\3\2\2\2\177\u020d\3\2\2\2\u0081\u020f\3\2\2\2\u0083\u0216\3\2\2"+
		"\2\u0085\u021d\3\2\2\2\u0087\u0225\3\2\2\2\u0089\u0227\3\2\2\2\u008b\u022a"+
		"\3\2\2\2\u008d\u0230\3\2\2\2\u008f\u023e\3\2\2\2\u0091\u0092\7u\2\2\u0092"+
		"\u0093\7w\2\2\u0093\u0094\7r\2\2\u0094\u0095\7g\2\2\u0095\u0096\7t\2\2"+
		"\u0096\4\3\2\2\2\u0097\u0098\7\'\2\2\u0098\6\3\2\2\2\u0099\u009a\7>\2"+
		"\2\u009a\u009b\7?\2\2\u009b\b\3\2\2\2\u009c\u009d\7@\2\2\u009d\u009e\7"+
		"?\2\2\u009e\n\3\2\2\2\u009f\u00a0\7p\2\2\u00a0\u00a1\7c\2\2\u00a1\u00a2"+
		"\7o\2\2\u00a2\u00a3\7g\2\2\u00a3\u00a4\7u\2\2\u00a4\u00a5\7r\2\2\u00a5"+
		"\u00a6\7c\2\2\u00a6\u00a7\7e\2\2\u00a7\u00a8\7g\2\2\u00a8\f\3\2\2\2\u00a9"+
		"\u00aa\7c\2\2\u00aa\u00ab\7d\2\2\u00ab\u00ac\7u\2\2\u00ac\u00ad\7v\2\2"+
		"\u00ad\u00ae\7t\2\2\u00ae\u00af\7c\2\2\u00af\u00b0\7e\2\2\u00b0\u00b1"+
		"\7v\2\2\u00b1\16\3\2\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5"+
		"\7v\2\2\u00b5\u00b6\7k\2\2\u00b6\u00b7\7x\2\2\u00b7\u00b8\7g\2\2\u00b8"+
		"\20\3\2\2\2\u00b9\u00ba\7h\2\2\u00ba\u00bb\7k\2\2\u00bb\u00bc\7g\2\2\u00bc"+
		"\u00bd\7n\2\2\u00bd\u00be\7f\2\2\u00be\22\3\2\2\2\u00bf\u00c0\7d\2\2\u00c0"+
		"\u00c1\7n\2\2\u00c1\u00c2\7q\2\2\u00c2\u00c3\7e\2\2\u00c3\u00c4\7m\2\2"+
		"\u00c4\u00c5\7k\2\2\u00c5\u00c6\7p\2\2\u00c6\u00c7\7i\2\2\u00c7\24\3\2"+
		"\2\2\u00c8\u00c9\7r\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7q\2\2\u00cb\u00cc"+
		"\7r\2\2\u00cc\u00cd\7c\2\2\u00cd\u00ce\7i\2\2\u00ce\u00cf\7c\2\2\u00cf"+
		"\u00d0\7v\2\2\u00d0\u00d1\7k\2\2\u00d1\u00d2\7p\2\2\u00d2\u00d3\7i\2\2"+
		"\u00d3\26\3\2\2\2\u00d4\u00d5\7n\2\2\u00d5\u00d6\7k\2\2\u00d6\u00d7\7"+
		"m\2\2\u00d7\u00d8\7g\2\2\u00d8\30\3\2\2\2\u00d9\u00da\7k\2\2\u00da\u00db"+
		"\7o\2\2\u00db\u00dc\7r\2\2\u00dc\u00dd\7q\2\2\u00dd\u00de\7t\2\2\u00de"+
		"\u00df\7v\2\2\u00df\32\3\2\2\2\u00e0\u00e1\7u\2\2\u00e1\u00e2\7g\2\2\u00e2"+
		"\u00e3\7n\2\2\u00e3\u00e4\7h\2\2\u00e4\34\3\2\2\2\u00e5\u00e6\7/\2\2\u00e6"+
		"\u00e7\7@\2\2\u00e7\36\3\2\2\2\u00e8\u00e9\7u\2\2\u00e9\u00ea\7w\2\2\u00ea"+
		"\u00eb\7d\2\2\u00eb\u00ec\7q\2\2\u00ec\u00ed\7d\2\2\u00ed\u00ee\7l\2\2"+
		"\u00ee\u00ef\7g\2\2\u00ef\u00f0\7e\2\2\u00f0\u00f1\7v\2\2\u00f1 \3\2\2"+
		"\2\u00f2\u00f3\7g\2\2\u00f3\u00f4\7z\2\2\u00f4\u00f5\7v\2\2\u00f5\u00f6"+
		"\7g\2\2\u00f6\u00f7\7p\2\2\u00f7\u00f8\7f\2\2\u00f8\u00f9\7u\2\2\u00f9"+
		"\"\3\2\2\2\u00fa\u00fb\7e\2\2\u00fb\u00fc\7n\2\2\u00fc\u00fd\7c\2\2\u00fd"+
		"\u00fe\7u\2\2\u00fe\u00ff\7u\2\2\u00ff$\3\2\2\2\u0100\u0101\7n\2\2\u0101"+
		"\u0102\7c\2\2\u0102\u0103\7{\2\2\u0103\u0104\7g\2\2\u0104\u0105\7t\2\2"+
		"\u0105&\3\2\2\2\u0106\u0107\7v\2\2\u0107\u0108\7t\2\2\u0108\u0109\7w\2"+
		"\2\u0109\u010a\7g\2\2\u010a(\3\2\2\2\u010b\u010c\7h\2\2\u010c\u010d\7"+
		"c\2\2\u010d\u010e\7n\2\2\u010e\u010f\7u\2\2\u010f\u0110\7g\2\2\u0110*"+
		"\3\2\2\2\u0111\u0112\7p\2\2\u0112\u0113\7w\2\2\u0113\u0114\7n\2\2\u0114"+
		"\u0115\7n\2\2\u0115,\3\2\2\2\u0116\u0117\7a\2\2\u0117.\3\2\2\2\u0118\u0119"+
		"\7~\2\2\u0119\60\3\2\2\2\u011a\u011b\7/\2\2\u011b\62\3\2\2\2\u011c\u011d"+
		"\7-\2\2\u011d\64\3\2\2\2\u011e\u011f\7\61\2\2\u011f\66\3\2\2\2\u0120\u0121"+
		"\7,\2\2\u01218\3\2\2\2\u0122\u0123\7=\2\2\u0123:\3\2\2\2\u0124\u0125\7"+
		"\60\2\2\u0125<\3\2\2\2\u0126\u0127\7.\2\2\u0127>\3\2\2\2\u0128\u0129\7"+
		"}\2\2\u0129@\3\2\2\2\u012a\u012b\7\177\2\2\u012bB\3\2\2\2\u012c\u012d"+
		"\7*\2\2\u012dD\3\2\2\2\u012e\u012f\7+\2\2\u012fF\3\2\2\2\u0130\u0131\7"+
		"?\2\2\u0131H\3\2\2\2\u0132\u0133\7?\2\2\u0133\u0134\7?\2\2\u0134J\3\2"+
		"\2\2\u0135\u0136\7#\2\2\u0136\u0137\7?\2\2\u0137L\3\2\2\2\u0138\u0139"+
		"\7t\2\2\u0139\u013a\7g\2\2\u013a\u013b\7v\2\2\u013b\u013c\7w\2\2\u013c"+
		"\u013d\7t\2\2\u013d\u013e\7p\2\2\u013eN\3\2\2\2\u013f\u0140\7`\2\2\u0140"+
		"P\3\2\2\2\u0141\u0142\7(\2\2\u0142R\3\2\2\2\u0143\u0144\7<\2\2\u0144T"+
		"\3\2\2\2\u0145\u0146\7>\2\2\u0146V\3\2\2\2\u0147\u0148\7@\2\2\u0148X\3"+
		"\2\2\2\u0149\u014a\7>\2\2\u014a\u014b\7>\2\2\u014bZ\3\2\2\2\u014c\u014d"+
		"\7@\2\2\u014d\u014e\7@\2\2\u014e\\\3\2\2\2\u014f\u0150\7@\2\2\u0150\u0151"+
		"\7@\2\2\u0151\u0152\7@\2\2\u0152^\3\2\2\2\u0153\u0154\7\62\2\2\u0154\u0155"+
		"\t\2\2\2\u0155\u0157\5i\65\2\u0156\u0158\5q9\2\u0157\u0156\3\2\2\2\u0157"+
		"\u0158\3\2\2\2\u0158`\3\2\2\2\u0159\u0168\7\62\2\2\u015a\u0164\4\63;\2"+
		"\u015b\u015d\5-\27\2\u015c\u015b\3\2\2\2\u015d\u0160\3\2\2\2\u015e\u015c"+
		"\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0161\3\2\2\2\u0160\u015e\3\2\2\2\u0161"+
		"\u0163\5o8\2\u0162\u015e\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2\2"+
		"\2\u0164\u0165\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0167\u0159"+
		"\3\2\2\2\u0167\u015a\3\2\2\2\u0168\u016a\3\2\2\2\u0169\u016b\5q9\2\u016a"+
		"\u0169\3\2\2\2\u016a\u016b\3\2\2\2\u016bb\3\2\2\2\u016c\u0174\7\62\2\2"+
		"\u016d\u016f\5-\27\2\u016e\u016d\3\2\2\2\u016f\u0172\3\2\2\2\u0170\u016e"+
		"\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0173\3\2\2\2\u0172\u0170\3\2\2\2\u0173"+
		"\u0175\4\629\2\u0174\u0170\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u0174\3\2"+
		"\2\2\u0176\u0177\3\2\2\2\u0177\u0179\3\2\2\2\u0178\u017a\5q9\2\u0179\u0178"+
		"\3\2\2\2\u0179\u017a\3\2\2\2\u017ad\3\2\2\2\u017b\u017c\7\62\2\2\u017c"+
		"\u017d\t\3\2\2\u017d\u0187\5g\64\2\u017e\u0180\5-\27\2\u017f\u017e\3\2"+
		"\2\2\u0180\u0183\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182"+
		"\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0186\5g\64\2\u0185\u0181\3\2"+
		"\2\2\u0186\u0189\3\2\2\2\u0187\u0185\3\2\2\2\u0187\u0188\3\2\2\2\u0188"+
		"\u018b\3\2\2\2\u0189\u0187\3\2\2\2\u018a\u018c\5q9\2\u018b\u018a\3\2\2"+
		"\2\u018b\u018c\3\2\2\2\u018cf\3\2\2\2\u018d\u018e\4\62\63\2\u018eh\3\2"+
		"\2\2\u018f\u0199\5k\66\2\u0190\u0192\5-\27\2\u0191\u0190\3\2\2\2\u0192"+
		"\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194\u0196\3\2"+
		"\2\2\u0195\u0193\3\2\2\2\u0196\u0198\5k\66\2\u0197\u0193\3\2\2\2\u0198"+
		"\u019b\3\2\2\2\u0199\u0197\3\2\2\2\u0199\u019a\3\2\2\2\u019aj\3\2\2\2"+
		"\u019b\u0199\3\2\2\2\u019c\u019f\5o8\2\u019d\u019f\t\4\2\2\u019e\u019c"+
		"\3\2\2\2\u019e\u019d\3\2\2\2\u019fl\3\2\2\2\u01a0\u01aa\5o8\2\u01a1\u01a3"+
		"\5-\27\2\u01a2\u01a1\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2\2\2\u01a4"+
		"\u01a5\3\2\2\2\u01a5\u01a7\3\2\2\2\u01a6\u01a4\3\2\2\2\u01a7\u01a9\5o"+
		"8\2\u01a8\u01a4\3\2\2\2\u01a9\u01ac\3\2\2\2\u01aa\u01a8\3\2\2\2\u01aa"+
		"\u01ab\3\2\2\2\u01abn\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ad\u01ae\4\62;\2"+
		"\u01aep\3\2\2\2\u01af\u01b0\t\5\2\2\u01b0r\3\2\2\2\u01b1\u01b2\5m\67\2"+
		"\u01b2\u01b4\7\60\2\2\u01b3\u01b5\5m\67\2\u01b4\u01b3\3\2\2\2\u01b4\u01b5"+
		"\3\2\2\2\u01b5\u01b7\3\2\2\2\u01b6\u01b8\5u;\2\u01b7\u01b6\3\2\2\2\u01b7"+
		"\u01b8\3\2\2\2\u01b8\u01ba\3\2\2\2\u01b9\u01bb\5w<\2\u01ba\u01b9\3\2\2"+
		"\2\u01ba\u01bb\3\2\2\2\u01bb\u01e4\3\2\2\2\u01bc\u01bd\7\60\2\2\u01bd"+
		"\u01bf\5m\67\2\u01be\u01c0\5u;\2\u01bf\u01be\3\2\2\2\u01bf\u01c0\3\2\2"+
		"\2\u01c0\u01c2\3\2\2\2\u01c1\u01c3\5w<\2\u01c2\u01c1\3\2\2\2\u01c2\u01c3"+
		"\3\2\2\2\u01c3\u01e4\3\2\2\2\u01c4\u01c5\5m\67\2\u01c5\u01c7\5u;\2\u01c6"+
		"\u01c8\5w<\2\u01c7\u01c6\3\2\2\2\u01c7\u01c8\3\2\2\2\u01c8\u01e4\3\2\2"+
		"\2\u01c9\u01ca\5m\67\2\u01ca\u01cb\5w<\2\u01cb\u01e4\3\2\2\2\u01cc\u01cd"+
		"\7\62\2\2\u01cd\u01d1\7z\2\2\u01ce\u01cf\7\62\2\2\u01cf\u01d1\7Z\2\2\u01d0"+
		"\u01cc\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d1\u01d3\3\2\2\2\u01d2\u01d4\5i"+
		"\65\2\u01d3\u01d2\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d9\3\2\2\2\u01d5"+
		"\u01d7\7\60\2\2\u01d6\u01d8\5i\65\2\u01d7\u01d6\3\2\2\2\u01d7\u01d8\3"+
		"\2\2\2\u01d8\u01da\3\2\2\2\u01d9\u01d5\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u01db\3\2\2\2\u01db\u01dd\t\6\2\2\u01dc\u01de\t\7\2\2\u01dd\u01dc\3\2"+
		"\2\2\u01dd\u01de\3\2\2\2\u01de\u01df\3\2\2\2\u01df\u01e1\5m\67\2\u01e0"+
		"\u01e2\5w<\2\u01e1\u01e0\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2\u01e4\3\2\2"+
		"\2\u01e3\u01b1\3\2\2\2\u01e3\u01bc\3\2\2\2\u01e3\u01c4\3\2\2\2\u01e3\u01c9"+
		"\3\2\2\2\u01e3\u01d0\3\2\2\2\u01e4t\3\2\2\2\u01e5\u01e7\t\b\2\2\u01e6"+
		"\u01e8\t\7\2\2\u01e7\u01e6\3\2\2\2\u01e7\u01e8\3\2\2\2\u01e8\u01e9\3\2"+
		"\2\2\u01e9\u01ea\5m\67\2\u01eav\3\2\2\2\u01eb\u01ec\t\t\2\2\u01ecx\3\2"+
		"\2\2\u01ed\u01f0\7)\2\2\u01ee\u01f1\5}?\2\u01ef\u01f1\n\n\2\2\u01f0\u01ee"+
		"\3\2\2\2\u01f0\u01ef\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f3\7)\2\2\u01f3"+
		"z\3\2\2\2\u01f4\u01f9\7$\2\2\u01f5\u01f8\5}?\2\u01f6\u01f8\n\13\2\2\u01f7"+
		"\u01f5\3\2\2\2\u01f7\u01f6\3\2\2\2\u01f8\u01fb\3\2\2\2\u01f9\u01f7\3\2"+
		"\2\2\u01f9\u01fa\3\2\2\2\u01fa\u01fc\3\2\2\2\u01fb\u01f9\3\2\2\2\u01fc"+
		"\u01fd\7$\2\2\u01fd|\3\2\2\2\u01fe\u01ff\7^\2\2\u01ff\u0203\t\f\2\2\u0200"+
		"\u0203\5\u0081A\2\u0201\u0203\5\177@\2\u0202\u01fe\3\2\2\2\u0202\u0200"+
		"\3\2\2\2\u0202\u0201\3\2\2\2\u0203~\3\2\2\2\u0204\u0205\7^\2\2\u0205\u0206"+
		"\4\62\65\2\u0206\u0207\4\629\2\u0207\u020e\4\629\2\u0208\u0209\7^\2\2"+
		"\u0209\u020a\4\629\2\u020a\u020e\4\629\2\u020b\u020c\7^\2\2\u020c\u020e"+
		"\4\629\2\u020d\u0204\3\2\2\2\u020d\u0208\3\2\2\2\u020d\u020b\3\2\2\2\u020e"+
		"\u0080\3\2\2\2\u020f\u0210\7^\2\2\u0210\u0211\7w\2\2\u0211\u0212\5k\66"+
		"\2\u0212\u0213\5k\66\2\u0213\u0214\5k\66\2\u0214\u0215\5k\66\2\u0215\u0082"+
		"\3\2\2\2\u0216\u0217\7c\2\2\u0217\u0218\7u\2\2\u0218\u0219\7u\2\2\u0219"+
		"\u021a\7g\2\2\u021a\u021b\7t\2\2\u021b\u021c\7v\2\2\u021c\u0084\3\2\2"+
		"\2\u021d\u0222\5\u0087D\2\u021e\u0221\5\u0087D\2\u021f\u0221\5\u0089E"+
		"\2\u0220\u021e\3\2\2\2\u0220\u021f\3\2\2\2\u0221\u0224\3\2\2\2\u0222\u0220"+
		"\3\2\2\2\u0222\u0223\3\2\2\2\u0223\u0086\3\2\2\2\u0224\u0222\3\2\2\2\u0225"+
		"\u0226\t\r\2\2\u0226\u0088\3\2\2\2\u0227\u0228\t\16\2\2\u0228\u008a\3"+
		"\2\2\2\u0229\u022b\t\17\2\2\u022a\u0229\3\2\2\2\u022b\u022c\3\2\2\2\u022c"+
		"\u022a\3\2\2\2\u022c\u022d\3\2\2\2\u022d\u022e\3\2\2\2\u022e\u022f\bF"+
		"\2\2\u022f\u008c\3\2\2\2\u0230\u0231\7\61\2\2\u0231\u0232\7,\2\2\u0232"+
		"\u0236\3\2\2\2\u0233\u0235\13\2\2\2\u0234\u0233\3\2\2\2\u0235\u0238\3"+
		"\2\2\2\u0236\u0237\3\2\2\2\u0236\u0234\3\2\2\2\u0237\u0239\3\2\2\2\u0238"+
		"\u0236\3\2\2\2\u0239\u023a\7,\2\2\u023a\u023b\7\61\2\2\u023b\u023c\3\2"+
		"\2\2\u023c\u023d\bG\2\2\u023d\u008e\3\2\2\2\u023e\u023f\7\61\2\2\u023f"+
		"\u0240\7\61\2\2\u0240\u0244\3\2\2\2\u0241\u0243\n\20\2\2\u0242\u0241\3"+
		"\2\2\2\u0243\u0246\3\2\2\2\u0244\u0242\3\2\2\2\u0244\u0245\3\2\2\2\u0245"+
		"\u0247\3\2\2\2\u0246\u0244\3\2\2\2\u0247\u0248\bH\2\2\u0248\u0090\3\2"+
		"\2\2+\2\u0157\u015e\u0164\u0167\u016a\u0170\u0176\u0179\u0181\u0187\u018b"+
		"\u0193\u0199\u019e\u01a4\u01aa\u01b4\u01b7\u01ba\u01bf\u01c2\u01c7\u01d0"+
		"\u01d3\u01d7\u01d9\u01dd\u01e1\u01e3\u01e7\u01f0\u01f7\u01f9\u0202\u020d"+
		"\u0220\u0222\u022c\u0236\u0244\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}