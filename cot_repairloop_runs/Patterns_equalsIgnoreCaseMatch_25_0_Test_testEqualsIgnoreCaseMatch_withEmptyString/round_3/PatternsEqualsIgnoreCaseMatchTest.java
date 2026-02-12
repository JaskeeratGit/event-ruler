package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 *
 * These tests avoid using reflection to inspect private fields (which can vary across implementations).
 * Instead they verify the observable behavior via equals/hashCode which is stable for ValuePatterns.
 */
public class PatternsEqualsIgnoreCaseMatchTest {

    @Test
    public void testEqualsIgnoreCaseMatch_withEmptyString() {
        String input = "";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        // Construct an expected instance with the same semantic values and compare
        ValuePatterns expected = new ValuePatterns(MatchType.EQUALS_IGNORE_CASE, input);
        assertEquals(expected, vp);
        assertEquals(expected.hashCode(), vp.hashCode());
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNonEmptyString() {
        String input = "TeSt";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        ValuePatterns expected = new ValuePatterns(MatchType.EQUALS_IGNORE_CASE, input);
        assertEquals(expected, vp);
        assertEquals(expected.hashCode(), vp.hashCode());

        // Verify that different values are not considered equal
        ValuePatterns different = new ValuePatterns(MatchType.EQUALS_IGNORE_CASE, "other");
        assertNotEquals(different, vp);
    }

    @Test
    public void testEqualsIgnoreCaseMatch_withNull() {
        String input = null;
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);

        ValuePatterns expected = new ValuePatterns(MatchType.EQUALS_IGNORE_CASE, input);
        assertEquals(expected, vp);
        assertEquals(expected.hashCode(), vp.hashCode());
    }
}
