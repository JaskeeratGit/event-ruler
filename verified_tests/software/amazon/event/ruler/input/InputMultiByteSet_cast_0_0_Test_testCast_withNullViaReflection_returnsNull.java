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

class InputMultiByteSet_cast_0_0_Test_testCast_withNullViaReflection_returnsNull {



    @Test
    void testCast_withNullViaReflection_returnsNull() throws Exception {
        Method castMethod = InputMultiByteSet.class.getDeclaredMethod("cast", InputCharacter.class);
        castMethod.setAccessible(true);
        Object result = castMethod.invoke(null, (Object) null);
        assertNull(result, "casting null should return null");
    }

}
