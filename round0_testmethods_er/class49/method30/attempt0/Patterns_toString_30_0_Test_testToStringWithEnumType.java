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

public class Patterns_toString_30_0_Test_testToStringWithEnumType {

    @Test
    public void testToStringWithEnumType() throws Exception {
        // Load MatchType enum via reflection and take its first constant
        Class<?> matchTypeClass = Class.forName("software.amazon.event.ruler.MatchType");
        Object[] enumConstants = matchTypeClass.getEnumConstants();
        assertNotNull(enumConstants, "MatchType should be an enum with constants");
        assertTrue(enumConstants.length > 0, "MatchType should have at least one constant");
        Object firstConstant = enumConstants[0];
        // Instantiate Patterns using the package-private constructor via reflection
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Constructor<?> ctor = patternsClass.getDeclaredConstructor(matchTypeClass);
        ctor.setAccessible(true);
        Object patternsInstance = ctor.newInstance(firstConstant);
        // Invoke toString() and compare with expected "T:" + matchType.toString()
        Method toStringMethod = patternsClass.getMethod("toString");
        Object actual = toStringMethod.invoke(patternsInstance);
        String expected = "T:" + firstConstant.toString();
        assertEquals(expected, actual);
    }


}
