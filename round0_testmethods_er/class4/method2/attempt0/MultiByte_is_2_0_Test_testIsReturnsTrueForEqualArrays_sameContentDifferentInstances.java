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

class MultiByte_is_2_0_Test_testIsReturnsTrueForEqualArrays_sameContentDifferentInstances {

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








}
