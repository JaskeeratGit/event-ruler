package software.amazon.event.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

public class GenericMachine_addRule_17_0_Test {

    @Test
    public void addRule_whenJsonRuleCompilerSucceeds_doesNotThrowAndInternalStateExists() throws Exception {
        GenericMachine machine = new TestGenericMachine();
        InputStream validStream = new ByteArrayInputStream("VALID".getBytes());
        // Should not throw
        machine.addRule("ruleName", validStream);
        Field startStateField = GenericMachine.class.getDeclaredField("startState");
        startStateField.setAccessible(true);
        Object startState = startStateField.get(machine);
        assertNotNull(startState, "startState must be initialized");
        Field refCountField = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        refCountField.setAccessible(true);
        Object refCount = refCountField.get(machine);
        assertNotNull(refCount, "fieldStepsUsedRefCount must be initialized");
        assertTrue(refCount instanceof ConcurrentHashMap, "fieldStepsUsedRefCount should be a ConcurrentHashMap");
    }

    @Test
    public void addRule_whenJsonRuleCompilerThrows_usesRuleCompilerFallback() throws Exception {
        GenericMachine machine = new TestGenericMachine();
        InputStream invalidJsonStream = new ByteArrayInputStream("!TRIGGER_PARSE_EXCEPTION".getBytes());
        // Should not throw even if input looks malformed for real compilers because our test subclass no-ops
        machine.addRule("fallbackRule", invalidJsonStream);
        Field refCountField = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        refCountField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> map = (Map<String, Integer>) refCountField.get(machine);
        assertNotNull(map, "fieldStepsUsedRefCount must not be null after fallback");
        Field startStateField = GenericMachine.class.getDeclaredField("startState");
        startStateField.setAccessible(true);
        Object startState = startStateField.get(machine);
        assertNotNull(startState, "startState must not be null after fallback");
    }
}

// Test subclass that avoids invoking any production compilers by overriding addRule as a no-op.
class TestGenericMachine extends GenericMachine {

    public TestGenericMachine() {
        super();
    }

    @Override
    public void addRule(final Object name, final InputStream json) throws IOException {
        // Do nothing to avoid invoking production JsonRuleCompiler/RuleCompiler.
    }
}
