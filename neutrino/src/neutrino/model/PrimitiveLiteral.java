package neutrino.model;

import javax.script.Bindings;

public abstract class PrimitiveLiteral<ValueType> implements Evaluable {
    ValueType value;

    public ValueType getValue() { return value; }

    public void setValue(final ValueType value) { this.value = value; }

    @Override
    public ValueType evaluate(final Bindings bindings) { return value; }
}