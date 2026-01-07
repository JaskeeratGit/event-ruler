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

class MultiByte_isLessThanOrEqualTo_5_0_Test_negativeByteValues_unsignedComparison_behavior {






    @Test
    void negativeByteValues_unsignedComparison_behavior() {
        // 255 unsigned
        MultiByte ff = new MultiByte((byte) 0xFF);
        // 128 unsigned
        MultiByte eighty = new MultiByte((byte) 0x80);
        // 255 > 128 so ff <= eighty is false
        assertFalse(ff.isLessThanOrEqualTo(eighty));
        assertTrue(eighty.isLessThanOrEqualTo(ff));
    }


}
