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

class InputByte_equals_3_0_Test_testEquals_withSubclassInstances_returnsFalse_dueToGetClassCheck {

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
    void testEquals_withSubclassInstances_returnsFalse_dueToGetClassCheck() throws Exception {
        InputByte parent = createInputByte((byte) 7);
        SubInputByte child = new SubInputByte((byte) 7);
        // getClass() comparison should make these not equal even though byte value is same
        assertFalse(parent.equals(child));
        assertFalse(child.equals(parent));
    }
}
