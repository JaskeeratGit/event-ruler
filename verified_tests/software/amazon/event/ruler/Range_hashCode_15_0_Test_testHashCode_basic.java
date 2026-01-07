package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

class Range_hashCode_15_0_Test_testHashCode_basic {

    @Test
    void testHashCode_basic() throws Exception {
        byte[] bottom = "1.23".getBytes(StandardCharsets.UTF_8);
        byte[] top = "4.56".getBytes(StandardCharsets.UTF_8);
        // package-private constructor is accessible since test is in same package
        Range range = new Range(bottom, false, top, true, false);
        // Invoke Patterns.hashCode (the superclass implementation) via reflection to obtain super.hashCode()
        Method patternsHash = Patterns.class.getDeclaredMethod("hashCode");
        patternsHash.setAccessible(true);
        int superHash = (Integer) patternsHash.invoke(range);
        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode(bottom);
        expected = 31 * expected + Boolean.hashCode(false);
        expected = 31 * expected + Arrays.hashCode(top);
        expected = 31 * expected + Boolean.hashCode(true);
        assertEquals(expected, range.hashCode());
    }



}
