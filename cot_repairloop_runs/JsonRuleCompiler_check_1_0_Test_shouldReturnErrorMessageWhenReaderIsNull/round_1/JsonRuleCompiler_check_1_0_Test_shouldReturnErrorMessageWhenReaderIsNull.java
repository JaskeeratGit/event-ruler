package software.amazon.event.ruler;

import java.io.IOException;
import java.io.Reader;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for JsonRuleCompiler.check(Reader).
 *
 * Adjusted to use JUnit 4 (org.junit.Test and org.junit.Assert) so the test
 * compiles in environments that don't have JUnit 5 on the classpath.
 */
public class JsonRuleCompiler_check_1_0_Test_shouldReturnErrorMessageWhenReaderIsNull {

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
    public void shouldReturnErrorMessageWhenReaderIsNull() {
        // Passing null should be caught and an error message returned (non-null).
        // Cast null to Reader to avoid ambiguity with other overloaded check(...) methods.
        String result = JsonRuleCompiler.check((Reader) null);

        // JUnit4 assert signatures take the message first.
        assertNotNull("Expected a non-null error message when Reader is null", result);
        assertFalse("Error message should not be empty", result.isEmpty());
    }
}
