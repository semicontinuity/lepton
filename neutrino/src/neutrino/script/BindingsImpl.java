package neutrino.script;

import javax.script.Bindings;
import java.util.HashMap;

public class BindingsImpl extends HashMap<String, Object> implements Bindings {
    private Bindings parentBindings;

    public BindingsImpl(final Bindings parentBindings) { this.parentBindings = parentBindings; }

    @Override
    public Object get(Object key) {
        final Object o = super.get(key);
        return o != null ? o : parentBindings.get(key);
    }
}
