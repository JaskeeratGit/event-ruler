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

class MultiByte_isLessThanOrEqualTo_5_0_Test_prefixShorter_isLessThanOrEqualTo_true_and_strict_true {


    @Test
    void prefixShorter_isLessThanOrEqualTo_true_and_strict_true() throws Exception {
        MultiByte shorter = new MultiByte((byte) 0x01);
        MultiByte longer = new MultiByte((byte) 0x01, (byte) 0x02);
        // shorter is a prefix of longer -> isLessThanOrEqualTo returns true
        assertTrue(shorter.isLessThanOrEqualTo(longer));
        // private isLessThan with orEqualTo == false should return true (strictly less)
        Method isLessThan = MultiByte.class.getDeclaredMethod("isLessThan", MultiByte.class, boolean.class);
        isLessThan.setAccessible(true);
        boolean strict = (Boolean) isLessThan.invoke(shorter, longer, false);
        assertTrue(strict);
    }






}
