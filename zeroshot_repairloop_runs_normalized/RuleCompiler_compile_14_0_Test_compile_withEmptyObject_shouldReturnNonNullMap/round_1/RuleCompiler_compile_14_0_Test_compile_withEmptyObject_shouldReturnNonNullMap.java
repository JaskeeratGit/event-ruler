package software.amazon.event.ruler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RuleCompiler_compile_14_0_Test_compile_withEmptyObject_shouldReturnNonNullMap {

    @Test
    void compile_withEmptyObject_shouldReturnNonNullMap() throws IOException {
        // Provide a minimal valid rules object with an empty rule array for the compiler to accept.
        // We only assert that it returns a non-null map (implementation-specific contents may vary).
        try (InputStream is = new ByteArrayInputStream("{\"x\":[]}".getBytes())) {
            Map<String, ?> result = RuleCompiler.compile(is, true);
            assertNotNull(result, "compile should not return null for a minimal JSON rules object");
        }
    }

}
