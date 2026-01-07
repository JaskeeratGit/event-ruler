package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Unit tests for Patterns.anythingButIgnoreCaseMatch(String)
 */
public class Patterns_anythingButIgnoreCaseMatch_11_0_Test_testAnythingButIgnoreCaseMatch_withSimpleString {

    @Test
    public void testAnythingButIgnoreCaseMatch_withSimpleString() throws Exception {
        String input = "TeStValue";
        // invoke the static method via reflection
        Method m = Patterns.class.getDeclaredMethod("anythingButIgnoreCaseMatch", String.class);
        m.setAccessible(true);
        Object result = m.invoke(null, input);
        assertNotNull(result, "Returned AnythingButValuesSet should not be null");
        // Inspect returned object's fields to find the MatchType enum field and the Set field
        Field enumField = findMatchTypeField(result);
        assertNotNull(enumField, "Expected a field of type MatchType in AnythingButValuesSet");
        enumField.setAccessible(true);
        Object enumValue = enumField.get(result);
        assertNotNull(enumValue, "MatchType field value should not be null");
        // Verify it's the ANYTHING_BUT_IGNORE_CASE constant
        assertEquals(MatchType.ANYTHING_BUT_IGNORE_CASE, enumValue, "MatchType should be ANYTHING_BUT_IGNORE_CASE");
        Field setField = findSetField(result);
        assertNotNull(setField, "Expected a Set field in AnythingButValuesSet");
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Object> values = (Set<Object>) setField.get(result);
        assertNotNull(values, "Values set should not be null");
        assertEquals(1, values.size(), "Values set should be a singleton");
        assertTrue(values.contains(input), "Values set should contain the original input string");
        // Verify the returned set is unmodifiable (Collections.singleton returns immutable set)
        assertThrows(UnsupportedOperationException.class, () -> values.add("another"), "Values set should be unmodifiable (adding should throw)");
    }



    // Helper to find a field of the returned object's class that is of type Set
    private Field findSetField(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            if (Set.class.isAssignableFrom(f.getType())) {
                return f;
            }
        }
        return null;
    }

    // Helper to find a field that likely holds the MatchType enum
    private Field findMatchTypeField(Object obj) {
        for (Field f : obj.getClass().getDeclaredFields()) {
            Class<?> t = f.getType();
            // direct type match by name is conservative and robust across possible packaging
            if (t.getName().endsWith(".MatchType") || t.getSimpleName().equals("MatchType") || t.isEnum()) {
                return f;
            }
        }
        return null;
    }
}
