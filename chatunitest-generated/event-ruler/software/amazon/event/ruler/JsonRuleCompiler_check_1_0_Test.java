package software.amazon.event.ruler;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import software.amazon.event.ruler.input.ParseException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class JsonRuleCompiler_check_1_0_Test {

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
    void shouldReturnNullForValidJson() {
        // Use a minimal valid JSON construct. Expectation: check returns null when compilation succeeds.
        Reader valid = new StringReader("[]");
        String result = JsonRuleCompiler.check(valid);
        assertNull(result, "Expected null on successful check for valid JSON input");
    }

    @Test
    void shouldReturnErrorMessageWhenReaderThrowsIOException() {
        Reader bad = new ThrowingReader("simulated IO");
        String result = JsonRuleCompiler.check(bad);
        assertNotNull(result, "Expected a non-null error message when Reader throws IOException");
        assertTrue(result.contains("simulated IO"), "Error message should contain the underlying exception message");
    }

    @Test
    void shouldReturnErrorMessageWhenReaderIsNull() {
        // Disambiguate the null to the Reader overload to avoid ambiguity with other overloads
        String result = JsonRuleCompiler.check((Reader) null);
        assertNotNull(result, "Expected a non-null error message when Reader is null");
        assertFalse(result.isEmpty(), "Error message should not be empty");
    }

    @Test
    void invokePrivateConstructorForCoverage() throws Exception {
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance);
        assertTrue(instance instanceof JsonRuleCompiler);
    }
}
