package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SubRuleContext_hashCode_2_0_Test {

    @Test
    public void testHashCodeMatchesLongHashCodeForVariousIds() throws Exception {
        long[] ids = new long[] { 0L, 1L, -1L, 123456789012345L, -98765432109876L, Long.MAX_VALUE, Long.MIN_VALUE };
        Field idField = SubRuleContext.class.getDeclaredField("id");
        idField.setAccessible(true);
        for (long id : ids) {
            SubRuleContext ctx = new SubRuleContext(id, "rule");
            // verify internal id field is set correctly
            long actualInternalId = idField.getLong(ctx);
            assertEquals(id, actualInternalId, "internal id should match constructor value");
            int expected = Long.hashCode(id);
            int actual = ctx.hashCode();
            assertEquals(expected, actual, "hashCode should be Long.hashCode(id) for id=" + id);
        }
    }

    @Test
    public void testSameIdDifferentRuleNameProducesSameHashCode() {
        long id = 42L;
        SubRuleContext a = new SubRuleContext(id, "ruleA");
        SubRuleContext b = new SubRuleContext(id, null);
        SubRuleContext c = new SubRuleContext(id, new Object());
        int ha = a.hashCode();
        int hb = b.hashCode();
        int hc = c.hashCode();
        assertEquals(ha, hb, "Instances with same id must have same hashCode (a vs b)");
        assertEquals(ha, hc, "Instances with same id must have same hashCode (a vs c)");
        assertEquals(Long.hashCode(id), ha, "hashCode should equal Long.hashCode(id)");
    }

    @Test
    public void testHashCodeInvocationViaReflection() throws Exception {
        SubRuleContext ctx = new SubRuleContext(9999L, "name");
        Method hashMethod = SubRuleContext.class.getMethod("hashCode");
        // even though method is public, invoke via reflection to ensure reflective invocation works
        Object reflected = hashMethod.invoke(ctx);
        assertTrue(reflected instanceof Integer);
        assertEquals(ctx.hashCode(), ((Integer) reflected).intValue());
    }

    @Test
    public void testDifferentIdsProduceDifferentHashCodesForSamplePairs() {
        // While not guaranteed universally, test a set of pairs expected to produce different hashCodes
        long[][] pairs = new long[][] { { 0L, 1L }, { 1L, 2L }, { 123L, 124L }, { Long.MAX_VALUE, Long.MAX_VALUE - 1 }, { Long.MIN_VALUE, Long.MIN_VALUE + 1 } };
        for (long[] pair : pairs) {
            SubRuleContext x = new SubRuleContext(pair[0], "x");
            SubRuleContext y = new SubRuleContext(pair[1], "y");
            assertNotEquals(x.hashCode(), y.hashCode(), "Expected different hashCodes for ids " + pair[0] + " and " + pair[1]);
        }
    }
}
