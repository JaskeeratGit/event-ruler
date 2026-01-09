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

class Patterns_anythingButNumberMatch_13_0_Test_testAnythingButNumberMatch_withEmptySet_returnsNonNullAndMatchesPrivateMethodResultClass {

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


}
