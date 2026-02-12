package software.amazon.event.ruler;

import org.junit.Test;

/**
 * Unit test for Patterns.anythingButSuffix(String) ensuring that passing null
 * as the suffix throws a NullPointerException.
 *
 * This test uses JUnit 4 style (@Test(expected=...)) because the project test
 * classpath doesn't include JUnit 5 (junit-jupiter).
 */
public class Patterns_anythingButSuffix_17_0_Test_testAnythingButSuffix_nullSuffix_throwsNullPointerException {

    @Test(expected = NullPointerException.class)
    public void testAnythingButSuffix_nullSuffix_throwsNullPointerException() {
        // Cast null to String to avoid ambiguity with an overload that accepts a Set<String>
        Patterns.anythingButSuffix((String) null);
    }
}
