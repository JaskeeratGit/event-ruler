package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

class ValuePatterns_hashCode_2_0_Test_testHashCode_withNullPatternAndNullType_returnsZero {

    @Test
    void testHashCode_withNullPatternAndNullType_returnsZero() throws Exception {
        // type = null, pattern = null -> super.hashCode() == 0 and pattern part == 0
        ValuePatterns vp = new ValuePatterns(null, null);
        Method hashMethod = ValuePatterns.class.getDeclaredMethod("hashCode");
        hashMethod.setAccessible(true);
        Object result = hashMethod.invoke(vp);
        assertTrue(result instanceof Integer);
        assertEquals(0, ((Integer) result).intValue());
    }



}
