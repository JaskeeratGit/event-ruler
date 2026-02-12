package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * This version is more robust to implementation details of ValuePatterns:
 * - It locates the stored MatchType by inspecting instance fields' runtime values
 *   rather than relying only on declared field types.
 * - It prefers a String-typed instance field for the stored value but will fall
 *   back to any non-match-type instance field if necessary.
 */
public class Patterns_equalsIgnoreCaseMatch_Test {

    @Test
    public void testEqualsIgnoreCaseMatch_withRegularString() throws Exception {
        String input = "TeStValUe";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp, "Returned ValuePatterns should not be null");

        Class<?> cls = vp.getClass();

        // 1) Find the field that holds the MatchType value by checking instance field values.
        Field matchField = findInstanceFieldHoldingValue(cls, vp, MatchType.EQUALS_IGNORE_CASE)
                .orElseThrow(() -> new AssertionError("No instance field with value MatchType.EQUALS_IGNORE_CASE found in ValuePatterns"));
        matchField.setAccessible(true);
        Object typeVal = matchField.get(vp);
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal, "MatchType stored should be EQUALS_IGNORE_CASE");

        // 2) Find the field that holds the provided string value.
        // Prefer a String-typed instance field (and not the matchField). If none, pick first non-static instance field that's not the matchField.
        Field valueField = findInstanceFieldOfType(cls, String.class, matchField)
                .orElseGet(() -> findFirstInstanceFieldExcluding(cls, matchField)
                        .orElseThrow(() -> new AssertionError("No suitable value field found in ValuePatterns")));
        valueField.setAccessible(true);
        Object valueVal = valueField.get(vp);

        assertEquals(input, valueVal, "Stored value should equal the input string");
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(null);
        assertNotNull(vp, "Returned ValuePatterns should not be null even when input is null");

        Class<?> cls = vp.getClass();

        Field matchField = findInstanceFieldHoldingValue(cls, vp, MatchType.EQUALS_IGNORE_CASE)
                .orElseThrow(() -> new AssertionError("No instance field with value MatchType.EQUALS_IGNORE_CASE found in ValuePatterns"));
        matchField.setAccessible(true);
        Object typeVal = matchField.get(vp);
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal, "MatchType stored should be EQUALS_IGNORE_CASE");

        // For null input we still prefer a String field (it will be null), otherwise pick any instance field except matchField.
        Field valueField = findInstanceFieldOfType(cls, String.class, matchField)
                .orElseGet(() -> findFirstInstanceFieldExcluding(cls, matchField)
                        .orElseThrow(() -> new AssertionError("No suitable value field found in ValuePatterns")));
        valueField.setAccessible(true);
        Object valueVal = valueField.get(vp);

        assertNull(valueVal, "Stored value should be null when input is null");
    }

    /**
     * Finds an instance (non-static) declared field in {@code cls} whose runtime value on {@code instance}
     * equals {@code expectedValue}. Returns the first match if any.
     */
    private Optional<Field> findInstanceFieldHoldingValue(Class<?> cls, Object instance, Object expectedValue) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers())) // only instance fields
                .filter(f -> {
                    try {
                        f.setAccessible(true);
                        Object val = f.get(instance);
                        if (expectedValue == null) {
                            return val == null;
                        } else {
                            return expectedValue.equals(val);
                        }
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                })
                .findFirst();
    }

    /**
     * Finds an instance (non-static) declared field in {@code cls} of the given {@code fieldType},
     * excluding a specific field if provided.
     */
    private Optional<Field> findInstanceFieldOfType(Class<?> cls, Class<?> fieldType, Field exclude) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> fieldType.isAssignableFrom(f.getType()))
                .filter(f -> exclude == null || !f.equals(exclude))
                .findFirst();
    }

    /**
     * Finds the first instance (non-static) declared field in {@code cls} excluding {@code exclude}.
     */
    private Optional<Field> findFirstInstanceFieldExcluding(Class<?> cls, Field exclude) {
        return Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> exclude == null || !f.equals(exclude))
                .findFirst();
    }
}
