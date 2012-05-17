package neutrino.model;

import javax.script.Bindings;

public abstract class NonPrimitiveLiteral<ValueType> implements Evaluable<ValueType> {

    protected Evaluable<?> annotation;
    protected AbstractBindingsLiteral<?> context;

    public void setContext(final AbstractBindingsLiteral<?> context) { this.context = context; }

    public AbstractBindingsLiteral<?> getContext() { return context; }

    public Evaluable<?> getAnnotation() { return annotation; }

    public void setAnnotation(final Evaluable<?> annotation) { this.annotation = annotation; }


    protected Bindings evaluateContext(final Bindings contextBindings) {
        if (context == null) {
            return contextBindings;
        }
        else {
            final Bindings bindings = context.evaluate(contextBindings);
            return bindings == null ? contextBindings : bindings;
        }
    }
}
