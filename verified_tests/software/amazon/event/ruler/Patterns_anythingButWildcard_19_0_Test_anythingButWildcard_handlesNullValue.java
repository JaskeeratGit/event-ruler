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

class Patterns_anythingButWildcard_19_0_Test_anythingButWildcard_handlesNullValue {



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
