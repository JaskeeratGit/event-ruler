package software.amazon.event.ruler.input;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import static software.amazon.event.ruler.input.DefaultParser.NINE_BYTE;
import static software.amazon.event.ruler.input.DefaultParser.ZERO_BYTE;

class MultiByte_isLessThanOrEqualTo_5_0_Test_constructor_noBytes_throwsIllegalArgumentException {







    @Test
    void constructor_noBytes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            // constructor is package-private; test is in same package so it can call it
            // zero-length varargs
            new MultiByte();
        });
    }

}
