package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

class ValuePatterns_hashCode_2_0_Test_testHashCode_isDeterministicAcrossInvocations {



    @Test
    void testHashCode_isDeterministicAcrossInvocations() throws Exception {
        ValuePatterns vp = new ValuePatterns(null, "deterministic");
        Method hashMethod = ValuePatterns.class.getDeclaredMethod("hashCode");
        hashMethod.setAccessible(true);
        int first = ((Integer) hashMethod.invoke(vp)).intValue();
        int second = ((Integer) hashMethod.invoke(vp)).intValue();
        assertEquals(first, second);
    }

}
