package neutrino.model;

import javax.script.Bindings;

/**
 * A context for composite literals - map and list.
 * @param <ElementType>
 * @param <ValueType>
 */
public abstract class CompositeLiteral<ElementType,ValueType> extends CollectionLiteral<ElementType, ValueType> {

    @Override
    protected char openingCharacter() { return '['; }

    @Override
    protected char closingCharacter() { return ']'; }


    public abstract void apply(Object object, Bindings bindings);
}
