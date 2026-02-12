package software.amazon.event.ruler;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for GenericMachine.addRule(T, Map)
 */
public class GenericMachine_addRule_8_0_Test_testAddRule_withEmptyMap_delegatesWithEmptyPatternMap {

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
            for (Map.Entry<String, List<Patterns>> e : patternMap.entrySet()) {
                this.capturedPatternMap.put(e.getKey(), new ArrayList<>(e.getValue()));
            }
        }
    }

    private TestGenericMachine gm;

    @Before
    public void setUp() {
        gm = new TestGenericMachine();
    }


    @Test
    public void testAddRule_withEmptyMap_delegatesWithEmptyPatternMap() {
        Map<String, List<String>> input = Collections.emptyMap();
        gm.addRule("empty-rule", input);
        assertEquals("empty-rule", gm.capturedName);
        assertNotNull(gm.capturedPatternMap);
        assertTrue(gm.capturedPatternMap.isEmpty());
    }


}
