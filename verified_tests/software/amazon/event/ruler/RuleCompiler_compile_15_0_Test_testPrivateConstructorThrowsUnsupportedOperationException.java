package software.amazon.event.ruler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static software.amazon.event.ruler.input.DefaultParser.getParser;

public class RuleCompiler_compile_15_0_Test_testPrivateConstructorThrowsUnsupportedOperationException {



    @Test
    public void testPrivateConstructorThrowsUnsupportedOperationException() throws NoSuchMethodException {
        // Verify the private constructor is unavailable for instantiation and throws the expected exception
        Constructor<RuleCompiler> ctor = RuleCompiler.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ite = Assertions.assertThrows(InvocationTargetException.class, () -> ctor.newInstance());
        Assertions.assertNotNull(ite.getCause(), "Constructor invocation should have a cause");
        Assertions.assertTrue(ite.getCause() instanceof UnsupportedOperationException, "Cause should be UnsupportedOperationException");
        Assertions.assertEquals("You can't create instance of utility class.", ite.getCause().getMessage(), "Unexpected constructor exception message");
    }
}
