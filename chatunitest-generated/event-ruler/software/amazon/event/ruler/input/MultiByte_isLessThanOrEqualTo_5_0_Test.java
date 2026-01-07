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

class MultiByte_isLessThanOrEqualTo_5_0_Test {

    @Test
    void equalBytes_areLessThanOrEqualTo_true_and_strictFalse() throws Exception {
        MultiByte a = new MultiByte((byte) 0x01, (byte) 0x02);
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x02);
        // public API: orEqualTo == true
        assertTrue(a.isLessThanOrEqualTo(b));
        assertTrue(b.isLessThanOrEqualTo(a));
        // private isLessThan with orEqualTo == false should return false for equal arrays
        Method isLessThan = MultiByte.class.getDeclaredMethod("isLessThan", MultiByte.class, boolean.class);
        isLessThan.setAccessible(true);
        boolean aStrictLessThanB = (Boolean) isLessThan.invoke(a, b, false);
        boolean bStrictLessThanA = (Boolean) isLessThan.invoke(b, a, false);
        assertFalse(aStrictLessThanB);
        assertFalse(bStrictLessThanA);
    }

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

    @Test
    void prefixLonger_isLessThanOrEqualTo_false_and_strict_false() {
        MultiByte longer = new MultiByte((byte) 0x01, (byte) 0x02);
        MultiByte shorter = new MultiByte((byte) 0x01);
        // longer has shorter as prefix -> longer > shorter so should be false
        assertFalse(longer.isLessThanOrEqualTo(shorter));
    }

    @Test
    void compareByUnsignedValue_firstByteDifference() {
        // unsigned 0
        MultiByte low = new MultiByte((byte) 0x00);
        // unsigned 255
        MultiByte high = new MultiByte((byte) 0xFF);
        assertTrue(low.isLessThanOrEqualTo(high));
        assertFalse(high.isLessThanOrEqualTo(low));
    }

    @Test
    void compareByUnsignedValue_laterByteDifference() {
        // second byte unsigned 128
        MultiByte a = new MultiByte((byte) 0x01, (byte) 0x80);
        // second byte unsigned 127
        MultiByte b = new MultiByte((byte) 0x01, (byte) 0x7F);
        assertFalse(a.isLessThanOrEqualTo(b));
        assertTrue(b.isLessThanOrEqualTo(a));
    }

    @Test
    void negativeByteValues_unsignedComparison_behavior() {
        // 255 unsigned
        MultiByte ff = new MultiByte((byte) 0xFF);
        // 128 unsigned
        MultiByte eighty = new MultiByte((byte) 0x80);
        // 255 > 128 so ff <= eighty is false
        assertFalse(ff.isLessThanOrEqualTo(eighty));
        assertTrue(eighty.isLessThanOrEqualTo(ff));
    }

    @Test
    void constructor_noBytes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            // constructor is package-private; test is in same package so it can call it
            // zero-length varargs
            new MultiByte();
        });
    }

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
