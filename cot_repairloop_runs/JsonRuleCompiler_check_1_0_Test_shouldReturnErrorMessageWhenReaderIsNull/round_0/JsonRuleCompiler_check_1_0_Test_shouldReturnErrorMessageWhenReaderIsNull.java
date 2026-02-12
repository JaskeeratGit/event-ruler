package software.amazon.event.ruler;

import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for JsonRuleCompiler.check(Reader).
 */
class JsonRuleCompiler_check_1_0_Test_shouldReturnErrorMessageWhenReaderIsNull {

    private static class ThrowingReader extends Reader {

        private final String message;

        ThrowingReader(String message) {
            this.message = message;
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new IOException(message);
        }

        @Override
        public void close() throws IOException {
            // no-op
        }

        @Override
        public int read() throws IOException {
            throw new IOException(message);
        }
    }

    @Test
    void shouldReturnErrorMessageWhenReaderIsNull() {
        // Passing null should be caught and an error message returned (non-null).
        // Cast null to Reader to avoid ambiguity with other overloaded check(...) methods.
        String result = JsonRuleCompiler.check((Reader) null);
        assertNotNull(result, "Expected a non-null error message when Reader is null");
        assertFalse(result.isEmpty(), "Error message should not be empty");
    }
}
