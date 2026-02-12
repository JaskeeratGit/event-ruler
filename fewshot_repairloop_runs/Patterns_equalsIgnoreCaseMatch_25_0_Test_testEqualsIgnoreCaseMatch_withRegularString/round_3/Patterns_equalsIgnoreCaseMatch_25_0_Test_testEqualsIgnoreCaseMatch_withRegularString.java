package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withRegularString {

    @Test
    public void testEqualsIgnoreCaseMatch_withRegularString() throws Exception {
        String input = "TeStValUe";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull("Returned ValuePatterns should not be null", vp);
        // use public API to access stored fields
        assertSame("MatchType should be EQUALS_IGNORE_CASE", MatchType.EQUALS_IGNORE_CASE, vp.type());
        assertEquals("Stored value should equal the input string", input, vp.pattern());
    }

}
