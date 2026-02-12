package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import org.junit.Test;
import org.junit.Assert;

public class JsonRuleCompiler_compile_12_0_Test_testPrivateConstructorAccessible {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        Assert.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        Assert.assertNotNull(instance);
    }

}
