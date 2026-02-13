package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RuleCompiler_compile_13_0_Test_testPrivateConstructorThrowsUnsupportedOperationException {

    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        try {
            ctor.newInstance();
            Assertions.fail("Expected constructor invocation to throw UnsupportedOperationException");
        } catch (InvocationTargetException itEx) {
            assertNotNull(itEx.getCause());
            assertTrue(itEx.getCause() instanceof UnsupportedOperationException, "Expected cause to be UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Constructor threw UnsupportedOperationException directly; this is acceptable.
        }
    }
}
