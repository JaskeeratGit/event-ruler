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
class Patterns_anythingButMatch_10_0_Test_testAnythingButMatchWithEmptySet {

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
    void testAnythingButMatchWithEmptySet() throws Exception {
        Set<String> input = Collections.emptySet();
        Object result = invokeAnythingButMatch(input);
        assertNotNull(result, "Returned object must not be null");
        // Expect the returned type to be named AnythingBut (or contain that text)
        String simpleName = result.getClass().getSimpleName();
        assertTrue(simpleName.toLowerCase().contains("anythingbut"), "Returned type should be an AnythingBut implementation");
        // Find a Set-typed field and verify it holds the same content (may be same reference or equivalent)
        Field setField = findFieldOfType(result, Set.class);
        if (setField != null) {
            Object fieldValue = setField.get(result);
            // For empty input, either stored null, empty set, or same emptySet; accept equal or both empty/null
            if (fieldValue == null) {
                // ok: stored null
            } else {
                assertTrue(fieldValue instanceof Set, "Discovered field must be a Set");
                @SuppressWarnings("unchecked")
                Set<String> stored = (Set<String>) fieldValue;
                assertTrue(stored.isEmpty(), "Stored set should be empty for empty input");
            }
        }
        // Find a boolean field (constructor supplies false) and assert at least one boolean field is false
        Field boolField = findBooleanField(result);
        if (boolField != null) {
            Object b = boolField.get(result);
            assertNotNull(b, "Boolean field must not be null");
            boolean value = (b instanceof Boolean) ? (Boolean) b : false;
            assertFalse(value, "Constructor invoked with false should produce at least one boolean field equal to false");
        }
    }


}
