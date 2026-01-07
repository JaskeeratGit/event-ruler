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

public class InputByte_cast_0_0_Test_cast_withInputByte_returnsSameAndByteValue {

    @Test
    public void cast_withInputByte_returnsSameAndByteValue() throws Exception {
        // create InputByte instance via reflection (constructor may not be public)
        Constructor<InputByte> ctor = InputByte.class.getDeclaredConstructor(byte.class);
        ctor.setAccessible(true);
        byte expected = (byte) 0x41;
        InputByte inputByte = ctor.newInstance(expected);
        // use the static cast method
        InputByte casted = InputByte.cast(inputByte);
        // same instance returned
        assertSame(inputByte, casted);
        // verify internal private field 'b' via reflection
        Field bField = InputByte.class.getDeclaredField("b");
        bField.setAccessible(true);
        byte actual = bField.getByte(casted);
        assertEquals(expected, actual);
        // also verify public getter returns same value (if available)
        assertEquals(expected, casted.getByte());
    }


}
