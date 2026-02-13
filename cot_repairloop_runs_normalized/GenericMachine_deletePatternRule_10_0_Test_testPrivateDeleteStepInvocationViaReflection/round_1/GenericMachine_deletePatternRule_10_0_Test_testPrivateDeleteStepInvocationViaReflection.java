package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GenericMachine_deletePatternRule_10_0_Test_testPrivateDeleteStepInvocationViaReflection {

    private GenericMachine<String> machine;

    @BeforeEach
    void setUp() {
        // Use the public no-arg constructor
        machine = new GenericMachine<>();
    }

    @Test
    void testPrivateDeleteStepInvocationViaReflection() throws Exception {
        // Prepare parameters for deleteStep: empty keys list should do nothing
        List<String> keys = new ArrayList<>();
        Map<String, List<Patterns>> namevals = new HashMap<>();
        List<String> deletedKeys = new ArrayList<>();
        Set<SubRuleContext> candidateSubRuleIds = new HashSet<>();
        // Use Class.forName to avoid compile-time reference to the non-public inner class NameState
        Class<?> nameStateClass = Class.forName("software.amazon.event.ruler.GenericMachine$NameState");
        Method deleteStep = GenericMachine.class.getDeclaredMethod("deleteStep", nameStateClass, List.class, int.class, Map.class, Object.class, List.class, Set.class);
        deleteStep.setAccessible(true);
        // Invoke deleteStep with index == keys.size() (0) -> should return without modification
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        assertTrue(deletedKeys.isEmpty(), "deletedKeys should remain empty when no keys provided");
        assertTrue(candidateSubRuleIds.isEmpty(), "candidateSubRuleIds should remain empty when no keys provided");
        // Now test deleteStep with a non-empty keys list: expect keys to be added to deletedKeys or candidateSubRuleIds to change
        keys.add("k1");
        keys.add("k2");
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        // We assert that either deletedKeys got populated or candidateSubRuleIds is non-empty (implementation dependent)
        assertTrue(deletedKeys.size() == 2 || !candidateSubRuleIds.isEmpty(), "Either deletedKeys should contain the keys or candidateSubRuleIds should be populated");
    }
}
