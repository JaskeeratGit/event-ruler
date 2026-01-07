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
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void testCompileWithArrayThrowsIOException() {
        // If the top-level JSON token is not an object, doCompile should barf -> an IOException (or subtype).
        assertThrows(IOException.class, () -> RuleCompiler.compile("[]", true));
    }

    @Test
    public void testCompileWithMalformedJsonThrowsJsonParseException() {
        // Malformed JSON should throw a JsonParseException (subclass of IOException)
        assertThrows(JsonParseException.class, () -> RuleCompiler.compile("{", true));
    }
}
