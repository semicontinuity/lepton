package javax.script;

import neutrino.model.Evaluable;
import neutrino.model.Function;
import neutrino.script.JavaBindings;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEngine__OL__Test {

    private ScriptEngine engine;

    @Before
    public void setUp() {
        engine = new ScriptEngineManager().getEngineByName("OL");
    }

    // =================================================================================================================
    // Boolean
    // =================================================================================================================

    @Test
    public void testEval__boolean__true() throws ScriptException {
        Object value = engine.eval("true");
        Assert.assertEquals(true, value);
    }


    @Test
    public void testEval__boolean__false() throws ScriptException {
        Object value = engine.eval("false");
        Assert.assertEquals(false, value);
    }

    // =================================================================================================================
    // List
    // =================================================================================================================

    @Test
    public void testEval__list__empty() throws ScriptException {
        Object value = engine.eval("[]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testEval__list__2() throws ScriptException {
        Object value = engine.eval("[1 'a']");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals('a', list.get(1));
    }


    @Test
    public void testEval__list__3() throws ScriptException {
        Object value = engine.eval("[<int:java.lang.Integer.valueOf> int(10) 'a']");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(2, list.size());
        Assert.assertSame(10, list.get(0));
        Assert.assertEquals('a', list.get(1));
    }


    @Test
    public void testEval__list__4() throws ScriptException {
        Object value = engine.eval(
            "<\n" +
            "  object: java.lang.Object\n " +
            ">" +
            "[\n" +
            "  object()" +
            "  <stringBuilder: java.lang.StringBuilder> stringBuilder()" +
            "]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(2, list.size());
        Assert.assertNotNull(list.get(0));
        Assert.assertNotNull(list.get(1));
        Assert.assertTrue(list.get(1) instanceof StringBuilder);
    }

    @Test
    public void testEval__list__5() throws ScriptException {
        Object value = engine.eval("[1,2,3]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals(2, list.get(1));
        Assert.assertEquals(3, list.get(2));
    }

    @Test(expected = IllegalStateException.class)
    public void testEval__list__5a() throws ScriptException {
        Object value = engine.eval("[1,2 3]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals(2, list.get(1));
        Assert.assertEquals(3, list.get(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEval__list__5b() throws ScriptException {
        Object value = engine.eval("[1 2,3]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(1, list.get(0));
        Assert.assertEquals(2, list.get(1));
        Assert.assertEquals(3, list.get(2));
    }

    // =================================================================================================================
    // Array
    // =================================================================================================================

    @Test
    public void testEval__array__empty() throws ScriptException {
        Object value = engine.eval("()");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Object[]);
        Object[] array = (Object[]) value;
        Assert.assertEquals(0, array.length);
    }

    @Test
    public void testEval__array__2() throws ScriptException {
        Object value = engine.eval("(1 'a')");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Object[]);
        Object[] array = (Object[]) value;
        Assert.assertEquals(2, array.length);
        Assert.assertEquals(1, array[0]);
        Assert.assertEquals('a', array[1]);
    }


    @Test
    public void testEval__array__3() throws ScriptException {
        Object value = engine.eval("(<int:java.lang.Integer.valueOf> int(10) 'a')");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Object[]);
        Object[] array = (Object[]) value;
        Assert.assertEquals(2, array.length);
        Assert.assertSame(10, array[0]);
        Assert.assertEquals('a', array[1]);
    }


    @Test
    public void testEval__array__4() throws ScriptException {
        Object value = engine.eval(
            "<\n" +
            "  object: java.lang.Object\n " +
            ">" +
            "(\n" +
            "  object()" +
            "  <stringBuilder: java.lang.StringBuilder> stringBuilder()" +
            ")");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Object[]);
        Object[] array = (Object[]) value;
        Assert.assertEquals(2, array.length);
        Assert.assertNotNull(array[0]);
        Assert.assertNotNull(array[1]);
        Assert.assertTrue(array[1] instanceof StringBuilder);
    }


    // =================================================================================================================
    // Map
    // =================================================================================================================

    @Test
    public void testEval__map__2() throws ScriptException {
        Object value = engine.eval("['h':\"Hello\" 'w':\"World\"]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof HashMap);
        Map map = (Map) value;
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Hello", map.get('h'));
        Assert.assertEquals("World", map.get('w'));
    }


    @Test
    public void testEval__map__3() throws ScriptException {
        Object value = engine.eval("['a':1, 'b':2, 'c':3]");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof HashMap);
        Map map = (Map) value;
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(1, map.get('a'));
        Assert.assertEquals(2, map.get('b'));
        Assert.assertEquals(3, map.get('c'));
    }

    // =================================================================================================================
    // Bindings
    // =================================================================================================================

    @Test
    public void testEval__bindings__1() throws ScriptException {
        Object value = engine.eval("(h:\"Hello\" w:\"World\")");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Bindings);
        Map map = (Map) value;
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Hello", map.get("h"));
        Assert.assertEquals("World", map.get("w"));
    }

    @Test
    public void testEval__bindings__2() throws ScriptException {
        Object value = engine.eval("<h:\"Hello\" w:\"World\"> (hh:h ww:w)");
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Bindings);
        Map map = (Map) value;
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Hello", map.get("hh"));
        Assert.assertEquals("World", map.get("ww"));
    }

    // =================================================================================================================
    // Reference
    // =================================================================================================================

    @Test
    public void testEval__reference__1() throws ScriptException {
        Object value = engine.eval("java.lang.String");
        Assert.assertNotNull(value);
    }


    @Test
    public void testEval__reference__2() throws ScriptException {
        Object value = engine.eval("<s:java.lang.String> s");
        Assert.assertNotNull(value);
    }


    @Test
    public void testEval__reference__3() throws ScriptException {
        Object value = engine.eval("java.lang.Integer.MAX_VALUE");
        Assert.assertNotNull(value);
        Assert.assertEquals(Integer.MAX_VALUE, value);
    }


    @Test
    public void testEval__reference__4() throws ScriptException {
        Object value = engine.eval("<Color: java.awt.Color> Color.YELLOW");
        Assert.assertNotNull(value);
        Assert.assertEquals(Color.YELLOW, value);
    }


    @Test
    public void testEval__reference__5() throws ScriptException {
        Object value = engine.eval("java.lang.Integer.valueOf");
        Assert.assertNotNull(value);
    }

    // =================================================================================================================
    // Calling java
    // =================================================================================================================

    @Test
    public void testEval__call__1() throws ScriptException {
        Object value = engine.eval("java.lang.String()");
        Assert.assertEquals("", value);
    }


    @Test
    public void testEval__call__2() throws ScriptException {
        Object value = engine.eval("<s:java.lang.String> s()");
        Assert.assertEquals("", value);
    }


    @Test
    public void testEval__call__3() throws ScriptException {
        Object value = engine.eval("java.lang.String()");
        Assert.assertEquals("", value);
    }


    @Test
    public void testEval__call__4() throws ScriptException {
        Object value = engine.eval("java.lang.String(\"Hello\")");
        Assert.assertEquals("Hello", value);
    }


    @Test
    public void testEval__call__5() throws ScriptException {
        Object value = engine.eval("<c:java.awt.Color> [c(100 101 102) c(200 201 202)]");
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(new Color(100,101,102), list.get(0));
        Assert.assertEquals(new Color(200,201,202), list.get(1));
    }

    @Test
    public void testEval__call__6() throws ScriptException {
        Object value = engine.eval("<c:java.awt.Color> <c1:c(100 101 102) c2:c(200 201 202)> [c1 c2 c1 c2]");
        Assert.assertTrue(value instanceof ArrayList);
        List list = (List) value;
        Assert.assertEquals(4, list.size());
        Object i0 = list.get(0);
        Assert.assertEquals(new Color(100,101,102), i0);
        Object i1 = list.get(1);
        Assert.assertEquals(new Color(200,201,202), i1);
        Object i2 = list.get(2);
        Assert.assertEquals(new Color(100,101,102), i2);
        Object i3 = list.get(3);
        Assert.assertEquals(new Color(200,201,202), i3);

        Assert.assertSame(i0, i2);
        Assert.assertSame(i1, i3);
    }


    @Test
    public void testEval__call__7() throws ScriptException {
        Object value = engine.eval("javax.swing.JButton {text: \"Hello\"}");
        Assert.assertTrue(value instanceof JButton);
        JButton button = (JButton) value;
        Assert.assertEquals("Hello", button.getText());
    }


    @Test
    public void testEval__call__8() throws ScriptException, NoSuchMethodException {
        Object value = engine.eval(
            "<" +
            "  panel:javax.swing.JPanel" +
            "  button:javax.swing.JButton" +
            ">" +
            "panel {" +
            "  button{text: \"B1\"}" +
            "  button{text: \"B2\"}" +
            "}");
        Assert.assertTrue(value instanceof JPanel);
        JPanel panel = (JPanel) value;

        Assert.assertEquals(2, panel.getComponentCount());

        JButton b1 = (JButton) panel.getComponent(0);
        Assert.assertEquals("B1", b1.getText());

        JButton b2 = (JButton) panel.getComponent(1);
        Assert.assertEquals("B2", b2.getText());
    }


    @Test
    public void testEval__call__9() throws ScriptException {
        Bindings bindings = engine.createBindings();
        bindings.put("message", "Hello");

        Object value = engine.eval("javax.swing.JButton{text:message}", bindings);

        Assert.assertTrue(value instanceof JButton);
        JButton button = (JButton) value;
        Assert.assertEquals("Hello", button.getText());
    }


    @Test
    public void testEval__call__10() throws ScriptException {
        Object value = engine.eval("java.lang.Integer.valueOf(10)");

        Assert.assertTrue(value instanceof Integer);
        Integer integer = (Integer) value;
        Assert.assertSame(10, integer);
    }

    // =================================================================================================================
    // Declare and call function
    // =================================================================================================================

    @Test
    public void testEval__declare_call__1() throws ScriptException {
        Object value = engine.eval("#() true");
        Assert.assertTrue(value instanceof Function);
        Object result = ((Function) value).invoke(engine.createBindings());
        Assert.assertTrue((Boolean)result);
    }

    @Test
    public void testEval__declare_call__2() throws ScriptException {
        Object value = engine.eval("#(a b) [a b a b]");
        Assert.assertTrue(value instanceof Function);
        Color a = new Color(100, 101, 102);
        Color b = new Color(200, 201, 202);

        Object result = ((Function) value).invoke(engine.createBindings(), a, b);

        Assert.assertTrue(result instanceof ArrayList);
        List list = (List) result;
        Assert.assertEquals(4, list.size());
        Assert.assertSame(a, list.get(0));
        Assert.assertSame(b, list.get(1));
        Assert.assertSame(a, list.get(2));
        Assert.assertSame(b, list.get(3));
    }


    @Test
    public void testEval__declare_call__3() throws ScriptException {
        Object value = engine.eval("#(returnValue) java.lang.Integer.valueOf(returnValue)");
        Assert.assertTrue(value instanceof Function);
        Object result = ((Function) value).invoke(engine.createBindings(), 10);
        Assert.assertTrue(result instanceof Integer);
        Assert.assertSame(10, result);
    }


    @Test
    public void testEval__declare_call__3_prefer_arguments() throws ScriptException {
        Object value = engine.eval("#(returnValue) java.lang.Integer.valueOf(returnValue)");
        Assert.assertTrue(value instanceof Function);

        Bindings bindings = engine.createBindings();
        bindings.put("returnValue", 30);

        Object result = ((Function) value).invoke(bindings, 10);

        Assert.assertTrue(result instanceof Integer);
        Assert.assertSame(10, result);
    }


    @Test
    public void testEval__declare_call__4() throws ScriptException {
        Object valueOf = engine.eval("java.lang.Integer.valueOf");
        Assert.assertTrue(valueOf instanceof Function);

        Bindings bindings = engine.createBindings();
        bindings.put("int", valueOf);

        Object value = engine.eval("int(10)", bindings);

        Assert.assertTrue(value instanceof Integer);
        Integer integer = (Integer) value;
        Assert.assertSame(10, integer);
    }


    @Test
    public void testEval__declare_call__5() throws ScriptException {
        Object value = engine.eval("<int: #(returnValue) java.lang.Integer.valueOf(returnValue)> int(10)");
        Assert.assertTrue(value instanceof Integer);
        Integer integer = (Integer) value;
        Assert.assertSame(10, integer);
    }

    @Test
    public void testEval__declare_call__6() throws ScriptException {
        Object value = engine.eval("<int: #(v) java.lang.Integer.valueOf(v)> int(v:10)");
        Assert.assertTrue(value instanceof Integer);
        Integer integer = (Integer) value;
        Assert.assertSame(10, integer);
    }

    @Test
    public void testEval__declare_call__7() throws ScriptException {
        Object value = engine.eval("<color: #(r g b) java.awt.Color(r g b)> color(10 20 30)");
        Assert.assertTrue(value instanceof Color);
        Color color = (Color) value;
        Assert.assertEquals(10, color.getRed());
        Assert.assertEquals(20, color.getGreen());
        Assert.assertEquals(30, color.getBlue());
    }

    @Test
    public void testEval__declare_call__8() throws ScriptException {
        Object value = engine.eval("<color: #(r g b) java.awt.Color(r g b)> color(r:10 g:20 b:30)");
        Assert.assertTrue(value instanceof Color);
        Color color = (Color) value;
        Assert.assertEquals(10, color.getRed());
        Assert.assertEquals(20, color.getGreen());
        Assert.assertEquals(30, color.getBlue());
    }

    @Test
    public void testEval__declare_call__9() throws ScriptException {
        Object value = engine.eval("<color: #(r g b) java.awt.Color(r g b)> color(g:20 b:30 r:10)");
        Assert.assertTrue(value instanceof Color);
        Color color = (Color) value;
        Assert.assertEquals(10, color.getRed());
        Assert.assertEquals(20, color.getGreen());
        Assert.assertEquals(30, color.getBlue());
    }

    @Test
    public void testEval__declare_call__10() throws ScriptException {
        Object value = engine.eval("#() #() true");
        Assert.assertTrue(value instanceof Function);
        Bindings bindings = engine.createBindings();

        Object result1 = ((Function) value).invoke(bindings);
        Assert.assertTrue(result1 instanceof Function);

        Object result2 = ((Function) value).invoke(bindings);
        Assert.assertTrue(result2 instanceof Function);

        Assert.assertNotSame(result1, result2);

        Assert.assertTrue((Boolean) ((Function) result1).invoke(engine.createBindings()));
        Assert.assertTrue((Boolean) ((Function) result2).invoke(engine.createBindings()));
    }


    // =================================================================================================================
    // List-type context
    // =================================================================================================================

    @Test
    public void testEval__list_context__1() throws ScriptException {
        Object value = engine.eval("<java.util java.lang> String()");
        Assert.assertEquals("", value);
    }


    // =================================================================================================================
    // Complex cases
    // =================================================================================================================

    @Test
    public void testEval__complex__000() throws ScriptException {
        final String script =
            "(" +
            "  platform: (swing: (frame: #() javax.swing.JFrame()))" +
            ")";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Bindings);
        Assert.assertTrue(((Bindings) value).containsKey("platform"));
        Assert.assertNotNull(((Bindings)value).get("platform"));
    }


    @Test
    public void testEval__complex__00() throws ScriptException {
        final String script =
            "<" +
            "  platform: (swing: (frame: #() javax.swing.JFrame()))" +
            ">" +
            "platform";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Bindings);
        Assert.assertTrue(((Bindings)value).containsKey("swing"));
        Assert.assertNotNull(((Bindings)value).get("swing"));
    }

    @Test
    public void testEval__complex__01() throws ScriptException {
        final String script =
            "<" +
            "  platform: (swing: 1)" +
            ">" +
            "platform.swing";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(1, value);
    }

    @Test
    public void testEval__complex__02() throws ScriptException {
        final String script =
            "<" +
            "  platform: (swing: (frame: #() javax.swing.JFrame()))" +
            ">" +
            "platform.swing.frame";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof Function);
        Assert.assertTrue(value instanceof Evaluable);
        Assert.assertTrue(((Evaluable)value).evaluate(new JavaBindings()) instanceof JFrame);
    }

    @Test
    public void testEval__complex__03() throws ScriptException {
        final String script =
            "<" +
            "  f: #() javax.swing.JFrame()" +
            ">" +
            "f()";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
    }

    @Test
    public void testEval__complex__0() throws ScriptException {
        final String script =
            "<" +
            "  platform: (swing: (frame: #() javax.swing.JFrame()))" +
            ">" +
            "platform.swing.frame()";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
    }

    @Test
    public void testEval__complex__1() throws ScriptException {
        final String script =
            "<" +
            "  platform: (swing: (frame: #() javax.swing.JFrame()))" +
            ">" +
            "<platform.swing> frame()";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
    }

    @Test
    public void testEval__complex__11() throws ScriptException {
        final String script =
            "<" +
            "  platform: (frame: #()javax.swing.JFrame())" +
            ">" +
            "<platform> frame()";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
    }

    @Test
    public void testEval__complex__2() throws ScriptException {
        final String script =
            "<" +
            "  platform: (frame: #()javax.swing.JFrame())" +
            "  demo: (swing: (boxes: #() frame()))" +
            ">" +
            "<platform> demo.swing.boxes()";
        Object value = engine.eval(script);
        Assert.assertNotNull(value);
    }

    @Test
    public void testEval__complex__30() throws ScriptException {
        Object value = engine.eval("<java.util java.lang> @(x:10, y:20, title:\"hello\") String(\"string\")");
        Assert.assertEquals("string", value);
    }

}
