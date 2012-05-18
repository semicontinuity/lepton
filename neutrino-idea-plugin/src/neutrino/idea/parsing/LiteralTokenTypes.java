package neutrino.idea.parsing;

import com.intellij.lang.Language;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.psi.tree.TokenSet;
import neutrino.idea.LiteralLanguage;

public interface LiteralTokenTypes {
    LiteralLanguage LANG = Language.findInstance(LiteralLanguage.class);
    IFileElementType FILE = new IStubFileElementType(LANG);


    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
//    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    IElementType BOOLEAN_LITERAL = new LiteralElementType("BOOLEAN_LITERAL");
    IElementType IDENTIFIER = new LiteralElementType("IDENTIFIER");

    IElementType LPAREN = new LiteralElementType("LPAREN");
    IElementType RPAREN = new LiteralElementType("RPAREN");
    IElementType LBRACE = new LiteralElementType("LBRACE");
    IElementType RBRACE = new LiteralElementType("RBRACE");
    IElementType LBRACK = new LiteralElementType("LBRACK");
    IElementType RBRACK = new LiteralElementType("RBRACK");
    IElementType SEMICOLON = new LiteralElementType("SEMICOLON");
    IElementType COMMA = new LiteralElementType("COMMA");
    IElementType COLON = new LiteralElementType("COLON");
    IElementType DOT = new LiteralElementType("DOT");
    IElementType AT = new LiteralElementType("AT");
    IElementType GT = new LiteralElementType("GT");
    IElementType LT = new LiteralElementType("LT");
    IElementType POUND = new LiteralElementType("POUND");
    IElementType PLUS = new LiteralElementType("PLUS");
    IElementType MINUS = new LiteralElementType("MINUS");


    IElementType APOSTROPHE = new LiteralElementType("APOSTROPHE");
    IElementType QUOTE = new LiteralElementType("QUOTE");
//    IElementType VALID_ESCAPED_CHAR = new LiteralElementType("VALID_ESCAPED_CHAR");
//    IElementType INVALID_ESCAPED_CHAR = new LiteralElementType("VALID_ESCAPED_CHAR");
    IElementType CHAR_DATA = new LiteralElementType("CHAR_DATA");

    IElementType FLOAT_LITERAL = new LiteralElementType("FLOAT_LITERAL");
    IElementType DOUBLE_LITERAL = new LiteralElementType("DOUBLE_LITERAL");
    IElementType INTEGER_LITERAL = new LiteralElementType("INTEGER_LITERAL");
    IElementType LONG_LITERAL = new LiteralElementType("LONG_LITERAL");

    IElementType END_OF_LINE_COMMENT = new LiteralElementType("END_OF_LINE_COMMENT");
    IElementType BLOCK_COMMENT = new LiteralElementType("BLOCK_COMMENT");



    TokenSet NUMBERS = TokenSet.create(
        FLOAT_LITERAL, DOUBLE_LITERAL, INTEGER_LITERAL, LONG_LITERAL
    );

    TokenSet COMMENTS = TokenSet.create(
        END_OF_LINE_COMMENT, BLOCK_COMMENT
    );

    TokenSet WHITESPACES = TokenSet.create(
        WHITE_SPACE
    );
}