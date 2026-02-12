package software.amazon.event.ruler;

import java.lang.reflect.Method;
import java.util.*;
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

/**
 * Unit tests for GenericMachine.addRule(T, Map)
 */
public class GenericMachine_addRule_8_0_Test_testAddRule_convertsStringsToPatterns_andDelegates {

    // A small test subclass to capture calls to addPatternRule
    // Placed in the same package so we can override package-private/protected methods.
    static class TestGenericMachine extends GenericMachine<String> {

        String capturedName;

        Map<String, List<Patterns>> capturedPatternMap;

        TestGenericMachine() {
            super(new GenericMachineConfiguration(false, false));
        }

        // Override the method that addRule delegates to so we can inspect inputs.
        @Override
        public void addPatternRule(final String name, final Map<String, List<Patterns>> patternMap) {
            this.capturedName = name;
            // make defensive deep copy for assertions
            this.capturedPatternMap = new HashMap<>();
            for (Map.Entry<String, List<Patterns>> e : patternMap.entrySet()) {
                this.capturedPatternMap.put(e.getKey(), new ArrayList<>(e.getValue()));
            }
        }
    }

    private TestGenericMachine gm;

    @BeforeEach
    void setUp() {
        gm = new TestGenericMachine();
    }

    @Test
    void testAddRule_convertsStringsToPatterns_andDelegates() {
        Map<String, List<String>> input = new HashMap<>();
        input.put("alpha", Arrays.asList("one", "two"));
        input.put("beta", Collections.singletonList("three"));
        gm.addRule("rule-1", input);
        // Verify delegation happened with same name
        assertEquals("rule-1", gm.capturedName);
        // Verify keys preserved
        assertNotNull(gm.capturedPatternMap);
        assertEquals(2, gm.capturedPatternMap.size());
        assertTrue(gm.capturedPatternMap.containsKey("alpha"));
        assertTrue(gm.capturedPatternMap.containsKey("beta"));
        // Verify values are converted to Patterns via Patterns.exactMatch(...)
        List<Patterns> expectedAlpha = Arrays.asList(Patterns.exactMatch("one"), Patterns.exactMatch("two"));
        List<Patterns> expectedBeta = Collections.singletonList(Patterns.exactMatch("three"));
        assertEquals(expectedAlpha, gm.capturedPatternMap.get("alpha"));
        assertEquals(expectedBeta, gm.capturedPatternMap.get("beta"));
    }



}
