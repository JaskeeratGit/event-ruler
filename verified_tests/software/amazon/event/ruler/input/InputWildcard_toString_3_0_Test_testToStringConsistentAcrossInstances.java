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

class InputWildcard_toString_3_0_Test_testToStringConsistentAcrossInstances {



    @Test
    void testToStringConsistentAcrossInstances() {
        InputWildcard a = new InputWildcard();
        InputWildcard b = new InputWildcard();
        String sa = a.toString();
        String sb = b.toString();
        assertEquals(sa, sb, "toString() should be consistent across instances");
        assertEquals("Wildcard", sa);
        assertFalse(sa.isEmpty(), "toString() result should not be empty");
        assertEquals(8, sa.length(), "Expected length of \"Wildcard\" is 8");
    }
}
