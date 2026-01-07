package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

class InputWildcard_toString_3_0_Test_testToStringReflectiveInvocation {


    @Test
    void testToStringReflectiveInvocation() throws Exception {
        Class<?> clazz = Class.forName("software.amazon.event.ruler.input.InputWildcard");
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        // constructor is package-private; ensure we can instantiate via reflection
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        Method m = clazz.getDeclaredMethod("toString");
        m.setAccessible(true);
        Object result = m.invoke(instance);
        assertTrue(result instanceof String, "Reflective invocation should return a String");
        assertEquals("Wildcard", result, "Reflective toString() should return the exact expected string");
    }

}
