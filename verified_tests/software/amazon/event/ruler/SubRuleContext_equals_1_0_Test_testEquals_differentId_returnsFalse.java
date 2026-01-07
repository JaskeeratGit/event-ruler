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

public final class SubRuleContext_equals_1_0_Test_testEquals_differentId_returnsFalse {



    @Test
    public void testEquals_differentId_returnsFalse() throws Exception {
        SubRuleContext a = new SubRuleContext(100L, "r1");
        SubRuleContext b = new SubRuleContext(101L, "r1");
        // direct call
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        // reflective call
        Method equalsMethod = SubRuleContext.class.getMethod("equals", Object.class);
        assertFalse((Boolean) equalsMethod.invoke(a, b));
        assertFalse((Boolean) equalsMethod.invoke(b, a));
    }


}
