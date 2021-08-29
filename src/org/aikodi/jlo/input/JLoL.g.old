lexer grammar JLoL;
@header {
  package be.kuleuven.cs.distrinet.jlo.input;
}
@members {
  public final static int JAVADOC_CHANNEL=1;
  protected boolean enumIsKeyword = true;
  protected boolean assertIsKeyword = true;
}


IntegerLiteral
: DecimalIntegerLiteral
| HexIntegerLiteral
| OctalIntegerLiteral
| BinaryIntegerLiteral
;

fragment
DecimalIntegerLiteral
: DecimalNumeral IntegerTypeSuffix?
;


fragment
DecimalNumeral
: '0'
| NonZeroDigit (Digits? | ('_')+ Digits)
;

fragment
Digits
: Digit ((Digit | '_')* Digit)?
;

fragment
Digit
: '0'
| NonZeroDigit
;

fragment
NonZeroDigit
: ('1'..'9')
;



fragment
OctalIntegerLiteral
: OctalNumeral IntegerTypeSuffix?
;

fragment
OctalNumeral
: '0' '_'* OctalDigits
;

fragment
OctalDigits
: OctalDigit ((OctalDigit | '_')* OctalDigit)?
;

fragment
OctalDigit
: ('0'..'7')
;




fragment
HexIntegerLiteral
: HexNumeral IntegerTypeSuffix?
;

fragment
HexNumeral
: '0' ('x' | 'X') HexDigits
;

fragment
HexDigits
: HexDigit ((HexDigit | '_') * HexDigit)?
;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;



fragment
BinaryIntegerLiteral
: BinaryNumeral IntegerTypeSuffix?
;


fragment
BinaryNumeral
: '0' ('b' | 'B') BinaryDigits
;

fragment
BinaryDigits
: BinaryDigit ((BinaryDigit | '_')* BinaryDigit)?
;

fragment
BinaryDigit
: ('0'|'1')
;


fragment
IntegerTypeSuffix : ('l'|'L') ;

//Floating point

FloatingPointLiteral
: DecimalFloatingPointLiteral
| HexadecimalFloatingPointLiteral
;

fragment
DecimalFloatingPointLiteral
: Digits '.' Digits? ExponentPart? FloatTypeSuffix?
| '.' Digits ExponentPart? FloatTypeSuffix?
| Digits ExponentPart FloatTypeSuffix?
| Digits FloatTypeSuffix
;

fragment
ExponentPart
: ExponentIndicator SignedInteger
;

fragment
ExponentIndicator
: ('e'|'E')
;

fragment
SignedInteger
: Sign? Digits
;

fragment
Sign
: ('+'|'-')
;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

fragment
HexadecimalFloatingPointLiteral
: HexSignificand BinaryExponent FloatTypeSuffix?
;

fragment
HexSignificand
: HexNumeral '.'?
| '0' ('x'|'X') HexDigits? '.' HexDigits
;

fragment
BinaryExponent
: BinaryExponentIndicator SignedInteger
;

fragment
BinaryExponentIndicator
: ('p'|'P')
;

CharacterLiteral
    :   '\'' ( EscapeSequence | ~('\''|'\\') ) '\''
    ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

ENUM:   'enum' {if (!enumIsKeyword) $type=Identifier;}
    ;
    
ASSERT
    :   'assert' {if (!assertIsKeyword) $type=Identifier;}
    ;

Export
    	:	'export'
    	;

Connector
    	:	'connector'
    	;

Connect
	:	'connect'
	;    	

Refines
  	:	'refines'
  	;

Overrides
    	:	'overrides'
    	;

    
Identifier 
    :   Letter (Letter|JavaIDDigit)*
    ;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    :  '\u0024' |
       '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
    ;

COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;
