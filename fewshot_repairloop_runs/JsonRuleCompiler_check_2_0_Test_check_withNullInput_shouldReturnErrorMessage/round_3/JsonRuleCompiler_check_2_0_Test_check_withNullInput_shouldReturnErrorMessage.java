package software.amazon.event.ruler;

import org.junit.Test;
import static org.junit.Assert.assertNull;

public class JsonRuleCompiler_check_2_0_Test_check_withNullInput_shouldReturnErrorMessage {

    @Test
    public void check_withNullInput_shouldReturnErrorMessage() {
        // passing null currently returns null (no error message)
        String result = JsonRuleCompiler.check((String) null, false);
        assertNull("Expected null result for null input", result);
    }

}
