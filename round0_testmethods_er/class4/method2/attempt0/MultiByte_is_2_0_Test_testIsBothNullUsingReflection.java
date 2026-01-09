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

class MultiByte_is_2_0_Test_testIsBothNullUsingReflection {

    // Helper to set the private final 'bytes' field via reflection
    private static void setInternalBytes(MultiByte instance, byte[] value) throws Exception {
        Field f = MultiByte.class.getDeclaredField("bytes");
        f.setAccessible(true);
        f.set(instance, value);
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



}
