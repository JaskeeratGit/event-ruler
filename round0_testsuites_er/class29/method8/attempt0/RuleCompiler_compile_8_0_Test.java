package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class RuleCompiler_compile_8_0_Test {

    @Test
    public void compile_returnsMap_forEmptyObject() throws Exception {
        // When compile is called with a bare empty JSON object it should return a Map (possibly empty)
        StringReader reader = new StringReader("{}");
        Map<?, ?> result = RuleCompiler.compile(reader, false);
        assertNotNull(result, "compile should not return null for an empty JSON object");
    }

    @Test
    public void compile_throwsOnMalformedJson() {
        // Malformed JSON (incomplete object) should result in an IOException / parse error
        StringReader reader = new StringReader("{");
        assertThrows(IOException.class, () -> RuleCompiler.compile(reader, false));
    }

    @Test
    public void private_doCompile_throws_whenNotObjectToken() throws Exception {
        // Use reflection to invoke the private doCompile(JsonParser, boolean) with a parser whose
        // first token is not START_OBJECT (here: an array). Expect the invocation to throw an exception.
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new StringReader("[]"));
        Method doCompile = RuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        // Invocation will wrap the underlying exception in InvocationTargetException
        InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
            doCompile.invoke(null, parser, Boolean.FALSE);
        });
        assertNotNull(ite.getCause(), "Underlying cause should be present");
    }
}
