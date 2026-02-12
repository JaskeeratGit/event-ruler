package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import sun.misc.Unsafe;

class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_respectsMaxAndCountsObjects {

    // Helper to create a mock NameState that adds `toAdd` distinct objects when gatherObjects is called.
    private static NameState createCountingNameState(final int toAdd, final boolean respectMax) {
        final NameState ns = mock(NameState.class);
        doAnswer(invocation -> {
            @SuppressWarnings("unchecked")
            Set<Object> objectSet = (Set<Object>) invocation.getArgument(0);
            int maxObjectCount = (Integer) invocation.getArgument(1);
            int limit = respectMax ? Math.min(toAdd, maxObjectCount) : toAdd;
            for (int i = 0; i < limit; i++) {
                objectSet.add(new StringBuilder("obj-" + i).toString());
            }
            return null;
        }).when(ns).gatherObjects(any(Set.class), anyInt());
        return ns;
    }

    // Helper to replace the private final startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);

        // Attempt to clear the FINAL modifier so we can set the field (works on many JDKs).
        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(startField, startField.getModifiers() & ~Modifier.FINAL);
            startField.set(machine, replacement);
            return;
        } catch (NoSuchFieldException | IllegalAccessException | SecurityException ignored) {
            // On some JVMs the 'modifiers' field is not present or not writable; proceed to other strategies.
        }

        // Fallback: try Unsafe to set the field (works on Java 8).
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) theUnsafeField.get(null);
            long offset = unsafe.objectFieldOffset(startField);
            unsafe.putObject(machine, offset, replacement);
            return;
        } catch (Throwable ignored) {
            // If Unsafe isn't available or fails, try the last resort below.
        }

        // Last resort: try direct set (may succeed on some JVMs).
        startField.set(machine, replacement);
    }

    @Test
    void approximateObjectCount_respectsMaxAnd_countsObjects() throws Exception {
        // arrange
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(cfg);
        // replace start state with one that will add 5 objects and respects max
        replaceStartState(machine, createCountingNameState(5, true));
        // act & assert
        int counted = machine.approximateObjectCount(3);
        assertEquals(3, counted, "Should return min(actualObjects, maxObjectCount)");
        // a larger max should return full count (5)
        int fullCount = machine.approximateObjectCount(10);
        assertEquals(5, fullCount, "Should return actual object count when max is large enough");
    }

}
