package neutrino.model;


import javax.script.Bindings;
import java.util.ArrayList;
import java.util.List;

// TODO: introduce 2 types of FunctionCallLiteral: call with named and positional arguments
public class FunctionCallLiteral<ValueType> extends NonPrimitiveLiteral<ValueType> {
    ReferenceLiteral reference;
    List<CompositeLiteral> initializers = new ArrayList<CompositeLiteral>();

    private CollectionLiteral arguments;

    public ReferenceLiteral getReference() { return reference; }

    public void setReference(final ReferenceLiteral reference) { this.reference = reference; }

    @SuppressWarnings({"UnusedDeclaration"})
    public CollectionLiteral getArguments() { return arguments; }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setArguments(final CollectionLiteral arguments) { this.arguments = arguments; }

    @SuppressWarnings({"UnusedDeclaration"})
    public List<CompositeLiteral> getInitializers() { return initializers; }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setInitializers(final List<CompositeLiteral> initializers) { this.initializers = initializers; }


    @Override
    public ValueType evaluate(final Bindings contextBindings) {
        assert contextBindings != null;
//        System.out.println("To invoke function, need to resolve reference: " + reference.value + " in " + context + ", " + contextBindings);

        final Bindings bindings = evaluateContext(contextBindings);
//        System.out.println("Context bindings for function call: " + bindings);

        final Object function = bindings.get(reference.value);
        if (function == null)
            throw new RuntimeException("Could not resolve '" + reference + '\'');
//        else
//            System.out.println("Reference '" + reference + "' resolved to " + function);

        final ValueType object;
        CollectionLiteral arguments = getArguments();
        if (function instanceof Function && arguments instanceof ArrayLiteral) {
            ArrayLiteral arrayLiteral = (ArrayLiteral) arguments;
            object = ((Function<ValueType>)function).invoke(bindings, arrayLiteral.evaluate(bindings));
        }
        else if (function instanceof Evaluable && arguments instanceof MapBindingsLiteral) {
            MapBindingsLiteral bindingsLiteral = (MapBindingsLiteral) arguments;
            object = ((Evaluable<ValueType>)function).evaluate(bindingsLiteral.evaluate(bindings));
        }
        else throw new IllegalArgumentException();


        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < initializers.size(); i++) {
            initializers.get(i).apply(object, bindings);
        }
        return object;
    }


    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        if (context != null) {
            stringBuilder.append(context);
        }
        stringBuilder.append(reference);
        stringBuilder.append(arguments);

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < initializers.size(); i++) {
            stringBuilder.append(initializers.get(i));
        }
        return stringBuilder.toString();
    }
}