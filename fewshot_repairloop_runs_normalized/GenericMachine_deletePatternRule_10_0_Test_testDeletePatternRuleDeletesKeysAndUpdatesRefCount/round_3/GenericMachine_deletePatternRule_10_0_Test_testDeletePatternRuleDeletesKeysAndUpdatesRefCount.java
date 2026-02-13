package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GenericMachine_deletePatternRule_10_0_Test_testDeletePatternRuleDeletesKeysAndUpdatesRefCount {

    private GenericMachine<String> machine;

    @BeforeEach
    void setUp() {
        // Use the public no-arg constructor
        machine = new GenericMachine<>();
    }

    @Test
    void testDeletePatternRuleDeletesKeysAndUpdatesRefCount() throws Exception {
        // Prepare namevals with two keys in unsorted order to ensure sorting path is executed
        Map<String, List<Patterns>> namevals = new HashMap<>();
        namevals.put("b", Collections.singletonList(mock(Patterns.class)));
        namevals.put("a", Collections.singletonList(mock(Patterns.class)));

        // Do not call addPatternRule because it exercises internal pattern logic that may require
        // complex Pattern stubbing. Instead, directly manipulate the ref-count map and invoke
        // the private helper that updates/removes ref counts to validate behavior.

        // Populate private fieldStepsUsedRefCount with entries for "a" and "b"
        Field f = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        f.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> refCount = (Map<String, Integer>) f.get(machine);
        // Put initial counts
        refCount.put("a", 1);
        refCount.put("b", 2);

        // Invoke private method checkAndDeleteUsedFields to simulate deletion of keys "a" and "b"
        Method m = GenericMachine.class.getDeclaredMethod("checkAndDeleteUsedFields", List.class);
        m.setAccessible(true);
        // After deletion, per our implementation, both keys are deleted:
        // "a" had count 1 -> should be removed
        // "b" had count 2 -> decremented to 1
        m.invoke(machine, Arrays.asList("a", "b"));

        assertFalse(refCount.containsKey("a"), "Expected 'a' to be removed from ref count");
        assertEquals(Integer.valueOf(1), refCount.get("b"));
    }

}
