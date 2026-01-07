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

public class Patterns_existencePatterns_23_0_Test {

    @Test
    public void testExistencePatternsNotNullAndDistinctInstances() throws Exception {
        Object p1 = Patterns.existencePatterns();
        Object p2 = Patterns.existencePatterns();
        assertNotNull(p1, "existencePatterns() should not return null");
        assertNotNull(p2, "existencePatterns() should not return null on subsequent call");
        assertNotSame(p1, p2, "existencePatterns() should create new instances on each call");
        assertEquals(Patterns.class, p1.getClass(), "Returned object should be instance of Patterns");
    }

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

    @Test
    public void testExistsByteStringConstant() throws Exception {
        Field f = Patterns.class.getField("EXISTS_BYTE_STRING");
        Object val = f.get(null);
        assertEquals("N", val, "EXISTS_BYTE_STRING should be \"N\"");
    }

    @Test
    public void testConstructorAccessibleAndCreatesInstanceWithNullParameter() throws Exception {
        // Find a constructor with exactly one parameter (the MatchType parameter) without referencing MatchType
        Constructor<?> targetCtor = null;
        for (Constructor<?> ctor : Patterns.class.getDeclaredConstructors()) {
            if (ctor.getParameterCount() == 1) {
                targetCtor = ctor;
                break;
            }
        }
        assertNotNull(targetCtor, "Expected a single-arg constructor on Patterns");
        targetCtor.setAccessible(true);
        // pass null for the MatchType parameter
        Object created = targetCtor.newInstance((Object) null);
        assertNotNull(created, "Constructor should create a Patterns instance even when passed null");
        assertEquals(Patterns.class, created.getClass(), "Constructed object should be of type Patterns");
        // Invoke type() on the instance; allow either null or a MatchType instance (no Class dependency)
        Method typeMethod = Patterns.class.getMethod("type");
        Object typeValue = typeMethod.invoke(created);
        // We only assert that calling type() does not throw and returns either null or an enum-like object
        if (typeValue != null) {
            Method nameMethod = typeValue.getClass().getMethod("name");
            Object name = nameMethod.invoke(typeValue);
            assertNotNull(name, "If type() is non-null it should have a name()");
        }
    }
}
