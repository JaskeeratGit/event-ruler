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

public final class SubRuleContext_equals_1_0_Test_testEquals_sameIdDifferentInstance_returnsTrueAndSymmetric {


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



}
