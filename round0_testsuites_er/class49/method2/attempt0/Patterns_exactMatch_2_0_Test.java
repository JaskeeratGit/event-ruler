package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_exactMatch_2_0_Test {

    @Test
    public void testExactMatchCreatesValuePatternsWithExactTypeAndValue() throws Exception {
        String input = "hello-world";
        Object valuePatterns = Patterns.exactMatch(input);
        assertNotNull(valuePatterns, "exactMatch should not return null");
        Class<?> vpClass = valuePatterns.getClass();
        // Find an enum field (MatchType) and a String field (value) in ValuePatterns via reflection.
        Field enumField = null;
        Field stringField = null;
        for (Field f : vpClass.getDeclaredFields()) {
            f.setAccessible(true);
            if (enumField == null && f.getType().isEnum()) {
                enumField = f;
            }
            if (stringField == null && f.getType().equals(String.class)) {
                stringField = f;
            }
            if (enumField != null && stringField != null)
                break;
        }
        // Fallbacks if exact types weren't found above
        if (enumField == null) {
            for (Field f : vpClass.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().isEnum()) {
                    enumField = f;
                    break;
                }
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
        assertNotNull(enumField, "Could not locate an enum field in ValuePatterns to verify MatchType");
        Object enumValue = enumField.get(valuePatterns);
        assertNotNull(enumValue, "MatchType enum field should not be null");
        assertEquals("EXACT", ((Enum<?>) enumValue).name(), "MatchType should be EXACT for exactMatch(...)");
        assertNotNull(stringField, "Could not locate a String/CharSequence field in ValuePatterns to verify value");
        Object storedValue = stringField.get(valuePatterns);
        assertEquals(input, storedValue, "ValuePatterns should store the provided value");
    }

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
        assertNotNull(stringField, "Could not locate a String/CharSequence field in ValuePatterns");
        Object storedValue = stringField.get(valuePatterns);
        assertNull(storedValue, "When passing null to exactMatch, the stored value should be null");
    }

    @Test
    public void testExistsByteStringConstant() {
        assertEquals("N", Patterns.EXISTS_BYTE_STRING, "EXISTS_BYTE_STRING constant should be 'N'");
    }
}
