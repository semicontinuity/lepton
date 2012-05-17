package neutrino.model;

import javax.script.Bindings;

public interface Evaluable<ValueType> {
    ValueType evaluate(Bindings bindings);
}
