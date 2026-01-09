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

class InputByte_equals_3_0_Test {

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
    void testEquals_withNull_returnsFalse_and_reflectionInvoke() throws Exception {
        InputByte ib = createInputByte((byte) 1);
        // direct invocation
        assertFalse(ib.equals(null), "equals should return false when compared with null");
        // reflection invocation of equals(Object)
        Method equalsMethod = InputByte.class.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        Object result = equalsMethod.invoke(ib, new Object[] { null });
        assertEquals(Boolean.FALSE, result, "reflection-invoked equals should return false for null");
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

    @Test
    void testEquals_withDifferentByteValue_returnsFalse() throws Exception {
        InputByte ib1 = createInputByte((byte) 10);
        InputByte ib2 = createInputByte((byte) 11);
        assertFalse(ib1.equals(ib2));
        assertFalse(ib2.equals(ib1));
        // also test via reflection
        Method equalsMethod = InputByte.class.getDeclaredMethod("equals", Object.class);
        equalsMethod.setAccessible(true);
        assertEquals(Boolean.FALSE, equalsMethod.invoke(ib1, ib2));
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

    @Test
    void testEquals_withSubclassInstances_returnsFalse_dueToGetClassCheck() throws Exception {
        InputByte parent = createInputByte((byte) 7);
        SubInputByte child = new SubInputByte((byte) 7);
        // getClass() comparison should make these not equal even though byte value is same
        assertFalse(parent.equals(child));
        assertFalse(child.equals(parent));
    }
}
