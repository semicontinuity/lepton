package neutrino.script;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.List;

public class ScriptEngineFactoryImpl implements ScriptEngineFactory {

    JavaBindings platformBindings = new JavaBindings();

    public String getEngineName() { return "OL"; }

    public String getEngineVersion() { return "0.1"; }

    public List<String> getExtensions() { return Arrays.asList("ol"); }

    public List<String> getMimeTypes() { return Arrays.asList("application/x-ol"); }

    public List<String> getNames() { return Arrays.asList("OL"); }

    public String getLanguageName() { return "OL"; }

    public String getLanguageVersion() { return "0.1"; }

    public Object getParameter(final String key) {
        if (ScriptEngine.ENGINE.equals(key)) { return getEngineName(); }
        else if (ScriptEngine.ENGINE_VERSION.equals(key)) { return getEngineVersion(); }
        else if (ScriptEngine.NAME.equals(key)) { return getLanguageName(); }
        else if (ScriptEngine.LANGUAGE.equals(key)) { return getLanguageName(); }
        else if (ScriptEngine.LANGUAGE_VERSION.equals(key)) { return getLanguageVersion(); }
        else return null;
    }

    public String getMethodCallSyntax(String obj, String m, String... args) {
        throw new UnsupportedOperationException();
    }

    public String getOutputStatement(String toDisplay) {
        throw new UnsupportedOperationException();
    }

    public String getProgram(String... statements) {
        throw new UnsupportedOperationException();
    }

    public ScriptEngine getScriptEngine() {
        return new ScriptEngineImpl(this);
    }


    public Bindings createBindings() {
        return new BindingsImpl(platformBindings);
    }
}
