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

public class MultiByte_hashCode_10_0_Test_testHashCodeMatchesArraysHashCode_singleByte {

    @Test
    void testHashCodeMatchesArraysHashCode_singleByte() {
        MultiByte mb = new MultiByte((byte) 0x01);
        int expected = Arrays.hashCode(mb.getBytes());
        assertEquals(expected, mb.hashCode(), "hashCode() should equal Arrays.hashCode(getBytes()) for single byte");
    }






}
