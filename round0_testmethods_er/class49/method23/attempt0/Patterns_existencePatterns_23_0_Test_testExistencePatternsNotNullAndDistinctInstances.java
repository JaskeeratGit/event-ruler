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

public class Patterns_existencePatterns_23_0_Test_testExistencePatternsNotNullAndDistinctInstances {

    @Test
    public void testExistencePatternsNotNullAndDistinctInstances() throws Exception {
        Object p1 = Patterns.existencePatterns();
        Object p2 = Patterns.existencePatterns();
        assertNotNull(p1, "existencePatterns() should not return null");
        assertNotNull(p2, "existencePatterns() should not return null on subsequent call");
        assertNotSame(p1, p2, "existencePatterns() should create new instances on each call");
        assertEquals(Patterns.class, p1.getClass(), "Returned object should be instance of Patterns");
    }



}
