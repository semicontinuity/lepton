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
}