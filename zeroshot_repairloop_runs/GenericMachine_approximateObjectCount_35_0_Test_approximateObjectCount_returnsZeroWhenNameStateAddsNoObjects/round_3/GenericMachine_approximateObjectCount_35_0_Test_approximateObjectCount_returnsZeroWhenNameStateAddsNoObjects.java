package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
     * This class implements both common observed signatures of gatherObjects:
     *   void gatherObjects(Set<Object> objectSet, int maxObjectCount)
     * and
     *   void gatherObjects(Set<Object> objectSet)
     *
     * We avoid using @Override to remain compatible if only one of the signatures exists.
     */
    static class CountingNameState extends NameState {

        private final int toAdd;

        private final boolean respectMax;

        CountingNameState(int toAdd, boolean respectMax) {
            this.toAdd = toAdd;
            this.respectMax = respectMax;
        }

        // If the NameState implementation uses the (Set, int) signature this will be used.
        public void gatherObjects(Set<Object> objectSet, int maxObjectCount) {
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
        }

        // If the NameState implementation uses the (Set) signature this will be used.
        public void gatherObjects(Set<Object> objectSet) {
            // Delegate to the two-arg version with effectively unbounded max.
            gatherObjects(objectSet, Integer.MAX_VALUE);
        }
    }

    // Helper to replace the private final startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
        // Remove final modifier so we can set the field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);
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
