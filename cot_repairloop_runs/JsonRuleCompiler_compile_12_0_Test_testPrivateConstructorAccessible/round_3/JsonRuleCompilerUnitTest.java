package software.amazon.event.ruler;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Fixed unit tests for JsonRuleCompiler adjusted for environments using JUnit 4.
 *
 * Notes:
 * - Uses JUnit 4 annotations and assertions so the test file compiles where
 *   JUnit 5 is not available on the classpath.
 * - Covers:
 *     - Accessing the private constructor via reflection.
 *     - Basic smoke tests for JsonRuleCompiler.compile(byte[], boolean)
 *       with an empty JSON array and with overriding=true.
 *     - Invalid JSON input causing an IOException (or subtype) to be thrown.
 */
public class JsonRuleCompilerUnitTest {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection (Java 8 compatible)
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        // on Java 8, use isAccessible() instead of canAccess(null)
        assertFalse("Constructor should not be accessible by default", ctor.isAccessible());
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull("Instantiated object from private constructor should not be null", instance);
    }

    @Test
    public void testCompileWithEmptyJsonArrayDoesNotThrow() throws Exception {
        // A simple smoke test for the compile method. Use a minimal valid JSON input (empty array).
        byte[] source = "[]".getBytes(StandardCharsets.UTF_8);
        List<?> result = JsonRuleCompiler.compile(source, false);
        assertNotNull("Result should not be null for empty JSON array", result);
        // Expecting an empty list for an empty JSON array input (smoke test to ensure the method runs).
        assertTrue("Result list should be empty for an empty JSON array", result.isEmpty());
    }

    @Test
    public void testCompileWithEmptyJsonArrayWithOverridingTrue() throws Exception {
        byte[] source = "[]".getBytes(StandardCharsets.UTF_8);
        List<?> result = JsonRuleCompiler.compile(source, true);
        assertNotNull("Result should not be null for empty JSON array with overriding", result);
        assertTrue("Result list should be empty for an empty JSON array with overriding", result.isEmpty());
    }

    @Test(expected = IOException.class)
    public void testCompileWithInvalidJsonThrowsIOException() throws Exception {
        byte[] source = "not-a-json".getBytes(StandardCharsets.UTF_8);
        // compile should throw IOException or a subtype (e.g., JsonParseException)
        JsonRuleCompiler.compile(source, false);
    }
}
