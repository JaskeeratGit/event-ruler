package software.amazon.event.ruler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.stream.Collectors;

class Patterns_anythingButNumberMatch_13_0_Test {

    @Test
    void testAnythingButNumberMatch_withEmptySet_returnsNonNullAndMatchesPrivateMethodResultClass() throws Exception {
        Method publicMethod = Patterns.class.getDeclaredMethod("anythingButNumberMatch", Set.class);
        publicMethod.setAccessible(true);
        Set<Double> input = Collections.emptySet();
        Object publicResult = publicMethod.invoke(null, input);
        assertNotNull(publicResult, "Public method should not return null for empty set");
        Method privateMethod = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        privateMethod.setAccessible(true);
        Set<String> expectedStrings = Collections.emptySet();
        Object privateResult = privateMethod.invoke(null, expectedStrings);
        assertNotNull(privateResult, "Private method should not return null for empty string set");
        // We assert that both results are of the same runtime class (behavior should be consistent)
        assertEquals(privateResult.getClass(), publicResult.getClass(), "Public and private method results should be of the same runtime class");
    }

    @Test
    void testAnythingButNumberMatch_withVariousDoubles_handlesDifferentDoubleValues() throws Exception {
        Method publicMethod = Patterns.class.getDeclaredMethod("anythingButNumberMatch", Set.class);
        publicMethod.setAccessible(true);
        Set<Double> doubles = new HashSet<>();
        doubles.add(1.23);
        doubles.add(-0.0);
        doubles.add(Double.NaN);
        doubles.add(Double.POSITIVE_INFINITY);
        doubles.add(-456.0);
        Object publicResult = publicMethod.invoke(null, doubles);
        assertNotNull(publicResult, "Public method should not return null for non-empty set");
        Method privateMethod = Patterns.class.getDeclaredMethod("anythingButNumbersMatch", Set.class);
        privateMethod.setAccessible(true);
        Set<String> strings = new HashSet<>();
        for (Double d : doubles) {
            strings.add(Double.toString(d));
        }
        Object privateResult = privateMethod.invoke(null, strings);
        assertNotNull(privateResult, "Private method should not return null for corresponding string set");
        // Ensure both produce the same runtime class
        assertEquals(privateResult.getClass(), publicResult.getClass(), "Public and private method results should be of the same runtime class for various doubles");
    }

    @Test
    void testAnythingButNumberMatch_withNull_throwsNullPointerExceptionWrappedInInvocationTargetException() throws Exception {
        Method publicMethod = Patterns.class.getDeclaredMethod("anythingButNumberMatch", Set.class);
        publicMethod.setAccessible(true);
        try {
            // invoking with null should cause the stream operation inside the method to throw a NullPointerException,
            // which will be wrapped in InvocationTargetException by Method.invoke
            publicMethod.invoke(null, (Object) null);
            fail("Expected InvocationTargetException when invoking with null");
        } catch (InvocationTargetException ite) {
            assertNotNull(ite.getCause(), "InvocationTargetException should have a cause");
            assertTrue(ite.getCause() instanceof NullPointerException, "Cause of InvocationTargetException should be a NullPointerException when input is null");
        }
    }
}
