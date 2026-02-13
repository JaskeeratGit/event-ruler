package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testMultipleCallsProduceDistinctInstances {

    @Test
    public void testMultipleCallsProduceDistinctInstances() throws Exception {
        java.lang.reflect.Method m = Patterns.class.getMethod("equalsIgnoreCaseMatch", String.class);
        Object vp1 = m.invoke(null, "a");
        Object vp2 = m.invoke(null, "b");
        assertNotSame(vp1, vp2, "Each call should produce a new ValuePatterns instance");
    }
}
