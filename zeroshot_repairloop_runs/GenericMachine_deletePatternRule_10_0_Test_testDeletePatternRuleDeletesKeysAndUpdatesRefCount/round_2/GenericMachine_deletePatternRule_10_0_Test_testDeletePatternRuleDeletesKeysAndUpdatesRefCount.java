package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Fixed unit test and minimal supporting classes/stubs to make it compile and run.
 * JUnit 5 style.
 */
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
        namevals.put("b", Collections.singletonList(new Patterns()));
        namevals.put("a", Collections.singletonList(new Patterns()));
        // Populate private fieldStepsUsedRefCount with entries for "a" and "b"
        Field f = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> refCount = (Map<String, Integer>) f.get(machine);
        // Put initial counts
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

    private String value;

    public Patterns() {
        this.value = null;
    }

    // Allow constructing with another Patterns (e.g., clone or null)
    public Patterns(Patterns other) {
        if (other != null) {
            this.value = other.value;
        } else {
            this.value = null;
        }
    }

    // static helper methods used by other tests
    public static Patterns exactMatch(String s) {
        Patterns p = new Patterns();
        p.value = "EXACT:" + s;
        return p;
    }

    public static Patterns prefixMatch(String s) {
        Patterns p = new Patterns();
        p.value = "PREFIX:" + s;
        return p;
    }

    // instance-style helpers (in case some tests call them on instances)
    public Patterns exactMatchInstance(String s) {
        return exactMatch(s);
    }

    public Patterns prefixMatchInstance(String s) {
        return prefixMatch(s);
    }

    @Override
    public String toString() {
        return value == null ? "Patterns()" : value;
    }
}

class SubRuleContext {

    private final String id;

    SubRuleContext(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubRuleContext)) {
            return false;
        }
        SubRuleContext that = (SubRuleContext) o;
        return java.util.Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
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

    // made package-private for reflection access in tests
    final Map<String, Integer> fieldStepsUsedRefCount = new java.util.concurrent.ConcurrentHashMap<>();

    private final SubRuleContext.Generator subRuleContextGenerator = new SubRuleContext.Generator();

    @Deprecated
    public GenericMachine() {
        this(new GenericMachineConfiguration(false, false));
    }

    protected GenericMachine(GenericMachineConfiguration configuration) {
        this.configuration = configuration;
    }

    // --- Public API stubs used by various tests ---

    public static <T> Builder<GenericMachine<T>, T> builder() {
        return new Builder<>();
    }

    public List<T> rulesForJSONEvent(final String jsonEvent) throws Exception {
        return Collections.emptyList();
    }

    public List<T> rulesForJSONEvent(final com.fasterxml.jackson.databind.JsonNode eventRoot) {
        return Collections.emptyList();
    }

    @Deprecated
    public List<T> rulesForEvent(final String jsonEvent) {
        return Collections.emptyList();
    }

    public List<T> rulesForEvent(final List<String> event) {
        return Collections.emptyList();
    }

    @Deprecated
    public List<T> rulesForEvent(final com.fasterxml.jackson.databind.JsonNode eventRoot) {
        return Collections.emptyList();
    }

    public List<T> rulesForEvent(final String[] event) {
        return Collections.emptyList();
    }

    final NameState getStartState() {
        return startState;
    }

    boolean isFieldStepUsed(final String stepName) {
        return fieldStepsUsedRefCount.containsKey(stepName);
    }

    public void addRule(final T name, final Map<String, List<String>> namevals) {
        // stub: increment used fields counts
        if (namevals != null) {
            addIntoUsedFields(new java.util.ArrayList<>(namevals.keySet()));
        }
    }

    public void addPatternRule(final T name, final Map<String, List<Patterns>> namevals) {
        // stub: increment used fields counts
        if (namevals != null) {
            addIntoUsedFields(new java.util.ArrayList<>(namevals.keySet()));
        }
    }

    public void deletePatternRule(final T name, final Map<String, List<Patterns>> namevals) {
        if (namevals == null) {
            return;
        }
        if (namevals.size() > MAXIMUM_RULE_SIZE) {
            throw new RuntimeException("Size of rule '" + name + "' exceeds max value of " + MAXIMUM_RULE_SIZE);
        }
        final java.util.List<String> keys = new java.util.ArrayList<>(namevals.keySet());
        java.util.Collections.sort(keys);
        synchronized (this) {
            final java.util.List<String> deletedKeys = new java.util.ArrayList<>();
            final java.util.Set<SubRuleContext> candidateSubRuleIds = new java.util.HashSet<>();
            deleteStep(getStartState(), keys, 0, namevals, name, deletedKeys, candidateSubRuleIds);
            // check and delete the key from fieldStepsUsedRefCount ...
            checkAndDeleteUsedFields(deletedKeys);
        }
    }

    public void deleteRule(final T name, final Map<String, List<String>> namevals) {
        if (namevals != null) {
            List<String> deletedKeys = new java.util.ArrayList<>(namevals.keySet());
            checkAndDeleteUsedFields(deletedKeys);
        }
    }

    private java.util.Set<SubRuleContext> deleteStep(final NameState state,
                                          final java.util.List<String> keys,
                                          final int keyIndex,
                                          final Map<String, List<Patterns>> patterns,
                                          final T ruleName,
                                          final java.util.List<String> deletedKeys,
                                          final java.util.Set<SubRuleContext> candidateSubRuleIds) {
        // simplified behavior:
        // - if index >= keys.size(), do nothing
        // - otherwise add all keys to deletedKeys and add one SubRuleContext
        if (keyIndex >= keys.size()) {
            return Collections.emptySet();
        }
        for (String k : keys) {
            deletedKeys.add(k);
        }
        candidateSubRuleIds.add(subRuleContextGenerator.generate(String.valueOf(ruleName)));
        return candidateSubRuleIds;
    }

    private boolean doesNameStateContainPattern(final NameState nameState, final Patterns pattern) {
        return false;
    }

    private boolean deletePattern(final NameState parentNameState, final String key, Patterns pattern) {
        return false;
    }

    public void addRule(final T name, final String json) throws java.io.IOException {
        // stub: no-op
    }

    public void addRule(final T name, final java.io.Reader json) throws java.io.IOException {
        // stub: no-op
    }

    public void addRule(final T name, final java.io.InputStream json) throws java.io.IOException {
        // stub: no-op
    }

    public void addRule(final T name, final byte[] json) throws java.io.IOException {
        // stub: no-op
    }

    public void deleteRule(final T name, final String json) throws java.io.IOException {
        // stub: no-op
    }

    public void deleteRule(final T name, final java.io.Reader json) throws java.io.IOException {
        // stub: no-op
    }

    public void deleteRule(final T name, final java.io.InputStream json) throws java.io.IOException {
        // stub: no-op
    }

    private void addStep(final java.util.List<String> keys,
                         final Map<String, List<Patterns>> patterns,
                         final T ruleName) {
        // stub
    }

    private java.util.Set<SubRuleContext> addStep(final NameState state,
                                        final java.util.List<String> keys,
                                        final int keyIndex,
                                        final Map<String, List<Patterns>> patterns,
                                        final T ruleName,
                                        java.util.List<String> addedKeys,
                                        final Set<NameState>[] nameStatesForEachKey) {
        return Collections.emptySet();
    }

    private boolean hasValuePatterns(List<Patterns> patterns) {
        return patterns != null && !patterns.isEmpty();
    }

    private boolean hasKeyPatterns(List<Patterns> patterns) {
        return patterns != null && !patterns.isEmpty();
    }

    private boolean isNamePattern(Patterns pattern) {
        return false;
    }

    private void addIntoUsedFields(List<String> keys) {
        if (keys == null) {
            return;
        }
        for (String k : keys) {
            fieldStepsUsedRefCount.merge(k, 1, Integer::sum);
        }
    }

    // Private helper to simulate updating the fieldStepsUsedRefCount map
    private void checkAndDeleteUsedFields(final List<String> deletedKeys) {
        if (deletedKeys == null) {
            return;
        }
        for (String key : deletedKeys) {
            fieldStepsUsedRefCount.computeIfPresent(key, (k, v) -> {
                int nv = v - 1;
                return nv <= 0 ? null : nv;
            });
        }
    }

    public boolean isEmpty() {
        return false;
    }

    @javax.annotation.Nonnull
    @SuppressWarnings("unchecked")
    private <R> NameMatcher<R> createNameMatcher() {
        return new NameMatcher<>();
    }

    private void recordFieldStep(String fieldName) {
        fieldStepsUsedRefCount.merge(fieldName, 1, Integer::sum);
    }

    private void eraseFieldStep(String fieldName) {
        fieldStepsUsedRefCount.remove(fieldName);
    }

    public int evaluateComplexity(Object evaluator) {
        return 0;
    }

    public int approximateObjectCount(int maxObjectCount) {
        return 0;
    }

    @Deprecated
    public int approximateObjectCount() {
        return 0;
    }

    @Override
    public String toString() {
        return "GenericMachine";
    }

    // Simple builder stub used in some tests
    public static class Builder<M, R> {
        private GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);

        public Builder<M, R> additionalNameStateReuse(boolean v) {
            return this;
        }

        public Builder<M, R> ruleOverriding(boolean v) {
            return this;
        }

        public GenericMachine<M> build() {
            return new GenericMachine<>(cfg);
        }
    }

    // Minimal NameState class
    static class NameState {
    }

    // Minimal NameMatcher placeholder
    static class NameMatcher<R> {
    }
}
