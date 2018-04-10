package osdi.utiltest;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Helper class for using reflection. Initially it's focussed on
 * invoking methods, but other tools may be added in the future.
 */
public class ReflectionUtil {

    /**
     * Invoke an object's constructor with the specified arguments.
     *
     * @param className the class of the instance to create
     * @param args the constructor arguments
     * @return the created instance
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public static Object constructObject(String className, Object[] args)
            throws IllegalArgumentException, SecurityException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            ClassNotFoundException {
        Class<?> targetClass = Thread.currentThread().getContextClassLoader().loadClass(className);
        return getMatchingConstructor(targetClass, args).newInstance(args);
    }

    private static Constructor<?> getMatchingConstructor(Class<?> targetClass, Object[] args)
            throws NoSuchMethodException {
        for (Constructor<?> ctor : targetClass.getConstructors()) {
            if (isMatchingArgs(ctor.getParameterTypes(), args)) {
                return ctor;
            }
        }
        throw new NoSuchMethodException("No constructor for " + targetClass
                + " compatible with args " + Arrays.toString(args));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getField(Object target, String fieldName, Class<T> fieldType) {
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return (T) f.get(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke a public method using reflection.
     *
     * @param target target object
     * @param method the method name
     * @param args the method arguments
     * @return the return value of the method call, if any
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object callMethod(Object target, String method, Object[] args)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        return getMatchingMethod(target.getClass(), method, args).invoke(target, args);
    }

    public static Object callMethod(Object target, Class<?> interfaceClass, String method,
            Object[] args) throws IllegalArgumentException, SecurityException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return getMatchingMethod(interfaceClass, method, args).invoke(target, args);
    }

    private static Method getMatchingMethod(Class<?> targetClass, String methodName, Object[] args)
            throws NoSuchMethodException {
        for (Method method : targetClass.getMethods()) {
            if (methodName.equals(method.getName())
                    && isMatchingArgs(method.getParameterTypes(), args)) {
                return method;
            }
        }
        throw new NoSuchMethodException(methodName);
    }

    private static boolean isMatchingArgs(Class<?>[] targetArgTypes, Object[] args) {
        if (args == null && targetArgTypes.length == 0) {
            return true;
        }
        if (args.length != targetArgTypes.length) {
            return false;
        }
        for (int j = 0; j < targetArgTypes.length; j++) {
            if (j >= args.length || !isAssignable(targetArgTypes[j], args[j].getClass())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAssignable(Class<?> assignee, Class<?> assigned) {
        if (assignee.isPrimitive()) {
            if (assignee == int.class || assignee == short.class || assignee == long.class
                    || assignee == byte.class) {
                assignee = Number.class;
            } else if (assignee == double.class || assignee == float.class) {
                assignee = Double.class;
            } else if (assignee == char.class) {
                assignee = Character.class;
            } else if (assignee == boolean.class) {
                assignee = Boolean.class;
            }
        }
        return assignee.isAssignableFrom(assigned);
    }

    /**
     * Invoke a static method with the specified arguments.
     *
     * @param methodFqn fully-qualified method name (e.g. "my.package.MyClass.someMethod")
     * @param args the arguments for the method call
     * @return the value returned by the method, if any
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     */
    public static Object callStaticMethod(String methodFqn, Object[] args)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
        int n = methodFqn.lastIndexOf('.');
        String className = methodFqn.substring(0, n);
        String methodName = methodFqn.substring(n + 1, methodFqn.length());
        Class<?> targetClass = Thread.currentThread().getContextClassLoader().loadClass(className);
        return getMatchingMethod(targetClass, methodName, args).invoke(null, args);
    }

    public static void dumpStackTraces() {
        try {
            Object threadMXBean = ReflectionUtil.callStaticMethod(
                    "java.lang.management.ManagementFactory.getThreadMXBean", null);
            Class<?> threadMXBeanInterface = Class.forName("java.lang.management.ThreadMXBean");
            long[] threadIds = (long[]) ReflectionUtil.callMethod(threadMXBean,
                    threadMXBeanInterface, "getAllThreadIds", null);
            Object[] threadInfos = (Object[]) ReflectionUtil.callMethod(threadMXBean,
                    threadMXBeanInterface, "getThreadInfo", new Object[] { threadIds, 10 });
            for (Object threadInfo : threadInfos) {
                System.out.println((String) ReflectionUtil.callMethod(threadInfo,
                        "getThreadName", null));
                BeanInfo info = Introspector.getBeanInfo(threadInfo.getClass());
                PropertyDescriptor[] parameters = info.getPropertyDescriptors();
                for (PropertyDescriptor parameter : parameters) {
                    if (parameter.getReadMethod() != null) {
                        Object value = parameter.getReadMethod().invoke(threadInfo,
                                (Object[]) null);
                        if (value != null && value.getClass().isArray()) {
                            System.out.println("  " + parameter.getName() + ":");
                            for (int a = 0; a < Array.getLength(value); a++) {
                                System.out.println("    " + Array.get(value, a));
                            }
                        } else {
                            if (value != null) {
                                System.out.println("  " + parameter.getName() + ": " + value);
                            }
                        }
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore, probably wrong JVM version
        }
    }

}
