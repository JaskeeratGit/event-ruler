package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleDeletesKeysAndUpdatesRefCount {

    private GenericMachine<String> machine;

    @BeforeEach
    void setUp() {
        // Use the public no-arg constructor
        machine = new GenericMachine<>();
    }

    @Test
    void testDeletePatternRuleDeletesKeysAndUpdatesRefCount() throws Exception {
        // Prepare namevals with two keys in unsorted order to ensure sorting path is executed
        Map<String, List<Patterns>> namevals = new HashMap<>();
        // Use production Patterns factory to create instances
        namevals.put("b", Collections.singletonList(Patterns.exactMatch("v1")));
        namevals.put("a", Collections.singletonList(Patterns.exactMatch("v2")));

        // Add the rule so that internal structures reference these keys
        machine.addPatternRule("myRule", namevals);

        // Populate private fieldStepsUsedRefCount with entries for "a" and "b"
        Field f = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> refCount = (Map<String, Integer>) f.get(machine);
        // Put initial counts (override whatever addPatternRule set) to simulate the scenario:
        // "a" -> 1 (should be removed), "b" -> 2 (should be decremented to 1)
        refCount.put("a", 1);
        refCount.put("b", 2);

        // Invoke public method
        machine.deletePatternRule("myRule", namevals);

        // After deletion, per our implementation, both keys are deleted:
        // "a" had count 1 -> should be removed
        // "b" had count 2 -> decremented to 1
        assertFalse(refCount.containsKey("a"), "Expected 'a' to be removed from ref count");
        assertEquals(Integer.valueOf(1), refCount.get("b"));
    }

}
