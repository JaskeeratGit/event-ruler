package software.amazon.event.ruler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for GenericMachine.addRule(T, Map)
 */
public class GenericMachine_addRule_8_0_Test_testAddRule_convertsStringsToPatterns_andDelegates {

    // A small test subclass to capture calls to addPatternRule
    // Placed in the same package so we can override package-private/protected methods.
    static class TestGenericMachine extends GenericMachine<String> {

        String capturedName;

        Map<String, List<Patterns>> capturedPatternMap;

        TestGenericMachine() {
            super(new GenericMachineConfiguration(false, false));
        }

        // Override the method that addRule delegates to so we can inspect inputs.
        @Override
        public void addPatternRule(final String name, final Map<String, List<Patterns>> patternMap) {
            this.capturedName = name;
            // make defensive deep copy for assertions
            this.capturedPatternMap = new HashMap<>();
            for (Entry<String, List<Patterns>> e : patternMap.entrySet()) {
                this.capturedPatternMap.put(e.getKey(), new ArrayList<>(e.getValue()));
            }
        }
    }

    private TestGenericMachine gm;

    @BeforeEach
    void setUp() {
        gm = new TestGenericMachine();
    }

    @Test
    void testAddRule_convertsStringsToPatterns_andDelegates() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("alpha", Arrays.asList("one", "two"));
        input.put("beta", Collections.singletonList("three"));
        gm.addRule("rule-1", input);
        // Verify delegation happened with same name
        assertEquals("rule-1", gm.capturedName);
        // Verify keys preserved
        assertNotNull(gm.capturedPatternMap);
        assertEquals(2, gm.capturedPatternMap.size());
        assertTrue(gm.capturedPatternMap.containsKey("alpha"));
        assertTrue(gm.capturedPatternMap.containsKey("beta"));
        // Verify values are converted to Patterns via Patterns.exactMatch(...)
        List<Patterns> expectedAlpha = Arrays.asList(Patterns.exactMatch("one"), Patterns.exactMatch("two"));
        List<Patterns> expectedBeta = Collections.singletonList(Patterns.exactMatch("three"));
        assertEquals(expectedAlpha, gm.capturedPatternMap.get("alpha"));
        assertEquals(expectedBeta, gm.capturedPatternMap.get("beta"));
    }

}
