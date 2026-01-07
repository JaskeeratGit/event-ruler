package software.amazon.event.ruler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import javax.annotation.Nonnull;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static software.amazon.event.ruler.SetOperations.intersection;

public class GenericMachine_addRule_18_0_Test_testAddRule_validJson_doesNotThrow {

    @Test
    public void testAddRule_validJson_doesNotThrow() throws Exception {
        // Arrange: create instance using public deprecated constructor
        GenericMachine machine = new GenericMachine();
        // Use reflection to obtain the addRule method (T is erased to Object at runtime)
        Method addRule = GenericMachine.class.getDeclaredMethod("addRule", Object.class, byte[].class);
        addRule.setAccessible(true);
        byte[] validJson = "[]".getBytes();
        // Act & Assert: invoking with valid JSON should not throw an IOException
        assertDoesNotThrow(() -> {
            try {
                addRule.invoke(machine, "ruleName", validJson);
            } catch (InvocationTargetException ite) {
                // Unwrap the cause and rethrow if it's a checked exception so assertDoesNotThrow can catch it
                Throwable cause = ite.getCause();
                if (cause instanceof IOException) {
                    throw (IOException) cause;
                } else {
                    throw new RuntimeException(cause);
                }
            }
        });
    }

}
