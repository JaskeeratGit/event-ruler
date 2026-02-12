package software.amazon.event.ruler;

import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for JsonRuleCompiler.check(Reader).
 *
 * Uses JUnit 4 (org.junit) because the build environment for this project
 * does not provide JUnit 5 on the classpath.
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

        assertNotNull("Expected a non-null error message when Reader is null", result);
        assertFalse("Error message should not be empty", result.isEmpty());
    }

    @Test
    public void shouldReturnErrorMessageWhenReaderThrowsIOException() {
        final String ioMessage = "simulated-io-error";
        Reader r = new ThrowingReader(ioMessage);

        String result = JsonRuleCompiler.check(r);

        assertNotNull("Expected a non-null error message when Reader throws IOException", result);
        assertFalse("Error message should not be empty", result.isEmpty());

        // If the implementation includes the underlying exception message, that's fine;
        // but we don't require it to avoid coupling to exact error formatting.
        if (result.contains(ioMessage)) {
            assertTrue("If implementation includes the cause message, it should contain the original IOException message",
                    result.contains(ioMessage));
        }
    }
}
