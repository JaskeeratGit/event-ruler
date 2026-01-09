package software.amazon.event.ruler;

import java.lang.reflect.Constructor;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.annotation.Nullable;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

class NameStateWithPattern_equals_2_0_Test_equals_sameFieldsDifferentInstances_returnsTrue {

    // Helper to create a Patterns instance via reflection (constructor takes MatchType, supply null)
    private Patterns createPatterns() throws Exception {
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Constructor<?> ctor = patternsClass.getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        Object p = ctor.newInstance(new Object[] { null });
        return (Patterns) p;
    }



    @Test
    void equals_sameFieldsDifferentInstances_returnsTrue() throws Exception {
        NameState sharedNs = new NameState();
        Patterns sharedP = createPatterns();
        NameStateWithPattern a = new NameStateWithPattern(sharedNs, sharedP);
        NameStateWithPattern b = new NameStateWithPattern(sharedNs, sharedP);
        // both fields are the same object references -> equals should be true
        assertTrue(a.equals(b));
        // reflexive
        assertTrue(a.equals(a));
    }


}
