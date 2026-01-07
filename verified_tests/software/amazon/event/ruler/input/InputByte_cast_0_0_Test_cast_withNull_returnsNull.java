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

public class InputByte_cast_0_0_Test_cast_withNull_returnsNull {


    @Test
    public void cast_withNull_returnsNull() {
        // casting null should return null (no exception)
        InputByte result = InputByte.cast(null);
        assertNull(result);
    }

}
