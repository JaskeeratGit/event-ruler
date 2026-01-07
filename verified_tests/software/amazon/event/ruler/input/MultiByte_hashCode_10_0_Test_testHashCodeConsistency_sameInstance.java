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

public class MultiByte_hashCode_10_0_Test_testHashCodeConsistency_sameInstance {



    @Test
    void testHashCodeConsistency_sameInstance() {
        MultiByte mb = new MultiByte((byte) 0x10, (byte) 0x20);
        int first = mb.hashCode();
        int second = mb.hashCode();
        assertEquals(first, second, "hashCode() should be consistent across multiple invocations on same instance");
    }




}
