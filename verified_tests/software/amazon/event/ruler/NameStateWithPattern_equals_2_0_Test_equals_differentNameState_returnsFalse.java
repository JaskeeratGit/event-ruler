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

class NameStateWithPattern_equals_2_0_Test_equals_differentNameState_returnsFalse {

    // Helper to create a Patterns instance via reflection (constructor takes MatchType, supply null)
    private Patterns createPatterns() throws Exception {
        Class<?> patternsClass = Class.forName("software.amazon.event.ruler.Patterns");
        Constructor<?> ctor = patternsClass.getDeclaredConstructors()[0];
        ctor.setAccessible(true);
        Object p = ctor.newInstance(new Object[] { null });
        return (Patterns) p;
    }




    @Test
    void equals_differentNameState_returnsFalse() throws Exception {
        NameState ns1 = new NameState();
        NameState ns2 = new NameState();
        Patterns p = createPatterns();
        NameStateWithPattern a = new NameStateWithPattern(ns1, p);
        NameStateWithPattern b = new NameStateWithPattern(ns2, p);
        assertFalse(a.equals(b));
    }

}
