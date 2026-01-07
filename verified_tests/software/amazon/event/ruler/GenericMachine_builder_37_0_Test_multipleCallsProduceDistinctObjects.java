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

class GenericMachine_builder_37_0_Test_multipleCallsProduceDistinctObjects {



    @Test
    void multipleCallsProduceDistinctObjects() {
        Object a = GenericMachine.builder();
        Object b = GenericMachine.builder();
        assertNotNull(a);
        assertNotNull(b);
        assertNotSame(a, b, "Subsequent calls to builder() return distinct instances");
        assertNotEquals(System.identityHashCode(a), System.identityHashCode(b), "Distinct instances should have different identity hash codes");
    }
}
