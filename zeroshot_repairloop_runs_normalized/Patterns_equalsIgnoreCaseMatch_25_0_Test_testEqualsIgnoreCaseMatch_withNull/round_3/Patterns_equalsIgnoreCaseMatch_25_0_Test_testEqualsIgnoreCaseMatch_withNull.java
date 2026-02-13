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
        Field typeField = findField(cls, "type");
        Field valueField;
        try {
            valueField = findField(cls, "value");
        } catch (NoSuchFieldException e) {
            // some versions store the payload in a field named "values"
            valueField = findField(cls, "values");
        }
        typeField.setAccessible(true);
        valueField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal);
        assertNull(valueVal, "Stored value should be null when input is null");
    }

    private Field findField(Class<?> cls, String name) throws NoSuchFieldException {
        Class<?> current = cls;
        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(name);
    }
}
