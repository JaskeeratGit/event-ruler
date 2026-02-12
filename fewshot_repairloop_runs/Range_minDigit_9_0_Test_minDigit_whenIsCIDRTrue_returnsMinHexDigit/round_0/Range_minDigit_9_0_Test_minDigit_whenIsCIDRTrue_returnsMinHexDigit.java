package software.amazon.event.ruler;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;
import static software.amazon.event.ruler.Constants.BASE128_DIGITS;
import static software.amazon.event.ruler.Constants.HEX_DIGITS;
import static software.amazon.event.ruler.Constants.MAX_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MAX_NUM_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class Range_minDigit_9_0_Test_minDigit_whenIsCIDRTrue_returnsMinHexDigit {

    @Test
    public void minDigit_whenIsCIDRTrue_returnsMinHexDigit() throws Exception {
        byte[] bottom = new byte[] { 0x01 };
        byte[] top = new byte[] { 0x02 };
        Range r = new Range(bottom, false, top, false, true);
        byte expected = MIN_HEX_DIGIT;
        assertEquals(expected, r.minDigit());
    }

}
