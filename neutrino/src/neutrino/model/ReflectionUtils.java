package neutrino.model;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class);

    public static Method setterMethod (
        final Class aClass,
        final String fieldName) throws NoSuchMethodException {

        return findSetterMethod (aClass, fieldName);
    }

    /**
     * Find a specified setter method.
     * Class.getMethod() cannot be used because we do not know the type of parameter.
     * Assume that a compliant object has only one method with the name setXXX().
     * @param aClass a class to introspect
     * @param fieldName field name to find setter for
     * @return setter Method
     */
    private static Method findSetterMethod (final Class aClass, final String fieldName) {
        final String setterName = setterName (fieldName);
        final Method[] methods = aClass.getMethods ();
        for (final Method method : methods) {
            if (setterName.equals (method.getName ()) && method.getParameterTypes ().length == 1)
                return method;
        }
        throw new IllegalArgumentException ("Cannot find setter " + setterName + " in class " + aClass.getName());
    }

    static String setterName (String fieldName) {return "set" + capitalize (fieldName);}


    public static Method getterMethod (final Class aClass, final String fieldName) throws NoSuchMethodException {

        try {
            return aClass.getMethod ("get" + capitalize (fieldName));
        }
        catch (NoSuchMethodException e) {
            return aClass.getMethod ("is" + capitalize (fieldName));
        }
    }

    public static String addMethodName(final String fieldName) {
        return "add" + capitalize (fieldName);
    }

    /** Returns a String which capitalizes the first letter of the string. */
    static String capitalize (String name) {
        if (name == null || name.length () == 0) {
            return name;
        }
        return name.substring (0, 1).toUpperCase (java.util.Locale.ENGLISH) + name.substring (1);
    }


    public static Method findAddMethod (
        final String methodName,
        final Class aClass,
        Class argumentClass) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Looking for method " + methodName);
            LOGGER.debug("Class: " + aClass + " " + aClass.getClassLoader());
        }

        while (true) {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.debug("Trying parameter class: " + argumentClass + " " + argumentClass.getClassLoader());
            }
            try {

                return aClass.getMethod(methodName, argumentClass);
            }
            catch (NoSuchMethodException e) {
            }

            for (Class anInterface : getAllInterfaces(argumentClass)) {
                if (LOGGER.isTraceEnabled()) {
                    LOGGER.debug("Trying parameter class: " + anInterface + " " + anInterface.getClassLoader());
                }
                try {
                    return aClass.getMethod(methodName, anInterface);
                }
                catch (NoSuchMethodException e) {
                }
            }

            argumentClass = argumentClass.getSuperclass();
            if (argumentClass == null)
                throw new IllegalStateException("Could not find " + methodName + " in hierarchy of " + aClass);
        }
    }

    static Type fieldType (Method setter) {
        final Type[] parameterTypes = setter.getGenericParameterTypes ();
        if (parameterTypes.length != 1)
            throw new IllegalArgumentException ("Setter method is not compliant.");
        return parameterTypes[0];
    }

    static Class[] fieldGenericParameters(Type type) {
        Type[] genTypes = ((ParameterizedType)type).getActualTypeArguments();
        Class[] retClasses  = new Class[genTypes.length];
        for (int i=0; i<genTypes.length;++i) {
            Type gt = genTypes[i];
            if (gt instanceof Class){
                retClasses[i] = (Class) gt;
            } else if (gt instanceof ParameterizedType) {
                retClasses[i] = (Class)((ParameterizedType)gt).getRawType();
            } else if (gt instanceof WildcardType) {
                retClasses[i] = (Class)((WildcardType)gt).getUpperBounds()[0];
            }
        }
        return retClasses;
    }

    private static void getAllInterfaces0(Class iface, Set<Class> set){
        set.add(iface);
        for (Class i : iface.getInterfaces()){
            getAllInterfaces0(i,set);
        }
    }

    private static Class[] getAllInterfaces(Class iface) {
        HashSet<Class> set = new HashSet<Class>();
        getAllInterfaces0(iface,set);
        return set.toArray(new Class[0]);
    }

    public static Class primitiveClassFor(Class aClass) {
        if (aClass == Boolean.class) return boolean.class;
        if (aClass == Byte.class) return byte.class;
        if (aClass == Short.class) return short.class;
        if (aClass == Character.class) return char.class;
        if (aClass == Integer.class) return int.class;
        if (aClass == Long.class) return long.class;
        if (aClass == Float.class) return float.class;
        if (aClass == Double.class) return double.class;
        return aClass;
    }
}
