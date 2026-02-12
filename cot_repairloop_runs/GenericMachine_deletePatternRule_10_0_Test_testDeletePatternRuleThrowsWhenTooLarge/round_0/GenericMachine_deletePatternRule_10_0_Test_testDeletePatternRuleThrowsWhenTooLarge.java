package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleThrowsWhenTooLarge {

    private GenericMachine<String> machine;

    @BeforeEach
    void setUp() {
        // Use the public no-arg constructor that the production class provides
        machine = new GenericMachine<>();
    }

    @Test
    void testDeletePatternRuleThrowsWhenTooLarge() {
        // Create a map whose size exceeds MAXIMUM_RULE_SIZE (256) -> use 257 entries
        Map<String, List<Patterns>> bigMap = new HashMap<>();
        for (int i = 0; i < 257; i++) {
            // Use an empty list of Patterns for each key
            bigMap.put("key" + i, Collections.emptyList());
        }

        RuntimeException ex = assertThrows(RuntimeException.class, () -> machine.deletePatternRule("ruleName", bigMap));
        assertTrue(ex.getMessage().contains("exceeds max value"));
        assertTrue(ex.getMessage().contains("ruleName"));
    }
}
