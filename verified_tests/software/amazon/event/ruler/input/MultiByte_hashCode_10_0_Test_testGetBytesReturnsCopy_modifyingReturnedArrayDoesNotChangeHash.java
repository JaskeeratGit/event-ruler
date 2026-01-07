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

public class MultiByte_hashCode_10_0_Test_testGetBytesReturnsCopy_modifyingReturnedArrayDoesNotChangeHash {





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


}
