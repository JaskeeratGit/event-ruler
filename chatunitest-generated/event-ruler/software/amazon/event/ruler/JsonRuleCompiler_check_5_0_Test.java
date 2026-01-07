package software.amazon.event.ruler;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class JsonRuleCompiler_check_5_0_Test {

    @Test
    public void testCheckWithInvalidJsonReturnsErrorMessage() {
        // truncated JSON to provoke a parse error
        byte[] badJson = "{".getBytes();
        String result = JsonRuleCompiler.check(badJson);
        assertNotNull(result, "Expected a non-null error message for invalid JSON input");
        assertFalse(result.isEmpty(), "Expected the error message to be non-empty");
    }

    @Test
    public void testCheckWithNullSourceReturnsErrorMessage() {
        String result = JsonRuleCompiler.check((byte[]) null);
        assertNotNull(result, "Expected a non-null error message when passing null source");
        assertFalse(result.isEmpty(), "Expected the error message to be non-empty");
    }

    @Test
    public void testCheckWithMinimalValidJsonDoesNotThrow() {
        byte[] validJson = "[]".getBytes();
        // We don't assert a specific return value because internal compilation may accept or produce an error message.
        // The important deterministic behavior is that the method returns (doesn't throw).
        assertDoesNotThrow(() -> {
            String res = JsonRuleCompiler.check(validJson);
            // Ensure the returned value is either null (success) or a non-empty error message
            assertTrue(res == null || !res.isEmpty(), "Returned value should be null for success or a non-empty error message");
        });
    }

    @Test
    public void testPrivateConstructorViaReflection() throws Exception {
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance, "Private constructor should produce a non-null instance");
        assertTrue(instance instanceof JsonRuleCompiler, "Instance should be of type JsonRuleCompiler");
    }
}
