package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PatternsEqualsIgnoreCaseMatchWithNullTest {

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() throws Exception {
        String input = null;
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp, "ValuePatterns should not be null");

        Class<?> cls = vp.getClass();
        Field typeField = cls.getDeclaredField("type");
        Field valueField = cls.getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);

        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);

        assertEquals(MatchType.EQUALS_IGNORE_CASE, typeVal, "Expected MatchType.EQUALS_IGNORE_CASE");
        assertNull(valueVal, "Stored value should be null when input is null");
    }
}
