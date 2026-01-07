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

public class Patterns_numericEquals_22_0_Test_testNumericEqualsProducesExpectedMatchTypeAndEncodedValue {


    @Test
    public void testNumericEqualsProducesExpectedMatchTypeAndEncodedValue() throws Exception {
        final String input = "123";
        final String expectedEncoded = ComparableNumber.generate(input);
        ValuePatterns vp = Patterns.numericEquals(input);
        assertNotNull(vp);
        boolean foundMatchType = false;
        boolean foundEncodedValue = false;
        Class<?> cls = vp.getClass();
        for (Field f : cls.getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(vp);
            if (value == null) {
                continue;
            }
            // detect MatchType field and verify it's NUMERIC_EQ
            if (value instanceof MatchType) {
                if (value == MatchType.NUMERIC_EQ) {
                    foundMatchType = true;
                }
            }
            // detect any field whose toString equals the encoded representation
            // This is robust if the internal stored representation is a String or wraps it.
            try {
                if (expectedEncoded.equals(value.toString())) {
                    foundEncodedValue = true;
                }
            } catch (Exception ignored) {
            }
        }
        assertTrue(foundMatchType, "ValuePatterns should contain a MatchType field equal to MatchType.NUMERIC_EQ");
        assertTrue(foundEncodedValue, "ValuePatterns should contain the ComparableNumber.generate(...) encoded value");
    }

}
