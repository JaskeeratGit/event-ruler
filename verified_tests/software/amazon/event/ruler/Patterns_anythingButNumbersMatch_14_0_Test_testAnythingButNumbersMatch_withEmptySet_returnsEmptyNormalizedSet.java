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

public class Patterns_anythingButNumbersMatch_14_0_Test_testAnythingButNumbersMatch_withEmptySet_returnsEmptyNormalizedSet {

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
    public void testAnythingButNumbersMatch_withEmptySet_returnsEmptyNormalizedSet() throws Exception {
        Set<String> input = Collections.emptySet();
        Method m = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        Object anythingButObj = m.invoke(null, input);
        Set<String> actualNormalized = extractNormalizedSetFromAnythingBut(anythingButObj);
        assertTrue(actualNormalized.isEmpty(), "Normalized set should be empty for empty input");
    }

}
