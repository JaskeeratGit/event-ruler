package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    void testCheckWithNullSourceReturnsMessage() {
        // passing null should cause creation/parsing to fail and return an error message
        String result = JsonRuleCompiler.check((String) null, false);
        assertNotNull(result, "Expected non-null error message for null input");
        assertFalse(result.isEmpty(), "Expected non-empty error message for null input");
    }

}
