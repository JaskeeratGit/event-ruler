package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import software.amazon.event.ruler.JsonRuleCompiler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import software.amazon.event.ruler.input.ParseException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class JsonRuleCompiler_compile_12_0_Test {

    @Test
    public void testPrivateConstructorAccessible() throws Exception {
        // cover private constructor via reflection
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        Assertions.assertFalse(ctor.canAccess(null));
        ctor.setAccessible(true);
        Object instance = ctor.newInstance();
        Assertions.assertNotNull(instance);
    }

    @Test
    public void testDoCompileWithEmptyObject_viaReflection() throws Exception {
        // Invoke private static doCompile(JsonParser, boolean) with a parser over "{}"
        JsonFactory factory = new JsonFactory();
        try (ByteArrayInputStream in = new ByteArrayInputStream("{}".getBytes())) {
            JsonParser parser = factory.createParser(in);
            Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
            doCompile.setAccessible(true);
            Object result = doCompile.invoke(null, parser, true);
            Assertions.assertNotNull(result, "doCompile should return a non-null result for empty object");
            Assertions.assertTrue(result instanceof List, "doCompile should return a List");
        }
    }

    @Test
    public void testCompileDoesNotThrowForEmptyObject() {
        // public compile should accept a JSON object
        Assertions.assertDoesNotThrow(() -> {
            List<?> rules = JsonRuleCompiler.compile("{}".getBytes(), false);
            Assertions.assertNotNull(rules);
        });
    }

    @Test
    public void testCompileThrowsForNonObjectStart() {
        // When JSON does not start with an object (e.g., array), compile should throw an exception
        Assertions.assertThrows(Exception.class, () -> JsonRuleCompiler.compile("[]".getBytes(), true));
    }

    @Test
    public void testCompileThrowsForMalformedJson() {
        // Malformed JSON should result in an IOException (JsonParseException)
        Assertions.assertThrows(IOException.class, () -> JsonRuleCompiler.compile("{".getBytes(), true));
    }
}
