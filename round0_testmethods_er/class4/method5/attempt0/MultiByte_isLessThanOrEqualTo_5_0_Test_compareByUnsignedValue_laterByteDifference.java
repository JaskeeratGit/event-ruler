package software.amazon.event.ruler.input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import static software.amazon.event.ruler.input.DefaultParser.NINE_BYTE;
import static software.amazon.event.ruler.input.DefaultParser.ZERO_BYTE;

class MultiByte_isLessThanOrEqualTo_5_0_Test_compareByUnsignedValue_laterByteDifference {





    @Test
    void compareByUnsignedValue_laterByteDifference() {
        // second byte unsigned 128
        MultiByte a = new MultiByte((byte) 0x01, (byte) 0x80);
        // second byte unsigned 127
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x7F);
        assertFalse(a.isLessThanOrEqualTo(b));
        assertTrue(b.isLessThanOrEqualTo(a));
    }



}
