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

class MultiByte_singular_1_0_Test_singularThrowsWhenNoBytes {


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

}
