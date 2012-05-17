package neutrino.model;


import javax.script.Bindings;

public class ArrayLiteral<E> extends CollectionLiteral<Evaluable<E>, E[]> {

    @Override
    protected char openingCharacter() { return '('; }

    @Override
    protected char closingCharacter() { return ')'; }


    @Override
    public E[] evaluate(final Bindings contextBindings) {
        final Bindings argumentsContext = evaluateContext(contextBindings);
        final Object[] evaluatedArguments = new Object[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            evaluatedArguments[i] = elements.get(i).evaluate(argumentsContext);
        }
        //noinspection unchecked
        return (E[]) evaluatedArguments;
    }
}
