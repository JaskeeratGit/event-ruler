package software.amazon.event.ruler;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Unit tests for GenericMachine.addRule(T, Map)
 *
 * Converted to JUnit 4 to match common test classpath and avoid junit-jupiter (JUnit 5) missing on some builds.
 */
public class GenericMachine_addRule_8_0_Test_test_getStartState_viaReflection_isAccessibleAndNotNull {

    // A small test subclass to capture calls to addPatternRule
    // Placed in the same package so we can override package-private/protected methods.
    static class TestGenericMachine extends GenericMachine<String> {

        String capturedName;
        Map<String, List<Patterns>> capturedPatternMap;

        TestGenericMachine() {
            super(new GenericMachineConfiguration(false, false));
        }

        // Override the method that addRule delegates to so we can inspect inputs.
        // Must be public because the super method is public.
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
    public void test_addRule_convertsValuesToExactMatchPatterns_and_delegatesToAddPatternRule() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("alpha", Arrays.asList("one", "two"));
        input.put("beta", Collections.singletonList("three"));

        gm.addRule("rule-1", input);

        assertEquals("rule-1", gm.capturedName);
        assertNotNull(gm.capturedPatternMap);

        // build expected map of Patterns using exactMatch
        Map<String, List<Patterns>> expected = new HashMap<>();
        for (Map.Entry<String, List<String>> e : input.entrySet()) {
            expected.put(e.getKey(), e.getValue().stream().map(Patterns::exactMatch).collect(Collectors.toList()));
        }
        assertEquals(expected.keySet(), gm.capturedPatternMap.keySet());

        // verify lists contents for each key
        for (String key : expected.keySet()) {
            List<Patterns> expList = expected.get(key);
            List<Patterns> actList = gm.capturedPatternMap.get(key);
            assertNotNull("Captured list for key " + key + " should not be null", actList);
            assertEquals("List sizes for key " + key + " should match", expList.size(), actList.size());
            for (int i = 0; i < expList.size(); i++) {
                assertEquals("Pattern at index " + i + " for key " + key + " should match", expList.get(i), actList.get(i));
            }
        }
    }

    @Test
    public void test_getStartState_viaReflection_isAccessibleAndNotNull() throws Exception {
        // getStartState has package-private access in the class; use reflection to invoke it
        Method getStart = GenericMachine.class.getDeclaredMethod("getStartState");
        getStart.setAccessible(true);
        Object startState = getStart.invoke(gm);
        assertNotNull(startState);
        assertEquals(NameState.class, startState.getClass());
    }
}
