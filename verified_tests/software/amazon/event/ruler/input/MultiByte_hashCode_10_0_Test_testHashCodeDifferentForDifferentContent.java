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

public class MultiByte_hashCode_10_0_Test_testHashCodeDifferentForDifferentContent {




    @Test
    void testHashCodeDifferentForDifferentContent() {
        MultiByte a = new MultiByte((byte) 0x01);
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x02);
        // Different contents should produce different Arrays.hashCode results for these specific inputs
        assertNotEquals(Arrays.hashCode(a.getBytes()), Arrays.hashCode(b.getBytes()));
        assertNotEquals(a.hashCode(), b.hashCode(), "Different byte content should typically produce different hash codes");
    }



}
