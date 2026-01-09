package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.lang.reflect.Constructor;
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
import com.fasterxml.jackson.core.JsonParseException;
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

class JsonRuleCompiler_compile_10_0_Test {

    @Test
    void testCompileThrowsOnNonObjectInput() throws Exception {
        // Use real Jackson parser to produce a token sequence that does not start with START_OBJECT
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser("[]");
        // Access the private static doCompile(JsonParser, boolean) via reflection
        Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        try {
            // Invoking the private method reflectively will wrap thrown exceptions in InvocationTargetException.
            doCompile.invoke(null, parser, false);
            fail("Expected doCompile to throw an exception for non-object input");
        } catch (InvocationTargetException ite) {
            // Ensure the underlying cause is an IOException (or subclass), as the method signature declares IOException
            Throwable cause = ite.getCause();
            assertNotNull(cause, "Expected a cause for InvocationTargetException");
            assertTrue(cause instanceof IOException, "Expected cause to be an IOException for non-object input");
            // Optionally check message contains the expected text used in source ("Filter is not an object")
            String msg = cause.getMessage();
            if (msg != null) {
                assertTrue(msg.contains("Filter is not an object") || msg.toLowerCase().contains("filter"), "Expected exception message to mention the filter/object problem");
            }
        } finally {
            parser.close();
        }
    }

    @Test
    void testCompileWithEmptyObjectReturnsListAndDoCompileAccessible() throws Exception {
        // Cover public compile method with a simple empty JSON object input.
        // The concrete behavior of parseObject is implementation-dependent; we at least assert we get a List back.
        List<Map<String, List<?>>> result = JsonRuleCompiler.compile("{}", true);
        assertNotNull(result, "compile should return a non-null List (possibly empty) for an empty JSON object");
        // Also invoke the private doCompile directly with a parser that starts with START_OBJECT and ensure it completes.
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser("{}");
        Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        try {
            Object invoked = doCompile.invoke(null, parser, true);
            // doCompile returns List<Map<String, List<Patterns>>>, so at least assert it's a List
            assertNotNull(invoked, "doCompile invocation should not return null for '{}'");
            assertTrue(invoked instanceof List, "doCompile should return a List");
        } catch (InvocationTargetException ite) {
            // If implementation throws, fail the test with the underlying cause
            throw new AssertionError("doCompile threw an unexpected exception", ite.getCause());
        } finally {
            parser.close();
        }
    }

    @Test
    void testPrivateConstructorViaReflection() throws Exception {
        // Cover the private constructor for code coverage using reflection
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        // Should be able to create an instance even though constructor is private (for coverage)
        JsonRuleCompiler instance = ctor.newInstance();
        assertNotNull(instance, "Reflection should construct an instance via the private constructor");
    }
}
