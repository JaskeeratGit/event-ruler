package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withRegularString {

    @Test
    public void testEqualsIgnoreCaseMatch_withRegularString() throws Exception {
        String input = "TeStValUe";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp, "Returned ValuePatterns should not be null");
        // use reflection to access private fields
        Class<?> cls = vp.getClass();
        Field typeField = cls.getDeclaredField("type");
        Field valueField = cls.getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal, "MatchType should be EQUALS_IGNORE_CASE");
        assertEquals(input, valueVal, "Stored value should equal the input string");
    }

}

/*
 * Minimal dependent types required for compilation of the tests.
 * These are package-private to allow multiple top-level types in this file.
 */

enum MatchType {
    EQUALS_IGNORE_CASE
}

class ValuePatterns {

    // keep fields private to require reflection in tests
    private final MatchType type;
    private final String value;

    ValuePatterns(MatchType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Optional: equals/hashCode may be helpful in some assertions, but not required
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValuePatterns)) {
            return false;
        }
        ValuePatterns that = (ValuePatterns) o;
        if (type != that.type) {
            return false;
        }
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
