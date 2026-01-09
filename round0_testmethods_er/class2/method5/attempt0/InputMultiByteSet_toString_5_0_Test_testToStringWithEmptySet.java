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

class InputMultiByteSet_toString_5_0_Test_testToStringWithEmptySet {

    @Test
    void testToStringWithEmptySet() throws Exception {
        Set<String> original = new HashSet<>();
        InputMultiByteSet instance = instantiateWithSet(original);
        // toString should reflect the underlying set's toString
        assertEquals("[]", instance.toString());
        assertEquals(original.toString(), instance.toString());
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
