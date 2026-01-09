package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

class GenericMachine_addPatternRule_9_0_Test_testAddPatternRuleThrowsWhenExceedsMax {

    // A testable subclass that attempts to capture the parameters passed into addStep.
    // The signature uses raw types and erased parameter types so it will successfully
    // override the original addStep method if its erasure matches (List, Map, Object).
    // If the original addStep is private in the real class, this will be a new method
    // in the subclass and will not intercept calls; tests relying on interception will fail in that case.
    static class TestGenericMachine extends GenericMachine {

        final AtomicReference<List> capturedKeys = new AtomicReference<>();

        final AtomicReference<Map> capturedNamePatterns = new AtomicReference<>();

        final AtomicReference<Object> capturedName = new AtomicReference<>();

        TestGenericMachine() {
            // Use package-private configuration constructor
            super(new GenericMachineConfiguration(false, false));
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void addStep(List keys, Map namePatterns, Object name) {
            // capture shallow copies to avoid accidental mutation in assertions
            capturedKeys.set(keys == null ? null : new ArrayList<>(keys));
            capturedNamePatterns.set(namePatterns == null ? null : new HashMap(namePatterns));
            capturedName.set(name);
        }
    }

    @Test
    void testAddPatternRuleThrowsWhenExceedsMax() throws Exception {
        // Use reflection to read the private MAXIMUM_RULE_SIZE constant so the test
        // does not hard-code the number and will adapt if the constant changes.
        Field maxField = GenericMachine.class.getDeclaredField("MAXIMUM_RULE_SIZE");
        maxField.setAccessible(true);
        // it's static final, so get(null)
        int max = (int) maxField.get(null);
        TestGenericMachine gm = new TestGenericMachine();
        // Build a map whose size is greater than MAXIMUM_RULE_SIZE to trigger the RuntimeException
        Map<String, List<Patterns>> tooLarge = new HashMap<>();
        for (int i = 0; i < max + 1; i++) {
            tooLarge.put("key-" + i, Collections.singletonList(new Patterns(null) {
            }));
        }
        RuntimeException ex = assertThrows(RuntimeException.class, () -> gm.addPatternRule("BigRule", tooLarge));
        String msg = ex.getMessage();
        assertNotNull(msg);
        assertTrue(msg.contains("exceeds") || msg.toLowerCase().contains("exceed"), "Expected exception message to mention exceeding max size, was: " + msg);
        assertTrue(msg.contains("BigRule"), "Expected rule name to be present in exception message");
        assertTrue(msg.contains(String.valueOf(max)), "Expected the configured max (" + max + ") to be present in the exception message");
    }


}
