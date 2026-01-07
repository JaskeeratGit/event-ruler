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

public class GenericMachine_deleteRule_19_0_Test_testDeleteRule_fallsBackToRuleCompiler_onJsonParseException {


    @Test
    public void testDeleteRule_fallsBackToRuleCompiler_onJsonParseException() throws IOException {
        GenericMachineConfiguration configuration = new GenericMachineConfiguration(false, false);
        GenericMachine machine = new GenericMachine(configuration);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        Map<String, List> fakeSingleCompiledRule = new HashMap<>();
        try (MockedStatic<JsonRuleCompiler> mockJson = Mockito.mockStatic(JsonRuleCompiler.class);
            MockedStatic<RuleCompiler> mockRule = Mockito.mockStatic(RuleCompiler.class)) {
            // Make JsonRuleCompiler.compile throw JsonParseException to trigger fallback
            mockJson.when(() -> JsonRuleCompiler.compile("other-json", configuration.isRuleOverriding())).thenThrow(new JsonParseException(null, "simulate parse error"));
            // Set RuleCompiler.compile to return a single rule map
            mockRule.when(() -> RuleCompiler.compile("other-json", configuration.isRuleOverriding())).thenReturn((Map) fakeSingleCompiledRule);
            // Should not throw; deleteRule should catch JsonParseException and use RuleCompiler
            assertDoesNotThrow(() -> machine.deleteRule("ruleName", "other-json"));
            // Verify both static calls happened as expected
            mockJson.verify(() -> JsonRuleCompiler.compile("other-json", configuration.isRuleOverriding()), times(1));
            mockRule.verify(() -> RuleCompiler.compile("other-json", configuration.isRuleOverriding()), times(1));
        }
    }
}
