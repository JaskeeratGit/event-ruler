package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_existencePatterns_23_0_Test_testExistsByteStringConstant {



    @Test
    public void testExistsByteStringConstant() throws Exception {
        Field f = Patterns.class.getField("EXISTS_BYTE_STRING");
        Object val = f.get(null);
        assertEquals("N", val, "EXISTS_BYTE_STRING should be \"N\"");
    }

}
