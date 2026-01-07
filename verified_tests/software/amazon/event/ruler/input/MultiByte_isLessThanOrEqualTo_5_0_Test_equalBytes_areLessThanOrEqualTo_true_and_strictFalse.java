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

class MultiByte_isLessThanOrEqualTo_5_0_Test_equalBytes_areLessThanOrEqualTo_true_and_strictFalse {

    @Test
    void equalBytes_areLessThanOrEqualTo_true_and_strictFalse() throws Exception {
        MultiByte a = new MultiByte((byte) 0x01, (byte) 0x02);
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x02);
        // public API: orEqualTo == true
        assertTrue(a.isLessThanOrEqualTo(b));
        assertTrue(b.isLessThanOrEqualTo(a));
        // private isLessThan with orEqualTo == false should return false for equal arrays
        Method isLessThan = MultiByte.class.getDeclaredMethod("isLessThan", MultiByte.class, boolean.class);
        isLessThan.setAccessible(true);
        boolean aStrictLessThanB = (Boolean) isLessThan.invoke(a, b, false);
        boolean bStrictLessThanA = (Boolean) isLessThan.invoke(b, a, false);
        assertFalse(aStrictLessThanB);
        assertFalse(bStrictLessThanA);
    }







}
