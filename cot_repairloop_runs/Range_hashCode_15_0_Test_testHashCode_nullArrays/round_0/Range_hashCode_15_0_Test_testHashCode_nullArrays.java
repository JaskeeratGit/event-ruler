package software.amazon.event.ruler;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Range_hashCode_15_0_Test_testHashCode_nullArrays {

    @Test
    void testHashCode_nullArrays() throws Exception {
        // create Range with null arrays for bottom and top
        Range range = new Range(null, true, null, false, true);

        // obtain Patterns.hashCode() (superclass implementation) via reflection
        Method patternsHash = Patterns.class.getDeclaredMethod("hashCode");
        patternsHash.setAccessible(true);
        int superHash = (Integer) patternsHash.invoke(range);

        // build expected hash using the same algorithm as Range.hashCode(),
        // ensuring we call the same Arrays.hashCode overload by casting null to byte[]
        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode((byte[]) null);
        expected = 31 * expected + Boolean.hashCode(true);
        expected = 31 * expected + Arrays.hashCode((byte[]) null);
        expected = 31 * expected + Boolean.hashCode(false);

        assertEquals(expected, range.hashCode());
    }
}
