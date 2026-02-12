package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_whenNameStateIgnoresMaxStillHonorsProvidedMax {

    /**
     * A Test helper that overrides NameState.gatherObjects to controllably add objects.
     *
     * Note: This class relies on the (observed) signature:
     *   void gatherObjects(Set<Object> objectSet, int maxObjectCount)
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
        void gatherObjects(Set<Object> objectSet, int maxObjectCount) {
            // If respectMax is true, stop at maxObjectCount.
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                // Use distinct objects to ensure set size increments.
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
        }
    }

    // Helper to replace the private startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
        // Try to remove final modifier so we can set the field reliably.
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some JVM implementations do not expose the 'modifiers' field; ignore if not present.
        }
        startField.set(machine, replacement);
    }

    @Test
    void approximateObjectCount_whenNameStateIgnoresMaxStillHonorsProvidedMax() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that does NOT respect max (will attempt to add 10)
        replaceStartState(machine, new CountingNameState(10, false));
        // act: even if NameState ignores max, approximateObjectCount returns min(size, max)
        int result = machine.approximateObjectCount(4);
        // Because our fake NameState will attempt to add 10 distinct objects, objectSet.size() will be 10,
        // so the method should return min(10, 4) = 4.
        assertEquals(4, result, "Result should be min(objectSet.size(), maxObjectCount) even if NameState ignores max argument");
    }

}
