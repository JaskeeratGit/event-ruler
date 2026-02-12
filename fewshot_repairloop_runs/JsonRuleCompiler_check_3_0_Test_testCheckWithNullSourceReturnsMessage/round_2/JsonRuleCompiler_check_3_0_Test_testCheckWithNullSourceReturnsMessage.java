package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        String result = JsonRuleCompiler.check((String) null);
        assertNotNull("Null source should return an error message (not null).", result);
        assertFalse("Error message should not be empty.", result.isEmpty());
    }

}
