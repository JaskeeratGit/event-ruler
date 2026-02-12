package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Test;

import java.io.InputStream;

public class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test(expected = JsonParseException.class)
    public void compile_withNullInput_shouldThrowJsonParseException() throws Exception {
        // Passing null results in the JSON factory attempting to create a parser with a null InputStream,
        // which throws a JsonParseException.
        RuleCompiler.compile((InputStream) null, true);
    }

}
