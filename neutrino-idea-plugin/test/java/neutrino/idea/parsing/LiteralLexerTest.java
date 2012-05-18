package neutrino.idea.parsing;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class LiteralLexerTest {

    // =================================================================================================================
    // IDENTIFIER
    // =================================================================================================================

    @Test
    public void test__IDENTIFIER__1() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();
        scanner.start("trues ");

        Assert.assertEquals("trues", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.IDENTIFIER, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }

    // =================================================================================================================
    // BOOLEAN_LITERAL
    // =================================================================================================================

    @Test
    public void test__BOOLEAN_LITERAL__1() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();
        scanner.start("true ");

        Assert.assertEquals("true", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.BOOLEAN_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }

    @Test
    public void test__BOOLEAN_LITERAL__2() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();
        scanner.start("false ");

        Assert.assertEquals("false", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.BOOLEAN_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }

    // =================================================================================================================
    // INTEGER_LITERAL and related
    // =================================================================================================================

    @Test
    public void test__INTEGER_LITERAL__normal() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();
        scanner.start("123 ");

        Assert.assertEquals("123", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.INTEGER_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }


    @Test
    public void test__INTEGER_LITERAL__very_long() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();

        scanner.start("11111111111111111111111111111111111111111111111 ");
        Assert.assertEquals("11111111111111111111111111111111111111111111111", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.INTEGER_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }


    @Test
    public void test__INTEGER_LITERAL__minus() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();

        scanner.start("-123 ");
        Assert.assertEquals("-", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.MINUS, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals("123", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.INTEGER_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }


    @Test
    public void test__INTEGER_LITERAL__plus() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();

        scanner.start("+123 ");
        Assert.assertEquals("+", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.PLUS, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals("123", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.INTEGER_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals(" ", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.WHITE_SPACE, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }


    // =================================================================================================================
    // COMPLEX
    // =================================================================================================================

    @Test
    public void test__1() throws IOException {
        final LiteralLexer scanner = new LiteralLexer();
        scanner.start("['a']");

        Assert.assertEquals("[", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.LBRACK, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals("'a'", scanner.getTokenSequence());
        Assert.assertEquals(LiteralElementTypes.CHAR_LITERAL, scanner.getTokenType());

        scanner.advance();
        Assert.assertEquals("]", scanner.getTokenSequence());
        Assert.assertEquals(LiteralTokenTypes.RBRACK, scanner.getTokenType());

        scanner.advance();
        Assert.assertNull(scanner.getTokenType());
    }

}
