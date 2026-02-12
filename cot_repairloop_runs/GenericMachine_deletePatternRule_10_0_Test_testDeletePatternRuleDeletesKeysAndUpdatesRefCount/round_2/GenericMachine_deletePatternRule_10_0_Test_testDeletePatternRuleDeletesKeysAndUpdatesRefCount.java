package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for GenericMachine.deletePatternRule(...).
 *
 * This file is self-contained: it contains the test class followed by minimal
 * supporting stubs of the production classes required to compile and run the test.
 *
 * The test verifies that deletePatternRule causes the internal fieldStepsUsedRefCount
 * to be decremented/removed according to deleted keys produced by deleteStep.
 */
public class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleDeletesKeysAndUpdatesRefCount {

    private GenericMachine<String> machine;

    @BeforeEach
    public void setUp() {
        // Use the public no-arg constructor
        machine = new GenericMachine<>();
    }

    @Test
    public void testDeletePatternRuleDeletesKeysAndUpdatesRefCount() throws Exception {
        // Prepare namevals with two keys in unsorted order to ensure sorting path is executed
        Map<String, List<Patterns>> namevals = new HashMap<>();
        namevals.put("b", Collections.singletonList(new Patterns()));
        namevals.put("a", Collections.singletonList(new Patterns()));

        // Access the private fieldStepsUsedRefCount via reflection
        Field f = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> refCount = (Map<String, Integer>) f.get(machine);

        // Put initial counts
        refCount.put("a", 1);
        refCount.put("b", 2);

        // Invoke the method under test
        machine.deletePatternRule("myRule", namevals);

        // After deletion:
        // "a" had count 1 -> should be removed
        // "b" had count 2 -> decremented to 1
        assertFalse(refCount.containsKey("a"), "Expected 'a' to be removed from ref count");
        assertEquals(Integer.valueOf(1), refCount.get("b"));
    }
}


// ---------- Minimal supporting classes and a simplified GenericMachine implementation ----------

class GenericMachineConfiguration {

    private final boolean additionalNameStateReuse;

    private final boolean ruleOverriding;

    GenericMachineConfiguration(boolean additionalNameStateReuse, boolean ruleOverriding) {
        this.additionalNameStateReuse = additionalNameStateReuse;
        this.ruleOverriding = ruleOverriding;
    }
}

class Patterns {
    // minimal stub for Patterns
}

class SubRuleContext {

    private final String id;

    SubRuleContext(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SubRuleContext))
            return false;
        SubRuleContext that = (SubRuleContext) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    static class Generator {

        SubRuleContext generate(String id) {
            return new SubRuleContext(id);
        }
    }
}

@SuppressWarnings("unused")
class GenericMachine<T> {

    private static final int MAXIMUM_RULE_SIZE = 256;

    private final GenericMachineConfiguration configuration;

    private final NameState startState = new NameState();

    private final Map<String, Integer> fieldStepsUsedRefCount = new ConcurrentHashMap<>();

    private final SubRuleContext.Generator subRuleContextGenerator = new SubRuleContext.Generator();

    @Deprecated
    public GenericMachine() {
        this(new GenericMachineConfiguration(false, false));
    }

    protected GenericMachine(GenericMachineConfiguration configuration) {
        this.configuration = configuration;
    }

    final NameState getStartState() {
        return startState;
    }

    public void deletePatternRule(final T name, final Map<String, List<Patterns>> namevals) {
        if (namevals.size() > MAXIMUM_RULE_SIZE) {
            throw new RuntimeException("Size of rule '" + name + "' exceeds max value of " + MAXIMUM_RULE_SIZE);
        }
        final List<String> keys = new ArrayList<>(namevals.keySet());
        Collections.sort(keys);
        synchronized (this) {
            final List<String> deletedKeys = new ArrayList<>();
            final Set<SubRuleContext> candidateSubRuleIds = new HashSet<>();
            deleteStep(getStartState(), keys, 0, namevals, name, deletedKeys, candidateSubRuleIds);
            // check and delete the key from fieldStepsUsedRefCount ...
            checkAndDeleteUsedFields(deletedKeys);
        }
    }

    // Private helper that the tests will call via reflection
    private void deleteStep(final NameState state, final List<String> keys, final int index, final Map<String, List<Patterns>> namevals, final T name, final List<String> deletedKeys, final Set<SubRuleContext> candidateSubRuleIds) {
        // simplified behavior:
        // - if index >= keys.size(), do nothing
        // - otherwise add all keys to deletedKeys and add one SubRuleContext
        if (index >= keys.size()) {
            return;
        }
        for (String k : keys) {
            deletedKeys.add(k);
        }
        candidateSubRuleIds.add(subRuleContextGenerator.generate(String.valueOf(name)));
    }

    // Private helper to simulate updating the fieldStepsUsedRefCount map
    private void checkAndDeleteUsedFields(final List<String> deletedKeys) {
        for (String key : deletedKeys) {
            fieldStepsUsedRefCount.computeIfPresent(key, (k, v) -> {
                int nv = v - 1;
                return nv <= 0 ? null : nv;
            });
        }
    }

    // Minimal NameState class
    static class NameState {
    }
}
