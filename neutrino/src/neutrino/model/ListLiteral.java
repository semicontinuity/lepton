package neutrino.model;


import javax.script.Bindings;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListLiteral<ElementType> extends CompositeLiteral<Evaluable<ElementType>, List<ElementType>> {

    @Override
    public List<ElementType> evaluate(final Bindings contextBindings) {
        final Bindings bindings = evaluateContext(contextBindings);

        final ArrayList<ElementType> result = new ArrayList<ElementType>();
        final List<? extends Evaluable<ElementType>> elements = getElements();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < elements.size(); i++) {
            result.add(elements.get(i).evaluate(bindings));
        }
        return result;
    }


    @Override
    public void apply(final Object object, final Bindings bindings) {
        final List<? extends Evaluable<ElementType>> elements = getElements();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < elements.size(); i++) {
            Evaluable<ElementType> literal = elements.get(i);
            try {
                final Object child = literal.evaluate(bindings);
                Method method = null;
                // if the initializer is "function call", e.g. "field(..) {..}",
                // try to find special addition method for it, like "addField(..)".
                // If not found, look for generic method "add(...)"
                if (literal instanceof FunctionCallLiteral) {
                    final String name = ((FunctionCallLiteral) literal).getReference().getValue();
                    try {
                        method = findAddMethod(object, child, ReflectionUtils.addMethodName(name));
                    }
                    catch (IllegalStateException e) {
                        method = null;
                    }
                }

                if (method == null) method = findAddMethod(object, child, "add");
                method.invoke(object, child);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Method findAddMethod(Object object, Object child, final String methodName) {
        return ReflectionUtils.findAddMethod(methodName, object.getClass(), child.getClass());
    }
}
