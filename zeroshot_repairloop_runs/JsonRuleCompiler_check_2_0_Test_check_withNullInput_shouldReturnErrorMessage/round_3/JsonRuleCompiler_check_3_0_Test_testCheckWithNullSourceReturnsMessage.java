package software.amazon.event.ruler;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        // passing null should cause creation/parsing to fail and return an error message
        String result = JsonRuleCompiler.check((String) null, false);
        assertNotNull("Expected non-null error message for null input", result);
        assertFalse("Expected non-empty error message for null input", result.isEmpty());
    }

}
