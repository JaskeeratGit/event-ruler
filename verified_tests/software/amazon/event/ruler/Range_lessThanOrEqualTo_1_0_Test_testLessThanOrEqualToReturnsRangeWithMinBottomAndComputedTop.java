package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

class Range_lessThanOrEqualTo_1_0_Test_testLessThanOrEqualToReturnsRangeWithMinBottomAndComputedTop {

    @Test
    void testLessThanOrEqualToReturnsRangeWithMinBottomAndComputedTop() throws Exception {
        String value = "123.45";
        // call the focal method
        Range r = Range.lessThanOrEqualTo(value);
        // access private static method stringToComparableBytes via reflection to compute expected top bytes
        Method stringToComparableBytes = Range.class.getDeclaredMethod("stringToComparableBytes", String.class);
        stringToComparableBytes.setAccessible(true);
        byte[] expectedTop = (byte[]) stringToComparableBytes.invoke(null, value);
        // access private static field MIN_RANGE_BYTES via reflection to get expected bottom bytes
        Field minField = Range.class.getDeclaredField("MIN_RANGE_BYTES");
        minField.setAccessible(true);
        byte[] expectedMin = (byte[]) minField.get(null);
        // verify the produced range
        assertNotNull(r, "Range should not be null");
        assertArrayEquals(expectedMin, r.bottom, "bottom should be MIN_RANGE_BYTES");
        assertArrayEquals(expectedTop, r.top, "top should equal stringToComparableBytes(value)");
        assertFalse(r.openBottom, "openBottom should be false");
        assertFalse(r.openTop, "openTop should be false");
        assertFalse(r.isCIDR, "isCIDR should be false");
    }

}
