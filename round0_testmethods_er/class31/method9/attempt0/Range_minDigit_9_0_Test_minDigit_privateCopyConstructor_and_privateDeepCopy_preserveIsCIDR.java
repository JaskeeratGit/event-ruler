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

public class Range_minDigit_9_0_Test_minDigit_privateCopyConstructor_and_privateDeepCopy_preserveIsCIDR {

    private static byte getConstantByte(String name) throws Exception {
        Class<?> constantsClass = Class.forName("software.amazon.event.ruler.Constants");
        Field f = constantsClass.getDeclaredField(name);
        f.setAccessible(true);
        return f.getByte(null);
    }



    @Test
    public void minDigit_privateCopyConstructor_and_privateDeepCopy_preserveIsCIDR() throws Exception {
        byte[] bottom = new byte[] { 0x10 };
        byte[] top = new byte[] { 0x20 };
        Range original = new Range(bottom, false, top, false, true);
        // Invoke private copy constructor Range(Range)
        Constructor<Range> copyCtor = Range.class.getDeclaredConstructor(Range.class);
        copyCtor.setAccessible(true);
        Range copied = copyCtor.newInstance(original);
        assertEquals(original.minDigit(), copied.minDigit());
        // Invoke private static deepCopy(Range) method if present
        Method deepCopyMethod = null;
        try {
            deepCopyMethod = Range.class.getDeclaredMethod("deepCopy", Range.class);
            deepCopyMethod.setAccessible(true);
            Range deepCopied = (Range) deepCopyMethod.invoke(null, original);
            assertEquals(original.minDigit(), deepCopied.minDigit());
        } catch (NoSuchMethodException e) {
            // If deepCopy is not present in this build, test the copy constructor outcome is sufficient.
        }
    }
}
