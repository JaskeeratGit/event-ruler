package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RuleCompiler_compile_13_0_Test_testPrivateConstructorThrowsUnsupportedOperationException {

    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException itEx = Assertions.assertThrows(InvocationTargetException.class, () -> ctor.newInstance());
        Assertions.assertNotNull(itEx.getCause());
        Assertions.assertTrue(itEx.getCause() instanceof UnsupportedOperationException, "Expected cause to be UnsupportedOperationException");
    }
}
