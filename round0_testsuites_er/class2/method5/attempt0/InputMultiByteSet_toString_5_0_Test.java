package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.Objects;
import static software.amazon.event.ruler.input.InputCharacterType.MULTI_BYTE_SET;

class InputMultiByteSet_toString_5_0_Test {

    @Test
    void testToStringWithEmptySet() throws Exception {
        Set<String> original = new HashSet<>();
        InputMultiByteSet instance = instantiateWithSet(original);
        // toString should reflect the underlying set's toString
        assertEquals("[]", instance.toString());
        assertEquals(original.toString(), instance.toString());
    }

    @Test
    void testToStringReflectsMutableUnderlyingSetChanges() throws Exception {
        Set<String> original = new HashSet<>();
        original.add("alpha");
        original.add("beta");
        InputMultiByteSet instance = instantiateWithSet(original);
        // initial equality
        assertEquals(original.toString(), instance.toString());
        // modify the original backing set -> should be visible through toString()
        original.add("gamma");
        assertEquals(original.toString(), instance.toString());
        // also verify getMultiBytes() (via reflection) reflects the same content
        Method getMultiBytes = InputMultiByteSet.class.getDeclaredMethod("getMultiBytes");
        Object returned = getMultiBytes.invoke(instance);
        assertTrue(returned instanceof Set);
        @SuppressWarnings("unchecked")
        Set<Object> returnedSet = (Set<Object>) returned;
        assertEquals(original.toString(), returnedSet.toString());
    }

    @Test
    void testToStringWithImmutableInputAndUnmodifiableView() throws Exception {
        Set<String> original = Set.of("singleton");
        InputMultiByteSet instance = instantiateWithSet(original);
        // toString should match the original set's toString
        assertEquals(original.toString(), instance.toString());
        // getMultiBytes() should return an unmodifiable view; attempting to modify it throws
        Method getMultiBytes = InputMultiByteSet.class.getDeclaredMethod("getMultiBytes");
        @SuppressWarnings("unchecked")
        Set<Object> returnedSet = (Set<Object>) getMultiBytes.invoke(instance);
        assertThrows(UnsupportedOperationException.class, () -> returnedSet.add("newElement"));
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
