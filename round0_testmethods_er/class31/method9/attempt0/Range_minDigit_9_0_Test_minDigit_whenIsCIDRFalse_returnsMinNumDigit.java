package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import software.amazon.event.ruler.Range;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

public class Range_minDigit_9_0_Test_minDigit_whenIsCIDRFalse_returnsMinNumDigit {

    private static byte getConstantByte(String name) throws Exception {
        Class<?> constantsClass = Class.forName("software.amazon.event.ruler.Constants");
        Field f = constantsClass.getDeclaredField(name);
        f.setAccessible(true);
        return f.getByte(null);
    }


    @Test
    public void minDigit_whenIsCIDRFalse_returnsMinNumDigit() throws Exception {
        byte[] bottom = new byte[] { 0x00 };
        byte[] top = new byte[] { 0x7F };
        Range r = new Range(bottom, true, top, true, false);
        byte expected = getConstantByte("MIN_NUM_DIGIT");
        assertEquals(expected, r.minDigit());
    }

}
