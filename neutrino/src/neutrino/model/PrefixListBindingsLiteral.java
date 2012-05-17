package neutrino.model;

import javax.script.Bindings;
import javax.script.SimpleBindings;

public class PrefixListBindingsLiteral extends AbstractBindingsLiteral<ReferenceLiteral> {

    @Override
    protected char openingCharacter() { return '<'; }

    @Override
    protected char closingCharacter() { return '>'; }

    @Override
    public Bindings evaluate(final Bindings contextBindings) {
        final Bindings parentBindings = (context != null) ? context.evaluate(contextBindings) : contextBindings;
        return new SimpleBindings() {
            @Override
            public Object get(final Object key) {
                final String name = (String) key;
//                System.out.println("Resolving '" + name + "' in " + PrefixListBindingsLiteral.this);

                //noinspection ForLoopReplaceableByForEach
                for (int i = 0; i < elements.size(); i++) {
                    final ReferenceLiteral child = elements.get(i);
                    String value = child.getValue();
                    String s = value + '.' + name;

                    try {
                        Object o = parentBindings.get(s);
                        if (o != null) return o;    // TODO: support null
                    } catch (Exception e) {
                        // ok, not found
                    }
                }
//                System.out.println("Could not resolve '" + name + "' in " + PrefixListBindingsLiteral.this);
                return parentBindings.get(key);
            }

            @Override
            public Object put(String name, Object value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String toString() {
                return "PrefixListBindings[literal=" + PrefixListBindingsLiteral.this + ", contextBindings=" + contextBindings + "]";
            }
        };
    }
}
