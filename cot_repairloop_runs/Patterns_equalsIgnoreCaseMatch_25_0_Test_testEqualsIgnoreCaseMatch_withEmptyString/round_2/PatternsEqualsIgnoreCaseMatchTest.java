package software.amazon.event.ruler;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

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
