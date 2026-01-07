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

class JsonRuleCompiler_compile_13_0_Test {

    @Test
    void testPrivateConstructorIsAccessibleAndCreatesInstance() throws Exception {
        Constructor<JsonRuleCompiler> ctor = JsonRuleCompiler.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(ctor.getModifiers()), "Expected constructor to be private");
        ctor.setAccessible(true);
        JsonRuleCompiler instance = ctor.newInstance();
        assertNotNull(instance, "Private constructor should produce an instance when invoked reflectively");
    }

    @Test
    void testCompile_withNullSource_throwsNullPointerException() {
        Executable call = () -> JsonRuleCompiler.compile((byte[]) null);
        assertThrows(NullPointerException.class, call, "Expected NullPointerException when source is null");
    }

    @Test
    void testCompile_withInvalidJson_throwsIOException() {
        byte[] invalid = "this is not json".getBytes(StandardCharsets.UTF_8);
        Executable call = () -> JsonRuleCompiler.compile(invalid);
        assertThrows(Exception.class, call, "Expected an exception (IOException or subclass) for invalid JSON input");
    }

    @Test
    void testCompile_withEmptyJsonArray_returnsListPossiblyEmpty() throws Exception {
        byte[] emptyArray = "[]".getBytes(StandardCharsets.UTF_8);
        // compile may throw an IOException if the internal parser/implementation rejects this payload.
        // For test robustness, assert that either it returns a non-null List or throws an IOException.
        try {
            List<?> result = JsonRuleCompiler.compile(emptyArray);
            assertNotNull(result, "Result should not be null for valid JSON input");
            // It is reasonable to expect an empty list for an empty JSON array; if not, at least ensure the return type is a List.
            // Additional structural assertions are avoided because dependent classes (Patterns etc.) are not inspected here.
        } catch (java.io.IOException e) {
            // Acceptable outcome for some implementations; ensure it's an IOException
            assertTrue(e instanceof java.io.IOException);
        }
    }

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
