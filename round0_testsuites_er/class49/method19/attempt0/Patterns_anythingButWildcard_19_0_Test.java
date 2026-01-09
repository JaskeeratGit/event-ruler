package software.amazon.event.ruler;

import java.lang.reflect.Field;
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

class Patterns_anythingButWildcard_19_0_Test {

    @Test
    void anythingButWildcard_returnsObjectContainingProvidedValue() throws Exception {
        String value = "abc";
        Object result = Patterns.anythingButWildcard(value);
        assertNotNull(result, "Result should not be null");
        // find a Set field in the returned object and verify it contains the provided value
        Field setField = findFieldAssignableTo(result.getClass(), Set.class);
        assertNotNull(setField, "Expected a Set field in the returned object");
        setField.setAccessible(true);
        Object setObj = setField.get(result);
        assertNotNull(setObj, "Set field value should not be null");
        assertTrue(setObj instanceof Set, "Field should be an instance of Set");
        @SuppressWarnings("unchecked")
        Set<Object> set = (Set<Object>) setObj;
        assertTrue(set.contains(value), "Returned set should contain the provided value");
    }

    @Test
    void anythingButWildcard_handlesEmptyString() throws Exception {
        String value = "";
        Object result = Patterns.anythingButWildcard(value);
        assertNotNull(result, "Result should not be null for empty string");
        Field setField = findFieldAssignableTo(result.getClass(), Set.class);
        assertNotNull(setField, "Expected a Set field in the returned object");
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Object> set = (Set<Object>) setField.get(result);
        assertTrue(set.contains(value), "Returned set should contain the empty string");
    }

    @Test
    void anythingButWildcard_handlesNullValue() throws Exception {
        String value = null;
        Object result = Patterns.anythingButWildcard(value);
        assertNotNull(result, "Result should not be null for null value");
        Field setField = findFieldAssignableTo(result.getClass(), Set.class);
        assertNotNull(setField, "Expected a Set field in the returned object");
        setField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Object> set = (Set<Object>) setField.get(result);
        // Collections.singleton(null) produces a set containing a single null element
        assertTrue(set.contains(null), "Returned set should contain null");
        assertEquals(1, set.size(), "Set should contain exactly one element when created with Collections.singleton(null)");
    }

    @Test
    void anythingButWildcard_setsMatchTypeToAnythingButWildcard() throws Exception {
        Object result = Patterns.anythingButWildcard("x");
        assertNotNull(result, "Result should not be null");
        // find an enum field in the returned object and verify its name is ANYTHING_BUT_WILDCARD
        Field enumField = findFirstFieldWhoseValueIsEnum(result);
        assertNotNull(enumField, "Expected an enum (MatchType) field in the returned object");
        enumField.setAccessible(true);
        Object enumVal = enumField.get(result);
        assertNotNull(enumVal, "Enum field value should not be null");
        assertTrue(enumVal instanceof Enum, "Field should be an enum");
        Enum<?> matchTypeEnum = (Enum<?>) enumVal;
        assertEquals("ANYTHING_BUT_WILDCARD", matchTypeEnum.name(), "Expected MatchType to be ANYTHING_BUT_WILDCARD");
    }

    @Test
    void existsByteString_constant_isN() {
        assertEquals("N", Patterns.EXISTS_BYTE_STRING, "EXISTS_BYTE_STRING should be 'N'");
    }

    // Helper methods using reflection
    private static Field findFieldAssignableTo(Class<?> cls, Class<?> target) {
        for (Field f : cls.getDeclaredFields()) {
            if (target.isAssignableFrom(f.getType())) {
                return f;
            }
        }
        // also search in superclasses (just in case)
        Class<?> sup = cls.getSuperclass();
        while (sup != null && sup != Object.class) {
            for (Field f : sup.getDeclaredFields()) {
                if (target.isAssignableFrom(f.getType())) {
                    return f;
                }
            }
            sup = sup.getSuperclass();
        }
        return null;
    }

    private static Field findFirstFieldWhoseValueIsEnum(Object instance) throws IllegalAccessException {
        Class<?> cls = instance.getClass();
        // check declared fields
        for (Field f : cls.getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(instance);
            if (val instanceof Enum) {
                return f;
            }
        }
        // check superclass fields
        Class<?> sup = cls.getSuperclass();
        while (sup != null && sup != Object.class) {
            for (Field f : sup.getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(instance);
                if (val instanceof Enum) {
                    return f;
                }
            }
            sup = sup.getSuperclass();
        }
        return null;
    }
}
