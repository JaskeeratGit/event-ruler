package software.amazon.event.ruler.input;

import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import static software.amazon.event.ruler.input.DefaultParser.NINE_BYTE;
import static software.amazon.event.ruler.input.DefaultParser.ZERO_BYTE;

class MultiByte_is_2_0_Test {

    // Helper to set the private final 'bytes' field via reflection
    private static void setInternalBytes(MultiByte instance, byte[] value) throws Exception {
        Field f = MultiByte.class.getDeclaredField("bytes");
        f.setAccessible(true);
        f.set(instance, value);
    }

    @Test
    void testIsReturnsTrueForEqualArrays_sameContentDifferentInstances() {
        MultiByte mb = new MultiByte((byte) 1, (byte) 2, (byte) 3);
        byte[] other = new byte[] { 1, 2, 3 };
        assertTrue(mb.is(other));
    }

    @Test
    void testIsReturnsTrueForEqualArrays_sameReference() {
        byte[] arr = new byte[] { 10, 20, 30 };
        MultiByte mb = new MultiByte(arr);
        // same reference should also be equal
        assertTrue(mb.is(arr));
    }

    @Test
    void testIsReturnsFalseForDifferentContent() {
        MultiByte mb = new MultiByte((byte) 5, (byte) 6);
        // different content
        byte[] other = new byte[] { 5, 7 };
        assertFalse(mb.is(other));
    }

    @Test
    void testIsReturnsFalseForDifferentLength() {
        MultiByte mb = new MultiByte((byte) 8, (byte) 9, (byte) 10);
        // shorter
        byte[] other = new byte[] { 8, 9 };
        assertFalse(mb.is(other));
    }

    @Test
    void testIsBothNullUsingReflection() throws Exception {
        // create instance and set its private bytes field to null
        // initial value irrelevant
        MultiByte mb = new MultiByte((byte) 1);
        setInternalBytes(mb, null);
        // pass null argument -> Arrays.equals(null, null) should be true
        assertTrue(mb.is((byte[]) null));
    }

    @Test
    void testIsInstanceNullArgumentNonNull() throws Exception {
        MultiByte mb = new MultiByte((byte) 2);
        setInternalBytes(mb, null);
        // instance bytes null, argument non-null -> false
        assertFalse(mb.is(new byte[0]));
        assertFalse(mb.is(new byte[] { 2 }));
    }

    @Test
    void testIsInstanceNonNullArgumentNull() {
        MultiByte mb = new MultiByte((byte) 11, (byte) 12);
        // instance non-null, argument null -> false
        assertFalse(mb.is((byte[]) null));
    }

    @Test
    void testIsWithNegativeByteValues() {
        // bytes > 127 are negative in signed byte representation
        // -1
        byte a = (byte) 0xFF;
        // -128
        byte b = (byte) 0x80;
        MultiByte mb = new MultiByte(a, b);
        assertTrue(mb.is(new byte[] { (byte) 0xFF, (byte) 0x80 }));
        assertFalse(mb.is(new byte[] { (byte) 0xFF }));
    }
}
