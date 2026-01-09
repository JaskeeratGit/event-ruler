package software.amazon.event.ruler;

import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Objects;

class ValuePatterns_hashCode_2_0_Test {

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

    @Test
    void testHashCode_isDeterministicAcrossInvocations() throws Exception {
        ValuePatterns vp = new ValuePatterns(null, "deterministic");
        Method hashMethod = ValuePatterns.class.getDeclaredMethod("hashCode");
        hashMethod.setAccessible(true);
        int first = ((Integer) hashMethod.invoke(vp)).intValue();
        int second = ((Integer) hashMethod.invoke(vp)).intValue();
        assertEquals(first, second);
    }

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
