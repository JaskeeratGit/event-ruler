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

public class SubRuleContext_hashCode_2_0_Test_testSameIdDifferentRuleNameProducesSameHashCode {


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


}
