package software.amazon.event.ruler;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * This test is written without external test-framework dependencies to avoid
 * compilation failures when JUnit is not present on the classpath.
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testMultipleCallsProduceDistinctInstances {

    public void testMultipleCallsProduceDistinctInstances() {
        ValuePatterns vp1 = Patterns.equalsIgnoreCaseMatch("a");
        ValuePatterns vp2 = Patterns.equalsIgnoreCaseMatch("b");
        if (vp1 == vp2) {
            throw new AssertionError("Each call should produce a new ValuePatterns instance");
        }
    }

    // Optional entry point for manual execution
    public static void main(String[] args) {
        new Patterns_equalsIgnoreCaseMatch_25_0_Test_testMultipleCallsProduceDistinctInstances()
                .testMultipleCallsProduceDistinctInstances();
        System.out.println("Test passed.");
    }
}
