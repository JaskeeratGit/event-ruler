package software.amazon.event.ruler;

import java.lang.reflect.Field;
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

public class Patterns_anythingButIgnoreCaseMatch_12_0_Test_testAnythingButIgnoreCaseWithEmptySet {

    // Helper: find a field of given type (or subtype) in object's class hierarchy and return its value
    @SuppressWarnings("unchecked")
    private <T> T getFieldValueByType(Object instance, Class<T> fieldType) throws IllegalAccessException {
        Class<?> cls = instance.getClass();
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                if (fieldType.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    Object val = f.get(instance);
                    return (T) val;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new AssertionError("No field of type " + fieldType.getName() + " found on " + instance.getClass());
    }


    @Test
    public void testAnythingButIgnoreCaseWithEmptySet() throws Exception {
        Set<String> input = Collections.emptySet();
        Object result = software.amazon.event.ruler.Patterns.anythingButIgnoreCaseMatch(input);
        assertNotNull(result);
        software.amazon.event.ruler.MatchType matchTypeValue = getFieldValueByType(result, software.amazon.event.ruler.MatchType.class);
        assertEquals(software.amazon.event.ruler.MatchType.ANYTHING_BUT_IGNORE_CASE, matchTypeValue);
        @SuppressWarnings("unchecked")
        Set<String> stored = getFieldValueByType(result, Set.class);
        // stored may be the empty set or another empty collection; just ensure it's empty (and non-null if implementation copies)
        assertNotNull(stored, "Stored set should not be null for an explicit empty set input");
        assertTrue(stored.isEmpty(), "Stored set should be empty for an empty input set");
    }


}
