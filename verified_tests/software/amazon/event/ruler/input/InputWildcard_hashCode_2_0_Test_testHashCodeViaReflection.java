package software.amazon.event.ruler.input;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static software.amazon.event.ruler.input.InputCharacterType.WILDCARD;

/**
 * JUnit 5 tests for InputWildcard.hashCode()
 */
public class InputWildcard_hashCode_2_0_Test_testHashCodeViaReflection {


    @Test
    public void testHashCodeViaReflection() throws Exception {
        InputWildcard instance = new InputWildcard();
        // Use reflection (getDeclaredMethod + setAccessible) to invoke hashCode
        Method hashCodeMethod = InputWildcard.class.getDeclaredMethod("hashCode");
        hashCodeMethod.setAccessible(true);
        Object result = hashCodeMethod.invoke(instance);
        assertNotNull(result, "Reflection invocation should not return null");
        assertTrue(result instanceof Integer, "hashCode() returns an int wrapped as Integer via reflection");
        assertEquals(Integer.valueOf(InputWildcard.class.hashCode()), result, "Reflected hashCode result must match InputWildcard.class.hashCode()");
    }
}
