package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Fixed unit test for Patterns.anythingButIgnoreCaseMatch(Set).
 *
 * The original test used JUnit Jupiter and Mockito imports that were not available
 * in the test classpath; this version uses JUnit 4 so it compiles in environments
 * that provide JUnit 4 only.
 */
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
        // Cast null to the Set<String> overload to avoid ambiguity with the String overload
        Object result = software.amazon.event.ruler.Patterns.anythingButIgnoreCaseMatch((Set<String>) null);
        assertNotNull("Result object should not be null", result);

        software.amazon.event.ruler.MatchType matchTypeValue =
                getFieldValueByType(result, software.amazon.event.ruler.MatchType.class);
        assertEquals("Match type should be ANYTHING_BUT_IGNORE_CASE",
                software.amazon.event.ruler.MatchType.ANYTHING_BUT_IGNORE_CASE, matchTypeValue);

        // For null input, implementation may store null or an empty set. Accept either.
        @SuppressWarnings("unchecked")
        Set<String> stored = getFieldValueByType(result, Set.class);
        if (stored != null) {
            assertTrue("Stored set should be empty when input is null (if implementation chooses empty collection)",
                    stored.isEmpty());
        } else {
            // acceptable: implementation stored null
            assertNull(stored);
        }
    }
}
