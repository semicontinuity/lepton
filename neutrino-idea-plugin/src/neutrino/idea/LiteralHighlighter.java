package neutrino.idea;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;
import neutrino.idea.parsing.LiteralElementTypes;
import neutrino.idea.parsing.LiteralHighlightingLexer;
import neutrino.idea.parsing.LiteralTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class LiteralHighlighter extends SyntaxHighlighterBase {
    private static final Map<IElementType, TextAttributesKey> keys1;
    private static final Map<IElementType, TextAttributesKey> keys2;

    @NotNull
    public Lexer getHighlightingLexer() {
        return new LiteralHighlightingLexer();
    }

    public static final TextAttributesKey TA__KEYWORD = TextAttributesKey.createTextAttributesKey(
        "LITERAL.KEYWORD",
        SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__NUMBER = TextAttributesKey.createTextAttributesKey(
        "LITERAL.NUMBER",
        SyntaxHighlighterColors.NUMBER.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__STRING = TextAttributesKey.createTextAttributesKey(
        "LITERAL.STRING",
        SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__VALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
        "LITERAL.VALID_STRING_ESCAPE",
        SyntaxHighlighterColors.VALID_STRING_ESCAPE.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__INVALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
        "LITERAL.INVALID_STRING_ESCAPE",
        SyntaxHighlighterColors.INVALID_STRING_ESCAPE.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__OPERATOR_SIGN = TextAttributesKey.createTextAttributesKey(
        "LITERAL.OPERATOR_SIGN",
        SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__PARENTHESES = TextAttributesKey.createTextAttributesKey(
        "LITERAL.PARENTHESES",
        SyntaxHighlighterColors.PARENTHS.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__BRACES = TextAttributesKey.createTextAttributesKey(
        "LITERAL.BRACES",
        SyntaxHighlighterColors.BRACES.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__BRACKETS = TextAttributesKey.createTextAttributesKey(
        "LITERAL.BRACKETS",
        SyntaxHighlighterColors.BRACKETS.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__COLON = TextAttributesKey.createTextAttributesKey(
        "LITERAL.COLON",
        SyntaxHighlighterColors.DOT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__DOT = TextAttributesKey.createTextAttributesKey(
        "LITERAL.DOT",
        SyntaxHighlighterColors.DOT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__POUND_SIGN = TextAttributesKey.createTextAttributesKey(
        "LITERAL.POUND_SIGN",
        SyntaxHighlighterColors.DOT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__TRIANGULAR_BRACES = TextAttributesKey.createTextAttributesKey(
        "LITERAL.TRIANGULAR_BRACES",
        SyntaxHighlighterColors.BRACES.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__END_OF_LINE_COMMENT = TextAttributesKey.createTextAttributesKey(
        "LITERAL.END_OF_LINE_COMMENT",
        SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(
        "LITERAL.BLOCK_COMMENT",
        SyntaxHighlighterColors.JAVA_BLOCK_COMMENT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__IDENTIFIER = TextAttributesKey.createTextAttributesKey(
        "LITERAL.IDENTIFIER",
        HighlighterColors.TEXT.getDefaultAttributes()
    );




    public static final TextAttributesKey TA__CLASS = TextAttributesKey.createTextAttributesKey(
        "LITERAL.CLASS",
        HighlighterColors.TEXT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__PROPERTY = TextAttributesKey.createTextAttributesKey(
        "LITERAL.PROPERTY",
        HighlighterColors.TEXT.getDefaultAttributes()
    );
    public static final TextAttributesKey TA__PARAMETER = TextAttributesKey.createTextAttributesKey(
        "LITERAL.PARAMETER",
        HighlighterColors.TEXT.getDefaultAttributes()
    );




    public static final TextAttributesKey TA__BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
        "LITERAL.BAD_CHARACTER",
        HighlighterColors.BAD_CHARACTER.getDefaultAttributes()
    );


    static {
        keys1 = new THashMap<IElementType, TextAttributesKey>();
        keys2 = new THashMap<IElementType, TextAttributesKey>();

        keys1.put(LiteralTokenTypes.BOOLEAN_LITERAL, TA__KEYWORD);
        keys1.put(LiteralTokenTypes.INTEGER_LITERAL, TA__NUMBER);
        keys1.put(LiteralTokenTypes.LONG_LITERAL, TA__NUMBER);
        keys1.put(LiteralTokenTypes.FLOAT_LITERAL, TA__NUMBER);
        keys1.put(LiteralTokenTypes.DOUBLE_LITERAL, TA__NUMBER);
        keys1.put(LiteralElementTypes.STRING_LITERAL, TA__STRING);
        keys1.put(LiteralElementTypes.CHAR_LITERAL, TA__STRING);
        keys1.put(StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN, TA__VALID_STRING_ESCAPE);
        keys1.put(StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, TA__INVALID_STRING_ESCAPE);
        keys1.put(StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN, TA__INVALID_STRING_ESCAPE);
        keys1.put(LiteralTokenTypes.PLUS, TA__OPERATOR_SIGN);
        keys1.put(LiteralTokenTypes.MINUS, TA__OPERATOR_SIGN);
        keys1.put(LiteralTokenTypes.LPAREN, TA__PARENTHESES);
        keys1.put(LiteralTokenTypes.RPAREN, TA__PARENTHESES);
        keys1.put(LiteralTokenTypes.LBRACE, TA__BRACES);
        keys1.put(LiteralTokenTypes.RBRACE, TA__BRACES);
        keys1.put(LiteralTokenTypes.LBRACK, TA__BRACKETS);
        keys1.put(LiteralTokenTypes.RBRACK, TA__BRACKETS);
        keys1.put(LiteralTokenTypes.COLON, TA__COLON);
        keys1.put(LiteralTokenTypes.DOT, TA__DOT);
        keys1.put(LiteralTokenTypes.POUND, TA__POUND_SIGN);
        keys1.put(LiteralTokenTypes.LT, TA__TRIANGULAR_BRACES);
        keys1.put(LiteralTokenTypes.GT, TA__TRIANGULAR_BRACES);
        keys1.put(LiteralTokenTypes.END_OF_LINE_COMMENT, TA__END_OF_LINE_COMMENT);
        keys1.put(LiteralTokenTypes.BLOCK_COMMENT, TA__BLOCK_COMMENT);
        keys1.put(LiteralTokenTypes.IDENTIFIER, TA__IDENTIFIER);
        keys1.put(TokenType.BAD_CHARACTER, TA__BAD_CHARACTER);


//        keys1.put(LiteralTokenTypes.QUOTE, TA__DEBUG);
//        keys1.put(LiteralTokenTypes.APOSTROPHE, TA__DEBUG);
//        keys1.put(LiteralTokenTypes.CHAR_DATA, TA__DEBUG);

    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(final IElementType tokenType) {
        return pack(keys1.get(tokenType));
    }
}