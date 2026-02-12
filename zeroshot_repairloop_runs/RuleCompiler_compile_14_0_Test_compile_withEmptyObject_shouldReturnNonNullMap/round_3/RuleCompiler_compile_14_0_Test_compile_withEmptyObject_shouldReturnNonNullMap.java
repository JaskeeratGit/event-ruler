package software.amazon.event.ruler;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import static org.junit.Assert.assertNotNull;

public class RuleCompiler_compile_14_0_Test_compile_withEmptyObject_shouldReturnNonNullMap {

    @Test
    public void compile_withEmptyObject_shouldReturnNonNullMap() throws Exception {
        // "{}" is a valid JSON object; compile should advance past START_OBJECT and attempt to parse the contents.
        // We only assert that it returns a non-null map (implementation-specific contents may vary).
        try (InputStream is = new ByteArrayInputStream("{}".getBytes(StandardCharsets.UTF_8))) {
            Map<String, ?> result = RuleCompiler.compile(is, true);
            assertNotNull("compile should not return null for an empty JSON object", result);
        }
    }

}
