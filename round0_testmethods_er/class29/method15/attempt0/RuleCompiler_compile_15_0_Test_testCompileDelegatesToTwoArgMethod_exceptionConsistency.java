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

public class RuleCompiler_compile_15_0_Test_testCompileDelegatesToTwoArgMethod_exceptionConsistency {

    @Test
    public void testCompileDelegatesToTwoArgMethod_exceptionConsistency() {
        // Use two fresh identical empty streams so each invocation gets its own stream
        InputStream streamForOneArg = new ByteArrayInputStream(new byte[0]);
        InputStream streamForTwoArg = new ByteArrayInputStream(new byte[0]);
        Exception exOneArg = Assertions.assertThrows(Exception.class, () -> RuleCompiler.compile(streamForOneArg));
        Exception exTwoArg = Assertions.assertThrows(Exception.class, () -> RuleCompiler.compile(streamForTwoArg, true));
        // Ensure delegation: both overloads produce the same exception type and message for identical input
        Assertions.assertEquals(exOneArg.getClass(), exTwoArg.getClass(), "Exception types should match");
        Assertions.assertEquals(exOneArg.getMessage(), exTwoArg.getMessage(), "Exception messages should match");
    }


}
