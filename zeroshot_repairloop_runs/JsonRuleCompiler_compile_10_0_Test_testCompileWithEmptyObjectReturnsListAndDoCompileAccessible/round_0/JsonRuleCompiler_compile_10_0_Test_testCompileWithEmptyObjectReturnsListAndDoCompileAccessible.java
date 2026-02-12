package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.JsonParseException;
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
import software.amazon.event.ruler.Patterns;

class JsonRuleCompiler_compile_10_0_Test_testCompileWithEmptyObjectReturnsListAndDoCompileAccessible {


    @Test
    void testCompileWithEmptyObjectReturnsListAndDoCompileAccessible() throws Exception {
        // Cover public compile method with a simple empty JSON object input.
        // The concrete behavior of parseObject is implementation-dependent; we at least assert we get a List back.
        List<Map<String, List<Patterns>>> result = JsonRuleCompiler.compile("{}", true);
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

}
