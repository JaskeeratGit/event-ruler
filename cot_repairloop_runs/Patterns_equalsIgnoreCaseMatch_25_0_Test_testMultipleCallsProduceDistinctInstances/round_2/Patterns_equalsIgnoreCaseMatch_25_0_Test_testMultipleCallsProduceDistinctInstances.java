package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * Note:
 * - The original tests assumed the ValuePatterns class had a field named "value".
 *   That field name is not guaranteed; to make tests resilient we use reflection to
 *   locate fields by their runtime contents/types instead of relying on a specific name.
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testMultipleCallsProduceDistinctInstances {

    @Test
    public void testMultipleCallsProduceDistinctInstances() {
        ValuePatterns vp1 = Patterns.equalsIgnoreCaseMatch("a");
        ValuePatterns vp2 = Patterns.equalsIgnoreCaseMatch("b");
        assertNotSame(vp1, vp2, "Each call should produce a new ValuePatterns instance");
    }

    @Test
    public void testReturnedObjectsHaveExpectedTypeAndValue() throws Exception {
        String expected = "abc";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(expected);
        assertNotNull(vp, "Returned ValuePatterns should not be null");

        // Find a field that holds the MatchType.EQUALS_IGNORE_CASE value
        Field matchTypeField = findFieldByValueInHierarchy(vp, val -> Objects.equals(val, MatchType.EQUALS_IGNORE_CASE));
        assertNotNull(matchTypeField, "Could not locate a field storing MatchType.EQUALS_IGNORE_CASE in ValuePatterns");

        // Find a field of type String that stores the provided value
        Field storedValueField = findFieldInHierarchy(vp, field -> {
            if (field.getType() == String.class) {
                try {
                    field.setAccessible(true);
                    Object val = field.get(vp);
                    return Objects.equals(val, expected);
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
            return false;
        });
        assertNotNull(storedValueField, "Could not locate a String-typed field holding the provided value in ValuePatterns");
    }

    @Test
    public void testNullValueIsPreserved() throws Exception {
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(null);
        assertNotNull(vp, "Returned ValuePatterns should not be null even if value is null");

        // Find a String-typed field whose value is null. This verifies the null passed is preserved.
        Field nullStringField = findFieldInHierarchy(vp, field -> {
            if (field.getType() == String.class) {
                try {
                    field.setAccessible(true);
                    return field.get(vp) == null;
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
            return false;
        });

        assertNotNull(nullStringField, "Could not locate a String-typed field with null value in ValuePatterns when null was passed");
    }

    // Helper: search declared fields in the class and superclasses and return the first matching Field
    private static Field findFieldInHierarchy(Object obj, Predicate<Field> predicate) {
        Class<?> cls = obj.getClass();
        List<Class<?>> hierarchy = new ArrayList<>();
        while (cls != null && cls != Object.class) {
            hierarchy.add(cls);
            cls = cls.getSuperclass();
        }
        for (Class<?> c : hierarchy) {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                try {
                    f.setAccessible(true);
                } catch (SecurityException ignored) {
                }
                try {
                    if (predicate.test(f)) {
                        return f;
                    }
                } catch (Throwable ignored) {
                    // ignore field access problems for this heuristic search
                }
            }
        }
        return null;
    }

    // Helper: search for a field whose runtime value (for the provided instance) matches a predicate on the value
    private static Field findFieldByValueInHierarchy(Object obj, Predicate<Object> valuePredicate) {
        return findFieldInHierarchy(obj, field -> {
            try {
                field.setAccessible(true);
                Object val = field.get(obj);
                return valuePredicate.test(val);
            } catch (IllegalAccessException e) {
                return false;
            }
        });
    }
}
