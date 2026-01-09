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

class InputMultiByteSet_cast_0_0_Test_testCast_withInputMultiByteSet_returnsSameInstance {

    @Test
    void testCast_withInputMultiByteSet_returnsSameInstance() {
        InputMultiByteSet original = new InputMultiByteSet(Collections.<software.amazon.event.ruler.input.MultiByte>emptySet());
        // upcast
        InputCharacter asCharacter = original;
        InputMultiByteSet result = InputMultiByteSet.cast(asCharacter);
        assertSame(original, result, "cast should return the same instance when the input is an InputMultiByteSet");
    }



}
