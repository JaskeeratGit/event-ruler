package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericMachine_approximateObjectCount_35_0_Test_deprecatedNoArgApproximateObjectCount_delegatesToMaxInt {

    /**
     * A Test helper that overrides NameState.gatherObjects to controllably add objects.
     *
     * Note: This class implements both potential observed signatures to be robust:
     *   void gatherObjects(Set<Object> objectSet, int maxObjectCount)
     *   void gatherObjects(Set<Object> objectSet, Integer maxObjectCount)
     */
    static class CountingNameState extends GenericMachine.NameState {

        private final int toAdd;

        private final boolean respectMax;

        CountingNameState(int toAdd, boolean respectMax) {
            this.toAdd = toAdd;
            this.respectMax = respectMax;
        }

        // Support primitive int signature
        @Override
        public void gatherObjects(Set<Object> objectSet, int maxObjectCount) {
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
        }

        // Support boxed Integer signature if present in some versions
        public void gatherObjects(Set<Object> objectSet, Integer maxObjectCount) {
            int max = (maxObjectCount == null) ? Integer.MAX_VALUE : maxObjectCount;
            gatherObjects(objectSet, max);
        }
    }

    // Helper to replace the private final startState field via reflection
    private static void replaceStartState(GenericMachine machine, GenericMachine.NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);

        // Remove final modifier so we can set the field on all JDKs
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);
        } catch (NoSuchFieldException ignored) {
            // Some runtimes may not have the modifiers field; ignore if absent
        }

        startField.set(machine, replacement);
    }

    @Test
    void deprecatedNoArgApproximateObjectCount_delegatesToMaxInt() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that will add 7 objects and respects max
        replaceStartState(machine, new CountingNameState(7, true));
        // act
        // deprecated no-arg delegates to int-max
        int result = machine.approximateObjectCount();
        // assert
        assertEquals(7, result, "No-arg deprecated method should delegate to approximateObjectCount(Integer.MAX_VALUE)");
    }

}
