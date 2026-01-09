package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.StandardCharsets;
import static software.amazon.event.ruler.input.InputCharacterType.BYTE;

class InputByte_equals_3_0_Test_testEquals_withSameByteValue_returnsTrue_and_symmetric {

    // Helper to instantiate InputByte via reflection (constructor is package-private)
    private InputByte createInputByte(byte b) throws Exception {
        Constructor<InputByte> ctor = InputByte.class.getDeclaredConstructor(byte.class);
        ctor.setAccessible(true);
        return ctor.newInstance(b);
    }

    // A subclass to test getClass() equality behavior (same package so can call package-private super ctor)
    static class SubInputByte extends InputByte {

        SubInputByte(byte b) {
            super(b);
        }
    }




    @Test
    void testEquals_withSameByteValue_returnsTrue_and_symmetric() throws Exception {
        InputByte ib1 = createInputByte((byte) 42);
        InputByte ib2 = createInputByte((byte) 42);
        // distinct instances but same byte -> equals should be true
        assertTrue(ib1.equals(ib2));
        assertTrue(ib2.equals(ib1));
        // reflexive
        assertTrue(ib1.equals(ib1));
        // reflection invocation
        Method equalsMethod = InputByte.class.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        assertEquals(Boolean.TRUE, equalsMethod.invoke(ib1, ib2));
    }

}
