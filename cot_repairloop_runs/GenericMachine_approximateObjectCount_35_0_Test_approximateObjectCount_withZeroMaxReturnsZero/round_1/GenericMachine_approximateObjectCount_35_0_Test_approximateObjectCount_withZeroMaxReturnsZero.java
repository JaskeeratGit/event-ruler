package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Fixed unit test(s) for GenericMachine.approximateObjectCount(int).
 *
 * This class uses JUnit 4 because the test classpath for this project does not provide
 * JUnit 5 (org.junit.jupiter.api). The previous version used JUnit 5 imports which
 * triggered compilation errors. The test also corrects the visibility of overridden
 * methods by keeping them public (matching NameState.gatherObjects).
 */
public class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_withZeroMaxReturnsZero {

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
        // If the field is final, this still works for most JVMs for tests; otherwise more complex hacks would be needed.
        startField.set(machine, replacement);
    }

    @Test
    public void approximateObjectCount_withZeroMaxReturnsZero() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that would add objects (but should respect max)
        replaceStartState(machine, new CountingNameState(100, true));
        // act
        int result = machine.approximateObjectCount(0);
        // assert
        assertEquals("If maxObjectCount is zero, returned count must be zero", 0, result);
    }

    @Test
    public void approximateObjectCount_withSmallMaxIsRespected() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that respects max and would try to add 100 objects
        replaceStartState(machine, new CountingNameState(100, true));
        // act
        int result = machine.approximateObjectCount(10);
        // assert
        assertEquals("When gatherObjects respects max, count should not exceed provided max", 10, result);
    }

    @Test
    public void approximateObjectCount_withoutRespectingMaxCountsAllAdded() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that ignores max and adds exactly 7 objects
        replaceStartState(machine, new CountingNameState(7, false));
        // act
        int result = machine.approximateObjectCount(3); // even though max is small, state ignores it
        // assert
        assertEquals("If gatherObjects ignores max, returned count should reflect all added objects", 7, result);
    }
}
