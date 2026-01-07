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

class MultiByte_singular_1_0_Test_singularThrowsWhenMultipleBytes {



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
