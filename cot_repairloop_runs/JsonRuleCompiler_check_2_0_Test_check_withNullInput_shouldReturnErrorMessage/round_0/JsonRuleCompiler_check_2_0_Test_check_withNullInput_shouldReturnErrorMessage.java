package software.amazon.event.ruler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for JsonRuleCompiler.check when a null String is passed.
 */
public class JsonRuleCompiler_check_2_0_Test_check_withNullInput_shouldReturnErrorMessage {

    @Test
    void check_withNullInput_shouldReturnErrorMessage() {
        // passing null should cause creation/parsing to fail and return an error message
        // Cast the null to String to avoid ambiguity with other overloaded check(...) methods.
        String result = JsonRuleCompiler.check((String) null, false);
        assertNotNull(result, "Expected non-null error message for null input");
        assertFalse(result.isEmpty(), "Expected non-empty error message for null input");
    }

}
