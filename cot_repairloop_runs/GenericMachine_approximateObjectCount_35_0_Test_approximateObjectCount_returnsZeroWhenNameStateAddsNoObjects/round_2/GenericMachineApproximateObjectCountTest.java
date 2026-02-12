package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for GenericMachine.approximateObjectCount(...)
 *
 * These tests replace the private (final) startState field in GenericMachine
 * via reflection with a controllable NameState implementation that adds a
 * programmable number of distinct objects when gatherObjects(...) is called.
 *
 * Tests included:
 *  - when NameState adds 0 objects the count is 0
 *  - when NameState respects the provided maxObjectCount the returned count is capped
 *  - when NameState ignores maxObjectCount the returned count is the number of objects added
 *  - the deprecated no-arg approximateObjectCount() delegates to approximateObjectCount(int)
 */
public class GenericMachineApproximateObjectCountTest {

    /**
     * A Test helper that overrides NameState.gatherObjects to controllably add objects.
     *
     * Observed signature in the real NameState class:
     *   public void gatherObjects(Set<Object> objectSet, int maxObjectCount)
     */
    static class CountingNameState extends NameState {
        private final int toAdd;
        private final boolean respectMax;

        CountingNameState(int toAdd, boolean respectMax) {
            this.toAdd = toAdd;
            this.respectMax = respectMax;
        }

        @Override
        public void gatherObjects(Set<Object> objectSet, int maxObjectCount) {
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                // Add distinct objects so Set size increases
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
        }
    }

    /**
     * Replace the private final startState field on GenericMachine with the provided replacement.
     * This uses reflection and removes the final modifier so set(...) will succeed.
     */
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);

        // Remove final modifier so we can set the field (works on Java 8)
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);

        startField.set(machine, replacement);
    }

    @Test
    public void approximateObjectCount_returnsZeroWhenNameStateAddsNoObjects() throws Exception {
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);

        replaceStartState(machine, new CountingNameState(0, true));

        int result = machine.approximateObjectCount(100);
        assertEquals(0, result, "If NameState adds no objects the count should be zero");
    }

    @Test
    public void approximateObjectCount_respectsProvidedMaxWhenNameStateDoes() throws Exception {
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);

        // NameState will add up to 10 objects but respect the provided max
        replaceStartState(machine, new CountingNameState(10, true));

        int result = machine.approximateObjectCount(5);
        assertEquals(5, result, "When NameState respects max the returned count should be capped by maxObjectCount");
    }

    @Test
    public void approximateObjectCount_ignoresProvidedMaxWhenNameStateDoesNotRespectIt() throws Exception {
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);

        // NameState will add 8 objects and will ignore the provided max
        replaceStartState(machine, new CountingNameState(8, false));

        int result = machine.approximateObjectCount(3);
        assertEquals(8, result, "When NameState ignores max the returned count should be the number actually added");
    }

    @Test
    public void deprecatedNoArgApproximateObjectCount_delegatesToIntVariant() throws Exception {
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);

        // NameState will add 3 objects and respects max; calling no-arg should delegate to Integer.MAX_VALUE
        replaceStartState(machine, new CountingNameState(3, true));

        int resultNoArg = machine.approximateObjectCount();
        int resultWithHugeMax = machine.approximateObjectCount(Integer.MAX_VALUE);

        assertEquals(resultWithHugeMax, resultNoArg, "No-arg approximateObjectCount should delegate to the int variant");
        assertEquals(3, resultNoArg, "Expected count of 3 from the test NameState");
    }
}
