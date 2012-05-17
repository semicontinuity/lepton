package neutrino.model;

import javax.script.Bindings;

public interface Function<ValueType> {
    ValueType invoke(Bindings context, Object... argumentValues);
}
