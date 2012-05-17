package neutrino.script;

import neutrino.model.Function;
import neutrino.model.ReflectionUtils;

import javax.script.Bindings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

class ReadOnlyBindings extends HashMap<String, Object> implements Bindings {
    @Override
    public Object put(String key, Object value) {
        throw new UnsupportedOperationException();
    }
}

public class JavaBindings extends ReadOnlyBindings {

    @Override
    public Object get(Object key) {
        final String name = (String) key;

        final Class<?> aClass;
        try {
            aClass = Class.forName(name);
            return new ClassPeer(aClass);
        }
        catch (ClassNotFoundException e) {
            // another attempt: class member
            final int lastDot = name.lastIndexOf('.');
            if (lastDot != -1) {
                final String className = name.substring(0, lastDot);
                final String memberName = name.substring(lastDot + 1);

                try {
                    final Class<?> clazz = Class.forName(className);
                    return staticMember(clazz, memberName);

                } catch (ClassNotFoundException e1) {
                    throw new RuntimeException(e1);
                }
            }
        }
        return null;
    }


    private static Object staticMember(final Class<?> clazz, final String memberName) {
        try {
            // try static field
            final Field field = clazz.getField(memberName);
            return field.get(null);
        } catch (Exception e1) {
            // try static method. does not check the existence of the method.
            return staticMethod(clazz, memberName);
        }
    }


    private static Function staticMethod(final Class clazz, final String name) {
        if (!hasMethod(clazz, name))
            throw new RuntimeException();
        return new Function() {
            public Object invoke(final Bindings context, final Object... argumentValues) {
                final Class[] argumentClasses = argumentClasses(argumentValues);

                try {
                    Method method = findMethod(argumentClasses, clazz, name);
                    return method.invoke(null, argumentValues);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String toString() {
                return clazz.getName() + '.' + name;
            }
        };
    }


    private static boolean hasMethod(final Class clazz, final String name) {
        final Method[] methods = clazz.getMethods();
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            if (method.getName().equals(name)) return true;
        }
        return false;
    }


    private static Class[] argumentClasses(final Object... argumentValues) {
        final Class[] argumentClasses = new Class[argumentValues.length];
        for (int i = 0; i < argumentValues.length; i++) {
            final Class argClass = argumentValues[i].getClass();
            argumentClasses[i] = ReflectionUtils.primitiveClassFor(argClass);
        }
        return argumentClasses;
    }



    private static Constructor<?> findConstructor(final Class[] argumentClasses, final Class<?> aClass)
        throws NoSuchMethodException {

        try {
            return aClass.getConstructor(argumentClasses);
        }
        catch (NoSuchMethodException e) {
            final Constructor<?>[] constructors = aClass.getConstructors();

            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < constructors.length; i++) {
                Constructor<?> constructor = constructors[i];
                // TODO: implement properly
                if (constructor.getParameterTypes().length == argumentClasses.length) return constructor;
            }
            throw e;
        }
    }


    private static Method findMethod(final Class[] argumentClasses, final Class<?> aClass, final String name)
        throws NoSuchMethodException {
        final Method[] methods = aClass.getMethods();
methods:
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            // TODO: implement properly
            if (method.getName().equals(name)) {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == argumentClasses.length) {
                    for (int j = 0; j < parameterTypes.length; j++) {
                        if (parameterTypes[j] != argumentClasses[j]) continue methods;
                    }
                    return method;
                }
            }
        }
        throw new NoSuchMethodException(name);
    }


    // -----------------------------------------------------------------------------------------------------------------

    private static class ClassPeer extends ReadOnlyBindings implements Function<Object> {
        private final Class<?> aClass;

        public ClassPeer(Class<?> aClass) {this.aClass = aClass;}

        public Object invoke(final Bindings context, final Object[] argumentValues) {
            try {
                if (argumentValues != null && argumentValues.length > 0) {
                    final Class[] argumentClasses = argumentClasses(argumentValues);

                    final Constructor<?> constructor = findConstructor(argumentClasses, aClass);
                    return constructor.newInstance(argumentValues);
                }
                else {
                    return aClass.newInstance();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);    // TODO: exception to API?
            }
        }

        @Override
        public Object get(final Object key) {
            return staticMember(aClass, (String) key);  // TODO: cache?
        }

        @Override
        public String toString() {
            return aClass.toString();
        }
    }
}
