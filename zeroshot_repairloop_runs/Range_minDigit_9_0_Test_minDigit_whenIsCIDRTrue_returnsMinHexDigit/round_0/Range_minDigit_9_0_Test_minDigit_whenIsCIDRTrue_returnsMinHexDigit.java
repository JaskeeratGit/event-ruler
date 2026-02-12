package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;

public class Range_minDigit_9_0_Test_minDigit_whenIsCIDRTrue_returnsMinHexDigit {

    @Test
    public void minDigit_whenIsCIDRTrue_returnsMinHexDigit() {
        byte[] bottom = new byte[] { 0x01 };
        byte[] top = new byte[] { 0x02 };
        Range r = new Range(bottom, false, top, false, true);
        assertEquals(MIN_HEX_DIGIT, r.minDigit());
    }
}
