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

class Range_hashCode_15_0_Test_testHashCode_privateCopyConstructor {




    @Test
    void testHashCode_privateCopyConstructor() throws Exception {
        byte[] bottom = "x".getBytes(StandardCharsets.UTF_8);
        byte[] top = "y".getBytes(StandardCharsets.UTF_8);
        Range original = new Range(bottom, true, top, false, false);
        Constructor<Range> copyCtor = Range.class.getDeclaredConstructor(Range.class);
        copyCtor.setAccessible(true);
        Range copy = copyCtor.newInstance(original);
        // Ensure arrays were cloned in the private copy constructor
        assertNotSame(original.bottom, copy.bottom);
        assertNotSame(original.top, copy.top);
        assertArrayEquals(original.bottom, copy.bottom);
        assertArrayEquals(original.top, copy.top);
        // Hash codes should match
        assertEquals(original.hashCode(), copy.hashCode());
    }
}
