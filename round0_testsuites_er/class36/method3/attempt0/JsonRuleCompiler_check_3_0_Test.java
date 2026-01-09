package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import software.amazon.event.ruler.JsonRuleCompiler;
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

public class JsonRuleCompiler_check_3_0_Test {

    @Test
    public void testCheckWithValidJsonReturnsNull() {
        String json = "{}";
        String result = JsonRuleCompiler.check(json);
        assertNull(result, "Valid JSON should return null (no error message).");
    }

    @Test
    public void testCheckWithInvalidJsonReturnsMessage() {
        String json = "{";
        String result = JsonRuleCompiler.check(json);
        assertNotNull(result, "Invalid JSON should return an error message.");
        assertFalse(result.isEmpty(), "Error message should not be empty.");
    }

    @Test
    public void testCheckWithNullSourceReturnsMessage() {
        String result = JsonRuleCompiler.check(null);
        assertNotNull(result, "Null source should return an error message (not null).");
        assertFalse(result.isEmpty(), "Error message should not be empty.");
    }

    @Test
    public void testPrivateConstructorCanBeInvokedViaReflection() throws Exception {
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        assertFalse(ctor.isAccessible(), "Constructor should be non-accessible (private).");
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance, "Private constructor should produce an instance when invoked reflectively.");
    }
}
