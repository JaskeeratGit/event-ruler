package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class Patterns_exactMatch_2_0_Test_testExactMatchWithNullValueStoresNull {

    @Test
    public void testExactMatchWithNullValueStoresNull() throws Exception {
        Object valuePatterns = Patterns.exactMatch(null);
        assertNotNull(valuePatterns, "exactMatch should not return null even when passed null");
        Class<?> vpClass = valuePatterns.getClass();
        Field stringField = null;
        for (Field f : vpClass.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.getType().equals(String.class)) {
                stringField = f;
                break;
            }
        }
        if (stringField == null) {
            for (Field f : vpClass.getDeclaredFields()) {
                f.setAccessible(true);
                if (CharSequence.class.isAssignableFrom(f.getType())) {
                    stringField = f;
                    break;
                }
            }
        }

        Field setField = null;
        if (stringField == null) {
            for (Field f : vpClass.getDeclaredFields()) {
                f.setAccessible(true);
                if (Set.class.isAssignableFrom(f.getType())) {
                    setField = f;
                    break;
                }
            }
        }

        if (stringField != null) {
            Object storedValue = stringField.get(valuePatterns);
            assertNull(storedValue, "When passing null to exactMatch, the stored value should be null");
        } else if (setField != null) {
            Object setObj = setField.get(valuePatterns);
            // Accept either a null set or a set that contains a null element
            boolean ok = (setObj == null) || ( (setObj instanceof Set) && ((Set<?>) setObj).contains(null) );
            assertTrue(ok, "When passing null to exactMatch, the stored Set should be null or contain null");
        } else {
            fail("Could not locate a String/CharSequence or Set field in ValuePatterns");
        }
    }

}
