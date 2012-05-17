package neutrino.model;

import javax.script.Bindings;

public class ReferenceLiteral<ValueType> extends NonPrimitiveLiteral<ValueType> {
    String value;

    public String getValue() { return value; }

    public void setValue(final String value) { this.value = value; }

    @Override
    public ValueType evaluate(final Bindings contextBindings) {
//        System.out.println("Resolving reference '" + value + "'");
        final Bindings bindings = evaluateContext(contextBindings);
        try {
            // try to resolve the whole reference
            Object result = bindings.get(value);
            if (result == null && !bindings.containsKey(value))
                throw new IllegalArgumentException("Unable to resolve " + value);
            return (ValueType) result;
        }
        catch (RuntimeException e) {
            // try to resolve only the prefix
            final int index = value.indexOf('.');
            if (index == -1) {
                throw e;
            }
            else {
                final String prefix = value.substring(0, index);
                final Bindings resolvedPrefix = (Bindings) bindings.get(prefix);
                if (resolvedPrefix == null) {throw e;}
                else {
                    final String suffix = value.substring(index + 1, value.length());
                    return (ValueType) resolvedPrefix.get(suffix);
                }
            }
        }
    }


    @Override
    public String toString() { return (context != null ? context : "") + value; }
}