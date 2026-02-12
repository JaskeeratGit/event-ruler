package software.amazon.event.ruler;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * These tests avoid relying on specific private field names (like "type" or "value").
 * Instead they locate the fields by their types (MatchType and String / Object) so that
 * reflection works even if the implementation's field names differ.
 */
public class Patterns_equalsIgnoreCaseMatch_Test {

    @Test
    public void testEqualsIgnoreCaseMatch_withRegularString() throws Exception {
        String input = "TeStValUe";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull("Returned ValuePatterns should not be null", vp);

        Class<?> cls = vp.getClass();

        Field typeField = findFieldOfType(cls, MatchType.class)
                .orElseThrow(() -> new AssertionError("No field of type MatchType found in ValuePatterns"));
        typeField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        assertSame("MatchType stored should be EQUALS_IGNORE_CASE", MatchType.EQUALS_IGNORE_CASE, typeVal);

        // Try to find a field that holds the value. Prefer String, then Object, then any non-MatchType field.
        Field valueField = findFieldOfType(cls, String.class)
                .orElseGet(() -> findFieldOfType(cls, Object.class)
                        .orElseGet(() -> findFirstNonMatchTypeField(cls)
                                .orElseThrow(() -> new AssertionError("No suitable value field found in ValuePatterns"))));
        valueField.setAccessible(true);
        Object valueVal = valueField.get(vp);

        assertEquals("Stored value should equal the input string", input, valueVal);
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(null);
        assertNotNull("Returned ValuePatterns should not be null even when input is null", vp);

        Class<?> cls = vp.getClass();

        Field typeField = findFieldOfType(cls, MatchType.class)
                .orElseThrow(() -> new AssertionError("No field of type MatchType found in ValuePatterns"));
        typeField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        assertSame("MatchType stored should be EQUALS_IGNORE_CASE", MatchType.EQUALS_IGNORE_CASE, typeVal);

        Field valueField = findFieldOfType(cls, String.class)
                .orElseGet(() -> findFieldOfType(cls, Object.class)
                        .orElseGet(() -> findFirstNonMatchTypeField(cls)
                                .orElseThrow(() -> new AssertionError("No suitable value field found in ValuePatterns"))));
        valueField.setAccessible(true);
        Object valueVal = valueField.get(vp);

        assertNull("Stored value should be null when input is null", valueVal);
    }

    private Optional<Field> findFieldOfType(Class<?> cls, Class<?> fieldType) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(f -> fieldType.isAssignableFrom(f.getType()))
                .findFirst();
    }

    private Optional<Field> findFirstNonMatchTypeField(Class<?> cls) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !MatchType.class.isAssignableFrom(f.getType()))
                .findFirst();
    }
}
