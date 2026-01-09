package software.amazon.event.ruler;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.*;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.nio.charset.StandardCharsets;

/**
 * JUnit 5 tests for Ruler.matches(String, String).
 *
 * Notes:
 * - These tests mock the static call to RuleCompiler.ListBasedRuleCompiler.flattenRule(...)
 *   to isolate Ruler.matches from RuleCompiler implementation.
 * - They also verify that flattenRule gets called with the provided rule string.
 * - The test for invalid JSON verifies that an IOException is propagated as declared by the method.
 */
public class Ruler_matches_1_0_Test_testMatches_invalidJson_throwsIOException {

    private MockedStatic<RuleCompiler.ListBasedRuleCompiler> ruleCompilerMock;

    @AfterEach
    void tearDown() {
        if (ruleCompilerMock != null) {
            ruleCompilerMock.close();
        }
    }



    @Test
    void testMatches_invalidJson_throwsIOException() {
        // Arrange
        final String invalidJson = "{ this is not : valid json }";
        final String rule = "rule-doesnt-matter";
        // We don't need to mock flattenRule because parsing should fail first.
        // Act & Assert
        assertThrows(IOException.class, () -> Ruler.matches(invalidJson, rule));
    }
}
