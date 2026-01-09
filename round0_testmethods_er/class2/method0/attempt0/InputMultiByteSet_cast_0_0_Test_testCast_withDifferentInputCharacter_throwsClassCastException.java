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

class InputMultiByteSet_cast_0_0_Test_testCast_withDifferentInputCharacter_throwsClassCastException {


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


}
