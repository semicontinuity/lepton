package neutrino.model;

import javax.script.Bindings;
import java.lang.reflect.Method;
import java.util.Map;

public class MapEntry<KeyType> implements Map.Entry<KeyType, Evaluable>{
    KeyType key;
    Evaluable value;

    public KeyType getKey() { return key; }

    public void setKey(final KeyType key) { this.key = key; }


    public Evaluable getValue() { return value; }

    public Evaluable setValue(final Evaluable value) { this.value = value; return value;}

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();    
        builder.append(key);
        builder.append(':');
        builder.append(value);
        return builder.toString();
    }


    void apply(final Object object, final Bindings bindings) {
//        System.out.println("Applying " + this);
        final String propertyName = ((ReferenceLiteral) getKey()).getValue();
        final Evaluable initializerValue = getValue();
        try {
            // a: b means setA(b)
            final Method method = ReflectionUtils.setterMethod(
                object.getClass(), propertyName);
            try {
                Object argument = initializerValue.evaluate(bindings);
                method.invoke(object, argument);
//                System.out.println("Invoked " + method + " with " + argument + " on " + object);
            } catch (Exception e) {
//                System.out.println("Could not invoke setter " + method);
                throw new RuntimeException(e);
            }
        }
        catch (Exception e) {
//            System.out.println("Not found " + propertyName + " in " + object.getClass() + ", try getter...");
            // a: b means for (i : b) {getA().add(i)}
            try {
                final Method method = ReflectionUtils.getterMethod(
                    object.getClass(), propertyName);
//                System.out.println("Found getter " + method);
                Object argument = method.invoke(object);
//                System.out.println("Getter call succeeded");
                if (initializerValue instanceof ListLiteral) {
                    ((ListLiteral)initializerValue).apply(argument, bindings);
                }
                else {
                    throw new RuntimeException("Don't know how to apply: " + initializerValue);
                }
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
    }
}