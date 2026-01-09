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

public class Range_greaterThan_2_0_Test_testStringToComparableBytesConsistency {

    /**
     * Use reflection to access the private static method stringToComparableBytes
     * and the private static field MAX_RANGE_BYTES to validate greaterThan.
     */


    /**
     * Validate that the private stringToComparableBytes produces consistent byte arrays
     * for the same input and different arrays (not the same instance) on repeated calls.
     */
    @Test
    public void testStringToComparableBytesConsistency() throws Exception {
        Method stringToComparableBytes = Range.class.getDeclaredMethod("stringToComparableBytes", String.class);
        stringToComparableBytes.setAccessible(true);
        String[] inputs = { "0", "1.2", "" };
        for (String input : inputs) {
            byte[] a = (byte[]) stringToComparableBytes.invoke(null, input);
            byte[] b = (byte[]) stringToComparableBytes.invoke(null, input);
            assertNotNull(a, "Returned bytes should not be null for input: " + input);
            assertNotNull(b, "Returned bytes should not be null for input: " + input);
            // content should be equal for same input
            assertArrayEquals(a, b, "Byte contents should be equal for repeated calls with input: " + input);
            // but they should be distinct instances (string.getBytes returns a new array)
            if (a != null && b != null) {
                assertNotSame(a, b, "Repeated calls should return distinct byte array instances for input: " + input);
            }
        }
    }

    /**
     * Quick sanity check that greaterThan with an empty string returns a valid Range
     * and sets the openBottom flag.
     */

}
