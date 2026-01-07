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

public class SubRuleContext_hashCode_2_0_Test_testDifferentIdsProduceDifferentHashCodesForSamplePairs {




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
