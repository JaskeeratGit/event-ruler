package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * Notes:
 * - These tests use reflection but search the class hierarchy (not just declared fields)
 *   so they are robust to different ValuePatterns implementations where fields might
 *   be declared on a superclass.
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withNull {

    /**
     * Find a field in the given object's class hierarchy that has the declared type MatchType.
     * If none is found by type, attempt to locate a field whose runtime value equals the
     * desired enum constant.
     */
    private Field findMatchTypeField(Object instance, MatchType desired) {
        Class<?> cls = instance.getClass();
        // First pass: find a field declared as MatchType
        Class<?> cur = cls;
        while (cur != null && cur != Object.class) {
            for (Field f : cur.getDeclaredFields()) {
                if (f.getType() == MatchType.class) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cur = cur.getSuperclass();
        }

        // Second pass: find a field whose runtime value equals the desired enum constant
        cur = cls;
        while (cur != null && cur != Object.class) {
            for (Field f : cur.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    Object val = f.get(instance);
                    if (desired.equals(val)) {
                        return f;
                    }
                } catch (IllegalAccessException ignore) {
                    // try next
                } catch (Throwable ignore) {
                    // In case some getter-like fields throw on access, ignore and continue
                }
            }
            cur = cur.getSuperclass();
        }

        return null;
    }

    /**
     * Find a String-typed field in the object's class hierarchy. If none declared,
     * try to find a field whose runtime value is a String.
     */
    private Field findStringField(Object instance) {
        Class<?> cls = instance.getClass();
        Class<?> cur = cls;
        // First pass: declared String type
        while (cur != null && cur != Object.class) {
            for (Field f : cur.getDeclaredFields()) {
                if (f.getType() == String.class) {
                    f.setAccessible(true);
                    return f;
                }
            }
            cur = cur.getSuperclass();
        }

        // Second pass: runtime String value
        cur = cls;
        while (cur != null && cur != Object.class) {
            for (Field f : cur.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    Object val = f.get(instance);
                    if (val instanceof String) {
                        return f;
                    }
                } catch (IllegalAccessException ignore) {
                } catch (Throwable ignore) {
                }
            }
            cur = cur.getSuperclass();
        }

        return null;
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        String input = null;
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp, "Returned ValuePatterns should not be null");

        Field matchField = findMatchTypeField(vp, MatchType.EQUALS_IGNORE_CASE);
        Field valueField = findStringField(vp);

        assertNotNull(matchField, "Could not locate field holding match type in ValuePatterns implementation");
        assertNotNull(valueField, "Could not locate field holding stored value (String) in ValuePatterns implementation");

        Object typeVal = matchField.get(vp);
        Object valueVal = valueField.get(vp);

        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal, "Match type should be EQUALS_IGNORE_CASE");
        assertNull(valueVal, "Stored value should be null when input is null");
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withValue() throws Exception {
        String input = "TeStVal";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp, "Returned ValuePatterns should not be null");

        Field matchField = findMatchTypeField(vp, MatchType.EQUALS_IGNORE_CASE);
        Field valueField = findStringField(vp);

        assertNotNull(matchField, "Could not locate field holding match type in ValuePatterns implementation");
        assertNotNull(valueField, "Could not locate field holding stored value (String) in ValuePatterns implementation");

        Object typeVal = matchField.get(vp);
        Object valueVal = valueField.get(vp);

        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal, "Match type should be EQUALS_IGNORE_CASE");
        assertEquals(input, valueVal, "Stored value should be the same as input");
    }
}
