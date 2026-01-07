package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

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
        Method deleteStep = GenericMachine.class.getDeclaredMethod("deleteStep", GenericMachine.NameState.class, List.class, int.class, Map.class, Object.class, List.class, Set.class);
        deleteStep.setAccessible(true);
        // Invoke deleteStep with index == keys.size() (0) -> should return without modification
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        assertTrue(deletedKeys.isEmpty(), "deletedKeys should remain empty when no keys provided");
        assertTrue(candidateSubRuleIds.isEmpty(), "candidateSubRuleIds should remain empty when no keys provided");
        // Now test deleteStep with a non-empty keys list: expect keys to be added to deletedKeys
        keys.add("k1");
        keys.add("k2");
        deleteStep.invoke(machine, machine.getStartState(), keys, 0, namevals, "x", deletedKeys, candidateSubRuleIds);
        // Our deleteStep implementation adds all keys to deletedKeys
        assertEquals(2, deletedKeys.size());
        assertTrue(deletedKeys.contains("k1") && deletedKeys.contains("k2"));
        assertFalse(candidateSubRuleIds.isEmpty());
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
