package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

class Range_toString_16_0_Test {

    @Test
    void testToString_whenIsCIDR_andCopyConstructorProducesEqualString() throws Exception {
        // prepare input
        byte[] bottom = "abc".getBytes(StandardCharsets.UTF_8);
        byte[] top = "def".getBytes(StandardCharsets.UTF_8);
        boolean openBottom = true;
        boolean openTop = false;
        boolean isCIDR = true;
        // obtain package-private constructor Range(byte[], boolean, byte[], boolean, boolean)
        Constructor<Range> ctor = Range.class.getDeclaredConstructor(byte[].class, boolean.class, byte[].class, boolean.class, boolean.class);
        ctor.setAccessible(true);
        // create instance via reflection
        Range range = ctor.newInstance(bottom, openBottom, top, openTop, isCIDR);
        // call toString and assert format for CIDR branch
        String actual = range.toString();
        String expectedPrefix = new String(bottom, StandardCharsets.UTF_8) + '/' + new String(top, StandardCharsets.UTF_8) + ':' + openBottom + '/' + openTop + ':' + isCIDR + " (";
        assertTrue(actual.startsWith(expectedPrefix), "toString should start with the expected CIDR prefix");
        assertTrue(actual.contains(" (T:"), "toString should include the superclass Patterns.toString() fragment starting with 'T:'");
        assertTrue(actual.endsWith(")"), "toString should end with a closing parenthesis for the super.toString() part");
        // obtain private copy constructor Range(Range) and create a copy
        Constructor<Range> copyCtor = Range.class.getDeclaredConstructor(Range.class);
        copyCtor.setAccessible(true);
        Range copy = copyCtor.newInstance(range);
        // ensure copy's toString equals original's toString (covering private constructor path)
        assertEquals(actual, copy.toString(), "Copy constructed Range should produce identical toString output");
    }

    @Test
    void testToString_whenNotCIDR_usesComparableNumberToIntVals() throws Exception {
        // prepare input
        byte[] bottom = "12".getBytes(StandardCharsets.UTF_8);
        byte[] top = "3".getBytes(StandardCharsets.UTF_8);
        boolean openBottom = false;
        boolean openTop = true;
        boolean isCIDR = false;
        // create Range via package-private constructor reflectively
        Constructor<Range> ctor = Range.class.getDeclaredConstructor(byte[].class, boolean.class, byte[].class, boolean.class, boolean.class);
        ctor.setAccessible(true);
        Range range = ctor.newInstance(bottom, openBottom, top, openTop, isCIDR);
        // build expected prefix using ComparableNumber.toIntVals (package-private, accessible in same package)
        List<Integer> bottomVals = ComparableNumber.toIntVals(new String(bottom, StandardCharsets.UTF_8));
        List<Integer> topVals = ComparableNumber.toIntVals(new String(top, StandardCharsets.UTF_8));
        String expectedPrefix = bottomVals.toString() + '/' + topVals.toString() + ':' + openBottom + '/' + openTop + ':' + isCIDR + " (";
        String actual = range.toString();
        // assertions: starts with expected prefix, contains superclass marker, and ends with closing paren
        assertTrue(actual.startsWith(expectedPrefix), "toString should start with ComparableNumber int values for non-CIDR branch");
        assertTrue(actual.contains(" (T:"), "toString should include the superclass Patterns.toString() fragment starting with 'T:'");
        assertTrue(actual.endsWith(")"), "toString should end with a closing parenthesis for the super.toString() part");
    }
}
