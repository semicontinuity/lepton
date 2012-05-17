package neutrino.script;

import neutrino.model.*;

import javax.script.Bindings;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DirectoryBindingsLiteral extends BindingsLiteral {
    private final File directory;
    private transient HashMap<String,Evaluable> literals;

    public DirectoryBindingsLiteral(final File directory) {
        this.directory = directory;
    }


    @Override
    public Bindings evaluate(final Bindings contextBindings) {
        if (literals == null) {
            literals = FilesystemPersistenceUtils.load(directory);
            for (String key : literals.keySet()) {
                final ReferenceLiteral referenceLiteral = new ReferenceLiteral();
                referenceLiteral.setValue(key);

                final MapEntry entry = new MapEntry<ReferenceLiteral>();
                entry.setKey(referenceLiteral);
                final Evaluable literal = literals.get(key);
//                if (literal instanceof NonPrimitiveLiteral) {
//                    ((NonPrimitiveLiteral)literal).setContext(this);
//                }

                entry.setValue(literal);
                final List elements1 = elements;
                elements1.add(entry);
            }
        }

        class DirectoryBindings extends HashMap<String, Object> implements Bindings {
            @Override
            public Object get(final Object key) {
//                System.out.println("Resolving " + key + " in " + this);
                final String name = (String)key;
                final String prefix = prefixOf(name);

                if (!super.containsKey(prefix) && literals.containsKey(prefix)) {
//                    System.out.println("Object '" + prefix + "' is not evaluated from literal yet, evaluating..");

                    super.put(prefix, null);    // TODO:???
                    final Evaluable literal = literals.get(prefix);
                    final Object evaluate = literal.evaluate(this); // TODO: this: suspicious?
                    super.put(prefix, evaluate);
                }

                if (super.containsKey(prefix)) {
                    final Object realObject = super.get(prefix);
                    return name.length() == prefix.length()
                        ? realObject
                        : ((Bindings) realObject).get(name.substring(prefix.length() + 1));
                }
                else {
//                    System.out.println("Name '" + prefix + "' is not known in " + this + ", asking parent");
                    return contextBindings.get(name);
                }
            }

            @Override
            public boolean containsKey(final Object key) {
                return literals.containsKey(key);
            }

            @Override
            public Object put(final String key, final Object value) { throw new UnsupportedOperationException(); }

            @Override
            public String toString() {
                return "DirectoryBindings['" + directory + "']";
            }
        }

        return new DirectoryBindings();
    }


    @Override
    public List<? extends MapEntry<ReferenceLiteral>> getElements() {
        throw new UnsupportedOperationException();
    }


    public static String prefixOf(final String name) {
        final int index = name.indexOf('.');
        return (index == -1) ? name : name.substring(0, index);
    }

    @Override
    public String toString() {
        return "DirectoryBindingsLiteral['" + directory + "']";
    }
}
