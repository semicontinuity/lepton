package neutrino.idea.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class LiteralParser implements PsiParser {
    private static final com.intellij.openapi.diagnostic.Logger LOGGER = Logger.getInstance(LiteralParser.class);


    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("root = " + root);
        builder.setDebugMode(true);
        final PsiBuilder.Marker rootMarker = builder.mark();
        boolean done = false;
        while (!builder.eof()) {
            if (done) builder.error("Expected end of file");
            parseLiteral(builder);
            done = true;
        }
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }


    static void parseLiteral(final PsiBuilder builder) {
        final IElementType tokenType = builder.getTokenType();

        if (tokenType == null) {
            builder.error("Expected literal");
        }
        else if (tokenType == LiteralTokenTypes.MINUS) {
            final PsiBuilder.Marker marker = builder.mark();
            builder.advanceLexer(); // consume MINUS
            final IElementType literalType = builder.getTokenType();
            if (literalType == LiteralTokenTypes.INTEGER_LITERAL ||
                literalType == LiteralTokenTypes.LONG_LITERAL ||
                literalType == LiteralTokenTypes.FLOAT_LITERAL ||
                literalType == LiteralTokenTypes.DOUBLE_LITERAL) {
                builder.advanceLexer();
                done(marker, literalType);
            }
            else {
                builder.error("Unexpected '-'");
                marker.drop();
            }
        }
        else if (tokenType == LiteralElementTypes.CHAR_LITERAL ||
            tokenType == LiteralElementTypes.STRING_LITERAL ||
            tokenType == LiteralTokenTypes.INTEGER_LITERAL ||
            tokenType == LiteralTokenTypes.LONG_LITERAL ||
            tokenType == LiteralTokenTypes.BOOLEAN_LITERAL ||
            tokenType == LiteralTokenTypes.FLOAT_LITERAL ||
            tokenType == LiteralTokenTypes.DOUBLE_LITERAL) {

            builder.advanceLexer();
        }
        else {
            parseContextExpression(builder);
        }
    }


    public static void parseContextExpression(final PsiBuilder builder) {
        final IElementType openingTokenType = builder.getTokenType();
        if (openingTokenType == LiteralTokenTypes.LT) {
            final PsiBuilder.Marker marker = builder.mark();
            parseContext(builder);
            parseContextExpression(builder);
            done(marker, LiteralElementTypes.CONTEXT_EXPRESSION);
        }
        else {
            parseNonPrimitiveLiteral(builder);
        }
    }


    public static void parseNonPrimitiveLiteral(final PsiBuilder builder) {

        final IElementType tokenType = builder.getTokenType();
        if (tokenType == LiteralTokenTypes.LBRACK) {
            parseListOrMapLiteral(builder);
        }
        else if (tokenType == LiteralTokenTypes.LPAREN) {
            parseArrayOrBindingsLiteral(builder);
        }
        else if (tokenType == LiteralTokenTypes.POUND) {
            parseFunctionDeclaration(builder);
        }
        else if (tokenType == LiteralTokenTypes.IDENTIFIER) {
            final PsiBuilder.Marker marker = builder.mark();
            parseReference(builder);
            if (builder.getTokenType() == LiteralTokenTypes.LPAREN
                || builder.getTokenType() == LiteralTokenTypes.LBRACE) {

                if (builder.getTokenType() == LiteralTokenTypes.LPAREN) {
                    parseArrayOrBindingsLiteral(builder);
                }
                while (true) {
                    if (builder.getTokenType() == LiteralTokenTypes.LBRACE) {
                        System.out.println("> parse initializer");
                        parseArrayOrBindingsLiteral(builder, LiteralTokenTypes.LBRACE, LiteralTokenTypes.RBRACE);
                        System.out.println("< parse initializer");
                    }
                    else break;
                }
                done(marker, LiteralElementTypes.FUNCTION_CALL);
            }
            else {
                marker.drop();
            }
        }
        else {
            builder.error("Error in literal");
            builder.advanceLexer();
        }
    }


    static void parseContext(final PsiBuilder builder)  {
        assert builder.getTokenType() == LiteralTokenTypes.LT;

        final PsiBuilder.Marker marker = builder.mark();
        boolean isList = readSequence(builder, LiteralTokenTypes.LT, LiteralTokenTypes.GT);
        IElementType iElementType = isList ? LiteralElementTypes.LIST_CONTEXT : LiteralElementTypes.MAP_CONTEXT;
        done(marker, iElementType);
    }


    static void parseFunctionDeclaration(final PsiBuilder builder) {
        assert builder.getTokenType() == LiteralTokenTypes.POUND;
        final PsiBuilder.Marker functionDeclarationMarker = builder.mark();
        builder.advanceLexer();

        if (builder.getTokenType() != LiteralTokenTypes.LPAREN) {
            builder.error("Expected '('");
            return;
        }
        builder.advanceLexer();

        final PsiBuilder.Marker argumentsMarker = builder.mark();
        while (true) {
            if (builder.getTokenType() == LiteralTokenTypes.IDENTIFIER) {
                final PsiBuilder.Marker marker = builder.mark();
                builder.advanceLexer();
                done(marker, LiteralElementTypes.ARGUMENT);
            }
            else if (builder.getTokenType() == LiteralTokenTypes.RPAREN) {
                done(argumentsMarker, LiteralElementTypes.LIST_CONTEXT);
                builder.advanceLexer();
                break;
            }
            else {
                done(argumentsMarker, LiteralElementTypes.LIST_CONTEXT);
                builder.error("Expected parameter name or ')'");
                break;
            }
        }

        parseLiteral(builder);  // return value

        done(functionDeclarationMarker, LiteralElementTypes.FUNCTION_DECLARATION);
    }


    static void parseReference(final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();
        while (true) {
            final IElementType tokenType = builder.getTokenType();
            if (tokenType != LiteralTokenTypes.IDENTIFIER) break;
            builder.advanceLexer();

            final IElementType nextTokenType = builder.getTokenType();
            if (nextTokenType != LiteralTokenTypes.DOT) break;
            builder.advanceLexer();
        }
        done(marker, LiteralElementTypes.REFERENCE);
    }


    static void parseArrayOrBindingsLiteral(final PsiBuilder builder) {
        parseArrayOrBindingsLiteral(builder, LiteralTokenTypes.LPAREN, LiteralTokenTypes.RPAREN);
    }

    private static void parseArrayOrBindingsLiteral(
        final PsiBuilder builder, final IElementType openingToken, final IElementType closingToken) {
        assert builder.getTokenType() == openingToken;

        final PsiBuilder.Marker marker = builder.mark();
        boolean b = readSequence(builder, openingToken, closingToken);
        IElementType iElementType = b ? LiteralElementTypes.ARRAY : LiteralElementTypes.BINDINGS;
        done(marker, iElementType);
    }


    static void parseListOrMapLiteral(final PsiBuilder builder) {
        assert builder.getTokenType() == LiteralTokenTypes.LBRACK;

        final PsiBuilder.Marker marker = builder.mark();
        boolean b = readSequence(builder, LiteralTokenTypes.LBRACK, LiteralTokenTypes.RBRACK);
        IElementType iElementType = b ? LiteralElementTypes.LIST : LiteralElementTypes.MAP;
        done(marker, iElementType);
    }


    static boolean readSequence(
        final PsiBuilder builder,
        final IElementType openingToken,
        final IElementType closingToken) {

        boolean commaRequired = false;
        boolean firstElementParsed = false;
        System.out.println("> readSequence");
        assert builder.getTokenType() == openingToken;
        builder.advanceLexer();

        final PsiBuilder.Marker marker = builder.mark();

        /**
         * 0: expect value or terminator (just started parsing)
         * 1: expect ':' or comma or value (list element key of the map entry) or terminator --> 4,5
         * 2: expect value (key of the map entry) or terminator
         * 3: expect ':'
         * 4: expect value (value of the map entry)
         * 5: expect value (list element) or comma or terminator
         */
        int state = 0;
        PsiBuilder.Marker mapEntryMarker = builder.mark();
        PsiBuilder.Marker keyMarker = builder.mark();
        while (true) {
            switch (state) {
            case 0: // expect value or terminator (just started parsing)
                System.out.println("State 0");
                if (builder.getTokenType() == closingToken) {
                    builder.advanceLexer();
                    keyMarker.drop();
                    mapEntryMarker.drop();
                    marker.done(LiteralElementTypes.LIST_ELEMENTS);
                    System.out.println("| readSequence: return true");
                    return true;   // empty list
                }
                else if (builder.getTokenType() == null) {
                    keyMarker.drop();
                    mapEntryMarker.drop();
                    marker.done(LiteralElementTypes.LIST_ELEMENTS);
                    System.out.println("Expected " + closingToken);
                    builder.error("Expected " + closingToken);
                    System.out.println("| readSequence: return false");
                    return true;
                }
                else {
                    parseLiteral(builder);  // map key or list element
                    state = 1;  // expect ':' or ',' or value (list element key of the map entry) or terminator
                    break;
                }
            case 1: // expect ':' or ',' or value (list element key of the map entry) or terminator
                System.out.println("State 1");
                if (builder.getTokenType() == LiteralTokenTypes.COLON) {
                    done(keyMarker, LiteralElementTypes.PROPERTY);
                    builder.advanceLexer();
                    // map detected
                    state = 4;  // expect value (value of the map entry)
                    break;
                }
                else if (builder.getTokenType() == LiteralTokenTypes.COMMA) {
                    // comma => list detected, comma required
                    commaRequired = true;
                }
                // list detected.
                keyMarker.drop();
                mapEntryMarker.drop();
                state = 5;  // expect value (list element) or terminator
                break;
            case 2: // expect value (key of the map entry) or terminator
                System.out.println("State 2");
                if (builder.getTokenType() == closingToken) {
                    marker.done(LiteralElementTypes.MAP_ELEMENTS);
                    builder.advanceLexer();
                    System.out.println("| readSequence: return false");
                    return false;   // map
                }
                else if (builder.getTokenType() == null) {
                    marker.done(LiteralElementTypes.MAP_ELEMENTS);
                    System.out.println("Expected " + closingToken);
                    builder.error("Expected " + closingToken);
                    return false;
                }
                else {
                    if (!firstElementParsed) {
                        firstElementParsed = true;
                        if (builder.getTokenType() == LiteralTokenTypes.COMMA) {
                            builder.advanceLexer();
                            commaRequired = true;
                        }
                    }
                    else if (commaRequired) {
                        if (builder.getTokenType() == LiteralTokenTypes.COMMA) {
                            builder.advanceLexer(); // consume ','
                        }
                        else {
                            builder.error("Expected ','");
                        }
                    }
                    mapEntryMarker = builder.mark();
                    keyMarker = builder.mark();
                    parseLiteral(builder);
                    done(keyMarker, LiteralElementTypes.PROPERTY);
                    state = 3;  // expect ':'
                    break;
                }
            case 3: // expect ':'
                System.out.println("State 3");
                if (builder.getTokenType() == LiteralTokenTypes.COLON) {
                    builder.advanceLexer();
                    state = 4; // expect value (value of the map entry)
                    break;
                }
                else {
                    builder.error("Expected ':'");
                    keyMarker.drop();
                    mapEntryMarker.drop();
                    state = 2; // expect value (key of the map entry) or terminator
                    break;
                }
            case 4: // expect value (value of the map entry)
                System.out.println("State 4");
                parseLiteral(builder);
                done(mapEntryMarker, LiteralElementTypes.MAP_ENTRY);
                state = 2;  // expect value (key of the map entry) or terminator
                break;
            case 5: // expect value (list element) or terminator
                System.out.println("State 5");
                if (builder.getTokenType() == closingToken) {
                    marker.done(LiteralElementTypes.LIST_ELEMENTS);
                    builder.advanceLexer();
                    System.out.println("| readSequence: return true");
                    return true;    // list
                }
                else if (builder.getTokenType() == null) {
                    marker.done(LiteralElementTypes.MAP_ELEMENTS);
                    System.out.println("Expected " + closingToken);
                    builder.error("Expected " + closingToken);
                    return true;
                }
                else {
                    if (commaRequired) {
                        if (builder.getTokenType() == LiteralTokenTypes.COMMA) {
                            builder.advanceLexer(); // consume COMMA
                        }
                        else {
                            builder.error("Expected ','");
                        }
                    }
                    System.out.println("State 5 - parsing literal");
                    parseLiteral(builder);
                    break;  // continue in this state
                }
            }            
        }
    }


    static void done(final PsiBuilder.Marker marker, final IElementType token) {
        marker.done(token);
        System.out.println("done " + token);
    }
}