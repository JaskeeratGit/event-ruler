package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.assertNull;

public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        String result = JsonRuleCompiler.check((String) null);
        assertNull("Null source should return null.", result);
    }

}
