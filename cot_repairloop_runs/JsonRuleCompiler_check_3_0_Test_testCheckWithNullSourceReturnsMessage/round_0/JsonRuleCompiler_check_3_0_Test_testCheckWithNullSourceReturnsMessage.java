package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        // Cast null to String to disambiguate overloaded check(...) methods (byte[] and InputStream overloads).
        String result = JsonRuleCompiler.check((String) null);
        assertNotNull(result, "Null source should return an error message (not null).");
        assertFalse(result.isEmpty(), "Error message should not be empty.");
    }

}
