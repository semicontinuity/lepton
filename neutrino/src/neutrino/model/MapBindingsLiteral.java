package neutrino.model;

import javax.script.Bindings;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.List;

public abstract class MapBindingsLiteral extends AbstractBindingsLiteral<MapEntry<ReferenceLiteral>> {

    private transient HashMap<String, Evaluable> evaluatedEntries;

    @Override
    public Bindings evaluate(final Bindings contextBindings) {
        if (evaluatedEntries == null) {
            evaluatedEntries = new HashMap<String, Evaluable>();
            final List<? extends MapEntry<ReferenceLiteral>> elements = getElements();

            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < elements.size(); i++) {
                final MapEntry<ReferenceLiteral> entry = elements.get(i);
                evaluatedEntries.put(
                    entry.getKey().getValue(),
                    entry.getValue());
            }
        }

//        System.out.println("Evaluating " + this + " in " + contextBindings);
        final Bindings parentBindings = (context != null) ? context.evaluate(contextBindings) : contextBindings;
        return new SimpleBindings() {

            @Override
            public int size() {
                return evaluatedEntries.size();
            }

            @Override
            public boolean containsKey(Object key) {
                return evaluatedEntries.containsKey(key);
            }

            @Override
            public Object get(final Object key) {
                final String name = (String) key;

                final int index = name.indexOf('.');
                if (index == -1) {
                    final Object o = smartGet(name, parentBindings);
                    if (o != null) return o;
                }
                else {
                    final String prefix = name.substring(0, index);
                    final Bindings resolvedPrefix = (Bindings) smartGet(prefix, this);
                    if (resolvedPrefix != null) {
                        final Object o = resolvedPrefix.get(name.substring(index + 1, name.length()));
                        if (o != null) return o;
                    }
                    final Object o = parentBindings.get(prefix);
                    if (o != null) return o;
                }
                return parentBindings.get(name);
            }


            private Object smartGet(String name, final Bindings parentBindings) {
                if (evaluatedEntries.containsKey(name) && !super.containsKey(name)) {
                    final Object result = evaluatedEntries.get(name).evaluate(parentBindings);
                    if (result != null) {
                        put(name, result);
                    }
                }

                Object o = super.get(name);
                return o != null ? o : parentBindings.get(name);
            }

            @Override
            public String toString() {
                return "MapBindingsLiteral[literal=" + MapBindingsLiteral.this + "; contextBindings=" + contextBindings + "]";
            }
        };
    }
}
