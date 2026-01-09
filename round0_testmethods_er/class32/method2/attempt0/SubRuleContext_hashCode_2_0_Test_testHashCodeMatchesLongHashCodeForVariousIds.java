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

public class SubRuleContext_hashCode_2_0_Test_testHashCodeMatchesLongHashCodeForVariousIds {

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



}
