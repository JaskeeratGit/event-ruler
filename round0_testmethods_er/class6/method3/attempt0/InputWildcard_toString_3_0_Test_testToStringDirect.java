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

class InputWildcard_toString_3_0_Test_testToStringDirect {

    @Test
    void testToStringDirect() {
        InputWildcard w = new InputWildcard();
        String s = w.toString();
        assertNotNull(s, "toString() should not return null");
        assertEquals("Wildcard", s, "toString() should return the exact expected string");
    }


}
