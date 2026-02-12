package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class JsonRuleCompiler_compile_12_0_Test_testPrivateConstructorAccessible {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance);
    }

}
