package software.amazon.event.ruler.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.nio.charset.StandardCharsets;
import static software.amazon.event.ruler.input.InputCharacterType.BYTE;

public class InputByte_cast_0_0_Test_cast_withDifferentInputCharacter_throwsClassCastException {



    @Test
    public void cast_withDifferentInputCharacter_throwsClassCastException() {
        // create an anonymous InputCharacter that is NOT an InputByte
        InputCharacter other = new InputCharacter() {

            @Override
            public InputCharacterType getType() {
                return null;
            }
        };
        assertThrows(ClassCastException.class, () -> InputByte.cast(other));
    }
}
