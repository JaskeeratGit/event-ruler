package software.amazon.event.ruler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Range_hashCode_15_0_Test_testHashCode_nullArrays {


    @Test
    void testHashCode_nullArrays() throws Exception {
        Range range = new Range(null, true, null, false, true);
        Method patternsHash = Patterns.class.getDeclaredMethod("hashCode");
        patternsHash.setAccessible(true);

        // Use a trusted Lookup to perform an invokespecial (call to the superclass implementation)
        Field implLookup = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
        implLookup.setAccessible(true);
        MethodHandles.Lookup trusted = (MethodHandles.Lookup) implLookup.get(null);

        MethodHandle mh = trusted.unreflectSpecial(patternsHash, Range.class).bindTo(range);
        int superHash = (int) mh.invokeWithArguments();

        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode(range.bottom);
        expected = 31 * expected + Boolean.hashCode(true);
        expected = 31 * expected + Arrays.hashCode(range.top);
        expected = 31 * expected + Boolean.hashCode(false);
        assertEquals(expected, range.hashCode());
    }


}
