package software.amazon.event.ruler;

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
import java.io.IOException;
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

class GenericMachine_builder_37_0_Test_builderViaReflectionProducesSameBuilderTypeAndIsCallableStatically {


    @Test
    void builderViaReflectionProducesSameBuilderTypeAndIsCallableStatically() throws Exception {
        // Use reflection to invoke the static builder() method
        Class<?> gmClass = Class.forName("software.amazon.event.ruler.GenericMachine");
        Method builderMethod = gmClass.getDeclaredMethod("builder");
        // Even though builder() is public, use setAccessible to demonstrate reflective invocation
        builderMethod.setAccessible(true);
        Object reflectedBuilder = builderMethod.invoke(null);
        assertNotNull(reflectedBuilder, "Reflective invocation of builder() should not return null");
        // Compare reflective result with direct call to ensure same runtime type
        Object directBuilder = GenericMachine.builder();
        assertEquals(directBuilder.getClass(), reflectedBuilder.getClass(), "Reflective and direct invocation should return the same Builder runtime type");
        // Multiple reflective invocations produce distinct instances
        Object reflectedBuilder2 = builderMethod.invoke(null);
        assertNotSame(reflectedBuilder, reflectedBuilder2, "Each reflective call to builder() should produce a new Builder instance");
    }

}
