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
                final Method method = ReflectionUtils.findAddMethod(
                    "add", object.getClass(), child.getClass());
                method.invoke(object, child);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
