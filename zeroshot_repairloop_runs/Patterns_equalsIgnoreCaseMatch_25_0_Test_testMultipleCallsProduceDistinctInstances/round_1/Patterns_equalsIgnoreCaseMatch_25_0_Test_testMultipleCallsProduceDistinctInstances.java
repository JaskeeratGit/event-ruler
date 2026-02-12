package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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
}

/*
 * Minimal dependent types required for compilation of the tests.
 * These are package-private and intended for test compilation only.
 */
enum MatchType {
    EQUALS_IGNORE_CASE
}

class ValuePatterns {

    // keep fields private to require reflection in tests
    private final MatchType type;

    private final String value;

    ValuePatterns(MatchType type, String value) {
        this.type = type;
        this.value = value;
    }

    // Optional: equals/hashCode may be helpful in some assertions, but not required
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValuePatterns)) {
            return false;
        }
        ValuePatterns that = (ValuePatterns) o;
        if (type != that.type) {
            return false;
        }
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
