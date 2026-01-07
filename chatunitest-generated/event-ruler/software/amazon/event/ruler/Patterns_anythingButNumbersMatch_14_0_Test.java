package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

public class Patterns_anythingButNumbersMatch_14_0_Test {

    /**
     * Helper that finds a Set<String> field inside the returned AnythingBut instance via reflection.
     * Returns the Set if found, otherwise fails the test.
     */
    @SuppressWarnings("unchecked")
    private Set<String> extractNormalizedSetFromAnythingBut(final Object anythingButInstance) throws IllegalAccessException {
        final Class<?> clazz = anythingButInstance.getClass();
        // search declared fields for a Set or Collection typed field holding strings
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(anythingButInstance);
            if (value instanceof Set) {
                return (Set<String>) value;
            }
        }
        fail("Could not find a Set field inside " + clazz.getName());
        // unreachable
        return null;
    }

    @Test
    public void testAnythingButNumbersMatch_withValidNumbers_createsNormalizedSet() throws Exception {
        // Prepare inputs - choose numbers that are exactly representable as doubles (integers and zeros)
        Set<String> input = new HashSet<>();
        input.add("1");
        input.add("0");
        input.add("-3");
        // large integer still exactly representable within double precision's integer range
        input.add("12345678");
        // Build expected normalized set by calling ComparableNumber.generate (package-private; test in same package)
        Set<String> expected = new HashSet<>();
        for (String s : input) {
            expected.add(ComparableNumber.generate(s));
        }
        // Invoke Patterns.anythingButNumbersMatch via reflection to avoid compile-time AnythingBut dependency
        Method m = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        Object anythingButObj = m.invoke(null, input);
        // Extract the normalized set stored inside the returned AnythingBut instance
        Set<String> actualNormalized = extractNormalizedSetFromAnythingBut(anythingButObj);
        assertEquals(expected, actualNormalized, "Normalized numbers set should match the ComparableNumber.generate outputs");
    }

    @Test
    public void testAnythingButNumbersMatch_withEmptySet_returnsEmptyNormalizedSet() throws Exception {
        Set<String> input = Collections.emptySet();
        Method m = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        Object anythingButObj = m.invoke(null, input);
        Set<String> actualNormalized = extractNormalizedSetFromAnythingBut(anythingButObj);
        assertTrue(actualNormalized.isEmpty(), "Normalized set should be empty for empty input");
    }

    @Test
    public void testAnythingButNumbersMatch_withNonRepresentableNumber_throwsIllegalArgumentException() throws Exception {
        // Choose a decimal that is not exactly representable and will cause ComparableNumber.generate to throw
        Set<String> input = new HashSet<>();
        // 0.1 is not exactly representable as a double; ComparableNumber.generate should throw
        input.add("0.1");
        Method m = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
            m.invoke(null, input);
        }, "Invoking anythingButNumbersMatch with a non-representable number should throw via reflection");
        // Ensure the underlying cause is IllegalArgumentException from ComparableNumber.generate
        assertNotNull(ite.getCause(), "InvocationTargetException should have a cause");
        assertTrue(ite.getCause() instanceof IllegalArgumentException, "Cause should be IllegalArgumentException for non-representable number");
    }
}
