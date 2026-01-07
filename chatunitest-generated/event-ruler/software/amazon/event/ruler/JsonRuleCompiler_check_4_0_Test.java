package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class JsonRuleCompiler_check_4_0_Test {

    @Test
    void testCheckReturnsMessageForArrayRoot() {
        byte[] source = "[]".getBytes(StandardCharsets.UTF_8);
        // root is an array, doCompile should barf and check should return an error message
        String result = JsonRuleCompiler.check(source, false);
        assertNotNull(result, "Expected an error message when root is not an object");
        // underlying error text in doCompile is "Filter is not an object"
        assertTrue(result.contains("Filter is not an object"), () -> "Expected message to contain 'Filter is not an object' but was: " + result);
    }

    @Test
    void testPrivateDoCompileThrowsForArrayRootViaReflection() throws Exception {
        Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser("[]".getBytes(StandardCharsets.UTF_8));
        try {
            InvocationTargetException ex = assertThrows(InvocationTargetException.class, () -> doCompile.invoke(null, parser, Boolean.TRUE));
            Throwable cause = ex.getCause();
            assertNotNull(cause, "Underlying cause should be present");
            assertTrue(cause.getMessage().contains("Filter is not an object"), () -> "Expected cause message to contain 'Filter is not an object' but was: " + cause.getMessage());
        } finally {
            parser.close();
        }
    }
}
