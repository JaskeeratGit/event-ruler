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

class Range_hashCode_15_0_Test_testHashCode_superZeroAfterNullingType {



    @Test
    void testHashCode_superZeroAfterNullingType() throws Exception {
        byte[] bottom = "a".getBytes(StandardCharsets.UTF_8);
        byte[] top = "b".getBytes(StandardCharsets.UTF_8);
        Range range = new Range(bottom, false, top, false, false);
        // Null out the private final 'type' field declared in Patterns to force super.hashCode() == 0
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        typeField.set(range, null);
        Method patternsHash = Patterns.class.getDeclaredMethod("hashCode");
        patternsHash.setAccessible(true);
        int superHash = (Integer) patternsHash.invoke(range);
        assertEquals(0, superHash);
        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode(range.bottom);
        expected = 31 * expected + Boolean.hashCode(range.openBottom);
        expected = 31 * expected + Arrays.hashCode(range.top);
        expected = 31 * expected + Boolean.hashCode(range.openTop);
        assertEquals(expected, range.hashCode());
    }

}
