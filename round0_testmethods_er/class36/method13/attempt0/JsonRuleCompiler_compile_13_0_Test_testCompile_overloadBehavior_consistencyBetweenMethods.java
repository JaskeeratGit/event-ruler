package software.amazon.event.ruler;

import org.junit.jupiter.api.function.Executable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class JsonRuleCompiler_compile_13_0_Test_testCompile_overloadBehavior_consistencyBetweenMethods {





    @Test
    void testCompile_overloadBehavior_consistencyBetweenMethods() throws Exception {
        byte[] payload = "[]".getBytes(StandardCharsets.UTF_8);
        // Use reflection to invoke the two-arg overload directly and compare behavior with one-arg method.
        // If either throws IOException, both should behave consistently; otherwise results should be non-null Lists.
        java.lang.reflect.Method twoArg = JsonRuleCompiler.class.getMethod("compile", byte[].class, boolean.class);
        try {
            Object fromTwoArg = twoArg.invoke(null, payload, true);
            Object fromOneArg = JsonRuleCompiler.compile(payload);
            assertNotNull(fromTwoArg);
            assertNotNull(fromOneArg);
            assertEquals(fromTwoArg.getClass(), fromOneArg.getClass(), "Expected both overloads to return the same runtime result type when both succeed");
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            // If the two-arg invocation throws an IOException (or other), ensure the single-arg also throws a similar exception.
            assertThrows(Exception.class, () -> JsonRuleCompiler.compile(payload));
            assertNotNull(cause);
        }
    }
}
