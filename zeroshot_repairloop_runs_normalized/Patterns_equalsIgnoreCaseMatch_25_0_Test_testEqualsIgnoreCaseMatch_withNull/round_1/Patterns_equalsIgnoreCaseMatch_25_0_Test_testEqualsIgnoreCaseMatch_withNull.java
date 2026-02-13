package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withNull {

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        String input = null;
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
        assertNull(valueVal, "Stored value should be null when input is null");
    }
}
