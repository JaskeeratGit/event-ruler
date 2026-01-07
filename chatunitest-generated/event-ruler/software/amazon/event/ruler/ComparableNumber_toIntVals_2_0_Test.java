package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ch.randelshofer.fastdoubleparser.JavaBigDecimalParser;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

class ComparableNumber_toIntVals_2_0_Test {

    @Test
    void testEmptyString() {
        List<Integer> result = ComparableNumber.toIntVals("");
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list for empty input string");
        // adding should be unsupported (fixed-size list), verify that behavior
        assertThrows(UnsupportedOperationException.class, () -> result.add(1));
    }

    @Test
    void testAsciiString() {
        String input = "ABC";
        List<Integer> result = ComparableNumber.toIntVals(input);
        List<Integer> expected = Arrays.asList(65, 66, 67);
        assertEquals(expected, result);
        assertEquals(3, result.size());
    }

    @Test
    void testSingleNonAsciiCharacter() {
        // U+00E9 -> 233
        String input = "é";
        List<Integer> result = ComparableNumber.toIntVals(input);
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(233), result.get(0));
    }

    @Test
    void testSurrogatePairCharactersAreReturnedAsTwoChars() {
        // Construct string: 'A' + U+1F600 (grinning face, surrogate pair) + 'B'
        String input = "A\uD83D\uDE00B";
        // chars: 'A' (65), high surrogate (55357), low surrogate (56832), 'B' (66)
        List<Integer> result = ComparableNumber.toIntVals(input);
        List<Integer> expected = Arrays.asList(65, 55357, 56832, 66);
        assertEquals(expected, result);
        assertEquals(4, result.size());
    }

    @Test
    void testListSetSupportedButAddUnsupported() {
        String input = "A";
        List<Integer> result = ComparableNumber.toIntVals(input);
        assertEquals(1, result.size());
        // set is supported for Arrays.asList-backed list
        result.set(0, 100);
        assertEquals(Integer.valueOf(100), result.get(0));
        // add is unsupported for fixed-size list
        assertThrows(UnsupportedOperationException.class, () -> result.add(5));
    }

    @Test
    void testNullInputThrowsNPE() {
        assertThrows(NullPointerException.class, () -> ComparableNumber.toIntVals(null));
    }

    @Test
    void testPrivateConstructorViaReflection() throws Exception {
        // Cover the private constructor using reflection
        Constructor<ComparableNumber> ctor = ComparableNumber.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        ComparableNumber instance = ctor.newInstance();
        assertNotNull(instance);
    }
}
