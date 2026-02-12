package software.amazon.event.ruler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for GenericMachine.addRule(T, Map)
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
    void setUp() {
        gm = new TestGenericMachine();
    }

    @Test
    void test_getStartState_viaReflection_isAccessibleAndNotNull() throws Exception {
        // getStartState has package-private access in the class; use reflection to invoke it
        Method getStart = GenericMachine.class.getDeclaredMethod("getStartState");
        getStart.setAccessible(true);
        Object startState = getStart.invoke(gm);
        assertNotNull(startState);
        assertEquals(NameState.class, startState.getClass());
    }
}
