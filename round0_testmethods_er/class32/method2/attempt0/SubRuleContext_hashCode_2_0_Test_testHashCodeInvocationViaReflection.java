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

public class SubRuleContext_hashCode_2_0_Test_testHashCodeInvocationViaReflection {



    @Test
    public void testHashCodeInvocationViaReflection() throws Exception {
        SubRuleContext ctx = new SubRuleContext(9999L, "name");
        Method hashMethod = SubRuleContext.class.getMethod("hashCode");
        // even though method is public, invoke via reflection to ensure reflective invocation works
        Object reflected = hashMethod.invoke(ctx);
        assertTrue(reflected instanceof Integer);
        assertEquals(ctx.hashCode(), ((Integer) reflected).intValue());
    }

}
