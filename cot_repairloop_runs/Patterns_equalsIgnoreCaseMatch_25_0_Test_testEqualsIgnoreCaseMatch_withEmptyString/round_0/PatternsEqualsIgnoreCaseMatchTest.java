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
 * These are placed in the same package as the class under test and made public
 * so other test classes in subpackages can access them as needed.
 */

public enum MatchType {
    EQUALS_IGNORE_CASE
}

public class ValuePatterns {

    // keep fields private to require reflection in tests
    private final MatchType type;
    private final String value;

    // constructor made public so tests in other locations can construct instances if needed
    public ValuePatterns(MatchType type, String value) {
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
