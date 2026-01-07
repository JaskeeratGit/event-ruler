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

class MultiByte_isLessThanOrEqualTo_5_0_Test_prefixLonger_isLessThanOrEqualTo_false_and_strict_false {



    @Test
    void prefixLonger_isLessThanOrEqualTo_false_and_strict_false() {
        MultiByte longer = new MultiByte((byte) 0x01, (byte) 0x02);
        MultiByte shorter = new MultiByte((byte) 0x01);
        // longer has shorter as prefix -> longer > shorter so should be false
        assertFalse(longer.isLessThanOrEqualTo(shorter));
    }





}
