package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class PatternsEqualsIgnoreCaseMatchTest {

    @Test
    public void testEqualsIgnoreCaseMatch_withEmptyString() throws Exception {
        String input = "";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        Class<?> cls = vp.getClass();
        Field typeField = cls.getDeclaredField("type");
        Field valueField = cls.getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);

        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);

        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal);
        assertEquals(input, valueVal);
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNonEmptyString() throws Exception {
        String input = "TeSt";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        Field typeField = vp.getClass().getDeclaredField("type");
        Field valueField = vp.getClass().getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);

        assertSame(MatchType.EQUALS_IGNORE_CASE, typeField.get(vp));
        assertEquals(input, valueField.get(vp));

        // Also verify equals/hashCode behavior with another instance with same data
        ValuePatterns expected = new ValuePatterns(MatchType.EQUALS_IGNORE_CASE, input);
        assertEquals(expected, vp);
        assertEquals(expected.hashCode(), vp.hashCode());
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        String input = null;
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        Field valueField = vp.getClass().getDeclaredField("value");
        Field typeField = vp.getClass().getDeclaredField("type");
        valueField.setAccessible(true);
        typeField.setAccessible(true);

        assertNull(valueField.get(vp));
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeField.get(vp));
    }
}

/*
 * Minimal dependent types required for compilation of the tests.
 *
 * These are package-private (no 'public' modifier) so they can live in the
 * same file as the test class without violating the one-public-class-per-file rule.
 * If the real project already provides these types, the declarations below
 * should be removed to avoid duplicate-type compilation errors.
 */

enum MatchType {
    EQUALS_IGNORE_CASE
}

class ValuePatterns {

    // keep fields private to require reflection in tests
    private final MatchType type;
    private final String value;

    // constructor made package-private so tests in the same package can construct instances if needed
    ValuePatterns(MatchType type, String value) {
        this.type = type;
        this.value = value;
    }

    // equals/hashCode are handy for assertions
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValuePatterns)) return false;

        ValuePatterns that = (ValuePatterns) o;

        if (type != that.type) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
