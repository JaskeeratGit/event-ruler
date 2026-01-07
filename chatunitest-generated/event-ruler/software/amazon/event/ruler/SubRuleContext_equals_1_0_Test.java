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

public final class SubRuleContext_equals_1_0_Test {

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

    @Test
    public void testEquals_sameIdDifferentInstance_returnsTrueAndSymmetric() throws Exception {
        SubRuleContext a = new SubRuleContext(42L, "ruleA");
        SubRuleContext b = new SubRuleContext(42L, "ruleB");
        // direct calls
        assertTrue(a.equals(b));
        // symmetry
        assertTrue(b.equals(a));
        // reflective calls
        Method equalsMethod = SubRuleContext.class.getMethod("equals", Object.class);
        assertTrue((Boolean) equalsMethod.invoke(a, b));
        assertTrue((Boolean) equalsMethod.invoke(b, a));
    }

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
