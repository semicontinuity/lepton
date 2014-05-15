package neutrino.parser;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;


public class ScannerTest {
    @Test
    public void testNextToken__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("['a']"));
        Assert.assertEquals(LiteralElementTypes.LBRACK, scanner.nextToken().getType());

        final LiteralElement t2 = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.CHARACTER_LITERAL, t2.getType());
        Assert.assertEquals('a', t2.getValue());

        Assert.assertEquals(LiteralElementTypes.RBRACK, scanner.nextToken().getType());
        Assert.assertEquals(LiteralElementTypes.EOF, scanner.nextToken().getType());
    }


    @Test
    public void testNextToken__2() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("truez"));
        LiteralElement literalElement = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.IDENTIFIER, literalElement.getType());
        Assert.assertEquals("truez", literalElement.getValue());
        Assert.assertEquals(LiteralElementTypes.EOF, scanner.nextToken().getType());
    }


    @Test
    public void testNextToken__multi_line_strings() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("\"\"\"string\nno\n1\"\"\" \"\"\"string\nno\n2\"\"\""));

        final LiteralElement literalElement1 = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.STRING_LITERAL, literalElement1.getType());
        Assert.assertEquals("string\nno\n1", literalElement1.getValue());

        final LiteralElement literalElement2 = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.STRING_LITERAL, literalElement2.getType());
        Assert.assertEquals("string\nno\n2", literalElement2.getValue());

        Assert.assertEquals(LiteralElementTypes.EOF, scanner.nextToken().getType());
    }

    @Test
    public void testNextToken__strings_literals() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("\"\"\"string\nno\n1\"\"\" \"string no 2\""));

        final LiteralElement literalElement1 = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.STRING_LITERAL, literalElement1.getType());
        Assert.assertEquals("string\nno\n1", literalElement1.getValue());

        final LiteralElement literalElement2 = scanner.nextToken();
        Assert.assertEquals(LiteralElementTypes.STRING_LITERAL, literalElement2.getType());
        Assert.assertEquals("string no 2", literalElement2.getValue());

        Assert.assertEquals(LiteralElementTypes.EOF, scanner.nextToken().getType());
    }
}
