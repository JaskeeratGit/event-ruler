package software.amazon.event.ruler;

import static software.amazon.event.ruler.Constants.MIN_HEX_DIGIT;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
