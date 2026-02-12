package software.amazon.event.ruler;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Fixed unit test that uses JUnit 4 so it compiles in environments that don't have JUnit 5 on the classpath.
 */
public class RuleCompiler_compile_10_0_Test_testCompileWithEmptyObjectReturnsEmptyMap {

    @Test
    public void testCompileWithEmptyObjectReturnsEmptyMap() throws Exception {
        Map<String, List<Patterns>> result = RuleCompiler.compile("{}", false);
        assertNotNull("Result map should not be null", result);
        assertTrue("Result map should be empty for empty JSON object", result.isEmpty());
    }

}
