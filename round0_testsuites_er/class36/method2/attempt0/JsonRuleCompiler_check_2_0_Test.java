package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
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

public class JsonRuleCompiler_check_2_0_Test {

    @Test
    void check_withValidEmptyObject_shouldReturnNull() {
        // "{}" is a valid JSON object; check should return null when compilation succeeds
        String result = JsonRuleCompiler.check("{}", false);
        assertNull(result, "Expected null result for valid empty JSON object");
    }

    @Test
    void check_withNonJsonString_shouldReturnErrorMessage() {
        // invalid JSON should cause an exception inside check and return a non-null message
        String result = JsonRuleCompiler.check("not a json", true);
        assertNotNull(result, "Expected non-null error message for invalid JSON");
        assertFalse(result.isEmpty(), "Expected non-empty error message for invalid JSON");
    }

    @Test
    void check_withNullInput_shouldReturnErrorMessage() {
        // passing null should cause creation/parsing to fail and return an error message
        String result = JsonRuleCompiler.check(null, false);
        assertNotNull(result, "Expected non-null error message for null input");
        assertFalse(result.isEmpty(), "Expected non-empty error message for null input");
    }

    @Test
    void privateConstructor_canBeInvokedReflectively() throws Exception {
        // Use reflection to access and invoke the private constructor
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Constructor should be private");
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        assertNotNull(instance, "Reflectively constructed instance should not be null");
        assertTrue(instance instanceof JsonRuleCompiler, "Instance should be of type JsonRuleCompiler");
    }
}
