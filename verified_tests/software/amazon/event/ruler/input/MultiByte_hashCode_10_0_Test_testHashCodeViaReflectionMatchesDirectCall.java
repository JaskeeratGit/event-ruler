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

public class MultiByte_hashCode_10_0_Test_testHashCodeViaReflectionMatchesDirectCall {







    @Test
    void testHashCodeViaReflectionMatchesDirectCall() throws Exception {
        MultiByte mb = new MultiByte((byte) 0x11, (byte) 0x22, (byte) 0x33);
        Method hashMethod = MultiByte.class.getMethod("hashCode");
        Object invoked = hashMethod.invoke(mb);
        assertTrue(invoked instanceof Integer);
        assertEquals(mb.hashCode(), ((Integer) invoked).intValue(), "Reflective invocation of hashCode() should match direct call");
    }
}
