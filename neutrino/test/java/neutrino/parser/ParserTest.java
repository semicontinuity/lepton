package neutrino.parser;

import neutrino.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;


public class ParserTest {

    // =================================================================================================================
    // Boolean
    // =================================================================================================================

    @Test
    public void testReadLiteral__boolean__true() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("true"));
        final Parser parser = new Parser();

        final BooleanLiteral value = (BooleanLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(true, value.getValue());
    }


    @Test
    public void testReadLiteral__boolean__false() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("false"));
        final Parser parser = new Parser();

        final BooleanLiteral value = (BooleanLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(false, value.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__boolean__extra_tokens() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("false false"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);        
    }


    // =================================================================================================================
    // Character
    // =================================================================================================================

    @Test
    public void testReadLiteral__character__simple() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'a'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('a', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashB() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\b'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\b', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashT() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\t'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\t', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashN() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\n'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\n', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashF() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\f'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\f', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashR() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\r'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\r', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashQuot() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\"'"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('"', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashAmp() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\''"));
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\'', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__backslashBackslash() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\\\'"));   // '\\'
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\\', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__octal__3() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\377'"));   // '\377'
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\377', value.getValue().charValue());
    }


    @Test
    public void testReadLiteral__character__octal__2() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\37'"));   // '\37'
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\37', value.getValue().charValue());
    }

    @Test
    public void testReadLiteral__character__octal__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\3'"));   // '\3'
        final Parser parser = new Parser();

        final CharacterLiteral value = (CharacterLiteral) parser.readLiteral(scanner);
        Assert.assertEquals('\3', value.getValue().charValue());
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__octal_invalid_1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\477'"));   // '\477'
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__octal_invalid_2() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\387'"));   // '\387'
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__octal_invalid_3() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\38'"));   // '\38'
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__octal_invalid_4() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\9'"));   // '\9'
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__octal_invalid_5() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\0000'"));   // '\0000'
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__backslashInvalid() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\z"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__backslash() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\\"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__unterminated() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'\n'"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__unclosed() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("'"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__character__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("''"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }

    // =================================================================================================================
    // String
    // =================================================================================================================

    @Test
    public void testReadLiteral__string__simple() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("\"Hello, world\""));  // "Hello, world"
        final Parser parser = new Parser();

        final StringLiteral value = (StringLiteral) parser.readLiteral(scanner);
        Assert.assertEquals("Hello, world", value.getValue());
    }

    // TODO: more tests for string

    // =================================================================================================================
    // Float
    // =================================================================================================================

    @Test
    public void testReadLiteral__float__simple__positive() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2.0f"));
        final Parser parser = new Parser();

        final FloatLiteral value = (FloatLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2.0f, value.getValue(), 0);
    }


    @Test
    public void testReadLiteral__float__simple__negative() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2.0f"));
        final Parser parser = new Parser();

        final FloatLiteral value = (FloatLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2.0f, value.getValue(), 0);
    }

    // TODO: more tests for float

    // =================================================================================================================
    // Double
    // =================================================================================================================

    @Test
    public void testReadLiteral__double__simple__positive() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2.0"));
        final Parser parser = new Parser();

        final DoubleLiteral value = (DoubleLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2.0d, value.getValue(), 0);
    }

    @Test
    public void testReadLiteral__double__simple__positive__explicit() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2.0d"));
        final Parser parser = new Parser();

        final DoubleLiteral value = (DoubleLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2.0d, value.getValue(), 0);
    }


    @Test
    public void testReadLiteral__double__simple__negative() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2.0"));
        final Parser parser = new Parser();

        final DoubleLiteral value = (DoubleLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2.0d, value.getValue(), 0);
    }


    @Test
    public void testReadLiteral__double__simple__negative__explicit() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2.0d"));
        final Parser parser = new Parser();

        final DoubleLiteral value = (DoubleLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2.0d, value.getValue(), 0);
    }


    // TODO: support
    public void testReadLiteral__double__complex() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("0x1.fffffffffffffP+1023"));
        final Parser parser = new Parser();

        final DoubleLiteral value = (DoubleLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(0x1.fffffffffffffP+1023, value.getValue(), 0);
    }

    // TODO: more tests for double

    // =================================================================================================================
    // Integer
    // =================================================================================================================

    @Test
    public void testReadLiteral__integer__simple__positive() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2, value.getValue().intValue());
    }


    @Test
    public void testReadLiteral__integer__simple__negative() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2, value.getValue().intValue());
    }


    @Test
    public void testReadLiteral__integer__MIN_VALUE() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2147483648"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(Integer.MIN_VALUE, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__integer__MAX_VALUE() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2147483647"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(Integer.MAX_VALUE, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__integer__hex__MIN_VALUE() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("0x80000000"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(Integer.MIN_VALUE, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__integer__hex__MAX_VALUE() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("0x7fffffff"));
        final Parser parser = new Parser();

        final IntegerLiteral value = (IntegerLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(Integer.MAX_VALUE, value.getValue().intValue());
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__integer__positive_out_of_int_range() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2147483648"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    @Test(expected = RuntimeException.class)
    public void testReadLiteral__integer__negative_out_of_int_range() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2147483649"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }

    // TODO: more tests for int

    // =================================================================================================================
    // Long
    // =================================================================================================================

    @Test
    public void testReadLiteral__long__simple__positive() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2L"));
        final Parser parser = new Parser();

        final LongLiteral value = (LongLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__long__simple__negative() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2L"));
        final Parser parser = new Parser();

        final LongLiteral value = (LongLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__long__negative_out_of_int_range() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("-2147483649L"));
        final Parser parser = new Parser();

        final LongLiteral value = (LongLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(-2147483649L, value.getValue().longValue());
    }


    @Test
    public void testReadLiteral__long__positive_out_of_int_range() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("2147483648L"));
        final Parser parser = new Parser();

        final LongLiteral value = (LongLiteral) parser.readLiteral(scanner);
        Assert.assertEquals(2147483648L, value.getValue().longValue());
    }


    // TODO: more tests for long

    // =================================================================================================================
    // List
    // =================================================================================================================

    @Test
    public void testReadLiteral__list__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("[]"));
        final Parser parser = new Parser();

        final ListLiteral parsed = (ListLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());
    }


    @Test
    public void testReadLiteral__list__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("[1 2L true  \"Hello\" 'a' 1.0f 2.0d]"));
        final Parser parser = new Parser();

        final ListLiteral parsed = (ListLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(7, elements.size());

        Assert.assertEquals(Integer.valueOf(1), ((IntegerLiteral) elements.get(0)).getValue());

        Assert.assertEquals(Long.valueOf(2), ((LongLiteral) elements.get(1)).getValue());

        Assert.assertEquals(true, ((BooleanLiteral) elements.get(2)).getValue());

        Assert.assertEquals("Hello", ((StringLiteral) elements.get(3)).getValue());

        Assert.assertEquals('a', ((CharacterLiteral) elements.get(4)).getValue().charValue());

        Assert.assertEquals(1.0f, ((FloatLiteral) elements.get(5)).getValue(), 0);

        Assert.assertEquals(2.0d, ((DoubleLiteral) elements.get(6)).getValue(), 0);
    }


    @Test
    public void testReadLiteral__list__nested() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("[1 [2]]"));
        final Parser parser = new Parser();

        final ListLiteral parsed = (ListLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final IntegerLiteral element0 = (IntegerLiteral) elements.get(0);
        Assert.assertEquals(Integer.valueOf(1), element0.getValue());

        final ListLiteral element1 = (ListLiteral) elements.get(1);
        Assert.assertNotNull(element1);
        final List<Evaluable> nested = element1.getElements();
        Assert.assertNotNull(nested);
        Assert.assertEquals(1, nested.size());
        final IntegerLiteral nested_element0 = (IntegerLiteral) nested.get(0);
        Assert.assertEquals(Integer.valueOf(2), nested_element0.getValue());
    }

    // TODO: more tests for list
    // =================================================================================================================
    // Array
    // =================================================================================================================

    @Test
    public void testReadLiteral__array__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("()"));
        final Parser parser = new Parser();

        final ArrayLiteral<?> parsed = (ArrayLiteral<?>) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends Evaluable<?>> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        Assert.assertNull(parsed.getContext());
    }


    @Test
    public void testReadLiteral__array__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("(1 2L true  \"Hello\" 'a' 1.0f 2.0d)"));
        final Parser parser = new Parser();

        final ArrayLiteral parsed = (ArrayLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(7, elements.size());

        Assert.assertEquals(Integer.valueOf(1), ((IntegerLiteral) elements.get(0)).getValue());

        Assert.assertEquals(Long.valueOf(2), ((LongLiteral) elements.get(1)).getValue());

        Assert.assertEquals(true, ((BooleanLiteral) elements.get(2)).getValue());

        Assert.assertEquals("Hello", ((StringLiteral) elements.get(3)).getValue());

        Assert.assertEquals('a', ((CharacterLiteral) elements.get(4)).getValue().charValue());

        Assert.assertEquals(1.0f, ((FloatLiteral) elements.get(5)).getValue(), 0);

        Assert.assertEquals(2.0d, ((DoubleLiteral) elements.get(6)).getValue(), 0);
    }


    @Test
    public void testReadLiteral__array__nested() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("(1 (2))"));
        final Parser parser = new Parser();

        final ArrayLiteral parsed = (ArrayLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final IntegerLiteral element0 = (IntegerLiteral) elements.get(0);
        Assert.assertEquals(Integer.valueOf(1), element0.getValue());

        final ArrayLiteral element1 = (ArrayLiteral) elements.get(1);
        Assert.assertNotNull(element1);
        final List<Evaluable> nested = element1.getElements();
        Assert.assertNotNull(nested);
        Assert.assertEquals(1, nested.size());
        final IntegerLiteral nested_element0 = (IntegerLiteral) nested.get(0);
        Assert.assertEquals(Integer.valueOf(2), nested_element0.getValue());
    }


    @Test
    public void testReadLiteral__array__can_have_context() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<object: java.lang.Object> (1 2)"));
        final Parser parser = new Parser();

        final ArrayLiteral parsed = (ArrayLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final IntegerLiteral element0 = (IntegerLiteral) elements.get(0);
        Assert.assertEquals(Integer.valueOf(1), element0.getValue());
        final IntegerLiteral element1 = (IntegerLiteral) elements.get(1);
        Assert.assertEquals(Integer.valueOf(2), element1.getValue());

        Assert.assertNotNull(parsed.getContext());
    }

    // TODO: more tests for array

    // =================================================================================================================
    // Map
    // =================================================================================================================

/*
    @Test
    public void testReadLiteral__map__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("{}"));
        final Parser parser = new Parser();

        final MapLiteral parsed = (MapLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<ChildInitializerLiteral> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());
    }

    @Test
    public void testReadLiteral__map__empty__map_context() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<x:1 y:2> {}"));
        final Parser parser = new Parser();

        final MapLiteral parsed = (MapLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<ChildInitializerLiteral> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        final MapContextLiteral context = (MapContextLiteral) parsed.getContext();
        Assert.assertNull(context.getContext());
        Assert.assertEquals(2, context.getElements().size());

        final Map.Entry entry0 = context.getElements().get(0);
        Assert.assertEquals("x", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = context.getElements().get(1);
        Assert.assertEquals("y", ((ReferenceLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());
    }


    @Test
    public void testReadLiteral__map__empty__two_contexts() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<x:1> <y:2> {}"));
        final Parser parser = new Parser();

        final MapLiteral parsed = (MapLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<ChildInitializerLiteral> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        final MapContextLiteral context = (MapContextLiteral) parsed.getContext();
        final MapContextLiteral context2 = (MapContextLiteral) context.getContext();
        Assert.assertNotNull(context2);
        Assert.assertNull(context2.getContext());
        Assert.assertEquals(1, context.getElements().size());
        Assert.assertEquals(1, context2.getElements().size());

        final Map.Entry entry0 = context2.getElements().get(0);
        Assert.assertEquals("x", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = context.getElements().get(0);
        Assert.assertEquals("y", ((ReferenceLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());
    }
*/


    @Test
    public void testReadLiteral__map__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("[\"a\":1 \"b\":2]"));
        final Parser parser = new Parser();

        final MapLiteral parsed = (MapLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends MapEntry> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final Map.Entry entry0 = elements.get(0);
        Assert.assertEquals("a", ((StringLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = elements.get(1);
        Assert.assertEquals("b", ((StringLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());
    }

    // =================================================================================================================
    // Bindings
    // =================================================================================================================

    @Test
    public void testReadLiteral__bindings__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("(a:1 b:2)"));
        final Parser parser = new Parser();

        final BindingsLiteral parsed = (BindingsLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends MapEntry> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final Map.Entry entry0 = elements.get(0);
        Assert.assertEquals("a", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = elements.get(1);
        Assert.assertEquals("b", ((ReferenceLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());
    }

    // =================================================================================================================
    // Reference
    // =================================================================================================================

    @Test
    public void testReadLiteral__reference__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("ref"));
        final Parser parser = new Parser();

        final ReferenceLiteral referenceLiteral = (ReferenceLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(referenceLiteral);
        Assert.assertEquals("ref", referenceLiteral.getValue());
    }

    @Test
    public void testReadLiteral__reference__context() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<x:1> ref"));
        final Parser parser = new Parser();

        final ReferenceLiteral referenceLiteral = (ReferenceLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(referenceLiteral);
        Assert.assertEquals("ref", referenceLiteral.getValue());

        final MapBindingsLiteral context = (MapBindingsLiteral) referenceLiteral.getContext();
        Assert.assertNotNull(context);
        Assert.assertEquals(1, context.getElements().size());
        final Map.Entry entry0 = context.getElements().get(0);
        Assert.assertEquals("x", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());
    }
    

    // =================================================================================================================
    // Function call
    // =================================================================================================================

    @Test
    public void testReadLiteral__functionCall__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<x:1> factory {y:2} {3 4}"));
        final Parser parser = new Parser();

        final FunctionCallLiteral callLiteral = (FunctionCallLiteral) parser.parseLiteral(scanner);
        Assert.assertNotNull(callLiteral);
        Assert.assertEquals("factory", callLiteral.getReference().getValue());

        final MapBindingsLiteral context = (MapBindingsLiteral) callLiteral.getContext();
        Assert.assertNotNull(context);
        Assert.assertEquals(1, context.getElements().size());
        final Map.Entry entry0 = context.getElements().get(0);
        Assert.assertEquals("x", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final List<CompositeLiteral> initializers = callLiteral.getInitializers();
        Assert.assertEquals(2, initializers.size());
    }

    @Test
    public void testReadLiteral__function_call__list_1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("f(1 2)"));
        final Parser parser = new Parser();

        final FunctionCallLiteral value = (FunctionCallLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        ArrayLiteral arguments = (ArrayLiteral) value.getArguments();
        List elements = arguments.getElements();

        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());
        Assert.assertTrue(elements.get(0) instanceof IntegerLiteral);
        Assert.assertEquals(Integer.valueOf(1), ((IntegerLiteral)elements.get(0)).getValue());
        Assert.assertTrue(elements.get(1) instanceof IntegerLiteral);
        Assert.assertEquals(Integer.valueOf(2), ((IntegerLiteral)elements.get(1)).getValue());
    }


    @Test
    public void testReadLiteral__function_call__map_1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("f(a:1 b:2)"));
        final Parser parser = new Parser();

        final FunctionCallLiteral value = (FunctionCallLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        MapBindingsLiteral arguments = (MapBindingsLiteral) value.getArguments();
        List elements = arguments.getElements();

        Assert.assertEquals(2, elements.size());
        MapEntry<ReferenceLiteral> o1 = (MapEntry<ReferenceLiteral>) elements.get(0);
        Assert.assertEquals("a", o1.getKey().getValue());
        Assert.assertEquals(Integer.valueOf(1), ((IntegerLiteral)o1.getValue()).getValue());

        MapEntry<ReferenceLiteral> o2 = (MapEntry<ReferenceLiteral>) elements.get(1);
        Assert.assertEquals("b", o2.getKey().getValue());
        Assert.assertEquals(Integer.valueOf(2), ((IntegerLiteral)o2.getValue()).getValue());
    }


    // =================================================================================================================
    // Function declaration
    // =================================================================================================================

    @Test
    public void testReadLiteral__functionDeclaration__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("#() true"));
        final Parser parser = new Parser();

        final FunctionDeclarationLiteral value = (FunctionDeclarationLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.argumentOrder);
        Assert.assertEquals(0, value.argumentOrder.size());
        Assert.assertTrue(value.returnValue instanceof BooleanLiteral);
        Assert.assertTrue(((BooleanLiteral) value.returnValue).getValue());
    }

    @Test
    public void testReadLiteral__functionDeclaration__2() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("#(a b) true"));
        final Parser parser = new Parser();

        final FunctionDeclarationLiteral value = (FunctionDeclarationLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.argumentOrder);
        Assert.assertEquals(2, value.argumentOrder.size());
        Assert.assertEquals(Integer.valueOf(0), value.argumentOrder.get("a"));
        Assert.assertEquals(Integer.valueOf(1), value.argumentOrder.get("b"));
        Assert.assertTrue(value.returnValue instanceof BooleanLiteral);
        Assert.assertTrue(((BooleanLiteral) value.returnValue).getValue());
    }

    @Test(expected = RuntimeException.class)
    public void testReadLiteral__function__cannot_have_context() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("<a:1> #() true"));
        final Parser parser = new Parser();

        parser.readLiteral(scanner);
    }


    // =================================================================================================================
    // Annotations
    // =================================================================================================================

    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_char() throws IOException {
        // not possible to annotate character literal
        final Scanner scanner = new Scanner(new StringReader("@(x:1) 'z'"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_string() throws IOException {
        // not possible to annotate string literal (perhaps, allow for "injected language literals"?)
        final Scanner scanner = new Scanner(new StringReader("@(x:1) \"z\""));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_integer() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1) 1"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_long() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1) 1L"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_boolean() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1) true"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_float() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1) 1f"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testParseLiteral__annotated_double() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1) 1d"));
        final Parser parser = new Parser();
        parser.parseLiteral(scanner);
    }


    @Test
    public void testReadLiteral__annotated_list__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a' 'b')) []"));
        final Parser parser = new Parser();

        final ListLiteral<?> parsed = (ListLiteral<?>) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        List<? extends Evaluable<?>> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        List<? extends MapEntry<ReferenceLiteral>> annotations = ((BindingsLiteral) parsed.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_array__empty() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a' 'b')) ()"));
        final Parser parser = new Parser();

        final ArrayLiteral<?> parsed = (ArrayLiteral<?>) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends Evaluable<?>> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(0, elements.size());

        Assert.assertNull(parsed.getContext());

        List<? extends MapEntry<ReferenceLiteral>> annotations = ((BindingsLiteral) parsed.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_map__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a''b')) [\"a\":1 \"b\":2]"));
        final Parser parser = new Parser();

        final MapLiteral parsed = (MapLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends MapEntry> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final Map.Entry entry0 = elements.get(0);
        Assert.assertEquals("a", ((StringLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = elements.get(1);
        Assert.assertEquals("b", ((StringLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());


        List<? extends MapEntry<ReferenceLiteral>> annotations = ((BindingsLiteral) parsed.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_bindings__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a''b')) (a:1 b:2)"));
        final Parser parser = new Parser();

        final BindingsLiteral parsed = (BindingsLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<? extends MapEntry> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final Map.Entry entry0 = elements.get(0);
        Assert.assertEquals("a", ((ReferenceLiteral) entry0.getKey()).getValue());
        Assert.assertEquals(1, ((IntegerLiteral) entry0.getValue()).getValue().intValue());

        final Map.Entry entry1 = elements.get(1);
        Assert.assertEquals("b", ((ReferenceLiteral) entry1.getKey()).getValue());
        Assert.assertEquals(2, ((IntegerLiteral) entry1.getValue()).getValue().intValue());

        final List<? extends MapEntry<ReferenceLiteral>> annotations =
            ((BindingsLiteral) parsed.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_reference__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a' 'b')) ref"));
        final Parser parser = new Parser();

        final ReferenceLiteral<?> referenceLiteral = (ReferenceLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(referenceLiteral);
        Assert.assertEquals("ref", referenceLiteral.getValue());

        final List<? extends MapEntry<ReferenceLiteral>> annotations =
            ((BindingsLiteral) referenceLiteral.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_functionDeclaration__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a' 'b')) #() true"));
        final Parser parser = new Parser();

        final FunctionDeclarationLiteral value = (FunctionDeclarationLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value.argumentOrder);
        Assert.assertEquals(0, value.argumentOrder.size());
        Assert.assertTrue(value.returnValue instanceof BooleanLiteral);
        Assert.assertTrue(((BooleanLiteral) value.returnValue).getValue());


        final List<? extends MapEntry<ReferenceLiteral>> annotations =
            ((BindingsLiteral) value.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }


    @Test
    public void testReadLiteral__annotated_function_call__list_1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("@(x:1, y:('a' 'b')) f(1 2)"));
        final Parser parser = new Parser();

        final FunctionCallLiteral<?> value = (FunctionCallLiteral<?>) parser.readLiteral(scanner);
        Assert.assertNotNull(value);
        ArrayLiteral arguments = (ArrayLiteral) value.getArguments();
        List elements = arguments.getElements();

        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());
        Assert.assertTrue(elements.get(0) instanceof IntegerLiteral);
        Assert.assertEquals(Integer.valueOf(1), ((IntegerLiteral)elements.get(0)).getValue());
        Assert.assertTrue(elements.get(1) instanceof IntegerLiteral);
        Assert.assertEquals(Integer.valueOf(2), ((IntegerLiteral)elements.get(1)).getValue());

        final List<? extends MapEntry<ReferenceLiteral>> annotations =
            ((BindingsLiteral) value.getAnnotation()).getElements();
        Assert.assertNotNull(annotations);
        Assert.assertEquals(2, annotations.size());

        MapEntry<ReferenceLiteral> a0 = annotations.get(0);
        Assert.assertEquals("x", a0.getKey().getValue());
        IntegerLiteral x = (IntegerLiteral) a0.getValue();
        Assert.assertEquals(Integer.valueOf(1), x.getValue());

        MapEntry<ReferenceLiteral> a1 = annotations.get(1);
        Assert.assertEquals("y", a1.getKey().getValue());
        ArrayLiteral y = (ArrayLiteral) a1.getValue();
        Assert.assertEquals(2, y.getElements().size());
        CharacterLiteral a = (CharacterLiteral) y.getElements().get(0);
        Assert.assertEquals(Character.valueOf('a'), a.getValue());
        CharacterLiteral b = (CharacterLiteral) y.getElements().get(1);
        Assert.assertEquals(Character.valueOf('b'), b.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadLiteral__annotated_initializer_functionCall__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader("factory @(a:1){y:2}"));
        final Parser parser = new Parser();

        // cannot annotate initializer
        parser.parseLiteral(scanner);
    }

    // =================================================================================================================
    // complex
    // =================================================================================================================

    @Test
    public void testReadLiteral__complex__1() throws IOException {
        final Scanner scanner = new Scanner(new StringReader(
            "<exception: java.lang.Exception> [exception() <stringBuilder: java.lang.StringBuilder> stringBuilder()]"
        ));
        final Parser parser = new Parser();

        final ListLiteral parsed = (ListLiteral) parser.readLiteral(scanner);
        Assert.assertNotNull(parsed);
        final List<Evaluable> elements = parsed.getElements();
        Assert.assertNotNull(elements);
        Assert.assertEquals(2, elements.size());

        final FunctionCallLiteral element0 = (FunctionCallLiteral) elements.get(0);
        Assert.assertNull(element0.getContext());


        final FunctionCallLiteral element1 = (FunctionCallLiteral) elements.get(1);
        Assert.assertNotNull(element1.getContext());
    }
}
