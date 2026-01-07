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
class Patterns_anythingButMatch_10_0_Test_testAnythingButMatchWithNullSet {

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
    void testAnythingButMatchWithNullSet() throws Exception {
        // Passing null should still create an AnythingBut instance (method directly forwards argument)
        Object result = invokeAnythingButMatch(null);
        assertNotNull(result, "Method should return an object even when passed null (it forwards the argument)");
        assertTrue(result.getClass().getSimpleName().toLowerCase().contains("anythingbut"));
        // If there is a Set field, it may be null; ensure behavior is consistent with forwarded null
        Field setField = findFieldOfType(result, Set.class);
        if (setField != null) {
            Object stored = setField.get(result);
            // Accept either null or an empty/equivalent representation; prefer null for forwarded null
            // So we assert that stored == null || stored is empty
            if (stored != null) {
                assertTrue(((Set<?>) stored).isEmpty(), "When null is forwarded some implementations may store an empty set");
            }
        }
        // Check boolean field again
        Field boolField = findBooleanField(result);
        if (boolField != null) {
            Object b = boolField.get(result);
            boolean value = (b instanceof Boolean) ? (Boolean) b : false;
            assertFalse(value, "Constructor passed false: expect boolean flag to be false");
        }
    }
}
