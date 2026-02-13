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

public class Patterns_anythingButIgnoreCaseMatch_12_0_Test_testAnythingButIgnoreCaseWithNullInput {

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
    public void testAnythingButIgnoreCaseWithNullInput() throws Exception {
        Object result = software.amazon.event.ruler.Patterns.anythingButIgnoreCaseMatch((Set<String>) null);
        assertNotNull(result);
        software.amazon.event.ruler.MatchType matchTypeValue = getFieldValueByType(result, software.amazon.event.ruler.MatchType.class);
        assertEquals(software.amazon.event.ruler.MatchType.ANYTHING_BUT_IGNORE_CASE, matchTypeValue);
        // For null input, implementation may store null or an empty set. Accept either.
        @SuppressWarnings("unchecked")
        Set<String> stored = getFieldValueByType(result, Set.class);
        if (stored != null) {
            assertTrue(stored.isEmpty(), "Stored set should be empty when input is null (if implementation chooses empty collection)");
        } else {
            // acceptable: implementation stored null
            assertNull(stored);
        }
    }

}
