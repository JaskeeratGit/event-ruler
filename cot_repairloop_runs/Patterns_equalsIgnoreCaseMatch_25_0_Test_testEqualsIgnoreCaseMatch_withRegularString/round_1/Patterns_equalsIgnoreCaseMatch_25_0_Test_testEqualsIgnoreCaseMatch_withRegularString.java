package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withRegularString {

    @Test
    public void testEqualsIgnoreCaseMatch_withRegularString() throws Exception {
        String input = "TeStValUe";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        // JUnit4 assertNotNull takes (String message, Object object)
        assertNotNull("Returned ValuePatterns should not be null", vp);

        // use reflection to access private fields
        Class<?> cls = vp.getClass();
        Field typeField = cls.getDeclaredField("type");
        Field valueField = cls.getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);

        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);

        // JUnit4 assertXxx methods take message first
        assertSame("MatchType should be EQUALS_IGNORE_CASE", MatchType.EQUALS_IGNORE_CASE, typeVal);
        assertEquals("Stored value should equal the input string", input, valueVal);
    }
}
