package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import software.amazon.event.ruler.RuleCompiler;
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
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import software.amazon.event.ruler.input.ParseException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class RuleCompiler_compile_10_0_Test {

    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> ctor.newInstance());
        assertNotNull(ite.getCause());
        assertTrue(ite.getCause() instanceof UnsupportedOperationException, "Expected cause to be UnsupportedOperationException");
    }

    @Test
    public void testCompileWithEmptyObjectReturnsEmptyMap() throws Exception {
        Map<String, List<?>> result = RuleCompiler.compile("{}", false);
        assertNotNull(result, "Result map should not be null");
        assertTrue(result.isEmpty(), "Result map should be empty for empty JSON object");
    }

    @Test
    public void testCompileWithArrayThrowsIOException() {
        // If the top-level JSON token is not an object, doCompile should barf -> an IOException (or subtype).
        assertThrows(IOException.class, () -> RuleCompiler.compile("[]", true));
    }

    @Test
    public void testCompileWithMalformedJsonThrowsJsonParseException() {
        // Malformed JSON should throw a JsonParseException (subclass of IOException)
        assertThrows(JsonParseException.class, () -> RuleCompiler.compile("{", true));
    }

    @Test
    public void testDoCompileViaReflectionWithEmptyObject() throws Exception {
        // Invoke private static doCompile(JsonParser, boolean) via reflection to increase coverage.
        Method doCompile = RuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser("{}");
        // static method -> null target
        Object result = doCompile.invoke(null, parser, false);
        assertNotNull(result);
        assertTrue(result instanceof Map, "Expected a Map result from doCompile");
        @SuppressWarnings("unchecked")
        Map<String, List<?>> map = (Map<String, List<?>>) result;
        assertTrue(map.isEmpty(), "doCompile on empty JSON object should return an empty map");
    }
}
