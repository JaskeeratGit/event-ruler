package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RuleCompiler_compile_14_0_Test_compile_withEmptyObject_shouldReturnNonNullMap {

    @Test
    public void compile_withEmptyObject_shouldThrowJsonParseException() throws Exception {
        try (InputStream is = new ByteArrayInputStream("{}".getBytes(StandardCharsets.UTF_8))) {
            Assertions.assertThrows(JsonParseException.class, () -> RuleCompiler.compile(is, true));
        }
    }

}
