package neutrino.idea.parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%class _LiteralLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  {return;}
%eof}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////// User code //////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
BlockComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"


/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*
DecLongLiteral    = {DecIntegerLiteral} [lL]

HexIntegerLiteral = 0 [xX] 0* {HexDigit} {1,8}
HexLongLiteral    = 0 [xX] 0* {HexDigit} {1,16} [lL]
HexDigit          = [0-9a-fA-F]

OctIntegerLiteral = 0+ [1-3]? {OctDigit} {1,15}
OctLongLiteral    = 0+ 1? {OctDigit} {1,21} [lL]
OctDigit          = [0-7]

/* floating point literals */
FloatLiteral  = ({FLit1}|{FLit2}|{FLit3}) {Exponent}? [fF]
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]*
FLit2    = \. [0-9]+
FLit3    = [0-9]+
Exponent = [eE] [+-]? [0-9]+

/* string and character literals */
StringCharacter = [^\r\n\"\\]
SingleCharacter = [^\r\n\'\\]



CHARACTER_LITERAL="'"([^\\\'\r\n]|{ESCAPE_SEQUENCE})*("'"|\\)?
STRING_LITERAL=\"([^\\\"\r\n]|{ESCAPE_SEQUENCE})*(\"|\\)?
ESCAPE_SEQUENCE=\\[^\r\n]


%state IN_STRING, IN_CHAR

%%


// =====================================================================================================================
// State YYINITIAL
// =====================================================================================================================

<YYINITIAL> {CHARACTER_LITERAL} { return neutrino.idea.parsing.LiteralElementTypes.CHAR_LITERAL; }
<YYINITIAL> {STRING_LITERAL} { return neutrino.idea.parsing.LiteralElementTypes.STRING_LITERAL; }

<YYINITIAL> {

 /* boolean literals */
 "true"                         { return LiteralTokenTypes.BOOLEAN_LITERAL; }
 "false"                        { return LiteralTokenTypes.BOOLEAN_LITERAL; }

 /* separators */
 "("                            { return LiteralTokenTypes.LPAREN; }
 ")"                            { return LiteralTokenTypes.RPAREN; }
 "{"                            { return LiteralTokenTypes.LBRACE; }
 "}"                            { return LiteralTokenTypes.RBRACE; }
 "["                            { return LiteralTokenTypes.LBRACK; }
 "]"                            { return LiteralTokenTypes.RBRACK; }
 ":"                            { return LiteralTokenTypes.COLON; }
 ";"                            { return LiteralTokenTypes.SEMICOLON; }
 ","                            { return LiteralTokenTypes.COMMA; }
 "."                            { return LiteralTokenTypes.DOT; }
 "@"                            { return LiteralTokenTypes.AT; }
 "#"                            { return LiteralTokenTypes.POUND; }

 /* operators */
 ">"                            { return LiteralTokenTypes.GT; }
 "<"                            { return LiteralTokenTypes.LT; }


 /* numeric literals */
 \+                             { return LiteralTokenTypes.PLUS; }
 \-                             { return LiteralTokenTypes.MINUS; }

 {DecIntegerLiteral}            { return LiteralTokenTypes.INTEGER_LITERAL; }
 {DecLongLiteral}               { return LiteralTokenTypes.LONG_LITERAL; }

 {HexIntegerLiteral}            { return LiteralTokenTypes.INTEGER_LITERAL; }
 {HexLongLiteral}               { return LiteralTokenTypes.LONG_LITERAL; }

 {OctIntegerLiteral}            { return LiteralTokenTypes.INTEGER_LITERAL; }
 {OctLongLiteral}               { return LiteralTokenTypes.LONG_LITERAL; }

 {FloatLiteral}                 { return LiteralTokenTypes.FLOAT_LITERAL; }
 {DoubleLiteral}                { return LiteralTokenTypes.DOUBLE_LITERAL; }
 {DoubleLiteral}[dD]            { return LiteralTokenTypes.DOUBLE_LITERAL; }

 /* comments */
 {BlockComment}                 { return LiteralTokenTypes.BLOCK_COMMENT; }
 {DocumentationComment}         { return LiteralTokenTypes.BLOCK_COMMENT; }
 {EndOfLineComment}             { return LiteralTokenTypes.END_OF_LINE_COMMENT; }

 /* whitespace */
 {WhiteSpace}                   { return LiteralTokenTypes.WHITE_SPACE; }

 /* identifiers */
 {Identifier}                   { return LiteralTokenTypes.IDENTIFIER; }
}

// =====================================================================================================================
// BAD_CHARACTER
// =====================================================================================================================
.|\n                            { return com.intellij.psi.TokenType.BAD_CHARACTER; }
