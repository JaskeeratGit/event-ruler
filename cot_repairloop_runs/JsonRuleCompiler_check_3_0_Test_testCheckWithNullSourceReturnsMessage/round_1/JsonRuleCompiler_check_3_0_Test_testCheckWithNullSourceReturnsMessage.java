package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Fixed unit test that uses JUnit 4 APIs (org.junit) so it compiles in projects
 * that do not have JUnit 5 (junit-jupiter) on the classpath.
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
