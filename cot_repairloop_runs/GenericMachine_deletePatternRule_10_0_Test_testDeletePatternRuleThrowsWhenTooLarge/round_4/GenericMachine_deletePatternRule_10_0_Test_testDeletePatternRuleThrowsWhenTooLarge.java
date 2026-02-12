package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test verifies that GenericMachine.deletePatternRule throws a RuntimeException
 * when the provided map size exceeds the implementation's MAXIMUM_RULE_SIZE (expected 256).
 *
 * Uses JUnit 5 (jupiter API).
 */
public class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleThrowsWhenTooLarge {

    private GenericMachine<String> machine;

    @BeforeEach
    public void setUp() {
        // Use the public no-arg constructor that the production class provides
        machine = new GenericMachine<>();
    }

    @Test
    public void testDeletePatternRuleThrowsWhenTooLarge() {
        // Create a map whose size exceeds MAXIMUM_RULE_SIZE (256) -> use 257 entries
        Map<String, List<Patterns>> bigMap = new HashMap<>();
        for (int i = 0; i < 257; i++) {
            // Use an empty list of Patterns for each key
            bigMap.put("key" + i, Collections.<Patterns>emptyList());
        }

        RuntimeException ex = assertThrows(RuntimeException.class, () -> machine.deletePatternRule("ruleName", bigMap),
                "Expected RuntimeException due to rule size > MAXIMUM_RULE_SIZE");

        String msg = ex.getMessage();
        assertNotNull(msg, "Exception message should not be null");
        assertTrue(msg.contains("exceeds max value"), "Exception message should mention exceeding max value");
        assertTrue(msg.contains("ruleName"), "Exception message should include the rule name");
    }
}
