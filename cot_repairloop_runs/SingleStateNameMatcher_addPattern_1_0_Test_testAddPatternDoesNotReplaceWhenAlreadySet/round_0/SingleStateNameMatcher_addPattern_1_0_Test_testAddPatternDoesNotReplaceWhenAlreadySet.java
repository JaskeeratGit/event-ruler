package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SingleStateNameMatcher.addPattern(...).
 *
 * This test verifies that when the internal nameState is already set on the matcher,
 * calling addPattern(...) does not replace it and returns the already-set NameState.
 */
class SingleStateNameMatcher_addPattern_1_0_Test_testAddPatternDoesNotReplaceWhenAlreadySet {

    // Utility to create an instance of a given class using one of its declared constructors.
    // For constructor parameters, provide reasonable defaults: primitives get zero/false, objects get null.
    private Object createInstanceWithDefaults(Class<?> cls) throws Exception {
        Constructor<?>[] ctors = cls.getDeclaredConstructors();
        if (ctors.length == 0) {
            // Try no-arg constructor via getDeclaredConstructor
            Constructor<?> ctor = cls.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        }
        Exception lastEx = null;
        for (Constructor<?> ctor : ctors) {
            try {
                ctor.setAccessible(true);
                Class<?>[] params = ctor.getParameterTypes();
                Object[] args = new Object[params.length];
                for (int i = 0; i < params.length; i++) {
                    Class<?> p = params[i];
                    if (!p.isPrimitive()) {
                        args[i] = null;
                    } else if (p == boolean.class) {
                        args[i] = false;
                    } else if (p == byte.class) {
                        args[i] = (byte) 0;
                    } else if (p == short.class) {
                        args[i] = (short) 0;
                    } else if (p == int.class) {
                        args[i] = 0;
                    } else if (p == long.class) {
                        args[i] = 0L;
                    } else if (p == float.class) {
                        args[i] = 0f;
                    } else if (p == double.class) {
                        args[i] = 0d;
                    } else if (p == char.class) {
                        args[i] = '\0';
                    } else {
                        args[i] = null;
                    }
                }
                return ctor.newInstance(args);
            } catch (Exception ex) {
                lastEx = ex;
                // try next constructor
            }
        }
        // If none worked, rethrow last exception
        if (lastEx != null) throw lastEx;
        // Fallback
        throw new IllegalStateException("Could not instantiate " + cls.getName());
    }

    private Object newPatternsInstance() throws Exception {
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        return createInstanceWithDefaults(patternsClass);
    }

    private Object newNameStateInstance() throws Exception {
        Class<?> nsClass = Class.forName("software.amazon.event.ruler.NameState");
        return createInstanceWithDefaults(nsClass);
    }

    private void setInternalNameState(Object matcher, Object nameState) throws Exception {
        Field f = matcher.getClass().getDeclaredField("nameState");
        f.setAccessible(true);
        f.set(matcher, nameState);
    }

    private Object getInternalNameState(Object matcher) throws Exception {
        Field f = matcher.getClass().getDeclaredField("nameState");
        f.setAccessible(true);
        return f.get(matcher);
    }

    @Test
    void testAddPatternDoesNotReplaceWhenAlreadySet() throws Exception {
        // Arrange
        Class<?> matcherClass = Class.forName("software.amazon.event.ruler.SingleStateNameMatcher");
        Object matcher = matcherClass.getDeclaredConstructor().newInstance();

        Object initialNameState = newNameStateInstance();
        // Pre-set internal nameState to simulate already set
        setInternalNameState(matcher, initialNameState);

        Object patterns = newPatternsInstance();
        Object newNameState = newNameStateInstance();

        // Get the method using exact parameter types from the classes
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Class<?> nameStateClass = Class.forName("software.amazon.event.ruler.NameState");
        Method addPatternMethod = matcherClass.getMethod("addPattern", patternsClass, nameStateClass);

        // Act: invoke addPattern with a different NameState
        Object returned = addPatternMethod.invoke(matcher, patterns, newNameState);

        // Assert: returned value should be the initially set NameState and internal field unchanged
        assertSame(initialNameState, returned, "addPattern should return the already set NameState when present");
        Object after = getInternalNameState(matcher);
        assertSame(initialNameState, after, "Internal nameState should remain the initially set NameState");
    }
}
