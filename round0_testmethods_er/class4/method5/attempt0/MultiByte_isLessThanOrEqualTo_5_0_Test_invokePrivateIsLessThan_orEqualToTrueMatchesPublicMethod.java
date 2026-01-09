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

class MultiByte_isLessThanOrEqualTo_5_0_Test_invokePrivateIsLessThan_orEqualToTrueMatchesPublicMethod {








    @Test
    void invokePrivateIsLessThan_orEqualToTrueMatchesPublicMethod() throws Exception {
        MultiByte a = new MultiByte((byte) 0x10, (byte) 0x20);
        MultiByte b = new MultiByte((byte) 0x10, (byte) 0x20, (byte) 0x30);
        Method isLessThan = MultiByte.class.getDeclaredMethod("isLessThan", MultiByte.class, boolean.class);
        isLessThan.setAccessible(true);
        // call private with orEqualTo == true and compare to public isLessThanOrEqualTo
        boolean privateResult = (Boolean) isLessThan.invoke(a, b, true);
        boolean publicResult = a.isLessThanOrEqualTo(b);
        assertEquals(privateResult, publicResult);
    }
}
