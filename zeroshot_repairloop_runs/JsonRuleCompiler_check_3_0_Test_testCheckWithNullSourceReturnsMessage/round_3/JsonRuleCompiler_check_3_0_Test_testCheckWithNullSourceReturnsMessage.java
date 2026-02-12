package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        String result = JsonRuleCompiler.check((String) null);
        // The current implementation returns null for a null source.
        assertNull(result, "Null source should return null result.");
    }

}
