// $ANTLR 3.3 Nov 30, 2010 12:45:30 JavaL.g 2013-07-12 20:46:31

  package org.aikodi.jlo.input;


import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class JLo_JavaL extends Lexer {
    public static final int EOF=-1;
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
    public static final int Identifier=4;
    public static final int ENUM=5;
    public static final int FloatingPointLiteral=6;
    public static final int CharacterLiteral=7;
    public static final int StringLiteral=8;
    public static final int IntegerLiteral=9;
    public static final int ASSERT=10;
    public static final int DecimalIntegerLiteral=11;
    public static final int HexIntegerLiteral=12;
    public static final int OctalIntegerLiteral=13;
    public static final int BinaryIntegerLiteral=14;
    public static final int DecimalNumeral=15;
    public static final int IntegerTypeSuffix=16;
    public static final int NonZeroDigit=17;
    public static final int Digits=18;
    public static final int Digit=19;
    public static final int OctalNumeral=20;
    public static final int OctalDigits=21;
    public static final int OctalDigit=22;
    public static final int HexNumeral=23;
    public static final int HexDigits=24;
    public static final int HexDigit=25;
    public static final int BinaryNumeral=26;
    public static final int BinaryDigits=27;
    public static final int BinaryDigit=28;
    public static final int DecimalFloatingPointLiteral=29;
    public static final int HexadecimalFloatingPointLiteral=30;
    public static final int ExponentPart=31;
    public static final int FloatTypeSuffix=32;
    public static final int ExponentIndicator=33;
    public static final int SignedInteger=34;
    public static final int Sign=35;
    public static final int HexSignificand=36;
    public static final int BinaryExponent=37;
    public static final int BinaryExponentIndicator=38;
    public static final int EscapeSequence=39;
    public static final int UnicodeEscape=40;
    public static final int OctalEscape=41;
    public static final int Letter=42;
    public static final int JavaIDDigit=43;
    public static final int WS=44;
    public static final int COMMENT=45;
    public static final int LINE_COMMENT=46;
    public static final int Tokens=136;

      public final static int JAVADOC_CHANNEL=1;
      protected boolean enumIsKeyword = true;
      protected boolean assertIsKeyword = true;


    // delegates
    // delegators
    public JLoLexer gJLo;
    public JLoLexer gParent;

    public JLo_JavaL() {;} 
    public JLo_JavaL(CharStream input, JLoLexer gJLo) {
        this(input, new RecognizerSharedState(), gJLo);
    }
    public JLo_JavaL(CharStream input, RecognizerSharedState state, JLoLexer gJLo) {
        super(input,state);

        this.gJLo = gJLo;
        gParent = gJLo;
    }
    public String getGrammarFileName() { return "JavaL.g"; }

    // $ANTLR start "IntegerLiteral"
    public final void mIntegerLiteral() throws RecognitionException {
        try {
            int _type = IntegerLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:13:1: ( DecimalIntegerLiteral | HexIntegerLiteral | OctalIntegerLiteral | BinaryIntegerLiteral )
            int alt1=4;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='0') ) {
                switch ( input.LA(2) ) {
                case 'X':
                case 'x':
                    {
                    alt1=2;
                    }
                    break;
                case 'B':
                case 'b':
                    {
                    alt1=4;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '_':
                    {
                    alt1=3;
                    }
                    break;
                default:
                    alt1=1;}

            }
            else if ( ((LA1_0>='1' && LA1_0<='9')) ) {
                alt1=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // JavaL.g:13:3: DecimalIntegerLiteral
                    {
                    mDecimalIntegerLiteral(); 

                    }
                    break;
                case 2 :
                    // JavaL.g:14:3: HexIntegerLiteral
                    {
                    mHexIntegerLiteral(); 

                    }
                    break;
                case 3 :
                    // JavaL.g:15:3: OctalIntegerLiteral
                    {
                    mOctalIntegerLiteral(); 

                    }
                    break;
                case 4 :
                    // JavaL.g:16:3: BinaryIntegerLiteral
                    {
                    mBinaryIntegerLiteral(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IntegerLiteral"

    // $ANTLR start "DecimalIntegerLiteral"
    public final void mDecimalIntegerLiteral() throws RecognitionException {
        try {
            // JavaL.g:21:1: ( DecimalNumeral ( IntegerTypeSuffix )? )
            // JavaL.g:21:3: DecimalNumeral ( IntegerTypeSuffix )?
            {
            mDecimalNumeral(); 
            // JavaL.g:21:18: ( IntegerTypeSuffix )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='L'||LA2_0=='l') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // JavaL.g:21:18: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "DecimalIntegerLiteral"

    // $ANTLR start "DecimalNumeral"
    public final void mDecimalNumeral() throws RecognitionException {
        try {
            // JavaL.g:27:1: ( '0' | NonZeroDigit ( ( Digits )? | ( '_' )+ Digits ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='0') ) {
                alt6=1;
            }
            else if ( ((LA6_0>='1' && LA6_0<='9')) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // JavaL.g:27:3: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // JavaL.g:28:3: NonZeroDigit ( ( Digits )? | ( '_' )+ Digits )
                    {
                    mNonZeroDigit(); 
                    // JavaL.g:28:16: ( ( Digits )? | ( '_' )+ Digits )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='_') ) {
                        alt5=2;
                    }
                    else {
                        alt5=1;}
                    switch (alt5) {
                        case 1 :
                            // JavaL.g:28:17: ( Digits )?
                            {
                            // JavaL.g:28:17: ( Digits )?
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                                alt3=1;
                            }
                            switch (alt3) {
                                case 1 :
                                    // JavaL.g:28:17: Digits
                                    {
                                    mDigits(); 

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // JavaL.g:28:27: ( '_' )+ Digits
                            {
                            // JavaL.g:28:27: ( '_' )+
                            int cnt4=0;
                            loop4:
                            do {
                                int alt4=2;
                                int LA4_0 = input.LA(1);

                                if ( (LA4_0=='_') ) {
                                    alt4=1;
                                }


                                switch (alt4) {
                            	case 1 :
                            	    // JavaL.g:28:28: '_'
                            	    {
                            	    match('_'); 

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt4 >= 1 ) break loop4;
                                        EarlyExitException eee =
                                            new EarlyExitException(4, input);
                                        throw eee;
                                }
                                cnt4++;
                            } while (true);

                            mDigits(); 

                            }
                            break;

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "DecimalNumeral"

    // $ANTLR start "Digits"
    public final void mDigits() throws RecognitionException {
        try {
            // JavaL.g:33:1: ( Digit ( ( Digit | '_' )* Digit )? )
            // JavaL.g:33:3: Digit ( ( Digit | '_' )* Digit )?
            {
            mDigit(); 
            // JavaL.g:33:9: ( ( Digit | '_' )* Digit )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>='0' && LA8_0<='9')||LA8_0=='_') ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // JavaL.g:33:10: ( Digit | '_' )* Digit
                    {
                    // JavaL.g:33:10: ( Digit | '_' )*
                    loop7:
                    do {
                        int alt7=3;
                        switch ( input.LA(1) ) {
                        case '0':
                            {
                            int LA7_1 = input.LA(2);

                            if ( ((LA7_1>='0' && LA7_1<='9')||LA7_1=='_') ) {
                                alt7=1;
                            }


                            }
                            break;
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            {
                            int LA7_2 = input.LA(2);

                            if ( ((LA7_2>='0' && LA7_2<='9')||LA7_2=='_') ) {
                                alt7=1;
                            }


                            }
                            break;
                        case '_':
                            {
                            alt7=2;
                            }
                            break;

                        }

                        switch (alt7) {
                    	case 1 :
                    	    // JavaL.g:33:11: Digit
                    	    {
                    	    mDigit(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // JavaL.g:33:19: '_'
                    	    {
                    	    match('_'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);

                    mDigit(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "Digits"

    // $ANTLR start "Digit"
    public final void mDigit() throws RecognitionException {
        try {
            // JavaL.g:38:1: ( '0' | NonZeroDigit )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='0') ) {
                alt9=1;
            }
            else if ( ((LA9_0>='1' && LA9_0<='9')) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // JavaL.g:38:3: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // JavaL.g:39:3: NonZeroDigit
                    {
                    mNonZeroDigit(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "Digit"

    // $ANTLR start "NonZeroDigit"
    public final void mNonZeroDigit() throws RecognitionException {
        try {
            // JavaL.g:44:1: ( ( '1' .. '9' ) )
            // JavaL.g:44:3: ( '1' .. '9' )
            {
            // JavaL.g:44:3: ( '1' .. '9' )
            // JavaL.g:44:4: '1' .. '9'
            {
            matchRange('1','9'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "NonZeroDigit"

    // $ANTLR start "OctalIntegerLiteral"
    public final void mOctalIntegerLiteral() throws RecognitionException {
        try {
            // JavaL.g:51:1: ( OctalNumeral ( IntegerTypeSuffix )? )
            // JavaL.g:51:3: OctalNumeral ( IntegerTypeSuffix )?
            {
            mOctalNumeral(); 
            // JavaL.g:51:16: ( IntegerTypeSuffix )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='L'||LA10_0=='l') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // JavaL.g:51:16: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "OctalIntegerLiteral"

    // $ANTLR start "OctalNumeral"
    public final void mOctalNumeral() throws RecognitionException {
        try {
            // JavaL.g:56:1: ( '0' ( '_' )* OctalDigits )
            // JavaL.g:56:3: '0' ( '_' )* OctalDigits
            {
            match('0'); 
            // JavaL.g:56:7: ( '_' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='_') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // JavaL.g:56:7: '_'
            	    {
            	    match('_'); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            mOctalDigits(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "OctalNumeral"

    // $ANTLR start "OctalDigits"
    public final void mOctalDigits() throws RecognitionException {
        try {
            // JavaL.g:61:1: ( OctalDigit ( ( OctalDigit | '_' )* OctalDigit )? )
            // JavaL.g:61:3: OctalDigit ( ( OctalDigit | '_' )* OctalDigit )?
            {
            mOctalDigit(); 
            // JavaL.g:61:14: ( ( OctalDigit | '_' )* OctalDigit )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>='0' && LA13_0<='7')||LA13_0=='_') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // JavaL.g:61:15: ( OctalDigit | '_' )* OctalDigit
                    {
                    // JavaL.g:61:15: ( OctalDigit | '_' )*
                    loop12:
                    do {
                        int alt12=3;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='7')) ) {
                            int LA12_1 = input.LA(2);

                            if ( ((LA12_1>='0' && LA12_1<='7')||LA12_1=='_') ) {
                                alt12=1;
                            }


                        }
                        else if ( (LA12_0=='_') ) {
                            alt12=2;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // JavaL.g:61:16: OctalDigit
                    	    {
                    	    mOctalDigit(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // JavaL.g:61:29: '_'
                    	    {
                    	    match('_'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);

                    mOctalDigit(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "OctalDigits"

    // $ANTLR start "OctalDigit"
    public final void mOctalDigit() throws RecognitionException {
        try {
            // JavaL.g:66:1: ( ( '0' .. '7' ) )
            // JavaL.g:66:3: ( '0' .. '7' )
            {
            // JavaL.g:66:3: ( '0' .. '7' )
            // JavaL.g:66:4: '0' .. '7'
            {
            matchRange('0','7'); 

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "OctalDigit"

    // $ANTLR start "HexIntegerLiteral"
    public final void mHexIntegerLiteral() throws RecognitionException {
        try {
            // JavaL.g:74:1: ( HexNumeral ( IntegerTypeSuffix )? )
            // JavaL.g:74:3: HexNumeral ( IntegerTypeSuffix )?
            {
            mHexNumeral(); 
            // JavaL.g:74:14: ( IntegerTypeSuffix )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='L'||LA14_0=='l') ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // JavaL.g:74:14: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexIntegerLiteral"

    // $ANTLR start "HexNumeral"
    public final void mHexNumeral() throws RecognitionException {
        try {
            // JavaL.g:79:1: ( '0' ( 'x' | 'X' ) HexDigits )
            // JavaL.g:79:3: '0' ( 'x' | 'X' ) HexDigits
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            mHexDigits(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "HexNumeral"

    // $ANTLR start "HexDigits"
    public final void mHexDigits() throws RecognitionException {
        try {
            // JavaL.g:84:1: ( HexDigit ( ( HexDigit | '_' )* HexDigit )? )
            // JavaL.g:84:3: HexDigit ( ( HexDigit | '_' )* HexDigit )?
            {
            mHexDigit(); 
            // JavaL.g:84:12: ( ( HexDigit | '_' )* HexDigit )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='F')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='f')) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // JavaL.g:84:13: ( HexDigit | '_' )* HexDigit
                    {
                    // JavaL.g:84:13: ( HexDigit | '_' )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')||(LA15_0>='A' && LA15_0<='F')||(LA15_0>='a' && LA15_0<='f')) ) {
                            int LA15_1 = input.LA(2);

                            if ( ((LA15_1>='0' && LA15_1<='9')||(LA15_1>='A' && LA15_1<='F')||LA15_1=='_'||(LA15_1>='a' && LA15_1<='f')) ) {
                                alt15=1;
                            }


                        }
                        else if ( (LA15_0=='_') ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // JavaL.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);

                    mHexDigit(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigits"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // JavaL.g:88:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // JavaL.g:88:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "BinaryIntegerLiteral"
    public final void mBinaryIntegerLiteral() throws RecognitionException {
        try {
            // JavaL.g:94:1: ( BinaryNumeral ( IntegerTypeSuffix )? )
            // JavaL.g:94:3: BinaryNumeral ( IntegerTypeSuffix )?
            {
            mBinaryNumeral(); 
            // JavaL.g:94:17: ( IntegerTypeSuffix )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='L'||LA17_0=='l') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // JavaL.g:94:17: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryIntegerLiteral"

    // $ANTLR start "BinaryNumeral"
    public final void mBinaryNumeral() throws RecognitionException {
        try {
            // JavaL.g:100:1: ( '0' ( 'b' | 'B' ) BinaryDigits )
            // JavaL.g:100:3: '0' ( 'b' | 'B' ) BinaryDigits
            {
            match('0'); 
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            mBinaryDigits(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryNumeral"

    // $ANTLR start "BinaryDigits"
    public final void mBinaryDigits() throws RecognitionException {
        try {
            // JavaL.g:105:1: ( BinaryDigit ( ( BinaryDigit | '_' )* BinaryDigit )? )
            // JavaL.g:105:3: BinaryDigit ( ( BinaryDigit | '_' )* BinaryDigit )?
            {
            mBinaryDigit(); 
            // JavaL.g:105:15: ( ( BinaryDigit | '_' )* BinaryDigit )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>='0' && LA19_0<='1')||LA19_0=='_') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // JavaL.g:105:16: ( BinaryDigit | '_' )* BinaryDigit
                    {
                    // JavaL.g:105:16: ( BinaryDigit | '_' )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( ((LA18_0>='0' && LA18_0<='1')) ) {
                            int LA18_1 = input.LA(2);

                            if ( ((LA18_1>='0' && LA18_1<='1')||LA18_1=='_') ) {
                                alt18=1;
                            }


                        }
                        else if ( (LA18_0=='_') ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // JavaL.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='1')||input.LA(1)=='_' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    mBinaryDigit(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryDigits"

    // $ANTLR start "BinaryDigit"
    public final void mBinaryDigit() throws RecognitionException {
        try {
            // JavaL.g:110:1: ( ( '0' | '1' ) )
            // JavaL.g:110:3: ( '0' | '1' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='1') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryDigit"

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // JavaL.g:115:19: ( ( 'l' | 'L' ) )
            // JavaL.g:115:21: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IntegerTypeSuffix"

    // $ANTLR start "FloatingPointLiteral"
    public final void mFloatingPointLiteral() throws RecognitionException {
        try {
            int _type = FloatingPointLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:120:1: ( DecimalFloatingPointLiteral | HexadecimalFloatingPointLiteral )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0=='0') ) {
                int LA20_1 = input.LA(2);

                if ( (LA20_1=='X'||LA20_1=='x') ) {
                    alt20=2;
                }
                else if ( (LA20_1=='.'||(LA20_1>='0' && LA20_1<='9')||(LA20_1>='D' && LA20_1<='F')||LA20_1=='_'||(LA20_1>='d' && LA20_1<='f')) ) {
                    alt20=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA20_0=='.'||(LA20_0>='1' && LA20_0<='9')) ) {
                alt20=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // JavaL.g:120:3: DecimalFloatingPointLiteral
                    {
                    mDecimalFloatingPointLiteral(); 

                    }
                    break;
                case 2 :
                    // JavaL.g:121:3: HexadecimalFloatingPointLiteral
                    {
                    mHexadecimalFloatingPointLiteral(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FloatingPointLiteral"

    // $ANTLR start "DecimalFloatingPointLiteral"
    public final void mDecimalFloatingPointLiteral() throws RecognitionException {
        try {
            // JavaL.g:126:1: ( Digits '.' ( Digits )? ( ExponentPart )? ( FloatTypeSuffix )? | '.' Digits ( ExponentPart )? ( FloatTypeSuffix )? | Digits ExponentPart ( FloatTypeSuffix )? | Digits FloatTypeSuffix )
            int alt27=4;
            alt27 = dfa27.predict(input);
            switch (alt27) {
                case 1 :
                    // JavaL.g:126:3: Digits '.' ( Digits )? ( ExponentPart )? ( FloatTypeSuffix )?
                    {
                    mDigits(); 
                    match('.'); 
                    // JavaL.g:126:14: ( Digits )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( ((LA21_0>='0' && LA21_0<='9')) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // JavaL.g:126:14: Digits
                            {
                            mDigits(); 

                            }
                            break;

                    }

                    // JavaL.g:126:22: ( ExponentPart )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0=='E'||LA22_0=='e') ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // JavaL.g:126:22: ExponentPart
                            {
                            mExponentPart(); 

                            }
                            break;

                    }

                    // JavaL.g:126:36: ( FloatTypeSuffix )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0=='D'||LA23_0=='F'||LA23_0=='d'||LA23_0=='f') ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // JavaL.g:126:36: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // JavaL.g:127:3: '.' Digits ( ExponentPart )? ( FloatTypeSuffix )?
                    {
                    match('.'); 
                    mDigits(); 
                    // JavaL.g:127:14: ( ExponentPart )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0=='E'||LA24_0=='e') ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // JavaL.g:127:14: ExponentPart
                            {
                            mExponentPart(); 

                            }
                            break;

                    }

                    // JavaL.g:127:28: ( FloatTypeSuffix )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0=='D'||LA25_0=='F'||LA25_0=='d'||LA25_0=='f') ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // JavaL.g:127:28: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // JavaL.g:128:3: Digits ExponentPart ( FloatTypeSuffix )?
                    {
                    mDigits(); 
                    mExponentPart(); 
                    // JavaL.g:128:23: ( FloatTypeSuffix )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0=='D'||LA26_0=='F'||LA26_0=='d'||LA26_0=='f') ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // JavaL.g:128:23: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // JavaL.g:129:3: Digits FloatTypeSuffix
                    {
                    mDigits(); 
                    mFloatTypeSuffix(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "DecimalFloatingPointLiteral"

    // $ANTLR start "ExponentPart"
    public final void mExponentPart() throws RecognitionException {
        try {
            // JavaL.g:134:1: ( ExponentIndicator SignedInteger )
            // JavaL.g:134:3: ExponentIndicator SignedInteger
            {
            mExponentIndicator(); 
            mSignedInteger(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ExponentPart"

    // $ANTLR start "ExponentIndicator"
    public final void mExponentIndicator() throws RecognitionException {
        try {
            // JavaL.g:139:1: ( ( 'e' | 'E' ) )
            // JavaL.g:139:3: ( 'e' | 'E' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ExponentIndicator"

    // $ANTLR start "SignedInteger"
    public final void mSignedInteger() throws RecognitionException {
        try {
            // JavaL.g:144:1: ( ( Sign )? Digits )
            // JavaL.g:144:3: ( Sign )? Digits
            {
            // JavaL.g:144:3: ( Sign )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0=='+'||LA28_0=='-') ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // JavaL.g:144:3: Sign
                    {
                    mSign(); 

                    }
                    break;

            }

            mDigits(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "SignedInteger"

    // $ANTLR start "Sign"
    public final void mSign() throws RecognitionException {
        try {
            // JavaL.g:149:1: ( ( '+' | '-' ) )
            // JavaL.g:149:3: ( '+' | '-' )
            {
            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Sign"

    // $ANTLR start "FloatTypeSuffix"
    public final void mFloatTypeSuffix() throws RecognitionException {
        try {
            // JavaL.g:153:17: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // JavaL.g:153:19: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FloatTypeSuffix"

    // $ANTLR start "HexadecimalFloatingPointLiteral"
    public final void mHexadecimalFloatingPointLiteral() throws RecognitionException {
        try {
            // JavaL.g:157:1: ( HexSignificand BinaryExponent ( FloatTypeSuffix )? )
            // JavaL.g:157:3: HexSignificand BinaryExponent ( FloatTypeSuffix )?
            {
            mHexSignificand(); 
            mBinaryExponent(); 
            // JavaL.g:157:33: ( FloatTypeSuffix )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='D'||LA29_0=='F'||LA29_0=='d'||LA29_0=='f') ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // JavaL.g:157:33: FloatTypeSuffix
                    {
                    mFloatTypeSuffix(); 

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexadecimalFloatingPointLiteral"

    // $ANTLR start "HexSignificand"
    public final void mHexSignificand() throws RecognitionException {
        try {
            // JavaL.g:162:1: ( HexNumeral ( '.' )? | '0' ( 'x' | 'X' ) ( HexDigits )? '.' HexDigits )
            int alt32=2;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // JavaL.g:162:3: HexNumeral ( '.' )?
                    {
                    mHexNumeral(); 
                    // JavaL.g:162:14: ( '.' )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0=='.') ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // JavaL.g:162:14: '.'
                            {
                            match('.'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // JavaL.g:163:3: '0' ( 'x' | 'X' ) ( HexDigits )? '.' HexDigits
                    {
                    match('0'); 
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // JavaL.g:163:17: ( HexDigits )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( ((LA31_0>='0' && LA31_0<='9')||(LA31_0>='A' && LA31_0<='F')||(LA31_0>='a' && LA31_0<='f')) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // JavaL.g:163:17: HexDigits
                            {
                            mHexDigits(); 

                            }
                            break;

                    }

                    match('.'); 
                    mHexDigits(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "HexSignificand"

    // $ANTLR start "BinaryExponent"
    public final void mBinaryExponent() throws RecognitionException {
        try {
            // JavaL.g:168:1: ( BinaryExponentIndicator SignedInteger )
            // JavaL.g:168:3: BinaryExponentIndicator SignedInteger
            {
            mBinaryExponentIndicator(); 
            mSignedInteger(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryExponent"

    // $ANTLR start "BinaryExponentIndicator"
    public final void mBinaryExponentIndicator() throws RecognitionException {
        try {
            // JavaL.g:173:1: ( ( 'p' | 'P' ) )
            // JavaL.g:173:3: ( 'p' | 'P' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "BinaryExponentIndicator"

    // $ANTLR start "CharacterLiteral"
    public final void mCharacterLiteral() throws RecognitionException {
        try {
            int _type = CharacterLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:177:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\'' )
            // JavaL.g:177:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) ) '\\''
            {
            match('\''); 
            // JavaL.g:177:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0=='\\') ) {
                alt33=1;
            }
            else if ( ((LA33_0>='\u0000' && LA33_0<='&')||(LA33_0>='(' && LA33_0<='[')||(LA33_0>=']' && LA33_0<='\uFFFF')) ) {
                alt33=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 33, 0, input);

                throw nvae;
            }
            switch (alt33) {
                case 1 :
                    // JavaL.g:177:16: EscapeSequence
                    {
                    mEscapeSequence(); 

                    }
                    break;
                case 2 :
                    // JavaL.g:177:33: ~ ( '\\'' | '\\\\' )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CharacterLiteral"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:181:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // JavaL.g:181:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // JavaL.g:181:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop34:
            do {
                int alt34=3;
                int LA34_0 = input.LA(1);

                if ( (LA34_0=='\\') ) {
                    alt34=1;
                }
                else if ( ((LA34_0>='\u0000' && LA34_0<='!')||(LA34_0>='#' && LA34_0<='[')||(LA34_0>=']' && LA34_0<='\uFFFF')) ) {
                    alt34=2;
                }


                switch (alt34) {
            	case 1 :
            	    // JavaL.g:181:14: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // JavaL.g:181:31: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // JavaL.g:186:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UnicodeEscape | OctalEscape )
            int alt35=3;
            int LA35_0 = input.LA(1);

            if ( (LA35_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt35=1;
                    }
                    break;
                case 'u':
                    {
                    alt35=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt35=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 35, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // JavaL.g:186:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // JavaL.g:187:9: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // JavaL.g:188:9: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // JavaL.g:193:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt36=3;
            int LA36_0 = input.LA(1);

            if ( (LA36_0=='\\') ) {
                int LA36_1 = input.LA(2);

                if ( ((LA36_1>='0' && LA36_1<='3')) ) {
                    int LA36_2 = input.LA(3);

                    if ( ((LA36_2>='0' && LA36_2<='7')) ) {
                        int LA36_4 = input.LA(4);

                        if ( ((LA36_4>='0' && LA36_4<='7')) ) {
                            alt36=1;
                        }
                        else {
                            alt36=2;}
                    }
                    else {
                        alt36=3;}
                }
                else if ( ((LA36_1>='4' && LA36_1<='7')) ) {
                    int LA36_3 = input.LA(3);

                    if ( ((LA36_3>='0' && LA36_3<='7')) ) {
                        alt36=2;
                    }
                    else {
                        alt36=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // JavaL.g:193:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // JavaL.g:193:14: ( '0' .. '3' )
                    // JavaL.g:193:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // JavaL.g:193:25: ( '0' .. '7' )
                    // JavaL.g:193:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // JavaL.g:193:36: ( '0' .. '7' )
                    // JavaL.g:193:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // JavaL.g:194:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // JavaL.g:194:14: ( '0' .. '7' )
                    // JavaL.g:194:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // JavaL.g:194:25: ( '0' .. '7' )
                    // JavaL.g:194:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // JavaL.g:195:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // JavaL.g:195:14: ( '0' .. '7' )
                    // JavaL.g:195:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // JavaL.g:200:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // JavaL.g:200:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "ENUM"
    public final void mENUM() throws RecognitionException {
        try {
            int _type = ENUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:203:5: ( 'enum' )
            // JavaL.g:203:9: 'enum'
            {
            match("enum"); 

            if (!enumIsKeyword) _type=Identifier;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ENUM"

    // $ANTLR start "ASSERT"
    public final void mASSERT() throws RecognitionException {
        try {
            int _type = ASSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:207:5: ( 'assert' )
            // JavaL.g:207:9: 'assert'
            {
            match("assert"); 

            if (!assertIsKeyword) _type=Identifier;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSERT"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:211:5: ( Letter ( Letter | JavaIDDigit )* )
            // JavaL.g:211:9: Letter ( Letter | JavaIDDigit )*
            {
            mLetter(); 
            // JavaL.g:211:16: ( Letter | JavaIDDigit )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0=='$'||(LA37_0>='0' && LA37_0<='9')||(LA37_0>='A' && LA37_0<='Z')||LA37_0=='_'||(LA37_0>='a' && LA37_0<='z')||(LA37_0>='\u00C0' && LA37_0<='\u00D6')||(LA37_0>='\u00D8' && LA37_0<='\u00F6')||(LA37_0>='\u00F8' && LA37_0<='\u1FFF')||(LA37_0>='\u3040' && LA37_0<='\u318F')||(LA37_0>='\u3300' && LA37_0<='\u337F')||(LA37_0>='\u3400' && LA37_0<='\u3D2D')||(LA37_0>='\u4E00' && LA37_0<='\u9FFF')||(LA37_0>='\uF900' && LA37_0<='\uFAFF')) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // JavaL.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // JavaL.g:219:5: ( '\\u0024' | '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' )
            // JavaL.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "JavaIDDigit"
    public final void mJavaIDDigit() throws RecognitionException {
        try {
            // JavaL.g:236:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // JavaL.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "JavaIDDigit"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:253:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // JavaL.g:253:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:257:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // JavaL.g:257:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // JavaL.g:257:14: ( options {greedy=false; } : . )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0=='*') ) {
                    int LA38_1 = input.LA(2);

                    if ( (LA38_1=='/') ) {
                        alt38=2;
                    }
                    else if ( ((LA38_1>='\u0000' && LA38_1<='.')||(LA38_1>='0' && LA38_1<='\uFFFF')) ) {
                        alt38=1;
                    }


                }
                else if ( ((LA38_0>='\u0000' && LA38_0<=')')||(LA38_0>='+' && LA38_0<='\uFFFF')) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // JavaL.g:257:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // JavaL.g:261:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // JavaL.g:261:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // JavaL.g:261:12: (~ ( '\\n' | '\\r' ) )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>='\u0000' && LA39_0<='\t')||(LA39_0>='\u000B' && LA39_0<='\f')||(LA39_0>='\u000E' && LA39_0<='\uFFFF')) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // JavaL.g:261:12: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

            // JavaL.g:261:26: ( '\\r' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0=='\r') ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // JavaL.g:261:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    public void mTokens() throws RecognitionException {
        // JavaL.g:1:8: ( IntegerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | ENUM | ASSERT | Identifier | WS | COMMENT | LINE_COMMENT )
        int alt41=10;
        alt41 = dfa41.predict(input);
        switch (alt41) {
            case 1 :
                // JavaL.g:1:10: IntegerLiteral
                {
                mIntegerLiteral(); 

                }
                break;
            case 2 :
                // JavaL.g:1:25: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 3 :
                // JavaL.g:1:46: CharacterLiteral
                {
                mCharacterLiteral(); 

                }
                break;
            case 4 :
                // JavaL.g:1:63: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 5 :
                // JavaL.g:1:77: ENUM
                {
                mENUM(); 

                }
                break;
            case 6 :
                // JavaL.g:1:82: ASSERT
                {
                mASSERT(); 

                }
                break;
            case 7 :
                // JavaL.g:1:89: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 8 :
                // JavaL.g:1:100: WS
                {
                mWS(); 

                }
                break;
            case 9 :
                // JavaL.g:1:103: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 10 :
                // JavaL.g:1:111: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA27 dfa27 = new DFA27(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA41 dfa41 = new DFA41(this);
    static final String DFA27_eotS =
        "\12\uffff";
    static final String DFA27_eofS =
        "\12\uffff";
    static final String DFA27_minS =
        "\3\56\1\uffff\2\56\1\60\3\uffff";
    static final String DFA27_maxS =
        "\1\71\2\146\1\uffff\2\146\1\137\3\uffff";
    static final String DFA27_acceptS =
        "\3\uffff\1\2\3\uffff\1\1\1\3\1\4";
    static final String DFA27_specialS =
        "\12\uffff}>";
    static final String[] DFA27_transitionS = {
            "\1\3\1\uffff\1\1\11\2",
            "\1\7\1\uffff\1\4\11\5\12\uffff\1\11\1\10\1\11\30\uffff\1\6"+
            "\4\uffff\1\11\1\10\1\11",
            "\1\7\1\uffff\1\4\11\5\12\uffff\1\11\1\10\1\11\30\uffff\1\6"+
            "\4\uffff\1\11\1\10\1\11",
            "",
            "\1\7\1\uffff\1\4\11\5\12\uffff\1\11\1\10\1\11\30\uffff\1\6"+
            "\4\uffff\1\11\1\10\1\11",
            "\1\7\1\uffff\1\4\11\5\12\uffff\1\11\1\10\1\11\30\uffff\1\6"+
            "\4\uffff\1\11\1\10\1\11",
            "\1\4\11\5\45\uffff\1\6",
            "",
            "",
            ""
    };

    static final short[] DFA27_eot = DFA.unpackEncodedString(DFA27_eotS);
    static final short[] DFA27_eof = DFA.unpackEncodedString(DFA27_eofS);
    static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars(DFA27_minS);
    static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars(DFA27_maxS);
    static final short[] DFA27_accept = DFA.unpackEncodedString(DFA27_acceptS);
    static final short[] DFA27_special = DFA.unpackEncodedString(DFA27_specialS);
    static final short[][] DFA27_transition;

    static {
        int numStates = DFA27_transitionS.length;
        DFA27_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
        }
    }

    class DFA27 extends DFA {

        public DFA27(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 27;
            this.eot = DFA27_eot;
            this.eof = DFA27_eof;
            this.min = DFA27_min;
            this.max = DFA27_max;
            this.accept = DFA27_accept;
            this.special = DFA27_special;
            this.transition = DFA27_transition;
        }
        public String getDescription() {
            return "124:1: fragment DecimalFloatingPointLiteral : ( Digits '.' ( Digits )? ( ExponentPart )? ( FloatTypeSuffix )? | '.' Digits ( ExponentPart )? ( FloatTypeSuffix )? | Digits ExponentPart ( FloatTypeSuffix )? | Digits FloatTypeSuffix );";
        }
    }
    static final String DFA32_eotS =
        "\3\uffff\1\5\2\uffff\1\5\1\uffff\1\5";
    static final String DFA32_eofS =
        "\11\uffff";
    static final String DFA32_minS =
        "\1\60\1\130\2\56\2\uffff\1\56\2\60";
    static final String DFA32_maxS =
        "\1\60\1\170\2\146\2\uffff\3\146";
    static final String DFA32_acceptS =
        "\4\uffff\1\2\1\1\3\uffff";
    static final String DFA32_specialS =
        "\11\uffff}>";
    static final String[] DFA32_transitionS = {
            "\1\1",
            "\1\2\37\uffff\1\2",
            "\1\4\1\uffff\12\3\7\uffff\6\3\32\uffff\6\3",
            "\1\10\1\uffff\12\6\7\uffff\6\6\30\uffff\1\7\1\uffff\6\6",
            "",
            "",
            "\1\10\1\uffff\12\6\7\uffff\6\6\30\uffff\1\7\1\uffff\6\6",
            "\12\6\7\uffff\6\6\30\uffff\1\7\1\uffff\6\6",
            "\12\4\7\uffff\6\4\32\uffff\6\4"
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "160:1: fragment HexSignificand : ( HexNumeral ( '.' )? | '0' ( 'x' | 'X' ) ( HexDigits )? '.' HexDigits );";
        }
    }
    static final String DFA41_eotS =
        "\1\uffff\2\14\3\uffff\2\10\6\uffff\4\14\1\uffff\2\10\2\uffff\2\14"+
        "\1\uffff\3\14\1\uffff\2\14\2\10\1\14\1\uffff\2\14\1\uffff\1\51\1"+
        "\10\1\uffff\1\10\1\54\1\uffff";
    static final String DFA41_eofS =
        "\55\uffff";
    static final String DFA41_minS =
        "\1\11\2\56\3\uffff\1\156\1\163\2\uffff\1\52\1\56\1\uffff\1\60\4"+
        "\56\1\60\1\165\1\163\2\uffff\2\56\1\60\3\56\1\60\2\56\1\155\1\145"+
        "\1\56\1\60\2\56\1\60\1\44\1\162\1\uffff\1\164\1\44\1\uffff";
    static final String DFA41_maxS =
        "\1\ufaff\1\170\1\146\3\uffff\1\156\1\163\2\uffff\1\57\1\146\1\uffff"+
        "\1\137\4\146\1\137\1\165\1\163\2\uffff\1\160\1\146\1\137\3\146\1"+
        "\137\2\146\1\155\1\145\1\160\3\146\1\137\1\ufaff\1\162\1\uffff\1"+
        "\164\1\ufaff\1\uffff";
    static final String DFA41_acceptS =
        "\3\uffff\1\2\1\3\1\4\2\uffff\1\7\1\10\2\uffff\1\1\10\uffff\1\11"+
        "\1\12\22\uffff\1\5\2\uffff\1\6";
    static final String DFA41_specialS =
        "\55\uffff}>";
    static final String[] DFA41_transitionS = {
            "\2\11\1\uffff\2\11\22\uffff\1\11\1\uffff\1\5\1\uffff\1\10\2"+
            "\uffff\1\4\6\uffff\1\3\1\12\1\1\11\2\7\uffff\32\10\4\uffff\1"+
            "\10\1\uffff\1\7\3\10\1\6\25\10\105\uffff\27\10\1\uffff\37\10"+
            "\1\uffff\u1f08\10\u1040\uffff\u0150\10\u0170\uffff\u0080\10"+
            "\u0080\uffff\u092e\10\u10d2\uffff\u5200\10\u5900\uffff\u0200"+
            "\10",
            "\1\3\1\uffff\1\16\7\17\2\3\12\uffff\3\3\21\uffff\1\13\6\uffff"+
            "\1\15\4\uffff\3\3\21\uffff\1\13",
            "\1\3\1\uffff\1\20\11\21\12\uffff\3\3\30\uffff\1\22\4\uffff"+
            "\3\3",
            "",
            "",
            "",
            "\1\23",
            "\1\24",
            "",
            "",
            "\1\25\4\uffff\1\26",
            "\1\3\1\uffff\12\27\7\uffff\6\27\32\uffff\6\27",
            "",
            "\1\16\7\17\2\3\45\uffff\1\15",
            "\1\3\1\uffff\1\30\7\32\2\3\12\uffff\3\3\30\uffff\1\31\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\30\7\32\2\3\12\uffff\3\3\30\uffff\1\31\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\33\11\34\12\uffff\3\3\30\uffff\1\35\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\33\11\34\12\uffff\3\3\30\uffff\1\35\4\uffff"+
            "\3\3",
            "\1\36\11\37\45\uffff\1\22",
            "\1\40",
            "\1\41",
            "",
            "",
            "\1\3\1\uffff\12\42\7\uffff\6\42\11\uffff\1\3\16\uffff\1\43"+
            "\1\uffff\6\42\11\uffff\1\3",
            "\1\3\1\uffff\1\30\7\32\2\3\12\uffff\3\3\30\uffff\1\31\4\uffff"+
            "\3\3",
            "\1\30\7\32\2\3\45\uffff\1\31",
            "\1\3\1\uffff\1\30\7\32\2\3\12\uffff\3\3\30\uffff\1\31\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\33\11\34\12\uffff\3\3\30\uffff\1\35\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\33\11\34\12\uffff\3\3\30\uffff\1\35\4\uffff"+
            "\3\3",
            "\1\33\11\34\45\uffff\1\35",
            "\1\3\1\uffff\1\44\11\45\12\uffff\3\3\30\uffff\1\46\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\44\11\45\12\uffff\3\3\30\uffff\1\46\4\uffff"+
            "\3\3",
            "\1\47",
            "\1\50",
            "\1\3\1\uffff\12\42\7\uffff\6\42\11\uffff\1\3\16\uffff\1\43"+
            "\1\uffff\6\42\11\uffff\1\3",
            "\12\42\7\uffff\6\42\30\uffff\1\43\1\uffff\6\42",
            "\1\3\1\uffff\1\44\11\45\12\uffff\3\3\30\uffff\1\46\4\uffff"+
            "\3\3",
            "\1\3\1\uffff\1\44\11\45\12\uffff\3\3\30\uffff\1\46\4\uffff"+
            "\3\3",
            "\1\44\11\45\45\uffff\1\46",
            "\1\10\13\uffff\12\10\7\uffff\32\10\4\uffff\1\10\1\uffff\32"+
            "\10\105\uffff\27\10\1\uffff\37\10\1\uffff\u1f08\10\u1040\uffff"+
            "\u0150\10\u0170\uffff\u0080\10\u0080\uffff\u092e\10\u10d2\uffff"+
            "\u5200\10\u5900\uffff\u0200\10",
            "\1\52",
            "",
            "\1\53",
            "\1\10\13\uffff\12\10\7\uffff\32\10\4\uffff\1\10\1\uffff\32"+
            "\10\105\uffff\27\10\1\uffff\37\10\1\uffff\u1f08\10\u1040\uffff"+
            "\u0150\10\u0170\uffff\u0080\10\u0080\uffff\u092e\10\u10d2\uffff"+
            "\u5200\10\u5900\uffff\u0200\10",
            ""
    };

    static final short[] DFA41_eot = DFA.unpackEncodedString(DFA41_eotS);
    static final short[] DFA41_eof = DFA.unpackEncodedString(DFA41_eofS);
    static final char[] DFA41_min = DFA.unpackEncodedStringToUnsignedChars(DFA41_minS);
    static final char[] DFA41_max = DFA.unpackEncodedStringToUnsignedChars(DFA41_maxS);
    static final short[] DFA41_accept = DFA.unpackEncodedString(DFA41_acceptS);
    static final short[] DFA41_special = DFA.unpackEncodedString(DFA41_specialS);
    static final short[][] DFA41_transition;

    static {
        int numStates = DFA41_transitionS.length;
        DFA41_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA41_transition[i] = DFA.unpackEncodedString(DFA41_transitionS[i]);
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = DFA41_eot;
            this.eof = DFA41_eof;
            this.min = DFA41_min;
            this.max = DFA41_max;
            this.accept = DFA41_accept;
            this.special = DFA41_special;
            this.transition = DFA41_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( IntegerLiteral | FloatingPointLiteral | CharacterLiteral | StringLiteral | ENUM | ASSERT | Identifier | WS | COMMENT | LINE_COMMENT );";
        }
    }
 

}