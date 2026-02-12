package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GenericMachine.addRule(T, Map).
 *
 * Notes:
 * - Uses JUnit 5 (org.junit.jupiter.api).
 * - The TestGenericMachine subclass captures calls to addPatternRule(name, patternMap)
 *   and makes a defensive deep copy so assertions are stable.
 */
public class GenericMachineAddRuleTest {

    // A small test subclass to capture calls to addPatternRule
    // Placed in the same package so we can override methods.
    static class TestGenericMachine extends GenericMachine<String> {

        String capturedName;
        Map<String, List<Patterns>> capturedPatternMap;

        TestGenericMachine() {
            super(new GenericMachineConfiguration(false, false));
        }

        // Must use the same (public) visibility as the method in the base class.
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

    @BeforeEach
    public void setUp() {
        gm = new TestGenericMachine();
    }

    @Test
    public void testAddRule_nullValueList_throwsNullPointerException() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("key-with-null", null);
        // should throw NPE when calling stream() on a null list value
        assertThrows(NullPointerException.class, () -> gm.addRule("npe-rule", input));
    }

    @Test
    public void testAddRule_validConvertsToPatterns() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("k", Arrays.asList("a", "b"));

        gm.addRule("rule-1", input);

        assertEquals("rule-1", gm.capturedName);
        assertNotNull(gm.capturedPatternMap, "Captured pattern map should not be null");
        assertTrue(gm.capturedPatternMap.containsKey("k"), "Captured pattern map should contain key 'k'");

        List<Patterns> patterns = gm.capturedPatternMap.get("k");
        assertNotNull(patterns, "Patterns list should not be null for key 'k'");
        assertEquals(2, patterns.size(), "Patterns list size should match input list size");
        assertNotNull(patterns.get(0), "First pattern should not be null");
        assertNotNull(patterns.get(1), "Second pattern should not be null");
    }

    @Test
    public void testAddRule_emptyListProducesEmptyPatternsList() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("empty", Collections.emptyList());

        gm.addRule("rule-empty", input);

        assertEquals("rule-empty", gm.capturedName, "Captured rule name should match");
        assertNotNull(gm.capturedPatternMap, "Captured pattern map should not be null");
        assertTrue(gm.capturedPatternMap.containsKey("empty"), "Captured pattern map should contain key 'empty'");
        List<Patterns> patterns = gm.capturedPatternMap.get("empty");
        assertNotNull(patterns, "Patterns list should not be null");
        assertEquals(0, patterns.size(), "Empty input list should produce empty Patterns list");
    }
}
