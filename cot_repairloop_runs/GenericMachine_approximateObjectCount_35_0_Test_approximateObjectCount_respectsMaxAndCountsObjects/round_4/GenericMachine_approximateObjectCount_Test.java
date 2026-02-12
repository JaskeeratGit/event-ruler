package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Fixed unit tests for GenericMachine.approximateObjectCount using JUnit 4.
 *
 * These tests replace the private startState field via reflection so we can control
 * what objects are gathered. CountingNameState overrides NameState.gatherObjects
 * (observed signature in NameState is: public void gatherObjects(Set<Object>, int)).
 */
public class GenericMachine_approximateObjectCount_Test {

    /**
     * A Test helper that overrides NameState.gatherObjects to controllably add objects.
     *
     * Note: This class relies on the (observed) signature in NameState:
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
            // If respectMax is true, stop at maxObjectCount.
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                // Use distinct objects to ensure set size increments.
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
        }
    }

    // Helper to replace the private (possibly final) startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
        // If the field is final, remove the final modifier so we can set it
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some JVMs may not allow changing modifiers; ignore and attempt to set anyway.
        }
        startField.set(machine, replacement);
    }

    @Test
    public void approximateObjectCount_respectsMaxAndCountsObjects() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that will add 5 objects and respects max
        replaceStartState(machine, new CountingNameState(5, true));
        // act & assert: when max smaller than actual, returns max
        int counted = machine.approximateObjectCount(3);
        assertEquals("Should return min(actualObjects, maxObjectCount)", 3, counted);
        // a larger max should return full count (5)
        int fullCount = machine.approximateObjectCount(10);
        assertEquals("Should return actual object count when max is large enough", 5, fullCount);
    }

    @Test
    public void approximateObjectCount_whenNameStateIgnoresMax_returnsActualCount() throws Exception {
        // arrange: NameState that ignores the provided max (respectMax = false)
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        replaceStartState(machine, new CountingNameState(5, false));
        // act: even though max is small, the NameState ignores it so we expect the actual count
        int counted = machine.approximateObjectCount(3);
        assertEquals("If the state ignores maxObjectCount, the returned value should be actual object count", 5, counted);
    }

    @Test
    public void approximateObjectCount_maxZero_returnsZeroWhenStateRespectsMax() throws Exception {
        // arrange: state that would add objects but respects max
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        replaceStartState(machine, new CountingNameState(5, true));
        // act: max is zero, should return zero
        int counted = machine.approximateObjectCount(0);
        assertEquals("When maxObjectCount is 0 and state respects max, result should be 0", 0, counted);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void deprecatedApproximateObjectCount_delegatesToMaxInt() throws Exception {
        // arrange: small known number of objects
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        replaceStartState(machine, new CountingNameState(2, true));
        // act: deprecated no-arg method should delegate to approximateObjectCount(Integer.MAX_VALUE)
        int counted = machine.approximateObjectCount();
        assertEquals("Deprecated no-arg method should return full count when using Integer.MAX_VALUE as limit", 2, counted);
    }
}
