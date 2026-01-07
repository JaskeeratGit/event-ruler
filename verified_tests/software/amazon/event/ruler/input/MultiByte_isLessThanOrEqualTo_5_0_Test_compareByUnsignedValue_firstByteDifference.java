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

class MultiByte_isLessThanOrEqualTo_5_0_Test_compareByUnsignedValue_firstByteDifference {




    @Test
    void compareByUnsignedValue_firstByteDifference() {
        // unsigned 0
        MultiByte low = new MultiByte((byte) 0x00);
        // unsigned 255
        MultiByte high = new MultiByte((byte) 0xFF);
        assertTrue(low.isLessThanOrEqualTo(high));
        assertFalse(high.isLessThanOrEqualTo(low));
    }




}
