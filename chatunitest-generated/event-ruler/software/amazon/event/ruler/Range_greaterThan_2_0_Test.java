package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

public class Range_greaterThan_2_0_Test {

    /**
     * Use reflection to access the private static method stringToComparableBytes
     * and the private static field MAX_RANGE_BYTES to validate greaterThan.
     */
    @Test
    public void testGreaterThanCreatesRangeWithExpectedBounds() throws Exception {
        String input = "123";
        // Access private static method stringToComparableBytes(String)
        Method stringToComparableBytes = Range.class.getDeclaredMethod("stringToComparableBytes", String.class);
        stringToComparableBytes.setAccessible(true);
        byte[] expectedBottom = (byte[]) stringToComparableBytes.invoke(null, input);
        assertNotNull(expectedBottom, "Expected bottom bytes from stringToComparableBytes to be non-null");
        // Call focal method
        Range range = Range.greaterThan(input);
        assertNotNull(range, "greaterThan should not return null");
        // Verify bottom equals expected bytes
        assertArrayEquals(expectedBottom, range.bottom, "Range.bottom should match bytes produced by stringToComparableBytes");
        // Access private static field MAX_RANGE_BYTES
        Field maxRangeBytesField = Range.class.getDeclaredField("MAX_RANGE_BYTES");
        maxRangeBytesField.setAccessible(true);
        byte[] maxRangeBytes = (byte[]) maxRangeBytesField.get(null);
        assertNotNull(maxRangeBytes, "MAX_RANGE_BYTES should be non-null");
        // Verify top equals MAX_RANGE_BYTES
        assertArrayEquals(maxRangeBytes, range.top, "Range.top should be MAX_RANGE_BYTES");
        // Verify open flags and isCIDR
        assertTrue(range.openBottom, "openBottom should be true for greaterThan");
        assertFalse(range.openTop, "openTop should be false for greaterThan");
        assertFalse(range.isCIDR, "isCIDR should be false for greaterThan");
    }
}
