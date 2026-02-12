package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Fixed unit test for GenericMachine.approximateObjectCount.
 *
 * The original failure was due to test using JUnit 5 imports while the test runtime
 * provides JUnit 4. This version uses JUnit 4 (@Test and Assert).
 *
 * The test replaces the private startState field via reflection so we can control
 * what objects are gathered. CountingNameState overrides NameState.gatherObjects
 * (must be public to match the real NameState signature).
 */
public class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_respectsMaxAndCountsObjects {

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

    // Helper to replace the private (possibly final) startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
        startField.set(machine, replacement);
    }

    @Test
    public void approximateObjectCount_respectsMaxAndCountsObjects() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that will add 5 objects and respects max
        replaceStartState(machine, new CountingNameState(5, true));
        // act & assert
        int counted = machine.approximateObjectCount(3);
        assertEquals("Should return min(actualObjects, maxObjectCount)", 3, counted);
        // a larger max should return full count (5)
        int fullCount = machine.approximateObjectCount(10);
        assertEquals("Should return actual object count when max is large enough", 5, fullCount);
    }
}
