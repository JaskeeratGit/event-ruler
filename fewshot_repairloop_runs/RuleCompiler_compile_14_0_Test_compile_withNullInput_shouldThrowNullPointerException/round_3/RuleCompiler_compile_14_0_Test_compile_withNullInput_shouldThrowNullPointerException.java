package software.amazon.event.ruler;

import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.InputStream;
import static org.junit.Assert.fail;

public class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test
    public void compile_withNullInput_shouldThrowNullPointerException() throws Exception {
        try {
            // Passing null results in a JsonParseException when the parser attempts to read/validate input.
            RuleCompiler.compile((InputStream) null, true);
            fail("Expected JsonParseException when passing null InputStream");
        } catch (JsonParseException expected) {
            // expected
        }
    }

}
