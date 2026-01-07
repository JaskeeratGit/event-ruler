package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

class ValuePatterns_hashCode_2_0_Test_testHashCode_differentPatternsProduceDifferentHashCodes {




    @Test
    void testHashCode_differentPatternsProduceDifferentHashCodes() throws Exception {
        ValuePatterns vp1 = new ValuePatterns(null, "a");
        ValuePatterns vp2 = new ValuePatterns(null, "b");
        Method hashMethod = ValuePatterns.class.getDeclaredMethod("hashCode");
        hashMethod.setAccessible(true);
        int h1 = ((Integer) hashMethod.invoke(vp1)).intValue();
        int h2 = ((Integer) hashMethod.invoke(vp2)).intValue();
        // Very unlikely that two different short strings have same hashCode; assert they differ.
        assertNotEquals(h1, h2);
    }
}
