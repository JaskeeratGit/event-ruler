package software.amazon.event.ruler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import static software.amazon.event.ruler.Constants.MIN_NUM_DIGIT;

public class Range_minDigitTest {

    @Test
    public void minDigit_whenIsCIDRTrue_returnsMinHexDigit() {
        byte[] bottom = new byte[] { 0x01 };
        byte[] top = new byte[] { 0x02 };
        // last boolean indicates isCIDR = true
        Range r = new Range(bottom, false, top, false, true);
        assertEquals(MIN_HEX_DIGIT, r.minDigit());
    }

    @Test
    public void minDigit_whenIsCIDRFalse_returnsMinNumDigit() {
        byte[] bottom = new byte[] { 0x01 };
        byte[] top = new byte[] { 0x02 };
        // last boolean indicates isCIDR = false
        Range r = new Range(bottom, false, top, false, false);
        assertEquals(MIN_NUM_DIGIT, r.minDigit());
    }
}
