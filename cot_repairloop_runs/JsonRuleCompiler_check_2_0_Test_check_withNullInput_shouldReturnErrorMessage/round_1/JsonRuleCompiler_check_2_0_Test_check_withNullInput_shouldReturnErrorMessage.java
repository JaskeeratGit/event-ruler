package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for JsonRuleCompiler.check when a null String is passed.
 *
 * Note: Use JUnit 4 imports (org.junit.*) so the test compiles in projects that don't have JUnit 5 on the classpath.
 */
public class JsonRuleCompiler_check_2_0_Test_check_withNullInput_shouldReturnErrorMessage {

    @Test
    public void check_withNullInput_shouldReturnErrorMessage() {
        // passing null should cause creation/parsing to fail and return an error message
        // Cast the null to String to avoid ambiguity with other overloaded check(...) methods.
        String result = JsonRuleCompiler.check((String) null, false);

        // verify an error message (non-null and non-empty) is returned
        assertNotNull("Expected non-null error message for null input", result);
        assertFalse("Expected non-empty error message for null input", result.isEmpty());
    }

}
