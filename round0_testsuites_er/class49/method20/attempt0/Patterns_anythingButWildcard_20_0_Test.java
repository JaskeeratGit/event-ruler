package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
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

class Patterns_anythingButWildcard_20_0_Test {

    @Test
    void testAnythingButWildcard_withValues_reflectInternalFields() throws Exception {
        Set<String> input = new HashSet<>(Arrays.asList("one", "two", "three"));
        Object result = Patterns.anythingButWildcard(input);
        assertNotNull(result, "Result should not be null");
        assertEquals("AnythingButValuesSet", result.getClass().getSimpleName(), "Returned type should be AnythingButValuesSet");
        // Find a field holding MatchType and assert it equals ANYTHING_BUT_WILDCARD
        Field matchTypeField = findFieldByTypeName(result.getClass(), "MatchType");
        assertNotNull(matchTypeField, "MatchType field not found in returned object");
        matchTypeField.setAccessible(true);
        Object matchTypeValue = matchTypeField.get(result);
        assertNotNull(matchTypeValue, "MatchType field value should not be null");
        assertEquals(MatchType.ANYTHING_BUT_WILDCARD, matchTypeValue, "MatchType should be ANYTHING_BUT_WILDCARD");
        // Find a field that is a Collection (likely the stored values) and verify contents
        Field collectionField = findFirstCollectionField(result.getClass());
        assertNotNull(collectionField, "No collection-like field found on returned object");
        collectionField.setAccessible(true);
        Object collectionObj = collectionField.get(result);
        assertNotNull(collectionObj, "Internal collection should not be null");
        assertTrue(collectionObj instanceof Collection, "Internal field should be a Collection");
        Collection<?> internalCollection = (Collection<?>) collectionObj;
        assertEquals(input.size(), internalCollection.size(), "Internal collection size should match input");
        assertTrue(internalCollection.containsAll(input), "Internal collection should contain all input values");
    }

    @Test
    void testAnythingButWildcard_withEmptySet() throws Exception {
        Set<String> empty = Collections.emptySet();
        Object result = Patterns.anythingButWildcard(empty);
        assertNotNull(result, "Result should not be null for empty input");
        Field collectionField = findFirstCollectionField(result.getClass());
        assertNotNull(collectionField, "No collection-like field found on returned object for empty input");
        collectionField.setAccessible(true);
        Object collectionObj = collectionField.get(result);
        assertNotNull(collectionObj, "Internal collection should not be null for empty input");
        assertTrue(collectionObj instanceof Collection, "Internal field should be a Collection for empty input");
        Collection<?> internalCollection = (Collection<?>) collectionObj;
        assertTrue(internalCollection.isEmpty(), "Internal collection should be empty when input is empty");
    }

    @Test
    void testPatternsConstructor_and_typeMethod_viaReflection() throws Exception {
        // Patterns has a package-private constructor Patterns(final MatchType type)
        Constructor<Patterns> ctor = Patterns.class.getDeclaredConstructor(MatchType.class);
        ctor.setAccessible(true);
        Patterns p = ctor.newInstance(MatchType.ANYTHING_BUT_WILDCARD);
        assertNotNull(p, "Patterns instance should be created via reflection");
        // Call the public type() method to verify stored value
        MatchType t = p.type();
        assertEquals(MatchType.ANYTHING_BUT_WILDCARD, t, "Patterns.type() should return the value provided to the constructor");
    }

    // --- Helper reflection utilities ---
    private static Field findFieldByTypeName(Class<?> cls, String simpleTypeName) {
        for (Field f : cls.getDeclaredFields()) {
            if (f.getType().getSimpleName().equals(simpleTypeName)) {
                return f;
            }
        }
        // search in superclasses as fallback
        Class<?> sup = cls.getSuperclass();
        while (sup != null) {
            for (Field f : sup.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(simpleTypeName)) {
                    return f;
                }
            }
            sup = sup.getSuperclass();
        }
        return null;
    }

    private static Field findFirstCollectionField(Class<?> cls) {
        for (Field f : cls.getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(f.getType())) {
                return f;
            }
        }
        // check superclasses as fallback
        Class<?> sup = cls.getSuperclass();
        while (sup != null) {
            for (Field f : sup.getDeclaredFields()) {
                if (Collection.class.isAssignableFrom(f.getType())) {
                    return f;
                }
            }
            sup = sup.getSuperclass();
        }
        return null;
    }
}
