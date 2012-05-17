package neutrino.model;


import javax.script.Bindings;
import java.util.HashMap;
import java.util.Map;

public class MapLiteral extends CompositeLiteral<MapEntry<Evaluable>, Map> {

    @Override
    public Map evaluate(final Bindings bindings) {
        final HashMap<Object,Object> result = new HashMap<Object, Object>();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < elements.size(); i++) {
            final MapEntry<Evaluable> child = elements.get(i);
            Object key = child.getKey().evaluate(bindings);
            result.put(
                key,
                child.getValue().evaluate(bindings)
            );
        }
        return result;
    }


    @Override
    public void apply(final Object object, final Bindings bindings) {
        if (object instanceof Map) {
            // TODO
        }
        else {  // bean
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).apply(object, bindings);
            }
        }
    }
}
