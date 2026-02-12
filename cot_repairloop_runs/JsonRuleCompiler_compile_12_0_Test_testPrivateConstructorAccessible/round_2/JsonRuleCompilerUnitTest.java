package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit tests for JsonRuleCompiler (JUnit 5).
 *
 * Notes:
 * - Renamed the test class to avoid duplicate-class compilation errors
 *   in projects that already contain a test with the original name.
 * - Uses JUnit 5 assertions and annotations.
 * - Includes smoke tests for JsonRuleCompiler.compile(byte[], boolean)
 *   using an empty JSON array and an invalid JSON input to assert behavior.
 */
public class JsonRuleCompilerUnitTest {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection (Java 8 compatible)
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        // on Java 8, use isAccessible() instead of canAccess(null)
        assertFalse(ctor.isAccessible(), "Constructor should not be accessible by default");
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance, "Instantiated object from private constructor should not be null");
    }

    @Test
    public void testCompileWithEmptyJsonArrayDoesNotThrow() throws Exception {
        // A simple smoke test for the compile method. Use a minimal valid JSON input (empty array).
        byte[] source = "[]".getBytes(StandardCharsets.UTF_8);
        List<?> result = JsonRuleCompiler.compile(source, false);
        assertNotNull(result, "Result should not be null for empty JSON array");
        // Expecting an empty list for an empty JSON array input (smoke test to ensure the method runs).
        assertTrue(result.isEmpty(), "Result list should be empty for an empty JSON array");
    }

    @Test
    public void testCompileWithEmptyJsonArrayWithOverridingTrue() throws Exception {
        byte[] source = "[]".getBytes(StandardCharsets.UTF_8);
        List<?> result = JsonRuleCompiler.compile(source, true);
        assertNotNull(result, "Result should not be null for empty JSON array with overriding");
        assertTrue(result.isEmpty(), "Result list should be empty for an empty JSON array with overriding");
    }

    @Test
    public void testCompileWithInvalidJsonThrowsIOException() {
        byte[] source = "not-a-json".getBytes(StandardCharsets.UTF_8);
        assertThrows(IOException.class, () -> JsonRuleCompiler.compile(source, false),
                "Invalid JSON input should cause an IOException (or a subtype) to be thrown");
    }
}
