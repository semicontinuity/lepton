package neutrino.script;

import javax.script.Bindings;
import javax.script.SimpleBindings;

public class DelegatingBindings extends SimpleBindings {
    protected final Bindings delegate;

    public DelegatingBindings(final Bindings bindings, final Bindings fallback) {
        super(bindings);
        this.delegate = fallback;
    }

    public DelegatingBindings(final Bindings fallback) {
        this.delegate = fallback;
    }

    @Override
    public Object get(final Object key) {
        final String name = (String) key;
        Object o = super.get(name);
        if (o != null) return o;    // TODO: support null

        return delegate.get(key);
    }
}
