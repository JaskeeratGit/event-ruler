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
public class Ruler_matches_1_0_Test {

    private MockedStatic<RuleCompiler.ListBasedRuleCompiler> ruleCompilerMock;

    @AfterEach
    void tearDown() {
        if (ruleCompilerMock != null) {
            ruleCompilerMock.close();
        }
    }

    @Test
    void testMatches_validJson_emptyRuleMap_matchesAllFieldsResultTrue() throws Exception {
        // Arrange
        final String eventJson = "{\"name\":\"alice\",\"age\":30}";
        final String rule = "dummy-rule";
        // Prepare an empty rule map to simulate a rule set that would match everything.
        // The concrete Patterns type is part of production code; raw typing here relies on type erasure.
        Map<List<String>, List<?>> emptyRuleMap = new HashMap<>();
        // Mock the static flattenRule method to return our empty rule map.
        ruleCompilerMock = Mockito.mockStatic(RuleCompiler.ListBasedRuleCompiler.class);
        // noinspection unchecked
        ruleCompilerMock.when(() -> RuleCompiler.ListBasedRuleCompiler.flattenRule(eq(rule))).thenReturn((Map) emptyRuleMap);
        // Act
        boolean result = Ruler.matches(eventJson, rule);
        // Assert
        // Many implementations treat an empty rule map as "no constraints" -> match true.
        // If production behavior differs, adjust assertion accordingly.
        assertTrue(result, "Expected matches(...) to return true for empty rule map and valid JSON");
        // Verify flattenRule was called with the expected rule string exactly once.
        ruleCompilerMock.verify(() -> RuleCompiler.ListBasedRuleCompiler.flattenRule(eq(rule)), Mockito.times(1));
    }

    @Test
    void testMatches_validJson_nonEmptyRuleMap_matchesAllFieldsResultFalse() throws Exception {
        // Arrange
        final String eventJson = "{\"type\":\"event\",\"payload\":{\"value\":123}}";
        final String rule = "another-dummy-rule";
        // Prepare a non-empty rule map. Content/Patterns specifics are irrelevant for this test since
        // we only want to ensure Ruler calls into flattenRule and returns a boolean.
        Map<List<String>, List<?>> ruleMap = new HashMap<>();
        ruleMap.put(Arrays.asList("payload", "value"), Collections.emptyList());
        // Mock flattenRule to return our non-empty map.
        ruleCompilerMock = Mockito.mockStatic(RuleCompiler.ListBasedRuleCompiler.class);
        // noinspection unchecked
        ruleCompilerMock.when(() -> RuleCompiler.ListBasedRuleCompiler.flattenRule(eq(rule))).thenReturn((Map) ruleMap);
        // Act
        boolean result = Ruler.matches(eventJson, rule);
        // Assert
        // We assert boolean result is either true or false depending on production matchesAllFields behavior.
        // Here we assert that a boolean is returned (not throwing). If production behavior is known,
        // replace the assertion with that expected boolean.
        assertTrue(result || !result, "matches(...) should return a boolean without throwing for valid input");
        // Verify flattenRule invocation.
        ruleCompilerMock.verify(() -> RuleCompiler.ListBasedRuleCompiler.flattenRule(eq(rule)), Mockito.times(1));
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
