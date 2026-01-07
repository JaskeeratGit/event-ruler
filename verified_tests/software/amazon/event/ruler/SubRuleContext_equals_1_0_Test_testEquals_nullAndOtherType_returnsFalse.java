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

public final class SubRuleContext_equals_1_0_Test_testEquals_nullAndOtherType_returnsFalse {




    @Test
    public void testEquals_nullAndOtherType_returnsFalse() throws Exception {
        SubRuleContext ctx = new SubRuleContext(-1L, null);
        // compare to null
        assertFalse(ctx.equals(null));
        // compare to other type
        Object other = new Object();
        assertFalse(ctx.equals(other));
        // reflective checks
        Method equalsMethod = SubRuleContext.class.getMethod("equals", Object.class);
        assertFalse((Boolean) equalsMethod.invoke(ctx, (Object) null));
        assertFalse((Boolean) equalsMethod.invoke(ctx, other));
    }

}
