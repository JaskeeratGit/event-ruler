package software.amazon.event.ruler.input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;
import java.util.Set;
import static software.amazon.event.ruler.input.InputCharacterType.MULTI_BYTE_SET;

class InputMultiByteSet_cast_0_0_Test {

    @Test
    void testCast_withInputMultiByteSet_returnsSameInstance() {
        InputMultiByteSet original = new InputMultiByteSet(Collections.<software.amazon.event.ruler.input.MultiByte>emptySet());
        // upcast
        InputCharacter asCharacter = original;
        InputMultiByteSet result = InputMultiByteSet.cast(asCharacter);
        assertSame(original, result, "cast should return the same instance when the input is an InputMultiByteSet");
    }

    @Test
    void testCast_withDifferentInputCharacter_throwsClassCastException() {
        InputCharacter other = new InputCharacter() {

            @Override
            public InputCharacterType getType() {
                return null;
            }
        };
        assertThrows(ClassCastException.class, () -> InputMultiByteSet.cast(other), "casting a non-InputMultiByteSet InputCharacter should throw ClassCastException");
    }

    @Test
    void testCast_withNullViaReflection_returnsNull() throws Exception {
        Method castMethod = InputMultiByteSet.class.getDeclaredMethod("cast", InputCharacter.class);
        castMethod.setAccessible(true);
        Object result = castMethod.invoke(null, (Object) null);
        assertNull(result, "casting null should return null");
    }

    @Test
    void testCast_withDifferentInputCharacterViaReflection_throwsInvocationTargetExceptionWithCauseClassCastException() throws Exception {
        InputCharacter other = new InputCharacter() {

            @Override
            public InputCharacterType getType() {
                return null;
            }
        };
        Method castMethod = InputMultiByteSet.class.getDeclaredMethod("cast", InputCharacter.class);
        castMethod.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> castMethod.invoke(null, other), "reflective invocation should wrap the ClassCastException in InvocationTargetException");
        assertNotNull(ite.getCause());
        assertTrue(ite.getCause() instanceof ClassCastException, "the cause should be ClassCastException");
    }
}
