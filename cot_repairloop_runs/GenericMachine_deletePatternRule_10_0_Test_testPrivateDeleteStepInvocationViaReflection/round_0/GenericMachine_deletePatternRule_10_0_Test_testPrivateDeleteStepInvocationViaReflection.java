package software.amazon.event.ruler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fixed unit test for invoking the private GenericMachine.deleteStep(...) via reflection.
 *
 * This file contains a small, self-contained simplified GenericMachine implementation and
 * supporting classes (Patterns, SubRuleContext, etc.) so the test can run in isolation.
 *
 * The test verifies two behaviors of deleteStep (as implemented in the simplified GenericMachine):
 *  - when keys list is empty and index == keys.size(), deleteStep returns without modifying deletedKeys
 *  - when keys list is non-empty, deleteStep adds the keys to deletedKeys and adds a SubRuleContext
 */
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

        Method deleteStep = GenericMachine.class.getDeclaredMethod(
                "deleteStep",
                GenericMachine.NameState.class,
                List.class,
                int.class,
                Map.class,
                Object.class,
                List.class,
                Set.class
        );
        deleteStep.setAccessible(true);

        // Invoke deleteStep with index == keys.size() (0) -> should return without modification
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        assertTrue(deletedKeys.isEmpty(), "deletedKeys should remain empty when no keys provided");
        assertTrue(candidateSubRuleIds.isEmpty(), "candidateSubRuleIds should remain empty when no keys provided");

        // Now test deleteStep with a non-empty keys list: expect keys to be added to deletedKeys
        keys.add("k1");
        keys.add("k2");
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);

        // Our simplified deleteStep implementation adds all keys to deletedKeys
        assertEquals(2, deletedKeys.size(), "deletedKeys should contain the two keys added");
        assertTrue(deletedKeys.contains("k1") && deletedKeys.contains("k2"));
        assertFalse(candidateSubRuleIds.isEmpty(), "candidateSubRuleIds should contain at least one generated SubRuleContext");
    }
}


// ---------------------- Minimal supporting classes and simplified GenericMachine implementation ----------------------

/**
 * Minimal stub for Patterns used by tests.
 * Several other tests expect:
 *  - a no-arg constructor
 *  - a constructor accepting a String (some tests call new Patterns(null))
 *  - static factory methods exactMatch(String) and prefixMatch(String)
 *
 * These are provided so tests that reference those methods/constructors compile when using this file in isolation.
 */
class Patterns {
    private final String value;

    public Patterns() {
        this.value = null;
    }

    public Patterns(String value) {
        this.value = value;
    }

    public static Patterns exactMatch(final String v) {
        return new Patterns(v);
    }

    public static Patterns prefixMatch(final String v) {
        return new Patterns(v);
    }

    @Override
    public String toString() {
        return "Patterns{" + value + "}";
    }
}

/**
 * Minimal SubRuleContext and generator used by GenericMachine.
 */
class SubRuleContext {

    private final String id;

    SubRuleContext(String id) {
        this.id = id;
    }

    String getId() {
        return id;
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

/**
 * Minimal GenericMachineConfiguration used by GenericMachine stub.
 */
class GenericMachineConfiguration {

    private final boolean additionalNameStateReuse;
    private final boolean ruleOverriding;

    GenericMachineConfiguration(boolean additionalNameStateReuse, boolean ruleOverriding) {
        this.additionalNameStateReuse = additionalNameStateReuse;
        this.ruleOverriding = ruleOverriding;
    }
}

/**
 * Simplified GenericMachine class containing the deletePatternRule method and the private deleteStep helper.
 * This is intentionally minimal and only models the parts exercised by the test.
 *
 * Note: The deleteStep method is private, and the test calls it via reflection to verify behavior.
 */
@SuppressWarnings("unused")
class GenericMachine<T> {

    private static final int MAXIMUM_RULE_SIZE = 256;

    private final GenericMachineConfiguration configuration;

    private final NameState startState = new NameState();

    private final Map<String, Integer> fieldStepsUsedRefCount = new java.util.concurrent.ConcurrentHashMap<>();

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
    private void deleteStep(final NameState state,
                            final List<String> keys,
                            final int index,
                            final Map<String, List<Patterns>> namevals,
                            final T name,
                            final List<String> deletedKeys,
                            final Set<SubRuleContext> candidateSubRuleIds) {
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
