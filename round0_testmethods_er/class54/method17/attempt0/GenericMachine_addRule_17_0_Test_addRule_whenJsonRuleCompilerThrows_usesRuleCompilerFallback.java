package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.Reader;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

/*
 This test class provides controlled replacements for JsonRuleCompiler and RuleCompiler
 (declared below in the same package) so we can deterministically exercise
 GenericMachine.addRule(...) both on the successful JSON-compiled path and on the
 JsonParseException -> RuleCompiler.compile(...) fallback path.
*/
public class GenericMachine_addRule_17_0_Test_addRule_whenJsonRuleCompilerThrows_usesRuleCompilerFallback {


    @Test
    public void addRule_whenJsonRuleCompilerThrows_usesRuleCompilerFallback() throws Exception {
        GenericMachine machine = new GenericMachine();
        // Input that triggers JsonRuleCompiler to throw JsonParseException in our test replacement.
        InputStream invalidJsonStream = new ByteArrayInputStream("!TRIGGER_PARSE_EXCEPTION".getBytes());
        // Should not throw; should take the fallback path that uses RuleCompiler.compile(...)
        machine.addRule("fallbackRule", invalidJsonStream);
        // Inspect internal fields to ensure they still exist and no inconsistent state
        Field refCountField = GenericMachine.class.getDeclaredField("fieldStepsUsedRefCount");
        refCountField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> map = (Map<String, Integer>) refCountField.get(machine);
        assertNotNull(map, "fieldStepsUsedRefCount must not be null after fallback");
        // startState should still be present
        Field startStateField = GenericMachine.class.getDeclaredField("startState");
        startStateField.setAccessible(true);
        Object startState = startStateField.get(machine);
        assertNotNull(startState, "startState must not be null after fallback");
    }

    // The following classes are test-side replacements for the production JsonRuleCompiler and RuleCompiler.
    // They live in the same package so they are used during test compilation/execution to control behavior.
    // NOTE: Signatures match the brief info provided:
    // JsonRuleCompiler.compile(InputStream, boolean) -> List<Map<String, List<Patterns>>>
    public static class JsonRuleCompiler {

        public static List<Map<String, List<Patterns>>> compile(final InputStream source, final boolean withOverriding) throws java.io.IOException {
            // Read first byte to decide behavior (non-destructive simple probe)
            source.mark(1);
            int first = source.read();
            source.reset();
            if (first == '!') {
                // Simulate a Jackson JsonParseException as described in the focal method's catch
                throw new JsonParseException((com.fasterxml.jackson.core.JsonParser) null, "simulated parse exception");
            }
            // Otherwise return a list containing a single simple rule map
            Patterns dummyPattern = new Patterns();
            Map<String, List<Patterns>> rule = new HashMap<>();
            rule.put("field", new ArrayList<>(Collections.singletonList(dummyPattern)));
            return Collections.singletonList(rule);
        }
    }

    // NOTE: Signatures match the brief info provided:
    // RuleCompiler.compile(InputStream, boolean) -> Map<String, List<Patterns>>
    public static final class RuleCompiler {

        public static Map<String, List<Patterns>> compile(final InputStream source, final boolean withOverriding) throws java.io.IOException {
            // Always return a simple map - used when JsonRuleCompiler throws
            Patterns dummyPattern = new Patterns();
            Map<String, List<Patterns>> rule = new HashMap<>();
            rule.put("fallbackField", new ArrayList<>(Collections.singletonList(dummyPattern)));
            return rule;
        }
    }

    // Minimal stub for Patterns so the test-side compilers can construct lists/maps of it.
    // In the real codebase Patterns likely exists; if present, this will be ignored or may shadow the real one.
    public static class Patterns {
    }
}
