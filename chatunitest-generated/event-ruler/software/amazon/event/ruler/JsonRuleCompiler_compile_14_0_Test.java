package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.fasterxml.jackson.core.StreamReadFeature;
import java.io.IOException;
import static software.amazon.event.ruler.input.DefaultParser.getParser;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonToken;
import software.amazon.event.ruler.input.ParseException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class JsonRuleCompiler_compile_14_0_Test {

    @Test
    void testCompileEmptyObject_throwsJsonParseException() throws IOException {
        try (InputStream is = new ByteArrayInputStream("{}".getBytes())) {
            assertThrows(JsonParseException.class, () -> JsonRuleCompiler.compile(is, false));
        }
    }

    @Test
    void testCompileNonObject_throwsJsonParseException() throws IOException {
        try (InputStream is = new ByteArrayInputStream("[]".getBytes())) {
            assertThrows(JsonParseException.class, () -> JsonRuleCompiler.compile(is, false));
        }
    }

    @Test
    void testCompileNullInput_throwsJsonParseException() {
        assertThrows(JsonParseException.class, () -> JsonRuleCompiler.compile((InputStream) null, false));
    }

    @Test
    void testPrivateDoCompileViaReflection_throwsJsonParseExceptionCause() throws Exception {
        // access private static JSON_FACTORY field
        Field jsonFactoryField = JsonRuleCompiler.class.getDeclaredField("JSON_FACTORY");
        jsonFactoryField.setAccessible(true);
        JsonFactory factory = (JsonFactory) jsonFactoryField.get(null);
        try (InputStream is = new ByteArrayInputStream("{}".getBytes())) {
            JsonParser parser = factory.createParser(is);
            // access private static doCompile method
            Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
            doCompile.setAccessible(true);
            InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> {
                doCompile.invoke(null, parser, true);
            });
            assertNotNull(ite.getCause(), "InvocationTargetException should have a cause");
            assertTrue(ite.getCause() instanceof JsonParseException, "Expected cause to be JsonParseException");
        }
    }
}
