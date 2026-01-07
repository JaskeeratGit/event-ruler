package software.amazon.event.ruler;

import org.junit.jupiter.api.function.Executable;
import java.lang.reflect.Field;
import java.util.Arrays;
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

public class Patterns_anythingButSuffix_18_0_Test_testAnythingButSuffixWithEmptySet {


    @Test
    public void testAnythingButSuffixWithEmptySet() throws Exception {
        Set<String> empty = Collections.emptySet();
        Object result = Patterns.anythingButSuffix(empty);
        assertNotNull(result, "Returned object should not be null for empty input");
        Field setField = findFieldByType(result, Set.class);
        assertNotNull(setField, "Set field not found in returned object");
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<String> values = (Set<String>) setField.get(result);
        assertNotNull(values, "Values set should not be null");
        assertTrue(values.isEmpty(), "Values set should be empty for empty input");
    }


    /**
     * Finds the first declared field in the given object's class that is assignable to the given type.
     */
    private static Field findFieldByType(Object obj, Class<?> type) {
        Class<?> cls = obj.getClass();
        for (Field f : cls.getDeclaredFields()) {
            if (type.isAssignableFrom(f.getType())) {
                f.setAccessible(true);
                return f;
            }
        }
        // If not found directly on the class, check superclasses
        Class<?> superCls = cls.getSuperclass();
        while (superCls != null && superCls != Object.class) {
            for (Field f : superCls.getDeclaredFields()) {
                if (type.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    return f;
                }
            }
            superCls = superCls.getSuperclass();
        }
        return null;
    }
}
