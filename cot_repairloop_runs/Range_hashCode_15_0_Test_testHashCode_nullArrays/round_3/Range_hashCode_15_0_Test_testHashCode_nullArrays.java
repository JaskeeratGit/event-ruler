package software.amazon.event.ruler;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Fixed unit test for Range.hashCode() when bottom and top arrays are intended to be null.
 *
 * The original test attempted to obtain the superclass (Patterns) hashCode by calling
 * Patterns.class.getDeclaredMethod("hashCode").invoke(range), but that still performs
 * virtual dispatch and ends up calling Range.hashCode() again. To get the actual
 * superclass implementation we use MethodHandles to perform an "invokespecial"
 * (i.e. call the Patterns.hashCode implementation on the Range instance).
 *
 * This test reflectively reads the actual fields used by Range.hashCode() so that
 * it works regardless of whether the constructor converts null arrays to empty arrays.
 */
public class Range_hashCode_15_0_Test_testHashCode_nullArrays {

    @Test
    public void testHashCode_nullArrays() throws Throwable {
        // create Range (attempting to pass null arrays)
        Range range = new Range(null, true, null, false, true);

        // obtain the Method object for Patterns.hashCode()
        Method patternsHashMethod = Patterns.class.getDeclaredMethod("hashCode");
        patternsHashMethod.setAccessible(true);

        // Use MethodHandles to invoke the superclass (Patterns) implementation on the Range instance.
        // This performs the equivalent of `super.hashCode()` for the given instance.
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle superHashHandle;
        try {
            // Restrict the lookup to the Patterns class to allow unreflectSpecial in some JVMs
            lookup = lookup.in(Patterns.class);
        } catch (IllegalArgumentException ignored) {
            // Some JVMs may not allow changing the lookup; ignore and try unreflectSpecial with the original lookup.
        }
        superHashHandle = lookup.unreflectSpecial(patternsHashMethod, Patterns.class).bindTo(range);
        int superHash = (int) superHashHandle.invoke();

        // reflectively retrieve the actual fields used in Range.hashCode(),
        // so we compute expected using the same runtime values the method uses.
        Field bottomField = Range.class.getDeclaredField("bottom");
        bottomField.setAccessible(true);
        byte[] bottom = (byte[]) bottomField.get(range);

        Field openBottomField = Range.class.getDeclaredField("openBottom");
        openBottomField.setAccessible(true);
        boolean openBottom = openBottomField.getBoolean(range);

        Field topField = Range.class.getDeclaredField("top");
        topField.setAccessible(true);
        byte[] top = (byte[]) topField.get(range);

        Field openTopField = Range.class.getDeclaredField("openTop");
        openTopField.setAccessible(true);
        boolean openTop = openTopField.getBoolean(range);

        // compute expected hash following the same algorithm as Range.hashCode()
        int expected = superHash;
        expected = 31 * expected + Arrays.hashCode(bottom);
        expected = 31 * expected + Boolean.hashCode(openBottom);
        expected = 31 * expected + Arrays.hashCode(top);
        expected = 31 * expected + Boolean.hashCode(openTop);

        assertEquals(expected, range.hashCode());
    }
}
