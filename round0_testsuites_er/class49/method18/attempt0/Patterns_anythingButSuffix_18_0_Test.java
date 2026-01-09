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

public class Patterns_anythingButSuffix_18_0_Test {

    @Test
    public void testAnythingButSuffixProducesReversedSet() throws Exception {
        Set<String> input = new HashSet<>(Arrays.asList("abc", "def"));
        Set<String> inputCopy = new HashSet<>(input);
        Object result = Patterns.anythingButSuffix(input);
        assertNotNull(result, "Returned object should not be null");
        // Find MatchType field
        Field matchTypeField = findFieldByType(result, MatchType.class);
        assertNotNull(matchTypeField, "MatchType field not found in returned object");
        matchTypeField.setAccessible(true);
        MatchType mt = (MatchType) matchTypeField.get(result);
        assertEquals(MatchType.ANYTHING_BUT_SUFFIX, mt, "MatchType should be ANYTHING_BUT_SUFFIX");
        // Find Set field (the reversed values)
        Field setField = findFieldByType(result, Set.class);
        assertNotNull(setField, "Set field not found in returned object");
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<String> values = (Set<String>) setField.get(result);
        assertNotNull(values, "Values set should not be null");
        assertEquals(new HashSet<>(Arrays.asList("cba", "fed")), values, "Values should be the reversed input strings");
        // Ensure original input was not modified
        assertEquals(inputCopy, input, "Original input set should not be modified");
    }

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

    @Test
    public void testAnythingButSuffixWithNullThrowsNPE() {
        Executable call = () -> Patterns.anythingButSuffix(null);
        assertThrows(NullPointerException.class, call, "Passing null should throw NullPointerException");
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
