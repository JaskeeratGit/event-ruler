package software.amazon.event.ruler;

import org.junit.Test;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;

public class RuleCompiler_compile_14_0_Test_compile_withEmptyObject_shouldReturnNonNullMap {

    @Test(expected = JsonParseException.class)
    public void compile_withEmptyObject_shouldReturnNonNullMap() throws IOException {
        RuleCompiler.compile(new java.io.ByteArrayInputStream("{}".getBytes()), true);
    }
}
