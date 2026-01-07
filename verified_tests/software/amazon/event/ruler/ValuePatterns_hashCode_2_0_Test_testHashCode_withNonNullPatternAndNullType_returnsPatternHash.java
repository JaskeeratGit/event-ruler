package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

class ValuePatterns_hashCode_2_0_Test_testHashCode_withNonNullPatternAndNullType_returnsPatternHash {


    @Test
    void testHashCode_withNonNullPatternAndNullType_returnsPatternHash() throws Exception {
        // type = null -> super.hashCode() == 0, so result should be pattern.hashCode()
        String pattern = "test-pattern";
        ValuePatterns vp = new ValuePatterns(null, pattern);
        Method hashMethod = ValuePatterns.class.getDeclaredMethod("hashCode");
        hashMethod.setAccessible(true);
        Object result = hashMethod.invoke(vp);
        int expected = 31 * 0 + pattern.hashCode();
        assertEquals(expected, ((Integer) result).intValue());
    }


}
