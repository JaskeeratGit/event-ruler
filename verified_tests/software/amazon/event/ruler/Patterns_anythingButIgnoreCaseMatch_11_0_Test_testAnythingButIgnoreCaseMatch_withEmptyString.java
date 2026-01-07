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
public class Patterns_anythingButIgnoreCaseMatch_11_0_Test_testAnythingButIgnoreCaseMatch_withEmptyString {


    @Test
    public void testAnythingButIgnoreCaseMatch_withEmptyString() throws Exception {
        String input = "";
        Method m = Patterns.class.getDeclaredMethod("anythingButIgnoreCaseMatch", String.class);
        m.setAccessible(true);
        Object result = m.invoke(null, input);
        assertNotNull(result);
        Field setField = findSetField(result);
        assertNotNull(setField);
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Object> values = (Set<Object>) setField.get(result);
        assertNotNull(values);
        assertEquals(1, values.size());
        assertTrue(values.contains(input));
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
