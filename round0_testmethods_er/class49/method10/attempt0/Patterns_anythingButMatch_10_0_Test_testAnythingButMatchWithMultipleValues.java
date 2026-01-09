package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Collectors;

/**
 * Unit tests for Patterns.anythingButMatch(Set) using reflection so the test does not
 * require compile-time knowledge of the returned AnythingBut class internals.
 */
class Patterns_anythingButMatch_10_0_Test_testAnythingButMatchWithMultipleValues {

    /**
     * Helper: invoke Patterns.anythingButMatch(Set) via reflection.
     */
    private Object invokeAnythingButMatch(Set<String> input) throws Exception {
        Method m = Patterns.class.getMethod("anythingButMatch", Set.class);
        return m.invoke(null, input);
    }

    /**
     * Helper: find the first field of given type in the object's class hierarchy.
     */
    private Field findFieldOfType(Object obj, Class<?> fieldType) {
        Class<?> cls = obj.getClass();
        while (cls != null && cls != Object.class) {
            for (Field f : cls.getDeclaredFields()) {
                if (fieldType.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    /**
     * Helper: find the first boolean or Boolean field in the object's class hierarchy.
     */
    private Field findBooleanField(Object obj) {
        Class<?> cls = obj.getClass();
        while (cls != null && cls != Object.class) {
            for (Field f : cls.getDeclaredFields()) {
                if (f.getType() == boolean.class || f.getType() == Boolean.class) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }


    @Test
    void testAnythingButMatchWithMultipleValues() throws Exception {
        Set<String> input = new HashSet<>();
        input.add("a");
        input.add("b");
        input.add("c");
        Object result = invokeAnythingButMatch(input);
        assertNotNull(result, "Returned object must not be null");
        assertTrue(result.getClass().getSimpleName().toLowerCase().contains("anythingbut"));
        // If the returned object stores a Set field, assert its contents match (order-independent)
        Field setField = findFieldOfType(result, Set.class);
        assertNotNull(setField, "Expected a Set-typed field on the returned AnythingBut object");
        @SuppressWarnings("unchecked")
        Set<String> stored = (Set<String>) setField.get(result);
        assertNotNull(stored, "Stored set must not be null for a provided input set");
        assertEquals(input.size(), stored.size(), "Stored set must have same size as input");
        assertTrue(stored.containsAll(input), "Stored set must contain all input values");
        // Ensure boolean flag taken from constructor is false (see focal method)
        Field boolField = findBooleanField(result);
        assertNotNull(boolField, "Expected a boolean-typed field on the returned AnythingBut object");
        Object b = boolField.get(result);
        boolean value = (b instanceof Boolean) ? (Boolean) b : false;
        assertFalse(value, "Constructor passed false: expect boolean flag to be false");
    }

}
