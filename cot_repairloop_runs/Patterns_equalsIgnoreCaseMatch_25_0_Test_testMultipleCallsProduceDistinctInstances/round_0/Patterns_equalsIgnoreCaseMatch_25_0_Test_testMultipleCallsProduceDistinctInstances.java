package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
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
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch("abc");
        assertNotNull(vp, "Returned ValuePatterns should not be null");

        // Use reflection to verify internal fields (keeps test resilient to visibility)
        Field typeField = vp.getClass().getDeclaredField("type");
        typeField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        assertEquals(MatchType.EQUALS_IGNORE_CASE, typeVal, "Match type should be EQUALS_IGNORE_CASE");

        Field valueField = vp.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        Object valueVal = valueField.get(vp);
        assertEquals("abc", valueVal, "Stored value should match the provided value");
    }

    @Test
    public void testNullValueIsPreserved() throws Exception {
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(null);
        assertNotNull(vp, "Returned ValuePatterns should not be null even if value is null");

        Field valueField = vp.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        assertNull(valueField.get(vp), "Value field should be null when null was passed in");
    }
}
