package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Patterns_existencePatterns_23_0_Test_testExistencePatternsTypeIsExists {


    @Test
    public void testExistencePatternsTypeIsExists() throws Exception {
        Object p = Patterns.existencePatterns();
        assertNotNull(p);
        // call the public type() method reflectively to avoid compile-time dependency on MatchType
        Method typeMethod = Patterns.class.getMethod("type");
        Object typeValue = typeMethod.invoke(p);
        // typeValue is expected to be an enum instance whose name() is "EXISTS"
        assertNotNull(typeValue, "type() should not return null for existencePatterns()");
        Method nameMethod = typeValue.getClass().getMethod("name");
        Object name = nameMethod.invoke(typeValue);
        assertEquals("EXISTS", name, "The MatchType name should be EXISTS for existencePatterns()");
    }


}
