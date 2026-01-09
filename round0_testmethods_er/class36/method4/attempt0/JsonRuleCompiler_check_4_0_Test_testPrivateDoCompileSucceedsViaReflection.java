package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

class JsonRuleCompiler_check_4_0_Test_testPrivateDoCompileSucceedsViaReflection {



    @Test
    void testPrivateDoCompileSucceedsViaReflection() throws Exception {
        Method doCompile = JsonRuleCompiler.class.getDeclaredMethod("doCompile", JsonParser.class, boolean.class);
        doCompile.setAccessible(true);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser("{}".getBytes(StandardCharsets.UTF_8));
        try {
            Object returned = doCompile.invoke(null, parser, Boolean.FALSE);
            assertNotNull(returned, "doCompile should return a non-null List");
            assertTrue(returned instanceof List, "doCompile should return a List");
            // noinspection unchecked
            List<?> rules = (List<?>) returned;
            assertNotNull(rules);
        } finally {
            parser.close();
        }
    }

}
