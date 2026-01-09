package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.io.IOException;
import java.lang.reflect.Constructor;
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

public class RuleCompiler_compile_12_0_Test_testDoCompilePrivateMethod_withValidAndInvalidParser_usingReflection {






    @Test
    public void testDoCompilePrivateMethod_withValidAndInvalidParser_usingReflection() throws Exception {
        // Access private static doCompile(JsonParser, boolean)
        Method doCompile = RuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        JsonFactory factory = new JsonFactory();
        // Valid object parser -> should return a map (likely empty for "{}")
        try (JsonParser validParser = factory.createParser("{}".getBytes())) {
            @SuppressWarnings("unchecked")
            Map<String, ?> result = (Map<String, ?>) doCompile.invoke(null, validParser, false);
            assertNotNull(result, "doCompile should return non-null Map for valid object parser");
            assertEquals(0, result.size(), "Empty JSON object should produce an empty rule map");
        }
        // Invalid parser (non-object) -> should cause an IOException wrapped in InvocationTargetException
        try (JsonParser invalidParser = factory.createParser("[]".getBytes())) {
            InvocationTargetException ite = assertThrows(InvocationTargetException.class, () -> doCompile.invoke(null, invalidParser, false));
            Throwable cause = ite.getCause();
            assertNotNull(cause);
            assertTrue(cause instanceof IOException, "doCompile invoked on non-object JSON should throw an IOException (or subclass)");
        }
    }
}
