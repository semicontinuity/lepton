package neutrino.script;

import neutrino.model.Evaluable;
import neutrino.parser.Parser;
import neutrino.parser.Scanner;

import javax.script.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class ScriptEngineImpl extends AbstractScriptEngine implements Invocable {

    private final ScriptEngineFactoryImpl factory;

    ScriptEngineImpl(final ScriptEngineFactoryImpl factory) {
        super();
        setBindings(new JavaBindings(), ScriptContext.ENGINE_SCOPE);
        this.factory = factory;
    }

    public Object eval(final String script, final ScriptContext context) throws ScriptException {
        return eval(new StringReader(script), context);
    }

    public Object eval(final Reader reader, final ScriptContext context) throws ScriptException {
        return evaluate(reader, context);
    }

    static Object evaluate(final Reader reader, final ScriptContext context) throws ScriptException {
        return readLiteral(reader).evaluate(context.getBindings(ScriptContext.ENGINE_SCOPE));
    }

    static Evaluable readLiteral(final Reader reader) throws ScriptException {
        final Scanner scanner = new Scanner(reader);
        final Parser parser = new Parser();
        try {
            return parser.readLiteral(scanner);
        } catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    public Bindings createBindings() { return factory.createBindings(); }

    public ScriptEngineFactory getFactory() { return factory; }



    public Object invokeMethod(Object object, String name, Object... args) throws ScriptException {
        final Class clazz = object.getClass();

        try {
            Method method = clazz.getMethod(name);
            method.invoke(object);
        }
        catch (NoSuchMethodException e) {
            if (object instanceof List) {
                for (Object o : (List) object) {
                    invokeMethod(o, name, args);
                }
            }
            else if (object instanceof Map) {
                Map object1 = (Map) object;
                for (Object value : object1.values()) {
                    invokeMethod(value, name, args);
                }
            }

        }
        catch (InvocationTargetException e) {
            throw new ScriptException(e);
        }
        catch (IllegalAccessException e) {
            throw new ScriptException(e);
        }

        return null;
    }

    public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
        throw new UnsupportedOperationException();
    }

    public <T> T getInterface(Class<T> clasz) {
        throw new UnsupportedOperationException();
    }

    public <T> T getInterface(Object thiz, Class<T> clasz) {
        throw new UnsupportedOperationException();
    }
}
