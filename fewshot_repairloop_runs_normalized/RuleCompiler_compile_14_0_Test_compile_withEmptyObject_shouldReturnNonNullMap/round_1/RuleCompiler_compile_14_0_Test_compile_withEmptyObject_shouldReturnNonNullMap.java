package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonParseException;
import software.amazon.event.ruler.input.ParseException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadFeature;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class RuleCompiler_compile_14_0_Test_compile_withEmptyObject_shouldReturnNonNullMap {

    @Test
    void compile_withEmptyObject_shouldReturnNonNullMap() throws IOException {
        // "{}" is a valid JSON object; compile should advance past START_OBJECT and attempt to parse the contents.
        // Current implementation rejects empty objects; assert that a JsonParseException is thrown with the expected message.
        try (InputStream is = new ByteArrayInputStream("{}".getBytes())) {
            JsonParseException ex = assertThrows(JsonParseException.class, () -> RuleCompiler.compile(is, false));
            assertTrue(ex.getMessage().contains("Empty objects are not allowed"), "Expected message about empty objects");
        }
    }



}
