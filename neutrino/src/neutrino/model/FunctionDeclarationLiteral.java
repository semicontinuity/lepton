package neutrino.model;

import javax.script.Bindings;
import java.util.HashMap;
import java.util.Map;

// TODO: document why function declaration does not have context (parsing?)
public class FunctionDeclarationLiteral extends NonPrimitiveLiteral<Function> {

    // TODO: move to one package
    public Map<String, Integer> argumentOrder = new HashMap<String, Integer>();

    public Evaluable<?> returnValue;  // TODO


    class FunctionImpl implements Function, Evaluable {
        @Override
        public Object invoke(final Bindings context, final Object... argumentValues) {
            final int size = argumentOrder.size();
            if ((argumentValues == null && size > 0) ||
                (argumentValues != null && argumentValues.length != size))
                throw new IllegalArgumentException();

            /**
             * The bindings for arguments.
             * When a name is resolved during the evaluation of {@linkplain returnValue},
             * it is matched against names of arguments.
             */
            class ArgumentBindings extends HashMap<String, Object> implements Bindings {
                @Override
                public Object get(final Object key) {
                    if (argumentValues != null) {
                        final Integer order = argumentOrder.get(key);
                        if (order != null) return argumentValues[order];
                    }
                    return context.get(key);
                }
            }

            return returnValue.evaluate(new ArgumentBindings());
        }

        @Override
        public Object evaluate(final Bindings bindings) {
            return returnValue.evaluate(bindings);
        }

        @Override
        public String toString() {
            return "DeclaredFunction[" + FunctionDeclarationLiteral.this + ']';
        }
    }

    @Override
    public Function evaluate(final Bindings bindings) {
        return new FunctionImpl();
    }


    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        if (context != null) {
            stringBuilder.append(context);
        }
        stringBuilder.append('#');

        stringBuilder.append('(');
        //noinspection ForLoopReplaceableByForEach
        // TODO
//        for (int i = 0; i < arguments.size(); i++) {
//            stringBuilder.append(arguments.get(i));
//            if (i < arguments.size() - 1) stringBuilder.append(' ');
//        }
        stringBuilder.append(')');
        stringBuilder.append(' ');
        stringBuilder.append(returnValue);
        return stringBuilder.toString();
    }
}