package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Optional;
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

public class Patterns_anythingButPrefix_15_0_Test_testAnythingButPrefix_withNull {

    // Helper: find a field of the given object whose value is an instance of the requested type (or assignable).
    private static Object findFieldValueByType(Object obj, Class<?> desiredType) throws Exception {
        Class<?> cls = obj.getClass();
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(obj);
                if (val != null && desiredType.isAssignableFrom(val.getClass())) {
                    return val;
                }
                // also accept when desiredType is an interface like Set and val implements it
                if (val != null && desiredType.isAssignableFrom(val.getClass())) {
                    return val;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new NoSuchFieldException("No field of type " + desiredType.getName() + " found in " + obj.getClass());
    }

    // Helper: find a field of the given object whose value is an Enum (MatchType)
    private static Object findEnumFieldValue(Object obj) throws Exception {
        Class<?> cls = obj.getClass();
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                f.setAccessible(true);
                Object val = f.get(obj);
                if (val != null && val.getClass().isEnum()) {
                    return val;
                }
            }
            cls = cls.getSuperclass();
        }
        throw new NoSuchFieldException("No enum field found in " + obj.getClass());
    }



    @Test
    public void testAnythingButPrefix_withNull() throws Exception {
        String prefix = null;
        Object result = Patterns.anythingButPrefix(prefix);
        assertNotNull(result);
        Object enumVal = findEnumFieldValue(result);
        assertNotNull(enumVal);
        String enumName = ((Enum<?>) enumVal).name();
        assertEquals("ANYTHING_BUT_PREFIX", enumName);
        Object valuesObj = findFieldValueByType(result, Set.class);
        assertNotNull(valuesObj);
        @SuppressWarnings("unchecked")
        Set<Object> valuesSet = (Set<Object>) valuesObj;
        assertEquals(1, valuesSet.size());
        // since Collections.singleton(null) is used in the implementation, the set should contain a single null element
        assertTrue(valuesSet.contains(null), "Values set should contain null when prefix is null");
    }
}
