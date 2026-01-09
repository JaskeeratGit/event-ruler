package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

public class ValuePatterns_toString_3_0_Test_comparableNumber_toIntVals_and_privateConstructor_viaReflection {



    @Test
    void comparableNumber_toIntVals_and_privateConstructor_viaReflection() throws Exception {
        // Invoke private constructor of ComparableNumber via reflection to satisfy reflective use
        Constructor<ComparableNumber> ctor = ComparableNumber.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        ComparableNumber inst = ctor.newInstance();
        assertNotNull(inst);
        // Invoke toIntVals(String) reflectively
        Method toIntVals = ComparableNumber.class.getDeclaredMethod("toIntVals", String.class);
        toIntVals.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Integer> result = (List<Integer>) toIntVals.invoke(null, "A1");
        // 'A' -> 65, '1' -> 49
        assertEquals(Arrays.asList(65, 49), result);
    }
}
