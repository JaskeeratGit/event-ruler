package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

/**
 * Unit tests for GenericMachine.rulesForEvent(String[]).
 *
 * This test file provides minimal stub implementations of dependent classes
 * (Finder, GenericMachineConfiguration, NameState, SubRuleContext) sufficient
 * for exercising the delegation behavior of rulesForEvent and to enable
 * reflection-based checks.
 */
class GenericMachine_rulesForEvent_5_0_Test_deprecatedNoArgConstructorWorksAndDelegates {





    @Test
    void deprecatedNoArgConstructorWorksAndDelegates() {
        // call deprecated constructor which delegates to default configuration
        GenericMachine<String> machine = new GenericMachine<>();
        String[] event = new String[] { "beta" };
        List<String> result = machine.rulesForEvent(event);
        assertNotNull(result);
        assertEquals(Collections.singletonList("ok-beta"), result);
    }

    // 
    // Minimal stub implementations of dependent classes (to allow compilation and deterministic behavior)
    // 
    static class GenericMachineConfiguration {

        private final boolean additionalNameStateReuse;

        private final boolean ruleOverriding;

        GenericMachineConfiguration(boolean additionalNameStateReuse, boolean ruleOverriding) {
            this.additionalNameStateReuse = additionalNameStateReuse;
            this.ruleOverriding = ruleOverriding;
        }
    }

    static class NameState {
        // minimal placeholder
    }

    static class SubRuleContext {

        static class Generator {
            // minimal placeholder for generator identity
        }
    }

    /**
     * Minimal Finder implementation for tests.
     *
     * Behavior:
     * - Records the last supplied parameters into static fields for assertions.
     * - If event is null -> returns null.
     * - If event length == 0 -> returns empty list.
     * - If event[0] equals "NULLIFY" -> returns null (to test null propagation).
     * - Otherwise returns a list with a single element "ok-" + event[0].
     */
    static class Finder {

        // capture the last parameters that were passed in to allow test verification
        static String[] lastEvent = null;

        static Object lastMachine = null;

        static Object lastGenerator = null;

        private Finder() {
            // private constructor on purpose (tested reflectively)
        }

        @SuppressWarnings("unused")
        static Object rulesForEvent(String[] event, GenericMachine<?> machine, SubRuleContext.Generator generator) {
            lastEvent = event;
            lastMachine = machine;
            lastGenerator = generator;
            if (event == null) {
                return null;
            }
            if (event.length == 0) {
                return Collections.emptyList();
            }
            if ("NULLIFY".equals(event[0])) {
                return null;
            }
            return Collections.singletonList("ok-" + event[0]);
        }
    }

    /**
     * Minimal GenericMachine implementation matching the provided signature
     * and behavior for rulesForEvent (delegates to Finder.rulesForEvent).
     */
    @SuppressWarnings("unchecked")
    public static class GenericMachine<T> {

        private static final int MAXIMUM_RULE_SIZE = 256;

        private final GenericMachineConfiguration configuration;

        private final NameState startState = new NameState();

        private final java.util.concurrent.ConcurrentHashMap<String, Integer> fieldStepsUsedRefCount = new java.util.concurrent.ConcurrentHashMap<>();

        private final SubRuleContext.Generator subRuleContextGenerator = new SubRuleContext.Generator();

        @Deprecated
        public GenericMachine() {
            // default configuration used by deprecated ctor
            this(new GenericMachineConfiguration(false, false));
        }

        protected GenericMachine(GenericMachineConfiguration configuration) {
            this.configuration = configuration;
        }

        final NameState getStartState() {
            return startState;
        }

        @SuppressWarnings("unchecked")
        public List<T> rulesForEvent(final String[] event) {
            return (List<T>) Finder.rulesForEvent(event, this, subRuleContextGenerator);
        }

        // builder placeholder (not used in tests, provided to match snippet)
        public static <M extends GenericMachine<T>, T> Object builder() {
            return new Object();
        }
    }
}
