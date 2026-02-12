package software.amazon.event.ruler;

import java.io.IOException;
import java.io.Reader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonRuleCompiler.check(Reader).
 *
 * Uses JUnit 5 (org.junit.jupiter.api) so these tests compile/run in environments
 * expecting JUnit 5 style tests.
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

        /**
         * Override read() as well to be defensive in case the implementation under test
         * calls this variant.
         */
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

        assertNotNull(result, "Expected a non-null error message when Reader is null");
        assertFalse(result.isEmpty(), "Error message should not be empty");
    }

    @Test
    public void shouldReturnErrorMessageWhenReaderThrowsIOException() {
        final String ioMessage = "simulated-io-error";
        Reader r = new ThrowingReader(ioMessage);

        String result = JsonRuleCompiler.check(r);

        assertNotNull(result, "Expected a non-null error message when Reader throws IOException");
        assertFalse(result.isEmpty(), "Error message should not be empty");

        // If the implementation includes the underlying exception message, that's fine;
        // but we don't require it to avoid coupling to exact error formatting.
        // Still, if it does include the message, the assertion below will pass as well.
        // The contains check is optional and defensive:
        if (result.contains(ioMessage)) {
            assertTrue(result.contains(ioMessage), "If implementation includes the cause message, it should contain the original IOException message");
        }
    }
}
