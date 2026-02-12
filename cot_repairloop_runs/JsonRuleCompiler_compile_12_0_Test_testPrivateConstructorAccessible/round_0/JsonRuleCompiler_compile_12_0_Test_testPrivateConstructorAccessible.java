package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Fixed unit tests for JsonRuleCompiler.
 *
 * Notes:
 * - Replaced Java 9+ Constructor.canAccess(null) call with Java 8 compatible Constructor.isAccessible().
 * - Added a simple smoke test for JsonRuleCompiler.compile(byte[], boolean) using an empty JSON array.
 */
public class JsonRuleCompiler_compile_12_0_Test_testPrivateConstructorAccessible {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection (Java 8 compatible)
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        // on Java 8, use isAccessible() instead of canAccess(null)
        Assertions.assertFalse(ctor.isAccessible());
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        Assertions.assertNotNull(instance);
    }

    @Test
    public void testCompileWithEmptyJsonArrayDoesNotThrow() throws Exception {
        // A simple smoke test for the compile method. Use a minimal valid JSON input (empty array).
        byte[] source = "[]".getBytes("UTF-8");
        // The compile method may throw IOException; declaring throws Exception on the test covers that.
        List<?> result = (List<?>) JsonRuleCompiler.compile(source, false);
        Assertions.assertNotNull(result);
        // Expecting an empty list for an empty JSON array input (smoke test to ensure the method runs).
        Assertions.assertTrue(result.isEmpty());
    }
}
