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

class MultiByte_is_2_0_Test_testIsWithNegativeByteValues {

    // Helper to set the private final 'bytes' field via reflection
    private static void setInternalBytes(MultiByte instance, byte[] value) throws Exception {
        Field f = MultiByte.class.getDeclaredField("bytes");
        f.setAccessible(true);
        f.set(instance, value);
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
