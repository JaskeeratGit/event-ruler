package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParseException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import software.amazon.event.ruler.input.ParseException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class JsonRuleCompiler_compile_11_0_Test {

    private static Method doCompileMethod;

    private static JsonFactory jsonFactory;

    @BeforeAll
    public static void setUp() throws NoSuchMethodException {
        // reflectively get the private static doCompile(JsonParser, boolean) method
        doCompileMethod = software.amazon.event.ruler.JsonRuleCompiler.class.getDeclaredMethod("doCompile", com.fasterxml.jackson.core.JsonParser.class, boolean.class);
        doCompileMethod.setAccessible(true);
        jsonFactory = new JsonFactory();
    }

    @Test
    public void testCompile_withMalformedJson_throwsException() {
        String badJson = "{ this is : not json }";
        // compile may throw JsonParseException or IOException (or wrapped), accept any Exception
        assertThrows(Exception.class, () -> software.amazon.event.ruler.JsonRuleCompiler.compile(badJson));
    }

    @Test
    public void test_DoCompile_private_invocation_withMalformedParser_throws() throws Exception {
        String badSource = "{ not: valid, json";
        JsonParser badParser = jsonFactory.createParser(badSource);
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> {
            // invoke will wrap underlying exception in InvocationTargetException
            doCompileMethod.invoke(null, badParser, true);
        });
        // underlying cause should be a parsing related exception (JsonParseException or IOException)
        Throwable cause = thrown.getCause();
        assertNotNull(cause, "Underlying cause should be present");
        assertTrue(cause instanceof IOException || cause instanceof JsonParseException || cause instanceof RuntimeException, "Expected parsing or IO related exception as cause");
    }
}
