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

public class Range_minDigit_9_0_Test_minDigit_whenIsCIDRTrue_returnsMinHexDigit {

    private static byte getConstantByte(String name) throws Exception {
        Class<?> constantsClass = Class.forName("software.amazon.event.ruler.Constants");
        Field f = constantsClass.getDeclaredField(name);
        f.setAccessible(true);
        return f.getByte(null);
    }

    @Test
    public void minDigit_whenIsCIDRTrue_returnsMinHexDigit() throws Exception {
        byte[] bottom = new byte[] { 0x01 };
        byte[] top = new byte[] { 0x02 };
        Constructor<Range> ctor = Range.class.getDeclaredConstructor(byte[].class, boolean.class, byte[].class, boolean.class, boolean.class);
        ctor.setAccessible(true);
        Range r = ctor.newInstance(bottom, false, top, false, true);
        byte expected = getConstantByte("MIN_HEX_DIGIT");
        assertEquals(expected, r.minDigit());
    }


}
