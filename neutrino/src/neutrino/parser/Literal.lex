/*
 * run with: java -cp JFlex.jar JFlex.Main (this file)
 */
package neutrino.parser;
import static neutrino.parser.LiteralElementTypes.*;

%%

%unicode

%line
%column

%public
%class Scanner
%function nextToken
%type neutrino.parser.LiteralElement





%{
  StringBuffer string = new StringBuffer();


  private LiteralElement symbol(LiteralElementTypes type) {
    return new LiteralElement(type, yyline+1, yycolumn+1);
  }

  private LiteralElement symbol(LiteralElementTypes type, Object value) {
    return new LiteralElement(type, yyline+1, yycolumn+1, value);
  }

  /*
   * assumes correct representation of a long value for
   * specified radix in scanner buffer from <code>start</code>
   * to <code>end</code>
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }


    private LiteralElement lastToken;
    public LiteralElement nextTokenBuffered() throws java.io.IOException {
        if (lastToken != null) {
            final LiteralElement result = lastToken;
            lastToken = null;
            return result;
        }
        else {
            return nextToken();
        }
    }

    public void pushBack(final LiteralElement token) {
        if (lastToken != null)
            throw new IllegalStateException();
        else
            lastToken = token;
    }
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
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

%state STRING, CHARLITERAL

%%

<YYINITIAL> {

  /* boolean literals */
  "true"                         { return symbol(BOOLEAN_LITERAL, new Boolean(true)); }
  "false"                        { return symbol(BOOLEAN_LITERAL, new Boolean(false)); }

  /* separators */
  "("                            { return symbol(LPAREN); }
  ")"                            { return symbol(RPAREN); }
  "{"                            { return symbol(LBRACE); }
  "}"                            { return symbol(RBRACE); }
  "["                            { return symbol(LBRACK); }
  "]"                            { return symbol(RBRACK); }
  ":"                            { return symbol(COLON); }
  ";"                            { return symbol(SEMICOLON); }
  ","                            { return symbol(COMMA); }
  "."                            { return symbol(DOT); }
  "@"                            { return symbol(AT); }
  "#"                            { return symbol(POUND); }

  /* operators */
  ">"                            { return symbol(GT); }
  "<"                            { return symbol(LT); }


  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* character literal */
  \'                             { yybegin(CHARLITERAL); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to
     be represented by a positive integer. */
  "-2147483648"                  { return symbol(INTEGER_LITERAL, new Integer(Integer.MIN_VALUE)); }

  [+-]?{DecIntegerLiteral}       { return symbol(INTEGER_LITERAL, new Integer(yytext())); }
  [+-]?{DecLongLiteral}          { return symbol(LONG_LITERAL, new Long(yytext().subSequence(0,yylength()-1).toString())); }

  {HexIntegerLiteral}            { return symbol(INTEGER_LITERAL, new Integer((int) parseLong(2, yylength(), 16))); }
  {HexLongLiteral}               { return symbol(LONG_LITERAL, new Long(parseLong(2, yylength()-1, 16))); }

  {OctIntegerLiteral}            { return symbol(INTEGER_LITERAL, new Integer((int) parseLong(0, yylength(), 8))); }
  {OctLongLiteral}               { return symbol(LONG_LITERAL, new Long(parseLong(0, yylength()-1, 8))); }

  [+-]?{FloatLiteral}            { return symbol(FLOAT_LITERAL, new Float(yytext().subSequence(0,yylength()-1).toString())); }
  [+-]?{DoubleLiteral}           { return symbol(DOUBLE_LITERAL, new Double(yytext().toString())); }
  [+-]?{DoubleLiteral}[dD]       { return symbol(DOUBLE_LITERAL, new Double(yytext().subSequence(0,yylength()-1).toString())); }

  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return symbol(IDENTIFIER, yytext()); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol(STRING_LITERAL, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }

  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().subSequence(1, yylength()).toString(),8);
                        				   string.append( val ); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}

<CHARLITERAL> {
  {SingleCharacter}\'            { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character(yytext().charAt(0))); }

  /* escape sequences */
  "\\b"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\b'));}
  "\\t"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\t'));}
  "\\n"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\n'));}
  "\\f"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\f'));}
  "\\r"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\r'));}
  "\\\""\'                       { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\"'));}
  "\\'"\'                        { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\''));}
  "\\\\"\'                       { yybegin(YYINITIAL); return symbol(CHARACTER_LITERAL, new Character('\\')); }
  \\[0-3]?{OctDigit}?{OctDigit}\' { yybegin(YYINITIAL);
			                              int val = Integer.parseInt(yytext().substring(1,yylength()-1),8);
			                            return symbol(CHARACTER_LITERAL, new Character((char)val)); }

  /* error cases */
  \\.                            { throw new RuntimeException("Illegal escape sequence \""+yytext()+"\""); }
  {LineTerminator}               { throw new RuntimeException("Unterminated character literal at end of line"); }
  <<EOF>>                        { throw new RuntimeException("Unclosed character literal at end of file"); }
}

/* error fallback */
.|\n                             { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return symbol(EOF); }
