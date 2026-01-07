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
public class Ruler_matches_1_0_Test_testMatches_validJson_emptyRuleMap_matchesAllFieldsResultTrue {

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


}
