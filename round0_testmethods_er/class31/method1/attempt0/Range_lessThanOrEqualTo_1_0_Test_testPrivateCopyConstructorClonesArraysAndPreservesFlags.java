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

class Range_lessThanOrEqualTo_1_0_Test_testPrivateCopyConstructorClonesArraysAndPreservesFlags {


    @Test
    void testPrivateCopyConstructorClonesArraysAndPreservesFlags() throws Exception {
        String value = "-7.89e2";
        // original range via focal method
        Range original = Range.lessThanOrEqualTo(value);
        // get private copy constructor Range(Range) and invoke it via reflection
        Constructor<Range> copyCtor = Range.class.getDeclaredConstructor(Range.class);
        copyCtor.setAccessible(true);
        Range copy = copyCtor.newInstance(original);
        // verify fields are equal in content
        assertArrayEquals(original.bottom, copy.bottom, "copied bottom bytes should equal original");
        assertArrayEquals(original.top, copy.top, "copied top bytes should equal original");
        assertEquals(original.openBottom, copy.openBottom, "openBottom flag should be preserved");
        assertEquals(original.openTop, copy.openTop, "openTop flag should be preserved");
        assertEquals(original.isCIDR, copy.isCIDR, "isCIDR flag should be preserved");
        // verify arrays were cloned (different instances)
        assertNotSame(original.bottom, copy.bottom, "bottom arrays should be cloned (different instances)");
        assertNotSame(original.top, copy.top, "top arrays should be cloned (different instances)");
    }
}
