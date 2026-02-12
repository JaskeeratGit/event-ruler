package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for GenericMachine.approximateObjectCount(int).
 */
class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_returnsZeroWhenNameStateAddsNoObjects {

    /**
     * A Test helper that overrides NameState.gatherObjects to controllably add objects.
     *
     * Note: This class relies on the (observed) signature:
     *   public void gatherObjects(Set<Object> objectSet, int maxObjectCount)
     * in the real NameState class.
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

    // Helper to replace the private final startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
        startField.set(machine, replacement);
    }

    @Test
    void approximateObjectCount_returnsZeroWhenNameStateAddsNoObjects() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that will add 0 objects
        replaceStartState(machine, new CountingNameState(0, true));
        // act
        int result = machine.approximateObjectCount(100);
        // assert
        assertEquals(0, result, "If NameState adds no objects the count should be zero");
    }
}
