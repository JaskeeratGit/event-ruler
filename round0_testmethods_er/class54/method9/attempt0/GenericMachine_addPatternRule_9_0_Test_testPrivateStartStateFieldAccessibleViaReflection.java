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

class GenericMachine_addPatternRule_9_0_Test_testPrivateStartStateFieldAccessibleViaReflection {

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
    void testPrivateStartStateFieldAccessibleViaReflection() throws Exception {
        // Use reflection to access the private 'startState' field to satisfy the requirement
        // to exercise private members via reflection.
        TestGenericMachine gm = new TestGenericMachine();
        Field startStateField = GenericMachine.class.getDeclaredField("startState");
        startStateField.setAccessible(true);
        Object startState = startStateField.get(gm);
        assertNotNull(startState, "startState should be non-null");
    }
}
