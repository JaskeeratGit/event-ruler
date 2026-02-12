package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RuleCompiler_compile_14_0_Test_compile_withNullInput_shouldThrowNullPointerException {

    @Test
    public void compile_withNullInput_shouldThrowJsonParseException() throws Exception {
        // Passing null results in the JSON factory attempting to create a parser with a null InputStream,
        // which throws a JsonParseException.
        assertThrows(JsonParseException.class, () -> RuleCompiler.compile((InputStream) null, true));
    }

}
