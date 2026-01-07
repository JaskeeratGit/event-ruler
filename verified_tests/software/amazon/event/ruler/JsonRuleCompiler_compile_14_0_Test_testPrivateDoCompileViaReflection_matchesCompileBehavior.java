package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import software.amazon.event.ruler.input.ParseException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
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
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class JsonRuleCompiler_compile_14_0_Test_testPrivateDoCompileViaReflection_matchesCompileBehavior {




    @Test
    void testPrivateDoCompileViaReflection_matchesCompileBehavior() throws Exception {
        // access private static JSON_FACTORY field
        Field jsonFactoryField = JsonRuleCompiler.class.getDeclaredField("JSON_FACTORY");
        jsonFactoryField.setAccessible(true);
        JsonFactory factory = (JsonFactory) jsonFactoryField.get(null);
        // create parser for an empty object
        try (InputStream is = new ByteArrayInputStream("{}".getBytes())) {
            JsonParser parser = factory.createParser(is);
            // access private static doCompile method
            Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
            doCompile.setAccessible(true);
            Object reflectedResult = doCompile.invoke(null, parser, true);
            assertNotNull(reflectedResult, "doCompile (via reflection) should not return null");
            @SuppressWarnings("unchecked")
            List<Map<String, List<?>>> reflectedList = (List<Map<String, List<?>>>) reflectedResult;
            assertTrue(reflectedList.isEmpty(), "Expected empty rules list from doCompile for empty JSON object");
        }
    }
}
