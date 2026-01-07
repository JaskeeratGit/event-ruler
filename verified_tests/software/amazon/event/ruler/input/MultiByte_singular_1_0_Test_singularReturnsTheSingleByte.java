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

class MultiByte_singular_1_0_Test_singularReturnsTheSingleByte {

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


}
