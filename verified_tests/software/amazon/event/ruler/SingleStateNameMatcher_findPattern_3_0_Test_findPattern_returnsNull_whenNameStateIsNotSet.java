package software.amazon.event.ruler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nonnull;

/**
 * JUnit 5 tests for SingleStateNameMatcher#findPattern(Patterns)
 */
public class SingleStateNameMatcher_findPattern_3_0_Test_findPattern_returnsNull_whenNameStateIsNotSet {

    @Test
    void findPattern_returnsNull_whenNameStateIsNotSet() throws Exception {
        SingleStateNameMatcher matcher = new SingleStateNameMatcher();
        // invoke the focal method directly
        // allowed to be null for this test
        Patterns patternArg = null;
        NameState resultDirect = matcher.findPattern(patternArg);
        assertNull(resultDirect, "Expected null when nameState was never set");
        // also invoke the method via reflection (demonstrates reflective invocation)
        Method findMethod = SingleStateNameMatcher.class.getDeclaredMethod("findPattern", Patterns.class);
        findMethod.setAccessible(true);
        Object resultReflect = findMethod.invoke(matcher, (Object) null);
        assertNull(resultReflect, "Expected null (reflective invocation) when nameState was never set");
    }

}
