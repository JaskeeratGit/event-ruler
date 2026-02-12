package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for InputMultiByteSet.toString() and that getMultiBytes() returns an unmodifiable view.
 *
 * Notes:
 * - We use reflection to invoke a package-private constructor and package-private getMultiBytes()
 *   to avoid relying on them being public.
 * - This test uses JUnit 4 to remain compatible with environments that do not provide JUnit 5 on the classpath.
 */
public class InputMultiByteSet_toString_5_0_Test_testToStringWithImmutableInputAndUnmodifiableView {

    @Test
    public void testToStringWithImmutableInputAndUnmodifiableView() throws Exception {
        // Use Collections.singleton to remain Java 8 compatible (Set.of is Java 9+)
        Set<String> original = Collections.singleton("singleton");
        InputMultiByteSet instance = instantiateWithSet(original);

        // toString should match the original set's toString
        assertEquals(original.toString(), instance.toString());

        // getMultiBytes() should return an unmodifiable view; attempting to modify it throws
        Method getMultiBytes = InputMultiByteSet.class.getDeclaredMethod("getMultiBytes");
        getMultiBytes.setAccessible(true);
        @SuppressWarnings("unchecked")
        Set<Object> returnedSet = (Set<Object>) getMultiBytes.invoke(instance);

        try {
            returnedSet.add("newElement");
            fail("Expected an UnsupportedOperationException when attempting to modify the returned set");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }

    // Helper to instantiate InputMultiByteSet via reflection (constructor is package-private)
    private static InputMultiByteSet instantiateWithSet(Set<?> s) throws Exception {
        Constructor<InputMultiByteSet> ctor = InputMultiByteSet.class.getDeclaredConstructor(Set.class);
        ctor.setAccessible(true);
        // Use raw set via reflection to avoid compile-time generics mismatch
        @SuppressWarnings("unchecked")
        InputMultiByteSet instance = ctor.newInstance((Set) s);
        return instance;
    }
}
