package software.amazon.event.ruler;

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

public final class SubRuleContext_equals_1_0_Test_testEquals_edgeIdValues {





    @Test
    public void testEquals_edgeIdValues() throws Exception {
        SubRuleContext min = new SubRuleContext(Long.MIN_VALUE, "min");
        SubRuleContext minSame = new SubRuleContext(Long.MIN_VALUE, "min2");
        SubRuleContext max = new SubRuleContext(Long.MAX_VALUE, "max");
        assertTrue(min.equals(minSame));
        assertTrue(minSame.equals(min));
        assertFalse(min.equals(max));
        Method equalsMethod = SubRuleContext.class.getMethod("equals", Object.class);
        assertTrue((Boolean) equalsMethod.invoke(min, minSame));
        assertFalse((Boolean) equalsMethod.invoke(min, max));
    }
}
