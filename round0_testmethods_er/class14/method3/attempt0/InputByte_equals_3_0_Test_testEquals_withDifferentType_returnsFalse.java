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

class InputByte_equals_3_0_Test_testEquals_withDifferentType_returnsFalse {

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
    void testEquals_withDifferentType_returnsFalse() throws Exception {
        InputByte ib = createInputByte((byte) 2);
        // different runtime class (String)
        assertFalse(ib.equals("some string"));
        // different runtime class (Object)
        assertFalse(ib.equals(new Object()));
        // reflection invocation with different type
        Method equalsMethod = InputByte.class.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        Object result = equalsMethod.invoke(ib, "another string");
        assertEquals(Boolean.FALSE, result);
    }



}
