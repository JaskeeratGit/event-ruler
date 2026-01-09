package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

public class GenericMachine_deleteRule_19_0_Test_testDeleteRule_usesJsonRuleCompiler_whenNoParseException {

    @Test
    public void testDeleteRule_usesJsonRuleCompiler_whenNoParseException() throws IOException {
        // configuration: ruleOverriding = false (passed to compiler)
        GenericMachineConfiguration configuration = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(configuration);
        // Prepare fake compiled rules: two maps to simulate multiple pattern rules returned
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Map<String, List>> fakeCompiledRules = new ArrayList<>();
        fakeCompiledRules.add(new HashMap<>());
        fakeCompiledRules.add(new HashMap<>());
        try (MockedStatic<JsonRuleCompiler> mockJson = Mockito.mockStatic(JsonRuleCompiler.class);
            MockedStatic<RuleCompiler> mockRule = Mockito.mockStatic(RuleCompiler.class)) {
            // JsonRuleCompiler.compile should be invoked and return our fake rules
            mockJson.when(() -> JsonRuleCompiler.compile("some-json", configuration.isRuleOverriding())).thenReturn((List) fakeCompiledRules);
            // Ensure RuleCompiler.compile is not called in this scenario
            // if erroneously called, it would return something harmless
            // if erroneously called, it would return something harmless
            mockRule.when(() -> RuleCompiler.compile("some-json", configuration.isRuleOverriding())).thenReturn((Map) new HashMap<>());
            // Call focal method and assert it doesn't throw
            assertDoesNotThrow(() -> machine.deleteRule("ruleName", "some-json"));
            // Verify static interactions
            mockJson.verify(() -> JsonRuleCompiler.compile("some-json", configuration.isRuleOverriding()), times(1));
            mockRule.verifyNoInteractions();
        }
    }

}
