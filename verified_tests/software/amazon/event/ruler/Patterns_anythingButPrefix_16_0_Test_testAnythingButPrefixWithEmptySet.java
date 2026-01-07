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

class Patterns_anythingButPrefix_16_0_Test_testAnythingButPrefixWithEmptySet {


    @Test
    void testAnythingButPrefixWithEmptySet() throws Exception {
        Set<String> empty = Collections.emptySet();
        Object result = Patterns.anythingButPrefix(empty);
        assertNotNull(result, "Returned object should not be null");
        MatchType mt = findMatchTypeValue(result);
        assertNotNull(mt, "MatchType value should not be null");
        assertEquals(MatchType.ANYTHING_BUT_PREFIX, mt, "MatchType must be ANYTHING_BUT_PREFIX");
        @SuppressWarnings("unchecked")
        Set<String> stored = findValuesSet(result);
        assertNotNull(stored, "Stored values set should not be null");
        assertEquals(0, stored.size(), "Stored set should be empty when input is empty");
    }

    // Helper to find a field whose value is a MatchType (via reflection)
    private MatchType findMatchTypeValue(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(obj);
            if (val instanceof MatchType) {
                return (MatchType) val;
            }
            // if value is null, still try to inspect declared type name
            Class<?> ft = f.getType();
            if (ft != null && ft.getSimpleName().equals("MatchType")) {
                // attempt to read value (may be null)
                return (MatchType) val;
            }
        }
        // Search parent classes as a fallback
        Class<?> superClazz = clazz.getSuperclass();
        while (superClazz != null) {
            for (Field f : superClazz.getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(obj);
                if (val instanceof MatchType) {
                    return (MatchType) val;
                }
                Class<?> ft = f.getType();
                if (ft != null && ft.getSimpleName().equals("MatchType")) {
                    return (MatchType) val;
                }
            }
            superClazz = superClazz.getSuperclass();
        }
        fail("No MatchType field found on " + clazz.getName());
        // unreachable
        return null;
    }

    // Helper to find a field whose runtime value is a Set (via reflection)
    @SuppressWarnings("unchecked")
    private Set<String> findValuesSet(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(obj);
            if (val instanceof Set) {
                return (Set<String>) val;
            }
        }
        // Search parent classes as a fallback
        Class<?> superClazz = clazz.getSuperclass();
        while (superClazz != null) {
            for (Field f : superClazz.getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(obj);
                if (val instanceof Set) {
                    return (Set<String>) val;
                }
            }
            superClazz = superClazz.getSuperclass();
        }
        fail("No Set<String> field found on " + clazz.getName());
        // unreachable
        return null;
    }
}
