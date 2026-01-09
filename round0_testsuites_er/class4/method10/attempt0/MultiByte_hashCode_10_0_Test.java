package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.DefaultParser.NINE_BYTE;
import static software.amazon.event.ruler.input.DefaultParser.ZERO_BYTE;

public class MultiByte_hashCode_10_0_Test {

    @Test
    void testHashCodeMatchesArraysHashCode_singleByte() {
        MultiByte mb = new MultiByte((byte) 0x01);
        int expected = Arrays.hashCode(mb.getBytes());
        assertEquals(expected, mb.hashCode(), "hashCode() should equal Arrays.hashCode(getBytes()) for single byte");
    }

    @Test
    void testHashCodeMatchesArraysHashCode_multiByte() {
        // use a variety of bytes including negative values
        MultiByte mb = new MultiByte((byte) 0x00, (byte) 0x7F, (byte) 0xC2, (byte) 0xBF, (byte) 0x80);
        int expected = Arrays.hashCode(mb.getBytes());
        assertEquals(expected, mb.hashCode(), "hashCode() should equal Arrays.hashCode(getBytes()) for multiple bytes");
    }

    @Test
    void testHashCodeConsistency_sameInstance() {
        MultiByte mb = new MultiByte((byte) 0x10, (byte) 0x20);
        int first = mb.hashCode();
        int second = mb.hashCode();
        assertEquals(first, second, "hashCode() should be consistent across multiple invocations on same instance");
    }

    @Test
    void testHashCodeDifferentForDifferentContent() {
        MultiByte a = new MultiByte((byte) 0x01);
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x02);
        // Different contents should produce different Arrays.hashCode results for these specific inputs
        assertNotEquals(Arrays.hashCode(a.getBytes()), Arrays.hashCode(b.getBytes()));
        assertNotEquals(a.hashCode(), b.hashCode(), "Different byte content should typically produce different hash codes");
    }

    @Test
    void testGetBytesReturnsCopy_modifyingReturnedArrayDoesNotChangeHash() {
        MultiByte mb = new MultiByte((byte) 0x0A, (byte) 0x0B, (byte) 0x0C);
        int before = mb.hashCode();
        byte[] out = mb.getBytes();
        // modify returned array
        out[0] = (byte) 0xFF;
        // hashCode should remain the same because getBytes() returns a copy
        assertEquals(before, mb.hashCode(), "Modifying the array returned by getBytes() must not affect internal state or hashCode()");
    }

    @Test
    void testConstructorThrowsOnEmptyVarargs() {
        assertThrows(IllegalArgumentException.class, () -> new MultiByte(), "Constructor must throw when no bytes are provided");
    }

    @Test
    void testHashCodeViaReflectionMatchesDirectCall() throws Exception {
        MultiByte mb = new MultiByte((byte) 0x11, (byte) 0x22, (byte) 0x33);
        Method hashMethod = MultiByte.class.getMethod("hashCode");
        Object invoked = hashMethod.invoke(mb);
        assertTrue(invoked instanceof Integer);
        assertEquals(mb.hashCode(), ((Integer) invoked).intValue(), "Reflective invocation of hashCode() should match direct call");
    }
}
