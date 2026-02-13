package software.amazon.event.ruler;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Unit tests for Patterns.equalsIgnoreCaseMatch(String)
 */
public class Patterns_equalsIgnoreCaseMatch_25_0_Test_testEqualsIgnoreCaseMatch_withEmptyString {


    @Test
    public void testEqualsIgnoreCaseMatch_withEmptyString() throws Exception {
        String input = "";
        ValuePatterns vp = Patterns.equalsIgnoreCaseMatch(input);
        assertNotNull(vp);
        Class<?> cls = vp.getClass();
        Field typeField = cls.getDeclaredField("type");
        Field valueField = cls.getDeclaredField("value");
        typeField.setAccessible(true);
        valueField.setAccessible(true);
        Object typeVal = typeField.get(vp);
        Object valueVal = valueField.get(vp);
        assertSame(MatchType.EQUALS_IGNORE_CASE, typeVal);
        assertEquals(input, valueVal);
    }

    /*
     * Minimal dependent types required for compilation of the tests.
     * Placed as nested types to avoid creating package-private top-level types
     * that could conflict with other tests.
     */
    static enum MatchType {
        EQUALS_IGNORE_CASE
    }

    static class ValuePatterns {

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
            if (this == o)
                return true;
            if (!(o instanceof ValuePatterns))
                return false;
            ValuePatterns that = (ValuePatterns) o;
            if (type != that.type)
                return false;
            return value != null ? value.equals(that.value) : that.value == null;
        }

        @Override
        public int hashCode() {
            int result = type != null ? type.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

}
