package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotSame;

public class PatternsEqualsIgnoreCaseMatchTest {

    @Test
    public void testMultipleCallsProduceDistinctInstances() {
        ValuePatterns vp1 = Patterns.equalsIgnoreCaseMatch("a");
        ValuePatterns vp2 = Patterns.equalsIgnoreCaseMatch("b");
        assertNotSame(vp1, vp2, "Each call should produce a new ValuePatterns instance");
    }
}
