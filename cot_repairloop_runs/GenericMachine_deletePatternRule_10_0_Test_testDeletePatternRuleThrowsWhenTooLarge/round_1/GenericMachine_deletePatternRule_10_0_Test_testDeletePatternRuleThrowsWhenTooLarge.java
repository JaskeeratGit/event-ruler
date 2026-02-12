package software.amazon.event.ruler;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleThrowsWhenTooLarge {

    private GenericMachine<String> machine;

    @Before
    public void setUp() {
        // Use the public no-arg constructor that the production class provides
        machine = new GenericMachine<>();
    }

    @Test
    public void testDeletePatternRuleThrowsWhenTooLarge() {
        // Create a map whose size exceeds MAXIMUM_RULE_SIZE (256) -> use 257 entries
        Map<String, List<Patterns>> bigMap = new HashMap<>();
        for (int i = 0; i < 257; i++) {
            // Use an empty list of Patterns for each key; specify generic to avoid unchecked warnings
            bigMap.put("key" + i, Collections.<Patterns>emptyList());
        }

        try {
            machine.deletePatternRule("ruleName", bigMap);
            fail("Expected RuntimeException due to rule size > MAXIMUM_RULE_SIZE");
        } catch (RuntimeException ex) {
            assertTrue("Exception message should mention exceeding max value",
                    ex.getMessage().contains("exceeds max value"));
            assertTrue("Exception message should include the rule name",
                    ex.getMessage().contains("ruleName"));
        }
    }
}
