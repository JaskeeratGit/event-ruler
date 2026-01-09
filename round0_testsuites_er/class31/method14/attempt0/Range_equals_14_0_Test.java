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

public class Range_equals_14_0_Test {

    private static byte[] b(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    @Test
    public void testEquals_sameReference() {
        Range r = new Range(b("10"), false, b("20"), true, false);
        assertTrue(r.equals(r));
    }

    @Test
    public void testEquals_nullAndDifferentClass() {
        Range r = new Range(b("10"), false, b("20"), true, false);
        assertFalse(r.equals(null));
        assertFalse(r.equals(new Object()));
    }

    @Test
    public void testEquals_equalDifferentInstances() {
        byte[] bottom = b("100");
        byte[] top = b("200");
        Range r1 = new Range(bottom, false, top, true, false);
        Range r2 = new Range(bottom.clone(), false, top.clone(), true, false);
        assertNotSame(r1, r2);
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));
    }

    @Test
    public void testEquals_bottomDifferent() {
        byte[] bottom = b("100");
        byte[] top = b("200");
        Range r1 = new Range(bottom, false, top, true, false);
        Range rDiffBottom = new Range(b("101"), false, top, true, false);
        assertFalse(r1.equals(rDiffBottom));
    }

    @Test
    public void testEquals_topDifferent() {
        byte[] bottom = b("100");
        byte[] top = b("200");
        Range r1 = new Range(bottom, false, top, true, false);
        Range rDiffTop = new Range(bottom, false, b("201"), true, false);
        assertFalse(r1.equals(rDiffTop));
    }

    @Test
    public void testEquals_openFlagsDifferent() {
        byte[] bottom = b("100");
        byte[] top = b("200");
        Range r = new Range(bottom, false, top, true, false);
        Range diffOpenBottom = new Range(bottom, true, top, true, false);
        assertFalse(r.equals(diffOpenBottom));
        Range diffOpenTop = new Range(bottom, false, top, false, false);
        assertFalse(r.equals(diffOpenTop));
    }

    @Test
    public void testEquals_superEqualsFalseUsingReflection() throws Exception {
        byte[] bottom = b("100");
        byte[] top = b("200");
        Range rNormal = new Range(bottom, false, top, true, false);
        Range rToCorrupt = new Range(bottom.clone(), false, top.clone(), true, false);
        // Access inherited private final field 'type' in Patterns and set it to null on rToCorrupt
        Field typeField = Patterns.class.getDeclaredField("type");
        typeField.setAccessible(true);
        // set to null to force Patterns.equals to return false
        typeField.set(rToCorrupt, null);
        // sanity: classes are same, but super.equals should now be false -> Range.equals returns false
        assertEquals(rNormal.getClass(), rToCorrupt.getClass());
        assertFalse(rNormal.equals(rToCorrupt));
    }
}
