package software.amazon.event.ruler;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class RuleCompiler_compile_11_0_Test {

    @Test
    void privateConstructorThrowsUnsupportedOperationException() throws Exception {
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException thrown = assertThrows(InvocationTargetException.class, () -> ctor.newInstance());
        assertNotNull(thrown.getCause());
        assertTrue(thrown.getCause() instanceof UnsupportedOperationException);
        assertEquals("You can't create instance of utility class.", thrown.getCause().getMessage());
    }

    @Test
    void compileWithInvalidJsonThrowsIOException() {
        // invalid JSON should cause a JsonParseException (subclass of IOException) when compile tries to parse it
        assertThrows(IOException.class, () -> RuleCompiler.compile("{invalid-json: }"));
    }

    @Test
    void compileWithEmptyStringThrowsIOException() {
        // an empty source is not valid JSON; parser or compile should fail with IOException
        assertThrows(IOException.class, () -> RuleCompiler.compile(""));
    }

    @Test
    void compileWithNullSourceThrowsNullPointerException() {
        // JsonFactory.createParser(null) is expected to throw NullPointerException
        assertThrows(NullPointerException.class, () -> RuleCompiler.compile(null));
    }
}
