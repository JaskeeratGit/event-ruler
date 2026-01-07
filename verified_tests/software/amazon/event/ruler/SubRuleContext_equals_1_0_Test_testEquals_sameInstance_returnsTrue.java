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

public final class SubRuleContext_equals_1_0_Test_testEquals_sameInstance_returnsTrue {

    @Test
    public void testEquals_sameInstance_returnsTrue() throws Exception {
        SubRuleContext ctx = new SubRuleContext(1L, "ruleA");
        // direct call
        assertTrue(ctx.equals(ctx));
        // reflective call
        Method equalsMethod = SubRuleContext.class.getMethod("equals", Object.class);
        Object result = equalsMethod.invoke(ctx, ctx);
        assertTrue((Boolean) result);
    }




}
