package software.amazon.event.ruler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import software.amazon.event.ruler.JsonRuleCompiler;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class JsonRuleCompiler_compile_12_0_Test_testCompileDoesNotThrowForEmptyObject {



    @Test
    public void testCompileDoesNotThrowForEmptyObject() {
        // public compile should accept a JSON object
        Assertions.assertDoesNotThrow(() -> {
            List<?> rules = JsonRuleCompiler.compile("{}".getBytes(), false);
            Assertions.assertNotNull(rules);
        });
    }


}
