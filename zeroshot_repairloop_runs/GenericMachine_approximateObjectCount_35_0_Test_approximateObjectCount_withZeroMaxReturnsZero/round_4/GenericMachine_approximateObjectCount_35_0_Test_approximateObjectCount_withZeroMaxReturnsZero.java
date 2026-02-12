package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenericMachine_approximateObjectCount_35_0_Test_approximateObjectCount_withZeroMaxReturnsZero {

    @Test
    void approximateObjectCount_withZeroMaxReturnsZero() {
        GenericMachineConfiguration cfg = new GenericMachineConfiguration(false, false);
        GenericMachine<?> machine = new GenericMachine<>(cfg);
        int result = machine.approximateObjectCount(0);
        assertEquals(0, result, "If maxObjectCount is zero, returned count must be zero");
    }
}
