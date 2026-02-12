package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RuleCompiler.
 *
 * This test verifies that the utility class RuleCompiler has a private constructor
 * which, if invoked reflectively, throws UnsupportedOperationException wrapped inside
 * an InvocationTargetException.
 */
public class RuleCompiler_compile_13_0_Test_testPrivateConstructorThrowsUnsupportedOperationException {

    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);

        InvocationTargetException itEx = assertThrows(InvocationTargetException.class, () -> ctor.newInstance());

        assertNotNull(itEx.getCause(), "Expected the InvocationTargetException to have a cause");
        assertTrue(itEx.getCause() instanceof UnsupportedOperationException,
                "Expected cause to be UnsupportedOperationException");
    }
}
