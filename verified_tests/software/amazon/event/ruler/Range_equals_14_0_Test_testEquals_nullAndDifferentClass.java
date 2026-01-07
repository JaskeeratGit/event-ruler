package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

public class Range_equals_14_0_Test_testEquals_nullAndDifferentClass {

    private static byte[] b(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }


    @Test
    public void testEquals_nullAndDifferentClass() {
        Range r = new Range(b("10"), false, b("20"), true, false);
        assertFalse(r.equals(null));
        assertFalse(r.equals(new Object()));
    }





}
