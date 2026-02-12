package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for JsonRuleCompiler.check(String).
 *
 * Uses JUnit 4 APIs to match the test runtime classpath.
 */
public class JsonRuleCompiler_check_3_0_Test_testCheckWithNullSourceReturnsMessage {

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        // Cast null to String to disambiguate overloaded check(...) methods (byte[] and InputStream overloads).
        String result = JsonRuleCompiler.check((String) null);

        // Verify that the method returns a non-null, non-empty error message when source is null.
        assertNotNull("Null source should return an error message (not null).", result);
        assertFalse("Error message should not be empty.", result.isEmpty());
    }

}
