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

class MultiByte_singular_1_0_Test {

    @Test
    void singularReturnsTheSingleByte() throws Exception {
        byte expected = (byte) 0x7F;
        MultiByte mb = new MultiByte(expected);
        // direct behavior
        byte result = mb.singular();
        assertEquals(expected, result, "singular() should return the single contained byte");
        // verify internal private field 'bytes' contains the same value via reflection
        Field bytesField = MultiByte.class.getDeclaredField("bytes");
        bytesField.setAccessible(true);
        byte[] internal = (byte[]) bytesField.get(mb);
        assertNotNull(internal, "internal bytes[] should not be null");
        assertArrayEquals(new byte[] { expected }, internal, "internal bytes array should contain the passed byte");
    }

    @Test
    void singularThrowsWhenNoBytes() throws Exception {
        // zero-length
        MultiByte mb = new MultiByte();
        IllegalStateException ex = assertThrows(IllegalStateException.class, mb::singular);
        assertEquals("Must be a singular byte", ex.getMessage());
        // also inspect internal bytes array length via reflection
        Field bytesField = MultiByte.class.getDeclaredField("bytes");
        bytesField.setAccessible(true);
        byte[] internal = (byte[]) bytesField.get(mb);
        assertNotNull(internal, "internal bytes[] should not be null even when constructed with no args");
        assertEquals(0, internal.length, "internal bytes length should be 0 for zero-arg construction");
    }

    @Test
    void singularThrowsWhenMultipleBytes() throws Exception {
        MultiByte mb = new MultiByte((byte) 0x01, (byte) 0x02);
        IllegalStateException ex = assertThrows(IllegalStateException.class, mb::singular);
        assertEquals("Must be a singular byte", ex.getMessage());
        // verify internal array has length > 1
        Field bytesField = MultiByte.class.getDeclaredField("bytes");
        bytesField.setAccessible(true);
        byte[] internal = (byte[]) bytesField.get(mb);
        assertNotNull(internal);
        assertTrue(internal.length > 1, "internal bytes length should be greater than 1 for multi-byte construction");
        assertArrayEquals(new byte[] { (byte) 0x01, (byte) 0x02 }, internal);
    }
}
