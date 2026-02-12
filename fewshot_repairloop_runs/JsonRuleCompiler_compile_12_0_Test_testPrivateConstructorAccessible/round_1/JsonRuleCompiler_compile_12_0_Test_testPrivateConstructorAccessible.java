package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class JsonRuleCompiler_compile_12_0_Test_testPrivateConstructorAccessible {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        Assertions.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        Assertions.assertNotNull(instance);
    }

}
