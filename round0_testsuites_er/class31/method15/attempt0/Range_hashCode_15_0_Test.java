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

class Range_hashCode_15_0_Test {

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

    @Test
    void testHashCode_nullArrays() throws Exception {
        Range range = new Range(null, true, null, false, true);
        Method patternsHash = Patterns.class.getDeclaredMethod("hashCode");
        patternsHash.setAccessible(true);
        int superHash = (Integer) patternsHash.invoke(range);
        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode(null);
        expected = 31 * expected + Boolean.hashCode(true);
        expected = 31 * expected + Arrays.hashCode(null);
        expected = 31 * expected + Boolean.hashCode(false);
        assertEquals(expected, range.hashCode());
    }

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
