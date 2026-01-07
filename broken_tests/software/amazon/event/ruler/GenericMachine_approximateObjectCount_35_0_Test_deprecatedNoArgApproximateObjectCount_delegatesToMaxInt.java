package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

class GenericMachine_approximateObjectCount_35_0_Test_deprecatedNoArgApproximateObjectCount_delegatesToMaxInt {

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

    // Helper to replace the private final startState field via reflection
    private static void replaceStartState(GenericMachine machine, NameState replacement) throws Exception {
        Field startField = GenericMachine.class.getDeclaredField("startState");
        startField.setAccessible(true);
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
