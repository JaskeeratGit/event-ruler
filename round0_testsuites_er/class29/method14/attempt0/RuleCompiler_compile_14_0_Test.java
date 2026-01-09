package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import software.amazon.event.ruler.input.ParseException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class RuleCompiler_compile_14_0_Test {

    @Test
    void compile_withEmptyObject_shouldReturnNonNullMap() throws IOException {
        // "{}" is a valid JSON object; compile should advance past START_OBJECT and attempt to parse the contents.
        // We only assert that it returns a non-null map (implementation-specific contents may vary).
        try (InputStream is = new ByteArrayInputStream("{}".getBytes())) {
            Map<String, ?> result = RuleCompiler.compile(is, true);
            assertNotNull(result, "compile should not return null for an empty JSON object");
        }
    }

    @Test
    void compile_withNonObject_shouldThrowParseException() {
        // an array ([]) is not a JSON object; doCompile should detect this and barf.
        try (InputStream is = new ByteArrayInputStream("[]".getBytes())) {
            assertThrows(ParseException.class, () -> RuleCompiler.compile(is, false));
        } catch (IOException e) {
            // The try-with-resources may throw on close; fail the test if unexpected IOException occurs here
            fail("Unexpected IOException during test setup/teardown: " + e.getMessage());
        }
    }

    @Test
    void compile_withNullInput_shouldThrowNullPointerException() {
        // Passing null should result in a NullPointerException when the factory attempts to create a parser.
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile(null, true));
    }

    @Test
    void privateConstructor_shouldThrowUnsupportedOperationException() throws Exception {
        // Use reflection to invoke the private constructor and ensure it prevents instantiation.
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(InvocationTargetException.class, ctor::newInstance);
        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof UnsupportedOperationException, "Private constructor should throw UnsupportedOperationException");
    }
}
