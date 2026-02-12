package software.amazon.event.ruler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class GenericMachine_deletePatternRule_10_0_Test_testPrivateDeleteStepInvocationViaReflection {

    private GenericMachine<String> machine;

    @Before
    public void setUp() {
        // Use the public no-arg constructor
        machine = new GenericMachine<>();
    }

    @Test
    public void testPrivateDeleteStepInvocationViaReflection() throws Exception {
        // Prepare parameters for deleteStep: empty keys list should do nothing
        List<String> keys = new ArrayList<>();
        Map<String, List<Patterns>> namevals = new HashMap<>();
        List<String> deletedKeys = new ArrayList<>();
        Set<SubRuleContext> candidateSubRuleIds = new HashSet<>();
        Method deleteStep = GenericMachine.class.getDeclaredMethod("deleteStep", machine.getStartState().getClass(), List.class, int.class, Map.class, Object.class, List.class, Set.class);
        deleteStep.setAccessible(true);
        // Invoke deleteStep with index == keys.size() (0) -> should return without modification
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        assertTrue("deletedKeys should remain empty when no keys provided", deletedKeys.isEmpty());
        assertTrue("candidateSubRuleIds should remain empty when no keys provided", candidateSubRuleIds.isEmpty());
        // Now test deleteStep with a non-empty keys list: expect keys to be added to deletedKeys
        keys.add("k1");
        keys.add("k2");
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        // Our deleteStep implementation in the real class may add keys to deletedKeys and populate candidateSubRuleIds
        assertEquals(2, deletedKeys.size());
        assertTrue(deletedKeys.contains("k1") && deletedKeys.contains("k2"));
        assertFalse(candidateSubRuleIds.isEmpty());
    }
}
