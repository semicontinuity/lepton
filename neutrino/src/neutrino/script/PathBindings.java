package neutrino.script;

import javax.script.Bindings;
import java.io.File;

public class PathBindings extends DelegatingBindings {

    private final Bindings[] elements;

    public PathBindings(final String[] path, final Bindings parent) {
        super(parent);
        this.elements = new Bindings[path.length];
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < path.length; i++) {
            elements[i] = new DirectoryBindingsLiteral(new File(path[i])).evaluate(parent);
        }
    }

    @Override
    public Object get(final Object key) {
        final String name = (String) key;

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < elements.length; i++) {
            final Bindings child = elements[i];
            try {
                Object o = child.get(name);
                if (o != null) return o;    // TODO: support null
            } catch (Exception e) {
                // ok, not found
            }
        }
        return super.get(key);
    }

    @Override
    public Object put(final String name, final Object value) {
        throw new UnsupportedOperationException();
    }
}
